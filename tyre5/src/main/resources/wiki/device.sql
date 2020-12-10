/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50703
Source Host           : 127.0.0.1:3306
Source Database       : tyre_tpms

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2016-12-21 17:19:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dtu_id` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '七个字节，由大写字母和数字组成，此终端ID由制造商自行定义',
  `terminal_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '终端手机号bcd[6],手机号不足12位，则在前面补0',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `caiji_time` datetime DEFAULT NULL COMMENT '采集时间',
  `province` int(11) DEFAULT NULL COMMENT '省域ID',
  `city` int(11) DEFAULT NULL COMMENT '市县域ID',
  `maker` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '制造商',
  `dtu_style` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '终端型号',
  `trucks_color` int(11) DEFAULT NULL COMMENT '车牌颜色',
  `trucks_id` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '车牌',
  `authenticationKey` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '鉴权码',
  `reversed_flag` int(11) DEFAULT '0' COMMENT '自定义预留状态，该字段与协议无关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='终端基本信息表';

-- ----------------------------
-- Table structure for device_fasheqi
-- ----------------------------
DROP TABLE IF EXISTS `device_fasheqi`;
CREATE TABLE `device_fasheqi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dtu_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '终端ID',
  `terminal_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `fasheqi_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '发射器编号',
  `tyre_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '轮胎胎号',
  `axis_no` int(11) DEFAULT NULL COMMENT '第几个轴,轴顺序为从前到后',
  `tyre_no` int(11) DEFAULT NULL COMMENT '第几个轮胎,轮胎顺序为从左到右',
  `yali` int(11) DEFAULT NULL COMMENT '胎压 1/10 公斤 ',
  `wendu` int(11) DEFAULT NULL COMMENT '胎温 （N-27300）/100 度 ',
  `status_field` int(11) DEFAULT NULL,
  `status_on` int(1) DEFAULT NULL COMMENT '传感器状态 0：关，1;开 ',
  `pressure_valve` int(11) DEFAULT NULL COMMENT '压力阀测试(Byte),保留以获取保留位',
  `chaoya` int(1) DEFAULT NULL COMMENT '超压报警  0：正常，1;超压报警 ',
  `diya` int(1) DEFAULT NULL COMMENT '低压报警 0：正常；1:低压报警 ',
  `chaodiya` int(1) DEFAULT NULL COMMENT '超低压报警 0：正常;1:超低压报警 ',
  `error_info` int(1) DEFAULT NULL COMMENT '错误和信号丢失报警  0：正常；1：错误和信号丢失报警',
  `selfcheck` int(1) DEFAULT NULL COMMENT '传感器自检中  0：正常；1：传感器自检中',
  `gaowen` int(1) DEFAULT NULL COMMENT '高温报警 0：正常；1:高温报警 ',
  `dianchiqianya` int(1) DEFAULT NULL COMMENT '电池欠压  0：正常；1:电池欠压 ',
  `dianya` int(11) DEFAULT NULL COMMENT '电压  1/10V ',
  `caiji_time` datetime DEFAULT NULL COMMENT '采集时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='胎温胎压信息汇报 ';

