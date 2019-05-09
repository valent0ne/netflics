-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 08, 2019 at 09:32 PM
-- Server version: 5.7.25
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `netflics`
--

-- --------------------------------------------------------

--
-- Table structure for table `availability`
--

CREATE TABLE `availability` (
  `id` int(10) UNSIGNED NOT NULL,
  `supplier_id` int(10) UNSIGNED NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cpu_saturation` double NOT NULL,
  `mem_saturation` double NOT NULL,
  `available` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`id`, `supplier_id`, `timestamp`, `cpu_saturation`, `mem_saturation`, `available`) VALUES
(218, 3, '2019-05-08 19:21:41', 0, 0, 0),
(219, 4, '2019-05-08 19:21:41', 0, 0, 0),
(220, 2, '2019-05-08 19:21:41', 0, 0, 0),
(221, 5, '2019-05-08 19:21:41', 0, 0, 0),
(222, 1, '2019-05-08 19:21:42', 0.45, 8589934591.99, 1),
(223, 5, '2019-05-08 19:23:54', 0, 0, 0),
(224, 4, '2019-05-08 19:23:54', 0, 0, 0),
(225, 3, '2019-05-08 19:23:54', 0, 0, 0),
(226, 2, '2019-05-08 19:23:54', 0, 0, 0),
(227, 1, '2019-05-08 19:23:55', 0.29, 0.98, 1),
(228, 5, '2019-05-08 19:25:24', 0, 0, 0),
(229, 3, '2019-05-08 19:25:24', 0, 0, 0),
(230, 4, '2019-05-08 19:25:24', 0, 0, 0),
(231, 1, '2019-05-08 19:25:25', 0.41, 0.98, 1),
(232, 2, '2019-05-08 19:25:26', 0.69, 0.98, 1),
(233, 4, '2019-05-08 19:25:47', 0, 0, 0),
(234, 5, '2019-05-08 19:25:47', 0, 0, 0),
(235, 3, '2019-05-08 19:25:47', 0, 0, 0),
(236, 2, '2019-05-08 19:25:47', 0.3, 0.96, 1),
(237, 1, '2019-05-08 19:25:47', 0.32, 0.96, 1),
(238, 5, '2019-05-08 19:25:56', 0, 0, 0),
(239, 3, '2019-05-08 19:25:56', 0, 0, 0),
(240, 4, '2019-05-08 19:25:56', 0, 0, 0),
(241, 2, '2019-05-08 19:25:56', 0.43, 0.96, 1),
(242, 1, '2019-05-08 19:25:56', 0.44, 0.96, 1),
(243, 4, '2019-05-08 19:26:04', 0, 0, 0),
(244, 3, '2019-05-08 19:26:04', 0, 0, 0),
(245, 5, '2019-05-08 19:26:04', 0, 0, 0),
(246, 2, '2019-05-08 19:26:04', 0.41, 0.97, 1),
(247, 1, '2019-05-08 19:26:04', 0.41, 0.97, 1),
(248, 4, '2019-05-08 19:26:13', 0, 0, 0),
(249, 3, '2019-05-08 19:26:13', 0, 0, 0),
(250, 5, '2019-05-08 19:26:13', 0, 0, 0),
(251, 2, '2019-05-08 19:26:13', 0.45, 0.95, 1),
(252, 1, '2019-05-08 19:26:13', 0.45, 0.95, 1),
(253, 4, '2019-05-08 19:26:20', 0, 0, 0),
(254, 3, '2019-05-08 19:26:20', 0, 0, 0),
(255, 5, '2019-05-08 19:26:20', 0, 0, 0),
(256, 2, '2019-05-08 19:26:20', 0.43, 0.96, 1),
(257, 1, '2019-05-08 19:26:20', 0.43, 0.96, 1),
(258, 1, '2019-05-08 19:27:46', 0.31, 1, 1),
(259, 2, '2019-05-08 19:27:46', 0.31, 1, 1),
(260, 2, '2019-05-08 19:27:59', 0.32, 0.98, 1),
(261, 1, '2019-05-08 19:27:59', 0.32, 0.98, 1),
(262, 1, '2019-05-08 19:27:59', 0.99, 0.98, 1),
(263, 2, '2019-05-08 19:27:59', 0.99, 0.98, 1),
(264, 1, '2019-05-08 19:27:59', 1, 0.98, 1),
(265, 2, '2019-05-08 19:27:59', 1, 0.98, 1);

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `id` int(10) UNSIGNED NOT NULL,
  `title` varchar(512) NOT NULL,
  `directors` text NOT NULL,
  `genres` text NOT NULL,
  `rating` float(3,1) NOT NULL,
  `imdb_id` varchar(512) NOT NULL,
  `poster` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL COMMENT 'UPLOADING or UPLOADED',
  `views` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`id`, `title`, `directors`, `genres`, `rating`, `imdb_id`, `poster`, `status`, `views`) VALUES
(19, 'Doctor Strange', 'Scott Derrickson', 'Action, Adventure, Fantasy, Sci-Fi', 7.5, 'tt1211837', '/4PiiNGXj1KENTmCBHeN6Mskj2Fq.jpg', 'UPLOADED', 3),
(20, 'Inception', 'Christopher Nolan', 'Action, Adventure, Sci-Fi, Thriller', 8.8, 'tt1375666', '/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg', 'UPLOADED', 5),
(21, 'Suicide Squad', 'David Ayer', 'Action, Adventure, Fantasy, Sci-Fi', 6.1, 'tt1386697', '/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg', 'UPLOADED', 1),
(22, 'Mad Max: Fury Road', 'George Miller', 'Action, Adventure, Sci-Fi, Thriller', 8.1, 'tt1392190', '/kqjL17yufvn9OVLyXYpvtyrFfak.jpg', 'UPLOADED', 100),
(23, 'Django Unchained', 'Quentin Tarantino', 'Drama, Western', 8.4, 'tt1853728', '/5WJnxuw41sddupf8cwOxYftuvJG.jpg', 'UPLOADED', 167),
(26, 'Star Wars: The Last Jedi', 'Rian Johnson', 'Action, Adventure, Fantasy, Sci-Fi', 7.2, 'tt2527336', '/kOVEVeg59E0wsnXmF9nrh6OmWII.jpg', 'UPLOADED', 0),
(27, 'Incredibles 2', 'Brad Bird', 'Animation, Action, Adventure, Comedy, Family, Sci-Fi', 7.7, 'tt3606756', '/9lFKBtaVIhP7E2Pk0IY1CwTKTMZ.jpg', 'UPLOADED', 0),
(28, 'Bridge of Spies', 'Steven Spielberg', 'Drama, History, Thriller', 7.6, 'tt3682448', '/xPCNA8zJxyyFKTj47QpvkXHukzB.jpg', 'UPLOADED', 0),
(30, 'Darkest Hour', 'Joe Wright', 'Biography, Drama, History, War', 7.4, 'tt4555426', '/xa6G3aKlysQeVg9wOb0dRcIGlWu.jpg', 'UPLOADED', 0),
(31, 'Bumblebee', 'Travis Knight', 'Action, Adventure, Sci-Fi', 7.0, 'tt4701182', '/fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg', 'UPLOADED', 0),
(32, 'Dunkirk', 'Christopher Nolan', 'Action, Drama, History, Thriller, War', 7.9, 'tt5013056', '/bOXBV303Fgkzn2K4FeKDc0O31q4.jpg', 'UPLOADED', 0),
(33, 'BlacKkKlansman', 'Spike Lee', 'Biography, Crime, Drama', 7.5, 'tt7349662', '/3ntR66u2SHZ2UA3r3DjF2Dl6Kwx.jpg', 'UPLOADED', 0),
(34, 'Captain America: The First Avenger', 'Joe Johnston', 'Action, Adventure, Sci-Fi', 6.9, 'tt0458339', '/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg', 'UPLOADED', 0),
(35, 'Captain America: The Winter Soldier', 'Anthony Russo, Joe Russo', 'Action, Adventure, Sci-Fi, Thriller', 7.8, 'tt1843866', '/5TQ6YDmymBpnF005OyoB7ohZps9.jpg', 'UPLOADED', 0),
(36, 'Avengers: Infinity War', 'Anthony Russo, Joe Russo', 'Action, Adventure, Sci-Fi', 8.5, 'tt4154756', '/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg', 'UPLOADED', 0),
(37, 'Avengers: Age of Ultron', 'Joss Whedon', 'Action, Adventure, Sci-Fi', 7.4, 'tt2395427', '/t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg', 'UPLOADED', 0);

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `token` varchar(512) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`id`, `user_id`, `token`) VALUES
(5, 2, 't'),
(6, 2, 'f36f6728-70cf-11e9-818b-2685283bb0bf'),
(7, 2, '574068d8-70d0-11e9-818b-2685283bb0bf'),
(8, 2, 'b1f63046-70d0-11e9-818b-2685283bb0bf'),
(12, 2, 'dab84a86-70db-11e9-818b-2685283bb0bf');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` int(10) UNSIGNED NOT NULL,
  `ip` varchar(512) NOT NULL,
  `port` varchar(512) NOT NULL,
  `token` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `ip`, `port`, `token`) VALUES
