CREATE DATABASE IF NOT EXISTS `example`;

CREATE TABLE IF NOT EXISTS `example`.`extra_ad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(48) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='额外的表';

TRUNCATE TABLE extra_ad;
