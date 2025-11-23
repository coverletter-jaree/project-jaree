---
tags:
  - backend
  - type
  - enum
---

# CompanyCategory 열거형

> 회사 카테고리 열거형 문서

## 개요

`CompanyCategory` 열거형은 회사의 규모와 설립 형태를 분류하는 타입입니다. 회사를 다양한 기준으로 분류하여 관리할 수 있습니다.

## 위치

`src/main/java/org/jaree/api/company/enums/CompanyCategory.java`

## 열거형 값

### 규모 기반 카테고리

| 값 | 표시 텍스트 | 설명 |
|----|------------|------|
| `MICRO` | "소기업" | 소규모 기업 |
| `SME` | "중소기업" | 중소기업 |
| `MID_SIZE` | "중견기업" | 중견기업 |
| `LARGE` | "대기업" | 대기업 |

### 설립 형태 / 성격 기반 카테고리

| 값 | 표시 텍스트 | 설명 |
|----|------------|------|
| `STARTUP` | "스타트업" | 스타트업 기업 |
| `PRIVATE` | "사기업" | 사기업 |
| `PUBLIC` | "공기업" | 공기업 |
| `NGO` | "비영리 단체" | 비영리 단체 |
| `GOVERNMENT` | "공기업" | 공기업 (중복, 수정 필요) |

## 구현 세부사항

```java
@AllArgsConstructor
public enum CompanyCategory {
    // 규모 기반
    MICRO("소기업"),
    SME("중소기업"),
    MID_SIZE("중견기업"),
    LARGE("대기업"),

    // 설립 형태 / 성격
    STARTUP("스타트업"),
    PRIVATE("사기업"),
    PUBLIC("공기업"),
    NGO("비영리 단체"),
    GOVERNMENT("공기업");

    private final String displayText;

    @JsonValue
    public String getDisplayText() {
        return displayText;
    }
}
```

## 특징

- `@JsonValue` 어노테이션을 사용하여 JSON 직렬화 시 `displayText` 값을 사용합니다.
- API 응답에서는 한글 표시 텍스트가 반환됩니다.
- 한 회사는 여러 카테고리에 속할 수 있습니다 (예: 대기업이면서 스타트업일 수도 있음).

## 주의사항

- `PUBLIC`과 `GOVERNMENT` 모두 "공기업"으로 표시되며, 이는 중복입니다. 필요에 따라 수정이 필요할 수 있습니다.

## 사용 예시

### Java 코드 예시

```java
// 카테고리 설정
Company company = Company.builder()
    .categories(Arrays.asList(
        CompanyCategory.LARGE,
        CompanyCategory.STARTUP
    ))
    .build();

// 카테고리 확인
if (company.getCategories().contains(CompanyCategory.STARTUP)) {
    // 스타트업 처리
}

// JSON 직렬화 시
// {"categories": ["대기업", "스타트업"]} 형태로 직렬화됨
```

### Neo4j 쿼리 예시

```cypher
// 특정 카테고리의 회사 조회
MATCH (c:Company)
WHERE "스타트업" IN c.categories
RETURN c

// 대기업이면서 스타트업인 회사 조회
MATCH (c:Company)
WHERE "대기업" IN c.categories AND "스타트업" IN c.categories
RETURN c

// 카테고리별 회사 개수 집계
MATCH (c:Company)
UNWIND c.categories AS category
RETURN category, count(*) as count
ORDER BY count DESC
```

## 카테고리 조합 예시

한 회사는 여러 카테고리를 가질 수 있습니다:

- **대기업 + 스타트업**: 대규모 스타트업
- **중견기업 + 사기업**: 중견 사기업
- **소기업 + 비영리 단체**: 소규모 비영리 단체

## 관련 엔티티

- [[company]] - Company 엔티티

## 태그
#backend #type #enum

