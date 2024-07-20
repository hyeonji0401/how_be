import sys
import json
from transformers import BertModel, BertTokenizer
import numpy as np

# BERT 모델과 토크나이저 로드
model_name = 'bert-base-multilingual-cased'
tokenizer = BertTokenizer.from_pretrained(model_name)
model = BertModel.from_pretrained(model_name)

# 직종명을 임베딩으로 변환하는 함수
def get_embedding(text):
    inputs = tokenizer(text, return_tensors='pt', truncation=True, padding=True, max_length=32)
    outputs = model(**inputs)
    return outputs.last_hidden_state.mean(dim=1).detach().numpy().squeeze()

# JSON 파일 로드 함수
def load_json(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        return json.load(file)

# 임베딩 벡터 간의 코사인 유사도 계산 함수
def cosine_similarity(vec1, vec2):
    vec1 = vec1 / np.linalg.norm(vec1)
    vec2 = vec2 / np.linalg.norm(vec2)
    return np.dot(vec1, vec2)

# 사용자 직종명과 회사 직종명이 관련이 있는지 확인하는 함수
def is_related_job_with_bert(user_job, job_categories):
    user_embedding = get_embedding(user_job)
    highest_similarity = 0
    best_category = None

    for category in job_categories.keys():
        category_embedding = get_embedding(category)
        similarity = cosine_similarity(user_embedding, category_embedding)
        if similarity > highest_similarity:
            highest_similarity = similarity
            best_category = category

    return best_category

# 직종명을 기반으로 일자리를 필터링하는 함수
def filter_jobs(user_info, companies, job_categories):
    recommended_jobs = []
    best_category = is_related_job_with_bert(user_info['jobNm'], job_categories)

    for company in companies:
        company_data = company['data']

        # 직종 필터링
        if not best_category or not any(job in company_data.get('jobNm', '') for job in job_categories[best_category]):
            continue

        # 지역 필터링
        if user_info.get('location') and user_info['location'] not in company_data['compAddr']:
            continue

        # 작업 환경 필터링
        if 'envBothHands' in company_data and not is_condition_satisfied(user_info['bothHands'], company_data['envBothHands']):
            continue
        if 'envEyesight' in company_data and not is_condition_satisfied(user_info['eyesight'], company_data['envEyesight']):
            continue
        if 'envHandwork' in company_data and not is_condition_satisfied(user_info['handwork'], company_data['envHandwork']):
            continue
        if 'envLiftPower' in company_data and not is_condition_satisfied(user_info['liftPower'], company_data['envLiftPower']):
            continue
        if 'envLstnTalk' in company_data and not is_condition_satisfied(user_info['lstnTalk'], company_data['envLstnTalk']):
            continue
        if 'envStndWalk' in company_data and not is_condition_satisfied(user_info['stndWalk'], company_data['envStndWalk']):
            continue

        # 자격증 필터링
        if 'reqLicens' in company_data and company_data['reqLicens'] != "무관" and not is_licenses_satisfied(user_info['licenses'], company_data['reqLicens']):
            continue

        # 요구 학력 필터링
        if user_info.get('education') and not is_education_satisfied(user_info['education'], company_data.get('reqEduc', '무관')):
            continue

        # 요구 경력 필터링
        if user_info.get('career') and not is_career_satisfied(user_info['career'], company_data.get('reqCareer', '무관')):
            continue

        recommended_jobs.append(company)

    return recommended_jobs

# 사용자 조건이 회사 조건을 충족하는지 확인하는 함수
def is_condition_satisfied(user_condition, company_condition):
    conditions_order = {
        '한손보조작업 가능': 1,
        '양손작업 가능': 2,
        '비교적 큰 인쇄물': 1,
        '작은 인쇄물': 2,
        '일상적 활동 가능': 3,
        '큰 물품 조립가능': 1,
        '작은 물품 조립가능': 2,
        '정밀한 작업가능': 3,
        '5Kg 이내의 물건을 다룰 수 있음': 1,
        '5~20Kg의 물건을 다룰 수 있음': 2,
        '20Kg 이상의 물건을 다룰 수 있음': 3,
        '듣고 말하는 작업 어려움': 1,
        '간단한 듣고 말하기 가능': 2,
        '듣고 말하기에 어려움 없음': 3,
        '서거나 걷는 일 어려움': 1,
        '일부 서서하는 작업 가능': 2,
        '오랫동안 가능': 3
    }

    user_condition_translated = {
        'BOTH_HANDS': '양손작업 가능',
        'ONE_HANDS_ASSIST': '한손보조작업 가능',
        'NORMAL_ACTIVITY': '일상적 활동 가능',
        'LARGE_PRINT': '비교적 큰 인쇄물',
        'SMALL_PRINT': '작은 인쇄물',
        'PRECISION_WORK': '정밀한 작업가능',
        'SMALL_ASSEMBLY': '작은 물품 조립가능',
        'LARGE_ASSEMBLY': '큰 물품 조립가능',
        'UNDER_5KG': '5Kg 이내의 물건을 다룰 수 있음',
        'FROM_5KG_TO_20KG': '5~20Kg의 물건을 다룰 수 있음',
        'ABOVE_20KG': '20Kg 이상의 물건을 다룰 수 있음',
        'NO_DIFFICULTY': '듣고 말하기에 어려움 없음',
        'SIMPLE_CONVERSATION': '간단한 듣고 말하기 가능',
        'DIFFICULTY': '듣고 말하는 작업 어려움',
        'SOME_STANDING': '일부 서서하는 작업 가능',
        'LONG_STANDING': '오랫동안 가능',
        'DIFFICULTY_STAND': '서거나 걷는 일 어려움'
    }.get(user_condition, user_condition)

    return conditions_order.get(user_condition_translated, 0) <= conditions_order.get(company_condition, 0)

# 사용자의 직종명이 회사의 직종명과 관련이 있는지 확인하는 함수
def is_related_job(user_job, company_job, job_categories):
    for category, jobs in job_categories.items():
        if user_job in jobs:
            return any(job in company_job for job in jobs)
    return False

# 자격증이 회사 요구 자격증과 매칭되는지 확인하는 함수
def is_licenses_satisfied(user_licenses, company_licenses):
    return all(license in company_licenses for license in user_licenses)

# 사용자 학력이 회사 요구 학력을 충족하는지 확인하는 함수
def is_education_satisfied(user_education, company_education):
    education_order = {
        '무관': 0,
        '초졸': 1,
        '중졸': 2,
        '고졸': 3,
        '대졸': 4
    }

    return education_order.get(user_education, 0) >= education_order.get(company_education, 0)

# 사용자 경력이 회사 요구 경력을 충족하는지 확인하는 함수
def is_career_satisfied(user_career, company_career):
    if company_career == '무관':
        return True

    # 사용자 경력 파싱
    user_years, user_months = 0, 0
    if '년' in user_career:
        try:
            user_years = int(user_career.split('년')[0].strip())
            if '개월' in user_career:
                user_months = int(user_career.split('년')[1].strip().split('개월')[0].strip())
        except ValueError:
            user_years, user_months = 0, 0
    elif '개월' in user_career:
        try:
            user_months = int(user_career.split('개월')[0].strip())
        except ValueError:
            user_months = 0

    # 회사 경력 파싱
    company_years, company_months = 0, 0
    if '년' in company_career:
        try:
            company_years = int(company_career.split('년')[0].strip())
            if '개월' in company_career:
                company_months = int(company_career.split('년')[1].strip().split('개월')[0].strip())
        except ValueError:
            company_years, company_months = 0, 0
    elif '개월' in company_career:
        try:
            company_months = int(company_career.split('개월')[0].strip())
        except ValueError:
            company_months = 0

    user_total_months = user_years * 12 + user_months
    company_total_months = company_years * 12 + company_months

    return user_total_months >= company_total_months


def main():
    if len(sys.argv) != 4:
        # 오류 메시지를 JSON 형식으로 출력
        error_message = [{
            "status": "error",
            "message": "Usage: python recommend_jobs.py <user_info_json_path> <companies_json_path> <job_categories_json_path>"
        }]
        sys.stdout.reconfigure(encoding='utf-8')
        print(json.dumps(error_message, ensure_ascii=False, indent=4))
        sys.exit(1)

    user_info_path = sys.argv[1]
    companies_json_path = sys.argv[2]
    job_categories_path = sys.argv[3]

    try:
        user_info = load_json(user_info_path)
        companies = load_json(companies_json_path)
        job_categories = load_json(job_categories_path)

        recommended_jobs = filter_jobs(user_info, companies, job_categories)

        # 결과를 JSON 형식으로 출력
        sys.stdout.reconfigure(encoding='utf-8')
        print(json.dumps(recommended_jobs, ensure_ascii=False, indent=4))

    except Exception as e:
        # 예외 발생 시 오류 메시지를 JSON 형식으로 출력
        error_message = [{
            "status": "error",
            "message": str(e)
        }]
        sys.stdout.reconfigure(encoding='utf-8')
        print(json.dumps(error_message, ensure_ascii=False, indent=4))
        sys.exit(1)

if __name__ == "__main__":
    main()
