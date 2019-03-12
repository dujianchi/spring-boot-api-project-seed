/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 100134
 Source Host           : localhost:3306
 Source Schema         : seed_project

 Target Server Type    : MySQL
 Target Server Version : 100134
 File Encoding         : 65001

 Date: 10/03/2019 22:44:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seed_user
-- ----------------------------
DROP TABLE IF EXISTS `seed_user`;
CREATE TABLE `seed_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数字id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `role_id` tinyint(3) NULL DEFAULT NULL COMMENT '角色id，tinyint类型最大127，跟角色相关',
  `user_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字符型用户id',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT COMMENT '角色id，tinyint类型最大127',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `value` int(11) NOT NULL DEFAULT 1 COMMENT '角色对应的值',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '角色创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` (`id`, `name`, `value`) VALUES (1, '超级管理员', 9999);
INSERT INTO `system_role` (`id`, `name`, `value`) VALUES (2, '管理员', 999);
INSERT INTO `system_role` (`id`, `name`, `value`) VALUES (3, '用户', 9);

SET FOREIGN_KEY_CHECKS = 1;
