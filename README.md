# spring-board
Spring 으로 구현한 게시판

<br>

## 사용한 기술
- Spring boot 2.4.6
- Gradle 6.9
- Java 11
- MySQL 8.0.15
- Thymeleaf
- JPA 
- Lombok

<br>

## 구현한 기능
- 기본 게시판 CRUD
- 검색
- 페이징(진행중)
- 댓글(미구현)
- 회원가입(미구현)
- 로그인(미구현)


<br>
<br>

## HTTP API 
### 게시판
- GET /boards → 게시글 목록 + 추가로 필터링 구현
- GET /boards/write → 게시글 쓰기 폼
- POST /boards/write → 게시글 쓰기
- GET /boards/{id} → 게시글 조회
- GET /boards/{id}/edit → 게시글 수정 폼
- POST /boards/{id}/edit → 게시글 수정
- GET /boards/{id}/delete → 게시글 삭제

### 게시판 - 댓글
- GET /boards/{id}/comments → 게시글의 댓글 목록 API
- POST /boards/{id}/comments → 댓글 작성 API

### 회원가입 & 정보
- GET /members/signUp → 회원가입 폼
- GET /members/check-duplicate/id/{id} → ID 중복체크 API
- POST /members/signUp → 회원가입
- GET /members/check-duplicate/nickname/{nickname} → 닉네임 중복체크 API

### 로그인 
- GET /member/login → 로그인 폼
- POST /member/login → 로그인 로직
