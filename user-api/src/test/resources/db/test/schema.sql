CREATE TABLE IF NOT EXISTS test_user
(
    id          INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nick_name` VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS `user`
(
    id                 VARCHAR(50)  NOT NULL PRIMARY KEY COMMENT '아이디 (이메일)',
    password           VARCHAR(100) NOT NULL COMMENT '비밀번호',
    `name`             VARCHAR(50)  NOT NULL COMMENT '이름',
    info_email_address VARCHAR(50)  NOT NULL COMMENT '서비스 정보 수신 이메일주소',
    created_at         DATETIME     NOT NULL COMMENT '생성 시간',
    updated_at         DATETIME     NOT NULL COMMENT '마지막 수정 시간'
);
