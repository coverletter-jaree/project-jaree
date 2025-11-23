---
tags:
  - backend
  - entity
  - type
---

# JsonData 엔티티

> JSON 데이터 저장 엔티티 문서

## 개요

`JsonData` 엔티티는 JSON 형식의 데이터를 저장하기 위한 Neo4j 노드입니다. 이력서의 세부 데이터(학력, 경력, 프로젝트 등)를 S3에 저장하고 참조하는 데 사용됩니다.

## 위치

`src/main/java/org/jaree/api/resume/entity/JsonData.java`

## 엔티티 구조

```java
@Node("User")
public class JsonData {
    @Id
    @GeneratedValue
    private Long id;
    
    private String s3Url;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @CreatedDate
    private LocalDateTime createdAt;
}
```

## 필드 설명

| 필드명 | 타입 | 설명 | 제약사항 |
|--------|------|------|----------|
| `id` | `Long` | 데이터 고유 식별자 | 자동 생성 |
| `s3Url` | `String` | JSON 데이터가 저장된 S3 URL | - |
| `updatedAt` | `LocalDateTime` | JSON 데이터 마지막 수정일자 | 자동 업데이트 |
| `createdAt` | `LocalDateTime` | JSON 데이터 생성일자 | 자동 생성 |

## 주의사항

**중요**: 현재 코드에서 `@Node("User")` 어노테이션이 사용되고 있으나, 이는 오류로 보입니다. 실제로는 `@Node("JsonData")`를 사용해야 합니다.

## S3 저장 전략

- JSON 형식의 데이터를 S3에 저장합니다.
- S3 URL을 통해 실제 데이터에 접근합니다.
- 데이터 구조는 유연하게 변경 가능합니다.

## 사용 예시

### Neo4j 쿼리 예시

```cypher
// JsonData 생성
CREATE (jd:JsonData {
    s3Url: "https://s3.example.com/resume/education-123.json",
    createdAt: datetime(),
    updatedAt: datetime()
})
RETURN jd

// 이력서와 연결된 JsonData 조회
MATCH (r:Resume)-[:RELATIONSHIP]->(jd:JsonData)
WHERE r.id = 1
RETURN r, jd

// 특정 타입의 JsonData 조회
MATCH (r:Resume)-[rel:RELATIONSHIP]->(jd:JsonData)
WHERE r.id = 1 AND rel.type = "CONTAINS_EDUCATION"
RETURN r, jd

// 최근 수정된 데이터 조회
MATCH (jd:JsonData)
WHERE jd.updatedAt > datetime() - duration({days: 7})
RETURN jd
ORDER BY jd.updatedAt DESC
```

## JSON 데이터 구조 예시

### 학력 데이터 (CONTAINS_EDUCATION)
```json
{
  "schoolName": "서울대학교",
  "major": "컴퓨터공학과",
  "degree": "학사",
  "startDate": "2018-03-01",
  "endDate": "2022-02-28",
  "gpa": 4.2,
  "description": "컴퓨터 과학 전반을 학습"
}
```

### 경력 데이터 (CONTAINS_WORK_EXPERIENCE)
```json
{
  "companyName": "테크 회사",
  "position": "백엔드 개발자",
  "startDate": "2022-03-01",
  "endDate": "2024-12-31",
  "description": "Spring Boot 기반 API 개발",
  "achievements": ["시스템 성능 50% 개선", "마이크로서비스 아키텍처 도입"]
}
```

### 프로젝트 데이터 (CONTAINS_PROJECTS)
```json
{
  "projectName": "Jaree 프로젝트",
  "role": "풀스택 개발자",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31",
  "technologies": ["React", "Spring Boot", "Neo4j"],
  "description": "채용 지원서 관리 시스템 개발"
}
```

## 비즈니스 규칙

1. JsonData는 S3에 저장된 JSON 파일을 참조합니다.
2. 데이터 수정 시 `updatedAt` 필드가 자동으로 업데이트됩니다.
3. 데이터 생성 시 `createdAt` 필드가 자동으로 설정됩니다.
4. 하나의 JsonData는 하나의 이력서 항목을 나타냅니다.

## 데이터 접근 패턴

1. **읽기**: S3 URL을 통해 JSON 데이터를 로드
2. **쓰기**: JSON 데이터를 S3에 저장하고 URL을 업데이트
3. **수정**: S3의 JSON 파일을 업데이트하고 `updatedAt` 갱신

## 관련 엔티티

- [[resume]] - Resume 엔티티
- [[resume-relationship]] - ResumeRelationship 엔티티

## 태그
#backend #entity #type

