-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 02, 2024 at 05:24 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `product_java`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity_log`
--

CREATE TABLE `activity_log` (
  `id_act` bigint(20) UNSIGNED NOT NULL,
  `id_user` int(11) NOT NULL,
  `date` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `tipe_act` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity_log`
--

INSERT INTO `activity_log` (`id_act`, `id_user`, `date`, `username`, `tipe_act`) VALUES
(122, 1, '2024-04-02 08:52:33', 'healm', 'LI'),
(123, 1, '2024-04-02 08:52:39', 'healm', 'TR'),
(124, 1, '2024-04-02 08:52:42', 'healm', 'TR YES'),
(125, 1, '2024-04-01 12:34:56', 'healm', 'LI'),
(126, 3, '2024-04-02 08:55:08', 'admin', 'LI'),
(127, 3, '2024-04-02 08:57:58', 'admin', 'LI'),
(128, 3, '2024-04-02 08:59:16', 'admin', 'LI'),
(129, 3, '2024-04-02 09:12:06', 'admin', 'LI'),
(130, 3, '2024-04-02 09:13:24', 'admin', 'LI'),
(131, 3, '2024-04-02 09:15:16', 'admin', 'LI'),
(132, 1, '2024-04-02 09:19:34', 'healm', 'LI'),
(133, 1, '2024-04-02 09:19:38', 'healm', 'TR'),
(134, 1, '2024-04-02 09:19:43', 'healm', 'TR YES'),
(135, 3, '2024-04-02 09:23:22', 'admin', 'LI'),
(136, 1, '2024-04-02 09:28:22', 'healm', 'LI'),
(137, 1, '2024-04-02 09:28:39', 'healm', 'LI'),
(138, 3, '2024-04-02 09:41:58', 'admin', 'LI'),
(139, 3, '2024-04-02 09:46:41', 'admin', 'LI'),
(140, 1, '2024-04-02 09:57:41', 'healm', 'LI'),
(141, 1, '2024-04-02 09:58:27', 'healm', 'TR'),
(142, 1, '2024-04-02 09:59:23', 'healm', 'TR NO'),
(143, 3, '2024-04-02 10:05:59', 'admin', 'LI');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `harga` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `nama`, `harga`) VALUES
(1, 'Apple', 10000),
(2, 'Chitatoe', 20000),
(3, 'Britos', 5000),
(7, 'OrengS', 10000),
(8, 'Oreo', 2000),
(10, 'Dorito', 9990),
(11, 'Jeruk', 12000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction_log`
--

CREATE TABLE `transaction_log` (
  `transaction_id` bigint(20) UNSIGNED NOT NULL,
  `user_id` int(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `date` varchar(30) NOT NULL,
  `item` varchar(30) NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction_log`
--

INSERT INTO `transaction_log` (`transaction_id`, `user_id`, `username`, `date`, `item`, `total`) VALUES
(3, 1, 'healm', '2024-04-02 05:36:21', 'Apple', 10000),
(4, 1, 'healm', '2024-04-02 05:36:21', 'Apple', 10000),
(5, 1, 'healm', '2024-04-02 05:36:21', 'Chitatoe', 20000),
(6, 1, 'healm', '2024-04-02 07:42:18', 'Apple', 10000),
(7, 1, 'healm', '2024-04-02 07:56:30', 'Apple', 10000),
(8, 1, 'healm', '2024-04-02 07:56:30', 'Apple', 10000),
(9, 1, 'healm', '2024-04-02 07:56:30', 'Apple', 10000),
(10, 1, 'healm', '2024-04-02 08:00:25', 'Apple', 10000),
(11, 1, 'healm', '2024-04-02 08:00:25', 'Apple', 10000),
(12, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(13, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(14, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(15, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(16, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(17, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(18, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(19, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(20, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(21, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(22, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(23, 8, 'healm2', '2024-04-02 08:24:32', 'Apple', 10000),
(24, 1, 'healm', '2024-04-02 08:48:55', 'Apple', 10000),
(25, 1, 'healm', '2024-04-02 08:48:55', 'Apple', 10000),
(26, 1, 'healm', '2024-04-02 08:48:56', 'Apple', 10000),
(27, 1, 'healm', '2024-04-02 08:50:05', 'Apple', 10000),
(28, 1, 'healm', '2024-04-02 08:50:05', 'Apple', 10000),
(29, 1, 'healm', '2024-04-02 08:51:50', 'Apple', 10000),
(30, 1, 'healm', '2024-04-02 08:51:50', 'Apple', 10000),
(31, 1, 'healm', '2024-04-02 08:51:50', 'Apple', 10000),
(32, 1, 'healm', '2024-04-02 08:52:42', 'Apple', 10000),
(33, 1, 'healm', '2024-04-02 09:19:43', 'Apple', 10000),
(34, 1, 'healm', '2024-04-02 09:19:43', 'Apple', 10000),
(35, 1, 'healm', '2024-04-02 09:19:43', 'Apple', 10000),
(36, 1, 'healm', '2024-04-02 09:19:43', 'Apple', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` int(30) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `role`) VALUES
(1, 'healm', 99151749, 'user'),
(3, 'admin', 92668751, 'admin'),
(4, 'b', 98, 'user'),
(5, 'c', 99, 'user'),
(6, 'd', 100, 'user'),
(7, '2', 50, 'user'),
(8, 'healm2', -1221263027, 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity_log`
--
ALTER TABLE `activity_log`
  ADD UNIQUE KEY `id_act` (`id_act`);

--
-- Indexes for table `transaction_log`
--
ALTER TABLE `transaction_log`
  ADD UNIQUE KEY `transaction_id` (`transaction_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity_log`
--
ALTER TABLE `activity_log`
  MODIFY `id_act` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=144;

--
-- AUTO_INCREMENT for table `transaction_log`
--
ALTER TABLE `transaction_log`
  MODIFY `transaction_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
