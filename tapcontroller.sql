/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.219.19
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 10.0.219.19:3306
 Source Schema         : tapcontroller

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 29/01/2021 20:41:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm
-- ----------------------------
DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm`  (
  `alarmId` int(11) NOT NULL AUTO_INCREMENT COMMENT '告警id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `rank` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '级别（未定义、信息、提示、次要、重要、紧急）',
  `time` datetime(0) NOT NULL COMMENT '触发时间',
  `day` int(11) NULL DEFAULT NULL COMMENT '已经持续时间',
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '告警状态（已处理、未处理）',
  PRIMARY KEY (`alarmId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 461 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '告警记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm
-- ----------------------------
INSERT INTO `alarm` VALUES (460, '设备节点一段时间接收不到数据', '提示', '2021-01-14 16:43:32', 4, '未处理');

-- ----------------------------
-- Table structure for flow_monitor
-- ----------------------------
DROP TABLE IF EXISTS `flow_monitor`;
CREATE TABLE `flow_monitor`  (
  `time` datetime(0) NOT NULL COMMENT '时间',
  `totalFlow` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实时流量-总流量',
  `rxFlow` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每秒接收字节速率',
  `txFlow` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每秒发送字节速率',
  `rxPackage` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每秒接收数据包数',
  `txPackage` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每秒发送数据包数',
  `lossPacket` float NULL DEFAULT NULL COMMENT '每秒丢包率'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流量监控表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow_monitor
-- ----------------------------
INSERT INTO `flow_monitor` VALUES ('2020-12-28 14:26:13', '11', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for line_rule
-- ----------------------------
DROP TABLE IF EXISTS `line_rule`;
CREATE TABLE `line_rule`  (
  `lineId` int(11) NOT NULL COMMENT '连线id',
  `ruleId` int(11) NOT NULL COMMENT '规则id',
  INDEX `line_rule_portline_lineId_fk`(`lineId`) USING BTREE,
  INDEX `line_rule_rule_ruleId_fk`(`ruleId`) USING BTREE,
  CONSTRAINT `line_rule_portline_lineId_fk` FOREIGN KEY (`lineId`) REFERENCES `portline` (`lineId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `line_rule_rule_ruleId_fk` FOREIGN KEY (`ruleId`) REFERENCES `rule` (`ruleId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '连线_规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of line_rule
-- ----------------------------
INSERT INTO `line_rule` VALUES (61, 68);
INSERT INTO `line_rule` VALUES (86, 65);
INSERT INTO `line_rule` VALUES (87, 65);
INSERT INTO `line_rule` VALUES (88, 65);
INSERT INTO `line_rule` VALUES (89, 66);
INSERT INTO `line_rule` VALUES (89, 67);
INSERT INTO `line_rule` VALUES (90, 66);
INSERT INTO `line_rule` VALUES (90, 67);
INSERT INTO `line_rule` VALUES (91, 66);
INSERT INTO `line_rule` VALUES (91, 67);
INSERT INTO `line_rule` VALUES (92, 66);
INSERT INTO `line_rule` VALUES (92, 67);
INSERT INTO `line_rule` VALUES (93, 66);
INSERT INTO `line_rule` VALUES (93, 67);
INSERT INTO `line_rule` VALUES (94, 66);
INSERT INTO `line_rule` VALUES (94, 67);

-- ----------------------------
-- Table structure for outportgroup
-- ----------------------------
DROP TABLE IF EXISTS `outportgroup`;
CREATE TABLE `outportgroup`  (
  `outPortGroupId` int(11) NOT NULL AUTO_INCREMENT,
  `strategyId` int(11) NOT NULL,
  PRIMARY KEY (`outPortGroupId`) USING BTREE,
  INDEX `outportgroup_strategy_strategyId_fk`(`strategyId`) USING BTREE,
  CONSTRAINT `outportgroup_strategy_strategyId_fk` FOREIGN KEY (`strategyId`) REFERENCES `strategy` (`strategyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出端口id组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outportgroup
-- ----------------------------
INSERT INTO `outportgroup` VALUES (77, 114);
INSERT INTO `outportgroup` VALUES (78, 114);
INSERT INTO `outportgroup` VALUES (70, 115);

-- ----------------------------
-- Table structure for outportgroup_port
-- ----------------------------
DROP TABLE IF EXISTS `outportgroup_port`;
CREATE TABLE `outportgroup_port`  (
  `portId` int(11) NOT NULL COMMENT '端口id',
  `outPortGroupId` int(11) NOT NULL COMMENT '出端口组Id',
  INDEX `outportgroup_port_outportgroup_outPortGroupId_fk`(`outPortGroupId`) USING BTREE,
  INDEX `outportgroup_port_port_portId_fk`(`portId`) USING BTREE,
  CONSTRAINT `outportgroup_port_outportgroup_outPortGroupId_fk` FOREIGN KEY (`outPortGroupId`) REFERENCES `outportgroup` (`outPortGroupId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `outportgroup_port_port_portId_fk` FOREIGN KEY (`portId`) REFERENCES `port` (`portId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '出端口组_端口对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outportgroup_port
-- ----------------------------
INSERT INTO `outportgroup_port` VALUES (20, 70);
INSERT INTO `outportgroup_port` VALUES (24, 77);
INSERT INTO `outportgroup_port` VALUES (21, 78);
INSERT INTO `outportgroup_port` VALUES (22, 78);

-- ----------------------------
-- Table structure for port
-- ----------------------------
DROP TABLE IF EXISTS `port`;
CREATE TABLE `port`  (
  `portId` int(11) NOT NULL AUTO_INCREMENT COMMENT '端口id',
  `portName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '端口名称',
  `state` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口状态 开启up，关闭down',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `type` int(11) NULL DEFAULT NULL COMMENT '端口类型（入端口0，出端口1,未使用-1）',
  `inPacket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收包数',
  `outPacket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送包数',
  `inRate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接收速率',
  `outRate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送速率',
  `rate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口速率',
  `ifIndex` int(11) NULL DEFAULT NULL COMMENT 'sflow定义的编号',
  PRIMARY KEY (`portId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '端口' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of port
-- ----------------------------
INSERT INTO `port` VALUES (1, 'eth0', 'up', '端口0', 0, '0', '0', '0.0', '0.0', '0', 8015092);
INSERT INTO `port` VALUES (2, 'eth1', 'up', '端口1', 0, '0', '0', '0.0', '0.0', '0', 9806969);
INSERT INTO `port` VALUES (3, 'eth10', 'up', '端口2', 0, '0', '0', '0.0', '0.0', '0', 3364777);
INSERT INTO `port` VALUES (4, 'eth11', 'up', '端口3', -1, '0', '0', '0.0', '0.0', '0', 11651395);
INSERT INTO `port` VALUES (5, 'eth12', 'up', '端口4', 0, '0', '0', '0.0', '0.0', '0', 10168944);
INSERT INTO `port` VALUES (6, 'eth13', 'up', '端口5', -1, '0', '0', '0.0', '0.0', '0', 8534709);
INSERT INTO `port` VALUES (7, 'eth14', 'down', '端口6', -1, '0', '0', '0.0', '0.0', '0', 12855175);
INSERT INTO `port` VALUES (8, 'eth15', 'down', '端口7', -1, '0', '0', '0.0', '0.0', '0', 3044562);
INSERT INTO `port` VALUES (9, 'eth18', 'down', '端口8', -1, '0', '0', '0.0', '0.0', '0', 11118127);
INSERT INTO `port` VALUES (10, 'eth19', 'down', '端口9', -1, '0', '0', '0.0', '0.0', '0', 13983229);
INSERT INTO `port` VALUES (11, 'eth2', 'down', '端口10', -1, '0', '0', '0.0', '0.0', '0', 12541995);
INSERT INTO `port` VALUES (12, 'eth20', 'down', '端口11', -1, '0', '0', '0.0', '0.0', '0', 3793417);
INSERT INTO `port` VALUES (13, 'eth21', 'up', '端口12', -1, '0', '0', '0.0', '0.0', '1000', 1704930);
INSERT INTO `port` VALUES (14, 'eth22', 'up', '端口13', -1, '0', '0', '0.0', '0.0', '0', 13854105);
INSERT INTO `port` VALUES (15, 'eth23', 'down', '端口14', -1, '0', '0', '0.0', '0.0', '0', 14039567);
INSERT INTO `port` VALUES (16, 'eth24', 'down', '端口15', -1, '0', '0', '0.0', '0.0', '0', 7380284);
INSERT INTO `port` VALUES (17, 'eth25', 'up', '端口16', -1, '0', '0', '0.0', '0.0', '0', 5346132);
INSERT INTO `port` VALUES (18, 'eth3', 'up', '端口17', -1, '0', '0', '0.0', '0.0', '0', 3580461);
INSERT INTO `port` VALUES (19, 'eth4', 'down', '端口18', -1, '0', '0', '0.0', '0.0', '0', 8723346);
INSERT INTO `port` VALUES (20, 'eth5', 'up', '端口19', 1, '0', '0', '0.0', '0.0', '0', 3558554);
INSERT INTO `port` VALUES (21, 'eth6', 'up', '端口20', 1, '0', '0', '0.0', '0.0', '0', 5970659);
INSERT INTO `port` VALUES (22, 'eth7', 'up', '端口21', 1, '0', '0', '0.0', '0.0', '0', 4630208);
INSERT INTO `port` VALUES (23, 'eth8', 'up', '端口22', -1, '0', '0', '0.0', '0.0', '0', 11123951);
INSERT INTO `port` VALUES (24, 'eth9', 'down', '端口23', 1, '0', '0', '0.0', '0.0', '0', 8046722);

-- ----------------------------
-- Table structure for port_monitor
-- ----------------------------
DROP TABLE IF EXISTS `port_monitor`;
CREATE TABLE `port_monitor`  (
  `portId` int(11) NOT NULL COMMENT '端口id',
  `portName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '端口名称',
  `txFlow` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该端口每秒发送自己速率',
  `rxFlow` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该端口每秒接受字节速率',
  `time` datetime(0) NOT NULL COMMENT '时间',
  INDEX `port_monitor_port_portId_fk`(`portId`) USING BTREE,
  CONSTRAINT `port_monitor_port_portId_fk` FOREIGN KEY (`portId`) REFERENCES `port` (`portId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '端口流量监控表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of port_monitor
-- ----------------------------
INSERT INTO `port_monitor` VALUES (1, 'eth0', NULL, NULL, '2020-12-28 14:46:38');
INSERT INTO `port_monitor` VALUES (2, 'eth1', NULL, NULL, '2020-12-29 17:59:58');

-- ----------------------------
-- Table structure for portline
-- ----------------------------
DROP TABLE IF EXISTS `portline`;
CREATE TABLE `portline`  (
  `lineId` int(11) NOT NULL AUTO_INCREMENT COMMENT '连线id',
  `inPortId` int(11) NOT NULL COMMENT '进端口id',
  `outPortId` int(11) NOT NULL COMMENT '出端口id',
  `rate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '速率',
  `strategyId` int(11) NULL DEFAULT NULL COMMENT '策略id',
  PRIMARY KEY (`lineId`) USING BTREE,
  INDEX `portline_port_portId_fk`(`inPortId`) USING BTREE,
  INDEX `portline_port_portId_fk_2`(`outPortId`) USING BTREE,
  INDEX `portline_strategy_strategyId_fk`(`strategyId`) USING BTREE,
  CONSTRAINT `portline_port_portId_fk` FOREIGN KEY (`inPortId`) REFERENCES `port` (`portId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `portline_port_portId_fk_2` FOREIGN KEY (`outPortId`) REFERENCES `port` (`portId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `portline_strategy_strategyId_fk` FOREIGN KEY (`strategyId`) REFERENCES `strategy` (`strategyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '端口连线' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of portline
-- ----------------------------
INSERT INTO `portline` VALUES (61, 3, 20, '0', 115);
INSERT INTO `portline` VALUES (86, 5, 24, '0', 114);
INSERT INTO `portline` VALUES (87, 1, 24, '0', 114);
INSERT INTO `portline` VALUES (88, 2, 24, '0', 114);
INSERT INTO `portline` VALUES (89, 5, 21, '0', 114);
INSERT INTO `portline` VALUES (90, 5, 22, '0', 114);
INSERT INTO `portline` VALUES (91, 1, 21, '0', 114);
INSERT INTO `portline` VALUES (92, 1, 22, '0', 114);
INSERT INTO `portline` VALUES (93, 2, 21, '0', 114);
INSERT INTO `portline` VALUES (94, 2, 22, '0', 114);

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `ruleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '规则id',
  `vlanRange` int(11) NULL DEFAULT NULL COMMENT 'vlan范围',
  `sourceMac` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源mac地址',
  `destinationMac` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的mac地址',
  `mplsLabel` int(11) NULL DEFAULT NULL COMMENT 'MPLS标签',
  `mplsTc` int(11) NULL DEFAULT NULL COMMENT 'MPLS tc',
  `sourceIp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源Ip',
  `destinationIp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目的Ip',
  `protocol` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协议',
  `sourcePort` int(11) NULL DEFAULT NULL COMMENT '源端口',
  `destinationPort` int(11) NULL DEFAULT NULL COMMENT '目的端口',
  `tcpFlag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'TCP flag',
  `vlanAction` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'vlan动作',
  `vlanId` int(11) NULL DEFAULT NULL COMMENT 'vlanId',
  `mplsAction` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MPLS动作',
  `mplsActionLabel` int(11) NULL DEFAULT NULL COMMENT 'MPLS动作标签',
  `updateSourceIp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改源Ip',
  `updateDestinationIp` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改目的Ip',
  `updateSourceMac` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改源mac地址',
  `updateDestinationMac` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改目的mac地址',
  `messageTruncate` int(11) NULL DEFAULT NULL COMMENT '报文截短',
  `outPortGroupId` int(11) NOT NULL COMMENT '出端口组id',
  PRIMARY KEY (`ruleId`) USING BTREE,
  INDEX `rule_outportgroup_outPortGroupId_fk`(`outPortGroupId`) USING BTREE,
  CONSTRAINT `rule_outportgroup_outPortGroupId_fk` FOREIGN KEY (`outPortGroupId`) REFERENCES `outportgroup` (`outPortGroupId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '策略规则' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES (65, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 77);
INSERT INTO `rule` VALUES (66, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 78);
INSERT INTO `rule` VALUES (67, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 78);
INSERT INTO `rule` VALUES (68, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 70);

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy`  (
  `strategyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '策略id',
  `flowStatistics` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '流量统计',
  PRIMARY KEY (`strategyId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '策略' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy
-- ----------------------------
INSERT INTO `strategy` VALUES (114, '0');
INSERT INTO `strategy` VALUES (115, '0');

-- ----------------------------
-- Table structure for strategy_port
-- ----------------------------
DROP TABLE IF EXISTS `strategy_port`;
CREATE TABLE `strategy_port`  (
  `strategyId` int(11) NOT NULL,
  `portId` int(11) NOT NULL,
  INDEX `strategy_port_port_portId_fk`(`portId`) USING BTREE,
  INDEX `strategy_port_strategy_strategyId_fk`(`strategyId`) USING BTREE,
  CONSTRAINT `strategy_port_port_portId_fk` FOREIGN KEY (`portId`) REFERENCES `port` (`portId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `strategy_port_strategy_strategyId_fk` FOREIGN KEY (`strategyId`) REFERENCES `strategy` (`strategyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '策略_入端口表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of strategy_port
-- ----------------------------
INSERT INTO `strategy_port` VALUES (115, 3);
INSERT INTO `strategy_port` VALUES (114, 5);
INSERT INTO `strategy_port` VALUES (114, 1);
INSERT INTO `strategy_port` VALUES (114, 2);

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office`  (
  `officeId` int(11) NOT NULL AUTO_INCREMENT,
  `officeName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createBy` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL DEFAULT NULL,
  `updateTime` datetime(0) NULL DEFAULT NULL,
  `parentId` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`officeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES (1, '部门1', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (2, '部门2', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (3, '部门3', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (4, '部门4', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (5, '部门5', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (6, '部门6', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (7, '部门7', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (8, '部门8', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (9, '部门9', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (10, '部门10', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (11, '部门11', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 0);
INSERT INTO `sys_office` VALUES (12, '部门12', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 1);
INSERT INTO `sys_office` VALUES (13, '部门13', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 1);
INSERT INTO `sys_office` VALUES (14, '部门14', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 12);
INSERT INTO `sys_office` VALUES (15, '部门15', 'ljx', '2020-12-14 14:55:40', '2020-12-14 14:55:42', 12);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permissionId` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `permissionName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `parentId` int(11) NOT NULL DEFAULT 0 COMMENT '父级id',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限接口路径',
  PRIMARY KEY (`permissionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '首页', 0, '');
INSERT INTO `sys_permission` VALUES (2, '总览', 1, '/tapapi/overview/sysInfo*,/tapapi/overview/sysInfoOther*,/tapapi/strategy/selectport*,/tapapi/alarm/alarmAll*,/tapapi/monitor/tapAllFlow*');
INSERT INTO `sys_permission` VALUES (3, '策略', 0, '');
INSERT INTO `sys_permission` VALUES (4, '策略配置（读）', 3, '/tapapi/strategy/selectstrategy*,/tapapi/strategy/selectrulebyid*,/tapapi/strategy/selectPortLine*');
INSERT INTO `sys_permission` VALUES (5, '策略配置（写）', 3, '/tapapi/strategy/updateRule*,/tapapi/strategy/insertstrategy*,/tapapi/strategy/deletestrategy*,/tapapi/strategy/updatestrategy*');
INSERT INTO `sys_permission` VALUES (6, '接口配置（读）', 3, '/tapapi/strategy/selectport*');
INSERT INTO `sys_permission` VALUES (7, '接口配置（写）', 3, '/tapapi/strategy/updateport*');
INSERT INTO `sys_permission` VALUES (8, '监控', 0, '');
INSERT INTO `sys_permission` VALUES (9, '流量监控', 8, '/tapapi/monitor/tapAllFlow*,/tapapi/monitor/portFlow*');
INSERT INTO `sys_permission` VALUES (10, '告警', 0, '');
INSERT INTO `sys_permission` VALUES (11, '告警概览（读）', 10, '/tapapi/alarm/alarmInfo*,/tapapi/alarm/alarmAll*');
INSERT INTO `sys_permission` VALUES (12, '告警概览（写）', 10, '');
INSERT INTO `sys_permission` VALUES (13, '告警记录（读）', 10, '/tapapi/alarm/alarmAll*');
INSERT INTO `sys_permission` VALUES (14, '告警记录（写）', 10, '');
INSERT INTO `sys_permission` VALUES (15, '系统', 0, '');
INSERT INTO `sys_permission` VALUES (16, '设备重启', 15, '/tapapi/system/reboot*');
INSERT INTO `sys_permission` VALUES (17, '系统时间（读）', 15, '/tapapi/system/selecttime*');
INSERT INTO `sys_permission` VALUES (18, '系统时间（写）', 15, '/tapapi/system/updatetime*');
INSERT INTO `sys_permission` VALUES (19, '系统日志（读）', 15, '/tapapi/system/selectlog*');
INSERT INTO `sys_permission` VALUES (20, '系统日志（写）', 15, '');
INSERT INTO `sys_permission` VALUES (21, '角色管理（读）', 15, '/tapapi/system/role/get*');
INSERT INTO `sys_permission` VALUES (22, '角色管理（写）', 15, '/tapapi/system/role/add*,/tapapi/system/role/update*,/tapapi/system/role/delete*');
INSERT INTO `sys_permission` VALUES (23, '账号管理（读）', 15, '/tapapi/system/users/get*');
INSERT INTO `sys_permission` VALUES (24, '账号管理（写）', 15, '/tapapi/system/users/add*,/tapapi/system/users/update*,/tapapi/system/users/start*,/tapapi/system/users/stop*,/tapapi/system/users/delete*');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `roleName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `createBy` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '角色133哥', 'ljx', '2020-12-14 14:55:17', '2021-01-27 14:38:34');
INSERT INTO `sys_role` VALUES (2, '角色2', 'ljx', '2020-12-14 14:55:17', NULL);
INSERT INTO `sys_role` VALUES (3, '角色3', 'ljx', '2020-12-14 14:55:17', NULL);
INSERT INTO `sys_role` VALUES (4, '角色4', 'ljx', '2020-12-14 14:55:17', NULL);
INSERT INTO `sys_role` VALUES (6, '角色6', 'ljx', '2020-12-14 14:55:17', NULL);
INSERT INTO `sys_role` VALUES (7, '角色7vv', 'ljx', '2020-12-14 14:55:17', '2021-01-25 09:57:13');
INSERT INTO `sys_role` VALUES (8, '角色8', 'ljx', '2020-12-14 14:55:17', NULL);
INSERT INTO `sys_role` VALUES (10, '角色10', 'ljx', '2020-12-14 14:55:17', NULL);
INSERT INTO `sys_role` VALUES (13, '111', 'ljx', '2021-01-27 14:04:59', NULL);
INSERT INTO `sys_role` VALUES (14, '1111', 'ljx', '2021-01-27 17:46:57', NULL);
INSERT INTO `sys_role` VALUES (15, '222222', 'ljx', '2021-01-27 17:47:01', NULL);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `roleId` int(11) NOT NULL COMMENT '角色id',
  `permissionId` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`roleId`, `permissionId`) USING BTREE,
  INDEX `sys_role_permission_sys_permission_permissionId_fk`(`permissionId`) USING BTREE,
  CONSTRAINT `sys_role_permission_sys_permission_permissionId_fk` FOREIGN KEY (`permissionId`) REFERENCES `sys_permission` (`permissionId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_permission_sys_role_roleId_fk` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`roleId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (3, 2);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (3, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (3, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 9);
INSERT INTO `sys_role_permission` VALUES (3, 9);
INSERT INTO `sys_role_permission` VALUES (3, 11);
INSERT INTO `sys_role_permission` VALUES (3, 12);
INSERT INTO `sys_role_permission` VALUES (3, 13);
INSERT INTO `sys_role_permission` VALUES (3, 14);
INSERT INTO `sys_role_permission` VALUES (3, 16);
INSERT INTO `sys_role_permission` VALUES (3, 17);
INSERT INTO `sys_role_permission` VALUES (3, 18);
INSERT INTO `sys_role_permission` VALUES (1, 19);
INSERT INTO `sys_role_permission` VALUES (3, 19);
INSERT INTO `sys_role_permission` VALUES (3, 20);
INSERT INTO `sys_role_permission` VALUES (1, 21);
INSERT INTO `sys_role_permission` VALUES (3, 21);
INSERT INTO `sys_role_permission` VALUES (1, 22);
INSERT INTO `sys_role_permission` VALUES (3, 22);
INSERT INTO `sys_role_permission` VALUES (1, 23);
INSERT INTO `sys_role_permission` VALUES (3, 23);
INSERT INTO `sys_role_permission` VALUES (1, 24);
INSERT INTO `sys_role_permission` VALUES (3, 24);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `userName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `userTel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '联系电话',
  `roleId` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `loginTime` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) NULL DEFAULT 0 COMMENT '帐号状态:0正常,1禁用',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE INDEX `index_sys_user_pk`(`userId`) USING BTREE,
  INDEX `user_name`(`userName`) USING BTREE,
  INDEX `sys_user_sys_role_roleId_fk`(`roleId`) USING BTREE,
  CONSTRAINT `sys_user_sys_role_roleId_fk` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`roleId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (3, 'ljx', '$2a$10$PWZ5GCnivIwSREJ.thIexe9/ObhqDbwQL.wLFHVsObW9.nHvcbJ32', 'ljx', '123456789', 1, '2020-12-11 14:53:07', '2021-01-25 10:08:53', 0, 'xxxx');
INSERT INTO `sys_user` VALUES (8, 'sw', '$2a$10$LrWLmkgPisjgmmEmFWvy0uj3bCtspEwjhtqZX2wUNUHtWM8l66OUS', '123', '123', 1, '2020-12-14 16:56:22', NULL, 0, '123');
INSERT INTO `sys_user` VALUES (9, '12c v', '$2a$10$6p0EnLq9Zf/ElBHzeig70egdd8WfFg.KL8N.8RJ/5R/b4v86bBEke', '123', '123', 1, '2020-12-14 16:58:06', NULL, 0, '123');
INSERT INTO `sys_user` VALUES (10, 'ddd', '$2a$10$PWZ5GCnivIwSREJ.thIexe9/ObhqDbwQL.wLFHVsObW9.nHvcbJ32', '123', '123', 1, '2020-12-14 16:59:45', NULL, 0, '123');
INSERT INTO `sys_user` VALUES (11, 'ccc', '$2a$10$LrWLmkgPisjgmmEmFWvy0uj3bCtspEwjhtqZX2wUNUHtWM8l66OUS', '123', '123', 1, '2020-12-14 17:00:06', NULL, 0, '123');
INSERT INTO `sys_user` VALUES (12, 'fff', '$2a$10$6p0EnLq9Zf/ElBHzeig70egdd8WfFg.KL8N.8RJ/5R/b4v86bBEke', '123', '123', 1, '2020-12-14 17:00:24', NULL, 0, '123');
INSERT INTO `sys_user` VALUES (13, 'chen', '$2a$10$PWZ5GCnivIwSREJ.thIexe9/ObhqDbwQL.wLFHVsObW9.nHvcbJ32', 'ch', '123456789', 1, '2020-12-14 17:23:56', NULL, 0, 'xxxx');
INSERT INTO `sys_user` VALUES (14, 'ch', '$2a$10$LrWLmkgPisjgmmEmFWvy0uj3bCtspEwjhtqZX2wUNUHtWM8l66OUS', 'ch', '123456789', 1, '2020-12-15 15:57:09', NULL, 0, '');
INSERT INTO `sys_user` VALUES (15, 'asd', '$2a$10$6p0EnLq9Zf/ElBHzeig70egdd8WfFg.KL8N.8RJ/5R/b4v86bBEke', 'asd', '123456789', 1, '2020-12-20 17:02:09', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (16, 'lll', '$2a$10$PWZ5GCnivIwSREJ.thIexe9/ObhqDbwQL.wLFHVsObW9.nHvcbJ32', 'lll', '123', 1, '2020-12-20 17:33:00', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (17, 'qwe', '$2a$10$LrWLmkgPisjgmmEmFWvy0uj3bCtspEwjhtqZX2wUNUHtWM8l66OUS', 'qwe', '123', 1, '2020-12-20 17:55:35', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (18, 'rrrp', '$2a$10$6p0EnLq9Zf/ElBHzeig70egdd8WfFg.KL8N.8RJ/5R/b4v86bBEke', 'rrrp', '123', 1, '2020-12-20 18:03:47', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (19, 'zy', '$2a$10$LrWLmkgPisjgmmEmFWvy0uj3bCtspEwjhtqZX2wUNUHtWM8l66OUS', 'zy', '12313213', 1, '2021-01-25 09:44:55', '2021-01-25 09:45:36', 0, NULL);
INSERT INTO `sys_user` VALUES (20, 'zaj', '$2a$10$LrWLmkgPisjgmmEmFWvy0uj3bCtspEwjhtqZX2wUNUHtWM8l66OUS', 'zaj', '1234567890', 1, '2021-01-25 22:33:47', NULL, 0, NULL);
INSERT INTO `sys_user` VALUES (21, 'ase', '56eb739c41a5dc445b53202511bb0cff', 'ase', '1234567890', 1, '2021-01-25 22:35:14', NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_user_action
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_action`;
CREATE TABLE `sys_user_action`  (
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名/真实姓名',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `IP` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `module` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块',
  `action` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动作',
  `log` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志',
  `result` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `useractionId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  PRIMARY KEY (`useractionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_action
-- ----------------------------
INSERT INTO `sys_user_action` VALUES ('ljx', '2020-12-20 19:30:47', '192.168.56.1', '账号管理', '添加新用户', '用户名为ljx(账号:ljx,IP地址:192.168.56.1)的操作者在2020-12-20 19:30:46时对账号管理模块进行添加新用户操作', '失败', 15, '添加失败，账号已存在');
INSERT INTO `sys_user_action` VALUES ('123', '2021-01-25 22:33:50', '0:0:0:0:0:0:0:1', '账号管理', '添加新用户', '用户名为123(账号:fff,IP地址:0:0:0:0:0:0:0:1)的操作者在2021-01-25 22:33:50时对账号管理模块进行添加新用户操作', '成功', 16, '操作成功');
INSERT INTO `sys_user_action` VALUES ('123', '2021-01-25 22:35:14', '0:0:0:0:0:0:0:1', '账号管理', '添加新用户', '用户名为123(账号:fff,IP地址:0:0:0:0:0:0:0:1)的操作者在2021-01-25 22:35:14时对账号管理模块进行添加新用户操作', '成功', 17, '操作成功');
INSERT INTO `sys_user_action` VALUES ('ljx', '2021-01-27 14:05:00', '172.30.40.28', '角色管理', '添加新角色', '用户名为ljx(账号:ljx,IP地址:172.30.40.28)的操作者在2021-01-27 14:05:00时对角色管理模块进行添加新角色操作', '成功', 18, '操作成功');
INSERT INTO `sys_user_action` VALUES ('ljx', '2021-01-27 14:45:45', '172.30.40.28', '角色管理', '添加新角色', '用户名为ljx(账号:ljx,IP地址:172.30.40.28)的操作者在2021-01-27 14:45:45时对角色管理模块进行添加新角色操作', '失败', 19, '添加失败,角色名称已存在');
INSERT INTO `sys_user_action` VALUES ('ljx', '2021-01-27 17:46:59', '172.30.40.28', '角色管理', '添加新角色', '用户名为ljx(账号:ljx,IP地址:172.30.40.28)的操作者在2021-01-27 17:46:58时对角色管理模块进行添加新角色操作', '失败', 20, '操作成功');
INSERT INTO `sys_user_action` VALUES ('ljx', '2021-01-27 17:47:03', '172.30.40.28', '角色管理', '添加新角色', '用户名为ljx(账号:ljx,IP地址:172.30.40.28)的操作者在2021-01-27 17:47:02时对角色管理模块进行添加新角色操作', '失败', 21, '操作成功');

SET FOREIGN_KEY_CHECKS = 1;
