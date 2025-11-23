---
tags:
  - backend
  - entity
  - type
---

# ResumeRelationship 엔티티

> 이력서 관계 속성 엔티티 문서

## 개요

`ResumeRelationship` 엔티티는 이력서와 이력서 데이터 간의 관계를 나타내는 Neo4j 관계 속성입니다. 이력서의 다양한 항목(학력, 경력, 프로젝트 등)을 타입별로 구분하여 관리합니다.

## 위치

`src/main/java/org/jaree/api/resume/entity/ResumeRelationship.java`

## 엔티티 구조

```java
@RelationshipProperties
public class ResumeRelationship {
    private final ResumeRelationshipType type;
    
    @TargetNode
    private final JsonData data;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `type` | `ResumeRelationshipType` | 관계 종류 (이력서 항목 타입) | 필수 |
| `data` | `JsonData` | 이력서 저장 데이터 | 필수, TargetNode |

## 관계 속성

`ResumeRelationship`는 `@RelationshipProperties` 어노테이션을 사용하는 관계 속성 엔티티입니다. 이는 Neo4j에서 관계(Relationship)에 속성을 부여하기 위한 방식입니다.

## 관계 타입

`ResumeRelationshipType` 열거형을 통해 관계 타입을 정의합니다:

- `CONTAINS_EDUCATION`: 학력
- `CONTAINS_WORK_EXPERIENCE`: 경력
- `CONTAINS_PROJECTS`: 프로젝트
- `CONTAINS_OTHER_EXPERIENCE`: 대외활동
- `CONTAINS_LECTURES`: 수업
- `CONTAINS_CERTIFICATES`: 자격증

자세한 내용은 [[resume-relationship-type]] 문서를 참고하세요.

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 이력서와 학력 데이터 간의 관계 생성
MATCH (r:Resume), (jd:JsonData)
WHERE r.id = 1 AND jd.id = 10
CREATE (r)-[rel:RELATIONSHIP {
    type: "CONTAINS_EDUCATION"
}]->(jd)
RETURN r, rel, jd

// 이력서의 모든 항목 조회
MATCH (r:Resume)-[rel:RELATIONSHIP]->(jd:JsonData)
WHERE r.id = 1
RETURN r, rel.type, jd

// 특정 타입의 이력서 항목 조회
MATCH (r:Resume)-[rel:RELATIONSHIP]->(jd:JsonData)
WHERE r.id = 1 AND rel.type = "CONTAINS_WORK_EXPERIENCE"
RETURN r, rel, jd

// 이력서의 경력 항목만 조회
MATCH (r:Resume)-[rel:RELATIONSHIP {type: "CONTAINS_WORK_EXPERIENCE"}]->(jd:JsonData)
WHERE r.id = 1
RETURN r, jd
ORDER BY jd.createdAt DESC
```

## 비즈니스 규칙

1. 한 이력서는 여러 타입의 관계를 가질 수 있습니다.
2. 같은 타입의 관계를 여러 개 가질 수 있습니다 (예: 여러 개의 경력 항목).
3. 관계 타입은 `ResumeRelationshipType` 열거형으로 정의됩니다.
4. 실제 데이터는 `JsonData` 노드에 저장됩니다.

## 데이터 모델

```
Resume
  ├── [RELATIONSHIP {type: CONTAINS_EDUCATION}] ──> JsonData (학력 정보)
  ├── [RELATIONSHIP {type: CONTAINS_WORK_EXPERIENCE}] ──> JsonData (경력 1)
  ├── [RELATIONSHIP {type: CONTAINS_WORK_EXPERIENCE}] ──> JsonData (경력 2)
  ├── [RELATIONSHIP {type: CONTAINS_PROJECTS}] ──> JsonData (프로젝트 1)
  └── [RELATIONSHIP {type: CONTAINS_CERTIFICATES}] ──> JsonData (자격증 1)
```

## 관련 엔티티

- [[resume]] - Resume 엔티티
- [[json-data]] - JsonData 엔티티
- [[resume-relationship-type]] - ResumeRelationshipType 열거형

## 태그
#backend #entity #type

