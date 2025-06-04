CREATE TABLE IF NOT EXISTS x_msw_mob
(
    id                        BIGINT PRIMARY KEY,
    code                      VARCHAR(50),
    name                      VARCHAR(255),
    -- 基础属性模板
    level                     INTEGER,
    max_hp                    INTEGER,
    max_mp                    INTEGER,
    exp                       INTEGER,

    -- 战斗属性模板
    physical_defense          INTEGER,
    magical_defense           INTEGER,
    evasion                   INTEGER,
    base_accuracy_requirement INTEGER,
    accuracy_level_penalty    DECIMAL(20, 2),

    create_time               TIMESTAMP NOT NULL,
    update_time               TIMESTAMP NOT NULL,
    deleted                   BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_code ON x_msw_mob (code);

CREATE TABLE IF NOT EXISTS x_msw_item
(
    id          BIGINT PRIMARY KEY,
    code        VARCHAR(50),
    name        VARCHAR(255),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_code ON x_msw_item (code);

CREATE TABLE IF NOT EXISTS x_msw_mob_item
(
    id          BIGINT PRIMARY KEY,
    mob_code    VARCHAR(50),
    item_code   VARCHAR(50),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_mob_code ON x_msw_mob_item (mob_code);
CREATE INDEX IF NOT EXISTS idx_item_code ON x_msw_mob_item (item_code);
