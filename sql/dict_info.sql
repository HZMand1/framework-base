DROP TABLE `dict_info`;
CREATE TABLE `dict_info` (
	`id` VARCHAR (50) NOT NULL COMMENT 'id',
	`pid` VARCHAR (50) DEFAULT '0' COMMENT ' 父ID ',
	`type` VARCHAR (50) NOT NULL COMMENT '类别',
	`code` VARCHAR (50) NOT NULL COMMENT ' 编码 ',
	`value` VARCHAR (200) NOT NULL COMMENT ' 数据名称/值 ',
	`sort_no` INT (11) UNSIGNED DEFAULT '1' COMMENT ' 顺序 ',
	`status` TINYINT (2) DEFAULT '0' COMMENT '0正常,1删除',
	`description` VARCHAR (400) DEFAULT NULL COMMENT '数据描述',
	`string1` VARCHAR (200) NOT NULL COMMENT ' 预留字段1 ',
	`string2` VARCHAR (200) NOT NULL COMMENT ' 预留字段2 ',
	`string3` VARCHAR (200) NOT NULL COMMENT ' 预留字段3 ',
	`update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `dict_info` (`id`),
	KEY `idx_dc_dt` (`type`, `code`)
) ENGINE = INNODB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8 COMMENT = '系统字典表';

