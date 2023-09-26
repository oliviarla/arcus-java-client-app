# Arcus Java Client 예제

## Notification 예제

- 인스타그램과 같은 소셜 미디어의 알람을 담는 예제입니다.
- 캐시의 리스트 자료구조를 활용해 최근 알람 10개만을 저장합니다.
- `POST localhost:8083/notis/set` 로 초기 데이터셋을 구성합니다.
- `POST localhost:8083/notis/update` 로 알람을 추가할 수 있습니다. +) 초기 데이터셋이 구성되지 않았다면 알람을 추가할 수 없습니다. 
  - Request Body
    ```json
    {
    "user": "username",
    "activity": "liked your comment."
    }
    ```
- `GET localhost:8083/notis/all` 로 알람 리스트를 확인할 수 있습니다.
