import react from '@vitejs/plugin-react';
import { defineConfig } from 'vite';

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  return {
    plugins: [react()],
    envDir: 'env', // .env 파일들이 위치할 디렉토리
    envPrefix: 'VITE_', // VITE_로 시작하는 환경변수만 클라이언트에 노출
    server: {
      port: 3000,
      open: true,
    },
    build: {
      outDir: 'dist',
      sourcemap: mode === 'development',
    },
  };
});
