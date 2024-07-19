import sys
import json
from sklearn.cluster import KMeans
from transformers import BertModel, BertTokenizer
import torch
import numpy as np

# BERT 모델과 토크나이저 로드
model_name = 'bert-base-multilingual-cased'
tokenizer = BertTokenizer.from_pretrained(model_name)
model = BertModel.from_pretrained(model_name)

# 직종명을 임베딩으로 변환하는 함수
def get_embedding(text):
    inputs = tokenizer(text, return_tensors='pt', truncation=True, padding=True, max_length=32)
    outputs = model(**inputs)
    return outputs.last_hidden_state.mean(dim=1).detach().numpy()

# 직종명을 클러스터링하여 관련 직종 카테고리를 생성하는 함수
def create_job_categories(job_titles, n_clusters=5):
    embeddings = np.vstack([get_embedding(job) for job in job_titles])
    kmeans = KMeans(n_clusters=n_clusters, random_state=0).fit(embeddings)

    clusters = {i: [] for i in range(n_clusters)}
    for job, label in zip(job_titles, kmeans.labels_):
        clusters[label].append(job)

    job_categories = {}
    for cluster_id, jobs in clusters.items():
        for job in jobs:
            job_categories[job] = jobs
    return job_categories

# JSON 파일 로드 함수
def load_json(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        return json.load(file)

# 직종명을 기반으로 일자리를 필터링하는 함수
def filter_jobs(user_info, companies, job_categories):
    recommended_jobs = []

    for company in companies:
        company_data = company['data']

        # 지역 필터링
        if user_info.get('location') and user_info['location'] not in company_data['compAddr']:
            continue

        # 직종 필터링
        if user_info.get('jobNm') and not is_related_job(user_info['jobNm'], company_data.get('jobNm', ''), job_categories):
            continue

        # 작업 환경 필터링
        if user_info.get('bothHands') and not is_condition_satisfied(user_info['bothHands'], company_data.get('envBothHands', '')):
            continue
        if user_info.get('eyesight') and not is_condition_satisfied(user_info['eyesight'], company_data.get('envEyesight', '')):
            continue
        if user_info.get('handwork') and not is_condition_satisfied(user_info['handwork'], company_data.get('envHandwork', '')):
            continue
        if user_info.get('liftPower') and not is_condition_satisfied(user_info['liftPower'], company_data.get('envLiftPower', '')):
            continue
        if user_info.get('lstnTalk') and not is_condition_satisfied(user_info['lstnTalk'], company_data.get('envLstnTalk', '')):
            continue
        if user_info.get('stndWalk') and not is_condition_satisfied(user_info['stndWalk'], company_data.get('envStndWalk', '')):
            continue

        # 자격증 필터링
        if user_info.get('licenses') and not is_licenses_satisfied(user_info['licenses'], company_data.get('licenses', [])):
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
        'DIFFICULTY': '서거나 걷는 일 어려움'
    }.get(user_condition, user_condition)

    return conditions_order.get(user_condition_translated, 0) <= conditions_order.get(company_condition, 0)


# 사용자의 직종명이 회사의 직종명과 관련이 있는지 확인하는 함수
def is_related_job(user_job, company_job, job_categories):
    related_job_list = job_categories.get(user_job, [user_job])
    return any(related_job in company_job for related_job in related_job_list)

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

    user_years, user_months = map(int, user_career.split('년')[0].strip()), int(user_career.split('년')[1].strip().split('개월')[0].strip())
    company_years, company_months = map(int, company_career.split('년')[0].strip()), int(company_career.split('년')[1].strip().split('개월')[0].strip())

    return (user_years > company_years) or (user_years == company_years and user_months >= company_months)

def main():
    if len(sys.argv) != 4:
        # 오류 메시지를 JSON 형식으로 출력
        error_message = [{
            "status": "error",
            "message": "Usage: python recommend_jobs.py <user_info_json_path> <companies_json_path> <job_titles_json_path>"
        }]
        print(json.dumps(error_message, ensure_ascii=False, indent=4))
        sys.exit(1)

    user_info_path = sys.argv[1]
    companies_json_path = sys.argv[2]
    job_titles_path = sys.argv[3]

    try:
        user_info = load_json(user_info_path)
        companies = load_json(companies_json_path)
        job_titles = load_json(job_titles_path)

        job_categories = create_job_categories(job_titles)
        recommended_jobs = filter_jobs(user_info, companies, job_categories)

        # 결과를 JSON 형식으로 출력
        print(json.dumps(recommended_jobs, ensure_ascii=False, indent=4))

    except Exception as e:
        # 예외 발생 시 오류 메시지를 JSON 형식으로 출력
        error_message = {
            "status": "error",
            "message": str(e)
        }
        print(json.dumps(error_message, ensure_ascii=False, indent=4))
        sys.exit(1)

if __name__ == "__main__":
    main()
