-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 09, 2019 at 01:41 PM
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
(265, 2, '2019-05-08 19:27:59', 1, 0.98, 1),
(266, 2, '2019-05-09 07:11:19', 1, 1, 1),
(267, 1, '2019-05-09 07:11:19', 1, 1, 1),
(268, 2, '2019-05-09 07:12:24', 0.27, 1, 1),
(269, 1, '2019-05-09 07:12:24', 0.27, 1, 1),
(270, 3, '2019-05-09 07:12:24', 0, 0, 0),
(271, 2, '2019-05-09 07:31:39', 0.6, 1, 1),
(272, 1, '2019-05-09 07:31:39', 0.77, 1, 1),
(273, 3, '2019-05-09 07:31:39', 0.77, 1, 1),
(274, 2, '2019-05-09 07:39:53', 0.38, 0.99, 1),
(275, 3, '2019-05-09 07:39:53', 0.38, 0.99, 1),
(276, 1, '2019-05-09 07:39:53', 0.38, 0.99, 1),
(277, 2, '2019-05-09 07:40:05', 0.37, 0.95, 1),
(278, 1, '2019-05-09 07:40:05', 0.37, 0.95, 1),
(279, 3, '2019-05-09 07:40:05', 0.37, 0.95, 1),
(280, 1, '2019-05-09 07:55:20', 0.23, 1, 1),
(281, 3, '2019-05-09 07:55:20', 0.23, 1, 1),
(282, 2, '2019-05-09 07:55:20', 0.23, 1, 1),
(283, 1, '2019-05-09 08:47:12', 0.24, 0.99, 1),
(284, 3, '2019-05-09 08:47:12', 0.24, 0.99, 1),
(285, 2, '2019-05-09 08:47:12', 0.24, 0.99, 1),
(286, 1, '2019-05-09 08:49:08', 0.39, 0.98, 1),
(287, 3, '2019-05-09 08:50:16', 0.43, 1, 1),
(288, 1, '2019-05-09 08:50:16', 0.5, 0.99, 1),
(289, 2, '2019-05-09 08:50:16', 0.43, 0.99, 1),
(290, 1, '2019-05-09 08:50:19', 0.58, 0.98, 1),
(291, 2, '2019-05-09 08:50:19', 0.57, 0.98, 1),
(292, 3, '2019-05-09 08:50:19', 0.59, 0.98, 1),
(293, 2, '2019-05-09 08:51:59', 0.42, 1, 1),
(294, 1, '2019-05-09 08:51:59', 0.42, 1, 1),
(295, 3, '2019-05-09 08:51:59', 0.42, 1, 1),
(296, 1, '2019-05-09 09:09:13', 1, 1, 1),
(297, 2, '2019-05-09 09:09:13', 1, 1, 1),
(298, 3, '2019-05-09 09:09:13', 1, 1, 1),
(299, 1, '2019-05-09 09:09:47', 0.4, 1, 1),
(300, 2, '2019-05-09 09:09:47', 0.4, 1, 1),
(301, 3, '2019-05-09 09:09:47', 0.4, 1, 1),
(302, 3, '2019-05-09 09:11:35', 0.38, 0.99, 1),
(303, 2, '2019-05-09 09:11:35', 0.38, 0.99, 1),
(304, 1, '2019-05-09 09:11:35', 0.38, 0.99, 1),
(305, 3, '2019-05-09 09:12:10', 0.4, 0.99, 1),
(306, 2, '2019-05-09 09:12:10', 0.4, 0.99, 1),
(307, 1, '2019-05-09 09:12:10', 0.4, 0.99, 1),
(308, 3, '2019-05-09 09:12:51', 0.39, 0.99, 1),
(309, 1, '2019-05-09 09:12:51', 0.39, 0.99, 1),
(310, 2, '2019-05-09 09:12:51', 0.39, 0.99, 1),
(311, 1, '2019-05-09 09:13:14', 0.41, 1, 1),
(312, 2, '2019-05-09 09:13:14', 0.41, 1, 1),
(313, 3, '2019-05-09 09:13:14', 0.41, 1, 1),
(314, 3, '2019-05-09 09:13:32', 0.38, 0.98, 1),
(315, 1, '2019-05-09 09:13:32', 0.38, 0.98, 1),
(316, 2, '2019-05-09 09:13:32', 0.38, 0.98, 1),
(317, 1, '2019-05-09 09:14:00', 0.35, 0.99, 1),
(318, 2, '2019-05-09 09:14:00', 0.35, 0.99, 1),
(319, 3, '2019-05-09 09:14:00', 0.35, 0.99, 1),
(320, 3, '2019-05-09 09:14:24', 0.4, 0.99, 1),
(321, 1, '2019-05-09 09:14:24', 0.4, 0.99, 1),
(322, 2, '2019-05-09 09:14:24', 0.4, 0.99, 1),
(323, 3, '2019-05-09 09:14:43', 0.44, 1, 1),
(324, 1, '2019-05-09 09:14:43', 0.44, 1, 1),
(325, 2, '2019-05-09 09:14:43', 0.44, 1, 1),
(326, 3, '2019-05-09 09:15:02', 0.54, 0.98, 1),
(327, 2, '2019-05-09 09:15:02', 0.54, 0.98, 1),
(328, 1, '2019-05-09 09:15:02', 0.54, 0.98, 1),
(329, 1, '2019-05-09 09:18:42', 0.37, 1, 1),
(330, 3, '2019-05-09 09:18:42', 0.37, 1, 1),
(331, 1, '2019-05-09 09:18:42', 1, 1, 1),
(332, 3, '2019-05-09 09:18:42', 1, 1, 1),
(333, 2, '2019-05-09 09:18:42', 0.37, 1, 1),
(334, 2, '2019-05-09 09:18:42', 0, 0, 0),
(335, 2, '2019-05-09 09:21:08', 0.39, 1, 1),
(336, 1, '2019-05-09 09:21:08', 0.39, 1, 1),
(337, 3, '2019-05-09 09:21:08', 0.39, 1, 1),
(338, 1, '2019-05-09 09:21:30', 0.47, 1, 1),
(339, 1, '2019-05-09 09:22:07', 0.4, 1, 1),
(340, 3, '2019-05-09 09:22:07', 0.43, 1, 1),
(341, 2, '2019-05-09 09:40:26', 0.37, 0.96, 1),
(342, 1, '2019-05-09 09:40:26', 0.37, 0.96, 1),
(343, 2, '2019-05-09 09:40:26', 1, 0.96, 1),
(344, 3, '2019-05-09 09:40:26', 0.37, 0.97, 1),
(345, 3, '2019-05-09 09:40:26', 1, 0.97, 1),
(346, 1, '2019-05-09 09:40:26', 1, 0.97, 1),
(347, 2, '2019-05-09 09:40:57', 0.41, 0.99, 1),
(348, 3, '2019-05-09 09:40:57', 0.41, 0.99, 1),
(349, 1, '2019-05-09 09:40:57', 0.41, 0.99, 1),
(350, 1, '2019-05-09 09:40:57', 1, 0.99, 1),
(351, 1, '2019-05-09 09:40:57', 1, 0.99, 1),
(352, 3, '2019-05-09 09:40:57', 1, 0.99, 1),
(353, 2, '2019-05-09 09:40:57', 1, 0.99, 1),
(354, 2, '2019-05-09 09:40:57', 0, 0, 0),
(355, 1, '2019-05-09 09:40:57', 1, 0.99, 1),
(356, 2, '2019-05-09 09:40:57', 0, 0, 0),
(357, 3, '2019-05-09 09:40:57', 0, 0, 0),
(358, 3, '2019-05-09 09:40:57', 0, 0, 0),
(359, 2, '2019-05-09 09:43:13', 0.46, 0.98, 1),
(360, 1, '2019-05-09 09:43:13', 0.46, 0.98, 1),
(361, 2, '2019-05-09 09:43:13', 1, 0.98, 1),
(362, 3, '2019-05-09 09:43:13', 0.46, 0.98, 1),
(363, 3, '2019-05-09 09:43:13', 0, 0, 0),
(364, 1, '2019-05-09 09:43:13', 0, 0, 0),
(365, 1, '2019-05-09 09:43:19', 0.61, 0.96, 1),
(366, 2, '2019-05-09 09:43:19', 0.61, 0.96, 1),
(367, 3, '2019-05-09 09:43:19', 0.61, 0.97, 1),
(368, 1, '2019-05-09 09:44:20', 0.56, 1, 1),
(369, 1, '2019-05-09 09:44:20', 1, 1, 1),
(370, 2, '2019-05-09 09:44:20', 0.56, 1, 1),
(371, 3, '2019-05-09 09:44:20', 0.56, 1, 1),
(372, 2, '2019-05-09 09:44:20', 0, 0, 0),
(373, 3, '2019-05-09 09:44:20', 0, 0, 0),
(374, 1, '2019-05-09 09:44:26', 0.54, 0.99, 1),
(375, 3, '2019-05-09 09:44:26', 0.54, 0.99, 1),
(376, 2, '2019-05-09 09:44:26', 0.54, 0.99, 1),
(377, 1, '2019-05-09 09:53:14', 0.45, 0.97, 1),
(378, 1, '2019-05-09 09:53:14', 1, 0.97, 1),
(379, 3, '2019-05-09 09:53:14', 1, 0.97, 1),
(380, 3, '2019-05-09 09:53:14', 0.45, 0.97, 1),
(381, 2, '2019-05-09 09:53:14', 0.45, 0.97, 1),
(382, 2, '2019-05-09 09:53:14', 0.45, 0.97, 1),
(383, 2, '2019-05-09 09:54:46', 0.43, 1, 1),
(384, 1, '2019-05-09 09:54:46', 0.43, 1, 1),
(385, 3, '2019-05-09 09:54:46', 0.43, 1, 1),
(386, 2, '2019-05-09 09:55:37', 0.39, 1, 1),
(387, 1, '2019-05-09 09:55:37', 0.39, 1, 1),
(388, 3, '2019-05-09 09:55:37', 0.4, 1, 1),
(389, 2, '2019-05-09 09:55:41', 0.44, 0.99, 1),
(390, 1, '2019-05-09 09:55:41', 0.44, 0.99, 1),
(391, 3, '2019-05-09 09:55:41', 0.4, 0.99, 1),
(392, 2, '2019-05-09 09:56:19', 0.45, 0.99, 1),
(393, 1, '2019-05-09 09:56:19', 0.45, 1, 1),
(394, 3, '2019-05-09 09:56:19', 0.45, 1, 1),
(395, 3, '2019-05-09 09:56:42', 0.42, 0.97, 1),
(396, 2, '2019-05-09 09:56:42', 0.42, 0.97, 1),
(397, 1, '2019-05-09 09:56:42', 0.42, 0.97, 1),
(398, 1, '2019-05-09 09:57:41', 0.4, 1, 1),
(399, 3, '2019-05-09 09:57:41', 0.4, 1, 1),
(400, 1, '2019-05-09 09:57:41', 1, 1, 1),
(401, 3, '2019-05-09 09:57:41', 0, 0, 0),
(402, 2, '2019-05-09 09:57:41', 0.4, 0.99, 1),
(403, 2, '2019-05-09 09:57:41', 0, 0, 0),
(404, 2, '2019-05-09 09:57:54', 0.43, 0.99, 1),
(405, 1, '2019-05-09 09:57:54', 0.43, 0.99, 1),
(406, 3, '2019-05-09 09:57:54', 0.43, 1, 1),
(407, 3, '2019-05-09 10:28:09', 0.4, 1, 1),
(408, 2, '2019-05-09 10:28:09', 0.4, 1, 1),
(409, 1, '2019-05-09 10:28:09', 0.4, 1, 1),
(410, 1, '2019-05-09 10:28:33', 0.38, 0.99, 1),
(411, 2, '2019-05-09 10:28:33', 0.38, 0.99, 1),
(412, 3, '2019-05-09 10:28:33', 0.38, 0.99, 1),
(413, 3, '2019-05-09 10:28:53', 0.42, 1, 1),
(414, 1, '2019-05-09 10:28:53', 0.42, 1, 1),
(415, 2, '2019-05-09 10:28:53', 0.42, 1, 1),
(416, 1, '2019-05-09 10:29:09', 0.38, 0.99, 1),
(417, 1, '2019-05-09 10:29:09', 1, 0.99, 1),
(418, 2, '2019-05-09 10:29:09', 0.38, 0.99, 1),
(419, 3, '2019-05-09 10:29:09', 0.38, 0.99, 1),
(420, 3, '2019-05-09 10:29:09', 1, 0.99, 1),
(421, 2, '2019-05-09 10:29:09', 1, 0.99, 1),
(422, 2, '2019-05-09 10:31:13', 0.43, 0.95, 1),
(423, 3, '2019-05-09 10:31:13', 0.43, 0.95, 1),
(424, 1, '2019-05-09 10:31:13', 0.43, 0.95, 1),
(425, 3, '2019-05-09 10:31:13', 1, 0.95, 1),
(426, 2, '2019-05-09 10:31:13', 1, 0.95, 1),
(427, 1, '2019-05-09 10:31:13', 1, 0.95, 1),
(428, 2, '2019-05-09 10:31:19', 0.42, 0.96, 1),
(429, 3, '2019-05-09 10:31:19', 0.42, 0.96, 1),
(430, 1, '2019-05-09 10:31:19', 0.42, 0.96, 1),
(431, 1, '2019-05-09 10:34:06', 0.35, 1, 1),
(432, 3, '2019-05-09 10:34:06', 0.35, 1, 1),
(433, 2, '2019-05-09 10:34:06', 0.35, 1, 1),
(434, 3, '2019-05-09 10:34:30', 0.47, 0.99, 1),
(435, 3, '2019-05-09 10:34:30', 1, 0.99, 1),
(436, 1, '2019-05-09 10:34:30', 0.47, 0.99, 1),
(437, 1, '2019-05-09 10:34:30', 1, 0.99, 1),
(438, 2, '2019-05-09 10:34:30', 0.47, 0.99, 1),
(439, 2, '2019-05-09 10:34:30', 0, 0, 0),
(440, 3, '2019-05-09 10:34:45', 0.67, 0.99, 1),
(441, 1, '2019-05-09 10:34:45', 0.67, 0.99, 1),
(442, 2, '2019-05-09 10:34:45', 0.67, 0.99, 1),
(443, 1, '2019-05-09 10:34:54', 0.56, 0.99, 1),
(444, 2, '2019-05-09 10:34:54', 0.56, 0.99, 1),
(445, 3, '2019-05-09 10:34:54', 0.56, 0.99, 1),
(446, 3, '2019-05-09 10:40:14', 0.37, 1, 1),
(447, 3, '2019-05-09 10:40:14', 1, 1, 1),
(448, 1, '2019-05-09 10:40:14', 0.37, 1, 1),
(449, 2, '2019-05-09 10:40:14', 0.37, 1, 1),
(450, 1, '2019-05-09 10:40:14', 1, 1, 1),
(451, 2, '2019-05-09 10:40:14', 0, 0, 0),
(452, 3, '2019-05-09 10:41:24', 0.42, 1, 1),
(453, 3, '2019-05-09 10:41:24', 1, 1, 1),
(454, 1, '2019-05-09 10:41:24', 0.42, 1, 1),
(455, 1, '2019-05-09 10:41:24', 1, 1, 1),
(456, 2, '2019-05-09 10:41:24', 0.42, 1, 1),
(457, 2, '2019-05-09 10:41:24', 0, 0, 0),
(458, 1, '2019-05-09 10:41:34', 0.44, 0.99, 1),
(459, 1, '2019-05-09 10:41:34', 1, 0.99, 1),
(460, 3, '2019-05-09 10:41:34', 0.44, 0.99, 1),
(461, 3, '2019-05-09 10:41:34', 1, 0.99, 1),
(462, 2, '2019-05-09 10:41:34', 0.45, 0.99, 1),
(463, 2, '2019-05-09 10:41:34', 0, 0, 0),
(464, 1, '2019-05-09 10:41:35', 0.66, 1, 1),
(465, 2, '2019-05-09 10:41:35', 0.65, 1, 1),
(466, 1, '2019-05-09 10:41:35', 1, 1, 1),
(467, 3, '2019-05-09 10:41:35', 0.68, 1, 1),
(468, 3, '2019-05-09 10:41:35', 1, 1, 1),
(469, 2, '2019-05-09 10:41:35', 0.98, 1, 1),
(470, 2, '2019-05-09 10:41:36', 0.78, 0.97, 1),
(471, 1, '2019-05-09 10:41:36', 0.8, 0.97, 1),
(472, 3, '2019-05-09 10:41:36', 0.79, 0.97, 1),
(473, 1, '2019-05-09 10:41:37', 0.72, 0.97, 1),
(474, 3, '2019-05-09 10:41:37', 0.71, 0.97, 1),
(475, 2, '2019-05-09 10:41:37', 0.72, 0.97, 1),
(476, 2, '2019-05-09 10:41:37', 0, 0, 0),
(477, 3, '2019-05-09 10:41:37', 0, 0, 0),
(478, 1, '2019-05-09 10:41:37', 0, 0, 0),
(479, 2, '2019-05-09 10:42:32', 0.4, 1, 1),
(480, 1, '2019-05-09 10:42:32', 0.4, 1, 1),
(481, 1, '2019-05-09 10:42:32', 1, 1, 1),
(482, 2, '2019-05-09 10:42:32', 1, 1, 1),
(483, 3, '2019-05-09 10:42:32', 0.4, 1, 1),
(484, 3, '2019-05-09 10:42:32', 1, 1, 1),
(485, 2, '2019-05-09 10:43:03', 0.47, 0.99, 1),
(486, 1, '2019-05-09 10:43:03', 0.47, 0.99, 1),
(487, 3, '2019-05-09 10:43:03', 0.47, 0.99, 1),
(488, 3, '2019-05-09 10:43:03', 1, 0.99, 1),
(489, 1, '2019-05-09 10:43:03', 0, 0, 0),
(490, 2, '2019-05-09 10:43:03', 0, 0, 0),
(491, 1, '2019-05-09 10:43:07', 0.47, 0.99, 1),
(492, 3, '2019-05-09 10:43:07', 0.46, 0.99, 1),
(493, 2, '2019-05-09 10:43:07', 0.47, 0.99, 1),
(494, 1, '2019-05-09 10:44:11', 0.37, 0.99, 1),
(495, 2, '2019-05-09 10:44:11', 0.37, 0.99, 1),
(496, 1, '2019-05-09 10:44:11', 0, 0, 0),
(497, 3, '2019-05-09 10:44:11', 0.37, 0.99, 1),
(498, 3, '2019-05-09 10:44:11', 0, 0, 0),
(499, 2, '2019-05-09 10:44:11', 0, 0, 0),
(500, 2, '2019-05-09 10:44:45', 0.36, 0.97, 1),
(501, 1, '2019-05-09 10:44:45', 0.36, 0.97, 1),
(502, 3, '2019-05-09 10:44:45', 0.36, 0.97, 1),
(503, 1, '2019-05-09 10:45:14', 0.43, 0.99, 1),
(504, 2, '2019-05-09 10:45:14', 0.43, 0.99, 1),
(505, 2, '2019-05-09 10:45:14', 1, 0.99, 1),
(506, 3, '2019-05-09 10:45:14', 0.43, 0.99, 1),
(507, 1, '2019-05-09 10:45:14', 1, 0.99, 1),
(508, 3, '2019-05-09 10:45:14', 1, 0.99, 1),
(509, 1, '2019-05-09 10:46:00', 0.41, 0.99, 1),
(510, 3, '2019-05-09 10:46:00', 0.41, 0.99, 1),
(511, 2, '2019-05-09 10:46:00', 0.41, 0.99, 1),
(512, 2, '2019-05-09 10:46:00', 1, 0.99, 1),
(513, 3, '2019-05-09 10:46:00', 1, 0.99, 1),
(514, 1, '2019-05-09 10:46:00', 1, 0.99, 1),
(515, 3, '2019-05-09 10:46:28', 0.38, 1, 1),
(516, 2, '2019-05-09 10:46:28', 0.38, 1, 1),
(517, 1, '2019-05-09 10:46:28', 0.38, 1, 1),
(518, 3, '2019-05-09 10:46:42', 0.42, 0.98, 1),
(519, 2, '2019-05-09 10:46:42', 0.42, 0.98, 1),
(520, 1, '2019-05-09 10:46:42', 0.42, 0.98, 1),
(521, 2, '2019-05-09 10:46:50', 0.41, 0.98, 1),
(522, 3, '2019-05-09 10:46:50', 0.41, 0.98, 1),
(523, 1, '2019-05-09 10:46:50', 0.41, 0.98, 1),
(524, 1, '2019-05-09 10:47:05', 0.56, 0.99, 1),
(525, 3, '2019-05-09 10:47:05', 0.56, 0.99, 1),
(526, 2, '2019-05-09 10:47:05', 0.57, 0.99, 1),
(527, 3, '2019-05-09 10:53:10', 0.47, 1, 1),
(528, 2, '2019-05-09 10:53:10', 0.47, 1, 1),
(529, 2, '2019-05-09 10:53:10', 0, 0, 0),
(530, 3, '2019-05-09 10:53:10', 1, 1, 1),
(531, 1, '2019-05-09 10:53:10', 0.47, 1, 1),
(532, 1, '2019-05-09 10:53:10', 0, 0, 0),
(533, 3, '2019-05-09 10:53:48', 0.62, 0.98, 1),
(534, 1, '2019-05-09 10:53:48', 0.62, 0.98, 1),
(535, 2, '2019-05-09 10:53:48', 0.62, 0.98, 1);

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
(19, 'Doctor Strange', 'Scott Derrickson', 'Action, Adventure, Fantasy, Sci-Fi', 7.5, 'tt1211837', '/4PiiNGXj1KENTmCBHeN6Mskj2Fq.jpg', 'UPLOADED', 14),
(20, 'Inception', 'Christopher Nolan', 'Action, Adventure, Sci-Fi, Thriller', 8.8, 'tt1375666', '/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg', 'UPLOADED', 7),
(21, 'Suicide Squad', 'David Ayer', 'Action, Adventure, Fantasy, Sci-Fi', 6.1, 'tt1386697', '/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg', 'UPLOADED', 2000),
(22, 'Mad Max: Fury Road', 'George Miller', 'Action, Adventure, Sci-Fi, Thriller', 8.1, 'tt1392190', '/kqjL17yufvn9OVLyXYpvtyrFfak.jpg', 'UPLOADED', 106),
(23, 'Django Unchained', 'Quentin Tarantino', 'Drama, Western', 8.4, 'tt1853728', '/5WJnxuw41sddupf8cwOxYftuvJG.jpg', 'UPLOADED', 188),
(26, 'Star Wars: The Last Jedi', 'Rian Johnson', 'Action, Adventure, Fantasy, Sci-Fi', 7.2, 'tt2527336', '/kOVEVeg59E0wsnXmF9nrh6OmWII.jpg', 'UPLOADED', 1),
(27, 'Incredibles 2', 'Brad Bird', 'Animation, Action, Adventure, Comedy, Family, Sci-Fi', 7.7, 'tt3606756', '/9lFKBtaVIhP7E2Pk0IY1CwTKTMZ.jpg', 'UPLOADED', 333),
(28, 'Bridge of Spies', 'Steven Spielberg', 'Drama, History, Thriller', 7.6, 'tt3682448', '/xPCNA8zJxyyFKTj47QpvkXHukzB.jpg', 'UPLOADED', 199),
(30, 'Darkest Hour', 'Joe Wright', 'Biography, Drama, History, War', 7.4, 'tt4555426', '/xa6G3aKlysQeVg9wOb0dRcIGlWu.jpg', 'UPLOADED', 555),
(31, 'Bumblebee', 'Travis Knight', 'Action, Adventure, Sci-Fi', 7.0, 'tt4701182', '/fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg', 'UPLOADED', 0),
(32, 'Dunkirk', 'Christopher Nolan', 'Action, Drama, History, Thriller, War', 7.9, 'tt5013056', '/bOXBV303Fgkzn2K4FeKDc0O31q4.jpg', 'UPLOADED', 26),
(33, 'BlacKkKlansman', 'Spike Lee', 'Biography, Crime, Drama', 7.5, 'tt7349662', '/3ntR66u2SHZ2UA3r3DjF2Dl6Kwx.jpg', 'UPLOADED', 943),
(34, 'Captain America: The First Avenger', 'Joe Johnston', 'Action, Adventure, Sci-Fi', 6.9, 'tt0458339', '/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg', 'UPLOADED', 0),
(35, 'Captain America: The Winter Soldier', 'Anthony Russo, Joe Russo', 'Action, Adventure, Sci-Fi, Thriller', 7.8, 'tt1843866', '/5TQ6YDmymBpnF005OyoB7ohZps9.jpg', 'UPLOADED', 444),
(36, 'Avengers: Infinity War', 'Anthony Russo, Joe Russo', 'Action, Adventure, Sci-Fi', 8.5, 'tt4154756', '/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg', 'UPLOADED', 7),
(37, 'Avengers: Age of Ultron', 'Joss Whedon', 'Action, Adventure, Sci-Fi', 7.4, 'tt2395427', '/t90Y3G8UGQp0f0DrP60wRu9gfrH.jpg', 'UPLOADED', 2);

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
(14, 2, '4e71fbf6-7259-11e9-b0c8-a225d4b81d68');

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
(88, 3, 21, 'FETCHED'),
(89, 1, 21, 'FETCHED'),
(91, 2, 21, 'FETCHED'),
(93, 2, 36, 'FETCHED'),
(94, 1, 36, 'FETCHED'),
(95, 3, 36, 'FETCHED');

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
-- Dumping data for table `user_movie`
--

INSERT INTO `user_movie` (`id`, `user_id`, `movie_id`) VALUES
(78, 2, 21),
(79, 2, 36);

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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=536;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `supplier_movie`
--
ALTER TABLE `supplier_movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=96;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user_movie`
--
ALTER TABLE `user_movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;

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
