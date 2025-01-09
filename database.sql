-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 08, 2025 at 11:37 PM
-- Server version: 8.0.30
-- PHP Version: 8.3.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pp2-tubes`
--

-- --------------------------------------------------------

--
-- Table structure for table `jenis_sampah`
--

CREATE TABLE `jenis_sampah` (
  `id` int NOT NULL,
  `category_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `jenis_sampah`
--

INSERT INTO `jenis_sampah` (`id`, `category_id`, `name`, `description`) VALUES
(1, 1, 'Televisi', 'Alat untuk menampilkan gambar dan suara'),
(2, 1, 'AC (Air Conditioner)', 'Alat pendingin ruangan'),
(3, 1, 'Mesin Cuci', 'Alat untuk mencuci pakaian'),
(4, 1, 'Kipas Angin', 'Alat untuk sirkulasi udara'),
(5, 1, 'Lampu LED Pintar', 'Lampu yang dapat diatur melalui aplikasi'),
(6, 2, 'Printer', 'Peralatan mencetak dokumen'),
(7, 2, 'Scanner', 'Alat untuk memindai dokumen'),
(8, 2, 'Mesin Fotokopi', 'Alat untuk menggandakan dokumen'),
(9, 2, 'Proyektor', 'Alat untuk menampilkan presentasi atau gambar besar'),
(10, 2, 'Telepon Kantor', 'Alat komunikasi untuk keperluan kantor'),
(11, 3, 'Blender', 'Alat untuk menghaluskan bahan makanan'),
(12, 3, 'Microwave', 'Alat untuk memanaskan makanan'),
(13, 3, 'Kulkas', 'Lemari pendingin untuk menyimpan bahan makanan'),
(14, 3, 'Kompor Listrik', 'Alat memasak dengan energi listrik'),
(15, 3, 'Dispenser Air', 'Alat untuk mengalirkan air minum'),
(16, 4, 'Smartphone', 'Telepon pintar yang multifungsi'),
(17, 4, 'Tablet', 'Perangkat portabel untuk kebutuhan multimedia'),
(18, 4, 'Power Bank', 'Alat penyimpan daya portabel'),
(19, 4, 'Earphone Bluetooth', 'Perangkat audio tanpa kabel'),
(20, 4, 'Smartwatch', 'Jam tangan pintar yang terhubung dengan smartphone'),
(21, 5, 'Laptop', 'Komputer portabel'),
(22, 5, 'Keyboard Mekanis', 'Papan ketik dengan tombol mekanis'),
(23, 5, 'Mouse Nirkabel', 'Alat pengendali kursor tanpa kabel'),
(24, 5, 'Monitor LED', 'Layar komputer dengan teknologi LED'),
(25, 5, 'Hard Disk Eksternal', 'Alat penyimpan data portabel'),
(26, 6, 'Televisi LED', 'Layar hiburan dengan teknologi LED'),
(27, 6, 'Home Theater', 'Sistem audio untuk hiburan di rumah'),
(28, 6, 'Speaker Bluetooth', 'Perangkat audio tanpa kabel'),
(29, 6, 'Konsol Game', 'Perangkat untuk bermain video game'),
(30, 6, 'Pemutar Blu-ray', 'Alat untuk memutar cakram Blu-ray'),
(31, 7, 'Mesin Las Elektrik', 'Alat las menggunakan energi listrik'),
(32, 7, 'Generator Listrik', 'Alat penghasil listrik'),
(33, 7, 'Mesin Bubut', 'Alat untuk membentuk benda kerja'),
(34, 7, 'Mesin Bor Industri', 'Alat bor untuk keperluan industri'),
(35, 7, 'PLC (Programmable Logic Controller)', 'Alat pengendali logika otomatisasi industri');

-- --------------------------------------------------------

--
-- Table structure for table `kategori_sampah`
--

