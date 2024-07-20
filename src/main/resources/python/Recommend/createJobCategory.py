import json

# 카테고리 키워드
category_keywords = {
    "사무 및 관리": ["사무", "총무", "기획", "지원", "통계", "운영자", "행정", "경영", "재무", "회계", "경리"],
    "청소 및 환경": ["청소", "환경미화", "세척", "폐기물", "세탁", "환경", "세차"],
    "고객서비스 및 상담": ["고객", "상담", "도우미", "서비스", "모니터","콜센터", "텔레마케터", "보험"],
    "홍보 및 마케팅":["홍보", "마케팅", "판촉", "광고"],
    "의료 및 복지": ["의료", "복지", "간호", "요양", "사회복지사", "환자", "돌봄", "병동", "병원"],
    "운송 및 배달": ["운전원", "배송", "배달", "물류", "택배", "우편", "운반", "운송", "하역", "적재", "이사", "이삿짐", "포장"],
    "제조 및 생산": ["제조", "생산", "기계", "포장", "조립", "검사", "조작", "부품"],
    "IT 및 디자인": ["디자이너", "데이터", "웹", "영상", "컴퓨터", "IT", "AI"],
    "요리 및 주방 보조": ["주방", "조리사", "음식", "급식", "바리스타", "식품", "제과", "제빵"],
    "판매": ["매장", "판매원", "진열원", "네일", "테마파크", "진열", "판매"],
    "경비 및 보안": ["경비", "CCTV", "경호"],
    "연구": ["연구", "실험"],
    "설치 및 수리": ["설치", "수리", "보수"],
    "단순노동": ["현장", "단순"],
    "동물":["동물", "사육", "동물병원"],
    "기타": []
}

def categorize_jobs(job_list, category_keywords):
    categorized_jobs = {key: [] for key in category_keywords}
    categorized_jobs["기타"] = []

    for job in job_list:
        added = False
        for category, keywords in category_keywords.items():
            for keyword in keywords:
                if keyword in job:
                    if job not in categorized_jobs[category]:  # 중복 제거
                        categorized_jobs[category].append(job)
                    added = True
        if not added:
            if job not in categorized_jobs["기타"]:  # 중복 제거
                categorized_jobs["기타"].append(job)

    return categorized_jobs

def main(job_titles_path, job_categories_path):
    try:
        # job_titles.json 파일 로드
        with open(job_titles_path, 'r', encoding='utf-8') as file:
            job_titles = json.load(file)

        # 직업 분류
        categorized_jobs = categorize_jobs(job_titles, category_keywords)

        # 결과를 JSON 형식으로 저장
        with open(job_categories_path, 'w', encoding='utf-8') as file:
            json.dump(categorized_jobs, file, ensure_ascii=False, indent=4)

        print(json.dumps({"status": "success", "message": "Job categories created successfully."}, ensure_ascii=False, indent=4))
    except Exception as e:
        print(json.dumps({"status": "error", "message": str(e)}, ensure_ascii=False, indent=4))

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print(json.dumps({"status": "error", "message": "Usage: python createJobCategory.py <job_titles_path> <job_categories_path>"}, ensure_ascii=False, indent=4))
        sys.exit(1)

    job_titles_path = sys.argv[1]
    job_categories_path = sys.argv[2]

    main(job_titles_path, job_categories_path)
