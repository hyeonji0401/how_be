import json

# apiJSON.json 파일 로드
with open('apiJSON.json', 'r', encoding='utf-8') as file:
    company_data = json.load(file)

# jobNm 필드 추출 및 중복 제거
job_titles = set()
for company in company_data:
    job_nm = company.get('data', {}).get('jobNm')
    if job_nm:
        job_titles.add(job_nm)

# job_titles.json 파일 생성
job_titles_list = list(job_titles)
with open('job_titles.json', 'w', encoding='utf-8') as file:
    json.dump(job_titles_list, file, ensure_ascii=False, indent=4)

print(f"job_titles.json 파일이 생성되었습니다. 총 {len(job_titles_list)}개의 직종명이 포함되었습니다.")
