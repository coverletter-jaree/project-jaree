---
tags:
  - backend
---

# jaree-server-repo 패키지 개요

> Spring Boot 기반 백엔드 API 서버

## 개요

jaree-server-repo는 Jaree 프로젝트의 백엔드 API 서버 패키지입니다. Spring Boot 3.5.4를 사용하여 구축되었으며, MongoDB와 Neo4j를 데이터베이스로 사용합니다.

## 기술 스택

- **프레임워크**: Spring Boot 3.5.4
- **언어**: Java 21
- **빌드 도구**: Gradle (Kotlin DSL)
- **데이터베이스**: 
  - MongoDB (문서 데이터베이스)
  - Neo4j (그래프 데이터베이스)
- **보안**: Spring Security, OAuth2 Client
- **모니터링**: Spring Boot Actuator

## 주요 의존성

### 핵심 의존성
- `spring-boot-starter-web`: 웹 애플리케이션 지원
- `spring-boot-starter-data-mongodb`: MongoDB 통합
- `spring-boot-starter-data-neo4j`: Neo4j 통합
- `spring-boot-starter-security`: 보안 기능
- `spring-boot-starter-oauth2-client`: OAuth2 클라이언트 지원
- `spring-boot-starter-actuator`: 모니터링 및 관리

### 개발 도구
- `lombok`: 보일러플레이트 코드 제거
- `spring-boot-devtools`: 개발 도구

## 프로젝트 구조

```
jaree-server-repo/
├── src/
│   ├── main/
│   │   ├── java/org/jaree/api/
│   │   │   ├── ApiApplication.java
│   │   │   ├── application/     # 지원서(자소서) 도메인
│   │   │   ├── company/         # 회사 도메인
│   │   │   ├── jobopening/      # 채용공고 도메인
│   │   │   ├── resume/          # 이력서 도메인
│   │   │   └── user/            # 사용자 도메인
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── application-dev.yaml
│   │       └── application-local.yaml
│   └── test/
├── build.gradle.kts
└── docker/
    └── local.db.docker-compose.yaml
```

## 도메인 모델

### 주요 엔티티

1. **User**: 사용자 엔티티 (Neo4j)
2. **Company**: 회사 엔티티 (Neo4j)
3. **JobOpening**: 채용공고 엔티티 (Neo4j)
4. **Application**: 지원서(자소서) 엔티티 (Neo4j)
5. **Resume**: 이력서 엔티티 (Neo4j)

### 관계형 데이터베이스 구조

- Neo4j를 사용하여 엔티티 간 관계를 그래프로 표현
- MongoDB는 향후 확장을 위해 설정됨

## 스크립트

- `local`: 로컬 개발 서버 실행 (연속 빌드 + 실행)
- `build`: 프로젝트 빌드
- `test`: 테스트 실행

## 환경 설정

애플리케이션은 프로파일 기반으로 환경을 관리합니다:

- `local`: 로컬 개발 환경
- `dev`: 개발 환경
- `production`: 프로덕션 환경 (기본)

### 기본 설정 (application.yaml)

- 애플리케이션 이름: `api`
- 보안: 기본 사용자 인증 (임시)
- Actuator: 헬스 체크 엔드포인트 활성화

## 개발 서버 실행

```bash
npm run local
# 또는
./gradlew bootRun --args='--spring.profiles.active=local'
```

## 빌드

```bash
npm run build
# 또는
./gradlew build
```

## 데이터베이스

### Neo4j
- 그래프 데이터베이스로 엔티티 간 관계 관리
- 사용자, 회사, 채용공고, 지원서, 이력서 간의 관계 표현

### MongoDB
- 문서 데이터베이스 (향후 확장용)
- 현재는 설정만 되어 있음

## 보안

- Spring Security를 사용한 인증/인가
- OAuth2 클라이언트 지원
- 현재는 기본 사용자 인증 사용 (임시)

## 모니터링

Spring Boot Actuator를 통해 애플리케이션 상태 모니터링:

- `/actuator/health`: 헬스 체크 엔드포인트

## 관련 문서
- [[jaree-server-repo-index|index]] - 인덱스 문서

## 태그
#backend
