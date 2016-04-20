CREATE DATABASE IF NOT EXISTS `modulo`;
USE `modulo`;

CREATE TABLE `users` (
  `id`       INT          NOT NULL AUTO_INCREMENT,
  `email`    VARCHAR(255) NOT NULL,
  `password` CHAR(128)    NOT NULL,
  `first_name`            VARCHAR(255) NOT NULL,
  `last_name`             VARCHAR(255) NOT NULL,
  `type`     VARCHAR(7)   NOT NULL DEFAULT 'STUDENT',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
);

INSERT INTO `users` VALUES (11, 'leerling1@school.be',
                            'pwd',
							'Hilde', 'Beerten',
                            'STUDENT');
INSERT INTO `users` VALUES (12, 'leerling2@school.be',
                            'pwd',
							'Katrien', 'Formesyn',
                            'STUDENT');
INSERT INTO `users` VALUES (13, 'leerling3@school.be',
                            'pwd',
							'Martine', 'Bonné',
                            'STUDENT');
INSERT INTO `users` VALUES (14, 'leerling4@school.be',
                            'pwd',
							'André', 'Coenen',
                            'STUDENT');
INSERT INTO `users` VALUES (15, 'leerling5@school.be',
                            'pwd',
							'Rembert', 'Henderix',
                            'STUDENT');
INSERT INTO `users` VALUES (21, 'leerkracht1@school.be',
                            'pwd',
							'Chana', 'Lauwers',
                            'TEACHER');
INSERT INTO `users` VALUES (22, 'leerkracht2@school.be',
                            'pwd',
							'Aaron', 'Charlier',
                            'TEACHER');
INSERT INTO `users` VALUES (31, 'ouder1@telenet.be',
                            'pwd',
							'Ellen', 'Copermans',
                            'PARENT');
INSERT INTO `users` VALUES (32, 'ouder2@belgacom.be',
                            'pwd',
							'Frederik', 'De Ridder',
                            'PARENT');
INSERT INTO `users` VALUES (41, 'admin@school.be',
                            'pwd',
							'Evelien', 'De Swert',
                            'ADMIN');

-- CREATE TABLE `parent_info` (
--   `id`         INT          NOT NULL AUTO_INCREMENT,
--   `user_id`    INT          NOT NULL,
--   `first_name` VARCHAR(255) NOT NULL,
--   `last_name`  VARCHAR(255) NOT NULL,
--   PRIMARY KEY (`id`),
--   FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)  ON DELETE CASCADE
-- );
--
-- INSERT INTO `parent_info` VALUES (1, 31, 'Jan', 'Ouder1');
-- INSERT INTO `parent_info` VALUES (2, 32, 'An', 'Ouder2');

