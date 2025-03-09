# [실행 전 준비]

## git hooks 등록
- .git/hooks 디렉토리로 commit-msg 파일 복사
- 파일에 권한부여 `chmod +x commit-msg`

## commit-msg 템플릿 설정
- 커밋 메시지 설정 쉘 스크립트 실행
  `./setup_git_hooks.sh start`

### IntelliJ에 적용
- File>Settings>Plugins>Commit message Template 설치
- File>Tools>Commit Message Template>Load Template File 에서 COMMIT_TEMPLATE.txt 선택

### 커밋 메시지 구조

```
[{commit-type}] {title}
{body}
{footer}
------------------------------------
ex.
[feat] 메인 배너 라우팅 변경
메인 배너의 라우팅 경로가 변경되어 수정 후 반영
close #13, resolve #14, #15, bugfix #16, #17
[QA-259], [QA-270]
```

커밋 타입(commit-type)

- feat: 새로운 기능 추가
- fix: 버그 수정
- docs : 문서 내용 변경
- style : 포멧, 세미클론 수정과 같은 스타일 관련 수정
- refactor : 리팩토링 코드
- test: 테스트 코드
- chore : build task 수정, 프로젝트 매니저 설정 수정 등

제목(title)

- 50자 이내로 작성

본문(body) 선택 사항

- 부연/자세한 설명
- 100자 미만 권장

꼬리말(footer) 선택 사항

- git issue 연결
- Jira 티켓 연동

## Google Java Style Guide Convention 설정
- File>Settings>Code Style>Java>Import Scheme>IntelliJ IDEA code style scheme

