DROP TABLE IF EXISTS `ebook_snapshot`;
CREATE TABLE `ebook_snapshot` (
  `id` BIGINT AUTO_INCREMENT NOT NULL COMMENT 'ID',
  `ebook_id` BIGINT NOT NULL DEFAULT 0 COMMENT '电子书ID',
  `snapshot_date` DATE NOT NULL COMMENT '快照日期',
  `view_count` INT NOT NULL DEFAULT 0 COMMENT '阅读数',
  `vote_count` INT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `view_increase` INT NOT NULL DEFAULT 0 COMMENT '阅读增长',
  `vote_increase` INT NOT NULL DEFAULT 0 COMMENT '点赞增长',
  `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ebook_date` (`ebook_id`, `snapshot_date`),
  KEY `idx_snapshot_date` (`snapshot_date`),
  KEY `idx_ebook_id` (`ebook_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子书快照表';