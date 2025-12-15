---
tags:
  - rules
---

# Obsidian 규칙 및 가이드라인

> Jaree 프로젝트에서 Obsidian을 활용한 문서화 규칙

## 📌 기본 원칙

### 1. 문서 구조
- 모든 프로젝트 문서는 `documents/` 폴더 내에 작성합니다.
- 각 문서는 명확한 제목과 목차를 포함합니다.
- 문서 간 연결은 `[[링크]]` 형식의 Obsidian 링크를 활용합니다.

### 2. 네이밍 규칙
- 파일명은 소문자와 하이픈(`-`)을 사용합니다.
- 예: `api-design.md`, `database-schema.md`
- 날짜가 포함된 문서는 `YYYY-MM-DD-제목.md` 형식을 사용합니다.

### 3. 문서 분류
- `README`를 제외한 모든 문서는 필요한 폴더 바로 하위에 `documents`폴더를 생성해 작성합니다.

## 🔗 링크 사용 규칙

### 내부 링크
- 다른 문서를 참조할 때는 `[[문서명]]` 형식을 사용합니다.
- 별칭이 필요한 경우 `[[문서명|표시할 텍스트]]` 형식을 사용합니다.

### 태그 사용
- 문서 분류를 위해 태그를 활용합니다.
- 태그는 문서 상단에 배치합니다.

**사용 가능한 태그:**
- `#frontend` - 프론트엔드 관련 문서
- `#backend` - 백엔드 관련 문서
- `#database` - 데이터베이스 관련 문서
- `#architecture` - 시스템 아키텍처 관련 문서
- `#api` - API 설계 및 명세 문서
- `#component` - 컴포넌트 관련 문서
- `#rule` - 규칙 및 가이드라인 문서
- `#type` - 타입 정의 및 스키마 문서
- `#feature` - 기능 관련 문서
- `#flow` - 프로세스 및 플로우 차트 문서
- `#template` - 문서 템플릿 파일

### 백링크 활용
- Obsidian의 백링크 기능을 활용하여 문서 간 관계를 파악합니다.
- 관련 문서들을 자동으로 연결하여 지식 그래프를 구축합니다.

## 📝 문서 작성 템플릿

프로젝트에서 사용할 문서 템플릿은 `templates/` 폴더에 준비되어 있습니다. 각 태그별로 맞춤형 템플릿을 제공합니다.

### 사용 가능한 템플릿

- **[[templates/template-database|데이터베이스 템플릿]]** (`#database`): 데이터베이스 관련 문서 작성 시 사용
- **[[templates/template-architecture|아키텍처 템플릿]]** (`#architecture`): 시스템 아키텍처 문서 작성 시 사용
- **[[templates/template-api|API 템플릿]]** (`#api`): API 설계 및 명세 문서 작성 시 사용
- **[[templates/template-component|컴포넌트 템플릿]]** (`#component`): 컴포넌트 관련 문서 작성 시 사용
- **[[templates/template-type|타입 템플릿]]** (`#type`): 타입 정의 및 스키마 문서 작성 시 사용
- **[[templates/template-feature|기능 템플릿]]** (`#feature`): 기능 관련 문서 작성 시 사용
- **[[templates/template-flow|플로우 템플릿]]** (`#flow`): 프로세스 및 플로우 차트 문서 작성 시 사용

## ✅ 체크리스트

문서 작성 시 다음 사항을 확인하세요:

- [ ] 명확한 제목과 목차가 있는가?
- [ ] 관련 문서와 링크가 연결되어 있는가?
- [ ] 적절한 태그가 부여되었는가?
- [ ] 날짜가 포함된 문서는 올바른 형식을 따르는가?
- [ ] 폴더 구조에 맞게 배치되었는가?

## 🔄 문서 업데이트

- 문서를 수정할 때는 변경 이력을 기록합니다.
- 중요한 변경사항은 문서 상단에 변경 로그를 추가합니다.
- 더 이상 사용하지 않는 문서는 `archive/` 폴더로 이동합니다.

## 📌 참고

- Obsidian 공식 문서: [obsidian.md](https://obsidian.md)
- Markdown 가이드: [Markdown Guide](https://www.markdownguide.org/)

