---
tags:
  - backend
  - entity
  - type
---

# Company 엔티티

> 회사 엔티티 문서

## 개요

`Company` 엔티티는 채용공고를 등록하는 회사를 나타내는 Neo4j 노드입니다. 회사 정보와 회사가 생성한 채용공고와의 관계를 관리합니다.

## 위치

`src/main/java/org/jaree/api/company/entity/Company.java`

## 엔티티 구조

```java
@Node("Company")
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private String description;
    
    private String logoUrl;
    
    private List<CompanyCategory> categories;
    
    @Relationship(type = "CREATED_BY", direction = Relationship.Direction.INCOMING)
    private List<JobOpening> jobOpenings;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 회사 고유 식별자 | 자동 생성 |
| `name` | `String` | 회사 이름 | - |
| `description` | `String` | 회사 설명 | - |
| `logoUrl` | `String` | 회사 대표 이미지 링크 | - |
| `categories` | `List<CompanyCategory>` | 회사 카테고리 목록 (대기업, 중견기업, 스타트업 등) | - |
| `jobOpenings` | `List<JobOpening>` | 회사가 생성한 채용공고 리스트 | 관계: CREATED_BY (INCOMING) |

## 관계

### CREATED_BY (INCOMING)
- **방향**: INCOMING (JobOpening 노드에서 Company로 향함)
- **대상 엔티티**: `JobOpening` - 회사가 생성한 채용공고들

## CompanyCategory 열거형

회사 카테고리 분류를 나타내는 열거형입니다.

위치: `src/main/java/org/jaree/api/company/enums/CompanyCategory.java`

예시 카테고리:
- 대기업
- 중견기업
- 스타트업
- 기타

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 회사 생성
CREATE (c:Company {
    name: "테크 회사",
    description: "혁신적인 기술을 개발하는 회사",
    logoUrl: "https://example.com/logo.png",
    categories: ["스타트업"]
})
RETURN c

// 회사가 생성한 모든 채용공고 조회
MATCH (c:Company)-[:CREATED_BY]->(j:JobOpening)
WHERE c.id = 1
RETURN c, j

// 특정 카테고리의 회사 조회
MATCH (c:Company)
WHERE "스타트업" IN c.categories
RETURN c
```

## 비즈니스 규칙

1. 한 회사는 여러 개의 채용공고를 생성할 수 있습니다.
2. 회사는 여러 카테고리에 속할 수 있습니다.
3. 회사 로고는 URL 형식으로 저장됩니다.

## 관련 엔티티

- [[job-opening]] - JobOpening 엔티티

## 태그
#backend #entity #type

