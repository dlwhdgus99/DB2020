-- MariaDB dump 10.18  Distrib 10.5.8-MariaDB, for osx10.15 (x86_64)
--
-- Host: localhost    Database: Music
-- ------------------------------------------------------
-- Server version	10.5.8-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `Music`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `Music` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `Music`;

--
-- Table structure for table `Album`
--

DROP TABLE IF EXISTS `Album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Album` (
  `album_num` int(11) NOT NULL,
  `album_name` varchar(10) DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `artist_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`album_num`),
  KEY `Album_FK` (`artist_num`),
  CONSTRAINT `Album_FK` FOREIGN KEY (`artist_num`) REFERENCES `Artist` (`artist_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Album`
--

LOCK TABLES `Album` WRITE;
/*!40000 ALTER TABLE `Album` DISABLE KEYS */;
INSERT INTO `Album` VALUES (0,'Lonely','2014-05-26',0),(1,'Mama','2020-10-02',1),(2,'Funk','2017-06-30',2),(3,'Teenage','2012-03-23',3),(4,'Red','2018-07-17',4),(5,'Speak','2018-04-27',5),(6,'Overexpose','2012-07-17',6),(15,'myalbum','2015-03-02',15);
/*!40000 ALTER TABLE `Album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Album_genre`
--

DROP TABLE IF EXISTS `Album_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Album_genre` (
  `genre` varchar(10) NOT NULL,
  `albumnum` int(11) NOT NULL,
  PRIMARY KEY (`genre`,`albumnum`),
  KEY `Album_genre_FK` (`albumnum`),
  CONSTRAINT `Album_genre_FK` FOREIGN KEY (`albumnum`) REFERENCES `Album` (`album_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Album_genre`
--

LOCK TABLES `Album_genre` WRITE;
/*!40000 ALTER TABLE `Album_genre` DISABLE KEYS */;
INSERT INTO `Album_genre` VALUES ('Ballad',1),('Ballad',3),('Hip-Hop',2),('Hip-Hop',4),('POP',0),('POP',3),('POP',5),('POP',6),('POP',15),('R&B',0),('R&B',2),('R&B',5),('Rock',1),('Rock',4),('Rock',6),('Rock',15);
/*!40000 ALTER TABLE `Album_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artist`
--

DROP TABLE IF EXISTS `Artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artist` (
  `artist_num` int(11) NOT NULL,
  `fName` varchar(10) DEFAULT NULL,
  `lName` varchar(10) DEFAULT NULL,
  `debut_yr` int(11) DEFAULT NULL,
  `solo_group` varchar(5) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  PRIMARY KEY (`artist_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artist`
--

LOCK TABLES `Artist` WRITE;
/*!40000 ALTER TABLE `Artist` DISABLE KEYS */;
INSERT INTO `Artist` VALUES (0,'sam','smith',2010,'Solo','M'),(1,'Lany',NULL,2015,'GROUP','M'),(2,'Calvin','Harris',2008,'Solo','M'),(3,'Katy','Perry',2006,'Solo','F'),(4,'Talyor','Swift',2007,'Solo','F'),(5,'Anne','Marie',2015,'Solo','F'),(6,'Maroon5',NULL,2004,'Group','M'),(15,'Lee','Jonghyun',2010,'Solo','M');
/*!40000 ALTER TABLE `Artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Artist_genre`
--

DROP TABLE IF EXISTS `Artist_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artist_genre` (
  `genre` varchar(10) NOT NULL,
  `artistnum` int(11) NOT NULL,
  PRIMARY KEY (`genre`,`artistnum`),
  KEY `Artist_genre_FK` (`artistnum`),
  CONSTRAINT `Artist_genre_FK` FOREIGN KEY (`artistnum`) REFERENCES `Artist` (`artist_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artist_genre`
--

LOCK TABLES `Artist_genre` WRITE;
/*!40000 ALTER TABLE `Artist_genre` DISABLE KEYS */;
INSERT INTO `Artist_genre` VALUES ('Ballad',1),('Ballad',3),('Hip-Hop',2),('Hip-Hop',4),('POP',0),('POP',3),('POP',5),('POP',6),('POP',15),('R&B',0),('R&B',2),('R&B',5),('Rock',1),('ROCK',4),('Rock',6),('Rock',15);
/*!40000 ALTER TABLE `Artist_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Consist_of`
--

DROP TABLE IF EXISTS `Consist_of`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Consist_of` (
  `music_num` int(11) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `playlist_name` varchar(10) NOT NULL,
  `music_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`music_num`,`nickname`,`playlist_name`),
  KEY `Consist_of_FK_1` (`nickname`,`playlist_name`),
  CONSTRAINT `Consist_of_FK` FOREIGN KEY (`music_num`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Consist_of_FK_1` FOREIGN KEY (`nickname`, `playlist_name`) REFERENCES `Playlist` (`sub_nickname`, `list_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Consist_of`
--

LOCK TABLES `Consist_of` WRITE;
/*!40000 ALTER TABLE `Consist_of` DISABLE KEYS */;
INSERT INTO `Consist_of` VALUES (0,'Alice','list1',1),(0,'jong','list2',1),(2,'Chris','list1',2),(2,'jong','list2',3),(3,'Database','list1',2),(3,'jong','list2',4),(4,'DBeaver','list1',2),(4,'jong','list2',5),(5,'Dim','list1',2),(5,'jong','list2',6),(6,'Java','list1',3),(7,'John','list1',3),(8,'jong','list1',3),(9,'Smith','list1',2),(10,'SQL','list1',1),(11,'System','list1',1),(12,'Alice','list1',2),(13,'Bob','list1',2),(14,'Chris','list1',1),(15,'Database','list1',1),(16,'DBeaver','list1',3),(17,'Dim','list1',3),(18,'Java','list1',2),(19,'John','list1',1),(20,'jong','list1',1),(21,'Smith','list1',3),(22,'SQL','list1',2),(23,'System','list1',2),(24,'Alice','list1',3),(25,'Bob','list1',1),(26,'Chris','list1',3),(27,'Database','list1',3),(28,'DBeaver','list1',1),(29,'Dim','list1',1),(30,'Java','list1',1),(31,'John','list1',2),(32,'jong','list1',2),(33,'Smith','list1',1),(34,'SQL','list1',3),(35,'System','list1',3);
/*!40000 ALTER TABLE `Consist_of` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Contains`
--

DROP TABLE IF EXISTS `Contains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contains` (
  `music_num` int(11) NOT NULL,
  `album_num` int(11) NOT NULL,
  `track_num` int(11) DEFAULT NULL,
  `isTitle` char(1) DEFAULT NULL,
  PRIMARY KEY (`music_num`,`album_num`),
  KEY `Contains_FK` (`album_num`),
  CONSTRAINT `Contains_FK` FOREIGN KEY (`album_num`) REFERENCES `Album` (`album_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Contains_FK_1` FOREIGN KEY (`music_num`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Contains`
--

LOCK TABLES `Contains` WRITE;
/*!40000 ALTER TABLE `Contains` DISABLE KEYS */;
INSERT INTO `Contains` VALUES (0,0,1,'N'),(2,0,3,'N'),(3,0,4,'N'),(4,0,5,'Y'),(5,0,6,'N'),(6,1,1,'N'),(7,1,2,'N'),(8,1,3,'Y'),(9,1,4,'N'),(10,1,5,'N'),(11,1,6,'N'),(12,2,1,'N'),(13,2,2,'N'),(14,2,3,'N'),(15,2,4,'Y'),(16,2,5,'N'),(17,2,6,'N'),(18,3,1,'Y'),(19,3,2,'N'),(20,3,3,'N'),(21,3,4,'N'),(22,3,5,'N'),(23,3,6,'N'),(24,4,1,'Y'),(25,4,2,'N'),(26,4,3,'N'),(27,4,4,'N'),(28,4,5,'N'),(29,4,6,'N'),(30,5,1,'Y'),(31,5,2,'N'),(32,5,3,'N'),(33,5,4,'N'),(34,5,5,'N'),(35,5,6,'N'),(36,6,1,'N'),(37,6,2,'N'),(38,6,3,'N'),(39,6,4,'N'),(40,6,5,'Y'),(41,6,6,'N'),(42,15,1,'Y');
/*!40000 ALTER TABLE `Contains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Makes`
--

DROP TABLE IF EXISTS `Makes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Makes` (
  `music_num` int(11) NOT NULL,
  `artist_num` int(11) NOT NULL,
  PRIMARY KEY (`music_num`,`artist_num`),
  KEY `Makes_FK` (`artist_num`),
  CONSTRAINT `Makes_FK` FOREIGN KEY (`artist_num`) REFERENCES `Artist` (`artist_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Makes_FK_1` FOREIGN KEY (`music_num`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Makes`
--

LOCK TABLES `Makes` WRITE;
/*!40000 ALTER TABLE `Makes` DISABLE KEYS */;
INSERT INTO `Makes` VALUES (0,0),(2,0),(3,0),(4,0),(5,0),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,3),(19,3),(20,3),(21,3),(22,3),(23,3),(24,4),(25,4),(26,4),(27,4),(28,4),(29,4),(30,5),(31,5),(32,5),(33,5),(34,5),(35,5),(36,6),(37,6),(38,6),(39,6),(40,6),(41,6),(42,15);
/*!40000 ALTER TABLE `Makes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Manager`
--

DROP TABLE IF EXISTS `Manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Manager` (
  `nickname` varchar(10) NOT NULL,
  `ID` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `fName` varchar(10) DEFAULT NULL,
  `lName` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`nickname`),
  UNIQUE KEY `ID` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Manager`
--

LOCK TABLES `Manager` WRITE;
/*!40000 ALTER TABLE `Manager` DISABLE KEYS */;
INSERT INTO `Manager` VALUES ('abced','abcde','abced','a','bcde'),('jjong','jjong','jjong','lee','jonghyun'),('kim','k','k','kim','seongae');
/*!40000 ALTER TABLE `Manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Music`
--

DROP TABLE IF EXISTS `Music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Music` (
  `mnum` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `playnum` int(11) NOT NULL,
  `mgr_nickname` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`mnum`),
  KEY `Music_FK` (`mgr_nickname`),
  CONSTRAINT `Music_FK` FOREIGN KEY (`mgr_nickname`) REFERENCES `Manager` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Music`
--

LOCK TABLES `Music` WRITE;
/*!40000 ALTER TABLE `Music` DISABLE KEYS */;
INSERT INTO `Music` VALUES (0,'Money',9132,'kim'),(2,'Stay',9799,'kim'),(3,'Leave',10362,'kim'),(4,'I\'m Not The Only one',8496,'kim'),(5,'Told',9512,'kim'),(6,'You',8179,'kim'),(7,'cowboy',8447,'kim'),(8,'heart',9271,'kim'),(9,'last',9257,'kim'),(10,'jesus',9106,'kim'),(11,'paper',10244,'kim'),(12,'Slide',9558,'kim'),(13,'Cash',9329,'kim'),(14,'Heart',9461,'kim'),(15,'Rollin',8332,'kim'),(16,'Holiday',9299,'kim'),(17,'Feels',8347,'kim'),(18,'Teenage',11456,'kim'),(19,'Friday night',9048,'kim'),(20,'California',9271,'kim'),(21,'Firework',8032,'jjong'),(22,'E.T.',9046,'jjong'),(23,'Part Of Me',9578,'jjong'),(24,'State Of Grace',10470,'jjong'),(25,'Red',8769,'jjong'),(26,'22',9561,'jjong'),(27,'Stay Stay',8317,'jjong'),(28,'I Almost Do',9795,'jjong'),(29,'Beautiful',8685,'jjong'),(30,'Cry',7715,'jjong'),(31,'Alarm',9464,'jjong'),(32,'FRIENDS',7616,'jjong'),(33,'2002',10636,'jjong'),(34,'Machine',9725,'jjong'),(35,'Perfect',8713,'jjong'),(36,'One More Night',9599,'jjong'),(37,'Payphone',7920,'jjong'),(38,'Lucky Strike',8287,'jjong'),(39,'Moves',8274,'jjong'),(40,'Love Somebody',8470,'jjong'),(41,'LadyKiller',8222,'jjong'),(42,'mysong',0,'jjong');
/*!40000 ALTER TABLE `Music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Music_genre`
--

DROP TABLE IF EXISTS `Music_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Music_genre` (
  `genre` varchar(10) NOT NULL,
  `g_mnum` int(11) NOT NULL,
  PRIMARY KEY (`genre`,`g_mnum`),
  KEY `Music_genre_FK` (`g_mnum`),
  CONSTRAINT `Music_genre_FK` FOREIGN KEY (`g_mnum`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Music_genre`
--

LOCK TABLES `Music_genre` WRITE;
/*!40000 ALTER TABLE `Music_genre` DISABLE KEYS */;
INSERT INTO `Music_genre` VALUES ('Ballad',5),('Ballad',9),('Ballad',20),('Ballad',22),('Ballad',26),('Ballad',35),('Ballad',37),('Ballad',40),('Hip-Hop',4),('Hip-Hop',10),('Hip-Hop',15),('Hip-Hop',18),('Hip-Hop',24),('Hip-Hop',30),('Hip-Hop',34),('POP',0),('POP',6),('POP',8),('POP',16),('POP',17),('POP',21),('POP',23),('POP',28),('POP',32),('POP',36),('POP',42),('R&B',2),('R&B',7),('R&B',12),('R&B',14),('R&B',19),('R&B',25),('R&B',33),('R&B',39),('Rock',3),('Rock',11),('Rock',13),('Rock',27),('Rock',29),('Rock',31),('Rock',38),('Rock',41),('Rock',42);
/*!40000 ALTER TABLE `Music_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Music_mood`
--

DROP TABLE IF EXISTS `Music_mood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Music_mood` (
  `mood` varchar(10) NOT NULL,
  `M_mnum` int(11) NOT NULL,
  PRIMARY KEY (`mood`,`M_mnum`),
  KEY `Music_mood_FK` (`M_mnum`),
  CONSTRAINT `Music_mood_FK` FOREIGN KEY (`M_mnum`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Music_mood`
--

LOCK TABLES `Music_mood` WRITE;
/*!40000 ALTER TABLE `Music_mood` DISABLE KEYS */;
INSERT INTO `Music_mood` VALUES ('Calm',0),('Calm',2),('Calm',6),('Calm',10),('Calm',17),('Calm',19),('Calm',22),('Calm',27),('Calm',29),('Dreamlike',9),('Dreamlike',12),('Dreamlike',15),('Dreamlike',21),('Dreamlike',24),('Dreamlike',26),('Dreamlike',28),('Dreamlike',32),('Dreamlike',36),('Dreamlike',37),('Dreamlike',40),('Exciting',2),('Exciting',4),('Exciting',9),('Exciting',14),('Exciting',31),('Exciting',34),('Exciting',38),('Exciting',41),('Exciting',42),('Groovy',7),('Groovy',11),('Groovy',16),('Groovy',18),('Groovy',20),('Groovy',22),('Groovy',23),('Groovy',25),('Groovy',28),('Groovy',32),('Groovy',33),('Groovy',37),('Groovy',39),('Groovy',41),('Trendy',3),('Trendy',5),('Trendy',6),('Trendy',8),('Trendy',11),('Trendy',13),('Trendy',14),('Trendy',15),('Trendy',18),('Trendy',25),('Trendy',27),('Trendy',30),('Trendy',33),('Trendy',35),('Trendy',39);
/*!40000 ALTER TABLE `Music_mood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Music_sit`
--

DROP TABLE IF EXISTS `Music_sit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Music_sit` (
  `sit` varchar(10) NOT NULL,
  `S_mnum` int(11) NOT NULL,
  PRIMARY KEY (`sit`,`S_mnum`),
  KEY `Music_sit_FK` (`S_mnum`),
  CONSTRAINT `Music_sit_FK` FOREIGN KEY (`S_mnum`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Music_sit`
--

LOCK TABLES `Music_sit` WRITE;
/*!40000 ALTER TABLE `Music_sit` DISABLE KEYS */;
INSERT INTO `Music_sit` VALUES ('Cafe',0),('Cafe',3),('Cafe',7),('Cafe',9),('Cafe',13),('Cafe',18),('Cafe',22),('Cafe',28),('Cafe',31),('Cafe',34),('Cafe',39),('Driving',3),('Driving',5),('Driving',8),('Driving',11),('Driving',14),('Driving',15),('Driving',20),('Driving',23),('Driving',26),('Driving',31),('Driving',36),('Driving',41),('Driving',42),('Party',10),('Party',12),('Party',27),('Party',29),('Party',32),('Party',35),('Party',37),('Studying',4),('Studying',6),('Studying',8),('Studying',12),('Studying',14),('Studying',16),('Studying',19),('Studying',21),('Studying',23),('Studying',24),('Studying',25),('Studying',28),('Studying',30),('Studying',33),('Studying',38),('Studying',40),('Travel',0),('Travel',2),('Travel',4),('Travel',7),('Travel',17),('Travel',19),('Travel',22),('Travel',24),('Travel',29),('Travel',32),('Travel',37),('Travel',40);
/*!40000 ALTER TABLE `Music_sit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Playlist`
--

DROP TABLE IF EXISTS `Playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Playlist` (
  `sub_nickname` varchar(10) NOT NULL,
  `list_name` varchar(10) NOT NULL,
  `music_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`sub_nickname`,`list_name`),
  CONSTRAINT `Playlist_FK` FOREIGN KEY (`sub_nickname`) REFERENCES `Subscriber` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Playlist`
--

LOCK TABLES `Playlist` WRITE;
/*!40000 ALTER TABLE `Playlist` DISABLE KEYS */;
INSERT INTO `Playlist` VALUES ('Alice','List1',3),('Bob','List1',3),('Chris','List1',3),('Database','List1',3),('DBeaver','List1',3),('Dim','List1',3),('Java','List1',3),('John','List1',3),('jong','List1',3),('jong','list2',0),('jong','list3',0),('Smith','List1',3),('SQL','List1',3),('System','List1',3);
/*!40000 ALTER TABLE `Playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Plays`
--

DROP TABLE IF EXISTS `Plays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Plays` (
  `music_num` int(11) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `playnum` int(11) DEFAULT NULL,
  PRIMARY KEY (`music_num`,`nickname`),
  KEY `Plays_FK` (`nickname`),
  CONSTRAINT `Plays_FK` FOREIGN KEY (`nickname`) REFERENCES `Subscriber` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Plays_FK_1` FOREIGN KEY (`music_num`) REFERENCES `Music` (`mnum`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plays`
--

LOCK TABLES `Plays` WRITE;
/*!40000 ALTER TABLE `Plays` DISABLE KEYS */;
INSERT INTO `Plays` VALUES (0,'ab',0),(0,'abcde',0),(0,'Alice',735),(0,'Bob',370),(0,'Chris',823),(0,'Database',1047),(0,'DBeaver',328),(0,'Dim',895),(0,'Java',417),(0,'John',926),(0,'jong',1085),(0,'Smith',581),(0,'SQL',939),(0,'System',986),(2,'ab',0),(2,'abcde',0),(2,'Alice',1098),(2,'Bob',159),(2,'Chris',1005),(2,'Database',549),(2,'DBeaver',1083),(2,'Dim',975),(2,'Java',139),(2,'John',1021),(2,'jong',722),(2,'Smith',1396),(2,'SQL',998),(2,'System',654),(3,'ab',0),(3,'abcde',0),(3,'Alice',755),(3,'Bob',1059),(3,'Chris',585),(3,'Database',941),(3,'DBeaver',827),(3,'Dim',1061),(3,'Java',1103),(3,'John',903),(3,'jong',840),(3,'Smith',676),(3,'SQL',847),(3,'System',765),(4,'ab',0),(4,'abcde',0),(4,'Alice',173),(4,'Bob',377),(4,'Chris',876),(4,'Database',440),(4,'DBeaver',620),(4,'Dim',671),(4,'Java',1084),(4,'John',747),(4,'jong',1164),(4,'Smith',631),(4,'SQL',888),(4,'System',825),(5,'ab',0),(5,'abcde',0),(5,'Alice',929),(5,'Bob',268),(5,'Chris',245),(5,'Database',948),(5,'DBeaver',616),(5,'Dim',1124),(5,'Java',776),(5,'John',564),(5,'jong',641),(5,'Smith',1365),(5,'SQL',1005),(5,'System',1031),(6,'ab',0),(6,'abcde',0),(6,'Alice',287),(6,'Bob',902),(6,'Chris',552),(6,'Database',467),(6,'DBeaver',413),(6,'Dim',900),(6,'Java',203),(6,'John',758),(6,'jong',858),(6,'Smith',1095),(6,'SQL',873),(6,'System',871),(7,'ab',0),(7,'abcde',0),(7,'Alice',201),(7,'Bob',420),(7,'Chris',469),(7,'Database',815),(7,'DBeaver',847),(7,'Dim',1141),(7,'Java',184),(7,'John',571),(7,'jong',1019),(7,'Smith',811),(7,'SQL',984),(7,'System',985),(8,'ab',0),(8,'abcde',0),(8,'Alice',422),(8,'Bob',992),(8,'Chris',403),(8,'Database',626),(8,'DBeaver',1115),(8,'Dim',658),(8,'Java',868),(8,'John',826),(8,'jong',769),(8,'Smith',1082),(8,'SQL',918),(8,'System',592),(9,'ab',0),(9,'abcde',0),(9,'Alice',527),(9,'Bob',707),(9,'Chris',801),(9,'Database',502),(9,'DBeaver',974),(9,'Dim',1161),(9,'Java',111),(9,'John',1210),(9,'jong',624),(9,'Smith',754),(9,'SQL',880),(9,'System',1006),(10,'ab',0),(10,'abcde',0),(10,'Alice',1087),(10,'Bob',215),(10,'Chris',307),(10,'Database',541),(10,'DBeaver',964),(10,'Dim',588),(10,'Java',806),(10,'John',923),(10,'jong',1085),(10,'Smith',1091),(10,'SQL',860),(10,'System',639),(11,'ab',0),(11,'abcde',0),(11,'Alice',970),(11,'Bob',989),(11,'Chris',776),(11,'Database',763),(11,'DBeaver',1015),(11,'Dim',1045),(11,'Java',308),(11,'John',1238),(11,'jong',954),(11,'Smith',496),(11,'SQL',966),(11,'System',724),(12,'ab',0),(12,'abcde',0),(12,'Alice',396),(12,'Bob',797),(12,'Chris',1022),(12,'Database',1092),(12,'DBeaver',410),(12,'Dim',1070),(12,'Java',947),(12,'John',785),(12,'jong',704),(12,'Smith',824),(12,'SQL',872),(12,'System',639),(13,'ab',0),(13,'abcde',0),(13,'Alice',206),(13,'Bob',911),(13,'Chris',727),(13,'Database',401),(13,'DBeaver',573),(13,'Dim',771),(13,'Java',800),(13,'John',1268),(13,'jong',899),(13,'Smith',1104),(13,'SQL',1011),(13,'System',658),(14,'ab',0),(14,'abcde',0),(14,'Alice',855),(14,'Bob',409),(14,'Chris',310),(14,'Database',895),(14,'DBeaver',895),(14,'Dim',1065),(14,'Java',383),(14,'John',863),(14,'jong',1055),(14,'Smith',835),(14,'SQL',919),(14,'System',977),(15,'ab',0),(15,'abcde',0),(15,'Alice',414),(15,'Bob',111),(15,'Chris',721),(15,'Database',570),(15,'DBeaver',285),(15,'Dim',1055),(15,'Java',882),(15,'John',759),(15,'jong',1104),(15,'Smith',588),(15,'SQL',1009),(15,'System',834),(16,'ab',0),(16,'abcde',0),(16,'Alice',191),(16,'Bob',176),(16,'Chris',898),(16,'Database',777),(16,'DBeaver',1054),(16,'Dim',1160),(16,'Java',935),(16,'John',820),(16,'jong',828),(16,'Smith',866),(16,'SQL',859),(16,'System',735),(17,'ab',0),(17,'abcde',0),(17,'Alice',563),(17,'Bob',344),(17,'Chris',1023),(17,'Database',676),(17,'DBeaver',541),(17,'Dim',688),(17,'Java',172),(17,'John',1193),(17,'jong',817),(17,'Smith',726),(17,'SQL',919),(17,'System',685),(18,'ab',0),(18,'abcde',0),(18,'Alice',1143),(18,'Bob',1028),(18,'Chris',1071),(18,'Database',788),(18,'DBeaver',875),(18,'Dim',1038),(18,'Java',1059),(18,'John',404),(18,'jong',784),(18,'Smith',1259),(18,'SQL',974),(18,'System',1033),(19,'ab',0),(19,'abcde',0),(19,'Alice',390),(19,'Bob',1089),(19,'Chris',598),(19,'Database',562),(19,'DBeaver',894),(19,'Dim',720),(19,'Java',624),(19,'John',673),(19,'jong',1188),(19,'Smith',734),(19,'SQL',891),(19,'System',685),(20,'ab',0),(20,'abcde',0),(20,'Alice',972),(20,'Bob',588),(20,'Chris',399),(20,'Database',514),(20,'DBeaver',758),(20,'Dim',479),(20,'Java',961),(20,'John',1167),(20,'jong',667),(20,'Smith',899),(20,'SQL',829),(20,'System',1038),(21,'ab',0),(21,'abcde',0),(21,'Alice',341),(21,'Bob',562),(21,'Chris',733),(21,'Database',832),(21,'DBeaver',714),(21,'Dim',827),(21,'Java',224),(21,'John',664),(21,'jong',732),(21,'Smith',749),(21,'SQL',984),(21,'System',670),(22,'ab',0),(22,'abcde',0),(22,'Alice',762),(22,'Bob',407),(22,'Chris',324),(22,'Database',624),(22,'DBeaver',1047),(22,'Dim',374),(22,'Java',1104),(22,'John',654),(22,'jong',764),(22,'Smith',1353),(22,'SQL',830),(22,'System',803),(23,'ab',0),(23,'abcde',0),(23,'Alice',1099),(23,'Bob',660),(23,'Chris',201),(23,'Database',728),(23,'DBeaver',874),(23,'Dim',353),(23,'Java',892),(23,'John',1035),(23,'jong',969),(23,'Smith',1065),(23,'SQL',844),(23,'System',858),(24,'ab',0),(24,'abcde',0),(24,'Alice',515),(24,'Bob',904),(24,'Chris',921),(24,'Database',961),(24,'DBeaver',816),(24,'Dim',675),(24,'Java',650),(24,'John',1163),(24,'jong',1182),(24,'Smith',1089),(24,'SQL',940),(24,'System',654),(25,'ab',0),(25,'abcde',0),(25,'Alice',560),(25,'Bob',583),(25,'Chris',893),(25,'Database',330),(25,'DBeaver',425),(25,'Dim',733),(25,'Java',662),(25,'John',587),(25,'jong',1087),(25,'Smith',1346),(25,'SQL',859),(25,'System',704),(26,'ab',0),(26,'abcde',0),(26,'Alice',902),(26,'Bob',640),(26,'Chris',862),(26,'Database',695),(26,'DBeaver',756),(26,'Dim',416),(26,'Java',543),(26,'John',643),(26,'jong',1141),(26,'Smith',1046),(26,'SQL',976),(26,'System',941),(27,'ab',0),(27,'abcde',0),(27,'Alice',170),(27,'Bob',564),(27,'Chris',906),(27,'Database',611),(27,'DBeaver',944),(27,'Dim',756),(27,'Java',448),(27,'John',405),(27,'jong',827),(27,'Smith',753),(27,'SQL',959),(27,'System',974),(28,'ab',0),(28,'abcde',0),(28,'Alice',230),(28,'Bob',981),(28,'Chris',550),(28,'Database',916),(28,'DBeaver',870),(28,'Dim',448),(28,'Java',839),(28,'John',1040),(28,'jong',852),(28,'Smith',1380),(28,'SQL',963),(28,'System',726),(29,'ab',0),(29,'abcde',0),(29,'Alice',970),(29,'Bob',370),(29,'Chris',358),(29,'Database',960),(29,'DBeaver',344),(29,'Dim',639),(29,'Java',669),(29,'John',1234),(29,'jong',651),(29,'Smith',946),(29,'SQL',937),(29,'System',607),(30,'ab',0),(30,'abcde',0),(30,'Alice',1129),(30,'Bob',697),(30,'Chris',243),(30,'Database',761),(30,'DBeaver',310),(30,'Dim',363),(30,'Java',125),(30,'John',1130),(30,'jong',687),(30,'Smith',667),(30,'SQL',954),(30,'System',649),(31,'ab',0),(31,'abcde',0),(31,'Alice',743),(31,'Bob',682),(31,'Chris',384),(31,'Database',635),(31,'DBeaver',520),(31,'Dim',970),(31,'Java',356),(31,'John',970),(31,'jong',1183),(31,'Smith',1205),(31,'SQL',941),(31,'System',875),(32,'ab',0),(32,'abcde',0),(32,'Alice',194),(32,'Bob',140),(32,'Chris',205),(32,'Database',588),(32,'DBeaver',425),(32,'Dim',843),(32,'Java',937),(32,'John',1157),(32,'jong',858),(32,'Smith',513),(32,'SQL',855),(32,'System',901),(33,'ab',0),(33,'abcde',0),(33,'Alice',850),(33,'Bob',671),(33,'Chris',828),(33,'Database',1043),(33,'DBeaver',869),(33,'Dim',1086),(33,'Java',634),(33,'John',460),(33,'jong',952),(33,'Smith',1233),(33,'SQL',957),(33,'System',1053),(34,'ab',0),(34,'abcde',0),(34,'Alice',938),(34,'Bob',515),(34,'Chris',436),(34,'Database',523),(34,'DBeaver',492),(34,'Dim',394),(34,'Java',792),(34,'John',1291),(34,'jong',1196),(34,'Smith',1398),(34,'SQL',914),(34,'System',836),(35,'ab',0),(35,'abcde',0),(35,'Alice',302),(35,'Bob',870),(35,'Chris',393),(35,'Database',815),(35,'DBeaver',1091),(35,'Dim',753),(35,'Java',853),(35,'John',503),(35,'jong',999),(35,'Smith',665),(35,'SQL',869),(35,'System',600),(36,'ab',0),(36,'abcde',0),(36,'Alice',1136),(36,'Bob',374),(36,'Chris',516),(36,'Database',1002),(36,'DBeaver',748),(36,'Dim',1127),(36,'Java',1038),(36,'John',556),(36,'jong',623),(36,'Smith',624),(36,'SQL',960),(36,'System',895),(37,'ab',0),(37,'abcde',0),(37,'Alice',742),(37,'Bob',271),(37,'Chris',503),(37,'Database',786),(37,'DBeaver',708),(37,'Dim',679),(37,'Java',581),(37,'John',702),(37,'jong',778),(37,'Smith',510),(37,'SQL',980),(37,'System',680),(38,'ab',0),(38,'abcde',0),(38,'Alice',678),(38,'Bob',138),(38,'Chris',491),(38,'Database',867),(38,'DBeaver',879),(38,'Dim',356),(38,'Java',841),(38,'John',746),(38,'jong',724),(38,'Smith',1076),(38,'SQL',845),(38,'System',646),(39,'ab',0),(39,'abcde',0),(39,'Alice',424),(39,'Bob',370),(39,'Chris',840),(39,'Database',742),(39,'DBeaver',333),(39,'Dim',603),(39,'Java',1017),(39,'John',742),(39,'jong',658),(39,'Smith',916),(39,'SQL',896),(39,'System',733),(40,'ab',0),(40,'abcde',0),(40,'Alice',294),(40,'Bob',470),(40,'Chris',938),(40,'Database',677),(40,'DBeaver',997),(40,'Dim',788),(40,'Java',395),(40,'John',683),(40,'jong',703),(40,'Smith',928),(40,'SQL',847),(40,'System',750),(41,'ab',0),(41,'abcde',0),(41,'Alice',184),(41,'Bob',415),(41,'Chris',905),(41,'Database',679),(41,'DBeaver',701),(41,'Dim',322),(41,'Java',666),(41,'John',1008),(41,'jong',623),(41,'Smith',1055),(41,'SQL',900),(41,'System',764),(42,'ab',0),(42,'abcde',0),(42,'Alice',0),(42,'Bob',0),(42,'Chris',0),(42,'Database',0),(42,'DBeaver',0),(42,'Dim',0),(42,'Java',0),(42,'John',0),(42,'jong',0),(42,'Smith',0),(42,'SQL',0),(42,'System',0);
/*!40000 ALTER TABLE `Plays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Releases`
--

DROP TABLE IF EXISTS `Releases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Releases` (
  `album_num` int(11) NOT NULL,
  `artist_num` int(11) NOT NULL,
  PRIMARY KEY (`album_num`,`artist_num`),
  KEY `Releases_FK_1` (`artist_num`),
  CONSTRAINT `Releases_FK` FOREIGN KEY (`album_num`) REFERENCES `Album` (`album_num`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Releases_FK_1` FOREIGN KEY (`artist_num`) REFERENCES `Artist` (`artist_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Releases`
--

LOCK TABLES `Releases` WRITE;
/*!40000 ALTER TABLE `Releases` DISABLE KEYS */;
INSERT INTO `Releases` VALUES (0,0),(1,1),(2,2),(3,3),(4,4),(5,5),(6,6);
/*!40000 ALTER TABLE `Releases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subscriber`
--

DROP TABLE IF EXISTS `Subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subscriber` (
  `nickname` varchar(10) NOT NULL,
  `ID` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `fName` varchar(10) DEFAULT NULL,
  `lName` varchar(10) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `bDate` date DEFAULT NULL,
  `mgr_nickname` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`nickname`),
  UNIQUE KEY `ID` (`ID`),
  KEY `Subscriber_FK_1` (`mgr_nickname`),
  CONSTRAINT `Subscriber_FK_1` FOREIGN KEY (`mgr_nickname`) REFERENCES `Manager` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscriber`
--

LOCK TABLES `Subscriber` WRITE;
/*!40000 ALTER TABLE `Subscriber` DISABLE KEYS */;
INSERT INTO `Subscriber` VALUES ('ab','ab','ab','a','b','F','1999-03-02','jjong'),('abcde','abcde','abcde','a','bcde','M','1983-04-29','jjong'),('Alice','alice','alice','alice','hi','F','2003-06-18','jjong'),('Bob','bob','bob','bob',NULL,'M','2004-10-29','jjong'),('Chris','chris','chris','chris',NULL,'M','2002-09-05','jjong'),('Database','database','database','kim','db','F','1998-05-13','jjong'),('DBeaver','dbeaver','dbeaver','dbeaver',NULL,'F','1976-01-09','kim'),('Dim','dim','dim','dim','dim','F','1989-04-02','kim'),('Java','java','java','java','java','M','1978-05-30','kim'),('John','john','john','john','john','M','1988-12-17','kim'),('jong','jjong','jjong','lee','jonghyun','M','1999-03-02','jjong'),('Smith','smith','smith','smith',NULL,'F','1987-10-26','kim'),('SQL','sql','sql','sql','sql','F','1979-03-06','kim'),('System','system','system','lee','system','M','1997-07-26','jjong');
/*!40000 ALTER TABLE `Subscriber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Subscriber_genre`
--

DROP TABLE IF EXISTS `Subscriber_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Subscriber_genre` (
  `genre` varchar(10) NOT NULL,
  `S_nickname` varchar(10) NOT NULL,
  PRIMARY KEY (`genre`,`S_nickname`),
  KEY `Subscriber_genre_FK` (`S_nickname`),
  CONSTRAINT `Subscriber_genre_FK` FOREIGN KEY (`S_nickname`) REFERENCES `Subscriber` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Subscriber_genre`
--

LOCK TABLES `Subscriber_genre` WRITE;
/*!40000 ALTER TABLE `Subscriber_genre` DISABLE KEYS */;
INSERT INTO `Subscriber_genre` VALUES ('Ballad','Bob'),('Ballad','Java'),('Ballad','jong'),('Ballad','Smith'),('Ballad','System'),('Hip-Hop','abcde'),('Hip-Hop','Alice'),('Hip-Hop','Chris'),('Hip-Hop','DBeaver'),('Hip-Hop','System'),('POP','ab'),('POP','Bob'),('POP','Chris'),('POP','DBeaver'),('POP','Dim'),('POP','jong'),('POP','SQL'),('POP','System'),('R&B','abcde'),('R&B','Alice'),('R&B','Java'),('R&B','John'),('R&B','jong'),('R&B','Smith'),('Rock','ab'),('Rock','Dim'),('Rock','John'),('Rock','SQL');
/*!40000 ALTER TABLE `Subscriber_genre` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-05 21:02:10
