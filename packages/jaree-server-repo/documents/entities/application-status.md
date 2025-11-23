---
tags:
  - backend
  - type
  - enum
---

# ApplicationStatus 열거형

> 지원서 상태 열거형 문서

## 개요

`ApplicationStatus` 열거형은 지원서(자소서)의 진행 상태를 나타내는 타입입니다. 지원서가 어떤 단계에 있는지를 명확하게 표현합니다.

## 위치

`src/main/java/org/jaree/api/application/enums/ApplicationStatus.java`

## 열거형 값

| 값 | 표시 텍스트 | 설명 |
|----|------------|------|
| `NOT_STARTED` | "시작 전" | 지원서 작성이 시작되지 않음 |
| `WORK_IN_PROGRESS` | "작성 중" | 지원서 작성이 진행 중 |
| `SUBMITTED` | "제출 완료" | 지원서 제출이 완료됨 |
| `APPROVED` | "합격" | 지원서가 합격 처리됨 |
| `REJECTED` | "불합격" | 지원서가 불합격 처리됨 |

## 구현 세부사항

```java
@AllArgsConstructor
public enum ApplicationStatus {
    NOT_STARTED("시작 전"),
    WORK_IN_PROGRESS("작성 중"),
    SUBMITTED("제출 완료"),
    APPROVED("합격"),
    REJECTED("불합격");

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

## 상태 전이

일반적인 상태 전이 흐름:

```
NOT_STARTED → WORK_IN_PROGRESS → SUBMITTED → APPROVED
                                      ↓
                                  REJECTED
```

### 상태 전이 규칙

1. **NOT_STARTED**: 초기 상태, 아직 작성 시작 전
2. **WORK_IN_PROGRESS**: 사용자가 작성 중인 상태
3. **SUBMITTED**: 작성 완료 후 제출한 상태
4. **APPROVED**: 제출 후 합격 처리된 상태
5. **REJECTED**: 제출 후 불합격 처리된 상태

## 사용 예시

### Java 코드 예시

```java
// 상태 설정
Application application = Application.builder()
    .status(ApplicationStatus.WORK_IN_PROGRESS)
    .build();

// 상태 확인
if (application.getStatus() == ApplicationStatus.SUBMITTED) {
    // 제출 완료 처리
}

// JSON 직렬화 시
// {"status": "제출 완료"} 형태로 직렬화됨
```

### Neo4j 쿼리 예시

```cypher
// 특정 상태의 지원서 조회
MATCH (a:Application)
WHERE a.status = "SUBMITTED"
RETURN a

// 상태별 지원서 개수 집계
MATCH (a:Application)
RETURN a.status, count(a) as count
ORDER BY count DESC
```

## 관련 엔티티

- [[application]] - Application 엔티티

## 태그
#backend #type #enum

