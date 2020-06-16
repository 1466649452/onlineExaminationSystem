/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : examsystem

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 14/06/2020 23:31:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Administrator
-- ----------------------------
DROP TABLE IF EXISTS `Administrator`;
CREATE TABLE `Administrator` (
  `ad_id` varchar(255) NOT NULL COMMENT '管理员账户id',
  `ad_name` varchar(255) DEFAULT NULL,
  `ad_password` varchar(255) DEFAULT NULL,
  `ad_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ad_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Answer
-- ----------------------------
DROP TABLE IF EXISTS `Answer`;
CREATE TABLE `Answer` (
  `question_id` int NOT NULL COMMENT '题目id',
  `answer` varchar(255) NOT NULL COMMENT '题目答案选项',
  PRIMARY KEY (`answer`,`question_id`),
  KEY `答案-题目` (`question_id`),
  CONSTRAINT `答案-题目` FOREIGN KEY (`question_id`) REFERENCES `Question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Classes
-- ----------------------------
DROP TABLE IF EXISTS `Classes`;
CREATE TABLE `Classes` (
  `class_id` varchar(255) NOT NULL,
  `grade` int DEFAULT NULL COMMENT '年级：例如2017',
  `t_id` varchar(255) DEFAULT NULL COMMENT '班级老师id',
  `stu_number` int DEFAULT NULL COMMENT '班级学生人数',
  `school` varchar(255) DEFAULT NULL COMMENT '班级所在学校',
  PRIMARY KEY (`class_id`),
  KEY `班级-教师` (`t_id`),
  KEY `班级-学校` (`school`),
  CONSTRAINT `班级-学校` FOREIGN KEY (`school`) REFERENCES `School` (`school_name`),
  CONSTRAINT `班级-教师` FOREIGN KEY (`t_id`) REFERENCES `Teacher` (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for CorrectRate
-- ----------------------------
DROP TABLE IF EXISTS `CorrectRate`;
CREATE TABLE `CorrectRate` (
  `question_id` int NOT NULL COMMENT '题目id',
  `paper_id` int NOT NULL COMMENT '试卷id',
  `accuracy` float(255,0) DEFAULT NULL COMMENT '正确率',
  `number` int DEFAULT NULL COMMENT '当前答题学生人数',
  `point` int DEFAULT NULL COMMENT '该题分值',
  PRIMARY KEY (`question_id`,`paper_id`),
  KEY `试题-试卷` (`paper_id`),
  CONSTRAINT `试题` FOREIGN KEY (`question_id`) REFERENCES `Question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `试题-试卷` FOREIGN KEY (`paper_id`) REFERENCES `Test` (`paper_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Question
-- ----------------------------
DROP TABLE IF EXISTS `Question`;
CREATE TABLE `Question` (
  `question_id` int NOT NULL COMMENT '题目id',
  `question_info` varchar(255) DEFAULT NULL COMMENT '题目内容',
  `correct_answer` varchar(255) DEFAULT NULL COMMENT '题目正确答案',
  `type` varchar(255) DEFAULT NULL COMMENT '题目类型',
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for School
-- ----------------------------
DROP TABLE IF EXISTS `School`;
CREATE TABLE `School` (
  `school_name` varchar(255) NOT NULL,
  `school_address` varchar(255) DEFAULT NULL,
  `school_phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`school_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Score
-- ----------------------------
DROP TABLE IF EXISTS `Score`;
CREATE TABLE `Score` (
  `stu_id` varchar(255) NOT NULL COMMENT '考试学生id',
  `paper_id` int NOT NULL COMMENT '试卷id',
  `score` int DEFAULT NULL COMMENT '学生考试成绩',
  `finish` date DEFAULT NULL COMMENT '学生提交时间',
  `stu_ans` json DEFAULT NULL COMMENT '学生答案列表',
  PRIMARY KEY (`stu_id`,`paper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Student
-- ----------------------------
DROP TABLE IF EXISTS `Student`;
CREATE TABLE `Student` (
  `stu_id` varchar(255) NOT NULL COMMENT '学生学号',
  `stu_name` varchar(255) NOT NULL,
  `stu_password` varchar(255) NOT NULL COMMENT '学生账号密码',
  `class_id` varchar(255) DEFAULT NULL COMMENT '班级号',
  `stu_Image` varchar(255) DEFAULT NULL COMMENT '学生头像地址',
  `stu_idcard` varchar(255) DEFAULT NULL COMMENT '学生身份证号',
  `stu_birthdate` date DEFAULT NULL COMMENT '学生出生日期',
  PRIMARY KEY (`stu_id`),
  KEY `学生-班级` (`class_id`),
  CONSTRAINT `学生-班级` FOREIGN KEY (`class_id`) REFERENCES `Classes` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Teacher
-- ----------------------------
DROP TABLE IF EXISTS `Teacher`;
CREATE TABLE `Teacher` (
  `t_id` varchar(255) NOT NULL COMMENT '教师id',
  `t_name` varchar(255) DEFAULT NULL COMMENT '教师姓名',
  `t_password` varchar(255) NOT NULL COMMENT '教师账号登陆密码',
  `t_image` varchar(255) DEFAULT NULL COMMENT '教师头像地址',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for Test
-- ----------------------------
DROP TABLE IF EXISTS `Test`;
CREATE TABLE `Test` (
  `paper_id` int NOT NULL COMMENT '试卷id',
  `paper_name` varchar(255) DEFAULT NULL COMMENT '试卷名称',
  `t_id` varchar(255) DEFAULT NULL COMMENT '出卷人id',
  `start` date NOT NULL COMMENT '考试开始时间',
  `end` date DEFAULT NULL COMMENT '考试结束时间',
  PRIMARY KEY (`paper_id`),
  KEY `试卷-老师` (`t_id`),
  CONSTRAINT `试卷-老师` FOREIGN KEY (`t_id`) REFERENCES `Teacher` (`t_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