CREATE TABLE `student_info` (
  `id`                             INT          NOT NULL AUTO_INCREMENT,
  `user_id`                        INT          NOT NULL,
  `parent_id`                      INT          ,
  `birthdate`                      DATE         NOT NULL,
  `birth_place`                    VARCHAR(255) NOT NULL,
  `nationality`                    VARCHAR(255) NOT NULL,
  `national_identification_number` CHAR(11)     NOT NULL,
  `street`                         VARCHAR(255) NOT NULL,
  `house_number`                   VARCHAR(255) NOT NULL,
  `postal_code`                    CHAR(4)      NOT NULL,
  `city`                           VARCHAR(255) NOT NULL,
  `phone_parent`                   VARCHAR(255) NOT NULL,
  `phone_cell`                     VARCHAR(255) NOT NULL,
  `bank_account`                   VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)  ON DELETE CASCADE,
  FOREIGN KEY (`parent_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

INSERT INTO `student_info` VALUES
  (1, 11, 32, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000',
   'Hasselt', '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (2, 12, 31, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000', 'Hasselt',
   '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (3, 13, 31, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000', 'Hasselt',
   '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (4, 14, 32, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000',
   'Hasselt', '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (5, 15, 31, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000', 'Hasselt',
   '012857496', '085479621', 'BE67-500-555-9685');

CREATE TABLE `classes` (
  `id`         INT          NOT NULL AUTO_INCREMENT,
  `teacher_id` INT,
  `name`       VARCHAR(255) NOT NULL,
  `type`       CHAR(3)      NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

INSERT INTO `classes` VALUES (1, 21, 'Metselaar 1', 'BGV');
INSERT INTO `classes` VALUES (2, 21, 'Metselaar 2', 'BGV');
INSERT INTO `classes` VALUES (3, 22, 'Elektricien 1', 'BGV');
INSERT INTO `classes` VALUES (4, 22, 'Elektriciten 2', 'BGV');
INSERT INTO `classes` VALUES (5, 22, 'PAV Klas 1', 'PAV');
INSERT INTO `classes` VALUES (6, 22, 'PAV Klas 2', 'PAV');


CREATE TABLE `certificates` (
  `id`      INT          NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `certificates` VALUES (1, 'Metselaar', '1');
INSERT INTO `certificates` VALUES (2, 'Elektricien', '1');
INSERT INTO `certificates` VALUES (3, 'Loodgieter', '0');

CREATE TABLE `sub_certificates` (
  `id`                 INT          NOT NULL AUTO_INCREMENT,
  `certificate_id`     INT          NOT NULL,
  `name`               VARCHAR(255) NOT NULL,
  `custom_name`        VARCHAR(255),
  `enabled`            TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`certificate_id`) REFERENCES `certificates` (`id`) ON DELETE CASCADE
);

INSERT INTO `sub_certificates` VALUES (1, 1, 'Bekisting',  NULL, 1);
INSERT INTO `sub_certificates` VALUES (2, 1, 'Fundering', NULL, 1);
INSERT INTO `sub_certificates` VALUES (3, 1, 'Cement',  NULL, 1);
INSERT INTO `sub_certificates` VALUES (4, 2, 'Bekabeling',  NULL, 1);
INSERT INTO `sub_certificates` VALUES (5, 2, 'Isolatie',  NULL, 1);
INSERT INTO `sub_certificates` VALUES (6, 2, 'Belichting', NULL, 0);

CREATE TABLE `sub_certificate_categories` (
  `id`                 INT          NOT NULL AUTO_INCREMENT,
  `sub_certificate_id` INT          NOT NULL,
  `name`               VARCHAR(255) NOT NULL,
  `custom_name`        VARCHAR(255),
  `enabled`            TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sub_certificate_id`) REFERENCES `sub_certificates` (`id`) ON DELETE CASCADE
);

