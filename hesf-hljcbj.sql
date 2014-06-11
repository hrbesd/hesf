/*
SQLyog 企业版 - MySQL GUI v7.14 
MySQL - 5.1.62-community : Database - hesf
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`hesf` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `hesf`;

/*Table structure for table `accounts` */

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  `version` int(11) DEFAULT '1',
  `year` char(4) DEFAULT NULL COMMENT '出账年份',
  `company_id` int(11) DEFAULT NULL COMMENT '公司id',
  `audit_id` int(11) DEFAULT NULL COMMENT '审核id',
  `total_money` decimal(16,2) DEFAULT NULL COMMENT '合计应缴款项=今年审核产生的款项+去年未缴金额',
  `is_finished` tinyint(1) DEFAULT '0' COMMENT '是否缴款完毕',
  `audit_process_status` int(11) DEFAULT NULL COMMENT '目前状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `accounts` */

/*Table structure for table `area` */

DROP TABLE IF EXISTS `area`;

CREATE TABLE `area` (
  `code` char(8) NOT NULL COMMENT '地区CODE',
  `name` varchar(50) NOT NULL COMMENT '地名',
  `pyname` varchar(255) DEFAULT NULL COMMENT '地名全拼',
  `abbr` varchar(50) DEFAULT NULL COMMENT '地名拼音缩写',
  `mark` varchar(50) DEFAULT NULL COMMENT '备注',
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  PRIMARY KEY (`code`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `area` */

insert  into `area`(`code`,`name`,`pyname`,`abbr`,`mark`,`is_active`) values ('10230000','黑龙江省',NULL,NULL,'',0),('20230100','哈尔滨市',NULL,NULL,'',0),('20230200','齐齐哈尔市',NULL,NULL,'',0),('20230300','鸡西市',NULL,NULL,'',0),('20230400','鹤岗市',NULL,NULL,'',0),('20230500','双鸭山市',NULL,NULL,'',0),('20230600','大庆市',NULL,NULL,'',0),('20230700','伊春市',NULL,NULL,'',0),('20230800','佳木斯市',NULL,NULL,'',0),('20230900','七台河市',NULL,NULL,'',0),('20231000','牡丹江市',NULL,NULL,'',0),('20231100','黑河市',NULL,NULL,'',0),('20231200','绥化市',NULL,NULL,'',0),('20232700','大兴安岭地区',NULL,NULL,'',0),('20232800','森工总局',NULL,NULL,'残保金项目需求添加，可删除掉',0),('n001','宝泉岭',NULL,NULL,'农垦保障金使用',1),('n002','北安',NULL,NULL,'农垦保障金使用',1),('n003','哈尔滨',NULL,NULL,'农垦保障金使用',1),('n004','红兴隆',NULL,NULL,'农垦保障金使用',1),('n005','建三江',NULL,NULL,'农垦保障金使用',1),('n006','九三',NULL,NULL,'农垦保障金使用',1),('n007','局直',NULL,NULL,'农垦保障金使用',1),('n008','牡丹江',NULL,NULL,'农垦保障金使用',1),('n009','齐齐哈尔',NULL,NULL,'农垦保障金使用',1),('n010','绥化',NULL,NULL,'农垦保障金使用',1);

/*Table structure for table `audit` */

DROP TABLE IF EXISTS `audit`;

CREATE TABLE `audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` char(4) NOT NULL COMMENT '年度',
  `company_id` int(11) NOT NULL COMMENT '外键--公司id',
  `company_emp_total` int(11) DEFAULT '0' COMMENT '企业员工总数',
  `company_handicap_total` int(11) DEFAULT '0' COMMENT '企业残疾人总数',
  `company_predict_total` int(11) DEFAULT '0' COMMENT '预计残疾人数',
  `company_should_total` decimal(16,2) DEFAULT '0.00' COMMENT '应该安排人数',
  `company_already_total` int(11) DEFAULT '0' COMMENT '已安排人数',
  `area_code` char(8) DEFAULT '10230000' COMMENT '地区code',
  `audit_process_status` int(11) NOT NULL DEFAULT '1' COMMENT '审批处理流程',
  `amount_payable` decimal(16,2) DEFAULT '0.00' COMMENT '应缴金额',
  `reduction_amount` decimal(16,2) DEFAULT '0.00' COMMENT '减缴金额',
  `actual_amount` decimal(16,2) DEFAULT '0.00' COMMENT '实际应缴金额',
  `pay_amount` decimal(16,2) DEFAULT '0.00' COMMENT '实缴总金额',
  `remain_amount` decimal(16,2) DEFAULT '0.00' COMMENT '上年度未缴金额',
  `complement_amount` decimal(16,2) DEFAULT '0.00' COMMENT '补缴金额',
  `delay_pay_amount` decimal(16,2) DEFAULT '0.00' COMMENT '滞纳金',
  `is_delay_pay` tinyint(1) DEFAULT '0' COMMENT '是否减免滞纳金,默认为0--不减免',
  `init_audit_user_id` int(11) DEFAULT NULL COMMENT '初审人',
  `init_audit_date` timestamp NULL DEFAULT NULL COMMENT '初审日期',
  `init_audit_comment` varchar(255) DEFAULT NULL COMMENT '初审意见',
  `jianmian_audit_user_id` int(11) DEFAULT NULL COMMENT '减免操作人',
  `jianmian_audit_date` timestamp NULL DEFAULT NULL COMMENT '减免日期',
  `jianmian_audit_comment` varchar(255) DEFAULT NULL COMMENT '减免意见',
  `verify_audit_user_id` int(11) DEFAULT NULL COMMENT '复审人',
  `verify_audit_date` timestamp NULL DEFAULT NULL COMMENT '复审日期',
  `verify_audit_comment` varchar(255) DEFAULT NULL COMMENT '复审意见',
  `final_audit_user_id` int(11) DEFAULT NULL COMMENT '终审人ID',
  `final_audit_date` timestamp NULL DEFAULT NULL COMMENT '终审时间',
  `final_audit_comment` varchar(255) DEFAULT NULL COMMENT '终审意见',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_exempt` tinyint(1) DEFAULT '0' COMMENT '是否是减免缓,1-免缴, 0-不免缴',
  `reduction_type` int(11) DEFAULT '0' COMMENT '减免缓类型',
  `reducion_apply_user` varchar(45) DEFAULT NULL COMMENT '减免申请人',
  `reduction_date` timestamp NULL DEFAULT NULL COMMENT '减免申请时间',
  `reduction_reason` varchar(255) DEFAULT NULL COMMENT '减免原因',
  `reduction_answer_user` varchar(45) DEFAULT NULL COMMENT '减免申请答复人',
  `reduction_answer_date` varchar(45) DEFAULT NULL COMMENT '答复日期',
  `reduction_answer_option` varchar(255) DEFAULT NULL COMMENT '答复意见',
  `reduction_remark` varchar(255) DEFAULT NULL COMMENT '减免缓 备注',
  `unaudit_years` int(4) DEFAULT NULL COMMENT '未审年数',
  `supplement_year` char(4) DEFAULT NULL COMMENT '补审年份',
  `delay_days` int(11) DEFAULT '0' COMMENT '缴纳滞纳金天数',
  `refuse_times` int(11) DEFAULT '0' COMMENT '被拒绝次数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NewIndex1` (`year`,`company_id`),
  KEY `fk_audit_audit_process_status1_idx` (`audit_process_status`),
  KEY `FK_audit_company_id` (`company_id`),
  KEY `FK_audit_area_code` (`area_code`),
  CONSTRAINT `FK_audit_area_code` FOREIGN KEY (`area_code`) REFERENCES `area` (`code`),
  CONSTRAINT `FK_audit_audit_process_staus` FOREIGN KEY (`audit_process_status`) REFERENCES `audit_process_status` (`id`),
  CONSTRAINT `FK_audit_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3766 DEFAULT CHARSET=utf8 COMMENT='		';

/*Data for the table `audit` */

/*Table structure for table `audit_parameter` */

DROP TABLE IF EXISTS `audit_parameter`;

CREATE TABLE `audit_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '年度',
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '版本表示',
  `year` char(4) DEFAULT NULL COMMENT '审核年份',
  `area_code` char(8) DEFAULT NULL COMMENT '审核地区\n',
  `put_scale` decimal(16,4) NOT NULL COMMENT '安置比例\n',
  `average_salary` decimal(16,2) NOT NULL COMMENT '当年平均工资',
  `audit_start_date` timestamp NULL DEFAULT NULL COMMENT '审核开始日期',
  `audit_close_date` timestamp NULL DEFAULT NULL COMMENT '审核截止日期\n',
  `audit_delay_date` timestamp NULL DEFAULT NULL COMMENT '滞纳金开始日期',
  `audit_delay_rate` decimal(16,4) DEFAULT '0.0050' COMMENT '滞纳金征收比例（每天）',
  `pay_close_date` timestamp NULL DEFAULT NULL COMMENT '支付截止日期\n',
  `pay_limit_days` int(11) DEFAULT NULL COMMENT '支付限制天数\n',
  `decimal_count` int(11) DEFAULT NULL COMMENT '小数位数',
  `retire_age_male` int(11) DEFAULT '60' COMMENT '职工退休年龄-男',
  `retire_age_female` int(11) DEFAULT '50' COMMENT '职工退休年龄-女',
  `eye_one` int(11) DEFAULT NULL COMMENT '视力1级',
  `eye_two` int(11) DEFAULT NULL COMMENT '视力2级',
  `eye_three` int(11) DEFAULT NULL COMMENT '视力3级',
  `eye_four` int(11) DEFAULT NULL COMMENT '视力4级',
  `hearing_one` int(11) DEFAULT NULL COMMENT '听力1级',
  `hearing_two` int(11) DEFAULT NULL COMMENT '听力2级',
  `hearing_three` int(11) DEFAULT NULL COMMENT '听力3级',
  `hearing_four` int(11) DEFAULT NULL COMMENT '听力4级',
  `body_one` int(11) DEFAULT NULL COMMENT '肢体1级',
  `body_two` int(11) DEFAULT NULL COMMENT '肢体2级',
  `body_three` int(11) DEFAULT NULL COMMENT '肢体3级',
  `body_four` int(11) DEFAULT NULL COMMENT '肢体4级',
  `speak_one` int(11) DEFAULT NULL COMMENT '言语1级',
  `speak_two` int(11) DEFAULT NULL COMMENT '言语2级',
  `speak_three` int(11) DEFAULT NULL COMMENT '言语3级',
  `speak_four` int(11) DEFAULT NULL COMMENT '言语4级',
  `intelligence_one` int(11) DEFAULT NULL COMMENT '智力1级',
  `intelligence_two` int(11) DEFAULT NULL COMMENT '智力2级',
  `intelligence_three` int(11) DEFAULT NULL COMMENT '智力3级',
  `intelligence_four` int(11) DEFAULT NULL COMMENT '智力4级',
  `mental_one` int(11) DEFAULT NULL COMMENT '精神1级',
  `mental_two` int(11) DEFAULT NULL COMMENT '精神2级',
  `mental_three` int(11) DEFAULT NULL COMMENT '精神3级',
  `mental_four` int(11) DEFAULT NULL COMMENT '精神4级',
  `multi_one` int(11) DEFAULT NULL COMMENT '多重1级',
  `multi_two` int(11) DEFAULT NULL COMMENT '多重2级',
  `multi_three` int(11) DEFAULT NULL COMMENT '多重3级',
  `multi_four` int(11) DEFAULT NULL COMMENT '多重4级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `NewIndex1` (`year`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='						';

/*Data for the table `audit_parameter` */

/*Table structure for table `audit_process_status` */

DROP TABLE IF EXISTS `audit_process_status`;

CREATE TABLE `audit_process_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_process_status` varchar(45) NOT NULL COMMENT '企业流程状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `priority` varchar(20) DEFAULT NULL COMMENT '排序值',
  `remark` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='审核进程表';

/*Data for the table `audit_process_status` */

insert  into `audit_process_status`(`id`,`audit_process_status`,`create_time`,`update_time`,`user_id`,`is_active`,`version`,`priority`,`remark`) values (1,'未初审','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'1','公用'),(2,'已初审 未复审','2014-06-09 14:13:48','2014-06-09 14:13:48',1,1,1,'2','农垦保障金使用'),(3,'已复审 未缴款','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'6','省残联保障金使用'),(4,'部分缴款','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'7','省残联保障金使用'),(5,'已缴款','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'8','省残联保障金使用'),(6,'达标','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'9','公用'),(7,'已复审 未通过','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'4','公用'),(8,'已复审 未终审','2014-06-09 14:13:48','2014-06-09 14:13:48',1,1,1,'8','农垦保障金使用'),(9,'已终审 未通过','2014-06-09 14:13:48','2014-06-09 14:13:48',1,1,1,'9','农垦保障金使用'),(10,'终审完成','2014-06-09 14:13:48','2014-06-09 14:13:48',1,1,1,'10','农垦保障金使用'),(11,'已初审 未减免','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'2','省残联保障金使用'),(12,'已减免 未复审','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'5','省残联保障金使用'),(13,'已减免 未通过','2014-06-09 14:13:48','2014-06-09 14:13:48',1,0,1,'3','省残联保障金使用');

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID标示',
  `company_code` varchar(45) NOT NULL COMMENT '联合主键--档案号',
  `company_management` varchar(45) DEFAULT NULL COMMENT '主管部门',
  `company_name` varchar(45) NOT NULL COMMENT '企业名称',
  `company_legal` varchar(45) NOT NULL COMMENT '企业法人',
  `company_contact_person` varchar(45) DEFAULT NULL COMMENT '公司联系人',
  `company_organization_code` varchar(128) NOT NULL COMMENT '组织机构代码证号',
  `company_tax_code` varchar(128) DEFAULT NULL COMMENT '税务编码',
  `company_address` varchar(255) NOT NULL COMMENT '企业地址',
  `company_zip_code` char(6) NOT NULL COMMENT '企业邮政编码',
  `company_fax` varchar(45) DEFAULT NULL COMMENT '传真---暂时未写到bean类中',
  `company_type` int(11) NOT NULL COMMENT '企业类型',
  `company_economy_type` int(11) NOT NULL COMMENT '企业经济类型',
  `company_property` int(11) NOT NULL COMMENT '企业属性',
  `area_code` char(8) NOT NULL COMMENT '所在地区code',
  `company_mobile` varchar(45) DEFAULT NULL COMMENT '企业电话',
  `company_phone` varchar(45) DEFAULT NULL COMMENT '企业联系人手机',
  `company_bank` varchar(45) DEFAULT NULL COMMENT '开户银行',
  `company_bank_account` varchar(45) DEFAULT NULL COMMENT '银行账户',
  `company_remark` varchar(1000) DEFAULT NULL COMMENT '企业信息备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`),
  KEY `fk_company_company_type_idx` (`company_type`),
  KEY `fk_company_company_economy_type1_idx` (`company_economy_type`),
  KEY `fk_company_company_property1_idx` (`company_property`),
  KEY `fk_company_area1_idx` (`area_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2200 DEFAULT CHARSET=utf8 COMMENT='公司表';

/*Data for the table `company` */

/*Table structure for table `company_economy_type` */

DROP TABLE IF EXISTS `company_economy_type`;

CREATE TABLE `company_economy_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_economy_type` varchar(45) NOT NULL COMMENT '企业经济类型',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `user_id` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='经济类型';

/*Data for the table `company_economy_type` */

insert  into `company_economy_type`(`id`,`company_economy_type`,`create_time`,`update_time`,`is_active`,`user_id`,`version`) values (1,'国有','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(2,'集体','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(3,'私营','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(4,'个体','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(5,'联营','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(6,'股份制','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(7,'外商投资','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(8,'港澳台投资','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1),(9,'其他','2014-06-09 14:13:49','2014-06-09 14:13:49',0,1,1);

/*Table structure for table `company_log` */

DROP TABLE IF EXISTS `company_log`;

CREATE TABLE `company_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `year` int(11) NOT NULL COMMENT '年度',
  `log_id` int(11) NOT NULL COMMENT '公司id',
  `company_name` varchar(45) NOT NULL COMMENT '企业名称',
  `company_legal` varchar(45) NOT NULL COMMENT '企业法人',
  `company_organization_code` varchar(128) NOT NULL COMMENT '组织机构代码证号',
  `company_tax_code` varchar(128) NOT NULL COMMENT '税务编码',
  `company_address` varchar(255) NOT NULL COMMENT '企业地址',
  `company_zip_code` int(11) NOT NULL COMMENT '企业邮政编码',
  `company_type` int(11) NOT NULL COMMENT '企业类型',
  `company_economy_type` int(11) NOT NULL COMMENT '企业经济类型',
  `company_property` int(11) NOT NULL COMMENT '企业属性',
  `company_phone` varchar(45) DEFAULT NULL COMMENT '企业电话',
  `company_mobile` int(11) DEFAULT NULL COMMENT '企业联系人手机',
  `company_bank` varchar(45) DEFAULT NULL COMMENT '开户银行',
  `company_bank_account` int(11) DEFAULT NULL COMMENT '银行账户',
  `company_remark` varchar(255) DEFAULT NULL COMMENT '企业信息备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `method` varchar(255) NOT NULL,
  PRIMARY KEY (`id`,`year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司表';

/*Data for the table `company_log` */

/*Table structure for table `company_property` */

DROP TABLE IF EXISTS `company_property`;

CREATE TABLE `company_property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_property` varchar(45) NOT NULL COMMENT '企业性质',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `user_id` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='企业性质';

/*Data for the table `company_property` */

insert  into `company_property`(`id`,`company_property`,`create_time`,`update_time`,`is_active`,`user_id`,`version`,`remark`) values (1,'中直企业','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1,'省残联保障金使用'),(2,'省直企业','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1,'省残联保障金使用'),(3,'机关事业单位','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1,'省残联保障金使用'),(4,'机关','2014-06-09 14:13:50','2014-06-09 14:13:50',1,1,1,'农垦保障金使用'),(5,'企事业单位','2014-06-09 14:13:50','2014-06-09 14:13:50',1,1,1,'农垦保障金使用'),(6,'省直/局直单位','2014-06-09 14:13:50','2014-06-09 14:13:50',1,1,1,'农垦保障金使用');

/*Table structure for table `company_type` */

DROP TABLE IF EXISTS `company_type`;

CREATE TABLE `company_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_type` varchar(45) NOT NULL COMMENT '企业类型',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `user_id` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='公司类型';

/*Data for the table `company_type` */

insert  into `company_type`(`id`,`company_type`,`create_time`,`update_time`,`is_active`,`user_id`,`version`) values (1,'企业','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1),(2,'事业','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1),(3,'机关','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1),(4,'机关团体','2014-06-09 14:13:50','2014-06-09 14:13:50',0,1,1);

/*Table structure for table `company_year_worker` */

DROP TABLE IF EXISTS `company_year_worker`;

CREATE TABLE `company_year_worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` char(4) NOT NULL COMMENT '年度',
  `worker_id` int(11) NOT NULL COMMENT '外键--员工id',
  `company_id` int(11) NOT NULL COMMENT '外键--公司id',
  `current_job` varchar(45) DEFAULT NULL COMMENT '现任职位',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_company_year_worker_worker_id` (`worker_id`),
  KEY `FK_company_year_worker_company_id` (`company_id`),
  CONSTRAINT `FK_company_year_worker_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_company_year_worker_worker_id` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `company_year_worker` */

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `primary_key` int(4) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `id` char(10) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `iconcls` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `checked` varchar(45) DEFAULT 'false',
  `user_id` int(11) NOT NULL DEFAULT '1',
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `permission_type_id` int(11) NOT NULL DEFAULT '1',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`primary_key`),
  KEY `fk_menu_permission_type1` (`permission_type_id`),
  CONSTRAINT `fk_menu_permission_type1` FOREIGN KEY (`permission_type_id`) REFERENCES `permission_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8;

/*Data for the table `menu` */

insert  into `menu`(`primary_key`,`id`,`text`,`remark`,`iconcls`,`state`,`url`,`checked`,`user_id`,`is_active`,`permission_type_id`,`create_time`,`update_time`,`version`) values (122,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(123,'10020000','退出',NULL,'','','/quit','false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(124,'20010100','基本档案',NULL,'icon-fold','open','','false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(125,'20010200','初审管理',NULL,'icon-fold','open',NULL,'false',1,0,7,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(126,'20010400','复审管理',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(127,'20010600','缴款管理',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(128,'20010700','文书管理',NULL,'icon-fold','open',NULL,'false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(129,'20010800','查询统计',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(130,'20010900','统计报表',NULL,'icon-fold','open',NULL,'false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(131,'20011000','系统设置',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(135,'30010201','年审单位初审',NULL,NULL,NULL,'audits/list/1','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(136,'30010401','年审单位复审',NULL,NULL,NULL,'audits/list/12','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(137,'30010601','企业缴款列表',NULL,NULL,NULL,'payment/list/3','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(138,'30010701','打印列表',NULL,NULL,NULL,'print/list','',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(139,'30010801','单位档案查询',NULL,NULL,NULL,'query/company/list',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(140,'30010802','单位年审查询',NULL,NULL,NULL,'query/audit/list',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(141,'30010803','残疾职工查询',NULL,NULL,NULL,'query/worker/list',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(142,'30010901','年审情况汇总表(单位性质)',NULL,NULL,NULL,'report/nature',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(143,'30010902','年审情况汇总表(地区)',NULL,NULL,NULL,'report/area',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(144,'30010903','年审情况汇总表(经济类型)',NULL,NULL,NULL,'report/economytype',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(145,'30011001','年审参数列表',NULL,NULL,NULL,'settings/yearAuditParameter',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(146,'30011002','用户设置列表',NULL,NULL,NULL,'settings/user',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(148,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(149,'10020000','退出',NULL,'','','/quit','false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(150,'20010100','基本档案',NULL,'icon-fold','open','','false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(155,'20010800','查询统计',NULL,'icon-fold','open',NULL,'false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(156,'20010900','统计报表',NULL,'icon-fold','open',NULL,'false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(161,'30010201','年审单位初审',NULL,NULL,NULL,'audits/list/1','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(168,'30010901','年审情况汇总表(单位性质)',NULL,NULL,NULL,'report/nature',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(169,'30010902','年审情况汇总表(地区)',NULL,NULL,NULL,'report/area',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(170,'30010903','年审情况汇总表(经济类型)',NULL,NULL,NULL,'report/economytype',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(179,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(180,'10020000','退出',NULL,'','','/quit','false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(181,'20010100','基本档案',NULL,'icon-fold','open','','false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(186,'20010800','查询统计',NULL,'icon-fold','open',NULL,'false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(187,'20010900','统计报表',NULL,'icon-fold','open',NULL,'false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(193,'30010401','年审单位复审',NULL,NULL,NULL,'audits/list/12','',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(199,'30010901','年审情况汇总表(单位性质)',NULL,NULL,NULL,'report/nature',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(200,'30010902','年审情况汇总表(地区)',NULL,NULL,NULL,'report/area',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(201,'30010903','年审情况汇总表(经济类型)',NULL,NULL,NULL,'report/economytype',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(205,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(206,'10020000','退出',NULL,'','','/quit','false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(210,'20010600','缴款管理',NULL,'icon-fold','open',NULL,'false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(212,'20010800','查询统计',NULL,'icon-fold','open',NULL,'false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(213,'20010900','统计报表',NULL,'icon-fold','open',NULL,'false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(220,'30010601','企业缴款列表',NULL,NULL,NULL,'payment/list/3','',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(225,'30010901','年审情况汇总表(单位性质)',NULL,NULL,NULL,'report/nature',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(226,'30010902','年审情况汇总表(地区)',NULL,NULL,NULL,'report/area',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(227,'30010903','年审情况汇总表(经济类型)',NULL,NULL,NULL,'report/economytype',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(228,'30010602','部分缴款列表',NULL,NULL,NULL,'payment/list/4',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(229,'30010602','部分缴款列表',NULL,NULL,NULL,'payment/list/4',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(230,'30010203','已复审未通过',NULL,NULL,NULL,'audits/list/7',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(231,'30010203','已复审未通过',NULL,NULL,NULL,'audits/list/7',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(232,'30010804','缴款记录查询',NULL,NULL,NULL,'query/payment/list',NULL,1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(236,'30011003','复审意见设置',NULL,NULL,NULL,'settings/reply',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(240,'20010800','查询统计',NULL,'icon-fold','open',NULL,'false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(241,'30010104','机关','农垦保障金使用',NULL,NULL,'company/list/4',NULL,0,1,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(242,'30010105','企事业单位','农垦保障金使用',NULL,NULL,'company/list/5',NULL,0,1,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(243,'30010106','省直/局直单位','农垦保障金使用',NULL,NULL,'company/list/6',NULL,0,1,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(245,'30010501','年审单位终审','农垦保障金使用',NULL,NULL,'audits/list/8',NULL,1,1,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(246,'20010500','终审管理','农垦保障金使用','icon-fold','open',NULL,'false',1,1,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(247,'30010205','已终审未通过','农垦保障金使用',NULL,NULL,'audits/list/9',NULL,1,1,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(248,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(249,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,7,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(250,'10020000','退出',NULL,'','','/quit','false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(251,'10020000','退出',NULL,'','','/quit','false',1,0,7,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(252,'20010100','基本档案',NULL,'icon-fold','open',NULL,'false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(256,'30010101','中直企业',NULL,NULL,NULL,'company/list/1','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(257,'30010102','省直企业',NULL,NULL,NULL,'company/list/2','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(258,'30010103','机关事业单位',NULL,NULL,NULL,'company/list/3','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(259,'30010101','中直企业',NULL,NULL,NULL,'company/list/1','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(260,'30010102','省直企业',NULL,NULL,NULL,'company/list/2','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(261,'30010103','机关事业单位',NULL,NULL,NULL,'company/list/3','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(268,'30010101','中直企业',NULL,NULL,NULL,'company/list/1','',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(269,'30010102','省直企业',NULL,NULL,NULL,'company/list/2','',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(270,'30010103','机关事业单位',NULL,NULL,NULL,'company/list/3','',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(271,'30010101','中直企业',NULL,NULL,NULL,'company/list/1','',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(272,'30010102','省直企业',NULL,NULL,NULL,'company/list/2','',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(273,'30010103','机关事业单位',NULL,NULL,NULL,'company/list/3','',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(299,'30010104','机关','农垦保障金使用',NULL,NULL,'company/list/4',NULL,1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(300,'30010105','企事业单位','农垦保障金使用',NULL,NULL,'company/list/5',NULL,1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(301,'30010106','省直/局直单位','农垦保障金使用',NULL,NULL,'company/list/6',NULL,1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(302,'30010104','机关','农垦保障金使用',NULL,NULL,'company/list/4',NULL,1,1,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(303,'30010105','企事业单位','农垦保障金使用',NULL,NULL,'company/list/5',NULL,1,1,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(304,'30010106','省直/局直单位','农垦保障金使用',NULL,NULL,'company/list/6',NULL,1,1,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(305,'30010104','机关','农垦保障金使用',NULL,NULL,'company/list/4',NULL,1,1,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(306,'30010105','企事业单位','农垦保障金使用',NULL,NULL,'company/list/5',NULL,1,1,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(307,'30010106','省直/局直单位','农垦保障金使用',NULL,NULL,'company/list/6',NULL,1,1,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(309,'30010204','公司信息录入','农垦保障金使用',NULL,NULL,'company/list/luru',NULL,1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(310,'30010205','已终审未通过','农垦保障金使用',NULL,NULL,'audits/list/9',NULL,1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(315,'20010200','初审管理',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(316,'20010200','初审管理',NULL,'icon-fold','open',NULL,'false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(318,'30010204','公司信息录入','农垦保障金使用',NULL,NULL,'company/list/luru',NULL,1,1,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(319,'30010204','公司信息录入','农垦保障金使用',NULL,NULL,'company/list/luru',NULL,1,1,7,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(321,'20010500','终审管理','农垦保障金使用','icon-fold','open',NULL,'false',1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(322,'30010501','年审单位终审','农垦保障金使用',NULL,NULL,'audits/list/8',NULL,1,1,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(323,'20010700','文书管理',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(324,'20010700','文书管理',NULL,'icon-fold','open',NULL,'false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(325,'20010700','文书管理',NULL,'icon-fold','open',NULL,'false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(326,'20010700','文书管理',NULL,'icon-fold','open',NULL,'false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(330,'30010701','打印列表',NULL,NULL,NULL,'print/list','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(331,'30010701','打印列表',NULL,NULL,NULL,'print/list','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(332,'30010701','打印列表',NULL,NULL,NULL,'print/list','',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(333,'30010701','打印列表',NULL,NULL,NULL,'print/list','',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(337,'30010801','单位档案查询',NULL,NULL,NULL,'query/company/list',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(338,'30010802','单位年审查询',NULL,NULL,NULL,'query/audit/list',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(339,'30010803','残疾职工查询',NULL,NULL,NULL,'query/worker/list',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(340,'30010801','单位档案查询',NULL,NULL,NULL,'query/company/list',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(341,'30010802','单位年审查询',NULL,NULL,NULL,'query/audit/list',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(342,'30010803','残疾职工查询',NULL,NULL,NULL,'query/worker/list',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(343,'30010801','单位档案查询',NULL,NULL,NULL,'query/company/list',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(344,'30010802','单位年审查询',NULL,NULL,NULL,'query/audit/list',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(345,'30010803','残疾职工查询',NULL,NULL,NULL,'query/worker/list',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(346,'30010801','单位档案查询',NULL,NULL,NULL,'query/company/list',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(347,'30010802','单位年审查询',NULL,NULL,NULL,'query/audit/list',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(348,'30010803','残疾职工查询',NULL,NULL,NULL,'query/worker/list',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(349,'30010804','缴款记录查询',NULL,NULL,NULL,'query/payment/list',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(350,'30010804','缴款记录查询',NULL,NULL,NULL,'query/payment/list',NULL,1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(351,'30010804','缴款记录查询',NULL,NULL,NULL,'query/payment/list',NULL,1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(352,'30010804','缴款记录查询',NULL,NULL,NULL,'query/payment/list',NULL,1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(368,'20010900','统计报表',NULL,'icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(369,'30010901','年审情况汇总表(单位性质)',NULL,NULL,NULL,'report/nature',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(370,'30010902','年审情况汇总表(地区)',NULL,NULL,NULL,'report/area',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(371,'30010903','年审情况汇总表(经济类型)',NULL,NULL,NULL,'report/economytype',NULL,1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(372,'20010300','减免缓管理','黑龙江省残联残保金使用','icon-fold','open',NULL,'false',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(373,'20010300','减免缓管理','黑龙江省残联残保金使用','icon-fold','open',NULL,'false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(374,'30010301','年审单位减免缓','黑龙江省残联残保金使用','','','audits/list/11','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(375,'30010301','年审单位减免缓','黑龙江省残联残保金使用','','','audits/list/11','',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(376,'30010202','已减免未通过','黑龙江省残联残保金使用',NULL,NULL,'audits/list/13','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(377,'30010202','已减免未通过','黑龙江省残联残保金使用',NULL,NULL,'audits/list/13','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(378,'20010900','统计报表',NULL,'icon-fold','open',NULL,'false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(379,'30010901','年审情况汇总表(单位性质)',NULL,NULL,NULL,'report/nature',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(380,'30010902','年审情况汇总表(地区)',NULL,NULL,NULL,'report/area',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(381,'30010903','年审情况汇总表(经济类型)',NULL,NULL,NULL,'report/economytype',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(382,'10010000','保障金菜单',NULL,'icon-fold','open','','false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(383,'10020000','退出',NULL,'','','/quit','false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(384,'20010100','基本档案',NULL,'icon-fold','open','','false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(386,'20010800','查询统计',NULL,'icon-fold','open',NULL,'false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(388,'30010101','中直企业',NULL,NULL,NULL,'company/list/1','',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(389,'30010102','省直企业',NULL,NULL,NULL,'company/list/2','',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(390,'30010103','机关事业单位',NULL,NULL,NULL,'company/list/3','',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(391,'30010104','机关','农垦保障金使用',NULL,NULL,'company/list/4',NULL,1,1,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(392,'30010105','企事业单位','农垦保障金使用',NULL,NULL,'company/list/5',NULL,1,1,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(393,'30010106','省直/局直单位','农垦保障金使用',NULL,NULL,'company/list/6',NULL,1,1,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(394,'20010700','文书管理',NULL,'icon-fold','open',NULL,'false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(395,'30010701','打印列表',NULL,NULL,NULL,'print/list','',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(396,'30010801','单位档案查询',NULL,NULL,NULL,'query/company/list',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(397,'30010802','单位年审查询',NULL,NULL,NULL,'query/audit/list',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(398,'30010803','残疾职工查询',NULL,NULL,NULL,'query/worker/list',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(399,'30010804','缴款记录查询',NULL,NULL,NULL,'query/payment/list',NULL,1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(409,'20010400','复审管理',NULL,'icon-fold','open',NULL,'false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(410,'20011000','系统设置',NULL,'icon-fold',NULL,NULL,'false',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(411,'20011000','系统设置',NULL,'icon-fold',NULL,NULL,'false',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(412,'20011000','系统设置',NULL,'icon-fold',NULL,NULL,'false',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(413,'20011000','系统设置',NULL,'icon-fold',NULL,NULL,'false',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(414,'20011000','系统设置',NULL,'icon-fold',NULL,NULL,'false',1,0,7,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(415,'20011000','系统设置',NULL,'icon-fold',NULL,NULL,'false',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(416,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,1,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(417,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,2,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(418,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,3,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(419,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,4,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(420,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,6,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(421,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,7,'2014-06-06 08:33:08','2014-06-06 08:33:08',1),(422,'30011004','修改密码',NULL,NULL,NULL,'settings/user/pwdedit','',1,0,8,'2014-06-06 08:33:08','2014-06-06 08:33:08',1);

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `year` char(4) DEFAULT NULL COMMENT '出账年份',
  `audit_year` char(4) DEFAULT NULL COMMENT '审核年份',
  `payment_date` timestamp NULL DEFAULT NULL COMMENT '缴款日期',
  `payment_money` decimal(16,2) DEFAULT '0.00' COMMENT '付款金额',
  `payment_person` int(11) DEFAULT NULL COMMENT '缴款操作人id',
  `payment_company` int(11) DEFAULT NULL COMMENT '缴款公司',
  `payment_bill` varchar(128) DEFAULT NULL COMMENT '票据号',
  `payment_type` int(1) DEFAULT NULL COMMENT '缴款方式 1-残联自收 2-地税代征',
  `payment_exceptional` int(5) DEFAULT NULL COMMENT '特殊缴款方式类别--外键',
  `bill_print_date` timestamp NULL DEFAULT NULL COMMENT '打票日期',
  `bill_exchange_date` timestamp NULL DEFAULT NULL COMMENT '换票日期',
  `bill_return` tinyint(1) DEFAULT '0' COMMENT '返票',
  `bill_finance` tinyint(1) DEFAULT '0' COMMENT '财政',
  `bill_obsolete` tinyint(1) DEFAULT '0' COMMENT '是否作废票据',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`),
  KEY `FK_payment_user_id` (`payment_person`),
  KEY `FK_payment_payment_type_id` (`payment_type`),
  CONSTRAINT `FK_payment_payment_type_id` FOREIGN KEY (`payment_type`) REFERENCES `payment_type` (`id`),
  CONSTRAINT `FK_payment_user_id` FOREIGN KEY (`payment_person`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='付款表--记录一年度审核中, 一个公司如果出现多次付款的情况';

/*Data for the table `payment` */

/*Table structure for table `payment_exceptional` */

DROP TABLE IF EXISTS `payment_exceptional`;

CREATE TABLE `payment_exceptional` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `payment_exceptional` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `payment_exceptional` */

insert  into `payment_exceptional`(`id`,`payment_exceptional`,`value`) values (1,'正常','normal'),(2,'手工补录','yellow'),(3,'电汇','blue'),(4,'退款','green');

/*Table structure for table `payment_type` */

DROP TABLE IF EXISTS `payment_type`;

CREATE TABLE `payment_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(45) DEFAULT NULL COMMENT '文本',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `payment_type` */

insert  into `payment_type`(`id`,`text`,`create_time`,`update_time`,`user_id`,`is_active`) values (1,'残联自收','2014-06-09 14:13:51','2014-06-09 14:13:51',NULL,1),(2,'地税代征','2014-06-09 14:13:51','2014-06-09 14:13:51',NULL,1);

/*Table structure for table `permission_type` */

DROP TABLE IF EXISTS `permission_type`;

CREATE TABLE `permission_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_type` varchar(45) NOT NULL COMMENT '权限类型名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `permission_type` */

insert  into `permission_type`(`id`,`permission_type`,`create_time`,`update_time`,`user_id`,`is_active`,`version`,`remark`) values (1,'超级管理员','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,NULL),(2,'初审','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,NULL),(3,'复审','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,NULL),(4,'缴款','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'黑龙江省残联残保金使用'),(5,'查询','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'黑龙江省残联残保金使用'),(6,'终审','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'农垦保障金使用'),(7,'录入','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'农垦保障金使用'),(8,'减免缓','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'黑龙江省残联残保金使用');

/*Table structure for table `reply` */

DROP TABLE IF EXISTS `reply`;

CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_estonian_ci DEFAULT NULL COMMENT '内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除, 0-no, 1-yes',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `reply` */

insert  into `reply`(`id`,`title`,`content`,`create_time`,`update_time`,`user_id`,`is_active`,`version`) values (1,'无','','2014-06-09 14:13:51','2014-06-09 14:13:51',0,0,4),(2,'年龄超标','存在年龄超标的残疾人员工','2014-06-09 14:13:51','2014-06-09 14:13:51',0,0,3),(5,'预定人数过多','预定人数过多','2014-06-09 14:13:51','2014-06-09 14:13:51',0,0,2),(7,'金额计算问题','金额计算有问题','2014-06-09 14:13:51','2014-06-09 14:13:51',0,0,2),(14,'安排人数问题','安排人数存在问题。确认人数是否正确','2014-06-09 14:13:51','2014-06-09 14:13:51',0,0,2);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(16) NOT NULL COMMENT '用户名',
  `user_real_name` varchar(45) DEFAULT NULL COMMENT '真实姓名',
  `user_password` varchar(32) NOT NULL COMMENT '密码',
  `user_email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `user_mobile` varchar(45) NOT NULL COMMENT '手机',
  `user_phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `user_group_id` int(11) NOT NULL COMMENT '用户组 user_group',
  `user_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `user_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`),
  KEY `fk_user_user_group1_idx` (`user_group_id`),
  CONSTRAINT `fk_user_user_group1` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`user_real_name`,`user_password`,`user_email`,`user_mobile`,`user_phone`,`user_group_id`,`user_status`,`create_time`,`update_time`,`user_id`,`is_active`,`version`,`user_remark`) values (1,'admin','管理员','d138768d3b5eca407f0dd579c5ca3767',NULL,'15846538450',NULL,1,1,'2014-06-06 09:18:43','2014-06-06 09:18:43',0,0,5,''),(2,'chushen','初审专家','b71cf3839f16f4e828d2a946e5af551f',NULL,'12321321',NULL,2,1,'2014-06-06 09:18:43','2014-06-06 09:18:43',0,0,3,''),(3,'fushen','复审专家','417e03278b3417e6e863f296ad6d288d',NULL,'1232321',NULL,3,1,'2014-06-06 09:18:43','2014-06-06 09:18:43',0,0,2,''),(4,'jiaokuan','缴款专家','3acfb9cafa6a27d56c38fd30988d62b1',NULL,'12321321321',NULL,4,1,'2014-06-06 09:18:43','2014-06-06 09:18:43',0,0,3,''),(5,'chaxun','查询专家','6fba6418627a2a1dfd7f8643a94324d6',NULL,'13812345678',NULL,5,1,'2014-06-06 09:18:43','2014-06-06 09:18:43',0,0,1,''),(6,'jianmian','减免专家','f5720f216ef7458307844bc4301ee420',NULL,'1381352458',NULL,8,1,'2014-06-06 09:18:43','2014-06-06 09:18:43',NULL,0,1,''),(7,'lufeng','lufeng','706f61a443a2f777788430def71a636a',NULL,'111',NULL,2,1,'2014-06-06 14:57:49','2014-06-06 14:57:49',NULL,0,1,''),(8,'jiaoyang','焦阳','dea2a34eacf853b7b8c062b5f2212930',NULL,'123',NULL,3,1,'2014-06-06 14:58:35','2014-06-09 14:03:39',NULL,0,2,''),(9,'zhangbei','zhangbei','bb22b1ad701cdda350f2b027761e7b5c',NULL,'12313',NULL,2,1,'2014-06-06 14:59:30','2014-06-06 14:59:30',NULL,0,1,''),(10,'tianjing','tianjing','c61e8240cfa3a4b91611c7e7e75ce152',NULL,'12312',NULL,4,1,'2014-06-06 15:00:00','2014-06-06 15:00:00',NULL,0,1,''),(11,'dishuiju','地税局','73b996c12093b38aab04dce1ba497043',NULL,'1234',NULL,4,1,'2014-06-06 15:27:25','2014-06-09 14:04:03',NULL,0,2,'');

/*Table structure for table `user_group` */

DROP TABLE IF EXISTS `user_group`;

CREATE TABLE `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_type_id` int(11) NOT NULL,
  `user_group_name` varchar(45) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `fk_user_group_permission_type1_idx` (`permission_type_id`),
  CONSTRAINT `fk_user_group_permission_type1` FOREIGN KEY (`permission_type_id`) REFERENCES `permission_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `user_group` */

insert  into `user_group`(`id`,`permission_type_id`,`user_group_name`,`create_time`,`update_time`,`user_id`,`is_active`,`version`,`remark`) values (1,1,'超级管理员组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,1,1,NULL),(2,2,'初审用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,NULL),(3,3,'复审用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,NULL),(4,4,'缴款用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'省残联保障金使用'),(5,5,'查询用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'省残联保障金使用'),(6,6,'终审用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,1,1,'农垦保障金使用'),(7,7,'录入用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,1,1,'农垦保障金使用'),(8,8,'减免用户组','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1,'黑龙江省残联残保金使用');

/*Table structure for table `user_log` */

DROP TABLE IF EXISTS `user_log`;

CREATE TABLE `user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `log_id` int(11) NOT NULL,
  `user_name` varchar(16) NOT NULL COMMENT '用户名',
  `user_password` varchar(32) NOT NULL COMMENT '密码',
  `user_email` varchar(255) NOT NULL COMMENT '邮箱',
  `user_mobile` varchar(45) NOT NULL COMMENT '手机',
  `user_phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `user_group_id` int(11) NOT NULL COMMENT '用户组 user_group',
  `user_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态',
  `user_remark` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `method` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_user_group1_idx` (`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户日志表';

/*Data for the table `user_log` */

/*Table structure for table `worker` */

DROP TABLE IF EXISTS `worker`;

CREATE TABLE `worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '职工唯一编号',
  `worker_name` varchar(45) NOT NULL COMMENT '职工姓名',
  `worker_gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `worker_birth` varchar(45) DEFAULT NULL COMMENT '出生日期',
  `worker_birth_year` char(4) DEFAULT NULL COMMENT '出生年份, 供查询使用, 不映射到bean类中',
  `education` int(11) DEFAULT NULL COMMENT '学历--暂时未加入到bean中',
  `hukou_address` varchar(255) DEFAULT NULL COMMENT '户口地址--暂时未加入到bean类中',
  `worker_id_card` varchar(20) NOT NULL COMMENT '身份证号',
  `career_card` varchar(45) DEFAULT NULL COMMENT '就业证号',
  `phone` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `current_job` varchar(45) DEFAULT NULL COMMENT '当前岗位',
  `worker_address` varchar(45) DEFAULT NULL COMMENT '工作人住址',
  `acode_back` char(8) DEFAULT NULL COMMENT '地区code, 备份数据',
  `area_code` char(8) NOT NULL DEFAULT '10230000' COMMENT '地区code,默认值为黑龙江省',
  `worker_handicap_code` varchar(45) NOT NULL COMMENT '残疾证编号',
  `worker_handicap_type` int(11) NOT NULL COMMENT '残疾类别',
  `worker_handicap_level` int(11) NOT NULL COMMENT '残疾级别',
  `is_college` tinyint(1) DEFAULT '0' COMMENT '是否是大学生',
  `is_retired` tinyint(1) DEFAULT '0' COMMENT '是否内退',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `worker_id_card_UNIQUE` (`worker_id_card`),
  UNIQUE KEY `worker_handicap_code_UNIQUE` (`worker_handicap_code`),
  UNIQUE KEY `NewIndex1` (`worker_id_card`,`worker_handicap_code`),
  KEY `fk_worker_handicap_type1_idx` (`worker_handicap_type`),
  KEY `fk_worker_handicap_level1_idx` (`worker_handicap_level`),
  KEY `fk_worker_area1_idx` (`area_code`)
) ENGINE=InnoDB AUTO_INCREMENT=26833 DEFAULT CHARSET=utf8 COMMENT='职工表';

