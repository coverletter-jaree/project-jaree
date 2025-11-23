---
tags:
  - backend
  - navigate
---

# jaree-server-repo 문서 인덱스

> 백엔드 API 서버 패키지 문서 모음

## 개요

이 폴더는 jaree-server-repo 패키지의 모든 문서를 포함합니다.

## 문서 목록

### 아키텍처 문서
- [[architecture]] - 아키텍처 설계 및 기술 결정사항

### 엔티티 문서
- [[entities/user]] - User 엔티티 문서
- [[entities/company]] - Company 엔티티 문서
- [[entities/job-opening]] - JobOpening 엔티티 문서
- [[entities/application]] - Application 엔티티 문서
- [[entities/resume]] - Resume 엔티티 문서

## 빠른 링크

- [패키지 개요](./package-overview.md)
- [아키텍처](./architecture.md)
- [엔티티 문서](./entities/)

## 엔티티 관계도

```
User ──WRITTEN_BY──> Application ──APPLIES_FOR──> JobOpening ──CREATED_BY──> Company
  │                      │
  │                      ├──HAS_QUESTION──> ApplicationQuestion
  │                      │
  │                      └──HAS_VERSION──> ApplicationVersion
  │
  └──WRITTEN_BY──> Resume ──RELATIONSHIP──> ResumeRelationship
```

## 관련 문서

- [[jaree-client-repo-index]] - 프론트엔드 패키지 문서
- [[../../documents/backend/index]] - 백엔드 문서

## 태그
#backend

