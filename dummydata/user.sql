-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 24, 2015 at 11:43 AM
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
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `CustomerID` varchar(100) NOT NULL,
  `AddressLine1` varchar(50) NOT NULL,
  `AddressLine2` varchar(50) DEFAULT NULL,
  `CreatedAt` datetime NOT NULL,
  `Email` varchar(255) NOT NULL,
  `EmployeeOverride` int(1) DEFAULT '0',
  `Enabled` int(1) DEFAULT '0',
  `FirstName` varchar(35) NOT NULL,
  `LastLoginAt` datetime NOT NULL,
  `LastName` varchar(70) NOT NULL,
  `MiddleName` varchar(35) DEFAULT NULL,
  `Password` varchar(60) NOT NULL,
  `Phone` varchar(15) NOT NULL,
  `State` varchar(2) NOT NULL,
  `UpdatedAt` datetime NOT NULL,
  `UserType` varchar(8) DEFAULT NULL,
  `ZipCode` varchar(5) NOT NULL,
  PRIMARY KEY (`CustomerID`),
  UNIQUE KEY `UK_ibcfppmacbm81husxf339yq0e` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`CustomerID`, `AddressLine1`, `AddressLine2`, `CreatedAt`, `Email`, `EmployeeOverride`, `Enabled`, `FirstName`, `LastLoginAt`, `LastName`, `MiddleName`, `Password`, `Phone`, `State`, `UpdatedAt`, `UserType`, `ZipCode`) VALUES
('55465467897', '940 S Terrace RD', NULL, '2015-10-19 00:00:00', 'contact@rahulparekh.in', 0, 1, 'Rahul', '2015-10-19 00:00:00', 'Parekh', 'B', '$2a$10$N7n5j8ctR3mlEfYsuXINRORFwsBR12xRNQRMQTz9B35IuHbNIyBca', '4807581112', 'AZ', '2015-10-19 00:00:00', 'Customer', '85281');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