INSERT INTO `sub_certificate_categories` VALUES (1, 1, 'Afwerking', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (2, 1, 'Ordelijk', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (3, 2, 'Afwerking', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (4, 2, 'Ordelijk', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (5, 3, 'Afwerking', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (6, 3, 'Ordelijk', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (7, 4, 'Afwerking', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (8, 4, 'Ordelijk', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (9, 5, 'Afwerking', NULL, 1);
INSERT INTO `sub_certificate_categories` VALUES (10, 5, 'Ordelijk', NULL, 1);

CREATE TABLE `grades` (
  `id`   INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255),
  PRIMARY KEY (`id`)
);

INSERT INTO `grades` VALUES (1, 'Graad 1');
INSERT INTO `grades` VALUES (2, 'Graad 2');

CREATE TABLE `student_class` (
  `student_info_id` INT NOT NULL,
  `class_id`        INT NOT NULL,
  PRIMARY KEY (`student_info_id`, `class_id`),
  FOREIGN KEY (`student_info_id`) REFERENCES `student_info` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE CASCADE
);

INSERT INTO `student_class` VALUES (1, 1);
INSERT INTO `student_class` VALUES (2, 1);
INSERT INTO `student_class` VALUES (3, 1);
INSERT INTO `student_class` VALUES (4, 2);
INSERT INTO `student_class` VALUES (5, 2);

CREATE TABLE `grade_class` (
  `grade_id` INT NOT NULL,
  `class_id`        INT NOT NULL,
  PRIMARY KEY (`grade_id`, `class_id`),
  FOREIGN KEY (`grade_id`) REFERENCES `grades` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE CASCADE
);

INSERT INTO `grade_class` VALUES (1,5);
INSERT INTO `grade_class` VALUES (2,6);

CREATE TABLE `class_certificate` (
  `class_id`       INT NOT NULL,
  `certificate_id` INT NOT NULL,
  PRIMARY KEY (`class_id`, `certificate_id`),
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`certificate_id`) REFERENCES `certificates` (`id`) ON DELETE CASCADE
);

INSERT INTO `class_certificate` VALUES (1, 1);
INSERT INTO `class_certificate` VALUES (2, 1);
INSERT INTO `class_certificate` VALUES (3, 2);
INSERT INTO `class_certificate` VALUES (4, 2);

CREATE TABLE `competences` (
  `id`                          INT          NOT NULL AUTO_INCREMENT,
  `sub_certificate_category_id` INT          NOT NULL,
  `name`                        VARCHAR(255) NOT NULL,
  `custom_name`                 VARCHAR(255),
  `enabled`                     TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sub_certificate_category_id`) REFERENCES `sub_certificate_categories` (`id`) ON DELETE CASCADE
);

INSERT INTO `competences` VALUES (1, 2, 'Gereedschap opruimen', NULL, 1);
INSERT INTO `competences` VALUES (2, 4, 'Gereedschap opruimen', NULL, 1);
INSERT INTO `competences` VALUES (3, 6, 'Gereedschap opruimen', NULL, 1);
INSERT INTO `competences` VALUES (4, 8, 'Gereedschap opruimen', NULL, 1);
INSERT INTO `competences` VALUES (5, 10, 'Gereedschap opruimen', NULL, 1);
INSERT INTO `competences` VALUES (6, 1, 'Afwerk Competentie', NULL, 1);
INSERT INTO `competences` VALUES (7, 3, 'Afwerk Competentie', NULL, 1);
INSERT INTO `competences` VALUES (8, 5, 'Afwerk Competentie', NULL, 1);
INSERT INTO `competences` VALUES (9, 7, 'Afwerk Competentie', NULL, 1);
INSERT INTO `competences` VALUES (10, 9, 'Afwerk Competentie', NULL, 1);

CREATE TABLE `course_topics` (
  `id`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `course_topics` VALUES (1, 'Vakthema 1');
INSERT INTO `course_topics` VALUES (2, 'Vakthema 2');

CREATE TABLE `objectives` (
  `id`              INT          NOT NULL AUTO_INCREMENT,
  `grade_id`        INT          NOT NULL,
  `course_topic_id` INT,
  `name`            VARCHAR(255) NOT NULL,
  `custom_name`     VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`grade_id`) REFERENCES `grades` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`course_topic_id`) REFERENCES `course_topics` (`id`) ON DELETE CASCADE
);

INSERT INTO `objectives` VALUES (1, 1, 1, 'Kent Vakthema 1', NULL);
INSERT INTO `objectives` VALUES (2, 2, 1, 'Kent Vakthema 2', NULL);

CREATE TABLE `student_bgv_score` (
  `id`            INT     NOT NULL AUTO_INCREMENT,
  `student_id`     INT     NOT NULL,
  `competence_id` INT     NOT NULL,
  `score`         CHAR(1) NOT NULL,
  `graded_date`    DATE    NOT NULL,
  `remarks`       VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`competence_id`) REFERENCES `competences` (`id`) ON DELETE CASCADE
);

INSERT INTO `student_bgv_score` VALUES (1, 1, 1, 'V', '2016-04-08', 'Remarks test');

CREATE TABLE `student_pav_score` (
  `id`           INT     NOT NULL AUTO_INCREMENT,
  `student_id`    INT     NOT NULL,
  `objective_id` INT     NOT NULL,
  `score`        CHAR(1) NOT NULL,
  `graded_date`   DATE    NOT NULL,
  `remarks`      VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`objective_id`) REFERENCES `objectives` (`id`) ON DELETE CASCADE
);

INSERT INTO `student_pav_score` VALUES (1, 1, 1, 'V', '2016-04-08', 'Remarks test');

CREATE TABLE `class_topics` (
  `course_topic_id` INT NOT NULL,
  `class_id`        INT NOT NULL,
  PRIMARY KEY (`course_topic_id`, `class_id`),
  FOREIGN KEY (`course_topic_id`) REFERENCES `course_topics` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`) ON DELETE CASCADE
);

INSERT INTO `class_topics` VALUES (1, 1);
