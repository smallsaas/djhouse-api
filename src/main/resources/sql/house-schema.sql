SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `t_house_property_building`;
CREATE TABLE `t_house_property_building`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `org_id`         bigint(20) NOT NULL DEFAULT 1 COMMENT '租户数据隔离',
    `community`      varchar(50) NOT NULL COMMENT '社区',
    `community_code` VARCHAR(50) DEFAULT NULL COMMENT '社区编号',
    `area`           VARCHAR(50) DEFAULT NULL COMMENT '区域',
    `code`           varchar(50) NOT NULL COMMENT '楼栋编号',
    `floors`         INT         DEFAULT NULL COMMENT '楼栋层数',
    `units`          INT         DEFAULT NULL COMMENT '每层单元数',
    PRIMARY KEY (`id`),
    UNIQUE (`community`, `code`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_house_property_building_unit`;
CREATE TABLE `t_house_property_building_unit`(
                                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                 `building_id` bigint(20) NOT NULL COMMENT '楼栋id',
                                                 `number` varchar(50) NOT NULL COMMENT '房产编号',
                                                 `house_type` VARCHAR(50) DEFAULT NULL COMMENT '户型',
                                                 `house_type_picture` VARCHAR(255) DEFAULT NULL COMMENT '户型图',
                                                 `area` DECIMAL(10,2) DEFAULT 0 COMMENT '面积',
                                                 PRIMARY KEY (`id`),
                                                 unique(`building_id`, `number`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_house_property_user_unit`;
CREATE TABLE `t_house_property_user_unit`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `unit_id` bigint(20) NOT NULL COMMENT '单元id',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_house_decorate_plan`;
CREATE TABLE `t_house_decorate_plan`
(
    `id`                  BIGINT NOT NULL auto_increment,
    `decorate_style_name` VARCHAR(50)    DEFAULT NULL COMMENT '装修风格名称',
    `allow_changed`       int            DEFAULT 1 COMMENT '装修风格是否可变，0是不可变 1是可变',
    `color_style`         VARCHAR(50)    DEFAULT NULL COMMENT '颜色格调',
    `total_budget`        DECIMAL(10, 2) DEFAULT 0 COMMENT '总预算',
    `cover`               varchar(200) NULL DEFAULT NULL COMMENT '封面',
    `merchant`            varchar(200) NULL DEFAULT NULL COMMENT '商家名称',
    `star`                INT            DEFAULT 0 COMMENT '人气值',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_house_vr_picture`;
CREATE TABLE `t_house_vr_picture`
(
    `id`               BIGINT       NOT NULL auto_increment,
    `decorate_plan_id` BIGINT       NOT NULL COMMENT '装修计划id',
    `name`             VARCHAR(50) DEFAULT NULL COMMENT 'vr图名',
    `vr_address`       VARCHAR(255) NOT NULL COMMENT 'vr图地址',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_house_decorate_plan_funiture`;
CREATE TABLE `t_house_decorate_plan_funiture`
(
    `id`               BIGINT NOT NULL auto_increment,
    `decorate_plan_id` BIGINT NOT NULL COMMENT '装修计划id',
    `furniture_id`     BIGINT NOT NULL COMMENT '家居id',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_house_user_decorate_plan`;
CREATE TABLE `t_house_user_decorate_plan`
(
    `id`                  BIGINT NOT NULL auto_increment,
    `user_id`             BIGINT NOT NULL COMMENT '用户id',
    `decorate_plan_id`    BIGINT NOT NULL COMMENT '装修计划id',
    `funiture_number`     INT          DEFAULT 1 COMMENT '家居数量，默认为1',
    `change_option`       VARCHAR(30)  DEFAULT NULL COMMENT '替换：replace 移除remove',
    `funiture_id`         BIGINT       DEFAULT NULL COMMENT '替换或者移除的家居id',
    `changed_funiture_id` BIGINT       DEFAULT NULL COMMENT '替换后的家居id 移除家居为空',
    `note`                VARCHAR(100) DEFAULT NULL COMMENT '替换或者移除说明',
    unique (`user_id`, `decorate_plan_id`, `funiture_id`),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--


DROP TABLE IF EXISTS `t_house_user_decorate_address`;
CREATE TABLE `t_house_user_decorate_address`
(
    `id`               BIGINT NOT NULL auto_increment,
    `user_id`          BIGINT NOT NULL COMMENT '用户id',
    `decorate_plan_id` BIGINT NOT NULL COMMENT '装修计划id',
    `unit_id`          BIGINT DEFAULT NULL COMMENT 'unitId',
    unique (`user_id`, `decorate_plan_id`),
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product`
(
    `id`                        int(11) NOT NULL AUTO_INCREMENT,
    `category_id`               int(11) NOT NULL,
    `brand_id`                  int(11) NULL DEFAULT NULL,
    `name`                      varchar(100)   NOT NULL,
    `short_name`                varchar(100)   NOT NULL,
    `cover`                     varchar(200) NULL DEFAULT NULL,
    `stock_balance`             int(11) NULL DEFAULT 0,
    `sales`                     int(11) NOT NULL DEFAULT 0,
    `status`                    varchar(50)    NOT NULL,
    `created_date`              datetime NULL DEFAULT NULL,
    `last_modified_date`        datetime NULL DEFAULT NULL,
    `unit`                      varchar(50) NULL DEFAULT NULL,
    `price`                     decimal(10, 2) NOT NULL DEFAULT 0.00,
    `cost_price`                decimal(10, 2) NOT NULL DEFAULT 0.00,
    `suggested_price`           decimal(10, 2) NOT NULL DEFAULT 0.00,
    `promoted`                  int(11) NOT NULL DEFAULT 0,
    `freight`                   decimal(10, 2) NOT NULL DEFAULT 0.00,
    `free_shipping`             int(11) NOT NULL DEFAULT 0,
    `sort_order`                int(11) NULL DEFAULT 100,
    `partner_level_zone`        int(11) NULL DEFAULT NULL,
    `view_count`                bigint(20) NULL DEFAULT 0,
    `fare_id`                   int(11) NULL DEFAULT NULL,
    `barcode`                   varchar(100) NULL DEFAULT NULL,
    `store_location`            varchar(200) NULL DEFAULT NULL,
    `weight`                    int(11) NULL DEFAULT 0,
    `bulk`                      int(11) NULL DEFAULT 0,
    `sku_id`                    varchar(50) NULL DEFAULT NULL,
    `sku_name`                  varchar(50) NULL DEFAULT NULL,
    `sku_code`                  varchar(50) NULL DEFAULT NULL,
    `bar_code`                  varchar(255) NULL DEFAULT NULL,
    `mid`                       int(11) NULL DEFAULT NULL COMMENT '商家id',
    `allow_coupon`              int(11) NULL DEFAULT 0 COMMENT '优惠活动-优惠券',
    `credit`                    int(11) NULL DEFAULT 0 COMMENT '优惠活动-可用积分',
    `is_virtual`                int(11) NULL DEFAULT 0 COMMENT '是否虚拟产品',
    `required_participate_exam` int(11) NULL DEFAULT 0 COMMENT '是否需要做了检测才可以购买',
    `distribution_price`        decimal(10, 2) NULL DEFAULT 0.00 COMMENT '分销价',
    `presale`                   int(11) NOT NULL DEFAULT 0 COMMENT '0',
    `org_id`                    bigint(20) NULL DEFAULT NULL COMMENT 'org_id',
    `banner`                    varchar(250) NULL DEFAULT NULL COMMENT '产品banner图',
    `region`                    text NULL COMMENT '产品销售地区, 省-市-区,省-市,省 格式',
    `description`               text NULL COMMENT '图片链接',
    PRIMARY KEY (`id`),
    INDEX                       `category_id`(`category_id`),
    INDEX                       `fare_id`(`fare_id`),
    INDEX                       `brand_id`(`brand_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
