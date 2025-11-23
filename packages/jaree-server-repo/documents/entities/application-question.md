---
tags:
  - backend
  - entity
  - type
---

# ApplicationQuestion 엔티티

> 자소서 질문 항목 엔티티 문서

## 개요

`ApplicationQuestion` 엔티티는 자소서에 등록된 질문 항목을 나타내는 Neo4j 노드입니다. 채용공고, 지원서, 답변과의 관계를 관리합니다.

## 위치

`src/main/java/org/jaree/api/application/entity/ApplicationQuestion.java`

## 엔티티 구조

```java
@Node("ApplicationQuestion")
public class ApplicationQuestion {
    @Id
    @GeneratedValue
    private Long id;
    
    private String content;
    
    private String description;
    
    private int order;
    
    @Relationship(type = "ANSWERS_TO", direction = Relationship.Direction.INCOMING)
    private List<ApplicationAnswer> answers;
    
    @Relationship(type = "HAS_QUESTION", direction = Relationship.Direction.INCOMING)
    private List<Application> applications;
    
    @Relationship(type = "ASKS", direction = Relationship.Direction.INCOMING)
    private List<JobOpening> jobOpenings;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 질문 고유 식별자 | 자동 생성 |
| `content` | `String` | 자소서 질문 내용 | - |
| `description` | `String` | 질문에 대한 부가적인 설명 | - |
| `order` | `int` | 질문 순서 | - |
| `answers` | `List<ApplicationAnswer>` | 해당 질문을 답한 답변 리스트 | 관계: ANSWERS_TO (INCOMING) |
| `applications` | `List<Application>` | 해당 질문을 가지고 있는 자소서 리스트 | 관계: HAS_QUESTION (INCOMING) |
| `jobOpenings` | `List<JobOpening>` | 해당 질문을 가지고 있는 지원 공고 리스트 | 관계: ASKS (INCOMING) |

## 관계

### ANSWERS_TO (INCOMING)
- **방향**: INCOMING (ApplicationAnswer 노드에서 ApplicationQuestion으로 향함)
- **대상 엔티티**: `ApplicationAnswer` - 질문에 대한 답변들

### HAS_QUESTION (INCOMING)
- **방향**: INCOMING (Application 노드에서 ApplicationQuestion으로 향함)
- **대상 엔티티**: `Application` - 질문을 포함하는 지원서들

### ASKS (INCOMING)
- **방향**: INCOMING (JobOpening 노드에서 ApplicationQuestion으로 향함)
- **대상 엔티티**: `JobOpening` - 질문을 요구하는 채용공고들

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 질문 생성
CREATE (q:ApplicationQuestion {
    content: "지원 동기를 작성해주세요",
    description: "회사에 지원하게 된 이유를 구체적으로 작성해주세요",
    order: 1
})
RETURN q

// 채용공고의 질문 조회
MATCH (j:JobOpening)-[:ASKS]->(q:ApplicationQuestion)
WHERE j.id = 1
RETURN j, q
ORDER BY q.order

// 지원서의 질문 조회
MATCH (a:Application)-[:HAS_QUESTION]->(q:ApplicationQuestion)
WHERE a.id = 1
RETURN a, q
ORDER BY q.order

// 질문에 대한 답변 조회
MATCH (q:ApplicationQuestion)<-[:ANSWERS_TO]-(ans:ApplicationAnswer)
WHERE q.id = 1
RETURN q, ans
```

## 비즈니스 규칙

1. 한 질문은 여러 채용공고에서 사용될 수 있습니다.
2. 한 질문은 여러 지원서에 포함될 수 있습니다.
3. 한 질문은 여러 답변을 가질 수 있습니다.
4. `order` 필드로 질문의 순서를 관리합니다.
5. 질문은 채용공고에서 정의되거나 지원서에서 직접 추가될 수 있습니다.

## 관련 엔티티

- [[application]] - Application 엔티티
- [[application-answer]] - ApplicationAnswer 엔티티
- [[job-opening]] - JobOpening 엔티티

## 태그
#backend #entity #type

