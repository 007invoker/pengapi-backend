-- 创建库
create database if not exists my_db2;

-- 切换库
use my_db2;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userAvatar   varchar(1024)                          null comment '用户头像',
    gender       tinyint                                null comment '性别',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    userPassword varchar(512)                           not null comment '密码',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '用户';

-- 帖子表
create table if not exists post
(
    id            bigint auto_increment comment 'id' primary key,
    age           int comment '年龄',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    education     varchar(512)                       null comment '学历',
    place         varchar(512)                       null comment '地点',
    job           varchar(512)                       null comment '职业',
    contact       varchar(512)                       null comment '联系方式',
    loveExp       varchar(512)                       null comment '感情经历',
    content       text                               null comment '内容（个人介绍）',
    photo         varchar(1024)                      null comment '照片地址',
    reviewStatus  int      default 0                 not null comment '状态（0-待审核, 1-通过, 2-拒绝）',
    reviewMessage varchar(512)                       null comment '审核信息',
    viewNum       int                                not null default 0 comment '浏览数',
    thumbNum      int                                not null default 0 comment '点赞数',
    userId        bigint                             not null comment '创建用户 id',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '帖子';


CREATE TABLE IF NOT EXISTS my_db2.`interface_info`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `name` VARCHAR(256) NOT NULL COMMENT '名称',
    `description` VARCHAR(256) NULL COMMENT '描述',
    `url` VARCHAR(512) NOT NULL COMMENT '接口地址',
    `requestHeader` TEXT NULL COMMENT '请求头',
    `responseHeader` TEXT NULL COMMENT '响应头',
    `requestParams` TEXT NULL COMMENT '请求参数',
    `status` INT DEFAULT 0 NOT NULL COMMENT '接口状态（0-关闭，1-开启）',
    `method` VARCHAR(256) NOT NULL COMMENT '请求类型',
    `userId` BIGINT NOT NULL COMMENT '创建人',
    `createTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updateTime` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete` TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除(0-未删, 1-已删)'
    ) COMMENT '接口信息';

INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('许擎宇', '薛聪健', 'www.cary-king.net', '潘博涛', '谭聪健', 0, '石炫明', 9500534531);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('陆弘文', '白志强', 'www.leslee-kuhn.net', '潘懿轩', '马鸿涛', 0, '陈峻熙', 3982575846);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('毛建辉', '罗文', 'www.rosaria-kilback.io', '冯子默', '彭哲瀚', 0, '赵远航', 121776355);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('彭雨泽', '蔡煜祺', 'www.norris-bergstrom.biz', '董思源', '田晓博', 0, '潘擎宇', 740);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('傅志强', '陈梓晨', 'www.jordan-reinger.com', '金志强', '熊锦程', 0, '邓睿渊', 35542559);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('吕黎昕', '孔越彬', 'www.fe-okon.info', '万伟宸', '林昊然', 0, '孟荣轩', 1445);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('夏雪松', '许子骞', 'www.lashawna-legros.co', '蔡昊然', '胡鹏涛', 0, '钟立辉', 34075514);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('严钰轩', '阎志泽', 'www.kay-funk.biz', '莫皓轩', '郭黎昕', 0, '龚天宇', 70956);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('萧嘉懿', '曹熠彤', 'www.margarette-lindgren.biz', '田泽洋', '邓睿渊', 0, '梁志强', 98);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('杜驰', '冯思源', 'www.vashti-auer.org', '黎健柏', '武博文', 0, '李伟宸', 9);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('史金鑫', '蔡鹏涛', 'www.diann-keebler.org', '徐烨霖', '阎建辉', 0, '李烨伟', 125);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('林炫明', '贾旭尧', 'www.dotty-kuvalis.io', '梁雨泽', '龙伟泽', 0, '许智渊', 79998);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('何钰轩', '赖智宸', 'www.andy-adams.net', '崔思淼', '白鸿煊', 0, '邵振家', 7167482751);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('魏志强', '于立诚', 'www.ione-aufderhar.biz', '朱懿轩', '万智渊', 0, '唐昊强', 741098);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('严君浩', '金胤祥', 'www.duane-boyle.org', '雷昊焱', '侯思聪', 0, '郝思', 580514);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('姚皓轩', '金鹏', 'www.lyda-klein.biz', '杜昊强', '邵志泽', 0, '冯鸿涛', 6546);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('廖驰', '沈泽洋', 'www.consuelo-sipes.info', '彭昊然', '邓耀杰', 0, '周彬', 7761037);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('赖智渊', '邓志泽', 'www.emerson-mann.co', '熊明哲', '贺哲瀚', 0, '田鹏', 381422);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('许涛', '陆致远', 'www.vella-ankunding.name', '贾哲瀚', '莫昊焱', 0, '袁越彬', 4218096);
INSERT INTO my_db2.`interface_info` (`name`, `description`, `url`, `requestHeader`, `responseHeader`, `status`, `method`, `userId`) VALUES ('吕峻熙', '沈鹏飞', 'www.shari-reichel.org', '郭鸿煊', '覃烨霖', 0, '熊黎昕', 493);