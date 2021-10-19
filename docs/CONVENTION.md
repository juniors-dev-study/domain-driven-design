# CONVENTION

## 커밋 로그

`[#이슈번호] 수정 내용` 의 형태로 커밋을 작성한다

## IDE 에서 자동으로 convention 을 맞추도록 설정

### 2021 이상의 버전인 경우

![image](https://user-images.githubusercontent.com/10507662/137309837-bb0d5d86-ba4a-413b-8365-a20f8a94b663.png)

`Tools > Actions on Save` 에서 상위 이미지와 같이 설정

### 하위 버전인 경우

![image](https://user-images.githubusercontent.com/10507662/137310119-039d3d45-e9e0-4ee8-9284-0d094c1ac034.png)

`Save Actions` 설치하여 상위 이미지와 같이 설정

## 모듈 및 패키지 구성

- Bounded Context 는 하나의 모듈로 분리한다
- Aggregate 는 하나의 패키지를 각각 갖는다
- Bounded Context 간의 통신은 API 등으로 진행한다
- DDD 의 기본적인 패키지 구조는 사내 툴을 따른다
  - 하나의 Aggregate 내에 domains, dtos, listeners, repositories

## 기본적인 코딩 컨벤션

kotlin 공식 룰을 따름 - https://kotlinlang.org/docs/coding-conventions.html

## 구성

- Event 는 Spring 에서 제공하는 것으로 사용한다 (`ApplicationEventPublisher`)

## Authentication

user 모듈에서 로그인하고 정보를 쿠키에 담고 그 외 모듈에서 쿠키에 있는 데이터를 user 에 요청하여 사용자 정보를 얻는다
