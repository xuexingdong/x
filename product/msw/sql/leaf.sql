CREATE TABLE t_leaf_alloc
(
    biz_tag     VARCHAR(128) NOT NULL DEFAULT '',
    max_id      BIGINT       NOT NULL DEFAULT 1,
    step        INTEGER      NOT NULL,
    description VARCHAR(256)          DEFAULT NULL,
    update_time TIMESTAMP    NOT NULL DEFAULT NOW(),
    PRIMARY KEY (biz_tag)
);

insert into t_leaf_alloc(biz_tag, max_id, step, description, update_time)
values ('x_msw_mob', 1, 100, '', NOW()),
       ('x_msw_item', 1, 100, '', NOW()),
       ('x_msw_mob_item', 1, 100, '', NOW());