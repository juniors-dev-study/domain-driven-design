DROP TABLE IF EXISTS "reaction";
CREATE TABLE IF NOT EXISTS "reaction"
(
    "id"          INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    "target_type" VARCHAR(10) NOT NULL,
    "target_id"   INT         NOT NULL,
    "type"        VARCHAR(10) NOT NULL,
    "user_id"     VARCHAR(50) NOT NULL
);
