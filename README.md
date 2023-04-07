# GP Blind
## 통합 배포는 4월 12일입니다. (그 전까지는 테스트 기간입니다.)

### 서비스 및 프로젝트 소개
- 익명성 보장을 기반으로 글을 통해 자신의 목소리를 낼수 있는 GP 소셜 네트워크 서비스입니다.

### 서비스 기능
- 익명성 게시글을 통해 직접 하지 못하는 얘기를 전달

### 버전
<img src="https://img.shields.io/badge/v 1.0.0-통합 배포(4/7)-red"> 

<img src="https://img.shields.io/badge/v 0.5.1-EC2 생성-green"> <img src="https://img.shields.io/badge/v 0.5.2-Backend Jenkins CI/CD-green"> 
<img src="https://img.shields.io/badge/v 0.5.2-Frontend Github Action CI/CD-green"> <img src="https://img.shields.io/badge/v 0.5.3-AWS DNS-green"> 
<img src="https://img.shields.io/badge/v 0.5.4-AWS ACM, ELB-green"> <img src="https://img.shields.io/badge/v 0.5.6-Nginx-green"> 
<img src="https://img.shields.io/badge/v 0.5.5-Backend Integration Test-green"> <img src="https://img.shields.io/badge/v 0.5.5-Frontend Integration Test-green">

<img src="https://img.shields.io/badge/v 0.4.1-CORS 처리-green"> <img src="https://img.shields.io/badge/v 0.4.2-API Response틀 수정-green"> 
<img src="https://img.shields.io/badge/v 0.4.2-여러개의 DTO 생성 및 커스텀-green"> <img src="https://img.shields.io/badge/v 0.4.3-댓글 생성-green"> 
<img src="https://img.shields.io/badge/v 0.4.4-댓글 수정, 삭제-green"> <img src="https://img.shields.io/badge/v 0.4.6-대댓글 생성-green"> 
<img src="https://img.shields.io/badge/v 0.4.5-QueryDSL 설정-green">

<img src="https://img.shields.io/badge/v 0.3.1-Post, Comment, Image ERD 구축-green"> <img src="https://img.shields.io/badge/v 0.3.2-게시글 생성-green"> 
<img src="https://img.shields.io/badge/v 0.3.2-S3 Bucket 생성 및 이미지 업로드 구현-green"> <img src="https://img.shields.io/badge/v 0.3.3-게시글 상세 수정, 삭제, 조회-green"> 
<img src="https://img.shields.io/badge/v 0.3.4-게시글 좋아요-green"> <img src="https://img.shields.io/badge/v 0.3.6-모든 게시글 조회시 페이징-green"> 
<img src="https://img.shields.io/badge/v 0.3.5-내가 쓴 게시글 확인-green"> <img src="https://img.shields.io/badge/v 0.3.8-상세 조회시 조회수 중복 제거-green">


<img src="https://img.shields.io/badge/v 0.2.1-User ERD 구축-green"> <img src="https://img.shields.io/badge/v 0.2.2-회원가입 (JWT)-green"> 
<img src="https://img.shields.io/badge/v 0.2.3-패키지 구조 변경(도메인)-green"> <img src="https://img.shields.io/badge/v 0.2.3-시프링 시큐리티 설정-green"> 
<img src="https://img.shields.io/badge/v 0.2.5-이메일 인증-green"> <img src="https://img.shields.io/badge/v 0.2.6-회원 변경-green"> 
<img src="https://img.shields.io/badge/v 0.2.7-회원 탈퇴-green"> 

<img src="https://img.shields.io/badge/v 0.1.1-프로젝트 시작-green"> <img src="https://img.shields.io/badge/v 0.1.2-사용 기술 스택 -green"> 
<img src="https://img.shields.io/badge/v 0.1.3-ERD 설계-green"> <img src="https://img.shields.io/badge/v 0.1.4-Architecture 설계-green"> 
<img src="https://img.shields.io/badge/v 0.1.5-개발 시작-green"> 



### 프로젝트 목표
- 프론트엔드와 백엔드의 연동 및 협의 중시
- Docker를 활용한 배포 및 관리의 단순화
- CI/CD를 적용하고 자동화된 빌드와 배포를 통해 개발의 생산성을 높히기
- NGINX를 활용한 Reverse Proxy Server
- 지속적인 확장이 가능한 설계
- 어떻게 코드를 작성하는 것이 이해하기 쉬운 코드(클린코드)이며 유지보수에 용이한가

### 사용 기술 스택
- Java
- Spring Boot
- Spring Data JPA
- MariaDB
- Redis 
- Next.js
- Nginx
- AWS(EC2, S3 bucket, Route 53, ACM) 
- Jenkins(CICD) -> backend
- Github Action(CICD) -> frontend
- Docker

### 아키텍처
<img width="791" alt="image" src="https://user-images.githubusercontent.com/65766105/228772835-98754542-043f-4c01-b044-ddf6d99be3ad.png">

### ERD
<img width="791" alt="image" src="https://user-images.githubusercontent.com/65766105/202895088-45a6350c-4c46-4ff7-9762-4e406177915f.png">

### 개발 회고
#### AWS EC2
- https://devty.tistory.com/1

#### Docker AWS EC2 deploy
- https://devty.tistory.com/2

#### Jenkins CI/CD with Github
- https://devty.tistory.com/3

#### SpringBoot & Docker & DockerHub & EC2 & GitHub & JenkinsCI/CD
- https://devty.tistory.com/4

#### AWS EC2 mariadb download
- https://devty.tistory.com/5

#### AWS Route 53 DNS and EC2 linkage
- https://devty.tistory.com/6

#### AWS CM, ELB, Nginx use HTTPS server building
- https://devty.tistory.com/7

#### Github Action CI/CD
- https://devty.tistory.com/8

#### AWS S3 Bucket
- https://devty.tistory.com/9

#### Nextjs Springboot Nginx Docker-compose
- https://devty.tistory.com/10
