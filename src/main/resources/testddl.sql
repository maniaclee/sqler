CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `sex` smallint(2) DEFAULT '0',
  `email` varchar(256) DEFAULT NULL,
  `phone` varchar(256) DEFAULT NULL,
  `level` int(11) DEFAULT '0',
  `image_url` varchar(256) DEFAULT 'img/user-anonymous.jpg',
  `image_thumb_url` varchar(256) DEFAULT NULL,
  `role` varchar(15) DEFAULT 'USER',
  `enabled` smallint(6) DEFAULT '1',
  `password` varchar(256) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_last_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

CREATE TABLE `User_Detail` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;