-- ----------------------------
-- Table structure for device_fasheqi_history
-- ----------------------------
DROP TABLE IF EXISTS `device_fasheqi_history`;
CREATE TABLE `device_fasheqi_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dtu_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '终端ID',
  `terminal_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `fasheqi_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '发射器编号',
  `tyre_id` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT '轮胎胎号',
  `axis_no` int(11) DEFAULT NULL COMMENT '第几个轴,轴顺序为从前到后',
  `tyre_no` int(11) DEFAULT NULL COMMENT '第几个轮胎,轮胎顺序为从左到右',
  `yali` int(11) DEFAULT NULL COMMENT '胎压 1/10 公斤 ',
  `wendu` int(11) DEFAULT NULL COMMENT '胎温 （N-27300）/100 度 ',
  `status_field` int(11) DEFAULT NULL,
  `status_on` int(1) DEFAULT NULL COMMENT '传感器状态 0：关，1;开 ',
  `pressure_valve` int(11) DEFAULT NULL COMMENT '压力阀测试(Byte),保留以获取保留位',
  `chaoya` int(1) DEFAULT NULL COMMENT '超压报警  0：正常，1;超压报警 ',
  `diya` int(1) DEFAULT NULL COMMENT '低压报警 0：正常；1:低压报警 ',
  `chaodiya` int(1) DEFAULT NULL COMMENT '超低压报警 0：正常;1:超低压报警 ',
  `error_info` int(1) DEFAULT NULL COMMENT '错误和信号丢失报警  0：正常；1：错误和信号丢失报警',
  `selfcheck` int(1) DEFAULT NULL COMMENT '传感器自检中  0：正常；1：传感器自检中',
  `gaowen` int(1) DEFAULT NULL COMMENT '高温报警 0：正常；1:高温报警 ',
  `dianchiqianya` int(1) DEFAULT NULL COMMENT '电池欠压  0：正常；1:电池欠压 ',
  `dianya` int(11) DEFAULT NULL COMMENT '电压  1/10V ',
  `caiji_time` datetime DEFAULT NULL COMMENT '采集时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='胎温胎压信息汇报 ';

-- ----------------------------
-- Table structure for device_location
-- ----------------------------
DROP TABLE IF EXISTS `device_location`;
CREATE TABLE `device_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dtu_id` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '终端ID',
  `terminal_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `warn_mark` int(11) DEFAULT NULL COMMENT '报警标志,暂未解析具体的详细信息',
  `status_field` int(11) DEFAULT NULL COMMENT '状态(DWORD(32)),记录该字段以获取其他保留字段',
  `ACC_status` int(1) DEFAULT NULL COMMENT 'ACC状态: 0关 1开',
  `location_status` int(1) DEFAULT NULL COMMENT '定位状态: 0未定位 1定位',
  `latitude_type` int(1) DEFAULT NULL COMMENT '0表示北纬,1表示南纬',
  `longitude_type` int(1) DEFAULT NULL COMMENT '0表示东经,1表示西经',
  `operation_status` int(1) DEFAULT NULL COMMENT '状态  0:运营状态;1:停运状态',
  `encrypt` int(1) DEFAULT NULL COMMENT '是否加密 0;经纬度未经保密插件加密;1:经纬度已经保密插件加密',
  `trucks_oilway` int(1) DEFAULT NULL COMMENT '车辆油路  0:车辆油路正常;1:车辆油路断开 ',
  `trucks_circuit` int(1) DEFAULT NULL COMMENT '车辆电路 0:车辆电路正常;1:车辆电路断开 ',
  `trucks_doorlock` int(1) DEFAULT NULL COMMENT '车门锁 0:车门解锁;1:车门加锁 ',
  `latitude` double(20,8) DEFAULT NULL COMMENT '纬度',
  `longitude` double(20,8) DEFAULT NULL COMMENT '经度',
  `elevation` int(11) DEFAULT NULL COMMENT '高程',
  `speed` int(11) DEFAULT NULL COMMENT '速度',
  `direction` int(255) DEFAULT NULL COMMENT '方向',
  `caiji_time` datetime DEFAULT NULL COMMENT '采集时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='位置信息汇报 ';

-- ----------------------------
-- Table structure for device_location_history
-- ----------------------------
DROP TABLE IF EXISTS `device_location_history`;
CREATE TABLE `device_location_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dtu_id` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '终端ID',
  `terminal_phone` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `warn_mark` int(11) DEFAULT NULL COMMENT '报警标志,暂未解析具体的详细信息',
  `status_field` int(11) DEFAULT NULL COMMENT '状态(DWORD(32)),记录该字段以获取其他保留字段',
  `ACC_status` int(1) DEFAULT NULL COMMENT 'ACC状态: 0关 1开',
  `location_status` int(1) DEFAULT NULL COMMENT '定位状态: 0未定位 1定位',
  `latitude_type` int(1) DEFAULT NULL COMMENT '0表示北纬,1表示南纬',
  `longitude_type` int(1) DEFAULT NULL COMMENT '0表示东经,1表示西经',
  `operation_status` int(1) DEFAULT NULL COMMENT '状态  0:运营状态;1:停运状态',
  `encrypt` int(1) DEFAULT NULL COMMENT '是否加密 0;经纬度未经保密插件加密;1:经纬度已经保密插件加密',
  `trucks_oilway` int(1) DEFAULT NULL COMMENT '车辆油路  0:车辆油路正常;1:车辆油路断开 ',
  `trucks_circuit` int(1) DEFAULT NULL COMMENT '车辆电路 0:车辆电路正常;1:车辆电路断开 ',
  `trucks_doorlock` int(1) DEFAULT NULL COMMENT '车门锁 0:车门解锁;1:车门加锁 ',
  `latitude` double(20,8) DEFAULT NULL COMMENT '纬度',
  `longitude` double(20,8) DEFAULT NULL COMMENT '经度',
  `elevation` int(11) DEFAULT NULL COMMENT '高程',
  `speed` int(11) DEFAULT NULL COMMENT '速度',
  `direction` int(255) DEFAULT NULL COMMENT '方向',
  `caiji_time` datetime DEFAULT NULL COMMENT '采集时间',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='位置信息汇报 ';
