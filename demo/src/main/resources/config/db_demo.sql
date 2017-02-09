CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `content` text,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `totalPrice` int,
  `unitPrice` int,
  `roomMainInfo` varchar(255),
  `roomSubInfo` varchar(255),
  `typeMainInfo` varchar(255),
  `typeSubInfo` varchar(255),
  `areaMainInfo` varchar(255),
  `areaSubInfo` varchar(255),
  `community` varchar(255),
  `position1` varchar(255),
  `position2` varchar(255),
  `position3` varchar(255),
  `position4` varchar(255),
  `content` text,
  `update_time` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

