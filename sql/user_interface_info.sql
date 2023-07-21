CREATE TABLE IF NOT EXISTS my_db2.`user_interface_info`
(
    `id`              BIGINT                             NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `userId`          bigint                             not null COMMENT '调用用户Id',
    `interfaceInfoId` bigint                             not null COMMENT '接口Id',
    `totalNum`        int      default 0                 not null comment '总调用次数',
    `leftNum`         int      default 0                 not null comment '剩余调用次数',
    `status`          int      default 0                 not null comment '0-正常,1-禁用',
    `createTime`      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updateTime`      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`        TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否删除(0-未删, 1-已删)'
) COMMENT '用户调用接口关系表';