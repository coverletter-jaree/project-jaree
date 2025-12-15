---
tags:
  - backend
  - entity
  - type
---

# ApplicationVersion 엔티티

> 자소서 커밋(버전) 엔티티 문서

## 개요

`ApplicationVersion` 엔티티는 자소서의 특정 버전을 기록한 커밋을 나타내는 Neo4j 노드입니다. 버전 관리 및 히스토리 추적을 위해 사용됩니다.

## 위치

`src/main/java/org/jaree/api/application/entity/ApplicationVersion.java`

## 엔티티 구조

```java
@Node("ApplicationVersion")
public class ApplicationVersion {
    @Id
    @GeneratedValue
    private Long id;
    
    private String commitMessage;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Relationship(type = "CHANGED_FROM", direction = Relationship.Direction.OUTGOING)
    private ApplicationVersion previousVersion;
    
    @Relationship(type = "ANSWERS", direction = Relationship.Direction.OUTGOING)
    private List<ApplicationAnswer> answers;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 버전 고유 식별자 | 자동 생성 |
| `commitMessage` | `String` | 자소서 커밋 제목/메시지 | - |
| `createdAt` | `LocalDateTime` | 자소서 커밋 생성일자 | 자동 생성 |
| `previousVersion` | `ApplicationVersion` | 해당 커밋의 직전 커밋 | 관계: CHANGED_FROM (OUTGOING) |
| `answers` | `List<ApplicationAnswer>` | 해당 버전에서 작성한 답변 리스트 | 관계: ANSWERS (OUTGOING) |

## 관계

### CHANGED_FROM (OUTGOING)
- **방향**: OUTGOING (ApplicationVersion에서 이전 ApplicationVersion으로 향함)
- **대상 엔티티**: `ApplicationVersion` - 이전 버전의 커밋
- **용도**: 버전 히스토리 체인을 형성하여 변경 이력을 추적

### ANSWERS (OUTGOING)
- **방향**: OUTGOING (ApplicationVersion에서 ApplicationAnswer로 향함)
- **대상 엔티티**: `ApplicationAnswer` - 해당 버전에 포함된 답변들

## 버전 관리 전략

ApplicationVersion은 Git과 유사한 버전 관리 시스템을 구현합니다:

1. **커밋 체인**: `previousVersion` 관계를 통해 버전 간 연결
2. **답변 스냅샷**: 각 버전은 해당 시점의 답변들을 포함
3. **히스토리 추적**: 변경 이력을 시간순으로 추적 가능

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// 버전 생성
CREATE (v:ApplicationVersion {
    commitMessage: "초안 작성",
    createdAt: datetime()
})
RETURN v

// 지원서의 버전 히스토리 조회
MATCH (a:Application)-[:HAS_VERSION]->(v:ApplicationVersion)
WHERE a.id = 1
RETURN v
ORDER BY v.createdAt DESC

// 버전 체인 조회 (최신부터 과거까지)
MATCH path = (v:ApplicationVersion)-[:CHANGED_FROM*]->(prev:ApplicationVersion)
WHERE v.id = 5
RETURN path
ORDER BY v.createdAt DESC

// 특정 버전의 답변 조회
MATCH (v:ApplicationVersion)-[:ANSWERS]->(ans:ApplicationAnswer)
WHERE v.id = 1
RETURN v, ans

// 버전 간 차이점 비교
MATCH (v1:ApplicationVersion)-[:ANSWERS]->(ans1:ApplicationAnswer),
      (v2:ApplicationVersion)-[:ANSWERS]->(ans2:ApplicationAnswer)
WHERE v1.id = 1 AND v2.id = 2
RETURN v1, v2, ans1, ans2
```

## 비즈니스 규칙

1. 한 버전은 여러 개의 답변을 포함할 수 있습니다.
2. 버전은 이전 버전과 연결되어 체인을 형성합니다.
3. 첫 번째 버전은 `previousVersion`이 null일 수 있습니다.
4. 한 지원서는 여러 버전을 가질 수 있습니다 (leaf node가 여러 개일 수 있음).
5. 버전 생성 시 `createdAt` 필드가 자동으로 설정됩니다.

## 버전 관리 시나리오

### 시나리오 1: 초기 작성
```
Application (id: 1)
  └── ApplicationVersion (id: 1, commitMessage: "초안 작성")
      └── ApplicationAnswer (질문 1 답변)
      └── ApplicationAnswer (질문 2 답변)
```

### 시나리오 2: 수정 및 커밋
```
Application (id: 1)
  ├── ApplicationVersion (id: 1, commitMessage: "초안 작성")
  │   └── ApplicationAnswer (질문 1 답변 - v1)
  │   └── ApplicationAnswer (질문 2 답변 - v1)
  └── ApplicationVersion (id: 2, commitMessage: "질문 1 수정", previousVersion: v1)
      └── ApplicationAnswer (질문 1 답변 - v2)
      └── ApplicationAnswer (질문 2 답변 - v1 복사)
```

### 시나리오 3: 브랜치 생성
```
Application (id: 1)
  ├── ApplicationVersion (id: 1, commitMessage: "초안 작성")
  ├── ApplicationVersion (id: 2, commitMessage: "버전 A", previousVersion: v1)
  └── ApplicationVersion (id: 3, commitMessage: "버전 B", previousVersion: v1)
```

## 관련 엔티티

- [[application]] - Application 엔티티
- [[application-answer]] - ApplicationAnswer 엔티티

## 태그
#backend #entity #type

