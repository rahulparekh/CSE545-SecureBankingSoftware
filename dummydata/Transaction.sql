-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 23, 2015 at 10:10 AM
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
-- Table structure for table `Transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `TransactionID` varchar(17) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `ReceiverAccNumber` varchar(17) NOT NULL,
  `SenderAccNumber` varchar(17) NOT NULL,
  `status` varchar(10) NOT NULL,
  `UpdatedAt` datetime NOT NULL,
  `Amount` decimal(13,2) NOT NULL,
  `Type` varchar(10) NOT NULL,
  PRIMARY KEY (`TransactionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Transaction`
--

INSERT INTO `Transaction` (`TransactionID`, `CreatedAt`, `ReceiverAccNumber`, `SenderAccNumber`, `status`, `UpdatedAt`, `Amount`, `Type`) VALUES
('099fee7826ac473aa', '2015-10-21 14:35:10', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 14:35:10', '1.50', 'Credit'),
('2d01edafcb7248d58', '2015-10-21 15:00:59', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:00:59', '512.00', 'Credit'),
('2fbe028a027a48b99', '2015-10-21 15:05:57', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:05:57', '50.00', 'Credit'),
('3f47e6b2bf4a45ee8', '2015-10-21 15:00:34', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:00:34', '512.00', 'Credit'),
('42c6296b359d42e7b', '2015-10-21 15:00:06', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:00:06', '512.00', 'Credit'),
('56ed5ebb5d4340d7b', '2015-10-21 14:55:13', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 14:55:13', '512.00', 'Credit'),
('5d45aadb77344bb59', '2015-10-21 11:03:46', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 11:03:46', '-20.00', 'Credit'),
('5d81b7e39cf04b35b', '2015-10-21 15:00:13', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:00:13', '512.00', 'Credit'),
('7697d06ce0fc4cedb', '2015-10-21 15:09:18', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:09:18', '50.00', 'Credit'),
('8c0f1866a12243ba8', '2015-10-21 14:58:14', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 14:58:14', '512.00', 'Credit'),
('a3e60c51bdcb42a89', '2015-10-21 02:06:50', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 02:06:50', '50.00', 'Credit'),
('acf55e8693b147f2a', '2015-10-21 11:36:59', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 11:36:59', '50.00', 'Credit'),
('c5a66ef09c7e4399b', '2015-10-21 15:09:57', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:09:57', '50.00', 'Credit'),
('cebe26ff5de844a8a', '2015-10-21 15:10:43', '10280870570602403', '10280870570602403', 'pending', '2015-10-21 15:10:43', '50.00', 'Credit'),
('e48891d9ccf841408', '2015-09-30 14:35:40', '10280870570602403', '10280870570602403', 'pending', '2015-09-21 14:35:40', '1.50', 'Credit');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
