-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (x86_64)
--
-- Host: localhost    Database: odaesan_dev
-- ------------------------------------------------------
-- Server version	5.6.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_info`
--

DROP TABLE IF EXISTS `app_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_info` (
  `id` varchar(45) NOT NULL,
  `tenant_id` varchar(45) NOT NULL COMMENT '用户ID',
  `weixin_id` varchar(100) NOT NULL COMMENT '微信号原始id',
  `app_id` varchar(100) DEFAULT NULL COMMENT '公众号appid',
  `app_secret` varchar(100) DEFAULT NULL COMMENT '公众号appsecret',
  `token` varchar(100) DEFAULT NULL COMMENT '开发者填写的token',
  `type` char(1) NOT NULL DEFAULT '0' COMMENT '微信公众帐类型，0为订阅号，1为服务号',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  `unbind_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绑定的公众号信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_info`
--

LOCK TABLES `app_info` WRITE;
/*!40000 ALTER TABLE `app_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword` (
  `id` varchar(45) NOT NULL,
  `group_id` varchar(45) NOT NULL,
  `content` varchar(500) NOT NULL,
  `keycode` varchar(100) NOT NULL,
  `fuzzy` tinyint(1) NOT NULL DEFAULT '0',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `keyword_keycode_index` (`keycode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword`
--

LOCK TABLES `keyword` WRITE;
/*!40000 ALTER TABLE `keyword` DISABLE KEYS */;
/*!40000 ALTER TABLE `keyword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyword_group`
--

DROP TABLE IF EXISTS `keyword_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyword_group` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `name` varchar(100) NOT NULL,
  `reply_id` varchar(45) DEFAULT NULL,
  `reply_type` enum('text','image','voice','video','music','news') DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyword_group`
--

