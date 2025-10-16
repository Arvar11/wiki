DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
                           `id` BIGINT NOT NULL COMMENT '文档ID',
                           `content` MEDIUMTEXT NOT NULL COMMENT '内容',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文档内容';