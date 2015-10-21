-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 20, 2015 at 08:15 AM
-- Server version: 5.5.36
-- PHP Version: 5.4.27

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sbsgroup11`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `Number` varchar(17) NOT NULL,
  `Balance` decimal(13,2) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `CustomerID` varchar(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Type` int(1) NOT NULL DEFAULT '0',
  `UpdatedAt` datetime NOT NULL,
  PRIMARY KEY (`Number`),
  KEY `FK_kc5x6g2ulrjer4qwebg4q4u4g` (`CustomerID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`Number`, `Balance`, `CreatedAt`, `CustomerID`, `Name`, `Type`, `UpdatedAt`) VALUES
('10280870570602403', '7511.11', '2015-10-19 00:00:00', '55465467897', 'Sun Devil Savigns Account', 1, '2015-10-19 00:00:00'),
('96236586013739048', '40.75', '2015-10-19 00:00:00', '55465467897', 'Sun Devil Checking Account', 0, '2015-10-19 00:00:00');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `FK_kc5x6g2ulrjer4qwebg4q4u4g` FOREIGN KEY (`CustomerID`) REFERENCES `user` (`CustomerID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
