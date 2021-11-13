CREATE TABLE IF NOT EXISTS `user`
(
    id                 VARCHAR(50)  NOT NULL PRIMARY KEY COMMENT '아이디 (이메일)',
    password           VARCHAR(100) NOT NULL COMMENT '비밀번호',
    `name`             VARCHAR(50)  NOT NULL COMMENT '이름',
    info_email_address VARCHAR(50)  NOT NULL COMMENT '서비스 정보 수신 이메일주소',
    created_at         DATETIME     NOT NULL COMMENT '생성 시간',
    updated_at         DATETIME     NOT NULL COMMENT '마지막 수정 시간'
);

CREATE TABLE IF NOT EXISTS `profile` (
    user_id VARCHAR(50) NOT NULL PRIMARY KEY COMMENT '아이디 (이메일)',
    nick_name VARCHAR(50) COMMENT '닉네임',
    icon_image_url VARCHAR(100) COMMENT '아이콘 이미지 URL',
    intro VARCHAR(200) COMMENT '소개, 약력',
    hobby_list VARCHAR(100) COMMENT '취미 목록',
    updated_at DATETIME NOT NULL COMMENT '마지막 수정 시간'
);
