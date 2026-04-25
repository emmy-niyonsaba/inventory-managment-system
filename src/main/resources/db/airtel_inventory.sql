-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 25, 2026 at 09:22 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `airtel_inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE `assignments` (
  `id` bigint(20) NOT NULL,
  `actual_return_date` datetime(6) DEFAULT NULL,
  `assigned_date` datetime(6) DEFAULT NULL,
  `assigned_to_department` varchar(100) DEFAULT NULL,
  `condition_at_assignment` text DEFAULT NULL,
  `condition_at_return` text DEFAULT NULL,
  `expected_return_date` datetime(6) DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `purpose` text DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `assigned_by_user_id` bigint(20) DEFAULT NULL,
  `assigned_to_user_id` bigint(20) NOT NULL,
  `equipment_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `id` bigint(20) NOT NULL,
  `asset_tag` varchar(50) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `current_department` varchar(100) DEFAULT NULL,
  `current_owner` varchar(100) DEFAULT NULL,
  `device_type` varchar(50) NOT NULL,
  `equipment_condition` text DEFAULT NULL,
  `last_updated` datetime(6) DEFAULT NULL,
  `model` varchar(100) NOT NULL,
  `notes` text DEFAULT NULL,
  `purchase_date` datetime(6) DEFAULT NULL,
  `serial_number` varchar(100) DEFAULT NULL,
  `specifications` text DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `equipment`
--

INSERT INTO `equipment` (`id`, `asset_tag`, `brand`, `current_department`, `current_owner`, `device_type`, `equipment_condition`, `last_updated`, `model`, `notes`, `purchase_date`, `serial_number`, `specifications`, `status`) VALUES
(1, 'LAPTOP-1', 'HP', NULL, NULL, 'LAPTOP', 'Excellent', '2026-04-24 21:26:16.000000', 'EliteBook', 'Thank you for working with us🥰🥰🙏', '2026-04-09 22:00:00.000000', '1010', '16GB', 'AVAILABLE'),
(2, 'DESKTOP_001', 'Dell', NULL, NULL, 'DESKTOP', 'Good', '2026-04-24 21:31:31.000000', 'XPS 15', 'Under Maintenance', '2026-04-15 22:00:00.000000', '1020', 'RAM: 16GB, Storage:512GB', 'UNDER_MAINTENANCE'),
(3, 'Mobile Phone', 'Pixel 3a', NULL, NULL, 'MOBILE', 'Good', '2026-04-24 21:33:29.000000', 'version 1', 'Good phone', '2026-01-14 22:00:00.000000', '1000', '8GB', 'ASSIGNED');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `last_login` datetime(6) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` varchar(20) DEFAULT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_at`, `department`, `email`, `full_name`, `is_active`, `last_login`, `password_hash`, `role`, `username`) VALUES
(1, '2026-04-24 20:28:02.000000', 'IT', 'admin@airtel.com', 'System Administrator', b'1', '2026-04-25 19:17:07.000000', '$2a$12$Y82qf1pZ6l0KD6UNMbuY5u4rZhkZgWEVbDf4IIACknMEkK28Pntwy', 'ADMIN', 'admin'),
(4, '2026-04-24 21:12:10.000000', 'IT', 'kevineumk250@gmail.com', 'Kevine Umukundwa', b'1', '2026-04-24 21:38:18.000000', '$2a$12$tgbQ6lU5TwXAtWBvWtViYuQqmjo5DY5qIVI9A8QMFZQvgaVEOxeeO', 'ADMIN', 'Kevine'),
(5, '2026-04-24 21:12:57.000000', 'IT', 'emmy@gmail.com', 'Emmanuel Niyonsaba', b'1', NULL, '$2a$12$wkkCZC8PmDLtsr34R0T9guDhMQXYRLEHQQt4bYKSimo7LFimIo4Li', 'ADMIN', 'Emmyson'),
(6, '2026-04-24 21:13:48.000000', 'HR', 'gatete@gmail.com', 'Gatete Patrick', b'1', NULL, '$2a$12$gkOj7TCXnk2TOfyb28suw.rvXJLvsSVFUVZfn9LSq0zJw2syOj17a', 'MANAGER', 'Gatete'),
(7, '2026-04-24 21:14:46.000000', 'sales', 'baby@gmail.com', 'toby baby', b'1', NULL, '$2a$12$VuqCSbtehinT1EBWd5/4auqwAEOKkh9Lk/9/eYyrdp28RQV0P8w1G', 'USER', 'baby'),
(8, '2026-04-24 21:39:24.000000', 'IT', 'kevineumk250@gmail.com', 'Admin', b'1', '2026-04-24 22:32:37.000000', '$2a$12$lJFcJIgcqcuyNOBrtThLyetwJBJG4nYl4/uodEnifq.dZUv6s34jm', 'ADMIN', '24RP04021');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignments`
--
ALTER TABLE `assignments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmgljq0lg90g9fgyp2lub5ib0k` (`assigned_by_user_id`),
  ADD KEY `FK6cg6ajs2cq2d2q2fkr40ka2vd` (`assigned_to_user_id`),
  ADD KEY `FKjj670xhqb2h4ugug9fdu1sljd` (`equipment_id`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_fewam03umjuhiy7des2u3w4uq` (`asset_tag`),
  ADD UNIQUE KEY `UK_agm98wn5ln6uoh6o9hx25ogic` (`serial_number`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignments`
--
ALTER TABLE `assignments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `equipment`
--
ALTER TABLE `equipment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assignments`
--
ALTER TABLE `assignments`
  ADD CONSTRAINT `FK6cg6ajs2cq2d2q2fkr40ka2vd` FOREIGN KEY (`assigned_to_user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKjj670xhqb2h4ugug9fdu1sljd` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`),
  ADD CONSTRAINT `FKmgljq0lg90g9fgyp2lub5ib0k` FOREIGN KEY (`assigned_by_user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
