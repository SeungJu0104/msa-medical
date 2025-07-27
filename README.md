# 슬기로운 EMR

소규모 병원에서 사용하는 <b>통합 예약 / 진료 관리</b> 시스템

## 프로젝트 소개

- <b>예약부터 진료, 문서 발급, 결제</b>까지 One-Stop으로 가능한 소규모 병원용 통합 EMR 시스템 개발
- 의사 2명, 간호사 2명의 소규모 인원을 상정
- 의료진 간에 채팅도 가능

## 개발 목적

- JavaScript와 Vue.js를 사용한 SPA 클라이언트 개발
- Java와 Spring Boot를 사용한 REST API 방식의 서버 개발
- 웹소켓을 이용한 실시간 메세지 전달

## 기능 소개

### 사용자 인증

- 아이디와 비밀번호를 사용해서 로그인합니다.
- 액세스 토큰과 리프레시 토큰을 사용해서 사용자 인증을 합니다.
- 리프레시 토큰은 화이트리스트 방식과 회전 방식을 사용해서 Redis에 저장됩니다.

#### 백엔드 구조
```tree/bash/shell
📦auth
 ┣ 📂config                       // Spring Security 등 인증 관련 설정
 ┣ 📂controller
 ┣ 📂dao                          // MyBatis 매퍼
 ┣ 📂domain
 ┣ 📂dto
 ┣ 📂filter                       // JWT 인증 필터
 ┣ 📂service
 ┃ ┣ 📜AccessTokenService.java
 ┃ ┣ 📜AuthService.java
 ┃ ┣ 📜CredentialsService.java
 ┃ ┗ 📜RefreshTokenService.java
 ┗ 📂util
   ┗ 📜JwtUtil.java
```

#### Axios 요청, 응답 인터셉터
- 프론트엔드에서는 Axios 인터셉터를 사용해서 인증과 재시도를 자동화했습니다.
- 재발급 도중에는 한 번의 재발급만 발생하도록 했습니다.
<img width="1200" height="596" alt="Image" src="https://github.com/user-attachments/assets/ec5be817-90ab-4759-bc32-f818996a201b" />

### 예약 및 대기
- 의사와 일자, 시간을 선택해서 예약을 등록할 수 있습니다.
- 예약, 대기 목록은 웹소켓을 사용해서 실시간으로 업데이트됩니다.

|예약 등록|예약열|대기열|
|:---:|:---:|:---:|
|![Image](https://github.com/user-attachments/assets/a967de56-e561-429c-af5e-7d45f050e35f)|![Image](https://github.com/user-attachments/assets/edd1a5ae-1026-403e-bcd6-852b15e96219)|![Image](https://github.com/user-attachments/assets/72521bbe-9963-419e-94a2-8c56f1bd3b09)|

### 진료
- 진료 시에는 이미지를 추가해서 내용을 보완할 수 있습니다.
- 진료 시에는 내원 이력이 생성됩니다.

|내원 이력|진료 작성|
|:---:|:---:|
|![Image](https://github.com/user-attachments/assets/77a3d587-75ae-4ec5-aed1-0292a85a3c9c)|![Image](https://github.com/user-attachments/assets/6f554bbf-9403-4b9b-a5f5-755d4a6214ee)|

### 채팅
- 의료진 간에 채팅이 가능합니다.
- 방 밖에서 채팅이 올 경우에 알림이 나타납니다.

|채팅방 목록|채팅 알림|채팅창|
|:---:|:---:|:---:|
|![Image](https://github.com/user-attachments/assets/badec411-3950-4927-bd6d-af7eca0dfce4)|![Image](https://github.com/user-attachments/assets/b2b721d0-1793-4a64-9d30-fafeb52c2da8)|![Image](https://github.com/user-attachments/assets/e4f4203b-a86b-4b02-b1e6-7cfc877958fb)|

## 학습 및 트러블 슈팅

- [전체 문서](https://www.notion.so/20861813692f814e985de7b5a3f58cda?v=20861813692f815b8222000c6818ecaa)
- (김찬희) [전역 예외 처리기](https://www.notion.so/21861813692f80f9a8e7c2dc1181ba37)
- (남승주) [Docker Compose 수정](https://www.notion.so/Docker-Compose-23661813692f80d4931afcaa21a429f1)
- (김수영) [STOMP 학습](https://www.notion.so/STOMP-21516ca889ab806795d9cc96b13f55b7)

## 팀원 소개

|[김찬희](https://github.com/Conut-1) | [남승주](https://github.com/SeungJu0104) | [김수영](https://github.com/suyungking) | [김영선](https://github.com/sunny950610) |
|:---------:|:---------:|:---------:|:---------:|
| ![](https://github.com/Conut-1.png) | ![](https://github.com/SeungJu0104.png) | ![](https://github.com/suyungking.png) | ![](https://github.com/sunny950610.png) |
| 사용자 인증, 회원 | 예약, 접수, 대기, DevOps | 채팅, 웹소켓, 진료 | 관리자 페이지 |

## 개발 기간

2025.06.16 - 2025.07.23 (약 5주)

## 기술 스택

### Frontend

- `JavaScript` `Vue.js` `Axios` `Bootstrap`

### Backend

- `Java` `Spring Boot` `Lombok` `MyBatis`

### Database

- `MariaDB` `Redis`

### Infra

- `AWS` `Docker` `Docker Compose` `nginx` `WebSocket`

### 그 외

- `JWT`

## 문서

[📒팀 프로젝트 관리 문서](https://www.notion.so/EMR-20861813692f8025a61ffd10267e8785)
