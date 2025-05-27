CREATE TABLE IF NOT EXISTS x_user
(
    id          BIGINT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    user_status INTEGER      NOT NULL,
    create_time TIMESTAMP    NOT NULL,
    update_time TIMESTAMP    NOT NULL,
    deleted     BOOLEAN      NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_username ON x_user (username);