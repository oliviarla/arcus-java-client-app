# Arcus Java Client 예제

- 트위터의 트윗과 비슷하게 피드를 추가하고 조회할 수 있도록 하는 간단한 예제입니다.
- `ArcusAppCommon`과 `ArcusJavaClient`를 사용하며 두 방식의 차이를 쉽게 이해할 수 있도록 합니다.
  - `ArcusAppCommon`을 사용하는 서비스 클래스의 경우 조회 API에 캐싱을 적용해 기존에 캐싱이 되어있지 않는다면 모두 캐싱하도록 합니다.
  - `ArcusJavaClient`를 사용하는 서비스 클래스의 경우 리스트 자료 구조를 활용해 최근 피드 10개를 저장합니다.

### 사용법
- `@Primary` 어노테이션을 통해 기본적으로 JavaClientFeedService를 사용하도록 되어있습니다. `ArcusAppCommon` 서비스 클래스를 사용하려면 `@Primary` 어노테이션을 AppCommonFeedService로 옮겨줘야 합니다.

- `POST localhost:8083/feeds/add` 로 피드를 추가할 수 있습니다.
    - Request Body
      ```json
      {
      "userId": "1",
      "content": "졸립다 퇴근하고 싶다.."
      }
      ```
- `GET localhost:8083/feeds/all` 로 알람 리스트를 확인할 수 있습니다.
  - Response Body
    ```json
    [
        {
        "id": 2,
        "userId": 1,
        "content": "hello!",
        "createdAt": "2023-12-01T17:26:28.758712"
        },
        {
        "id": 1,
        "userId": 1,
        "content": "hello!",
        "createdAt": "2023-12-01T17:26:28.229125"
        }
      ]
    ```