CREATE TABLE `kategori_sampah` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kategori_sampah`
--

INSERT INTO `kategori_sampah` (`id`, `name`, `description`) VALUES
(1, 'Elektronik Rumah', 'Peralatan elektronik untuk keperluan rumah'),
(2, 'Elektronik Kantor', 'Peralatan elektronik untuk keperluan kantor'),
(3, 'Peralatan Dapur', 'Elektronik yang digunakan di dapur'),
(4, 'Elektronik Portabel', 'Elektronik yang mudah dibawa'),
(5, 'Komputer dan Aksesori', 'Komponen komputer dan aksesorinya'),
(6, 'Elektronik Hiburan', 'Elektronik untuk keperluan hiburan'),
(7, 'Elektronik Industri', 'Elektronik untuk kebutuhan industri');

-- --------------------------------------------------------

--
-- Table structure for table `otp`
--

CREATE TABLE `otp` (
  `id` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `otp_code` varchar(6) NOT NULL,
  `expires_at` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `otp`
--

INSERT INTO `otp` (`id`, `email`, `otp_code`, `expires_at`, `status`, `created_at`) VALUES
(4, 'tester@mailinator.com', '576536', '2024-12-30 23:10:46', 'USED', '2024-12-30 22:10:46'),
(5, 'tester@mailinator.com', '559979', '2024-12-30 23:21:57', 'USED', '2024-12-30 22:21:57'),
(6, 'tester@mailinator.com', '534971', '2024-12-31 00:01:43', 'USED', '2024-12-30 23:01:42'),
(7, 'arya@mailinator.com', '736433', '2025-01-01 01:40:41', 'USED', '2025-01-01 00:40:40'),
(8, 'kaka@mailinator.com', '963402', '2025-01-01 21:23:26', 'USED', '2025-01-01 20:23:25'),
(9, 'jita@mailinator.com', '364286', '2025-01-04 12:31:39', 'USED', '2025-01-04 11:31:39'),
(10, 'kaka@mailinator.com', '173649', '2025-01-08 18:18:08', 'USED', '2025-01-08 17:18:08');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'Admin'),
(2, 'Masyarakat');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `address` text,
  `birth_date` date DEFAULT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_verified` enum('YES','NO') DEFAULT 'NO',
  `role_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `address`, `birth_date`, `photo_path`, `created_at`, `is_verified`, `role_id`) VALUES
(1, 'Admin', 'admin@mail.com', 'admin123', 'Bandung', '1970-01-01', NULL, '2024-12-30 11:41:06', '', NULL),
(2, 'Daffa', 'daffa@mail.com', 'daffa123', 'Bandung', '2003-10-19', NULL, '2024-12-30 11:41:06', '', 1),
(22, 'test', 'tester@mailinator.com', '$2a$10$.kyw8V/Wmq//HNjD3xrBsOzSJE5QeLszPz.TpktWHp.rHaw9SPQNy', 'jalan', '1990-01-01', NULL, '2024-12-30 16:01:42', 'YES', 1),
(25, 'jita', 'jita@mailinator.com', 'jita', 'jalan', '2003-10-10', 'C:\\Users\\jitab\\Pictures\\ffxiv_dx11 2023-04-07 22-23-19.png', '2025-01-04 04:31:39', 'YES', 2),
(26, 'kaka', 'kaka@mailinator.com', 'kaka', 'kaka', '1909-01-01', NULL, '2025-01-08 10:18:08', 'YES', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jenis_sampah`
--
ALTER TABLE `jenis_sampah`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `kategori_sampah`
--
ALTER TABLE `kategori_sampah`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `otp`
--
ALTER TABLE `otp`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `fk_role` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jenis_sampah`
--
ALTER TABLE `jenis_sampah`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `kategori_sampah`
--
ALTER TABLE `kategori_sampah`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `otp`
--
ALTER TABLE `otp`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `jenis_sampah`
--
ALTER TABLE `jenis_sampah`
  ADD CONSTRAINT `jenis_sampah_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `kategori_sampah` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
