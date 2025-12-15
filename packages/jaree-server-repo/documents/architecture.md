---
tags:
  - backend
  - architecture
---

# jaree-server-repo 아키텍처

> 백엔드 API 서버의 아키텍처 설계 문서

## 개요

jaree-server-repo는 Spring Boot 기반의 RESTful API 서버로 설계되었습니다. 도메인 주도 설계(DDD) 원칙을 따르며, Neo4j 그래프 데이터베이스를 사용하여 복잡한 관계를 효율적으로 관리합니다.

## 아키텍처 다이어그램

```
┌─────────────────────────────────────────┐
│         Client Applications            │
│  (jaree-client-repo, Mobile Apps, etc) │
└──────────────┬──────────────────────────┘
               │ HTTP/HTTPS
               ▼
┌─────────────────────────────────────────┐
│      Spring Boot Application            │
│                                         │
│  ┌──────────────────────────────────┐  │
│  │      Spring Security Layer       │  │
│  │    (Authentication/Authorization)│  │
│  └──────────────────────────────────┘  │
│                                         │
│  ┌──────────────────────────────────┐  │
│  │      REST API Controllers        │  │
│  └──────────────────────────────────┘  │
│                                         │
│  ┌──────────────────────────────────┐  │
│  │      Service Layer               │  │
│  └──────────────────────────────────┘  │
│                                         │
│  ┌──────────────────────────────────┐  │
│  │      Repository Layer            │  │
│  └──────────────────────────────────┘  │
└──────────────┬──────────────────────────┘
               │
       ┌───────┴───────┐
       │               │
       ▼               ▼
┌─────────────┐  ┌─────────────┐
│   Neo4j     │  │   MongoDB   │
│  (Graph DB) │  │  (Document) │
└─────────────┘  └─────────────┘
```

## 주요 컴포넌트

### 1. 도메인 모델

애플리케이션은 다음 도메인으로 구성됩니다:

#### User 도메인
- 사용자 정보 관리
- Neo4j 노드로 저장

#### Company 도메인
- 회사 정보 관리
- 회사 카테고리 분류

#### JobOpening 도메인
- 채용공고 관리
- 회사와의 관계 (CREATED_BY)

#### Application 도메인
- 지원서(자소서) 관리
- 사용자, 채용공고, 질문과의 관계

#### Resume 도메인
- 이력서 관리
- 사용자와의 관계 (WRITTEN_BY)

### 2. 데이터베이스 전략

#### Neo4j (그래프 데이터베이스)
- 엔티티 간 관계 중심 데이터 모델링
- 복잡한 관계 쿼리 최적화
- 사용자, 회사, 채용공고, 지원서, 이력서 간 관계 표현

주요 관계:
- `WRITTEN_BY`: 사용자가 작성한 자소서/이력서
- `CREATED_BY`: 회사가 생성한 채용공고
- `APPLIES_FOR`: 지원서가 지원하는 채용공고
- `HAS_QUESTION`: 지원서가 가진 질문들
- `HAS_VERSION`: 지원서의 버전 관리

#### MongoDB (문서 데이터베이스)
- 향후 확장을 위한 설정
- 대용량 문서 데이터 저장용

### 3. 보안 계층

- Spring Security를 통한 인증/인가
- OAuth2 클라이언트 지원
- 현재는 기본 사용자 인증 사용 (개발 단계)

## 데이터 흐름

### 요청 처리 흐름

1. **클라이언트 요청**: HTTP 요청 수신
2. **보안 검증**: Spring Security를 통한 인증/인가
3. **컨트롤러**: REST API 엔드포인트 매핑
4. **서비스**: 비즈니스 로직 처리
5. **리포지토리**: 데이터베이스 접근
6. **응답**: JSON 형식으로 응답 반환

### 관계 쿼리 예시

Neo4j를 사용한 관계 쿼리:
- 사용자가 작성한 모든 지원서 조회
- 특정 채용공고에 지원한 모든 지원서 조회
- 회사가 생성한 모든 채용공고 조회

## 기술 결정사항

### Spring Boot 3.5.4 선택 이유
- **최신 기능**: 최신 Spring 기능 활용
- **성능**: 향상된 성능과 메모리 효율성
- **Java 21 지원**: 최신 Java 기능 활용

### Neo4j 선택 이유
- **관계 중심**: 복잡한 관계 데이터 모델링에 적합
- **성능**: 관계 쿼리 최적화
- **유연성**: 동적인 관계 구조 지원

### MongoDB 선택 이유
- **확장성**: 향후 대용량 문서 데이터 저장 대비
- **유연성**: 스키마 없는 문서 저장
- **성능**: 빠른 읽기/쓰기 성능

### 이중 데이터베이스 전략
- Neo4j: 관계 중심 데이터 (사용자, 회사, 채용공고, 지원서)
- MongoDB: 문서 중심 데이터 (향후 확장용)

## 패키지 구조

```
org.jaree.api/
├── ApiApplication.java          # 메인 애플리케이션 클래스
├── user/                        # 사용자 도메인
│   └── entity/
│       └── User.java
├── company/                     # 회사 도메인
│   ├── entity/
│   │   └── Company.java
│   └── enums/
│       └── CompanyCategory.java
├── jobopening/                  # 채용공고 도메인
│   └── entity/
│       └── JobOpening.java
├── application/                 # 지원서 도메인
│   ├── entity/
│   │   ├── Application.java
│   │   ├── ApplicationAnswer.java
│   │   ├── ApplicationQuestion.java
│   │   └── ApplicationVersion.java
│   └── enums/
│       └── ApplicationStatus.java
└── resume/                      # 이력서 도메인
    ├── entity/
    │   ├── Resume.java
    │   ├── ResumeRelationship.java
    │   └── JsonData.java
    └── enums/
        └── ResumeRelationshipType.java
```

## API 설계 원칙

- RESTful API 설계 원칙 준수
- 리소스 중심 URL 구조
- HTTP 메서드 적절한 사용 (GET, POST, PUT, DELETE)
- JSON 형식의 요청/응답

## 에러 처리

- 표준 HTTP 상태 코드 사용
- 일관된 에러 응답 형식 (향후 구현)

## 모니터링 및 관리

- Spring Boot Actuator를 통한 헬스 체크
- 애플리케이션 메트릭 수집 (향후 확장)

## 향후 개선 사항

- [ ] REST API 컨트롤러 구현
- [ ] 서비스 레이어 구현
- [ ] 리포지토리 레이어 구현
- [ ] DTO 클래스 추가
- [ ] 예외 처리 메커니즘 구현
- [ ] API 문서화 (Swagger/OpenAPI)
- [ ] 단위 테스트 및 통합 테스트 작성
- [ ] 로깅 전략 수립
- [ ] 캐싱 전략 수립

## 참고 자료
- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Neo4j 공식 문서](https://neo4j.com/docs/)

## 태그
#backend #architecture