(1, 'localhost', '8081', 'tokensupplier1'),
(2, 'localhost', '8082', 'tokensupplier2'),
(3, 'localhost', '8083', 'tokensupplier3'),
(4, 'localhost', '8084', 'tokensupplier4'),
(5, 'localhost', '8085', 'tokensupplier5');

-- --------------------------------------------------------

--
-- Table structure for table `supplier_movie`
--

CREATE TABLE `supplier_movie` (
  `id` int(10) UNSIGNED NOT NULL,
  `supplier_id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL,
  `status` varchar(255) NOT NULL DEFAULT 'FETCHING' COMMENT 'FETCHING or FETCHED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `supplier_movie`
--

INSERT INTO `supplier_movie` (`id`, `supplier_id`, `movie_id`, `status`) VALUES
(1, 1, 34, 'FETCHED'),
(4, 2, 34, 'FETCHED'),
(5, 3, 34, 'FETCHED'),
(6, 4, 34, 'FETCHED'),
(7, 5, 34, 'FETCHED'),
(8, 1, 21, 'FETCHED'),
(10, 1, 32, 'FETCHED'),
(11, 1, 26, 'FETCHED'),
(12, 2, 26, 'FETCHED');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(10) UNSIGNED NOT NULL,
  `email` varchar(512) NOT NULL,
  `password` varchar(512) NOT NULL,
  `role` varchar(255) NOT NULL DEFAULT 'USER' COMMENT 'USER or ADMIN'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `role`) VALUES
(1, 's@s.com', 'pippo', 'USER'),
(2, 'v@v.com', 'pippo', 'ADMIN'),
(3, 'supplier001@netflics', 'netflics', 'ADMIN'),
(4, 'supplier002@netflics', 'netflics', 'ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `user_movie`
--

CREATE TABLE `user_movie` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `movie_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `availability`
--
ALTER TABLE `availability`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dispatcher_id` (`supplier_id`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `imdb_id` (`imdb_id`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `supplier_movie`
--
ALTER TABLE `supplier_movie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dispatcher_id` (`supplier_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_movie`
--
ALTER TABLE `user_movie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `availability`
--
ALTER TABLE `availability`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=266;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `supplier_movie`
--
ALTER TABLE `supplier_movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user_movie`
--
ALTER TABLE `user_movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `availability_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `supplier_movie`
--
ALTER TABLE `supplier_movie`
  ADD CONSTRAINT `supplier_movie_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `supplier_movie_ibfk_2` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_movie`
--
ALTER TABLE `user_movie`
  ADD CONSTRAINT `user_movie_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `user_movie_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
