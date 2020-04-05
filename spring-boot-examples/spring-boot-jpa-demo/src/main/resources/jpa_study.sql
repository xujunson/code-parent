
DROP TABLE IF EXISTS `worker`;
CREATE TABLE `worker` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` char(64) NOT NULL DEFAULT '' COMMENT '员工类型',
  `name` char(64) NOT NULL DEFAULT '' COMMENT '姓名',
  `salary` bigint(20) unsigned DEFAULT '0' COMMENT '薪水',
  `province` char(64) NOT NULL DEFAULT '' COMMENT '省份',
  `city` char(64) NOT NULL DEFAULT '' COMMENT '城市',
  PRIMARY KEY (`id`),
  KEY `city_nam_type_salary_idx` (`city`,`name`,`type`,`salary`),
  KEY `city_salary_idx` (`city`,`salary`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Records of worker
-- ----------------------------
INSERT INTO `worker` VALUES ('1', 'A', 'X', '10000', '北京市', '北京市');
INSERT INTO `worker` VALUES ('2', 'A', 'S', '15000', '北京市', '北京市');
INSERT INTO `worker` VALUES ('3', 'A', 'Y', '20000', '北京市', '北京市');
