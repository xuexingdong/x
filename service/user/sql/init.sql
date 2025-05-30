CREATE TABLE IF NOT EXISTS x_user
(
    id          BIGINT PRIMARY KEY,
    username    VARCHAR(50),
    password    VARCHAR(255),
    user_status INTEGER,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_username ON x_user (username);
CREATE INDEX IF NOT EXISTS idx_create_time ON x_user (create_time);
CREATE INDEX IF NOT EXISTS idx_update_time ON x_user (update_time);