#!/bin/bash

# 프로젝트 루트 디렉토리로 이동
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

echo "프로젝트 루트: $PROJECT_ROOT"
echo "모든 패키지에 대해 pnpm install을 실행합니다..."
echo ""

# packages 디렉토리의 모든 하위 디렉토리를 순회
PACKAGES_DIR="$PROJECT_ROOT/packages"

if [ ! -d "$PACKAGES_DIR" ]; then
  echo "오류: packages 디렉토리를 찾을 수 없습니다."
  exit 1
fi

# packages 디렉토리의 각 하위 디렉토리에 대해 pnpm install 실행
for package_dir in "$PACKAGES_DIR"/*; do
  if [ -d "$package_dir" ]; then
    package_name=$(basename "$package_dir")
    echo "=========================================="
    echo "패키지: $package_name"
    echo "디렉토리: $package_dir"
    echo "=========================================="
    
    cd "$package_dir" || continue
    
    # package.json이 존재하는지 확인
    if [ ! -f "package.json" ]; then
      echo "⚠ $package_name: package.json이 없습니다. 건너뜁니다."
    else
      # pnpm install 실행
      pnpm install
      
      if [ $? -eq 0 ]; then
        echo "✓ $package_name: pnpm install 완료"
      else
        echo "✗ $package_name: pnpm install 실패"
      fi
    fi
    
    echo ""
  fi
done

echo "모든 패키지에 대한 pnpm install이 완료되었습니다."

