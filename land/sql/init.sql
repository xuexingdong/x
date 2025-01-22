CREATE TABLE `land_paper`
(
    `id`             BIGINT       NOT NULL PRIMARY KEY,
    `out_paper_id`   VARCHAR(255) NOT NULL,
    `name`           VARCHAR(255),
    `question_count` INT,
    `create_time`    DATETIME     NOT NULL,
    `update_time`    DATETIME     NOT NULL,
    `is_deleted`     TINYINT(1)   NOT NULL
);

CREATE TABLE `land_material`
(
    `id`              BIGINT       NOT NULL PRIMARY KEY,
    `out_material_id` VARCHAR(255) NOT NULL,
    `content`         TEXT,
    `paper_id`        BIGINT,
    `index_status`    VARCHAR(50),
    `create_time`     DATETIME     NOT NULL,
    `update_time`     DATETIME     NOT NULL,
    `is_deleted`      TINYINT(1)   NOT NULL,
    INDEX `idx_paper_id` (`paper_id`)
);

CREATE TABLE `land_question`
(
    `id`              BIGINT       NOT NULL PRIMARY KEY,
    `out_question_id` VARCHAR(255) NOT NULL,
    `content`         TEXT,
    `chapter_name`    VARCHAR(255),
    `module`          INT,
    `option_type`     INT,
    `options`         TEXT,
    `answer`          TEXT,
    `material_ids`    TEXT,
    `paper_id`        BIGINT,
    `create_time`     DATETIME     NOT NULL,
    `update_time`     DATETIME     NOT NULL,
    `is_deleted`      TINYINT(1)   NOT NULL,
    INDEX `idx_paper_id` (`paper_id`)
);

CREATE TABLE `land_idiom`
(
    `id`                BIGINT     NOT NULL PRIMARY KEY,
    `word`              VARCHAR(255),
    `pinyin`            VARCHAR(255),
    `emotion`           TINYINT,
    `synonyms`          TEXT,
    `antonyms`          TEXT,
    `meaning`           TEXT,
    `origin`            TEXT,
    `usage`             TEXT,
    `example`           TEXT,
    `distinction`       TEXT,
    `story`             TEXT,
    `explanation`       TEXT,
    `example_sentences` TEXT,
    `last_update_time`  DATETIME,
    `create_time`       DATETIME   NOT NULL,
    `update_time`       DATETIME   NOT NULL,
    `is_deleted`        TINYINT(1) NOT NULL,
    INDEX `idx_word` (`word`)
);

CREATE TABLE `land_idiom_question_relation`
(
    `id`          BIGINT     NOT NULL PRIMARY KEY,
    `idiom`       VARCHAR(255),
    `question_id` BIGINT,
    `create_time` DATETIME   NOT NULL,
    `update_time` DATETIME   NOT NULL,
    `is_deleted`  TINYINT(1) NOT NULL,
    INDEX `idx_idiom` (`idiom`),
    INDEX `idx_question_id` (`question_id`)
);

insert into t_leaf_alloc(biz_tag, max_id, step, description, update_time)
values ('land_paper', 1, 2000, '', NOW()),
       ('land_material', 1, 2000, '', NOW()),
       ('land_question', 1, 2000, '', NOW()),
       ('land_idiom', 1, 2000, '', NOW()),
       ('land_idiom_question_relation', 1, 2000, '', NOW());