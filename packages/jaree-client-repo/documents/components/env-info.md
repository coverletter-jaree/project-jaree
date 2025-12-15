---
tags:
  - frontend
  - component
---

# EnvInfo 컴포넌트

> 환경 변수 정보를 표시하는 컴포넌트

## 개요

`EnvInfo` 컴포넌트는 애플리케이션의 환경 변수 정보를 사용자에게 시각적으로 표시하는 컴포넌트입니다. 개발 및 디버깅 목적으로 사용됩니다.

## 위치

`src/components/EnvInfo.tsx`

## Props / Inputs

이 컴포넌트는 props를 받지 않습니다. 대신 `import.meta.env`를 통해 환경 변수에 직접 접근합니다.

## 사용 예시

```tsx
import EnvInfo from './components/EnvInfo';

function App() {
  return (
    <>
      <h1>Vite + React</h1>
      <EnvInfo />
    </>
  );
}
```

## 표시되는 환경 변수

컴포넌트는 다음 환경 변수들을 표시합니다:

- `VITE_API_BASE_URL`: API 기본 URL
- `VITE_APP_NAME`: 애플리케이션 이름
- `VITE_DEBUG`: 디버그 모드 활성화 여부
- `VITE_LOG_LEVEL`: 로그 레벨
- `__BUILD_TIME__`: 빌드 시간 (빌드 시 주입)
- `__APP_VERSION__`: 애플리케이션 버전 (빌드 시 주입)

## 상태 관리

이 컴포넌트는 내부 상태를 가지지 않으며, 순수 함수형 컴포넌트입니다.

## 스타일링

- Tailwind CSS를 사용하여 스타일링
- `bg-gray-100`: 배경색
- `rounded-lg`: 둥근 모서리
- `p-4`: 패딩
- `space-y-2`: 세로 간격

## 타입 정의

```typescript
interface ImportMetaEnv {
  readonly VITE_API_BASE_URL?: string;
  readonly VITE_APP_NAME?: string;
  readonly VITE_DEBUG?: string;
  readonly VITE_LOG_LEVEL?: string;
  readonly __BUILD_TIME__?: string;
  readonly __APP_VERSION__?: string;
}
```

## 구현 세부사항

컴포넌트는 `import.meta.env`를 통해 Vite의 환경 변수에 접근합니다. Vite는 `VITE_` 접두사로 시작하는 환경 변수만 클라이언트 번들에 포함시킵니다.

## 사용 시나리오

1. **개발 환경**: 개발 중 환경 변수 설정 확인
2. **디버깅**: 환경 변수 관련 문제 해결
3. **데모**: 환경 설정 정보 표시

## 주의사항

- 프로덕션 환경에서는 보안상의 이유로 이 컴포넌트를 제거하거나 조건부 렌더링하는 것을 권장합니다.
- 민감한 정보가 포함된 환경 변수는 표시하지 않도록 주의해야 합니다.

## 향후 개선 사항

- [ ] 프로덕션 환경에서 자동 숨김 처리
- [ ] 환경 변수 필터링 기능 추가
- [ ] 환경 변수 편집 기능 (개발 환경 전용)
- [ ] 환경 변수 검증 기능

## 태그
#frontend #component

