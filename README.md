# [실행 전 준비]

## commit-msg 템플릿 설정
- .git 디렉토리로 .gitmessage.txt 파일 복사
- git에 템플릿으로 적용 `git config --global commit.template .git/.gitmessage.txt`

### IntelliJ에 적용
- File>Settings>Plugins>Commit message Template 설치
- File>Tools>Commit Message Template>Load Template File 에서 .git/.gitmessage.txt 선택

## git hooks 등록
- .git/hooks 디렉토리로 commit-msg 파일 복사
- 파일에 권한부여 `chmod +x commit-msg`

## Google Java Sytle Guide Convention 설정  
- File>Settings>Code Style>Java>Import Scheme>IntelliJ IDEA code style scheme
