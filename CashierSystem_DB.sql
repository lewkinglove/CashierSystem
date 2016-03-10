CREATE DATABASE IF NOT EXISTS `cashier_system` DEFAULT CHARACTER SET utf8;

USE `cashier_system`;


DROP TABLE IF EXISTS  `good_type`;
CREATE TABLE `good_type` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键;自增ID;',
  `name` VARCHAR(32) NOT NULL COMMENT '商品类型名称',
  `description` VARCHAR(64) DEFAULT NULL COMMENT '商品类型说明',
  `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '状态;1:正常;2:禁用;3已删除;',
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MYISAM  DEFAULT CHARSET=utf8 COMMENT='商品类型表';


DROP TABLE IF EXISTS  `good`;
CREATE TABLE `good` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键;自增ID',
  `code` VARCHAR(32) NOT NULL COMMENT '识别码;条形码;唯一;',
  `type` INT(10) UNSIGNED NOT NULL COMMENT '商品类型;外键:good_type.id',
  `name` VARCHAR(64) NOT NULL COMMENT '商品名称',
  `price` DECIMAL(11,4) UNSIGNED NOT NULL COMMENT '商品单价',
  `count_unit` VARCHAR(4) NOT NULL COMMENT '数量单位; 如: 个、千克、支等',
  `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '状态;1:正常;2:禁用;3已删除;',
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNI_CODE` (`code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品信息表';

DROP TABLE IF EXISTS  `promotion_type`;
CREATE TABLE `promotion_type` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键;自增ID;',
  `code` VARCHAR(32) NOT NULL COMMENT '营销活动类型编码; 每一类营销活动和系统中的活动处理逻辑块儿对应; 故请慎重编辑',
  `name` VARCHAR(32) NOT NULL COMMENT '营销活动类型名称',
  `description` TEXT NOT NULL COMMENT '营销活动说明; 必填; 主要包含技术类说明数据, 如活动类型参数数据结构等;',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='营销活动类型';


DROP TABLE IF EXISTS  `promotion`;
CREATE TABLE `promotion` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键;自增ID;',
  `name` VARCHAR(32) NOT NULL COMMENT '营销活动名称',
  `start_time` TIMESTAMP NOT NULL COMMENT '活动开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '活动结束时间',
  `suit_all` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '优惠是否适用于所有商品; 0: 否; 1: 是;',
  `priority` INT(10) UNSIGNED NOT NULL COMMENT '活动优先级别, 用于对活动进行排他的优先选择; 优先级越高, 则优先被选中; 建议使用创建日期的数字格式;',
  `promotion_type` INT(10) UNSIGNED NOT NULL COMMENT '营销活动类型; 外键:promotion_type.id',
  `promotion_type_args` TEXT NOT NULL COMMENT '营销活动类型参数; JSON格式化数据;程序生成;',
  `status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '状态;1:正常;2:禁用;3已删除;',
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='营销活动';


DROP TABLE IF EXISTS  `promotion_target`;
CREATE TABLE `promotion_target` (
  `pro_id` INT(10) UNSIGNED NOT NULL COMMENT '组合主键;外键: promotion.id;',
  `target_type` ENUM('GOOD_TYPE','SPEC_GOOD') NOT NULL COMMENT '优惠目标类型; 枚举; GOOD_TYPE:部分类型商品参与活动; SPEC_GOOD: 指定商品参与活动;',
  `pro_target` INT(10) UNSIGNED NOT NULL COMMENT '优惠目标ID; 外键; 根据target_type不同, 可能为good.id或者good_type.id',
  PRIMARY KEY (`pro_id`,`target_type`,`pro_target`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='营销活动目标';


INSERT INTO `good_type` (`name`) VALUES ('饮料'),('水果'),('体育用品'); 

INSERT INTO `good`(`code`, `type`, `name`, `price`, `count_unit`) VALUES
('ITEM000005' , 1, '可口可乐', 3.00, '瓶'), ('ITEM000003' , 2, '苹果', 5.50, '斤'),  ('ITEM000001' , 3, '羽毛球', 1.00, '个');

INSERT INTO `promotion_type`(`code`,`name`, `description`) VALUES
('DISCOUNT', '折扣类活动', '参数共一个; discount_value: decimal类型, 介于0-1之间.'), 
('BUY_GET_FREE', '买赠类活动', '参数共两个; buy_count: decimal类型, 买满数量; free_count: decimal类型, 赠送数量; max_free_count: decimal类型, 最大赠送数量;');

INSERT INTO `promotion`(`name`, `start_time`, `end_time`, `suit_all`, `priority`,`promotion_type`, `promotion_type_args` ) VALUES
('95折', '2016-02-01 09:00:00', '2016-04-01 00:00:00', 0, 20160201, 1, '{"discount_value": 0.95}'), 
('买二赠一', '2016-03-01 09:00:00', '2016-04-01 00:00:00', 0, 20160301, 2, '{"buy_count": 2, "free_count": 1, "max_free_count": 3}');

INSERT INTO `promotion_target`(`pro_id`, `target_type`, `pro_target`) VALUES (1, 'GOOD_TYPE', 2), (2, 'SPEC_GOOD', 1), (2, 'SPEC_GOOD', 3);




