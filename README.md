# 책

- 제목 : 도메인 주도 설계
- 저자 : 에릭 에반스

# 진행 방식

- 매주 n 장 만큼 미리 읽는다.
- Issues 에 해당하는 장에 Comment 로 정리해서 기록한다. (자유)
- 매주 온라인으로 의견을 공유한다.

----

# 프로젝트 세팅

- spring boot 2.5.5
- spring 1.0.11.RELEASE
- java 11
- kotlin 1.15.31
- Spring Boot’s Gradle plugin requires Gradle 6.8, 6.9, or 7.x and can be used with Gradle’s configuration cache.

## IntelliJ IDEA 세팅

- Preference > Editor > File And Code Template > schema > project 로 세팅.
- Preference > Version Control > Git > 계정 선택.
- 터미널로도 계정설정필요!
    ```shell
    $ git config --local user.name {name}
    $ git config --local user.email {email}
    # 선택
    $ git config --global --unset user.name
    $ git config --global --unset user.email
    ```
- ktlint 설정
  ```shell
  ./gradlew addKtlintFormatGitPreCommitHook
  ```

## 로컬 DB

```shell
docker pull mysql:8.0
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password --name mysql -v {로컬저장소}:/var/lib/mysql mysql
```

서버따로 구매안해둔 상태라 로컬에서 띄우려면 mysql도 띄워야합니다 :)

## 접속확인

- [SWAGGER](http://localhost:10001/swagger-ui/index.html)
- [H2](http://localhost:10001/h2-console/)
