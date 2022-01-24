CREATE TABLE IF NOT EXISTS `article`
(
    article_id     INT         NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'article id',
    image_urls     VARCHAR(1000) COMMENT '이미지 주소 리스트',
    body           VARCHAR(1000) COMMENT '본문',
    writer_user_id VARCHAR(50) NOT NULL COMMENT '작성자 ID',
    created_at     DATETIME    NOT NULL COMMENT '생성 시간',
    updated_at     DATETIME    NOT NULL COMMENT '마지막 수정 시간'
);

CREATE INDEX article_writer_user_id_index ON article (writer_user_id ASC);

