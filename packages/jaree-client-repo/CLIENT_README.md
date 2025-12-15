---
tags:
  - frontend
---
# jaree-client-repo 패키지 개요

> React + TypeScript + Vite 기반 프론트엔드 클라이언트 애플리케이션

## 개요

jaree-client-repo는 Jaree 프로젝트의 프론트엔드 클라이언트 패키지입니다. React 19와 TypeScript를 사용하여 구축되었으며, Vite를 빌드 도구로 사용합니다.

## 기술 스택

- **프레임워크**: React 19.1.1
- **언어**: TypeScript 5.8.3
- **빌드 도구**: Vite 7.1.0
- **스타일링**: Tailwind CSS 4.1.11
- **코드 품질**: ESLint, Prettier

## 주요 의존성

### 프로덕션 의존성
- `react`: ^19.1.1
- `react-dom`: ^19.1.1
- `react-bits`: ^1.0.5
- `prettier`: ^3.6.2

### 개발 의존성
- `@vitejs/plugin-react`: ^4.7.0
- `typescript`: ~5.8.3
- `typescript-eslint`: ^8.39.0
- `tailwindcss`: ^4.1.11
- `eslint`: ^9.32.0

## 프로젝트 구조

```
jaree-client-repo/
├── src/
│   ├── components/        # React 컴포넌트
│   │   └── EnvInfo.tsx
│   ├── assets/           # 정적 자산
│   ├── App.tsx           # 메인 앱 컴포넌트
│   ├── main.tsx          # 진입점
│   └── index.css         # 전역 스타일
├── env/                  # 환경 변수 파일
├── public/               # 공개 정적 파일
├── vite.config.ts        # Vite 설정
└── package.json
```

## 스크립트

- `dev`: 개발 모드 실행 (dev 프로파일)
- `local`: 로컬 개발 모드 실행
- `build`: 프로덕션 빌드
- `build:dev`: 개발 환경 빌드
- `build:prod`: 프로덕션 환경 빌드
- `lint`: ESLint 실행
- `preview`: 빌드 결과 미리보기
- `format`: Prettier 포맷팅
- `format:check`: Prettier 포맷팅 검사

## 환경 변수

환경 변수는 `env/` 디렉토리에 위치하며, `VITE_` 접두사로 시작하는 변수만 클라이언트에 노출됩니다.

주요 환경 변수:
- `VITE_API_BASE_URL`: API 기본 URL
- `VITE_APP_NAME`: 애플리케이션 이름
- `VITE_DEBUG`: 디버그 모드
- `VITE_LOG_LEVEL`: 로그 레벨

## 개발 서버

개발 서버는 기본적으로 포트 3000에서 실행되며, 자동으로 브라우저를 엽니다.

```bash
npm run dev
```

## 빌드

프로덕션 빌드는 `dist/` 디렉토리에 생성됩니다.

```bash
npm run build:prod
```

## 관련 문서
- [[jaree-client-repo-index|index]] - 프로젝트 인덱스 문서

## 태그
#frontend