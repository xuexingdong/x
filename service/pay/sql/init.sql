CREATE TABLE IF NOT EXISTS x_pay_pay_type
(
    id          BIGINT PRIMARY KEY,
    code        VARCHAR(50),
    name        VARCHAR(255),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE TABLE IF NOT EXISTS x_pay_pay_channel
(
    id          BIGINT PRIMARY KEY,
    code        VARCHAR(50),
    name        VARCHAR(255),
    config      TEXT,
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE TABLE IF NOT EXISTS x_pay_pay_channel_pay_type
(
    id               BIGINT PRIMARY KEY,
    pay_channel_code VARCHAR(50),
    pay_type_code    VARCHAR(50),
    create_time      TIMESTAMP NOT NULL,
    update_time      TIMESTAMP NOT NULL,
    deleted          BOOLEAN   NOT NULL
);

-- 索引优化
CREATE INDEX IF NOT EXISTS idx_channel_code ON x_pay_channel_pay_type (channel_code);
CREATE INDEX IF NOT EXISTS idx_pay_type_code ON x_pay_channel_pay_type (pay_type_code);
CREATE UNIQUE INDEX IF NOT EXISTS uk_channel_pay_type
    ON x_pay_channel_pay_type (channel_code, pay_type_code)
    WHERE deleted = false;

CREATE TABLE IF NOT EXISTS x_pay_app
(
    id          BIGINT PRIMARY KEY,
    appid       VARCHAR(255),
    name        VARCHAR(255),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_appid ON x_pay_app (appid);
CREATE INDEX IF NOT EXISTS idx_create_time ON x_pay_app (create_time);
CREATE INDEX IF NOT EXISTS idx_update_time ON x_pay_app (update_time);

CREATE TABLE IF NOT EXISTS x_pay_merchant
(
    id          BIGINT PRIMARY KEY,
    appid       VARCHAR(255),
    mchid       VARCHAR(255),
    name        VARCHAR(255),
    out_mchid   VARCHAR(50),
    create_time TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL,
    deleted     BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_appid ON x_pay_merchant (appid);
CREATE INDEX IF NOT EXISTS idx_mchid ON x_pay_merchant (mchid);
CREATE INDEX IF NOT EXISTS idx_out_mchid ON x_pay_merchant (out_mchid);
CREATE INDEX IF NOT EXISTS idx_create_time ON x_pay_merchant (create_time);
CREATE INDEX IF NOT EXISTS idx_update_time ON x_pay_merchant (update_time);

CREATE TABLE IF NOT EXISTS x_pay_merchant_pay_channel
(
    id               BIGINT PRIMARY KEY,
    mchid            VARCHAR(255),
    pay_channel_code VARCHAR(255),
    config           TEXT,
    create_time      TIMESTAMP NOT NULL,
    update_time      TIMESTAMP NOT NULL,
    deleted          BOOLEAN   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_mchid ON x_pay_merchant_pay_channel (mchid);
CREATE INDEX IF NOT EXISTS idx_pay_channel_code ON x_pay_merchant_pay_channel (pay_channel_code);
CREATE INDEX IF NOT EXISTS idx_create_time ON x_user (create_time);
CREATE INDEX IF NOT EXISTS idx_update_time ON x_user (update_time);

CREATE TABLE IF NOT EXISTS x_pay_merchant_pay_channel_router
(
    id               BIGINT PRIMARY KEY,
    mchid            VARCHAR(255) NOT NULL,
    pay_type_code    VARCHAR(255),
    pay_channel_code VARCHAR(255),
    create_time      TIMESTAMP    NOT NULL,
    update_time      TIMESTAMP    NOT NULL,
    deleted          BOOLEAN      NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_mchid ON x_pay_merchant_pay_channel_router (mchid);
CREATE INDEX IF NOT EXISTS idx_pay_type_code ON x_pay_merchant_pay_channel_router (pay_type_code);
CREATE INDEX IF NOT EXISTS idx_pay_channel_code ON x_pay_merchant_pay_channel_router (pay_channel_code);
CREATE INDEX IF NOT EXISTS idx_create_time ON x_user (create_time);
CREATE INDEX IF NOT EXISTS idx_update_time ON x_user (update_time);

CREATE TABLE IF NOT EXISTS x_pay_pay_order
(
    id                       BIGINT PRIMARY KEY,
    mchid                    VARCHAR(255)   NOT NULL,
    appid                    VARCHAR(255)   NOT NULL,
    pay_order_no             VARCHAR(255)   NOT NULL,
    out_trade_no             VARCHAR(255)   NOT NULL,
    total_amount             DECIMAL(20, 2) NOT NULL,
    refunded_amount          DECIMAL(20, 2),
    trans_mode               VARCHAR(255),
    auth_code                VARCHAR(255),
    sub_appid                VARCHAR(255),
    sub_openid               VARCHAR(255),
    pay_type_code            VARCHAR(32)    NOT NULL,
    pay_type_name            VARCHAR(32)    NOT NULL,
    pay_channel_code         VARCHAR(32)    NOT NULL,
    pay_channel_name         VARCHAR(32)    NOT NULL,
    trade_time               TIMESTAMP      NOT NULL,
    time_expire              TIMESTAMP,
    subject                  VARCHAR(255),
    description              VARCHAR(255),
    custom_data              TEXT,
    pay_status               VARCHAR(32)    NOT NULL,
    polling_start_time       TIMESTAMP,
    pay_time                 TIMESTAMP,
    third_trade_no           VARCHAR(255),
    qr_code                  VARCHAR(1024),
    client_pay_invoke_params TEXT,
    create_time              TIMESTAMP      NOT NULL,
    update_time              TIMESTAMP      NOT NULL,
    deleted                  Boolean        NOT NULL,
    INDEX                    idx_mchid(mchid),
    INDEX                    idx_appid(appid),
    INDEX                    idx_pay_order_no(pay_order_no),
    INDEX                    idx_out_trade_no(out_trade_no),
    INDEX                    idx_trade_time(trade_time),
    INDEX                    idx_time_expire(time_expire),
    INDEX                    idx_pay_time(pay_time),
    INDEX                    idx_third_trade_no(third_trade_no)
);
CREATE INDEX IF NOT EXISTS idx_create_time ON x_user (create_time);
CREATE INDEX IF NOT EXISTS idx_update_time ON x_user (update_time);

CREATE TABLE IF NOT EXISTS x_pay_refund_order
(
    id                 BIGINT PRIMARY KEY,
    mchid              VARCHAR(255)   NOT NULL,
    appid              VARCHAR(255)   NOT NULL,
    pay_order_no       VARCHAR(255)   NOT NULL,
    out_trade_no       VARCHAR(255)   NOT NULL,
    refund_order_no    VARCHAR(255)   NOT NULL,
    out_refund_no      VARCHAR(255)   NOT NULL,
    total_amount       DECIMAL(20, 2) NOT NULL,
    refund_amount      DECIMAL(20, 2) NOT NULL,
    refund_reason      VARCHAR(255),
    pay_type_code      VARCHAR(32)    NOT NULL,
    pay_type_name      VARCHAR(32)    NOT NULL,
    pay_channel_code   VARCHAR(32)    NOT NULL,
    pay_channel_name   VARCHAR(32)    NOT NULL,
    refund_status      VARCHAR(32)    NOT NULL,
    polling_start_time TIMESTAMP,
    refund_time        TIMESTAMP,
    third_trade_no     VARCHAR(255),
    create_time        TIMESTAMP      NOT NULL,
    update_time        TIMESTAMP      NOT NULL,
    deleted            Boolean        NOT NULL,
    INDEX              idx_mchid(mchid),
    INDEX              idx_appid(appid),
    INDEX              idx_pay_order_no(pay_order_no),
    INDEX              idx_out_trade_no(out_trade_no),
    INDEX              idx_refund_order_no(refund_order_no),
    INDEX              idx_out_refund_no(out_refund_no),
    INDEX              idx_refund_time(refund_time),
    INDEX              idx_third_trade_no(third_trade_no)
);

CREATE TABLE IF NOT EXISTS x_pay_order_log
(
    id              BIGINT PRIMARY KEY,
    mchid           VARCHAR(255),
    appid           VARCHAR(255),
    pay_order_no    VARCHAR(255),
    refund_order_no VARCHAR(255),
    req             TEXT,
    resp            TEXT,
    create_time     TIMESTAMP NOT NULL,
    update_time     TIMESTAMP NOT NULL,
    deleted         Boolean   NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_pay_order_no ON x_pay_order_log (pay_order_no);
CREATE INDEX IF NOT EXISTS idx_refund_order_no ON x_pay_order_log (refund_order_no);


CREATE TABLE t_leaf_alloc
(
    biz_tag     varchar(128) NOT NULL DEFAULT '',
    max_id      bigint(20)   NOT NULL DEFAULT '1',
    step        int(11)      NOT NULL,
    description varchar(256)          DEFAULT NULL,
    update_time timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (biz_tag)
) ENGINE = InnoDB;

insert into t_leaf_alloc(biz_tag, max_id, step, description, update_time)
values ('x_pay_app', 1, 100, '', NOW()),
       ('x_pay_merchant', 1, 100, '', NOW()),
       ('x_pay_merchant_pay_channel', 1, 100, '', NOW()),
       ('x_pay_merchant_pay_channel_router', 1, 100, '', NOW()),
       ('x_pay_pay_order', 1, 100, '', NOW()),
       ('x_pay_refund_order', 1, 100, '', NOW()),
       ('x_pay_order_log', 1, 100, '', NOW());
