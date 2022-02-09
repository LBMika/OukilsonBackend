-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: oukilson
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `denied_list`
--

LOCK TABLES `denied_list` WRITE;
/*!40000 ALTER TABLE `denied_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `denied_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'538e71c1-2205-4c63-8da0-91dffc90560a','Le super event',4,2,'2022-01-20 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Un event de test',1,0),(2,'2020c1d2-7753-4541-8d34-f530f0bce6ac','Titre : Le super super event de la mort qui tue',1,3,'2022-03-12 11:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,7,'Un event de la mort qui tue de test',2,0),(3,'4f449d3d-fc30-453a-8cd0-cb3fbcab4292','Le super event de la mort qui tue',1,3,'2022-01-08 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Un event de test',3,0),(5,'43a11fbf-7412-463a-a038-a2b2d29c61e5','Le super event de la mort qui tue',1,3,'2022-01-28 11:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Un event de test',4,0),(6,'e31252c9-fcf1-421c-ad0a-6b9f406f863f','C\'est le rush',1,3,'2022-01-20 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Rush hour',6,1),(7,'8ac648c6-01ab-4b5c-9928-c1f14dc6245a','C\'est le rush 2',1,3,'2022-02-15 19:00:00',NULL,'2021-12-22 23:00:00','2022-01-19 11:00:00',3,6,'Rush hour 2',7,1),(12,'4f4d6815-255c-4f90-b35d-d10106d62424','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2021-12-25 11:00:00','2022-01-25 11:00:00',3,5,'Juste un event, bro !',22,0),(13,'00ae5a9e-c564-4d00-875e-e0d20b3fc4be','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-12 10:57:46','2022-01-25 11:00:00',3,5,'Juste un event, bro !',23,0),(14,'11e0e630-b285-4c84-a6e6-d7f286b9aa4a','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-12 13:52:09','2022-01-25 11:00:00',3,5,'Juste un event, bro !',24,0),(15,'c8e897cd-4490-463f-a0c6-73f6c0e6cae0','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-12 13:53:05','2022-01-25 11:00:00',3,5,'Juste un event, bro !',25,0),(16,'1a994bd3-987b-440c-a42c-45c37ed98443','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-12 13:55:14','2022-01-25 11:00:00',3,5,'Juste un event, bro !',26,0),(17,'4e6c0c57-a50b-4e58-bef8-6b7cfae3cfbe','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-12 15:19:44','2022-01-25 11:00:00',3,5,'Juste un event, bro !',27,0),(18,'6a64178b-b376-46ec-a0b4-7cfd4115a9c9','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-14 14:27:44','2022-01-25 11:00:00',3,5,'Juste un event, bro !',28,0),(19,'44744027-8cdc-4e58-a1ad-effffe43d81e','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-14 14:27:47','2022-01-25 11:00:00',3,5,'Juste un event, bro !',29,0),(20,'d08c0839-7470-43f8-ba06-a6525af19ab5','Un event',4,6,'2022-02-02 18:00:00','2022-02-02 22:59:59','2022-01-14 14:27:50','2022-01-25 11:00:00',3,5,'Juste un event, bro !',30,0),(21,'b0b0be26-a4de-41f9-895b-6ed62fae82e2','Un event',4,6,'2022-02-02 18:00:00',NULL,'2022-01-14 14:27:54','2022-01-25 11:00:00',3,5,'Juste un event, bro !',31,0);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event_user`
--

LOCK TABLES `event_user` WRITE;
/*!40000 ALTER TABLE `event_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event_user_in_queue`
--

LOCK TABLES `event_user_in_queue` WRITE;
/*!40000 ALTER TABLE `event_user_in_queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_user_in_queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `friend_list`
--

LOCK TABLES `friend_list` WRITE;
/*!40000 ALTER TABLE `friend_list` DISABLE KEYS */;
INSERT INTO `friend_list` VALUES (11,1),(11,4);
/*!40000 ALTER TABLE `friend_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,'fecc2fb7-8af2-4f80-907e-bcac17739749','7 Wonders',3,7,30,30,10,'Antoine Bauza'),(2,'5f0231d8-8fc6-4221-b5f9-ed36cf4750f5','Innovation',NULL,NULL,NULL,NULL,NULL,NULL),(3,'aaba0a2d-cbdc-4192-a42a-c6135624c341','Myrmes',2,4,NULL,NULL,12,NULL),(4,'445d42eb-0985-45d0-80e5-582292a3a056','Root',NULL,NULL,NULL,NULL,NULL,'Cole Wehrle'),(5,'f6b186a2-e604-4b82-a8b2-010f6df02de5','Mysterium',NULL,NULL,42,NULL,NULL,NULL),(6,'248efeb9-3d1a-4905-89b3-7289ac47c681','Inis',NULL,NULL,60,NULL,14,NULL),(8,'c8b4b920-6435-43a4-a114-865261aa3f57','7 Wonders : Duel',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `game_tag`
--

LOCK TABLES `game_tag` WRITE;
/*!40000 ALTER TABLE `game_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Lille','59900','3 Boulevard de mons'),(2,'Paris','75005','52 Rue des Écoles'),(3,'Oloron sainte Marie','64400',NULL),(4,'Pau',NULL,'19 Boulevard des Pyrénées'),(5,'Bordeaux',NULL,NULL),(6,'Bordeaux','33000',NULL),(7,'Bordeaux','33000','3 rue Poyenne'),(22,'Lyon','69001','10 Rue Joseph Serlin'),(23,'Lyon','69001','10 Rue Joseph Serlin'),(24,'Lyon','69001','10 Rue Joseph Serlin'),(25,'Lyon',NULL,NULL),(26,'Lyon',NULL,NULL),(27,'Lyon',NULL,NULL),(28,'Lyon','69001','10 Rue Joseph Serlin'),(29,'Lyon','69001','10 Rue Joseph Serlin'),(30,'Lyon',NULL,NULL),(31,'Lyon',NULL,NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `token_type`
--

LOCK TABLES `token_type` WRITE;
/*!40000 ALTER TABLE `token_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `token_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `type_tag`
--

LOCK TABLES `type_tag` WRITE;
/*!40000 ALTER TABLE `type_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `type_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Toto','01e55b4659645f43046d52617179bb71ce73201e','Toto','Super','toto@super.com',_binary 'hjmkfhgefdmigunyermoutmouic,oejniuctmgnhmti,gsudxngio,igsuit'),(2,'AlphaGamer','ba65b07de6914d1e13973775e1d056949fc598f6','Jean-Michel','de Petit','jmichel@petit.fr',_binary 'hmkqsnmkqdumixnrurixumnrqouwnfrmioxnuriqgorgixurnmiruxmir,fg'),(3,'Meeple','655d463111c9918877f2f8859b6441099b297d74','Nick','Teton','tnick@germany.de',_binary '6jt746j871je3768l6i7esd34g35g7esrg41jl38i671e34zf35e7f3681a7zu3k1yk37y8y71ez6'),(4,'Gandalf','3cfefd5cf5dfdb9f6745ef806c863e9fcfcbfb61','Grudugnan','Grumeleux','grugru@trouduculdumonde.fr',_binary 'j3l41,8vjg4ch368x7/**/kdghluqd57485eszthflsduqfkrh'),(5,'Papyrus','a95c40f310c47178537882d69dcfe6b8b1dcc080','Marie','Curry','mc@cuisineradioactive.com',_binary 'hkghrelkghremlgerqlhrg46517416743681768sfljugiqrgl'),(6,'LaPlanche','e0a92ec80c51f547c747b9fcdcac283cd8e54deb','Julie','d\'artichaut','julie@artichaut.fr',_binary 'fhgsdgfsdfsdhgfhdlfgrlfgdsqfgdhssg'),(7,'Bidulle','1b516c652801e95bfb5af35ead4a2308561995a4',NULL,NULL,'letest@letest.com',NULL),(11,'Malabar','f8e7166763183dafbacb12a793469711642c5af6',NULL,NULL,'yoyo@malabar.com',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_game_list`
--

LOCK TABLES `user_game_list` WRITE;
/*!40000 ALTER TABLE `user_game_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_game_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_like_list`
--

LOCK TABLES `user_like_list` WRITE;
/*!40000 ALTER TABLE `user_like_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_like_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_token`
--

LOCK TABLES `user_token` WRITE;
/*!40000 ALTER TABLE `user_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-08 14:32:09
