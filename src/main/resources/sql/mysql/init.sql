/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.40-log : Database - call_center
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`call_center` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `call_center`;

/*Table structure for table `tbl_dept` */

DROP TABLE IF EXISTS `tbl_dept`;

CREATE TABLE `tbl_dept` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '部门名称',
  `dept_level` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '部门级别',
  `parent_id` int(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `enabled` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '0:不可用1:可用',
  `description` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_role` */

DROP TABLE IF EXISTS `tbl_role`;

CREATE TABLE `tbl_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `enabled` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '0:不可用1:可用',
  `description` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(20) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `tbl_user` */

DROP TABLE IF EXISTS `tbl_user`;

CREATE TABLE `tbl_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `dept_id` int(20) DEFAULT NULL COMMENT '部门id',
  `role_id` int(20) DEFAULT NULL COMMENT '角色id',
  `enabled` char(1) COLLATE utf8_bin DEFAULT '1' COMMENT '0:不可用1:可用',
  `description` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `telphone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号',
  `sex` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '0:男 1:女',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `creator` int(20) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
