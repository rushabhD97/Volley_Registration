-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 05, 2018 at 06:21 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `assignment_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `authentication`
--

CREATE TABLE IF NOT EXISTS `authentication` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(50) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` text NOT NULL,
  `created_on` datetime NOT NULL,
  `last_signin` datetime NOT NULL,
  `first_name` varchar(15) NOT NULL,
  `middle_name` varchar(15) NOT NULL,
  `last_name` varchar(15) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `gender` varchar(7) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id` (`email_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `authentication`
--

INSERT INTO `authentication` (`id`, `email_id`, `username`, `password`, `created_on`, `last_signin`, `first_name`, `middle_name`, `last_name`, `mobile_no`, `gender`) VALUES
(15, 'rkdcoc4@gmail.com', ' rkd4', '$2y$10$ZcmLHvsPfUu4o7M5jB/H3.DU6kZCELjkMfayZAunfAJ7lLPkH1eaC', '2018-07-05 00:34:22', '2018-07-05 00:35:40', 'rkd2', '', '', '123456789', 'm'),
(16, 'rkdcoc2@gmail.com', ' rkd2', '$2y$10$nOZJDuGhrOe2YqV407v/medhT3dGkO.PgkQPYqlcBzT1DHtv4P5Ru', '2018-07-05 00:38:16', '2018-07-05 00:38:16', 'rkd1', '', '', '123456789', 'm'),
(17, 'rushabhdoshi.97@gmail.com', 'rushabhd', '$2y$10$QC8BpQOQDKfD5cH/eQqZkuiHXQrvLxOLWrlkjYqOO1HnvqBQl/gFi', '2018-07-05 00:46:40', '2018-07-05 09:37:07', 'Rushabh', 'Ketan', 'Doshi', '9677652755', 'Male'),
(18, 'g@g.com', 'g', '$2y$10$JbPnyNKXRszFnIa7SOkJz.iIC8vvXO4cplXPWO5mPA.81C2CvyCSK', '2018-07-05 08:53:31', '2018-07-05 08:53:31', 'r', '', '', '123456789', 'Male'),
(19, 'a@a.con', 'a', '$2y$10$T4Uss6Vmvsqjd51ESZDcoewYAC3xuaqw1DgXeVsRn.m.uiVXDCSU.', '2018-07-05 08:56:53', '2018-07-05 08:56:53', 'a', '', '', '123456789', 'Male'),
(20, 'c@a.com', 'qwwr', '$2y$10$BpWBoAmZn3KGLU0L1uMijuo3l/nldfjZfj0h1zSScLgTTLv6/y9Gy', '2018-07-05 09:42:11', '2018-07-05 09:42:11', 'r', 'k', 'd', '9820273831', 'Male');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
