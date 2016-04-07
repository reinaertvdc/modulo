-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Genereertijd: 05 apr 2016 om 14:10
-- Serverversie: 5.5.46-0ubuntu0.14.04.2
-- PHP-versie: 5.6.14-1+deb.sury.org~trusty+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `modulo`
--
CREATE DATABASE IF NOT EXISTS `modulo`
  DEFAULT CHARACTER SET latin1
  COLLATE latin1_swedish_ci;
USE `modulo`;

-- --------------------------------------------------------

CREATE TABLE users (
  id       INT(10)                      NOT NULL AUTO_INCREMENT,
  email    VARCHAR(255)                 NOT NULL UNIQUE,
  password CHAR(128)                    NOT NULL,
  type     VARCHAR(7) DEFAULT 'STUDENT' NOT NULL,
  PRIMARY KEY (id)
) ;
CREATE TABLE classes (
  id         INT(10)      NOT NULL AUTO_INCREMENT,
  teacher_id INT(10)      NOT NULL,
  name       VARCHAR(255) NOT NULL UNIQUE,
  type       CHAR(3),
  PRIMARY KEY (id)
) ;
CREATE TABLE user_class (
  student_id INT(10) NOT NULL,
  class_id   INT(10) NOT NULL,
  PRIMARY KEY (student_id, class_id)
) ;
CREATE TABLE certificate (
  id   INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
) ;
CREATE TABLE sub_certificate (
  id             INT(10)      NOT NULL AUTO_INCREMENT,
  certificate_id INT(10)      NOT NULL,
  name           VARCHAR(255) NOT NULL,
  description    VARCHAR(255) NOT NULL,
  required       TINYINT      NOT NULL,
  PRIMARY KEY (id)
) ;
CREATE TABLE competence (
  id                 INT(10)      NOT NULL AUTO_INCREMENT,
  category_id        INT(10)      NOT NULL,
  name               VARCHAR(255) NOT NULL,
  description        VARCHAR(255) NOT NULL,
  custom_name        VARCHAR(255),
  custom_description VARCHAR(255),
  required           TINYINT      NOT NULL,
  PRIMARY KEY (id)
) ;
CREATE TABLE student_bgv_score (
  id            INT(10) NOT NULL AUTO_INCREMENT,
  student_id    INT(10) NOT NULL,
  competence_id INT(10) NOT NULL,
  score         CHAR(1),
  graded_date   DATE,
  remarks       VARCHAR(255),
  PRIMARY KEY (id)
) ;
CREATE TABLE student_info (
  id                         INT(10)      NOT NULL AUTO_INCREMENT,
  user_id                    INT(10)      NOT NULL,
  parent_id                  INT(10)      NOT NULL,
  first_name                 VARCHAR(255) NOT NULL,
  last_name                  VARCHAR(255) NOT NULL,
  birthdate                 DATE         NOT NULL,
  birth_place                VARCHAR(255) NOT NULL,
  nationality                VARCHAR(255) NOT NULL,
  national_identification_number CHAR(11)     NOT NULL,
  street                     VARCHAR(255) NOT NULL,
  house_number                     VARCHAR(255) NOT NULL,
  postal_code                CHAR(4)      NOT NULL,
  city                       VARCHAR(255) NOT NULL,
  phone_parent             VARCHAR(255) NOT NULL,
  phone_cell                 VARCHAR(255) NOT NULL,
  bank_account               VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ;
CREATE TABLE sub_certificate_categories (
  id                 INT(10) NOT NULL AUTO_INCREMENT,
  sub_certificate_id INT(10) NOT NULL,
  name               VARCHAR(255),
  description        VARCHAR(255),
  PRIMARY KEY (id)
) ;
CREATE TABLE grade (
  id   INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
) ;
CREATE TABLE objective (
  id              INT(10)      NOT NULL AUTO_INCREMENT,
  grade_id        INT(10),
  course_topic_id INT(10),
  name            VARCHAR(255) NOT NULL,
  custom_name     VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ;
CREATE TABLE student_pav_score (
  id           INT(10) NOT NULL AUTO_INCREMENT,
  student_id   INT(10) NOT NULL,
  objective_id INT(10) NOT NULL,
  score        CHAR(1) NOT NULL,
  graded_date  DATE,
  remarks      VARCHAR(255),
  PRIMARY KEY (id)
) ;
CREATE TABLE course_topic (
  id   INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
) ;
CREATE TABLE class_topics (
  course_topic_id INT(10) NOT NULL,
  class_id        INT(10) NOT NULL,
  PRIMARY KEY (course_topic_id, class_id)
) ;
CREATE TABLE class_certificate (
  class_id       INT(10) NOT NULL,
  certificate_id INT(10) NOT NULL
) ;
ALTER TABLE classes
  ADD INDEX FKclasses174595 (teacher_id),
  ADD CONSTRAINT FKclasses174595 FOREIGN KEY (teacher_id) REFERENCES users (id);
ALTER TABLE user_class
  ADD INDEX FKuser_class117622 (class_id),
  ADD CONSTRAINT FKuser_class117622 FOREIGN KEY (class_id) REFERENCES classes (id);
ALTER TABLE sub_certificate
  ADD INDEX FKsub_certif481276 (certificate_id),
  ADD CONSTRAINT FKsub_certif481276 FOREIGN KEY (certificate_id) REFERENCES certificate (id);
ALTER TABLE student_bgv_score
  ADD INDEX FKstudent_bg641955 (competence_id),
  ADD CONSTRAINT FKstudent_bg641955 FOREIGN KEY (competence_id) REFERENCES competence (id);
ALTER TABLE student_info
  ADD INDEX FKstudent_in636242 (user_id),
  ADD CONSTRAINT FKstudent_in636242 FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE sub_certificate_categories
  ADD INDEX FKsub_certif807484 (sub_certificate_id),
  ADD CONSTRAINT FKsub_certif807484 FOREIGN KEY (sub_certificate_id) REFERENCES sub_certificate (id);
ALTER TABLE competence
  ADD INDEX FKcompetence311647 (category_id),
  ADD CONSTRAINT FKcompetence311647 FOREIGN KEY (category_id) REFERENCES sub_certificate_categories (id);
ALTER TABLE user_class
  ADD INDEX FKuser_class290132 (student_id),
  ADD CONSTRAINT FKuser_class290132 FOREIGN KEY (student_id) REFERENCES student_info (id);
ALTER TABLE student_bgv_score
  ADD INDEX FKstudent_bg817805 (student_id),
  ADD CONSTRAINT FKstudent_bg817805 FOREIGN KEY (student_id) REFERENCES student_info (id);
ALTER TABLE student_info
  ADD INDEX FKstudent_in145199 (parent_id),
  ADD CONSTRAINT FKstudent_in145199 FOREIGN KEY (parent_id) REFERENCES users (id);
ALTER TABLE objective
  ADD INDEX FKobjective442663 (grade_id),
  ADD CONSTRAINT FKobjective442663 FOREIGN KEY (grade_id) REFERENCES grade (id);
ALTER TABLE student_pav_score
  ADD INDEX FKstudent_pa666259 (student_id),
  ADD CONSTRAINT FKstudent_pa666259 FOREIGN KEY (student_id) REFERENCES student_info (id);
ALTER TABLE student_pav_score
  ADD INDEX FKstudent_pa337820 (objective_id),
  ADD CONSTRAINT FKstudent_pa337820 FOREIGN KEY (objective_id) REFERENCES objective (id);
ALTER TABLE objective
  ADD INDEX FKobjective223514 (course_topic_id),
  ADD CONSTRAINT FKobjective223514 FOREIGN KEY (course_topic_id) REFERENCES course_topic (id);
ALTER TABLE class_topics
  ADD INDEX FKclass_topi484843 (class_id),
  ADD CONSTRAINT FKclass_topi484843 FOREIGN KEY (class_id) REFERENCES classes (id);
ALTER TABLE class_topics
  ADD INDEX FKclass_topi735797 (course_topic_id),
  ADD CONSTRAINT FKclass_topi735797 FOREIGN KEY (course_topic_id) REFERENCES course_topic (id);
ALTER TABLE class_certificate
  ADD INDEX FKclass_cert633607 (class_id),
  ADD CONSTRAINT FKclass_cert633607 FOREIGN KEY (class_id) REFERENCES classes (id);
ALTER TABLE class_certificate
  ADD INDEX FKclass_cert642406 (certificate_id),
  ADD CONSTRAINT FKclass_cert642406 FOREIGN KEY (certificate_id) REFERENCES grade (id);
ALTER TABLE class_certificate
  ADD INDEX FKclass_cert143959 (certificate_id),
  ADD CONSTRAINT FKclass_cert143959 FOREIGN KEY (certificate_id) REFERENCES certificate (id);
