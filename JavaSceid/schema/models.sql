
CREATE TABLE `patient` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `medic_id` int(5) NOT NULL,
  `name` varchar(200) NOT NULL,
  `same` varchar(50) NOT NULL,
  `age` varchar(10) NOT NULL,
  `sex` varchar(30) NOT NULL,
  `color` varchar(30) NOT NULL,
  `profession` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `born_city` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` datetime NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `medic` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(5) NOT NULL,
  `name` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `phone` varchar(128) NOT NULL,
  `city` varchar(255) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` datetime NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `clinical` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `patient_id` int(5) NOT NULL,
  `bloating` tinyint(1) NOT NULL,
  `epigastric_pain` tinyint(1) NOT NULL,
  `slimming` tinyint(1) NOT NULL,
  `palpable_lymphonodes` varchar(255) NOT NULL,
  `anorexia` tinyint(1) NOT NULL,
  `gastrointestinal_bleeding` tinyint(1) NOT NULL,
  `palpable_mass` varchar(255) NOT NULL,
  `vomiting` tinyint(1) NOT NULL,
  `hepatomegaly` tinyint(1) NOT NULL,
  `rectal_examination` varchar(255) NOT NULL,
  `ascite` tinyint(1) NOT NULL,
  `anemia` tinyint(1) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` datetime NOT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


