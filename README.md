# Jaree Project

> 프로젝트 진입부 및 개요 문서

## 📋 프로젝트 개요

Jaree는 모노레포 구조로 구성된 풀스택 애플리케이션 프로젝트입니다.

## 🏗️ 프로젝트 구조

```
project-jaree/
├── documents/          # 프로젝트 문서 및 Obsidian 노트
├── packages/
│   ├── jaree-client-repo/  # React + Vite 프론트엔드
│   └── jaree-server-repo/  # Spring Boot 백엔드
├── package.json        # 모노레포 루트 설정
├── pnpm-workspace.yaml # pnpm 워크스페이스 설정
└── turbo.json         # Turborepo 설정
```

## 🛠️ 기술 스택

### Frontend (`jaree-client-repo`)
- **Framework**: React 19.1.1
- **Build Tool**: Vite 7.1.0
- **Language**: TypeScript 5.8.3
- **Styling**: Tailwind CSS 4.1.11
- **Package Manager**: pnpm 10.11.1

### Backend (`jaree-server-repo`)
- **Framework**: Spring Boot 3.5.4
- **Language**: Java 21
- **Build Tool**: Gradle
- **Database**: 
  - MongoDB (Spring Data MongoDB)
  - Neo4j (Spring Data Neo4j)
- **Security**: Spring Security + OAuth2 Client

### DevOps & Tools
- **Monorepo**: Turborepo 2.5.6
- **Package Manager**: pnpm
- **Documentation**: Obsidian

## 🚀 시작하기

### 사전 요구사항

- Node.js (pnpm 10.11.1)
- Java 21
- Gradle
- MongoDB
- Neo4j

### 설치 및 실행

```bash
# 의존성 설치
pnpm install

# 로컬 개발 환경 실행 (클라이언트 + 서버)
pnpm local
# 또는
pnpm start
```

### 개별 실행

#### 클라이언트
```bash
cd packages/jaree-client-repo
pnpm local      # 로컬 모드
pnpm dev        # 개발 모드
pnpm build      # 빌드
```

#### 서버
```bash
cd packages/jaree-server-repo
pnpm local      # 로컬 모드 실행
pnpm build      # 빌드
pnpm test       # 테스트 실행
```

## 📚 문서

- [Obsidian 규칙 및 가이드라인](./documents/obsidian-rules.md)
- 프로젝트 상세 문서는 `documents/` 폴더를 참고하세요.

## 📝 개발 가이드

### 코드 스타일

- **Frontend**: ESLint + Prettier 사용
- **Backend**: Java 표준 코딩 컨벤션 준수

## ⚡ Turborepo

이 프로젝트는 [Turborepo](https://turbo.build/)를 사용하여 모노레포를 관리합니다.

### Turborepo란?

Turborepo는 고성능 빌드 시스템으로, 여러 패키지를 효율적으로 빌드하고 실행할 수 있게 해줍니다. 캐싱, 병렬 실행, 작업 의존성 관리 등의 기능을 제공합니다.

### 주요 기능

- **캐싱**: 이전 빌드 결과를 캐시하여 불필요한 재빌드를 방지
- **병렬 실행**: 독립적인 작업을 병렬로 실행하여 빌드 시간 단축
- **작업 의존성 관리**: 패키지 간 의존성을 자동으로 파악하여 올바른 순서로 실행
- **환경 변수 관리**: 전역 및 작업별 환경 변수 설정

### 현재 설정 (`turbo.json`)

```json
{
  "globalDependencies": ["**/.env.*local"],
  "globalPassThroughEnv": ["NODE_ENV"],
  "tasks": {
    "local": {
      "cache": false,
      "persistent": true,
      "env": ["JAVA_HOME", "GRADLE_OPTS", "PATH"]
    },
    "test": {
      "dependsOn": ["^build"],
      "outputs": ["coverage/**"]
    }
  },
  "ui": "tui"
}
```

### 설정 설명

- **`globalDependencies`**: 모든 작업에 영향을 주는 전역 의존성 파일
  - `.env.*local` 파일 변경 시 모든 작업이 재실행됩니다.

- **`globalPassThroughEnv`**: 모든 작업에 전달되는 환경 변수
  - `NODE_ENV`가 모든 패키지에 전달됩니다.

- **`tasks.local`**: 로컬 개발 환경 실행 작업
  - `cache: false`: 캐싱 비활성화 (개발 중에는 항상 최신 상태 유지)
  - `persistent: true`: 장기 실행 작업 (서버 등)
  - `env`: Java 및 Gradle 관련 환경 변수 전달

- **`tasks.test`**: 테스트 작업
  - `dependsOn: ["^build"]`: 의존 패키지의 빌드가 완료된 후 실행
  - `outputs`: 테스트 커버리지 결과물 경로

- **`ui: "tui"`**: 터미널 UI 모드 사용

### 사용 가능한 명령어

```bash
# 모든 패키지의 local 작업 실행 (병렬)
pnpm local

# 특정 패키지의 작업만 실행
pnpm --filter jaree-client-repo local

# 캐시 무시하고 실행
pnpm local --force

# 특정 작업 실행
pnpm turbo test

# 빌드 그래프 시각화
pnpm turbo run build --graph
```

### 캐시 관리

```bash
# 캐시 확인
pnpm turbo run build --dry-run

# 캐시 삭제
pnpm turbo run build --force

# 원격 캐시 사용 (Vercel 등)
# 환경 변수 설정 필요: TURBO_TOKEN, TURBO_TEAM
```

### 참고 자료

- [Turborepo 공식 문서](https://turbo.build/repo/docs)
- [Turborepo GitHub](https://github.com/vercel/turbo)

## 🔗 관련 링크

- [클라이언트 README](CLIENT_README.md)
- [서버 README](SERVER_README.md)

## 📄 라이선스

ISC

