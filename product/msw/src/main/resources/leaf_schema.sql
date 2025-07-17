DROP TABLE t_leaf_alloc;
CREATE TABLE IF NOT EXISTS t_leaf_alloc
(
    biz_tag     VARCHAR(128) NOT NULL DEFAULT '',
    max_id      BIGINT       NOT NULL DEFAULT 1,
    step        INTEGER      NOT NULL,
    description VARCHAR(256)          DEFAULT NULL,
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (biz_tag)
);