-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: identicall
-- ------------------------------------------------------
-- Server version	5.5.31-0+wheezy1

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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `birth_date` varchar(255) DEFAULT NULL,
  `business_phone` varchar(255) DEFAULT NULL,
  `cell_phone` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `corporate_name` varchar(255) DEFAULT NULL,
  `cpf_cnpj` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `legal_person` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `observation` text,
  `post` bit(1) DEFAULT NULL,
  `problems` bit(1) DEFAULT NULL,
  `recort_date` varchar(255) DEFAULT NULL,
  `residential_phone` varchar(255) DEFAULT NULL,
  `state_abbreviation` varchar(255) DEFAULT NULL,
  `municipality` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2,'Rua Joaquin Siqueira 33','22/03/2012','5599334455','5599254645','Porto Alegre','JJ','99339999933','Centro','jose@gmail.com','5533441122','Física','Fibroso Fontes','Berend Dekens, thank you for reporting this and helping make Ubuntu better. This bug was reported a while ago and there hasn\'t been any activity in it recently. We were wondering if this is still an issue? Can you try with the latest development release of Ubuntu? ISO CD images are available from http://cdimage.ubuntu.com/releases/ .\r\n\r\nIf it remains an issue, could you run the following command from a Terminal (Applications->Accessories->Terminal). It will automatically gather and attach updated debug information to this report.\r\n\r\napport-collect -p linux <replace-with-bug-number>\r\n\r\nAlso, if you could test the latest upstream kernel available that would be great. It will allow additional upstream developers to examine the issue. Refer to https://wiki.ubuntu.com/KernelMainlineBuilds . Once you\'ve tested the upstream kernel, please remove the \'needs-upstream-testing\' tag. This can be done by clicking on the yellow pencil icon next to the tag located at the bottom of the bug description and deleting the \'needs-upstream-testing\' text. Please let us know your results.','','','22/12/208','5599776655','RS','Liberato Salzana'),(3,'Rua dos Andradas 22, 323','22/03/2012','5112345678','5599887766','Porto Alegre','JJ','99339999933','Centro','jose@gmail.com','5533441122','Física','José carlos','Um texto grande.','','','22/12/208','5599776655','RS','Porto Alegre'),(4,'Rua Vidal','22/03/2014','5488998899','5388998899','Porto Alegre','JJ','99339999933','Centro','jose@gmail.com','5888998899','Jurídica','Pedroso Afonso','As you write, xhci_hcd is the kernel module (driver) for USB 3.0, and it appears to cause similar problems in other systems. Maybe you should file a bug report, because I do not see a question in what you have posted here.','\0','','22/12/2000','5788998899','DF','Gravatai');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incoming_call`
--

DROP TABLE IF EXISTS `incoming_call`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incoming_call` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `call_at` time DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incoming_call`
--

LOCK TABLES `incoming_call` WRITE;
/*!40000 ALTER TABLE `incoming_call` DISABLE KEYS */;
/*!40000 ALTER TABLE `incoming_call` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-05  1:17:40
