---
tags:
  - backend
  - entity
  - type
---

# Resume 엔티티

> 이력서 엔티티 문서

## 개요

`Resume` 엔티티는 사용자가 작성한 이력서를 나타내는 Neo4j 노드입니다. 학력, 경험, 경력 등의 정보를 포함하며, 사용자와의 관계를 통해 관리됩니다.

## 위치

`src/main/java/org/jaree/api/resume/entity/Resume.java`

## 엔티티 구조

```java
@Node("Resume")
public class Resume {
    @Id
    @GeneratedValue
    private Long id;
    
    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private User user;
    
    @Relationship(direction = Relationship.Direction.OUTGOING)
    private List<ResumeRelationship> data;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 이력서 고유 식별자 | 자동 생성 |
| `user` | `User` | 이력서를 작성한 사용자 | 관계: WRITTEN_BY (OUTGOING) |
| `data` | `List<ResumeRelationship>` | 이력서 저장 데이터(내용) 목록 | 관계: OUTGOING |

## 관계

### WRITTEN_BY (OUTGOING)
- **방향**: OUTGOING (Resume에서 User로 향함)
- **대상 엔티티**: `User` - 이력서를 작성한 사용자

### ResumeRelationship (OUTGOING)
- **방향**: OUTGOING (Resume에서 ResumeRelationship으로 향함)
- **대상 엔티티**: `ResumeRelationship` - 이력서의 세부 데이터 (학력, 경험, 경력 등)

## ResumeRelationship 엔티티

이력서의 세부 데이터를 나타내는 엔티티입니다.

위치: `src/main/java/org/jaree/api/resume/entity/ResumeRelationship.java`

## ResumeRelationshipType 열거형

이력서 관계 타입을 나타내는 열거형입니다.

위치: `src/main/java/org/jaree/api/resume/enums/ResumeRelationshipType.java`

예시 타입:
- 학력
- 경험
- 경력
- 자격증
- 기타

## JsonData 엔티티

이력서 데이터의 JSON 형식 저장을 위한 엔티티입니다.

위치: `src/main/java/org/jaree/api/resume/entity/JsonData.java`

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 이력서 생성
CREATE (r:Resume)
RETURN r

// 사용자의 이력서 조회
MATCH (u:User)-[:WRITTEN_BY]->(r:Resume)
WHERE u.id = 1
RETURN u, r

// 이력서의 세부 데이터 조회
MATCH (r:Resume)-[:RELATIONSHIP]->(rd:ResumeRelationship)
WHERE r.id = 1
RETURN r, rd
```

## 비즈니스 규칙

1. 한 사용자는 하나의 이력서를 가질 수 있습니다.
2. 이력서는 여러 개의 세부 데이터(학력, 경험, 경력 등)를 가질 수 있습니다.
3. 이력서 데이터는 관계형 구조로 저장되어 유연한 데이터 모델링이 가능합니다.

## 관련 엔티티

- [[user]] - User 엔티티
- ResumeRelationship - 이력서 세부 데이터 엔티티
- JsonData - JSON 데이터 엔티티

## 태그
#backend #entity #type

