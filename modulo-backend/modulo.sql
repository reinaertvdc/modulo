-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Genereertijd: 07 apr 2016 om 09:24
-- Serverversie: 5.5.46-0ubuntu0.14.04.2
-- PHP-versie: 5.6.14-1+deb.sury.org~trusty+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `modulo`
--
CREATE DATABASE IF NOT EXISTS `modulo` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `modulo`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `certificate`
--

DROP TABLE IF EXISTS `certificate`;
CREATE TABLE IF NOT EXISTS `certificate` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `classes`
--

DROP TABLE IF EXISTS `classes`;
CREATE TABLE IF NOT EXISTS `classes` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` char(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `FKclasses174595` (`teacher_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Gegevens worden uitgevoerd voor tabel `classes`
--

INSERT INTO `classes` (`id`, `teacher_id`, `name`, `type`) VALUES
  (1, 1, 'Metselaar 1', 'BGV'),
  (2, 1, 'Metselaar 2', 'BGV'),
  (3, 1, 'Metselaar 3', 'BGV');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `class_certificate`
--

DROP TABLE IF EXISTS `class_certificate`;
CREATE TABLE IF NOT EXISTS `class_certificate` (
  `class_id` int(10) NOT NULL,
  `certificate_id` int(10) NOT NULL,
  KEY `FKclass_cert633607` (`class_id`),
  KEY `FKclass_cert642406` (`certificate_id`),
  KEY `FKclass_cert143959` (`certificate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `class_topics`
--

DROP TABLE IF EXISTS `class_topics`;
CREATE TABLE IF NOT EXISTS `class_topics` (
  `course_topic_id` int(10) NOT NULL,
  `class_id` int(10) NOT NULL,
  PRIMARY KEY (`course_topic_id`,`class_id`),
  KEY `FKclass_topi484843` (`class_id`),
  KEY `FKclass_topi735797` (`course_topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `competence`
--

DROP TABLE IF EXISTS `competence`;
CREATE TABLE IF NOT EXISTS `competence` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category_id` int(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `custom_name` varchar(255) DEFAULT NULL,
  `custom_description` varchar(255) DEFAULT NULL,
  `required` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcompetence311647` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `course_topic`
--

DROP TABLE IF EXISTS `course_topic`;
CREATE TABLE IF NOT EXISTS `course_topic` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `grade`
--

DROP TABLE IF EXISTS `grade`;
CREATE TABLE IF NOT EXISTS `grade` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `objective`
--

DROP TABLE IF EXISTS `objective`;
CREATE TABLE IF NOT EXISTS `objective` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `grade_id` int(10) DEFAULT NULL,
  `course_topic_id` int(10) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `custom_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKobjective442663` (`grade_id`),
  KEY `FKobjective223514` (`course_topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `parent_info`
--

DROP TABLE IF EXISTS `parent_info`;
CREATE TABLE IF NOT EXISTS `parent_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Gegevens worden uitgevoerd voor tabel `parent_info`
--

INSERT INTO `parent_info` (`id`, `first_name`, `last_name`) VALUES
  (1, 'Ouder', 'De Ouder');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `student_bgv_score`
--

DROP TABLE IF EXISTS `student_bgv_score`;
CREATE TABLE IF NOT EXISTS `student_bgv_score` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `student_id` int(10) NOT NULL,
  `competence_id` int(10) NOT NULL,
  `score` char(1) DEFAULT NULL,
  `graded_date` date DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKstudent_bg641955` (`competence_id`),
  KEY `FKstudent_bg817805` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `student_info`
--

DROP TABLE IF EXISTS `student_info`;
CREATE TABLE IF NOT EXISTS `student_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `parent_id` int(10) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `birth_place` varchar(255) NOT NULL,
  `nationality` varchar(255) NOT NULL,
  `national_identification_number` char(11) NOT NULL,
  `street` varchar(255) NOT NULL,
  `house_number` varchar(255) NOT NULL,
  `postal_code` char(4) NOT NULL,
  `city` varchar(255) NOT NULL,
  `phone_parent` varchar(255) NOT NULL,
  `phone_cell` varchar(255) NOT NULL,
  `bank_account` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKstudent_in636242` (`user_id`),
  KEY `FKstudent_in145199` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `student_pav_score`
--

DROP TABLE IF EXISTS `student_pav_score`;
CREATE TABLE IF NOT EXISTS `student_pav_score` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `student_id` int(10) NOT NULL,
  `objective_id` int(10) NOT NULL,
  `score` char(1) NOT NULL,
  `graded_date` date DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKstudent_pa666259` (`student_id`),
  KEY `FKstudent_pa337820` (`objective_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `sub_certificate`
--

DROP TABLE IF EXISTS `sub_certificate`;
CREATE TABLE IF NOT EXISTS `sub_certificate` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `certificate_id` int(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `required` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsub_certif481276` (`certificate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `sub_certificate_categories`
--

DROP TABLE IF EXISTS `sub_certificate_categories`;
CREATE TABLE IF NOT EXISTS `sub_certificate_categories` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sub_certificate_id` int(10) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsub_certif807484` (`sub_certificate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` char(128) NOT NULL,
  `type` varchar(7) NOT NULL DEFAULT 'STUDENT',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Gegevens worden uitgevoerd voor tabel `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `type`) VALUES
  (1, 'test@test.com', 'password', 'TEACHER'),
  (2, 'student@uhasselt.be', 'wachtwoord', 'STUDENT'),
  (10, 'ouder@ouders.com', 'password', 'PARENT');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `user_class`
--

DROP TABLE IF EXISTS `user_class`;
CREATE TABLE IF NOT EXISTS `user_class` (
  `student_id` int(10) NOT NULL,
  `class_id` int(10) NOT NULL,
  PRIMARY KEY (`student_id`,`class_id`),
  KEY `FKuser_class117622` (`class_id`),
  KEY `FKuser_class290132` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Beperkingen voor gedumpte tabellen
--

--
-- Beperkingen voor tabel `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `FKclasses174595` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`);

--
-- Beperkingen voor tabel `class_certificate`
--
ALTER TABLE `class_certificate`
  ADD CONSTRAINT `FKclass_cert143959` FOREIGN KEY (`certificate_id`) REFERENCES `certificate` (`id`),
  ADD CONSTRAINT `FKclass_cert633607` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  ADD CONSTRAINT `FKclass_cert642406` FOREIGN KEY (`certificate_id`) REFERENCES `grade` (`id`);

--
-- Beperkingen voor tabel `class_topics`
--
ALTER TABLE `class_topics`
  ADD CONSTRAINT `FKclass_topi735797` FOREIGN KEY (`course_topic_id`) REFERENCES `course_topic` (`id`),
  ADD CONSTRAINT `FKclass_topi484843` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`);

--
-- Beperkingen voor tabel `competence`
--
ALTER TABLE `competence`
  ADD CONSTRAINT `FKcompetence311647` FOREIGN KEY (`category_id`) REFERENCES `sub_certificate_categories` (`id`);

--
-- Beperkingen voor tabel `objective`
--
ALTER TABLE `objective`
  ADD CONSTRAINT `FKobjective223514` FOREIGN KEY (`course_topic_id`) REFERENCES `course_topic` (`id`),
  ADD CONSTRAINT `FKobjective442663` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`);

--
-- Beperkingen voor tabel `student_bgv_score`
--
ALTER TABLE `student_bgv_score`
  ADD CONSTRAINT `FKstudent_bg817805` FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`),
  ADD CONSTRAINT `FKstudent_bg641955` FOREIGN KEY (`competence_id`) REFERENCES `competence` (`id`);

--
-- Beperkingen voor tabel `student_info`
--
ALTER TABLE `student_info`
  ADD CONSTRAINT `FKstudent_in145199` FOREIGN KEY (`parent_id`) REFERENCES `parent_info` (`id`),
  ADD CONSTRAINT `FKstudent_in636242` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Beperkingen voor tabel `student_pav_score`
--
ALTER TABLE `student_pav_score`
  ADD CONSTRAINT `FKstudent_pa337820` FOREIGN KEY (`objective_id`) REFERENCES `objective` (`id`),
  ADD CONSTRAINT `FKstudent_pa666259` FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`);

--
-- Beperkingen voor tabel `sub_certificate`
--
ALTER TABLE `sub_certificate`
  ADD CONSTRAINT `FKsub_certif481276` FOREIGN KEY (`certificate_id`) REFERENCES `certificate` (`id`);

--
-- Beperkingen voor tabel `sub_certificate_categories`
--
ALTER TABLE `sub_certificate_categories`
  ADD CONSTRAINT `FKsub_certif807484` FOREIGN KEY (`sub_certificate_id`) REFERENCES `sub_certificate` (`id`);

--
-- Beperkingen voor tabel `user_class`
--
ALTER TABLE `user_class`
  ADD CONSTRAINT `FKuser_class290132` FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`),
  ADD CONSTRAINT `FKuser_class117622` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;