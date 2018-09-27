-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for rps
CREATE DATABASE IF NOT EXISTS `rps` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `rps`;

-- Dumping structure for table rps.contract
CREATE TABLE IF NOT EXISTS `contract` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_status` varchar(50) NOT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table rps.contract: ~2 rows (approximately)
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` (`c_id`, `contract_status`) VALUES
	(1, 'CONTRACTED'),
	(2, 'NON-CONTRACTED');
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;

-- Dumping structure for table rps.rig
CREATE TABLE IF NOT EXISTS `rig` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `rig_typ` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table rps.rig: ~2 rows (approximately)
/*!40000 ALTER TABLE `rig` DISABLE KEYS */;
INSERT INTO `rig` (`r_id`, `rig_typ`) VALUES
	(1, 'JACK UP'),
	(2, 'TENDER ASSISTED');
/*!40000 ALTER TABLE `rig` ENABLE KEYS */;

-- Dumping structure for table rps.rig_plan
CREATE TABLE IF NOT EXISTS `rig_plan` (
  `rp_id` int(11) NOT NULL AUTO_INCREMENT,
  `rig_type` varchar(50) NOT NULL,
  `rig_name` varchar(50) NOT NULL,
  `well_name` varchar(100) NOT NULL,
  `well_typ` varchar(50) NOT NULL,
  `pac` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `block` varchar(50) DEFAULT NULL,
  `plan_days` int(11) NOT NULL,
  `actual_days` int(11) DEFAULT NULL,
  `rig_up` datetime DEFAULT NULL,
  `rig_down` datetime DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `contract_status` varchar(50) NOT NULL,
  PRIMARY KEY (`rp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table rps.rig_plan: ~7 rows (approximately)
/*!40000 ALTER TABLE `rig_plan` DISABLE KEYS */;
INSERT INTO `rig_plan` (`rp_id`, `rig_type`, `rig_name`, `well_name`, `well_typ`, `pac`, `status`, `block`, `plan_days`, `actual_days`, `rig_up`, `rig_down`, `start_date`, `end_date`, `contract_status`) VALUES
	(1, 'JACK UP', 'J/U NAGA 6', 'TUKAU TIMUR A2', 'DEV', 'PCSB', 'COMPLETED', 'SK', 78, 63, '2016-10-17 07:55:00', NULL, '2016-10-22 07:55:00', '2016-12-23 07:55:00', 'SIGNED'),
	(2, 'JACK UP', 'J/U NAGA 6', 'TUKAU TIMUR A5', 'DEV', 'PCSB', 'COMPLETED', 'SK', 78, 65, NULL, NULL, '2016-12-23 07:55:00', '2017-02-27 07:55:00', 'SIGNED'),
	(3, 'JACK UP', 'J/U NAGA 6', 'TUKAU TIMUR A8', 'DEV', 'PCSB', 'COMPLETED', 'SK', 75, 86, NULL, '2017-06-24 07:55:00', '2017-04-09 07:55:00', '2017-06-23 07:55:00', 'SIGNED'),
	(4, 'JACK UP', 'J/U NAGA 6', 'SB309 REALGAR', 'EXP', 'REPSOL', 'ONGOING', 'SB', 45, NULL, '2018-04-23 07:55:00', NULL, '2018-04-24 07:55:00', '2018-06-07 07:55:00', 'SIGNED'),
	(5, 'TENDER ASSISTED', 'TADR - T9', 'F12-A1', 'DEV', 'PCSB', 'COMPLETED', 'SK', 31, 23, '2017-05-03 11:59:10', NULL, '2017-05-08 11:59:28', '2017-05-31 11:59:38', 'NO'),
	(6, 'JACK UP', 'J/U TBA 1', 'ANGSI REVISIT', 'DEV', 'PCSB', 'ONGOING', 'PM', 80, NULL, '2018-09-01 04:34:39', '2018-11-30 04:34:48', '2018-09-06 04:35:25', '2018-11-25 04:35:37', 'NO'),
	(7, 'JACK UP', 'J/U NAGA 6', 'TUKAU TIMUR 6', 'EXP', 'PCSB', 'COMPLETED', 'SB', 10, 9, NULL, NULL, '2017-03-07 07:55:00', '2017-04-08 07:55:00', 'SIGNED');
/*!40000 ALTER TABLE `rig_plan` ENABLE KEYS */;

-- Dumping structure for table rps.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(150) NOT NULL,
  `user_password` varchar(30) NOT NULL,
  `user_roles` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table rps.user: ~2 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_name`, `user_password`, `user_roles`) VALUES
	(1, 'admin', 'password', 'admin'),
	(2, 'dril_eng', 'password', 'drilling engineer');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
