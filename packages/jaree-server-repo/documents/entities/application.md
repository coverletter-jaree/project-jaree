---
tags:
  - backend
  - entity
  - type
---

# Application 엔티티

> 지원서(자소서) 엔티티 문서

## 개요

`Application` 엔티티는 사용자가 특정 채용공고에 대해 작성한 지원서(자소서)를 나타내는 Neo4j 노드입니다. 지원서의 상태, 질문과 답변, 버전 관리 정보를 포함합니다.

## 위치

`src/main/java/org/jaree/api/application/entity/Application.java`

## 엔티티 구조

```java
@Node("Application")
public class Application {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    private String position;
    
    private ApplicationStatus status;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    private LocalDateTime dueAt;
    
    @Relationship(type = "HAS_QUESTION", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationQuestion> questions;
    
    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private User user;
    
    @Relationship(type = "APPLIES_FOR", direction = Relationship.Direction.OUTGOING)
    private JobOpening jobOpening;
    
    @Relationship(type = "HAS_VERSION", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationVersion> versions;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 지원서 고유 식별자 | 자동 생성 |
| `title` | `String` | 자소서 제목 | - |
| `position` | `String` | 지원 직무 | - |
| `status` | `ApplicationStatus` | 자소서 상태 (작성 중, 합격, 제출 완료 등) | - |
| `createdAt` | `LocalDateTime` | 자소서 생성일자 | 자동 생성 |
| `updatedAt` | `LocalDateTime` | 자소서 마지막 수정일자 | 자동 업데이트 |
| `dueAt` | `LocalDateTime` | 자소서 마감일자 | - |
| `questions` | `List<ApplicationQuestion>` | 자소서 질문 항목 리스트 | 관계: HAS_QUESTION (OUTGOING) |
| `user` | `User` | 자소서 작성자 | 관계: WRITTEN_BY (OUTGOING) |
| `jobOpening` | `JobOpening` | 자소서 관련 채용 공고 | 관계: APPLIES_FOR (OUTGOING) |
| `versions` | `List<ApplicationVersion>` | 자소서 커밋 리스트 (버전 관리) | 관계: HAS_VERSION (OUTGOING) |

## 관계

### HAS_QUESTION (OUTGOING)
- **방향**: OUTGOING (Application에서 ApplicationQuestion으로 향함)
- **대상 엔티티**: `ApplicationQuestion` - 지원서가 포함하는 질문들

### WRITTEN_BY (OUTGOING)
- **방향**: OUTGOING (Application에서 User로 향함)
- **대상 엔티티**: `User` - 지원서를 작성한 사용자

### APPLIES_FOR (OUTGOING)
- **방향**: OUTGOING (Application에서 JobOpening으로 향함)
- **대상 엔티티**: `JobOpening` - 지원서가 지원하는 채용공고

### HAS_VERSION (OUTGOING)
- **방향**: OUTGOING (Application에서 ApplicationVersion으로 향함)
- **대상 엔티티**: `ApplicationVersion` - 지원서의 버전 히스토리

## ApplicationStatus 열거형

지원서 상태를 나타내는 열거형입니다.

위치: `src/main/java/org/jaree/api/application/enums/ApplicationStatus.java`

예시 상태:
- `DRAFT`: 작성 중
- `SUBMITTED`: 제출 완료
- `PASSED`: 합격
- `FAILED`: 불합격
- 기타

## 관련 엔티티

### ApplicationQuestion
지원서의 질문 항목을 나타내는 엔티티입니다.

위치: `src/main/java/org/jaree/api/application/entity/ApplicationQuestion.java`

### ApplicationAnswer
질문에 대한 답변을 나타내는 엔티티입니다.

위치: `src/main/java/org/jaree/api/application/entity/ApplicationAnswer.java`

### ApplicationVersion
지원서의 버전 정보를 나타내는 엔티티입니다. 버전 관리 및 히스토리 추적에 사용됩니다.

위치: `src/main/java/org/jaree/api/application/entity/ApplicationVersion.java`

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 지원서 생성
CREATE (a:Application {
    title: "백엔드 개발자 지원서",
    position: "백엔드 개발자",
    status: "DRAFT",
    createdAt: datetime(),
    updatedAt: datetime(),
    dueAt: datetime("2024-12-31T23:59:59")
})
RETURN a

// 사용자가 작성한 모든 지원서 조회
MATCH (u:User)-[:WRITTEN_BY]->(a:Application)
WHERE u.id = 1
RETURN u, a

// 특정 채용공고에 지원한 모든 지원서 조회
MATCH (j:JobOpening)<-[:APPLIES_FOR]-(a:Application)
WHERE j.id = 1
RETURN j, a

// 지원서의 질문과 답변 조회
MATCH (a:Application)-[:HAS_QUESTION]->(q:ApplicationQuestion)
WHERE a.id = 1
RETURN a, q

// 지원서의 버전 히스토리 조회
MATCH (a:Application)-[:HAS_VERSION]->(v:ApplicationVersion)
WHERE a.id = 1
RETURN a, v
ORDER BY v.createdAt DESC
```

## 비즈니스 규칙

1. 한 지원서는 하나의 사용자에 속합니다.
2. 한 지원서는 하나의 채용공고에 지원합니다.
3. 한 지원서는 여러 개의 질문을 가질 수 있습니다.
4. 지원서는 버전 관리가 가능하며, 여러 버전을 가질 수 있습니다.
5. `status` 필드로 지원서의 진행 상태를 추적합니다.
6. `dueAt` 필드로 마감일을 관리합니다.

## 관련 엔티티

- [[user]] - User 엔티티
- [[job-opening]] - JobOpening 엔티티
- [[application-question]] - ApplicationQuestion 엔티티
- [[application-version]] - ApplicationVersion 엔티티

## 태그
#backend #entity #type