/*Data for the table `worker` */

/*Table structure for table `worker_handicap_level` */

DROP TABLE IF EXISTS `worker_handicap_level`;

CREATE TABLE `worker_handicap_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `handicap_level` varchar(45) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='残疾等级';

/*Data for the table `worker_handicap_level` */

insert  into `worker_handicap_level`(`id`,`handicap_level`,`create_time`,`update_time`,`user_id`,`is_active`,`version`) values (1,'一级','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(2,'二级','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(3,'三级','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(4,'四级','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1);

/*Table structure for table `worker_handicap_type` */

DROP TABLE IF EXISTS `worker_handicap_type`;

CREATE TABLE `worker_handicap_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `handicap_type` varchar(45) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='残疾类型';

/*Data for the table `worker_handicap_type` */

insert  into `worker_handicap_type`(`id`,`handicap_type`,`create_time`,`update_time`,`user_id`,`is_active`,`version`) values (1,'视力残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(2,'听力残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(3,'言语残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(4,'肢体残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(5,'智力残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(6,'精神残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1),(7,'多重残疾','2014-06-09 14:13:51','2014-06-09 14:13:51',1,0,1);

/*Table structure for table `worker_temp` */

DROP TABLE IF EXISTS `worker_temp`;

CREATE TABLE `worker_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '职工唯一编号',
  `worker_name` varchar(45) DEFAULT NULL COMMENT '职工姓名',
  `worker_gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `worker_birth` varchar(45) DEFAULT NULL COMMENT '出生日期',
  `worker_birth_year` char(4) DEFAULT NULL COMMENT '出生年份, 供查询使用, 不映射到bean类中',
  `worker_id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `worker_handicap_code` varchar(45) DEFAULT NULL COMMENT '残疾证编号',
  `worker_handicap_type` int(11) DEFAULT NULL COMMENT '残疾类别',
  `worker_handicap_level` int(11) DEFAULT NULL COMMENT '残疾级别',
  `is_ok` tinyint(1) DEFAULT '0' COMMENT '是否通过验证, 默认为o-false',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  `check_code` varchar(50) DEFAULT NULL COMMENT '检查码, 防止数据冲突',
  `pre_id` int(11) DEFAULT NULL COMMENT '校验时检出为已经存在的数据, 此字段保存该员工在员工表worker中的真实id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职工表';

/*Data for the table `worker_temp` */

/*Table structure for table `audit_company_view` */

DROP TABLE IF EXISTS `audit_company_view`;

/*!50001 DROP VIEW IF EXISTS `audit_company_view` */;
/*!50001 DROP TABLE IF EXISTS `audit_company_view` */;

/*!50001 CREATE TABLE `audit_company_view` (
  `c_id` int(11) DEFAULT '0' COMMENT '唯一ID标示',
  `c_company_code` varchar(45) COMMENT '联合主键--档案号',
  `c_company_management` varchar(45) DEFAULT NULL COMMENT '主管部门',
  `c_company_name` varchar(45) COMMENT '企业名称',
  `c_company_legal` varchar(45) COMMENT '企业法人',
  `c_company_contact_person` varchar(45) DEFAULT NULL COMMENT '公司联系人',
  `c_company_organization_code` varchar(128) COMMENT '组织机构代码证号',
  `c_company_tax_code` varchar(128) DEFAULT NULL COMMENT '税务编码',
  `c_company_address` varchar(255) COMMENT '企业地址',
  `c_company_zip_code` char(6) COMMENT '企业邮政编码',
  `c_company_fax` varchar(45) DEFAULT NULL COMMENT '传真---暂时未写到bean类中',
  `c_company_phone` varchar(45) DEFAULT NULL COMMENT '企业联系人手机',
  `c_company_mobile` varchar(45) DEFAULT NULL COMMENT '企业电话',
  `c_company_bank` varchar(45) DEFAULT NULL COMMENT '开户银行',
  `c_company_bank_account` varchar(45) DEFAULT NULL COMMENT '银行账户',
  `c_company_remark` varchar(1000) DEFAULT NULL COMMENT '企业信息备注',
  `c_create_time` timestamp NULL DEFAULT NULL,
  `c_update_time` timestamp NULL DEFAULT NULL,
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `c_version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `t_id` int(11) DEFAULT '0',
  `t_company_type` varchar(45) COMMENT '企业类型',
  `et_id` int(11) DEFAULT '0',
  `et_company_economy_type` varchar(45) COMMENT '企业经济类型',
  `p_id` int(11) DEFAULT '0',
  `p_company_property` varchar(45) COMMENT '企业性质',
  `a_code` char(8) COMMENT '地区CODE',
  `a_name` varchar(50) COMMENT '地名',
  `au_id` int(11) NOT NULL DEFAULT '0',
  `au_year` char(4) NOT NULL COMMENT '年度',
  `au_company_emp_total` int(11) DEFAULT '0' COMMENT '企业员工总数',
  `au_company_handicap_total` int(11) DEFAULT '0' COMMENT '企业残疾人总数',
  `au_company_predict_total` int(11) DEFAULT '0' COMMENT '预计残疾人数',
  `au_company_should_total` decimal(16,2) DEFAULT '0.00' COMMENT '应该安排人数',
  `au_company_already_total` int(11) DEFAULT '0' COMMENT '已安排人数',
  `au_amount_payable` decimal(16,2) DEFAULT '0.00' COMMENT '应缴金额',
  `au_reduction_amount` decimal(16,2) DEFAULT '0.00' COMMENT '减缴金额',
  `au_actual_amount` decimal(16,2) DEFAULT '0.00' COMMENT '实际应缴金额',
  `au_pay_amount` decimal(16,2) DEFAULT '0.00' COMMENT '实缴总金额',
  `au_remain_amount` decimal(16,2) DEFAULT '0.00' COMMENT '上年度未缴金额',
  `au_complement_amount` decimal(16,2) DEFAULT '0.00' COMMENT '补缴金额',
  `au_delay_pay_amount` decimal(16,2) DEFAULT '0.00' COMMENT '滞纳金',
  `au_is_delay_pay` tinyint(1) DEFAULT '0' COMMENT '是否减免滞纳金,默认为0--不减免',
  `au_init_audit_user_id` int(11) DEFAULT NULL COMMENT '初审人',
  `au_init_audit_date` timestamp NULL DEFAULT NULL COMMENT '初审日期',
  `au_init_audit_comment` varchar(255) DEFAULT NULL COMMENT '初审意见',
  `au_jianmian_audit_user_id` int(11) DEFAULT NULL COMMENT '减免操作人',
  `au_jianmian_audit_date` timestamp NULL DEFAULT NULL COMMENT '减免日期',
  `au_jianmian_audit_comment` varchar(255) DEFAULT NULL COMMENT '减免意见',
  `au_verify_audit_user_id` int(11) DEFAULT NULL COMMENT '复审人',
  `au_verify_audit_date` timestamp NULL DEFAULT NULL COMMENT '复审日期',
  `au_verify_audit_comment` varchar(255) DEFAULT NULL COMMENT '复审意见',
  `au_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `au_is_exempt` tinyint(1) DEFAULT '0' COMMENT '是否是减免缓,1-免缴, 0-不免缴',
  `au_reduction_type` int(11) DEFAULT '0' COMMENT '减免缓类型',
  `au_reducion_apply_user` varchar(45) DEFAULT NULL COMMENT '减免申请人',
  `au_reduction_date` timestamp NULL DEFAULT NULL COMMENT '减免申请时间',
  `au_reduction_reason` varchar(255) DEFAULT NULL COMMENT '减免原因',
  `au_reduction_answer_user` varchar(45) DEFAULT NULL COMMENT '减免申请答复人',
  `au_reduction_answer_date` varchar(45) DEFAULT NULL COMMENT '答复日期',
  `au_reduction_answer_option` varchar(255) DEFAULT NULL COMMENT '答复意见',
  `au_reduction_remark` varchar(255) DEFAULT NULL COMMENT '减免缓 备注',
  `au_unaudit_years` int(4) DEFAULT NULL COMMENT '未审年数',
  `au_supplement_year` char(4) DEFAULT NULL COMMENT '补审年份',
  `au_delay_days` int(11) DEFAULT '0' COMMENT '缴纳滞纳金天数',
  `au_refuse_times` int(11) DEFAULT '0' COMMENT '被拒绝次数',
  `au_create_time` timestamp NULL DEFAULT NULL,
  `au_update_time` timestamp NULL DEFAULT NULL,
  `au_user_id` int(11) DEFAULT NULL,
  `au_is_active` tinyint(1) DEFAULT '0' COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `au_version` int(11) DEFAULT '1' COMMENT '悲观锁--版本号',
  `aup_id` int(11) DEFAULT '0',
  `aup_audit_process_status` varchar(45) COMMENT '企业流程状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 */;

/*Table structure for table `company_worker_view` */

DROP TABLE IF EXISTS `company_worker_view`;

/*!50001 DROP VIEW IF EXISTS `company_worker_view` */;
/*!50001 DROP TABLE IF EXISTS `company_worker_view` */;

/*!50001 CREATE TABLE `company_worker_view` (
  `c_id` int(11) NOT NULL DEFAULT '0' COMMENT '唯一ID标示',
  `c_company_code` varchar(45) NOT NULL COMMENT '联合主键--档案号',
  `c_company_management` varchar(45) DEFAULT NULL COMMENT '主管部门',
  `c_company_name` varchar(45) NOT NULL COMMENT '企业名称',
  `c_company_legal` varchar(45) NOT NULL COMMENT '企业法人',
  `c_company_contact_person` varchar(45) DEFAULT NULL COMMENT '公司联系人',
  `c_company_organization_code` varchar(128) NOT NULL COMMENT '组织机构代码证号',
  `c_company_tax_code` varchar(128) DEFAULT NULL COMMENT '税务编码',
  `c_company_address` varchar(255) NOT NULL COMMENT '企业地址',
  `c_company_zip_code` char(6) NOT NULL COMMENT '企业邮政编码',
  `c_company_fax` varchar(45) DEFAULT NULL COMMENT '传真---暂时未写到bean类中',
  `c_company_phone` varchar(45) DEFAULT NULL COMMENT '企业联系人手机',
  `c_company_mobile` varchar(45) DEFAULT NULL COMMENT '企业电话',
  `c_company_bank` varchar(45) DEFAULT NULL COMMENT '开户银行',
  `c_company_bank_account` varchar(45) DEFAULT NULL COMMENT '银行账户',
  `c_company_remark` varchar(1000) DEFAULT NULL COMMENT '企业信息备注',
  `c_create_time` timestamp NULL DEFAULT NULL,
  `c_update_time` timestamp NULL DEFAULT NULL,
  `c_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `c_is_active` tinyint(1) DEFAULT NULL COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `c_version` int(11) DEFAULT NULL COMMENT '悲观锁--版本号',
  `cyw_id` int(11) NOT NULL DEFAULT '0',
  `cyw_year` char(4) NOT NULL COMMENT '年度',
  `cyw_worker_id` int(11) NOT NULL COMMENT '外键--员工id',
  `cyw_company_id` int(11) NOT NULL COMMENT '外键--公司id',
  `cyw_current_job` varchar(45) DEFAULT NULL COMMENT '现任职位',
  `cyw_create_time` timestamp NULL DEFAULT NULL,
  `cyw_update_time` timestamp NULL DEFAULT NULL,
  `cyw_is_active` tinyint(1) DEFAULT NULL COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `cyw_user_id` int(11) DEFAULT NULL,
  `w_id` int(11) NOT NULL DEFAULT '0' COMMENT '职工唯一编号',
  `w_worker_name` varchar(45) NOT NULL COMMENT '职工姓名',
  `w_worker_gender` varchar(2) DEFAULT NULL COMMENT '性别',
  `w_worker_birth` varchar(45) DEFAULT NULL COMMENT '出生日期',
  `w_worker_birth_year` char(4) DEFAULT NULL COMMENT '出生年份, 供查询使用, 不映射到bean类中',
  `w_worker_id_card` varchar(20) NOT NULL COMMENT '身份证号',
  `w_career_card` varchar(45) DEFAULT NULL COMMENT '就业证号',
  `w_phone` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `w_current_job` varchar(45) DEFAULT NULL COMMENT '当前岗位',
  `w_worker_address` varchar(45) DEFAULT NULL COMMENT '工作人住址',
  `w_worker_handicap_code` varchar(45) NOT NULL COMMENT '残疾证编号',
  `w_is_college` tinyint(1) DEFAULT NULL COMMENT '是否是大学生',
  `w_is_retired` tinyint(1) DEFAULT NULL COMMENT '是否内退',
  `w_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `w_create_time` timestamp NULL DEFAULT NULL,
  `w_update_time` timestamp NULL DEFAULT NULL,
  `w_user_id` int(11) DEFAULT NULL,
  `w_is_active` tinyint(1) DEFAULT NULL COMMENT '是否逻辑删除: 0--no(即未被删除), 1--yes(即被删除)',
  `w_version` int(11) DEFAULT NULL COMMENT '悲观锁--版本号',
  `t_id` int(11) NOT NULL DEFAULT '0',
  `t_handicap_type` varchar(45) DEFAULT NULL,
  `l_id` int(11) NOT NULL DEFAULT '0',
  `l_handicap_level` varchar(45) NOT NULL,
  `a_code` char(8) NOT NULL COMMENT '地区CODE',
  `a_name` varchar(50) NOT NULL COMMENT '地名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 */;

/*View structure for view audit_company_view */

/*!50001 DROP TABLE IF EXISTS `audit_company_view` */;
/*!50001 DROP VIEW IF EXISTS `audit_company_view` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `audit_company_view` AS select `c`.`id` AS `c_id`,`c`.`company_code` AS `c_company_code`,`c`.`company_management` AS `c_company_management`,`c`.`company_name` AS `c_company_name`,`c`.`company_legal` AS `c_company_legal`,`c`.`company_contact_person` AS `c_company_contact_person`,`c`.`company_organization_code` AS `c_company_organization_code`,`c`.`company_tax_code` AS `c_company_tax_code`,`c`.`company_address` AS `c_company_address`,`c`.`company_zip_code` AS `c_company_zip_code`,`c`.`company_fax` AS `c_company_fax`,`c`.`company_phone` AS `c_company_phone`,`c`.`company_mobile` AS `c_company_mobile`,`c`.`company_bank` AS `c_company_bank`,`c`.`company_bank_account` AS `c_company_bank_account`,`c`.`company_remark` AS `c_company_remark`,`c`.`create_time` AS `c_create_time`,`c`.`update_time` AS `c_update_time`,`c`.`user_id` AS `c_user_id`,`c`.`is_active` AS `c_is_active`,`c`.`version` AS `c_version`,`t`.`id` AS `t_id`,`t`.`company_type` AS `t_company_type`,`et`.`id` AS `et_id`,`et`.`company_economy_type` AS `et_company_economy_type`,`p`.`id` AS `p_id`,`p`.`company_property` AS `p_company_property`,`a`.`code` AS `a_code`,`a`.`name` AS `a_name`,`au`.`id` AS `au_id`,`au`.`year` AS `au_year`,`au`.`company_emp_total` AS `au_company_emp_total`,`au`.`company_handicap_total` AS `au_company_handicap_total`,`au`.`company_predict_total` AS `au_company_predict_total`,`au`.`company_should_total` AS `au_company_should_total`,`au`.`company_already_total` AS `au_company_already_total`,`au`.`amount_payable` AS `au_amount_payable`,`au`.`reduction_amount` AS `au_reduction_amount`,`au`.`actual_amount` AS `au_actual_amount`,`au`.`pay_amount` AS `au_pay_amount`,`au`.`remain_amount` AS `au_remain_amount`,`au`.`complement_amount` AS `au_complement_amount`,`au`.`delay_pay_amount` AS `au_delay_pay_amount`,`au`.`is_delay_pay` AS `au_is_delay_pay`,`au`.`init_audit_user_id` AS `au_init_audit_user_id`,`au`.`init_audit_date` AS `au_init_audit_date`,`au`.`init_audit_comment` AS `au_init_audit_comment`,`au`.`jianmian_audit_user_id` AS `au_jianmian_audit_user_id`,`au`.`jianmian_audit_date` AS `au_jianmian_audit_date`,`au`.`jianmian_audit_comment` AS `au_jianmian_audit_comment`,`au`.`verify_audit_user_id` AS `au_verify_audit_user_id`,`au`.`verify_audit_date` AS `au_verify_audit_date`,`au`.`verify_audit_comment` AS `au_verify_audit_comment`,`au`.`remark` AS `au_remark`,`au`.`is_exempt` AS `au_is_exempt`,`au`.`reduction_type` AS `au_reduction_type`,`au`.`reducion_apply_user` AS `au_reducion_apply_user`,`au`.`reduction_date` AS `au_reduction_date`,`au`.`reduction_reason` AS `au_reduction_reason`,`au`.`reduction_answer_user` AS `au_reduction_answer_user`,`au`.`reduction_answer_date` AS `au_reduction_answer_date`,`au`.`reduction_answer_option` AS `au_reduction_answer_option`,`au`.`reduction_remark` AS `au_reduction_remark`,`au`.`unaudit_years` AS `au_unaudit_years`,`au`.`supplement_year` AS `au_supplement_year`,`au`.`delay_days` AS `au_delay_days`,`au`.`refuse_times` AS `au_refuse_times`,`au`.`create_time` AS `au_create_time`,`au`.`update_time` AS `au_update_time`,`au`.`user_id` AS `au_user_id`,`au`.`is_active` AS `au_is_active`,`au`.`version` AS `au_version`,`aup`.`id` AS `aup_id`,`aup`.`audit_process_status` AS `aup_audit_process_status` from ((((((`audit` `au` left join `audit_process_status` `aup` on((`au`.`audit_process_status` = `aup`.`id`))) left join `company` `c` on((`au`.`company_id` = `c`.`id`))) left join `company_type` `t` on((`c`.`company_type` = `t`.`id`))) left join `company_economy_type` `et` on((`c`.`company_economy_type` = `et`.`id`))) left join `company_property` `p` on((`c`.`company_property` = `p`.`id`))) left join `area` `a` on((`c`.`area_code` = `a`.`code`))) group by `au`.`id` order by `c`.`company_code` */;

/*View structure for view company_worker_view */

/*!50001 DROP TABLE IF EXISTS `company_worker_view` */;
/*!50001 DROP VIEW IF EXISTS `company_worker_view` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `company_worker_view` AS (select `c`.`id` AS `c_id`,`c`.`company_code` AS `c_company_code`,`c`.`company_management` AS `c_company_management`,`c`.`company_name` AS `c_company_name`,`c`.`company_legal` AS `c_company_legal`,`c`.`company_contact_person` AS `c_company_contact_person`,`c`.`company_organization_code` AS `c_company_organization_code`,`c`.`company_tax_code` AS `c_company_tax_code`,`c`.`company_address` AS `c_company_address`,`c`.`company_zip_code` AS `c_company_zip_code`,`c`.`company_fax` AS `c_company_fax`,`c`.`company_phone` AS `c_company_phone`,`c`.`company_mobile` AS `c_company_mobile`,`c`.`company_bank` AS `c_company_bank`,`c`.`company_bank_account` AS `c_company_bank_account`,`c`.`company_remark` AS `c_company_remark`,`c`.`create_time` AS `c_create_time`,`c`.`update_time` AS `c_update_time`,`c`.`user_id` AS `c_user_id`,`c`.`is_active` AS `c_is_active`,`c`.`version` AS `c_version`,`cyw`.`id` AS `cyw_id`,`cyw`.`year` AS `cyw_year`,`cyw`.`worker_id` AS `cyw_worker_id`,`cyw`.`company_id` AS `cyw_company_id`,`cyw`.`current_job` AS `cyw_current_job`,`cyw`.`create_time` AS `cyw_create_time`,`cyw`.`update_time` AS `cyw_update_time`,`cyw`.`is_active` AS `cyw_is_active`,`cyw`.`user_id` AS `cyw_user_id`,`w`.`id` AS `w_id`,`w`.`worker_name` AS `w_worker_name`,`w`.`worker_gender` AS `w_worker_gender`,`w`.`worker_birth` AS `w_worker_birth`,`w`.`worker_birth_year` AS `w_worker_birth_year`,`w`.`worker_id_card` AS `w_worker_id_card`,`w`.`career_card` AS `w_career_card`,`w`.`phone` AS `w_phone`,`w`.`current_job` AS `w_current_job`,`w`.`worker_address` AS `w_worker_address`,`w`.`worker_handicap_code` AS `w_worker_handicap_code`,`w`.`is_college` AS `w_is_college`,`w`.`is_retired` AS `w_is_retired`,`w`.`remark` AS `w_remark`,`w`.`create_time` AS `w_create_time`,`w`.`update_time` AS `w_update_time`,`w`.`user_id` AS `w_user_id`,`w`.`is_active` AS `w_is_active`,`w`.`version` AS `w_version`,`t`.`id` AS `t_id`,`t`.`handicap_type` AS `t_handicap_type`,`l`.`id` AS `l_id`,`l`.`handicap_level` AS `l_handicap_level`,`a`.`code` AS `a_code`,`a`.`name` AS `a_name` from (((((`company` `c` join `company_year_worker` `cyw`) join `worker` `w`) join `worker_handicap_type` `t`) join `worker_handicap_level` `l`) join `area` `a`) where ((`c`.`id` = `cyw`.`company_id`) and (`cyw`.`worker_id` = `w`.`id`) and (`w`.`worker_handicap_type` = `t`.`id`) and (`w`.`worker_handicap_level` = `l`.`id`) and (`w`.`area_code` = `a`.`code`))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
