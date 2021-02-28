-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 28, 2021 at 06:06 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `isomessages`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getISOMessage_Client` ()  BEGIN
    SET @readymessage = (SELECT id FROM pendingmessages WHERE status = 'Pending' LIMIT 1);
    UPDATE pendingmessages SET status = 'Transmitted' WHERE id = @readymessage;
    SELECT * FROM isomessages iso
    WHERE iso.id = (SELECT isoMessageId FROM pendingmessages WHERE id = @readymessage);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMessageResult` (IN `id` VARCHAR(20), IN `messageString` VARCHAR(255))  BEGIN
	INSERT INTO messageresults
	(id, messageTime, message) 
	VALUES (id, NOW(), messageString);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `isomessages`
--

CREATE TABLE `isomessages` (
  `id` int(11) NOT NULL,
  `MTI` varchar(4) NOT NULL,
  `dataelement_4` char(12) NOT NULL,
  `subelement_127_3` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `isomessages`
--

INSERT INTO `isomessages` (`id`, `MTI`, `dataelement_4`, `subelement_127_3`) VALUES
(1, '800', '000000000000', 'test'),
(2, '0800', '000000000120', 'Another Terminal'),
(3, '0800', '000000140010', 'A third test'),
(4, '0800', '000000088020', 'A fourth test');

-- --------------------------------------------------------

--
-- Table structure for table `messageresults`
--

CREATE TABLE `messageresults` (
  `id` int(20) NOT NULL,
  `messageTime` date NOT NULL,
  `message` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `messageresults`
--

INSERT INTO `messageresults` (`id`, `messageTime`, `message`) VALUES
(1, '2021-02-28', '0810<4>000000000000</4><39>00</39>'),
(1, '2021-02-28', '0810<4>000000000000</4><39>00</39>');

-- --------------------------------------------------------

--
-- Table structure for table `pendingmessages`
--

CREATE TABLE `pendingmessages` (
  `id` int(11) NOT NULL,
  `isoMessageId` int(20) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pendingmessages`
--

INSERT INTO `pendingmessages` (`id`, `isoMessageId`, `status`) VALUES
(1, 1, 'Transmitted'),
(2, 1, 'Transmitted'),
(3, 2, 'Transmitted'),
(4, 3, 'Transmitted');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
