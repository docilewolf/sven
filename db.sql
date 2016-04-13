create table  `account`
(
       `id`              BIGINT(20) auto_increment comment '编号',
       `user_name`       VARCHAR(32),
       `password`        VARCHAR(32),
       `email`           VARCHAR(20),
       `mobile`          VARCHAR(20),
       `type`            INT(4) comment '1.管理员 2.普通用户',
       `status`          INT(4) comment '1.未认证 2.已认证 3.黑名单',
       `create_at`       BIGINT(20),
       `update_at`       BIGINT(20),
        primary key(id)
);

create table  `member`
(
       `id`              BIGINT(20) auto_increment  comment '编号',
       `account_id`      BIGINT(20) comment '账户id',
       `name`            VARCHAR(512),
       `head_img`        VARCHAR(512) comment '头像',
       `age`             INT(4),
       `gender`          INT(4) comment '0.未知 1.男 2.女',
       `mobile`          VARCHAR(20),
       `email`           VARCHAR(20),
       `signature`       VARCHAR(1024) comment '签名',
       `create_at`       BIGINT(20),
       `update_at`       BIGINT(20),
       primary key(id)
);

create table  `profile`
(
       `id`              BIGINT(20) auto_increment comment '编号',
       `name`            VARCHAR(256) comment '简介名称',
       `description`     VARCHAR(512) comment '简介描述',
       `img_url`          VARCHAR(512) COMMENT '图片url',
       `type`            INT(4) comment '类型 1.文章 2.图片',
       `create_at`       BIGINT(20),
       `isdel`           INT(4) DEFAULT 0,
       `essay_id`        BIGINT(20) COMMENT '文章id',
       `picture_id`        BIGINT(20) COMMENT '图片id',
       `category_id`     BIGINT(20) COMMENT '分类id',
       primary key(id)
);

create table  `essay`
(
       `id`              BIGINT(20)  auto_increment comment '编号',
       `name`            VARCHAR(256),
       `description`     VARCHAR(1024),
       `content`         Text,
       `category_id`     BIGINT(20),
       `create_at`       BIGINT(20),
       `update_at`       BIGINT(20),
       `isdel`           INT(4) DEFAULT 0,
       `is_markdown`      INT(4) DEFAULT 0
       primary key(id)
);

create table  `category`
(
       `id`              BIGINT(20) auto_increment comment '编号',
       `pid`             BIGINT(20) DEFAULT 0 comment '关联编号',
       `name`            VARCHAR(32) comment '名称',
       `create_at`       BIGINT(20),
       `type`            INT(4) comment '1.文章 2.图片',
       `isdel`           INT(4) DEFAULT 0,
        primary key(id)
);

create table  `picture`
(
       `id`              BIGINT(20) auto_increment comment '编号',
       `category_id`     BIGINT(20),
       `create_at`       BIGINT(20),
       `isdel`           INT(4) DEFAULT 0,
       primary key(id)
);

CREATE TABLE `picture_url`
(
       `id` BIGINT(20) AUTO_INCREMENT,
       `picture_id` BIGINT(20),
       `url`  VARCHAR(512) COMMENT '图片url',
       `create_at` BIGINT(20),
       `isdel`       INT(4) DEFAULT 0,
       PRIMARY KEY(id)
);

create table  `comment`
(
       `id`              BIGINT(20) auto_increment comment '编号',
       `essay_id`        BIGINT(20),
       `picture_id`      BIGINT(20),
       `comment_id`      BIGINT(20),
       `content`         Text,
       `from_account_id` BIGINT(20),
       `from_member_id`  BIGINT(20),
       `create_at`       BIGINT(20),
       `type`            INT(4) comment '11.文章评论 12.图片评论 20.留言 30.回复',
       `to_account_id`   BIGINT(20),
       `to_member_id`    BIGINT(20),
       `isdel`           INT(4) DEFAULT 0,
        primary key(id)
);