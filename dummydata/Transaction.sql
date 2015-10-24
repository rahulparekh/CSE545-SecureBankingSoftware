-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 24, 2015 at 06:02 AM
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
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `TransactionID` varchar(17) NOT NULL,
  `Amount` decimal(13,2) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `Name` varchar(255) NOT NULL,
  `ReceiverAccNumber` varchar(17) NOT NULL,
  `SenderAccNumber` varchar(17) NOT NULL,
  `Status` varchar(10) NOT NULL,
  `Type` varchar(10) NOT NULL,
  `UpdatedAt` datetime NOT NULL,
  `Balance` decimal(13,2) DEFAULT NULL,
  PRIMARY KEY (`TransactionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionID`, `Amount`, `CreatedAt`, `Name`, `ReceiverAccNumber`, `SenderAccNumber`, `Status`, `Type`, `UpdatedAt`, `Balance`) VALUES
('099fee7826ac473aa', '1.50', '2015-09-21 14:35:10', '', '10280870570602403', '10280870570602403', 'completed', 'Credit', '2015-10-21 14:35:10', NULL),
('2d01edafcb7248d58', '512.00', '2015-09-21 15:00:59', '', '10280870570602403', '10280870570602403', 'completed', 'Credit', '2015-10-21 15:00:59', NULL),
('2fbe028a027a48b99', '50.00', '2015-09-21 15:05:57', '', '10280870570602403', '10280870570602403', 'completed', 'Credit', '2015-10-21 15:05:57', NULL),
('3f47e6b2bf4a45ee8', '512.00', '2015-09-21 15:00:34', '', '10280870570602403', '10280870570602403', 'completed', 'Credit', '2015-10-21 15:00:34', NULL),
('42c6296b359d42e7b', '512.00', '2015-09-21 15:00:06', '', '10280870570602403', '10280870570602403', 'completed', 'Credit', '2015-10-21 15:00:06', NULL),
('56ed5ebb5d4340d7b', '512.00', '2015-10-21 14:55:13', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 14:55:13', NULL),
('5d45aadb77344bb59', '-20.00', '2015-10-21 11:03:46', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 11:03:46', NULL),
('5d81b7e39cf04b35b', '512.00', '2015-10-21 15:00:13', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 15:00:13', NULL),
('7697d06ce0fc4cedb', '50.00', '2015-10-21 15:09:18', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 15:09:18', NULL),
('8c0f1866a12243ba8', '512.00', '2015-10-21 14:58:14', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 14:58:14', NULL),
('a3e60c51bdcb42a89', '50.00', '2015-10-21 02:06:50', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 02:06:50', NULL),
('acf55e8693b147f2a', '50.00', '2015-10-21 11:36:59', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 11:36:59', NULL),
('c5a66ef09c7e4399b', '50.00', '2015-10-21 15:09:57', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 15:09:57', NULL),
('cebe26ff5de844a8a', '50.00', '2015-10-21 15:10:43', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-10-21 15:10:43', NULL),
('e48891d9ccf841408', '1.50', '2015-09-30 14:35:40', '', '10280870570602403', '10280870570602403', 'pending', 'Credit', '2015-09-21 14:35:40', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
