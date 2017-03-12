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
  `rid`  varchar(32),
  PRIMARY KEY (`id`),
  INDEX `idx` (`title`, `totalPrice`, `unitPrice`, `dealDate`) USING BTREE,
  INDEX `idx_rid` (`rid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_chengjiao3` (
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
  `rid`  varchar(32),
  PRIMARY KEY (`id`),
  INDEX `idx` (`title`, `totalPrice`, `unitPrice`, `dealDate`) USING BTREE,
  INDEX `idx_rid` (`rid`) USING BTREE,
  INDEX `idx_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_xiaoqu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) DEFAULT NULL,
  `houseInfo` varchar(200),
  `positionInfo1` varchar(100),
  `positionInfo2` varchar(100),
  `positionInfo` varchar(255),
  `tagList` varchar(255),
  `averagePrice` varchar(255),
  `num` varchar(255),
  `url`  varchar(500),
  `update_time` timestamp,
  `totalPage`  int(11),
  `fetchPage`  int(11),
  `rid`  varchar(32),
  PRIMARY KEY (`id`),
  INDEX `idx` (`title`, `positionInfo1`, `positionInfo2`) USING BTREE ,
  INDEX `idx_rid` (`rid`) USING BTREE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(64),
  `msg` varchar(255) DEFAULT NULL,
  `createtime` timestamp,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `ljhouse_district` (
  `id` int(11) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  INDEX `idx` (`name`) USING BTREE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('1', '西城');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('2', '东城');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('3', '海淀');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('4', '朝阳');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('5', '石景山');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('6', '丰台');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('7', '通州');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('8', '门头沟');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('9', '顺义');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('10', '大兴');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('11', '房山');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('12', '昌平');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('13', '亦庄开发区');
INSERT INTO `ljhouse_district` (`id`, `name`) VALUES ('14', '燕郊');
