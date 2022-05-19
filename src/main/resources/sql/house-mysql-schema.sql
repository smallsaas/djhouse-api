
SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '供应商',
                               `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '介绍',
                               `type` int(11) NOT NULL DEFAULT 1 COMMENT '类型 0-软装修 1-家居家电装饰品提供商',
                               `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '供应商图片',
                               `address` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '供应商地址',
                               `org_id` bigint(20) NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '供应商（软装修/家居/家电/装饰品）' ROW_FORMAT = Dynamic;
