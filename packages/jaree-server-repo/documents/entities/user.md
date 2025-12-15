---
tags:
  - backend
  - entity
  - type
---

# User 엔티티

> 사용자 엔티티 문서

## 개요

`User` 엔티티는 시스템의 사용자를 나타내는 Neo4j 노드입니다. 사용자가 작성한 지원서(자소서)와 이력서와의 관계를 관리합니다.

## 위치

`src/main/java/org/jaree/api/user/entity/User.java`

## 엔티티 구조

```java
@Node("User")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.INCOMING)
    private List<Application> applications;
    
    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.INCOMING)
    private Resume resume;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 사용자 고유 식별자 | 자동 생성 |
| `name` | `String` | 사용자 이름 | - |
| `createdAt` | `LocalDateTime` | 생성 일시 | 자동 생성 |
| `applications` | `List<Application>` | 사용자가 작성한 지원서 리스트 | 관계: WRITTEN_BY (INCOMING) |
| `resume` | `Resume` | 사용자가 작성한 이력서 | 관계: WRITTEN_BY (INCOMING) |

## 관계

### WRITTEN_BY (INCOMING)
- **방향**: INCOMING (다른 노드에서 User로 향함)
- **대상 엔티티**: 
  - `Application`: 사용자가 작성한 지원서들
  - `Resume`: 사용자가 작성한 이력서

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 사용자 생성
CREATE (u:User {name: "홍길동", createdAt: datetime()})
RETURN u

// 사용자가 작성한 모든 지원서 조회
MATCH (u:User)-[:WRITTEN_BY]->(a:Application)
WHERE u.id = 1
RETURN u, a

// 사용자의 이력서 조회
MATCH (u:User)-[:WRITTEN_BY]->(r:Resume)
WHERE u.id = 1
RETURN u, r
```

## 비즈니스 규칙

1. 한 사용자는 여러 개의 지원서를 작성할 수 있습니다.
2. 한 사용자는 하나의 이력서를 가질 수 있습니다.
3. 사용자 생성 시 `createdAt` 필드는 자동으로 설정됩니다.

## 관련 엔티티

- [[application]] - Application 엔티티
- [[resume]] - Resume 엔티티

## 태그
#backend #entity #type

