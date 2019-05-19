-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 19, 2019 at 10:07 AM
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
  `available` tinyint(1) NOT NULL,
  `free_slots` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`id`, `supplier_id`, `timestamp`, `cpu_saturation`, `mem_saturation`, `available`, `free_slots`) VALUES
(1438, 2, '2019-05-18 20:10:06', 0, 0, 1, 5),
(1439, 2, '2019-05-18 20:10:06', 0, 0, 1, 5),
(1440, 1, '2019-05-18 20:10:06', 0, 0, 1, 5),
(1441, 1, '2019-05-18 20:10:06', 0, 0, 1, 5),
(1442, 1, '2019-05-18 20:10:09', 0, 0, 1, 3),
(1443, 1, '2019-05-18 20:10:11', 0, 0, 1, 3),
(1444, 3, '2019-05-18 20:10:11', 0, 0, 1, 5),
(1445, 1, '2019-05-18 20:10:12', 0, 0, 1, 3),
(1446, 3, '2019-05-18 20:10:12', 0, 0, 1, 5),
(1447, 3, '2019-05-18 20:10:13', 0, 0, 1, 5),
(1448, 1, '2019-05-18 20:10:13', 0, 0, 1, 3),
(1449, 1, '2019-05-18 20:10:14', 0, 0, 1, 3),
(1450, 3, '2019-05-18 20:10:14', 0, 0, 1, 5),
(1451, 3, '2019-05-18 20:10:14', 0, 0, 1, 5),
(1452, 1, '2019-05-18 20:10:14', 0, 0, 1, 3),
(1453, 1, '2019-05-18 20:10:15', 0, 0, 1, 3),
(1454, 3, '2019-05-18 20:10:15', 0, 0, 1, 5),
(1455, 3, '2019-05-18 20:10:16', 0, 0, 1, 5),
(1456, 1, '2019-05-18 20:10:16', 0, 0, 1, 3),
(1457, 3, '2019-05-18 20:10:17', 0, 0, 1, 5),
(1458, 1, '2019-05-18 20:10:17', 0, 0, 1, 3),
(1459, 1, '2019-05-18 20:10:17', 0, 0, 1, 3),
(1460, 3, '2019-05-18 20:10:17', 0, 0, 1, 5),
(1461, 1, '2019-05-18 20:10:18', 0, 0, 1, 3),
(1462, 3, '2019-05-18 20:10:18', 0, 0, 1, 5),
(1463, 1, '2019-05-18 20:10:19', 0, 0, 1, 3),
(1464, 3, '2019-05-18 20:10:19', 0, 0, 1, 5),
(1465, 1, '2019-05-18 20:10:20', 0, 0, 1, 3),
(1466, 3, '2019-05-18 20:10:20', 0, 0, 1, 5),
(1467, 1, '2019-05-18 20:10:20', 0, 0, 1, 3),
(1468, 3, '2019-05-18 20:10:21', 0, 0, 1, 5),
(1469, 3, '2019-05-18 20:10:21', 0, 0, 1, 5),
(1470, 1, '2019-05-18 20:10:21', 0, 0, 1, 4),
(1471, 1, '2019-05-18 20:10:22', 0, 0, 1, 5),
(1472, 3, '2019-05-18 20:10:22', 0, 0, 1, 5),
(1473, 1, '2019-05-18 20:10:23', 0, 0, 1, 4),
(1474, 1, '2019-05-18 20:10:23', 0, 0, 1, 4),
(1475, 3, '2019-05-18 20:10:23', 0, 0, 1, 5),
(1476, 1, '2019-05-18 20:10:25', 0, 0, 1, 4),
(1477, 3, '2019-05-18 20:10:25', 0, 0, 1, 5),
(1478, 1, '2019-05-18 20:10:26', 0, 0, 1, 4),
(1479, 3, '2019-05-18 20:10:26', 0, 0, 1, 5),
(1480, 1, '2019-05-18 20:10:26', 0, 0, 1, 4),
(1481, 3, '2019-05-18 20:10:26', 0, 0, 1, 5),
(1482, 3, '2019-05-18 20:10:27', 0, 0, 1, 5),
(1483, 1, '2019-05-18 20:10:27', 0, 0, 1, 4),
(1484, 3, '2019-05-18 20:10:28', 0, 0, 1, 5),
(1485, 1, '2019-05-18 20:10:28', 0, 0, 1, 4),
(1486, 1, '2019-05-18 20:10:29', 0, 0, 1, 4),
(1487, 3, '2019-05-18 20:10:29', 0, 0, 1, 5),
(1488, 3, '2019-05-18 20:10:29', 0, 0, 1, 5),
(1489, 1, '2019-05-18 20:10:29', 0, 0, 1, 4),
(1490, 3, '2019-05-18 20:10:30', 0, 0, 1, 5),
(1491, 1, '2019-05-18 20:10:30', 0, 0, 1, 4),
(1492, 1, '2019-05-18 20:10:31', 0, 0, 1, 4),
(1493, 3, '2019-05-18 20:10:31', 0, 0, 1, 5),
(1494, 1, '2019-05-18 20:10:32', 0, 0, 1, 4),
(1495, 3, '2019-05-18 20:10:32', 0, 0, 1, 5),
(1496, 3, '2019-05-18 20:10:33', 0, 0, 1, 5),
(1497, 1, '2019-05-18 20:10:33', 0, 0, 1, 4),
(1498, 3, '2019-05-18 20:10:34', 0, 0, 1, 5),
(1499, 1, '2019-05-18 20:10:34', 0, 0, 1, 4),
(1500, 3, '2019-05-18 20:10:36', 0, 0, 1, 5),
(1501, 1, '2019-05-18 20:10:36', 0, 0, 1, 5),
(1502, 1, '2019-05-18 20:12:06', 0, 0, 1, 5),
(1503, 1, '2019-05-18 20:12:08', 0, 0, 1, 4),
(1504, 1, '2019-05-18 20:12:11', 0, 0, 1, 4),
(1505, 2, '2019-05-18 20:12:11', 0, 0, 1, 5),
(1506, 2, '2019-05-18 20:12:14', 0, 0, 1, 5),
(1507, 1, '2019-05-18 20:12:14', 0, 0, 1, 4),
(1508, 2, '2019-05-18 20:12:17', 0, 0, 1, 5),
(1509, 1, '2019-05-18 20:12:17', 0, 0, 1, 4),
(1510, 1, '2019-05-18 20:12:20', 0, 0, 1, 4),
(1511, 2, '2019-05-18 20:12:20', 0, 0, 1, 5),
(1512, 1, '2019-05-18 20:16:24', 0, 0, 1, 5),
(1513, 1, '2019-05-18 20:16:24', 0, 0, 1, 5),
(1514, 2, '2019-05-18 20:16:24', 0, 0, 1, 5),
(1515, 2, '2019-05-18 20:16:24', 0, 0, 1, 5),
(1516, 1, '2019-05-18 20:16:27', 0, 0, 1, 3),
(1517, 1, '2019-05-18 20:16:30', 0, 0, 1, 3),
(1518, 3, '2019-05-18 20:16:30', 0, 0, 1, 5),
(1519, 1, '2019-05-18 20:18:11', 0, 0, 0, 0),
(1520, 3, '2019-05-18 20:18:11', 0, 0, 0, 0),
(1521, 1, '2019-05-18 20:18:13', 0, 0, 0, 0),
(1522, 3, '2019-05-18 20:18:13', 0, 0, 0, 0),
(1523, 3, '2019-05-18 20:18:16', 0, 0, 0, 0),
(1524, 1, '2019-05-18 20:18:16', 0, 0, 0, 0),
(1525, 1, '2019-05-18 20:19:25', 0, 0, 1, 5),
(1526, 3, '2019-05-18 20:19:25', 0, 0, 1, 5),
(1527, 1, '2019-05-18 20:19:26', 0, 0, 1, 5),
(1528, 3, '2019-05-18 20:19:26', 0, 0, 1, 5),
(1529, 1, '2019-05-18 20:19:28', 0, 0, 1, 3),
(1530, 1, '2019-05-18 20:19:31', 0, 0, 1, 3),
(1531, 3, '2019-05-18 20:19:32', 0, 0, 1, 5),
(1532, 1, '2019-05-18 20:19:34', 0, 0, 1, 3),
(1533, 3, '2019-05-18 20:19:34', 0, 0, 1, 5),
(1534, 1, '2019-05-18 20:19:38', 0, 0, 1, 3),
(1535, 3, '2019-05-18 20:19:38', 0, 0, 1, 5),
(1536, 3, '2019-05-18 20:20:54', 0, 0, 1, 5),
(1537, 1, '2019-05-18 20:20:54', 0, 0, 1, 5),
(1538, 1, '2019-05-18 20:20:56', 0, 0, 1, 4),
(1539, 2, '2019-05-18 20:20:59', 0, 0, 1, 5),
(1540, 1, '2019-05-18 20:20:59', 0, 0, 1, 4),
(1541, 1, '2019-05-18 20:21:02', 0, 0, 1, 4),
(1542, 2, '2019-05-18 20:21:02', 0, 0, 1, 4),
(1543, 1, '2019-05-18 20:21:05', 0, 0, 1, 4),
(1544, 3, '2019-05-18 20:21:05', 0, 0, 1, 5),
(1545, 2, '2019-05-18 20:21:05', 0, 0, 1, 4),
(1546, 1, '2019-05-18 20:21:08', 0, 0, 1, 4),
(1547, 2, '2019-05-18 20:21:08', 0, 0, 1, 4),
(1548, 3, '2019-05-18 20:21:08', 0, 0, 1, 4),
(1549, 3, '2019-05-18 20:23:53', 0, 0, 1, 5),
(1550, 3, '2019-05-18 20:23:53', 0, 0, 1, 5),
(1551, 2, '2019-05-18 20:23:53', 0, 0, 1, 5),
(1552, 2, '2019-05-18 20:23:53', 0, 0, 1, 5),
(1553, 1, '2019-05-18 20:23:53', 0, 0, 1, 5),
(1554, 1, '2019-05-18 20:23:53', 0, 0, 1, 5),
(1555, 3, '2019-05-18 20:23:55', 0, 0, 1, 5),
(1556, 2, '2019-05-18 20:23:55', 0, 0, 1, 5),
(1557, 1, '2019-05-18 20:23:55', 0, 0, 1, 5),
(1558, 1, '2019-05-18 20:23:57', 0, 0, 1, 2),
(1559, 1, '2019-05-18 20:24:00', 0, 0, 1, 2),
(1560, 3, '2019-05-18 20:24:00', 0, 0, 1, 5),
(1561, 1, '2019-05-18 20:24:03', 0, 0, 1, 2),
(1562, 3, '2019-05-18 20:24:03', 0, 0, 1, 4),
(1563, 1, '2019-05-18 20:24:05', 0, 0, 1, 2),
(1564, 3, '2019-05-18 20:24:05', 0, 0, 1, 3),
(1565, 1, '2019-05-19 07:22:31', 0, 0, 1, 5),
(1566, 1, '2019-05-19 07:22:31', 0, 0, 1, 5),
(1567, 1, '2019-05-19 07:22:31', 0, 0, 1, 5),
(1568, 1, '2019-05-19 07:22:31', 0, 0, 1, 5),
(1569, 1, '2019-05-19 07:22:31', 0, 0, 1, 5),
(1570, 1, '2019-05-19 07:22:32', 0, 0, 1, 0),
(1571, 1, '2019-05-19 07:22:33', 0, 0, 1, 0),
(1572, 2, '2019-05-19 07:22:34', 0, 0, 1, 5),
(1573, 1, '2019-05-19 07:22:34', 0, 0, 1, 0),
(1574, 1, '2019-05-19 07:22:34', 0, 0, 1, 0),
(1575, 2, '2019-05-19 07:22:34', 0, 0, 1, 5),
(1576, 1, '2019-05-19 07:22:35', 0, 0, 1, 0),
(1577, 1, '2019-05-19 07:22:35', 0, 0, 1, 0),
(1578, 2, '2019-05-19 07:22:36', 0, 0, 1, 5),
(1579, 2, '2019-05-19 07:22:36', 0, 0, 1, 5),
(1580, 1, '2019-05-19 07:22:36', 0, 0, 1, 0),
(1581, 2, '2019-05-19 07:22:36', 0, 0, 1, 1),
(1582, 1, '2019-05-19 07:22:38', 0, 0, 1, 0),
(1583, 3, '2019-05-19 07:22:38', 0, 0, 1, 5),
(1584, 2, '2019-05-19 07:22:39', 0, 0, 1, 1),
(1585, 1, '2019-05-19 07:22:41', 0, 0, 1, 0),
(1586, 3, '2019-05-19 07:22:41', 0, 0, 1, 5),
(1587, 2, '2019-05-19 07:22:41', 0, 0, 1, 1),
(1588, 1, '2019-05-19 07:22:42', 0, 0, 1, 0),
(1589, 3, '2019-05-19 07:22:42', 0, 0, 1, 5),
(1590, 2, '2019-05-19 07:22:42', 0, 0, 1, 1),
(1591, 1, '2019-05-19 07:22:43', 0, 0, 1, 0),
(1592, 3, '2019-05-19 07:22:43', 0, 0, 1, 5),
(1593, 2, '2019-05-19 07:22:43', 0, 0, 1, 1),
(1594, 1, '2019-05-19 07:22:44', 0, 0, 1, 0),
(1595, 3, '2019-05-19 07:22:44', 0, 0, 1, 5),
(1596, 2, '2019-05-19 07:22:44', 0, 0, 1, 1),
(1597, 3, '2019-05-19 07:22:46', 0, 0, 1, 5),
(1598, 1, '2019-05-19 07:22:46', 0, 0, 1, 0),
(1599, 2, '2019-05-19 07:22:46', 0, 0, 1, 1),
(1600, 3, '2019-05-19 07:22:48', 0, 0, 1, 5),
(1601, 1, '2019-05-19 07:22:48', 0, 0, 1, 0),
(1602, 2, '2019-05-19 07:22:48', 0, 0, 1, 1),
(1603, 1, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1604, 1, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1605, 1, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1606, 3, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1607, 3, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1608, 3, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1609, 2, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1610, 2, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1611, 2, '2019-05-19 07:41:36', 0, 0, 1, 5),
(1612, 2, '2019-05-19 07:41:38', 0, 0, 1, 5),
(1613, 3, '2019-05-19 07:41:38', 0, 0, 1, 5),
(1614, 1, '2019-05-19 07:41:38', 0, 0, 1, 5),
(1615, 1, '2019-05-19 07:41:39', 0, 0, 1, 1),
(1616, 1, '2019-05-19 07:41:42', 0, 0, 1, 1),
(1617, 3, '2019-05-19 07:41:42', 0, 0, 1, 5),
(1618, 1, '2019-05-19 07:41:42', 0, 0, 1, 1),
(1619, 3, '2019-05-19 07:41:42', 0, 0, 1, 5),
(1620, 1, '2019-05-19 07:41:44', 0, 0, 1, 1),
(1621, 3, '2019-05-19 07:41:44', 0, 0, 1, 4),
(1622, 1, '2019-05-19 07:41:44', 0, 0, 1, 1),
(1623, 3, '2019-05-19 07:41:44', 0, 0, 1, 3),
(1624, 1, '2019-05-19 07:41:46', 0, 0, 1, 1),
(1625, 3, '2019-05-19 07:41:46', 0, 0, 1, 2),
(1626, 2, '2019-05-19 07:41:48', 0, 0, 1, 5),
(1627, 1, '2019-05-19 07:41:48', 0, 0, 1, 1),
(1628, 3, '2019-05-19 07:41:48', 0, 0, 1, 2),
(1629, 1, '2019-05-19 07:41:48', 0, 0, 1, 1),
(1630, 3, '2019-05-19 07:41:48', 0, 0, 1, 2),
(1631, 2, '2019-05-19 07:41:48', 0, 0, 1, 5),
(1632, 1, '2019-05-19 07:41:50', 0, 0, 1, 1),
(1633, 3, '2019-05-19 07:41:50', 0, 0, 1, 2),
(1634, 2, '2019-05-19 07:41:50', 0, 0, 1, 3),
(1635, 3, '2019-05-19 07:41:52', 0, 0, 1, 2),
(1636, 1, '2019-05-19 07:41:52', 0, 0, 1, 1),
(1637, 2, '2019-05-19 07:41:52', 0, 0, 1, 2),
(1638, 3, '2019-05-19 07:49:12', 0, 0, 1, 5),
(1639, 3, '2019-05-19 07:49:12', 0, 0, 1, 5),
(1640, 3, '2019-05-19 07:49:13', 0, 0, 1, 4),
(1641, 3, '2019-05-19 07:49:14', 0, 0, 1, 3),
(1642, 3, '2019-05-19 07:49:15', 0, 0, 1, 2),
(1643, 3, '2019-05-19 07:49:17', 0, 0, 1, 2),
(1644, 1, '2019-05-19 07:49:17', 0, 0, 1, 5),
(1645, 1, '2019-05-19 07:49:18', 0, 0, 1, 5),
(1646, 3, '2019-05-19 07:49:18', 0, 0, 1, 2),
(1647, 1, '2019-05-19 07:49:19', 0, 0, 1, 3),
(1648, 3, '2019-05-19 07:49:19', 0, 0, 1, 2),
(1649, 3, '2019-05-19 07:49:21', 0, 0, 1, 2),
(1650, 1, '2019-05-19 07:49:21', 0, 0, 1, 2),
(1651, 2, '2019-05-19 07:49:23', 0, 0, 1, 5),
(1652, 3, '2019-05-19 07:49:23', 0, 0, 1, 2),
(1653, 1, '2019-05-19 07:49:24', 0, 0, 1, 2),
(1654, 3, '2019-05-19 07:49:24', 0, 0, 1, 2),
(1655, 1, '2019-05-19 07:49:24', 0, 0, 1, 2),
(1656, 2, '2019-05-19 07:49:24', 0, 0, 1, 5),
(1657, 2, '2019-05-19 07:49:25', 0, 0, 1, 3),
(1658, 1, '2019-05-19 07:49:25', 0, 0, 1, 2),
(1659, 3, '2019-05-19 07:49:25', 0, 0, 1, 2),
(1660, 3, '2019-05-19 07:49:27', 0, 0, 1, 2),
(1661, 2, '2019-05-19 07:49:27', 0, 0, 1, 2),
(1662, 1, '2019-05-19 07:49:27', 0, 0, 1, 2),
(1663, 3, '2019-05-19 07:49:29', 0, 0, 1, 2),
(1664, 1, '2019-05-19 07:49:29', 0, 0, 1, 2),
(1665, 2, '2019-05-19 07:49:29', 0, 0, 1, 2),
(1666, 2, '2019-05-19 07:53:57', 0, 0, 1, 5),
(1667, 2, '2019-05-19 07:53:58', 0, 0, 1, 4),
(1668, 2, '2019-05-19 07:54:00', 0, 0, 1, 3),
(1669, 2, '2019-05-19 07:54:02', 0, 0, 1, 2),
(1670, 1, '2019-05-19 07:54:04', 0, 0, 1, 5),
(1671, 2, '2019-05-19 07:54:04', 0, 0, 1, 2),
(1672, 2, '2019-05-19 07:54:04', 0, 0, 1, 2),
(1673, 1, '2019-05-19 07:54:04', 0, 0, 1, 5),
(1674, 2, '2019-05-19 07:54:06', 0, 0, 1, 2),
(1675, 1, '2019-05-19 07:54:06', 0, 0, 1, 3),
(1676, 1, '2019-05-19 07:54:08', 0, 0, 1, 2),
(1677, 2, '2019-05-19 07:54:08', 0, 0, 1, 2),
(1678, 1, '2019-05-19 07:54:10', 0, 0, 1, 2),
(1679, 3, '2019-05-19 07:54:10', 0, 0, 1, 5),
(1680, 2, '2019-05-19 07:54:10', 0, 0, 1, 2),
(1681, 3, '2019-05-19 07:54:10', 0, 0, 1, 4),
(1682, 1, '2019-05-19 07:54:10', 0, 0, 1, 2),
(1683, 2, '2019-05-19 07:54:11', 0, 0, 1, 2),
(1684, 1, '2019-05-19 07:54:12', 0, 0, 1, 2),
(1685, 2, '2019-05-19 07:54:12', 0, 0, 1, 2),
(1686, 3, '2019-05-19 07:54:12', 0, 0, 1, 3),
(1687, 1, '2019-05-19 07:54:14', 0, 0, 1, 2),
(1688, 3, '2019-05-19 07:54:14', 0, 0, 1, 2),
(1689, 2, '2019-05-19 07:54:14', 0, 0, 1, 2),
(1690, 1, '2019-05-19 07:54:16', 0, 0, 1, 2),
(1691, 2, '2019-05-19 07:54:16', 0, 0, 1, 2),
(1692, 3, '2019-05-19 07:54:16', 0, 0, 1, 2),
(1693, 3, '2019-05-19 07:57:42', 0, 0, 1, 5),
(1694, 1, '2019-05-19 07:57:42', 0, 0, 1, 5),
(1695, 2, '2019-05-19 07:57:42', 0, 0, 1, 5),
(1696, 1, '2019-05-19 07:57:43', 0, 0, 1, 4),
(1697, 1, '2019-05-19 07:57:45', 0, 0, 1, 3),
(1698, 1, '2019-05-19 07:57:47', 0, 0, 1, 2),
(1699, 3, '2019-05-19 07:57:49', 0, 0, 1, 5),
(1700, 1, '2019-05-19 07:57:49', 0, 0, 1, 2),
(1701, 1, '2019-05-19 07:57:49', 0, 0, 1, 2),
(1702, 3, '2019-05-19 07:57:49', 0, 0, 1, 5),
(1703, 3, '2019-05-19 07:57:51', 0, 0, 1, 3),
(1704, 1, '2019-05-19 07:57:51', 0, 0, 1, 2),
(1705, 3, '2019-05-19 07:57:53', 0, 0, 1, 2),
(1706, 1, '2019-05-19 07:57:53', 0, 0, 1, 2),
(1707, 2, '2019-05-19 07:57:55', 0, 0, 1, 5),
(1708, 3, '2019-05-19 07:57:55', 0, 0, 1, 2),
(1709, 1, '2019-05-19 07:57:55', 0, 0, 1, 2),
(1710, 1, '2019-05-19 07:57:55', 0, 0, 1, 2),
(1711, 2, '2019-05-19 07:57:55', 0, 0, 1, 5),
(1712, 3, '2019-05-19 07:57:55', 0, 0, 1, 2),
(1713, 1, '2019-05-19 07:57:57', 0, 0, 1, 2),
(1714, 3, '2019-05-19 07:57:57', 0, 0, 1, 2),
(1715, 2, '2019-05-19 07:57:57', 0, 0, 1, 3),
(1716, 1, '2019-05-19 07:57:59', 0, 0, 1, 2),
(1717, 3, '2019-05-19 07:57:59', 0, 0, 1, 2),
(1718, 2, '2019-05-19 07:57:59', 0, 0, 1, 2),
(1719, 2, '2019-05-19 07:58:01', 0, 0, 1, 2),
(1720, 1, '2019-05-19 07:58:01', 0, 0, 1, 2),
(1721, 3, '2019-05-19 07:58:01', 0, 0, 1, 2);

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
(27, 'Incredibles 2', 'Brad Bird', 'Animation, Action, Adventure, Comedy, Family, Sci-Fi', 7.7, 'tt3606756', '/9lFKBtaVIhP7E2Pk0IY1CwTKTMZ.jpg', 'UPLOADED', 347),
(28, 'Bridge of Spies', 'Steven Spielberg', 'Drama, History, Thriller', 7.6, 'tt3682448', '/xPCNA8zJxyyFKTj47QpvkXHukzB.jpg', 'UPLOADED', 199),
(30, 'Darkest Hour', 'Joe Wright', 'Biography, Drama, History, War', 7.4, 'tt4555426', '/xa6G3aKlysQeVg9wOb0dRcIGlWu.jpg', 'UPLOADED', 561),
(31, 'Bumblebee', 'Travis Knight', 'Action, Adventure, Sci-Fi', 7.0, 'tt4701182', '/fw02ONlDhrYjTSZV8XO6hhU3ds3.jpg', 'UPLOADED', 0),
(32, 'Dunkirk', 'Christopher Nolan', 'Action, Drama, History, Thriller, War', 7.9, 'tt5013056', '/bOXBV303Fgkzn2K4FeKDc0O31q4.jpg', 'UPLOADED', 437),
(33, 'BlacKkKlansman', 'Spike Lee', 'Biography, Crime, Drama', 7.5, 'tt7349662', '/3ntR66u2SHZ2UA3r3DjF2Dl6Kwx.jpg', 'UPLOADED', 945),
(34, 'Captain America: The First Avenger', 'Joe Johnston', 'Action, Adventure, Sci-Fi', 6.9, 'tt0458339', '/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg', 'UPLOADED', 0),
(35, 'Captain America: The Winter Soldier', 'Anthony Russo, Joe Russo', 'Action, Adventure, Sci-Fi, Thriller', 7.8, 'tt1843866', '/5TQ6YDmymBpnF005OyoB7ohZps9.jpg', 'UPLOADED', 446),
(36, 'Avengers: Infinity War', 'Anthony Russo, Joe Russo', 'Action, Adventure, Sci-Fi', 8.5, 'tt4154756', '/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg', 'UPLOADED', 8),
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
(5, 2, '0'),
(16, 2, '1'),
(17, 2, '2'),
(18, 2, '3'),
(19, 2, '4'),
(20, 2, '5'),
(21, 2, '6'),
(22, 2, '7'),
(23, 2, '8'),
(24, 2, '9'),
(25, 2, '10');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` int(10) UNSIGNED NOT NULL,
  `ip` varchar(512) NOT NULL,
  `port` varchar(512) NOT NULL,
  `token` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL COMMENT 'AWAKE or SLEEP',
  `slots` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `ip`, `port`, `token`, `status`, `slots`) VALUES
(1, 'localhost', '8081', 'tokensupplier1', 'AWAKE', 5),
(2, 'localhost', '8082', 'tokensupplier2', 'AWAKE', 5),
(3, 'localhost', '8083', 'tokensupplier3', 'AWAKE', 5);

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
(21, 1, 32, 'FETCHED'),
(23, 3, 32, 'FETCHED'),
(24, 2, 32, 'FETCHED');

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
(1, 2, 30),
(2, 2, 30),
(3, 2, 30),
(4, 2, 30),
(5, 2, 35),
(6, 2, 35),
(7, 2, 27),
(8, 2, 27),
(9, 2, 27),
(10, 2, 27),
(11, 2, 27),
(12, 2, 27),
(13, 2, 27),
(14, 2, 27),
(15, 2, 27),
(16, 2, 27),
(17, 2, 27),
(18, 2, 27),
(19, 2, 27),
(20, 2, 27),
(21, 2, 32),
(22, 2, 32),
(23, 2, 32),
(24, 2, 32),
(25, 2, 32),
(26, 2, 32),
(27, 2, 32),
(28, 2, 32),
(29, 2, 32),
(30, 2, 32),
(31, 2, 32),
(32, 2, 32),
(33, 2, 32),
(34, 2, 32),
(35, 2, 32),
(36, 2, 32),
(37, 2, 32),
(38, 2, 32),
(39, 2, 32),
(40, 2, 32),
(41, 2, 32),
(42, 2, 32),
(43, 2, 32),
(44, 2, 32),
(45, 2, 32),
(46, 2, 32),
(47, 2, 32),
(48, 2, 32),
(49, 2, 32),
(50, 2, 32),
(51, 2, 32),
(52, 2, 32),
(53, 2, 32),
(54, 2, 32),
(55, 2, 32),
(56, 2, 32),
(57, 2, 32),
(58, 2, 32),
(59, 2, 32),
(60, 2, 32),
(61, 2, 32),
(62, 2, 32),
(63, 2, 32),
(64, 2, 32),
(65, 2, 32),
(66, 2, 32),
(67, 2, 32),
(68, 2, 32),
(69, 2, 32),
(70, 2, 32),
(71, 2, 32),
(72, 2, 32),
(73, 2, 32),
(74, 2, 32),
(75, 2, 32),
(76, 2, 32),
(77, 2, 32),
(78, 2, 32),
(79, 2, 32),
(80, 2, 32),
(81, 2, 32),
(82, 2, 32),
(83, 2, 32),
(84, 2, 32),
(85, 2, 32),
(86, 2, 32),
(87, 2, 32),
(88, 2, 32),
(89, 2, 32),
(90, 2, 32),
(91, 2, 32),
(92, 2, 32),
(93, 2, 32),
(94, 2, 32),
(95, 2, 32),
(96, 2, 32),
(97, 2, 32),
(98, 2, 32),
(99, 2, 32),
(100, 2, 32),
(101, 2, 32),
(102, 2, 32),
(103, 2, 32),
(104, 2, 32),
(105, 2, 32),
(106, 2, 32),
(107, 2, 32),
(108, 2, 32),
(109, 2, 32),
(110, 2, 32),
(111, 2, 32),
(112, 2, 32),
(113, 2, 32),
(114, 2, 32),
(115, 2, 32),
(116, 2, 32),
(117, 2, 32),
(118, 2, 32),
(119, 2, 32),
(120, 2, 32),
(121, 2, 32),
(122, 2, 32),
(123, 2, 32),
(124, 2, 32),
(125, 2, 32),
(126, 2, 32),
(127, 2, 32),
(128, 2, 32),
(129, 2, 32),
(130, 2, 32),
(131, 2, 32),
(132, 2, 32),
(133, 2, 32),
(134, 2, 32),
(135, 2, 32),
(136, 2, 32),
(137, 2, 32),
(138, 2, 32),
(139, 2, 32),
(140, 2, 32),
(141, 2, 32),
(142, 2, 32),
(143, 2, 32),
(144, 2, 32),
(145, 2, 32),
(146, 2, 32),
(147, 2, 32),
(148, 2, 32),
(149, 2, 32),
(150, 2, 32),
(151, 2, 32),
(152, 2, 32),
(153, 2, 32),
(154, 2, 32),
(155, 2, 32),
(156, 2, 32),
(157, 2, 32),
(158, 2, 32),
(159, 2, 32),
(160, 2, 32),
(161, 2, 32),
(162, 2, 32),
(163, 2, 32),
(164, 2, 32),
(165, 2, 32),
(166, 2, 32),
(167, 2, 32),
(168, 2, 32),
(169, 2, 32),
(170, 2, 32),
(171, 2, 32),
(172, 2, 32),
(173, 2, 32),
(174, 2, 32),
(175, 2, 32),
(176, 2, 32),
(177, 2, 32),
(178, 2, 32),
(179, 2, 32),
(180, 2, 32),
(181, 2, 32),
(182, 2, 32),
(183, 2, 32),
(184, 2, 32),
(185, 2, 32),
(186, 2, 32),
(187, 2, 32),
(188, 2, 32),
(189, 2, 32),
(190, 2, 32),
(191, 2, 32),
(192, 2, 32),
(193, 2, 32),
(194, 2, 32),
(195, 2, 32),
(196, 2, 32),
(197, 2, 32),
(198, 2, 32),
(199, 2, 32),
(200, 2, 32),
(201, 2, 32),
(202, 2, 32),
(203, 2, 32),
(204, 2, 32),
(205, 2, 32),
(206, 2, 32),
(207, 2, 32),
(208, 2, 32),
(209, 2, 32),
(210, 2, 32),
(211, 2, 32),
(212, 2, 32),
(213, 2, 32),
(214, 2, 32),
(215, 2, 32),
(216, 2, 32),
(217, 2, 32),
(218, 2, 32),
(219, 2, 32),
(220, 2, 32),
(221, 2, 32),
(222, 2, 32),
(223, 2, 32),
(224, 2, 32),
(225, 2, 32),
(226, 2, 32),
(227, 2, 32),
(228, 2, 32),
(229, 2, 32),
(230, 2, 32),
(231, 2, 32),
(232, 2, 32),
(233, 2, 32),
(234, 2, 32),
(235, 2, 32),
(236, 2, 32),
(237, 2, 32),
(238, 2, 32),
(239, 2, 32),
(240, 2, 32),
(241, 2, 32),
(242, 2, 32),
(243, 2, 32),
(244, 2, 32),
(245, 2, 32),
(246, 2, 32),
(247, 2, 32),
(248, 2, 32),
(249, 2, 32),
(250, 2, 32),
(251, 2, 32),
(252, 2, 32),
(253, 2, 32),
(254, 2, 32),
(255, 2, 32),
(256, 2, 32),
(257, 2, 32),
(258, 2, 32),
(259, 2, 32),
(260, 2, 32),
(261, 2, 32),
(262, 2, 32),
(263, 2, 32),
(264, 2, 32),
(265, 2, 32),
(266, 2, 32),
(267, 2, 32),
(268, 2, 32),
(269, 2, 32),
(270, 2, 32),
(271, 2, 32),
(272, 2, 32),
(273, 2, 32),
(274, 2, 32),
(275, 2, 32),
(276, 2, 32),
(277, 2, 32),
(278, 2, 32),
(279, 2, 32),
(280, 2, 32),
(281, 2, 32),
(282, 2, 32),
(283, 2, 32),
(284, 2, 32),
(285, 2, 32),
(286, 2, 32),
(287, 2, 32),
(288, 2, 32),
(289, 2, 32),
(290, 2, 32),
(291, 2, 32),
(292, 2, 32),
(293, 2, 32),
(294, 2, 32),
(295, 2, 32),
(296, 2, 32),
(297, 2, 32),
(298, 2, 32),
(299, 2, 32),
(300, 2, 32),
(301, 2, 32),
(302, 2, 32),
(303, 2, 32),
(304, 2, 32),
(305, 2, 32),
(306, 2, 32),
(307, 2, 32),
(308, 2, 32),
(309, 2, 32),
(310, 2, 32),
(311, 2, 32),
(312, 2, 32),
(313, 2, 32),
(314, 2, 32),
(315, 2, 32),
(316, 2, 32),
(317, 2, 32),
(318, 2, 32),
(319, 2, 32),
(320, 2, 32),
(321, 2, 32),
(322, 2, 32),
(323, 2, 32),
(324, 2, 32),
(325, 2, 32),
(326, 2, 32),
(327, 2, 32),
(328, 2, 32),
(329, 2, 32),
(330, 2, 32),
(331, 2, 32),
(332, 2, 32),
(333, 2, 32),
(334, 2, 32),
(335, 2, 32),
(336, 2, 32),
(337, 2, 32),
(338, 2, 32),
(339, 2, 32),
(340, 2, 32),
(341, 2, 32),
(342, 2, 32),
(343, 2, 32),
(344, 2, 32),
(345, 2, 32),
(346, 2, 32),
(347, 2, 32),
(348, 2, 32),
(349, 2, 32),
(350, 2, 32),
(351, 2, 32),
(352, 2, 32),
(353, 2, 32),
(354, 2, 32),
(355, 2, 32),
(356, 2, 32),
(357, 2, 32),
(358, 2, 32),
(359, 2, 32),
(360, 2, 32),
(361, 2, 32),
(362, 2, 32),
(363, 2, 32),
(364, 2, 32),
(365, 2, 32),
(366, 2, 32),
(367, 2, 32),
(368, 2, 32),
(369, 2, 32),
(370, 2, 32),
(371, 2, 32),
(372, 2, 32),
(373, 2, 32),
(374, 2, 32),
(375, 2, 32),
(376, 2, 32),
(377, 2, 32),
(378, 2, 32),
(379, 2, 32),
(380, 2, 32),
(381, 2, 32),
(382, 2, 32),
(383, 2, 32),
(384, 2, 32),
(385, 2, 32),
(386, 2, 32),
(387, 2, 32),
(388, 2, 32),
(389, 2, 32),
(390, 2, 32),
(391, 2, 32),
(392, 2, 32),
(393, 2, 32),
(394, 2, 32),
(395, 2, 32),
(396, 2, 32),
(397, 2, 32),
(398, 2, 32),
(399, 2, 32),
(400, 2, 32),
(401, 2, 32),
(402, 2, 32),
(403, 2, 32),
(404, 2, 32),
(405, 2, 32),
(406, 2, 32),
(407, 2, 32),
(408, 2, 32),
(409, 2, 32),
(410, 2, 32),
(411, 2, 32),
(412, 2, 32),
(413, 2, 32),
(414, 2, 32),
(415, 2, 32),
(416, 2, 32),
(417, 2, 32),
(418, 2, 32),
(419, 2, 32),
(420, 2, 32),
(421, 2, 32),
(422, 2, 32),
(423, 2, 32),
(424, 2, 32),
(425, 2, 32),
(426, 2, 32),
(427, 2, 32),
(428, 2, 32),
(429, 2, 32),
(430, 2, 32),
(431, 2, 32);

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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1722;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `supplier_movie`
--
ALTER TABLE `supplier_movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user_movie`
--
ALTER TABLE `user_movie`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=432;

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
