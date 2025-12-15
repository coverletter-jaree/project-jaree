---
tags:
  - backend
  - entity
  - type
---

# JobOpening 엔티티

> 채용공고 엔티티 문서

## 개요

`JobOpening` 엔티티는 회사가 등록한 채용공고를 나타내는 Neo4j 노드입니다. 채용공고 정보, 회사와의 관계, 지원서와의 관계를 관리합니다.

## 위치

`src/main/java/org/jaree/api/jobopening/entity/JobOpening.java`

## 엔티티 구조

```java
@Node("JobOpening")
public class JobOpening {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    private String description;
    
    private String contentS3Url;
    
    private String imageUrl;
    
    private LocalDateTime startsAt;
    
    private LocalDateTime endsAt;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.OUTGOING)
    private Company company;
    
    @Relationship(type = "APPLIES_FOR", direction = Relationship.Direction.INCOMING)
    private List<Application> applications;
    
    @Relationship(type = "ASKS", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationQuestion> questions;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 채용공고 고유 식별자 | 자동 생성 |
| `title` | `String` | 공고 제목 | - |
| `description` | `String` | 공고 간단 설명 | - |
| `contentS3Url` | `String` | 공고 내용 (S3 저장소 URL, 이미지 포함 가능) | - |
| `imageUrl` | `String` | 공고 대표 이미지 링크 | - |
| `startsAt` | `LocalDateTime` | 지원 가능 시작일 | - |
| `endsAt` | `LocalDateTime` | 지원 마감일 | - |
| `createdAt` | `LocalDateTime` | 공고 생성일 | 자동 생성 |
| `company` | `Company` | 공고를 등록한 회사 | 관계: CREATED_BY (OUTGOING) |
| `applications` | `List<Application>` | 해당 공고에 지원한 지원서 리스트 | 관계: APPLIES_FOR (INCOMING) |
| `questions` | `List<ApplicationQuestion>` | 해당 공고가 갖는 자소서 질문 리스트 | 관계: ASKS (OUTGOING) |

## 관계

### CREATED_BY (OUTGOING)
- **방향**: OUTGOING (JobOpening에서 Company로 향함)
- **대상 엔티티**: `Company` - 공고를 등록한 회사

### APPLIES_FOR (INCOMING)
- **방향**: INCOMING (Application 노드에서 JobOpening으로 향함)
- **대상 엔티티**: `Application` - 해당 공고에 지원한 지원서들

### ASKS (OUTGOING)
- **방향**: OUTGOING (JobOpening에서 ApplicationQuestion으로 향함)
- **대상 엔티티**: `ApplicationQuestion` - 공고가 요구하는 자소서 질문들

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 채용공고 생성
CREATE (j:JobOpening {
    title: "백엔드 개발자 채용",
    description: "Spring Boot 경험자 우대",
    contentS3Url: "https://s3.example.com/content.pdf",
    imageUrl: "https://example.com/job-image.png",
    startsAt: datetime("2024-01-01T00:00:00"),
    endsAt: datetime("2024-12-31T23:59:59"),
    createdAt: datetime()
})
RETURN j

// 회사가 생성한 채용공고 조회
MATCH (c:Company)-[:CREATED_BY]->(j:JobOpening)
WHERE c.id = 1
RETURN c, j

// 특정 공고에 지원한 모든 지원서 조회
MATCH (j:JobOpening)<-[:APPLIES_FOR]-(a:Application)
WHERE j.id = 1
RETURN j, a

// 공고의 자소서 질문 조회
MATCH (j:JobOpening)-[:ASKS]->(q:ApplicationQuestion)
WHERE j.id = 1
RETURN j, q
```

## 비즈니스 규칙

1. 한 채용공고는 하나의 회사에 속합니다.
2. 한 채용공고는 여러 개의 지원서를 받을 수 있습니다.
3. 한 채용공고는 여러 개의 자소서 질문을 가질 수 있습니다.
4. 공고 내용은 S3에 저장되며, 이미지가 포함될 수 있습니다.
5. `startsAt`과 `endsAt`으로 지원 기간을 관리합니다.

## 관련 엔티티

- [[company]] - Company 엔티티
- [[application]] - Application 엔티티
- [[application-question]] - ApplicationQuestion 엔티티

## 태그
#backend #entity #type

