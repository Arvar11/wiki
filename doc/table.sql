-- 删除已存在的表
drop table if exists `doc`;

-- 创建文档表（增强版）
create table `doc` (
                       `id` bigint not null comment 'ID',
                       `ebook_id` bigint not null default 0 comment '电子书ID',
                       `parent` bigint not null default 0 comment '父ID',
                       `name` varchar(50) not null comment '名称',
                       `sort` int comment '顺序',
                       `view_count` int default 0 comment '阅读数',
                       `vote_count` int default 0 comment '点赞数',
                       `created_time` datetime default current_timestamp comment '创建时间',
                       `updated_time` datetime default current_timestamp on update current_timestamp comment '更新时间',
                       `deleted` tinyint(1) default 0 comment '删除标识（0-未删除，1-已删除）',
                       primary key (`id`),
                       key `idx_ebook_id` (`ebook_id`),
                       key `idx_parent` (`parent`),
                       key `idx_sort` (`sort`)
) engine=innodb default charset=utf8mb4 comment='文档表';

-- 插入测试数据
-- 插入文档数据
insert into `doc` (`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`)
values (1, 1, 0, '文档1', 1, 0, 0);

insert into `doc` (`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`)
values (2, 1, 1, '文档1.1', 1, 0, 0);

insert into `doc` (`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`)
values (3, 1, 0, '文档2', 2, 0, 0);

insert into `doc` (`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`)
values (4, 1, 3, '文档2.1', 1, 0, 0);

insert into `doc` (`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`)
values (5, 1, 3, '文档2.2', 2, 0, 0);

insert into `doc` (`id`, `ebook_id`, `parent`, `name`, `sort`, `view_count`, `vote_count`)
values (6, 1, 5, '文档2.2.1', 1, 0, 0);