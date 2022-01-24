DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment`
(
    id         INT           NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '아이디',
    root_type  VARCHAR(50)   NOT NULL COMMENT '대상글 유형',
    root_id    VARCHAR(50)   NOT NULL COMMENT '대상글 id',
    contents   VARCHAR(1000) NOT NULL COMMENT '내용',
    writer_id  VARCHAR(50)   NOT NULL COMMENT '작성자 id',
    created_at DATETIME      NOT NULL COMMENT '작성 시각',
    updated_at DATETIME      NOT NULL COMMENT '최종 수정시각'
);
