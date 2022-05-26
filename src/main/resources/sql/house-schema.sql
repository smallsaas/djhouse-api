SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `t_house_property_building`;
CREATE TABLE `t_house_property_building`  (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`org_id` bigint(20) NOT NULL DEFAULT 1 COMMENT '租户数据隔离',
`community` varchar(50) NOT NULL COMMENT '社区',
`community_code` VARCHAR(50) DEFAULT NULL COMMENT '社区编号',
`area` VARCHAR(50) DEFAULT NULL COMMENT '区域',
`code` varchar(50) NOT NULL COMMENT '楼栋编号',
`floors` INT DEFAULT NULL COMMENT '楼栋层数',
`units` INT DEFAULT NULL COMMENT '每层单元数',
PRIMARY KEY (`id`),
UNIQUE(`community`,`code`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_house_property_unit`;
CREATE TABLE `t_house_property_unit`  (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` bigint(20) NOT NULL UNIQUE COMMENT '用户id',
`building_id` bigint(20) NOT NULL COMMENT '楼栋id',
`number` varchar(50) NOT NULL COMMENT '房产编号',
PRIMARY KEY (`id`),
unique(`building_id`, `number`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;