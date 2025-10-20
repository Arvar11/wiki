DROP TABLE IF EXISTS `ebook_snapshot`;  -- 如果表已存在则删除，确保重新创建

CREATE TABLE `ebook_snapshot` (
                                  `id` BIGINT AUTO_INCREMENT NOT NULL COMMENT '主键ID，自增长',  -- 唯一标识每条快照记录
                                  `ebook_id` BIGINT NOT NULL DEFAULT 0 COMMENT '电子书ID，关联ebook表',  -- 记录快照对应的电子书
                                  `date` DATE NOT NULL COMMENT '快照日期，格式YYYY-MM-DD',  -- 记录快照的日期，用于时间序列分析
                                  `view_count` INT NOT NULL DEFAULT 0 COMMENT '阅读总数（截止到快照日期）',  -- 累计阅读量
                                  `vote_count` INT NOT NULL DEFAULT 0 COMMENT '点赞总数（截止到快照日期）',  -- 累计点赞量
                                  `view_increase` INT NOT NULL DEFAULT 0 COMMENT '日阅读增长量',  -- 与前一日相比的阅读增长数
                                  `vote_increase` INT NOT NULL DEFAULT 0 COMMENT '日点赞增长量',  -- 与前一日相比的点赞增长数
                                  PRIMARY KEY (`id`),  -- 主键索引，保证记录唯一性
                                  UNIQUE KEY `ebook_id_date_unique` (`ebook_id`, `date`)  -- 唯一约束：确保同一电子书同一天只有一条快照记录
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='电子书快照表 - 用于统计分析和报表生成';