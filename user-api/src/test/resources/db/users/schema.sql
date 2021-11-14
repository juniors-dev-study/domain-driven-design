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

CREATE TABLE IF NOT EXISTS `friend`
(
    user_id        VARCHAR(50) NOT NULL COMMENT '사용자 아이디',
    friend_user_id VARCHAR(50) NOT NULL COMMENT '친구 사용자 아이디',
    created_at     DATETIME    NOT NULL COMMENT '생성 시간',

    UNIQUE INDEX idx_user_id_friend_user_id (user_id, friend_user_id)
);

CREATE INDEX idx_user_id ON friend (user_id);

CREATE TABLE IF NOT EXISTS `friend_request`
(
    id           BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '친구요청 아이디',
    requester_id VARCHAR(50)     NOT NULL COMMENT '사용자 아이디',
    receiver_id  VARCHAR(50)     NOT NULL COMMENT '친구 사용자 아이디',
    created_at   DATETIME        NOT NULL COMMENT '생성 시간',

    UNIQUE INDEX idx_requester_id_receiver_id (requester_id, receiver_id)
);