LOCK TABLES `keyword_group` WRITE;
/*!40000 ALTER TABLE `keyword_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `keyword_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `masssend_message`
--

DROP TABLE IF EXISTS `masssend_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `masssend_message` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `msg_type` varchar(45) NOT NULL COMMENT '消息类型',
  `msg_id` varchar(45) DEFAULT NULL COMMENT '图文消息id',
  `wx_msg_id` varchar(100) DEFAULT NULL COMMENT '微信返回的消息ID',
  `status` varchar(45) DEFAULT NULL,
  `errcode` int(11) DEFAULT NULL,
  `errmsg` varchar(500) DEFAULT NULL,
  `total_count` int(11) DEFAULT NULL COMMENT '预计发送人数',
  `filter_count` int(11) DEFAULT NULL COMMENT '实际发送人数',
  `sent_count` int(11) DEFAULT NULL COMMENT '发送成功的人数',
  `error_count` int(11) DEFAULT NULL COMMENT '发送失败的人数',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户各个公众号群发消息记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `masssend_message`
--

LOCK TABLES `masssend_message` WRITE;
/*!40000 ALTER TABLE `masssend_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `masssend_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_image`
--

DROP TABLE IF EXISTS `material_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_image` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `info_id_idx` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_image`
--

LOCK TABLES `material_image` WRITE;
/*!40000 ALTER TABLE `material_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_media`
--

DROP TABLE IF EXISTS `material_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_media` (
  `id` int(11) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `material_type` enum('image','voice','video','thumb') NOT NULL,
  `material_id` varchar(45) NOT NULL,
  `media_id` varchar(100) NOT NULL,
  `create_time` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_media`
--

LOCK TABLES `material_media` WRITE;
/*!40000 ALTER TABLE `material_media` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_news`
--

DROP TABLE IF EXISTS `material_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_news` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `news_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_news`
--

LOCK TABLES `material_news` WRITE;
/*!40000 ALTER TABLE `material_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_news_image`
--

DROP TABLE IF EXISTS `material_news_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_news_image` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `news_image_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_news_image`
--

LOCK TABLES `material_news_image` WRITE;
/*!40000 ALTER TABLE `material_news_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_news_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_news_item`
--

DROP TABLE IF EXISTS `material_news_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_news_item` (
  `id` varchar(45) NOT NULL,
  `news_id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) DEFAULT NULL,
  `image_id` varchar(45) NOT NULL,
  `digest` varchar(500) NOT NULL,
  `content` longtext NOT NULL,
  `content_source_url` varchar(2000) DEFAULT NULL,
  `sequence` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `news_item_news_id_index` (`news_id`),
  KEY `news_item_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_news_item`
--

LOCK TABLES `material_news_item` WRITE;
/*!40000 ALTER TABLE `material_news_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_news_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_text`
--

DROP TABLE IF EXISTS `material_text`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_text` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `text_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_text`
--

LOCK TABLES `material_text` WRITE;
/*!40000 ALTER TABLE `material_text` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_text` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_video`
--

DROP TABLE IF EXISTS `material_video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_video` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `description` varchar(1000) NOT NULL DEFAULT '',
  `url` varchar(2000) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `info_id_idx` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_video`
--

LOCK TABLES `material_video` WRITE;
/*!40000 ALTER TABLE `material_video` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material_voice`
--

DROP TABLE IF EXISTS `material_voice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material_voice` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(2000) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `info_id_idx` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material_voice`
--

LOCK TABLES `material_voice` WRITE;
/*!40000 ALTER TABLE `material_voice` DISABLE KEYS */;
/*!40000 ALTER TABLE `material_voice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `parent_id` varchar(45) NOT NULL DEFAULT '0',
  `name` varchar(100) NOT NULL,
  `type` enum('click','view') DEFAULT NULL,
  `keycode` varchar(500) DEFAULT NULL,
  `url` varchar(2000) DEFAULT NULL,
  `reply_id` varchar(45) DEFAULT NULL,
  `reply_type` enum('text','image','voice','video','music','news') DEFAULT NULL,
  `sequence` int(11) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `menu_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` varchar(100) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `type` enum('text','image','voice','video','location','link','event') NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `sender_id` varchar(100) NOT NULL,
  `ivrmsg` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`info_id`),
  KEY `message_sender_id_index` (`sender_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_content`
--

DROP TABLE IF EXISTS `message_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_content` (
  `msg_id` varchar(100) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `meta_key` varchar(100) NOT NULL,
  `meta_value` varchar(2000) NOT NULL,
  PRIMARY KEY (`msg_id`,`meta_key`,`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_content`
--

LOCK TABLES `message_content` WRITE;
/*!40000 ALTER TABLE `message_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrcode_scene`
--

DROP TABLE IF EXISTS `qrcode_scene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qrcode_scene` (
  `id` varchar(45) NOT NULL,
  `scene_id` int(11) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `data` varchar(2000) NOT NULL,
  `ticket` varchar(500) NOT NULL,
  `url` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrcode_scene`
--

LOCK TABLES `qrcode_scene` WRITE;
/*!40000 ALTER TABLE `qrcode_scene` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrcode_scene` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `openid` varchar(100) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `gender` mediumint(9) DEFAULT NULL,
  `avatar` varchar(2000) DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `unionid` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `subscribed` tinyint(1) NOT NULL DEFAULT '0',
  `subscribe_time` datetime DEFAULT NULL,
  `unsubscribe_time` datetime DEFAULT NULL,
  PRIMARY KEY (`openid`),
  KEY `user_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group` (
  `id` varchar(45) NOT NULL,
  `info_id` varchar(45) NOT NULL,
  `wx_group_id` varchar(45) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`info_id`),
  UNIQUE KEY `name` (`info_id`,`name`),
  KEY `user_group_info_id_index` (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group_mapping`
--

DROP TABLE IF EXISTS `user_group_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group_mapping` (
  `info_id` varchar(45) NOT NULL,
  `openid` varchar(100) NOT NULL,
  `group_id` varchar(45) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`info_id`,`openid`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group_mapping`
--

LOCK TABLES `user_group_mapping` WRITE;
/*!40000 ALTER TABLE `user_group_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-29 16:33:24
