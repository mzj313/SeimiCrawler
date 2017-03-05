CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `content` text,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_zaishou` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `totalPrice` varchar(11),
  `unitPrice` varchar(11),
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
  `url`  varchar(500),
  `update_time` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_chengjiao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `totalPrice` varchar(11),
  `unitPrice` varchar(11),
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
  `url`  varchar(500),
  `update_time` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_chengjiao2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `totalPrice` varchar(11),
  `unitPrice` varchar(11),
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
  `dealDate` varchar(20),
  `content` text,
  `url`  varchar(500),
  `update_time` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_xiaoqu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `houseInfo` varchar(200),
  `positionInfo1` varchar(255),
  `positionInfo2` varchar(255),
  `positionInfo` varchar(255),
  `tagList` varchar(255),
  `averagePrice` varchar(255),
  `num` varchar(255),
  `url`  varchar(500),
  `update_time` timestamp,
  `totalPage`  int(11),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
