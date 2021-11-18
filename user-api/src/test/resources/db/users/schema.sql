DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`
(
    id                 VARCHAR(50)  NOT NULL PRIMARY KEY COMMENT '아이디 (이메일)',
    password           VARCHAR(100) NOT NULL COMMENT '비밀번호',
    `name`             VARCHAR(50)  NOT NULL COMMENT '이름',
    info_email_address VARCHAR(50)  NOT NULL COMMENT '서비스 정보 수신 이메일주소',
    status             VARCHAR(10)  NOT NULL COMMENT '상태',
    created_at         DATETIME     NOT NULL COMMENT '생성 시간',
    updated_at         DATETIME     NOT NULL COMMENT '마지막 수정 시간'
);

DROP TABLE IF EXISTS `auth_code`;
CREATE TABLE IF NOT EXISTS `auth_code`
(
    user_id    VARCHAR(50) NOT NULL COMMENT 'user.id',
    purpose    VARCHAR(50) NOT NULL COMMENT '사용 목적',
    code       VARCHAR(50) NOT NULL COMMENT '인증 코드',
    created_at DATETIME    NOT NULL COMMENT '생성 시각',
    PRIMARY KEY (`user_id`, purpose)
);
