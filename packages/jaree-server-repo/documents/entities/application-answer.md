---
tags:
  - backend
  - entity
  - type
---

# ApplicationAnswer 엔티티

> 자소서 답변 엔티티 문서

## 개요

`ApplicationAnswer` 엔티티는 자소서 질문에 대한 답변을 나타내는 Neo4j 노드입니다. 답변 내용은 S3에 저장되며, 특정 자소서 버전에 속합니다.

## 위치

`src/main/java/org/jaree/api/application/entity/ApplicationAnswer.java`

## 엔티티 구조

```java
@Node("ApplicationAnswer")
public class ApplicationAnswer {
    @Id
    @GeneratedValue
    private Long id;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    private String s3Link;
    
    @Transient
    private String content;
    
    @Relationship(type = "ANSWERS", direction = Relationship.Direction.INCOMING)
    private ApplicationVersion applicationVersion;
    
    @Relationship(type = "ANSWERS_TO", direction = Relationship.Direction.OUTGOING)
    private ApplicationQuestion question;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 답변 고유 식별자 | 자동 생성 |
| `createdAt` | `LocalDateTime` | 답변 생성일자 | 자동 생성 |
| `s3Link` | `String` | 자소서 답변이 저장되어 있는 S3 링크 | - |
| `content` | `String` | 실제 답변 내용 (S3에서 가져온 데이터, Transient) | 데이터베이스에 저장되지 않음 |
| `applicationVersion` | `ApplicationVersion` | 해당 답변이 속해있는 자소서 버전 | 관계: ANSWERS (INCOMING) |
| `question` | `ApplicationQuestion` | 답변 대상 질문 | 관계: ANSWERS_TO (OUTGOING) |

## 관계

### ANSWERS (INCOMING)
- **방향**: INCOMING (ApplicationVersion 노드에서 ApplicationAnswer로 향함)
- **대상 엔티티**: `ApplicationVersion` - 답변이 속한 자소서 버전

### ANSWERS_TO (OUTGOING)
- **방향**: OUTGOING (ApplicationAnswer에서 ApplicationQuestion으로 향함)
- **대상 엔티티**: `ApplicationQuestion` - 답변 대상 질문

## Transient 필드

`content` 필드는 `@Transient` 어노테이션이 붙어 있어 Neo4j에 저장되지 않습니다. 이 필드는 S3에서 가져온 실제 답변 내용을 임시로 저장하기 위한 편의 필드입니다.

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 답변 생성
CREATE (ans:ApplicationAnswer {
    s3Link: "https://s3.example.com/answer-123.pdf",
    createdAt: datetime()
})
RETURN ans

// 자소서 버전의 답변 조회
MATCH (v:ApplicationVersion)-[:ANSWERS]->(ans:ApplicationAnswer)
WHERE v.id = 1
RETURN v, ans

// 질문에 대한 답변 조회
MATCH (q:ApplicationQuestion)<-[:ANSWERS_TO]-(ans:ApplicationAnswer)
WHERE q.id = 1
RETURN q, ans

// 특정 버전의 특정 질문에 대한 답변 조회
MATCH (v:ApplicationVersion)-[:ANSWERS]->(ans:ApplicationAnswer)-[:ANSWERS_TO]->(q:ApplicationQuestion)
WHERE v.id = 1 AND q.id = 1
RETURN v, ans, q
```

## 비즈니스 규칙

1. 한 답변은 하나의 질문에 대응합니다.
2. 한 답변은 하나의 자소서 버전에 속합니다.
3. 답변 내용은 S3에 저장되며, `s3Link`로 참조합니다.
4. `content` 필드는 런타임에 S3에서 로드하여 사용합니다.
5. 답변 생성 시 `createdAt` 필드가 자동으로 설정됩니다.

## S3 저장 전략

- 답변 내용은 텍스트 또는 문서 형식으로 S3에 저장됩니다.
- S3 링크를 통해 실제 내용에 접근합니다.
- `content` 필드는 성능 최적화를 위해 캐싱 목적으로 사용됩니다.

## 관련 엔티티

- [[application-question]] - ApplicationQuestion 엔티티
- [[application-version]] - ApplicationVersion 엔티티

## 태그
#backend #entity #type

