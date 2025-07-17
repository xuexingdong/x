CREATE TABLE IF NOT EXISTS x_user
(
    id          BIGINT PRIMARY KEY,
    username    VARCHAR(50),
    password    VARCHAR(255),
    user_status INTEGER,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_username ON x_user (username);
CREATE INDEX IF NOT EXISTS idx_created_at ON x_user (created_at);
CREATE INDEX IF NOT EXISTS idx_updated_at ON x_user (updated_at);