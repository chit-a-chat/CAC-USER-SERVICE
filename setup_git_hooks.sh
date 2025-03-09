#!/bin/bash
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
# Git 템플릿 및 훅 경로 설정
TEMPLATE_PATH="$SCRIPT_DIR/COMMIT_TEMPLATE.txt"
HOOKS_PATH="$SCRIPT_DIR/.git/hooks"

# 로컬 config에서 template 설정 확인
LOCAL_TEMPLATE=$(git config --local --get commit.template)
LOCAL_HOOKS_PATH=$(git config --local --get core.hooksPath)

# 템플릿 설정 확인 및 추가/업데이트
if [ -z "$LOCAL_TEMPLATE" ]; then
    # 템플릿 설정이 없는 경우 추가
    git config --local commit.template "$TEMPLATE_PATH"
    echo "템플릿 설정이 추가되었습니다: $TEMPLATE_PATH"
else
    # 기존 템플릿과 비교
    if [ "$LOCAL_TEMPLATE" = "$TEMPLATE_PATH" ]; then
        echo "템플릿 설정 확인완료: $LOCAL_TEMPLATE"
    else
        # 다른 경우 업데이트
        git config --local commit.template "$TEMPLATE_PATH"
        echo "템플릿 설정이 업데이트되었습니다: $TEMPLATE_PATH"
    fi
fi

# 훅 경로 설정 확인 및 추가/업데이트
if [ -z "$LOCAL_HOOKS_PATH" ]; then
    # 훅 경로 설정이 없는 경우 추가
    git config --local core.hooksPath "$HOOKS_PATH"
    echo "훅 경로 설정이 추가되었습니다: $HOOKS_PATH"
else
    # 기존 훅 경로와 비교
    if [ "$LOCAL_HOOKS_PATH" = "$HOOKS_PATH" ]; then
        echo "훅 경로 설정 확인완료: $HOOKS_PATH"
    else
        # 다른 경우 업데이트
        git config --local core.hooksPath "$HOOKS_PATH"
        echo "훅 경로 설정이 업데이트되었습니다: $HOOKS_PATH"
    fi
fi

# 템플릿 및 훅 파일 확인
if [ ! -f "$TEMPLATE_PATH" ]; then
    echo "ERROR: 템플릿 파일을 찾을 수 없습니다: $TEMPLATE_PATH"
    exit 1
fi

if [ ! -d "$HOOKS_PATH" ]; then
    echo "ERROR: 훅 디렉토리를 찾을 수 없습니다: $HOOKS_PATH"
    exit 1
fi

echo "설정이 완료되었습니다!"
