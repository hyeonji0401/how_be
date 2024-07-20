import requests
import json
import xmltodict

# API 호출을 위한 정보 설정
base_url = "http://apis.data.go.kr/B552583/job/job_list_env"
service_key = "W8au71fF3t4zbcYex0DaM8rC31gJulZ7ytCE0j6XkyPzc9q1VkJZ6C6i3hEocGSYyOBoKxHrvmqEQ9l13vtJvw=="  # 발급받은 서비스 키를 여기에 입력하세요

all_data = []
page_no = 1
num_of_rows = 100

json_file_path = "src/main/resources/jsonFile/apiJSON.json"

while True:
    # API 요청 URL 구성
    request_url = f"{base_url}?serviceKey={service_key}&pageNo={page_no}&numOfRows={num_of_rows}"

    # API 요청 및 응답 확인
    response = requests.get(request_url)

    if response.status_code == 200:
        try:
            # 응답 내용 출력 (디버깅용)
            print(f"Response text for page {page_no}: {response.text[:200]}...")  # 첫 200자만 출력

            # XML 응답을 JSON으로 변환
            data = xmltodict.parse(response.text)

            # 데이터가 없으면 반복 종료
            if not data.get('response', {}).get('body', {}).get('items', {}).get('item'):
                print("No items found in response")
                break

            # 수집한 데이터 추가
            items = data['response']['body']['items']['item']
            if isinstance(items, list):
                all_data.extend(items)
            else:
                all_data.append(items)

            # 다음 페이지로 이동
            page_no += 1
        except Exception as e:
            print(f"Error: {e}")
            print(f"Response text: {response.text}")
            break
    else:
        print(f"Failed to fetch data. Status code: {response.status_code}")
        print(f"Response text: {response.text}")
        break

    # 수집한 모든 데이터를 JSON 파일로 저장
if all_data:
    with open(json_file_path, "w", encoding='utf-8') as json_file:
        json.dump(all_data, json_file, ensure_ascii=False, indent=4)
    print(f"20 pages of data have been saved to {json_file_path}")
else:
    print("No data was fetched.")