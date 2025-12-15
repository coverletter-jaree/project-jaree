---
tags:
  - backend
  - type
  - enum
---

# ResumeRelationshipType 열거형

> 이력서 관계 타입 열거형 문서

## 개요

`ResumeRelationshipType` 열거형은 이력서의 다양한 항목 타입을 나타내는 열거형입니다. 이력서에 포함될 수 있는 다양한 섹션을 구분합니다.

## 위치

`src/main/java/org/jaree/api/resume/enums/ResumeRelationshipType.java`

## 열거형 값

| 값 | 설명 |
|----|------|
| `CONTAINS_EDUCATION` | 학력 |
| `CONTAINS_WORK_EXPERIENCE` | 경력 |
| `CONTAINS_PROJECTS` | 프로젝트 |
| `CONTAINS_OTHER_EXPERIENCE` | 대외활동 |
| `CONTAINS_LECTURES` | 수업 |
| `CONTAINS_CERTIFICATES` | 자격증 |

## 구현 세부사항

```java
@AllArgsConstructor
public enum ResumeRelationshipType {
    CONTAINS_EDUCATION,         // 학력
    CONTAINS_WORK_EXPERIENCE,   // 경력
    CONTAINS_PROJECTS,          // 프로젝트
    CONTAINS_OTHER_EXPERIENCE,  // 대외활동
    CONTAINS_LECTURES,          // 수업
    CONTAINS_CERTIFICATES;      // 자격증
}
```

## 특징

- 이 열거형은 `ResumeRelationship` 관계 속성에서 사용됩니다.
- 각 값은 이력서의 특정 섹션을 나타냅니다.
- 한 이력서는 여러 타입의 항목을 가질 수 있습니다.
- 같은 타입의 항목을 여러 개 가질 수 있습니다 (예: 여러 개의 경력 항목).

## 사용 예시

### Java 코드 예시

```java
// 관계 생성
ResumeRelationship relationship = new ResumeRelationship(
    ResumeRelationshipType.CONTAINS_WORK_EXPERIENCE,
    jsonData
);

// 타입 확인
if (relationship.getType() == ResumeRelationshipType.CONTAINS_EDUCATION) {
    // 학력 데이터 처리
}
```

### Neo4j 쿼리 예시

```cypher
// 특정 타입의 이력서 항목 조회
MATCH (r:Resume)-[rel:RELATIONSHIP]->(jd:JsonData)
WHERE r.id = 1 AND rel.type = "CONTAINS_WORK_EXPERIENCE"
RETURN r, rel, jd

// 모든 경력 항목 조회
MATCH (r:Resume)-[rel:RELATIONSHIP {type: "CONTAINS_WORK_EXPERIENCE"}]->(jd:JsonData)
WHERE r.id = 1
RETURN r, jd
ORDER BY jd.createdAt DESC

// 이력서의 모든 항목 타입별 집계
MATCH (r:Resume)-[rel:RELATIONSHIP]->(jd:JsonData)
WHERE r.id = 1
RETURN rel.type, count(*) as count
ORDER BY count DESC
```

## 항목 타입별 설명

### CONTAINS_EDUCATION (학력)
- 학교명, 전공, 학위, 기간 등의 정보를 포함합니다.
- 학사, 석사, 박사 등 다양한 학위 수준을 표현할 수 있습니다.

### CONTAINS_WORK_EXPERIENCE (경력)
- 회사명, 직책, 근무 기간, 담당 업무 등의 정보를 포함합니다.
- 여러 개의 경력 항목을 가질 수 있습니다.

### CONTAINS_PROJECTS (프로젝트)
- 프로젝트명, 역할, 기간, 기술 스택 등의 정보를 포함합니다.
- 개인 프로젝트, 팀 프로젝트 등을 구분할 수 있습니다.

### CONTAINS_OTHER_EXPERIENCE (대외활동)
- 동아리, 봉사활동, 인턴십 등의 정보를 포함합니다.
- 다양한 형태의 대외활동을 기록할 수 있습니다.

### CONTAINS_LECTURES (수업)
- 수강한 강의, 온라인 강의 등의 정보를 포함합니다.
- 교육 이력을 기록할 수 있습니다.

### CONTAINS_CERTIFICATES (자격증)
- 자격증명, 취득일, 발급 기관 등의 정보를 포함합니다.
- 다양한 자격증을 기록할 수 있습니다.

## 관련 엔티티

- [[resume-relationship]] - ResumeRelationship 엔티티
- [[resume]] - Resume 엔티티

## 태그
#backend #type #enum

