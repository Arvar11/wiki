drop table if exists `ebook`;
create table `ebook` (
                         `id` bigint not null comment 'id',
                         `name` varchar(50) comment '名称',
                         `category1_id` bigint comment '分类1',
                         `category2_id` bigint comment '分类2',
                         `description` varchar(200) comment '描述',
                         `cover` varchar(200) comment '封面',
                         `doc_count` int comment '文档数',
                         `view_count` int comment '阅读数',
                         `vote_count` int comment '点赞数',
                         primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='电子书';

insert into `ebook` (id, name, description) values (1, 'Spring Boot 入门教程', '零基础入门 Java 开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name, description) values (2, 'Vue 3 实战指南', '全面讲解 Vue 3 新特性及企业级项目实践');
insert into `ebook` (id, name, description) values (3, 'MySQL 数据库优化', '深入理解数据库原理，掌握性能优化技巧');
insert into `ebook` (id, name, description) values (4, '微服务架构设计', '从单体应用到微服务的完整转型指南');
insert into `ebook` (id, name, description) values (5, 'Docker 容器化部署', '容器化技术原理与实践，助力 DevOps 流程');
insert into `ebook` (id, name, description) values (6, 'Redis 深度历险', '高性能缓存与分布式存储的核心技术解析');