CREATE DATABASE  IF NOT EXISTS `greenbottledb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `greenbottledb`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: greenbottledb
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `abbonamento`
--

DROP TABLE IF EXISTS `abbonamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abbonamento` (
  `prezzo` float DEFAULT NULL,
  `id` bigint NOT NULL,
  `frequenza` enum('GIORNALIERO','MENSILE','SETTIMANALE') DEFAULT NULL,
  `rinnovo` enum('ANNUALE','BIMESTRALE','MENSILE','SEMESTRALE') DEFAULT NULL,
  `tipo` enum('BRONZE','GOLD','SILVER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `abbonamento_seq`
--

DROP TABLE IF EXISTS `abbonamento_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `abbonamento_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(319) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc0r9atamxvbhjjvy5j8da1kam` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` bigint NOT NULL,
  `nome` varchar(256) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoria_seq`
--

DROP TABLE IF EXISTS `categoria_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `bottiglie` int NOT NULL,
  `risparmio` float NOT NULL,
  `abbonamento_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sottoscrizione` datetime(6) DEFAULT NULL,
  `cognome` varchar(30) DEFAULT NULL,
  `nome` varchar(30) DEFAULT NULL,
  `email` varchar(319) NOT NULL,
  `password` varchar(255) NOT NULL,
  `img` longblob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcmxo70m08n43599l3h0h07cc6` (`email`),
  KEY `FKqqmwhnl54f950ur1ijgs04l5v` (`abbonamento_id`),
  CONSTRAINT `FKqqmwhnl54f950ur1ijgs04l5v` FOREIGN KEY (`abbonamento_id`) REFERENCES `abbonamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `composizione`
--

DROP TABLE IF EXISTS `composizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `composizione` (
  `quantita` int NOT NULL,
  `id` bigint NOT NULL,
  `ordine_id` bigint NOT NULL,
  `prodotto_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjjy47e527370m56jlx4i8wghf` (`ordine_id`),
  KEY `FK3dd5g7ctoq2i3sjp8yea2c0rt` (`prodotto_id`),
  CONSTRAINT `FK3dd5g7ctoq2i3sjp8yea2c0rt` FOREIGN KEY (`prodotto_id`) REFERENCES `prodotto` (`id`),
  CONSTRAINT `FKjjy47e527370m56jlx4i8wghf` FOREIGN KEY (`ordine_id`) REFERENCES `ordine` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `composizione_seq`
--

DROP TABLE IF EXISTS `composizione_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `composizione_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `corriere`
--

DROP TABLE IF EXISTS `corriere`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corriere` (
  `id` bigint NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `cognome` varchar(30) NOT NULL,
  `nome` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `corriere_seq`
--

DROP TABLE IF EXISTS `corriere_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corriere_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disposizione`
--

DROP TABLE IF EXISTS `disposizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disposizione` (
  `quantita` int NOT NULL,
  `abbonamento_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL,
  `prodotto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2dlhewnce2dhgk6gmopi3wcls` (`abbonamento_id`),
  KEY `FKsd0du1g9c63x3cux7t3gabdka` (`prodotto_id`),
  CONSTRAINT `FK2dlhewnce2dhgk6gmopi3wcls` FOREIGN KEY (`abbonamento_id`) REFERENCES `abbonamento` (`id`),
  CONSTRAINT `FKsd0du1g9c63x3cux7t3gabdka` FOREIGN KEY (`prodotto_id`) REFERENCES `prodotto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disposizione_seq`
--

DROP TABLE IF EXISTS `disposizione_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disposizione_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `indirizzo`
--

DROP TABLE IF EXISTS `indirizzo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `indirizzo` (
  `civico` int NOT NULL,
  `provincia` varchar(2) NOT NULL,
  `cap` varchar(5) NOT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL,
  `citta` varchar(256) NOT NULL,
  `via` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr5j4ipijb5k7ku4um83mtsi2j` (`cliente_id`),
  CONSTRAINT `FKr5j4ipijb5k7ku4um83mtsi2j` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `indirizzo_seq`
--

DROP TABLE IF EXISTS `indirizzo_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `indirizzo_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordine` (
  `is_ritiro` bit(1) NOT NULL,
  `is_supporto` bit(1) NOT NULL,
  `prezzo` float NOT NULL,
  `admin_id` bigint DEFAULT NULL,
  `cliente_id` bigint NOT NULL,
  `data` datetime(6) NOT NULL,
  `id` bigint NOT NULL,
  `indirizzo_id` bigint NOT NULL,
  `carta` varchar(39) NOT NULL,
  `descrizione` varchar(300) DEFAULT NULL,
  `stato` enum('ACCETTATO','CONSEGNATO','ELABORAZIONE','RIFIUTATO','SPEDITO') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9hamonxu4andj94ajikxxped6` (`admin_id`),
  KEY `FK2uimjo3vaql1lj73x6t3vwaed` (`cliente_id`),
  KEY `FK5ow1xfqhvp140uvyqluodvpn0` (`indirizzo_id`),
  CONSTRAINT `FK2uimjo3vaql1lj73x6t3vwaed` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `FK5ow1xfqhvp140uvyqluodvpn0` FOREIGN KEY (`indirizzo_id`) REFERENCES `indirizzo` (`id`),
  CONSTRAINT `FK9hamonxu4andj94ajikxxped6` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ordine_seq`
--

DROP TABLE IF EXISTS `ordine_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordine_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodotto` (
  `media_voti` float DEFAULT NULL,
  `prezzo` float NOT NULL,
  `quantita` int NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(256) NOT NULL,
  `descrizione` varchar(1024) NOT NULL,
  `img` longblob,
  PRIMARY KEY (`id`),
  KEY `FKp54y50a2i7pdiipduc60tttrw` (`categoria_id`),
  CONSTRAINT `FKp54y50a2i7pdiipduc60tttrw` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recensione`
--

DROP TABLE IF EXISTS `recensione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recensione` (
  `voto` tinyint NOT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL,
  `prodotto_id` bigint DEFAULT NULL,
  `descrizione` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnj6s60n9lu0is01ejwa5yl4dm` (`cliente_id`),
  KEY `FKmck9chsellevum9b1nfc51vna` (`prodotto_id`),
  CONSTRAINT `FKmck9chsellevum9b1nfc51vna` FOREIGN KEY (`prodotto_id`) REFERENCES `prodotto` (`id`),
  CONSTRAINT `FKnj6s60n9lu0is01ejwa5yl4dm` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `recensione_chk_1` CHECK ((`voto` between 0 and 4))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recensione_seq`
--

DROP TABLE IF EXISTS `recensione_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recensione_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

INSERT INTO admin(`email`, `password`) VALUES('admin@greenbottle.it', '1FU7WYQZftZbHQuBb3M5Tw==');

