CREATE TABLE `wave` (
                           `id` varchar(45) NOT NULL,
                           `cityName` varchar(255) NOT NULL,
                           `state` varchar(4) DEFAULT NULL,
                           `period` int DEFAULT NULL,
                           `updatedAt` timestamp DEFAULT NULL,
                           `waveDay` timestamp DEFAULT NULL,
                           `unrest` varchar(20) DEFAULT NULL,
                           `height` float DEFAULT NULL,
                           `direction` varchar(10) DEFAULT NULL,
                           `wind` float DEFAULT NULL,
                           `windDirection` varchar(10) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


