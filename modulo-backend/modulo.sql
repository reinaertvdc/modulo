SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `certificates`;
DROP TABLE IF EXISTS `classes`;
DROP TABLE IF EXISTS `class_certificate`;
DROP TABLE IF EXISTS `class_topics`;
DROP TABLE IF EXISTS `competences`;
DROP TABLE IF EXISTS `course_topics`;
DROP TABLE IF EXISTS `grades`;
DROP TABLE IF EXISTS `grade_class`;
DROP TABLE IF EXISTS `objectives`;
DROP TABLE IF EXISTS `student_bgv_score`;
DROP TABLE IF EXISTS `student_class`;
DROP TABLE IF EXISTS `student_info`;
DROP TABLE IF EXISTS `student_pav_score`;
DROP TABLE IF EXISTS `sub_certificates`;
DROP TABLE IF EXISTS `sub_certificate_categories`;
DROP TABLE IF EXISTS `users`;
SET FOREIGN_KEY_CHECKS=1;



CREATE DATABASE IF NOT EXISTS `modulo`;
USE `modulo`;

CREATE TABLE `users` (
  `id`         INT          NOT NULL AUTO_INCREMENT,
  `email`      VARCHAR(255) NOT NULL,
  `password`   CHAR(128)    NOT NULL,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name`  VARCHAR(255) NOT NULL,
  `type`       VARCHAR(7)   NOT NULL DEFAULT 'STUDENT',
  `enabled`    TINYINT      NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
);

INSERT INTO `users` VALUES (11, 'leerling1@school.be',
                            'pwd',
                            'Hilde', 'Beerten',
                            'STUDENT', 1);
INSERT INTO `users` VALUES (12, 'leerling2@school.be',
                            'pwd',
                            'Katrien', 'Formesyn',
                            'STUDENT', 1);
INSERT INTO `users` VALUES (13, 'leerling3@school.be',
                            'pwd',
                            'Martine', 'Bonné',
                            'STUDENT', 1);
INSERT INTO `users` VALUES (14, 'leerling4@school.be',
                            'pwd',
                            'André', 'Coenen',
                            'STUDENT', 1);
INSERT INTO `users` VALUES (15, 'leerling5@school.be',
                            'pwd',
                            'Rembert', 'Henderix',
                            'STUDENT', 1);
INSERT INTO `users` VALUES (21, 'leerkracht1@school.be',
                            'pwd',
                            'Chana', 'Lauwers',
                            'TEACHER', 1);
INSERT INTO `users` VALUES (22, 'leerkracht2@school.be',
                            'pwd',
                            'Aaron', 'Charlier',
                            'TEACHER', 1);
INSERT INTO `users` VALUES (31, 'ouder1@telenet.be',
                            'pwd',
                            'Ellen', 'Copermans',
                            'PARENT', 1);
INSERT INTO `users` VALUES (32, 'ouder2@belgacom.be',
                            'pwd',
                            'Frederik', 'De Ridder',
                            'PARENT', 1);
INSERT INTO `users` VALUES (41, 'admin@school.be',
                            'pwd',
                            'Evelien', 'De Swert',
                            'ADMIN', 1);

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


CREATE TABLE `classes` (
  `id`         INT          NOT NULL AUTO_INCREMENT,
  `teacher_id` INT,
  `name`       VARCHAR(255) NOT NULL,
  `type`       CHAR(3)      NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `classes` VALUES (1, 21, 'Metselaar 1', 'BGV');
INSERT INTO `classes` VALUES (2, 21, 'Metselaar 2', 'BGV');
INSERT INTO `classes` VALUES (3, 21, 'Elektricien 1', 'BGV');
INSERT INTO `classes` VALUES (4, 22, 'Elektriciten 2', 'BGV');
INSERT INTO `classes` VALUES (5, 22, 'PAV Klas 1', 'PAV');
INSERT INTO `classes` VALUES (6, 22, 'PAV Klas 2', 'PAV');


CREATE TABLE `certificates` (
  `id`      INT          NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(255) NOT NULL,
  `enabled` TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `sub_certificates` (
  `id`             INT          NOT NULL AUTO_INCREMENT,
  `certificate_id` INT          NOT NULL,
  `name`           VARCHAR(255) NOT NULL,
  `custom_name`    VARCHAR(255),
  `enabled`        TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`certificate_id`) REFERENCES `certificates` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `certificates` VALUES (1, 'Buitenschrijnwerker', '1');
    INSERT INTO `sub_certificates` VALUES (1, 1, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (2, 1, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (3, 1, 'Plaatsing buitenschrijnwerk hout', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (4, 1, 'Werkplaatsbuitenschrijnwerk', NULL, '1');
INSERT INTO `certificates` VALUES (2, 'Werkplaatsbinnenschrijnwerker hout', '0');
    INSERT INTO `sub_certificates` VALUES (5, 2, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (6, 2, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (7, 2, 'Werkplaatsbinnenschrijnwerk', NULL, '1');
INSERT INTO `certificates` VALUES (3, 'Rioollegger', '0');
    INSERT INTO `sub_certificates` VALUES (8, 3, 'Plaatsing openbare riolering', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (9, 3, 'Sleuven en bouwputten', NULL, '1');
INSERT INTO `certificates` VALUES (4, 'Medewerker snackbar-taverne', '0');
    INSERT INTO `sub_certificates` VALUES (10, 4, 'Snackbar-taverne', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (11, 4, 'Sneldienstrestauratie', NULL, '1');
INSERT INTO `certificates` VALUES (5, 'Tegelzetter', '1');
    INSERT INTO `sub_certificates` VALUES (12, 5, 'Basistechnieken tegelzetten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (13, 5, 'Plaatsing vloertegels', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (14, 5, 'Plaatsing wandtegels', NULL, '1');
INSERT INTO `certificates` VALUES (6, 'Plaatser boven- en ondergrondse kabels en leidingen', '0');
    INSERT INTO `sub_certificates` VALUES (15, 6, 'Bovengrondse kabels en leidingen elektriciteit', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (16, 6, 'Ondergrondse kabels en leidingen elektriciteit', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (17, 6, 'Openbare verlichting', NULL, '1');
INSERT INTO `certificates` VALUES (7, 'Dekvloerlegger', '0');
    INSERT INTO `sub_certificates` VALUES (18, 7, 'Gietvloeren', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (19, 7, 'Hechtende dekvloer', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (20, 7, 'Niet-hechtende dekvloer', NULL, '1');
INSERT INTO `certificates` VALUES (8, 'Onderhoudsmecanicien personenwagens en lichte bedrijfsvoertuigen ', '0');
    INSERT INTO `sub_certificates` VALUES (21, 8, 'Periodiek onderhoud personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (22, 8, 'Vervanging en herstelling onderdelen personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (23, 8, 'Voorbereiding technische keuring personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
INSERT INTO `certificates` VALUES (9, 'Operator in de houtzagerij', '0');
    INSERT INTO `sub_certificates` VALUES (24, 9, 'Aan- en afvoer grondstoffen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (25, 9, 'Bediening en instelling zaagstraat', NULL, '1');
INSERT INTO `certificates` VALUES (10, 'Lasser beklede elektrode', '1');
    INSERT INTO `sub_certificates` VALUES (26, 10, 'Beklede elektrode hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (27, 10, 'Beklede elektrode pijplas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (28, 10, 'Beklede elektrode plaatlas', NULL, '1');
INSERT INTO `certificates` VALUES (11, 'Uitsnijder-uitbener', '1');
    INSERT INTO `sub_certificates` VALUES (29, 11, 'Industriële vleesafwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (30, 11, 'Ontvangst vlees en karkassen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (31, 11, 'Rund versnijden en uitbenen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (32, 11, 'Schaap versnijden en uitbenen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (33, 11, 'Varken versnijden en uitbenen', NULL, '1');
INSERT INTO `certificates` VALUES (12, 'Uitsnijder-uitbener rund', '0');
    INSERT INTO `sub_certificates` VALUES (34, 12, 'Industriële vleesafwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (35, 12, 'Ontvangst vlees en karkassen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (36, 12, 'Rund versnijden en uitbenen', NULL, '1');
INSERT INTO `certificates` VALUES (13, 'Technicus elektrische zwembaduitrustingen', '0');
    INSERT INTO `sub_certificates` VALUES (37, 13, 'Inbedrijfstelling zwembaduitrustingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (38, 13, 'Installatiewerken zwembaduitrustingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (39, 13, 'Voorbereidende werkzaamheden zwembaduitrustingen', NULL, '1');
INSERT INTO `certificates` VALUES (14, 'Bouwplaatsmachinist', '0');
    INSERT INTO `sub_certificates` VALUES (40, 14, 'Grondverzet bouwplaatsmachine', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (41, 14, 'Hijswerk bouwplaatsmachine', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (42, 14, 'Vervoer bouwplaatsmachine', NULL, '1');
INSERT INTO `certificates` VALUES (15, 'Pijplasser', '1');
    INSERT INTO `sub_certificates` VALUES (43, 15, 'Beklede elektrode hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (44, 15, 'Beklede elektrode pijplas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (45, 15, 'Beklede elektrode plaatlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (46, 15, 'MIG/MAG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (47, 15, 'MIG/MAG pijplas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (48, 15, 'MIG/MAG plaatlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (49, 15, 'TIG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (50, 15, 'TIG pijplas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (51, 15, 'TIG plaatlas', NULL, '1');
INSERT INTO `certificates` VALUES (16, 'Grootkeukenhulpkok', '1');
    INSERT INTO `sub_certificates` VALUES (52, 16, 'Grootkeukenbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (53, 16, 'Grootkeukenprocessen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (54, 16, 'Grootkeukentechnieken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (55, 16, 'Initiatie keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (56, 16, 'Verdeelsystemen grootkeuken', NULL, '1');
INSERT INTO `certificates` VALUES (17, 'Grootkeukenkok', '1');
    INSERT INTO `sub_certificates` VALUES (57, 17, 'Dieetkeuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (58, 17, 'Grootkeukenbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (59, 17, 'Grootkeukenorganisatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (60, 17, 'Grootkeukenprocessen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (61, 17, 'Grootkeukentechnieken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (62, 17, 'Initiatie keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (63, 17, 'Verdeelsystemen grootkeuken', NULL, '1');
INSERT INTO `certificates` VALUES (18, 'Voorbewerker carrosserie', '0');
    INSERT INTO `sub_certificates` VALUES (64, 18, 'Bewerking ondergrond carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (65, 18, 'Grondlagen carrosserie', NULL, '1');
INSERT INTO `certificates` VALUES (19, 'Plaatser soepele vloerbekleding', '1');
    INSERT INTO `sub_certificates` VALUES (66, 19, 'Basistechnieken vloerbekleding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (67, 19, 'Plaatsing soepele vloerbekleding', NULL, '1');
INSERT INTO `certificates` VALUES (20, 'IJsbereider', '0');
    INSERT INTO `sub_certificates` VALUES (68, 20, 'Beslagen banketproducten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (69, 20, 'IJs en ijstaarten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (70, 20, 'Initiatie ijs', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (71, 20, 'Marsepein, suikerwerk en chocolade', NULL, '1');
INSERT INTO `certificates` VALUES (21, 'Industrieel vleesbewerker', '0');
    INSERT INTO `sub_certificates` VALUES (72, 21, 'Industriële vleesafwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (73, 21, 'Ontvangst vlees en karkassen', NULL, '1');
INSERT INTO `certificates` VALUES (22, 'Werkplaatsschrijnwerker kunststoffen', '0');
    INSERT INTO `sub_certificates` VALUES (74, 22, 'Assemblage kunststofhalffabricaten ramen en deuren', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (75, 22, 'Bewerking kunststofhalffabricaten ramen en deuren', NULL, '1');
INSERT INTO `certificates` VALUES (23, 'Werfbediener', '1');
    INSERT INTO `sub_certificates` VALUES (76, 23, 'Basistechnieken metselwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (77, 23, 'Funderingen op staal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (78, 23, 'Grondwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (79, 23, 'Opritten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (80, 23, 'Riolerings- en afwateringsstelsels', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (81, 23, 'Werfbediening ruwbouw', NULL, '1');
INSERT INTO `certificates` VALUES (24, 'Verhuizer-inpakker', '1');
    INSERT INTO `sub_certificates` VALUES (82, 24, 'Installatie en bediening verhuislift', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (83, 24, 'Laden en lossen goederen verhuis', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (84, 24, 'Verhuizing niet-standaard goederen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (85, 24, 'Voorbereiding verhuis', NULL, '1');
INSERT INTO `certificates` VALUES (25, 'Plaatser binnenschrijnwerk', '0');
    INSERT INTO `sub_certificates` VALUES (86, 25, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (87, 25, 'Plaatsing binnenschrijnwerk', NULL, '1');
INSERT INTO `certificates` VALUES (26, 'Kok', '1');
    INSERT INTO `sub_certificates` VALUES (88, 26, 'Creatieve keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (89, 26, 'Initiatie keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (90, 26, 'Keukenprocessen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (91, 26, 'Keukentechnieken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (92, 26, 'Keukenorganisatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (93, 26, 'Koude keukenbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (94, 26, 'Warme keukenbereidingen', NULL, '1');
INSERT INTO `certificates` VALUES (27, 'Onderhouds- en diagnosetechnicus zware bedrijfsvoertuigen', '0');
    INSERT INTO `sub_certificates` VALUES (95, 27, 'Diagnose complexe storingen zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (96, 27, 'Herstelling elektrische defecten zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (97, 27, 'In- of opbouw geavanceerde installaties zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (98, 27, 'In- of opbouw installaties zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (99, 27, 'Periodiek onderhoud zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (100, 27, 'Vervanging en herstelling onderdelen zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (101, 27, 'Voorbereiding technische keuring zware bedrijfsvoertuigen', NULL, '1');
INSERT INTO `certificates` VALUES (28, 'Plaatser parket', '0');
    INSERT INTO `sub_certificates` VALUES (102, 28, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (103, 28, 'Plaatsing parket', NULL, '1');
INSERT INTO `certificates` VALUES (29, 'Assistent productieoperator hout', '0');
    INSERT INTO `sub_certificates` VALUES (104, 29, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (105, 29, 'Bediening machinestraat of installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (106, 29, 'Initiatie houtbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (30, 'Dakdekker rieten daken', '0');
    INSERT INTO `sub_certificates` VALUES (107, 30, 'Plaatsing rieten daken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (108, 30, 'Voorbereiding dakwerken', NULL, '1');
INSERT INTO `certificates` VALUES (31, 'Assistent productieoperator voeding', '0');
    INSERT INTO `sub_certificates` VALUES (109, 31, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (110, 31, 'Bediening machinestraat of installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (111, 31, 'Voedingsindustrie', NULL, '1');
INSERT INTO `certificates` VALUES (32, 'Interieurbouwer', '0');
    INSERT INTO `sub_certificates` VALUES (112, 32, 'Interieurelementen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (113, 32, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (114, 32, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (115, 32, 'Plaatsing interieurelementen', NULL, '1');
INSERT INTO `certificates` VALUES (33, 'Stukadoor', '1');
    INSERT INTO `sub_certificates` VALUES (116, 33, 'Basistechnieken bepleistering', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (117, 33, 'Binnenbepleistering', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (118, 33, 'Buitenbepleistering', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (119, 33, 'Plaatsing gipsblokwanden', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (120, 33, 'Plaatsing gipskartonplaten', NULL, '1');
INSERT INTO `certificates` VALUES (34, 'Onderhoudsmecanicien zware bedrijfsvoertuigen', '0');
    INSERT INTO `sub_certificates` VALUES (121, 34, 'Periodiek onderhoud zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (122, 34, 'Vervanging en herstelling onderdelen zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (123, 34, 'Voorbereiding technische keuring zware bedrijfsvoertuigen', NULL, '1');
INSERT INTO `certificates` VALUES (35, 'IJzervlechter', '1');
    INSERT INTO `sub_certificates` VALUES (124, 35, 'Betonwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (125, 35, 'Funderingen op staal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (126, 35, 'Grondwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (127, 35, 'IJzervlechtwerk', NULL, '1');
INSERT INTO `certificates` VALUES (36, 'Administratief medewerker kmo', '0');
    INSERT INTO `sub_certificates` VALUES (128, 36, 'Aankoopadministratie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (129, 36, 'Administratief werk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (130, 36, 'Ondersteunende boekhoudkundige taken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (131, 36, 'Ondersteunende taken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (132, 36, 'Onthaal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (133, 36, 'Postverwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (134, 36, 'Telefonie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (135, 36, 'Verkoopadministratie', NULL, '1');
INSERT INTO `certificates` VALUES (37, 'Aanvuller', '0');
    INSERT INTO `sub_certificates` VALUES (136, 37, 'Aanvulwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (137, 37, 'Klantencontact', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (138, 37, 'Presentatiewerk', NULL, '1');
INSERT INTO `certificates` VALUES (38, 'Bordenbouwer', '0');
    INSERT INTO `sub_certificates` VALUES (139, 38, 'Bedrading en testing bord en kast elektriciteit', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (140, 38, 'Montage bord en kast elektriciteit', NULL, '1');
INSERT INTO `certificates` VALUES (39, 'Technicus domotica', '0');
    INSERT INTO `sub_certificates` VALUES (141, 39, 'Implementatie domoticasysteem', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (142, 39, 'Inbedrijfstelling residentiële installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (143, 39, 'Installatiewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (144, 39, 'Montagewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (145, 39, 'Ruwbouwwerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (146, 39, 'Verdeelbord elektriciteit residentieel', NULL, '1');
INSERT INTO `certificates` VALUES (40, 'Chocoladebewerker', '1');
    INSERT INTO `sub_certificates` VALUES (147, 40, 'Chocoladewerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (148, 40, 'Dessertkoekjes', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (149, 40, 'Marsepein, suikerwerk en chocolade', NULL, '1');
INSERT INTO `certificates` VALUES (41, 'Demonteur/monteur carrosserie', '0');
    INSERT INTO `sub_certificates` VALUES (150, 41, 'Demontage/montage carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (151, 41, 'Voorbereiding carrosseriewerken', NULL, '1');
INSERT INTO `certificates` VALUES (42, 'Industrieel verpakker', '1');
    INSERT INTO `sub_certificates` VALUES (152, 42, 'Industriële verpakkingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (153, 42, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (154, 42, 'Paletten en kratten', NULL, '1');
INSERT INTO `certificates` VALUES (43, 'Onderhouds- en diagnosetechnicus personenwagens en lichte bedrijfsvoertuigen', '0');
    INSERT INTO `sub_certificates` VALUES (155, 43, 'Diagnose complexe storingen personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (156, 43, 'Herstelling elektrische defecten personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (157, 43, 'In- of opbouw geavanceerde installaties personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (158, 43, 'In- of opbouw installaties personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (159, 43, 'Periodiek onderhoud personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (160, 43, 'Vervanging en herstelling onderdelen personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (161, 43, 'Voorbereiding technische keuring personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
INSERT INTO `certificates` VALUES (44, 'Werkplaatsbuitenschrijnwerker hout', '0');
    INSERT INTO `sub_certificates` VALUES (162, 44, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (163, 44, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (164, 44, 'Werkplaatsbuitenschrijnwerk', NULL, '1');
INSERT INTO `certificates` VALUES (45, 'Hulpkelner', '0');
    INSERT INTO `sub_certificates` VALUES (165, 45, 'Bediening', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (166, 45, 'Initiatie bar en bediening', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (167, 45, 'Zaal- en tafelschikking', NULL, '1');
INSERT INTO `certificates` VALUES (46, 'Dakdekker leien en pannen', '0');
    INSERT INTO `sub_certificates` VALUES (168, 46, 'Plaatsing leien', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (169, 46, 'Plaatsing pannen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (170, 46, 'Voorbereiding dakwerken', NULL, '1');
INSERT INTO `certificates` VALUES (47, 'Fietsmecanicien', '0');
    INSERT INTO `sub_certificates` VALUES (171, 47, 'Onderhoud en herstelling fiets', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (172, 47, 'Opbouw en afstelling fiets', NULL, '1');
INSERT INTO `certificates` VALUES (48, 'Productiemedewerker voeding', '0');
    INSERT INTO `sub_certificates` VALUES (173, 48, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (174, 48, 'Voedingsindustrie', NULL, '1');
INSERT INTO `certificates` VALUES (49, 'Slager', '0');
    INSERT INTO `sub_certificates` VALUES (175, 49, 'Ontvangst en opslag vers vlees', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (176, 49, 'Toonbankverkoop', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (177, 49, 'Uitbenen en versnijden', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (178, 49, 'Vleesbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (179, 49, 'Winkelklaar maken vers vlees', NULL, '1');
INSERT INTO `certificates` VALUES (50, 'Administratief medewerker expeditie', '0');
    INSERT INTO `sub_certificates` VALUES (180, 50, 'Administratief werk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (181, 50, 'Klantendossier', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (182, 50, 'Ondersteunende taken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (183, 50, 'Onthaal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (184, 50, 'Postverwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (185, 50, 'Telefonie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (186, 50, 'Transportdocumenten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (187, 50, 'Transportorganisatie', NULL, '1');
INSERT INTO `certificates` VALUES (51, 'Plaatwerker carrosserie', '0');
    INSERT INTO `sub_certificates` VALUES (188, 51, 'Demontage/montage carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (189, 51, 'Plaatwerk carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (190, 51, 'Voorbereiding carrosseriewerken', NULL, '1');
INSERT INTO `certificates` VALUES (52, 'Basisoperator proceschemie', '0');
    INSERT INTO `sub_certificates` VALUES (191, 52, 'Aan-, door- en afvoer grondstoffen proceschemie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (192, 52, 'Controle en bediening procesinstallatie chemie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (193, 52, 'Onderhoudswerken installatie proceschemie', NULL, '1');
INSERT INTO `certificates` VALUES (53, 'Grootkeukenmedewerker', '0');
    INSERT INTO `sub_certificates` VALUES (194, 53, 'Grootkeukentechnieken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (195, 53, 'Initiatie keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (196, 53, 'Verdeelsystemen grootkeuken', NULL, '1');
INSERT INTO `certificates` VALUES (54, 'Industrieel isolatiewerker', '0');
    INSERT INTO `sub_certificates` VALUES (197, 54, 'Basistechnieken industriële isolatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (198, 54, 'Beplating industriële isolatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (199, 54, 'Montage beplating industriële isolatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (200, 54, 'Plaatsing industriële isolatie', NULL, '1');
INSERT INTO `certificates` VALUES (55, 'Lasser TIG', '0');
    INSERT INTO `sub_certificates` VALUES (201, 55, 'TIG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (202, 55, 'TIG pijplas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (203, 55, 'TIG plaatlas', NULL, '1');
INSERT INTO `certificates` VALUES (56, 'Banketbakker', '0');
    INSERT INTO `sub_certificates` VALUES (204, 56, 'Beslagen banketproducten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (205, 56, 'Crèmes, mousses en romen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (206, 56, 'Degen banketproducten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (207, 56, 'Harde- en zachteluxe', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (208, 56, 'Harde- en zachteluxespecialiteiten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (209, 56, 'Initiatie ijs', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (210, 56, 'Marsepein, suikerwerk en chocolade', NULL, '1');
INSERT INTO `certificates` VALUES (57, 'Bromfietsmecanicien', '0');
    INSERT INTO `sub_certificates` VALUES (211, 57, 'Montage en afstelling bromfiets', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (212, 57, 'Periodiek onderhoud bromfiets', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (213, 57, 'Vervanging en herstelling onderdelen bromfiets', NULL, '1');
INSERT INTO `certificates` VALUES (58, 'Dakafdichter', '0');
    INSERT INTO `sub_certificates` VALUES (214, 58, 'Plaatsing dakafdichting', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (215, 58, 'Voorbereiding dakwerken', NULL, '1');
INSERT INTO `certificates` VALUES (59, 'Productiemedewerker metaal', '0');
    INSERT INTO `sub_certificates` VALUES (216, 59, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (217, 59, 'Initiatie metaalbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (60, 'Torenkraanbestuurder', '0');
    INSERT INTO `sub_certificates` VALUES (218, 60, 'Hijswerk torenkraan', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (219, 60, 'Voorbereiding werking torenkraan', NULL, '1');
INSERT INTO `certificates` VALUES (61, 'Natuursteenbewerker', '0');
    INSERT INTO `sub_certificates` VALUES (220, 61, 'Basistechnieken natuursteenbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (221, 61, 'Marmerwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (222, 61, 'Plaatsing natuursteen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (223, 61, 'Steenhouwerswerk', NULL, '1');
INSERT INTO `certificates` VALUES (62, 'Werkplaatsschrijnwerker aluminium', '0');
    INSERT INTO `sub_certificates` VALUES (224, 62, 'Assemblage aluminiumhalffabricaten ramen en deuren', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (225, 62, 'Bewerking aluminiumhalffabricaten ramen en deuren', NULL, '1');
INSERT INTO `certificates` VALUES (63, 'Plaatser natuursteen', '0');
    INSERT INTO `sub_certificates` VALUES (226, 63, 'Basistechnieken natuursteenbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (227, 63, 'Plaatsing natuursteen', NULL, '1');
INSERT INTO `certificates` VALUES (64, 'Broodbakker', '0');
    INSERT INTO `sub_certificates` VALUES (228, 64, 'Broden', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (229, 64, 'Broodspecialiteiten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (230, 64, 'Harde- en zachteluxe', NULL, '1');
INSERT INTO `certificates` VALUES (65, 'Plaatser interieurelementen', '0');
    INSERT INTO `sub_certificates` VALUES (231, 65, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (232, 65, 'Plaatsing interieurelementen', NULL, '1');
INSERT INTO `certificates` VALUES (66, 'Uitsnijder-uitbener varken', '0');
    INSERT INTO `sub_certificates` VALUES (233, 66, 'Industriële vleesafwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (234, 66, 'Ontvangst vlees en karkassen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (235, 66, 'Varken versnijden en uitbenen', NULL, '1');
INSERT INTO `certificates` VALUES (67, 'Pijpfitter', '0');
    INSERT INTO `sub_certificates` VALUES (236, 67, 'Montage en demontage pijpleidingsysteem', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (237, 67, 'Vervaardiging pijpleidingonderdelen', NULL, '1');
INSERT INTO `certificates` VALUES (68, 'Behanger', '0');
    INSERT INTO `sub_certificates` VALUES (238, 68, 'Basistechnieken wandbekleding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (239, 68, 'Plaatsing wandbekleding', NULL, '1');
INSERT INTO `certificates` VALUES (69, 'Monteur centrale verwarming', '0');
    INSERT INTO `sub_certificates` VALUES (240, 69, 'Plaatsing en aansluiting toestellen centrale verwarming', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (241, 69, 'Plaatsing leidingen centrale verwarming', NULL, '1');
INSERT INTO `certificates` VALUES (70, 'Podiumtechnicus', '0');
    INSERT INTO `sub_certificates` VALUES (242, 70, 'Audiovisuele technieken podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (243, 70, 'Belichting podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (244, 70, 'Decormontage podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (245, 70, 'Geluidsversterking podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (246, 70, 'Licht- en geluidstechnieken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (247, 70, 'Opvolging productie podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (248, 70, 'Voorbereiding productie podiumkunsten', NULL, '1');
INSERT INTO `certificates` VALUES (71, 'Productiemedewerker hout', '0');
    INSERT INTO `sub_certificates` VALUES (249, 71, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (250, 71, 'Initiatie houtbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (72, 'Slager/bereider verkoopklare gerechten', '0');
    INSERT INTO `sub_certificates` VALUES (251, 72, 'Inkoop vlees', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (252, 72, 'Ontvangst en opslag vers vlees', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (253, 72, 'Toonbankverkoop', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (254, 72, 'Uitbenen en versnijden', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (255, 72, 'Verkoopklare gerechten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (256, 72, 'Vleesbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (257, 72, 'Winkelklaar maken vers vlees', NULL, '1');
INSERT INTO `certificates` VALUES (73, 'Callcentermedewerker', '0');
    INSERT INTO `sub_certificates` VALUES (258, 73, 'Callcenter', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (259, 73, 'Telefonie', NULL, '1');
INSERT INTO `certificates` VALUES (74, 'Matroos binnenvaart', '0');
    INSERT INTO `sub_certificates` VALUES (260, 74, 'Basisonderhoud scheepsinstallaties', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (261, 74, 'Basisonderhoud schip', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (262, 74, 'Ondersteuning laden en lossen schip', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (263, 74, 'Ondersteuning manoeuvres schip', NULL, '1');
INSERT INTO `certificates` VALUES (75, 'Daktimmerman', '0');
    INSERT INTO `sub_certificates` VALUES (264, 75, 'Dakconstructies', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (265, 75, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (266, 75, 'Plaatsing balklagen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (267, 75, 'Plaatsing repetitieve spanten', NULL, '1');
INSERT INTO `certificates` VALUES (76, 'Plaatser buitenschrijnwerk', '0');
    INSERT INTO `sub_certificates` VALUES (268, 76, 'Afwerking buitenschrijnwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (269, 76, 'Plaatsing buitenschrijnwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (270, 76, 'Voorbereiding plaatsing buitenschrijnwerk', NULL, '1');
INSERT INTO `certificates` VALUES (77, 'Schilder', '0');
    INSERT INTO `sub_certificates` VALUES (271, 77, 'Basistechnieken schilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (272, 77, 'Pistoolschilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (273, 77, 'Schilderwerk', NULL, '1');
INSERT INTO `certificates` VALUES (78, 'Plaatlasser', '0');
    INSERT INTO `sub_certificates` VALUES (274, 78, 'Beklede elektrode hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (275, 78, 'Beklede elektrode plaatlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (276, 78, 'MIG/MAG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (277, 78, 'MIG/MAG plaatlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (278, 78, 'TIG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (279, 78, 'TIG plaatlas', NULL, '1');
INSERT INTO `certificates` VALUES (79, 'Paletten- en krattenmaker', '0');
    INSERT INTO `sub_certificates` VALUES (280, 79, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (281, 79, 'Paletten en kratten', NULL, '1');
INSERT INTO `certificates` VALUES (80, 'Productiemedewerker kunststoffen', '0');
    INSERT INTO `sub_certificates` VALUES (282, 80, 'Afwerking en verpakking kunststofproduct', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (283, 80, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (284, 80, 'Conditionering grondstoffen voor kunststoffen', NULL, '1');
INSERT INTO `certificates` VALUES (81, 'Medewerker industrieel schilder', '0');
    INSERT INTO `sub_certificates` VALUES (285, 81, 'Basistechnieken industrieel schilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (286, 81, 'Voorbehandeling industrieel schilderwerk', NULL, '1');
INSERT INTO `certificates` VALUES (82, 'Publiciteitsschilder', '0');
    INSERT INTO `sub_certificates` VALUES (287, 82, 'Belettering', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (288, 82, 'Publiciteitsschilderwerk', NULL, '1');
INSERT INTO `certificates` VALUES (83, 'Karkassenmaker', '0');
    INSERT INTO `sub_certificates` VALUES (289, 83, 'Karkassen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (290, 83, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (291, 83, 'Manuele houtbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (84, 'Containerhersteller', '0');
    INSERT INTO `sub_certificates` VALUES (292, 84, 'Containerherstelling', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (293, 84, 'MIG/MAG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (294, 84, 'MIG/MAG plaatlas', NULL, '1');
INSERT INTO `certificates` VALUES (85, 'Industrieel elektrotechnisch installateur', '0');
    INSERT INTO `sub_certificates` VALUES (295, 85, 'Borden elektriciteit industrieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (296, 85, 'Inbedrijfstelling industriële installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (297, 85, 'Installatiewerken elektriciteit industrieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (298, 85, 'Montagewerken elektriciteit industrieel', NULL, '1');
INSERT INTO `certificates` VALUES (86, 'Pc -technicus', '0');
    INSERT INTO `sub_certificates` VALUES (299, 86, 'Aanpassingen en herstellingen pc', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (300, 86, 'Assemblage en pre-installatie pc', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (301, 86, 'Installatie pc en randapparatuur', NULL, '1');
INSERT INTO `certificates` VALUES (87, 'Kelner', '0');
    INSERT INTO `sub_certificates` VALUES (302, 87, 'Bediening', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (303, 87, 'Initiatie bar en bediening', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (304, 87, 'Klantenservice', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (305, 87, 'Zaal- en tafelschikking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (306, 87, 'Zaalorganisatie', NULL, '1');
INSERT INTO `certificates` VALUES (88, 'Plaatser en hersteller van elektrische en elektronische apparatuur', '0');
    INSERT INTO `sub_certificates` VALUES (307, 88, 'Herstelling elektrische en elektronische apparaten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (308, 88, 'Plaatsing elektrische en elektronische apparaten', NULL, '1');
INSERT INTO `certificates` VALUES (89, 'Machineregelaar thermisch vormen', '0');
    INSERT INTO `sub_certificates` VALUES (309, 89, 'Afwerking en verpakking kunststofproduct', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (310, 89, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (311, 89, 'Bediening machine thermisch vormen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (312, 89, 'Conditionering grondstoffen voor kunststoffen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (313, 89, 'Opvolging productieproces kunststofverwerking', NULL, '1');
INSERT INTO `certificates` VALUES (90, 'Betonhersteller', '0');
    INSERT INTO `sub_certificates` VALUES (314, 90, 'Betonherstelwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (315, 90, 'Betonwerk', NULL, '1');
INSERT INTO `certificates` VALUES (91, 'Voeger', '0');
    INSERT INTO `sub_certificates` VALUES (316, 91, 'Basistechnieken metselwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (317, 91, 'Elastisch voegwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (318, 91, 'Voegwerk in cement', NULL, '1');
INSERT INTO `certificates` VALUES (92, 'Wegmarkeerder', '0');
    INSERT INTO `sub_certificates` VALUES (319, 92, 'Voorbereiding wegmarkering', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (320, 92, 'Wegmarkering met thermoplast', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (321, 92, 'Wegmarkering met verf', NULL, '1');
INSERT INTO `certificates` VALUES (93, 'Spuiter carrosserie', '0');
    INSERT INTO `sub_certificates` VALUES (322, 93, 'Bewerking ondergrond carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (323, 93, 'Grondlagen carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (324, 93, 'Hechtings- en laklagen carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (325, 93, 'Spuitklaar maken carrosserie', NULL, '1');
INSERT INTO `certificates` VALUES (94, 'Onderhoudstechnicus industriële installaties', '0');
    INSERT INTO `sub_certificates` VALUES (326, 94, 'Onderhoudswerken industriële installaties', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (327, 94, 'Operationele ingrepen industriële installaties', NULL, '1');
INSERT INTO `certificates` VALUES (95, 'Technicus inbraakbeveiligingssystemen', '0');
    INSERT INTO `sub_certificates` VALUES (328, 95, 'Implementatie inbraakbeveiligingssysteem', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (329, 95, 'Inbedrijfstelling residentiële installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (330, 95, 'Installatiewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (331, 95, 'Montagewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (332, 95, 'Ruwbouwwerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (333, 95, 'Verdeelbord elektriciteit residentieel', NULL, '1');
INSERT INTO `certificates` VALUES (96, 'Bedrader-monteerder', '0');
    INSERT INTO `sub_certificates` VALUES (334, 96, 'Montage componenten printplaat', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (335, 96, 'Testing en herstelling schakelingen printplaat', NULL, '1');
INSERT INTO `certificates` VALUES (97, 'Winkelbediende', '0');
    INSERT INTO `sub_certificates` VALUES (336, 97, 'Aanvulwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (337, 97, 'Kassawerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (338, 97, 'Klantencontact', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (339, 97, 'Presentatiewerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (340, 97, 'Verkoop', NULL, '1');
INSERT INTO `certificates` VALUES (98, 'Uitsnijder-uitbener schaap', '0');
    INSERT INTO `sub_certificates` VALUES (341, 98, 'Industriële vleesafwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (342, 98, 'Ontvangst vlees en karkassen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (343, 98, 'Schaap versnijden en uitbenen', NULL, '1');
INSERT INTO `certificates` VALUES (99, 'Oppervlaktebehandelaar', '0');
    INSERT INTO `sub_certificates` VALUES (344, 99, 'Manuele oppervlaktebehandeling hout', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (345, 99, 'Oppervlaktebehandeling hout', NULL, '1');
INSERT INTO `certificates` VALUES (100, 'Residentieel elektrotechnisch installateur', '0');
    INSERT INTO `sub_certificates` VALUES (346, 100, 'Inbedrijfstelling residentiële installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (347, 100, 'Installatiewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (348, 100, 'Montagewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (349, 100, 'Ruwbouwwerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (350, 100, 'Verdeelbord elektriciteit residentieel', NULL, '1');
INSERT INTO `certificates` VALUES (101, 'Magazijnmedewerker', '0');
    INSERT INTO `sub_certificates` VALUES (351, 101, 'Inkomende goederen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (352, 101, 'Magazijntaken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (353, 101, 'Uitgaande goederen', NULL, '1');
INSERT INTO `certificates` VALUES (102, 'Hulpkok', '0');
    INSERT INTO `sub_certificates` VALUES (354, 102, 'Initiatie keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (355, 102, 'Keukenprocessen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (356, 102, 'Keukentechnieken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (357, 102, 'Koude keukenbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (358, 102, 'Warme keukenbereidingen', NULL, '1');
INSERT INTO `certificates` VALUES (103, 'Machineregelaar spuitgieten', '0');
    INSERT INTO `sub_certificates` VALUES (359, 103, 'Afwerking en verpakking kunststofproduct', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (360, 103, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (361, 103, 'Bediening spuitgietmachine', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (362, 103, 'Conditionering grondstoffen voor kunststoffen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (363, 103, 'Opvolging productieproces kunststofverwerking', NULL, '1');
INSERT INTO `certificates` VALUES (104, 'Polyvalent mecanicien personenwagens en lichte bedrijfsvoertuigen', '0');
    INSERT INTO `sub_certificates` VALUES (364, 104, 'Herstelling elektrische defecten personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (365, 104, 'Herstelling mechanische en hydraulische defecten personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (366, 104, 'In- of opbouw installaties personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (367, 104, 'Periodiek onderhoud personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (368, 104, 'Vervanging en herstelling onderdelen personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (369, 104, 'Voorbereiding technische keuring personenwagens en lichte bedrijfsvoertuigen', NULL, '1');
INSERT INTO `certificates` VALUES (105, 'Productieoperator hout', '0');
    INSERT INTO `sub_certificates` VALUES (370, 105, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (371, 105, 'Bediening machinestraat of installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (372, 105, 'Initiatie houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (373, 105, 'Opvolging productieproces', NULL, '1');
INSERT INTO `certificates` VALUES (106, 'Verkoper', '0');
    INSERT INTO `sub_certificates` VALUES (374, 106, 'Klantencontact', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (375, 106, 'Presentatiewerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (376, 106, 'Verkoop', NULL, '1');
INSERT INTO `certificates` VALUES (107, 'Bestuurder reachtruck', '0');
    INSERT INTO `sub_certificates` VALUES (377, 107, 'Besturing reachtruck', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (378, 107, 'Verplaatsing lading reachtruck', NULL, '1');
INSERT INTO `certificates` VALUES (108, 'Productieoperator voeding', '0');
    INSERT INTO `sub_certificates` VALUES (379, 108, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (380, 108, 'Bediening machinestraat of installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (381, 108, 'Opvolging productieproces', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (382, 108, 'Voedingsindustrie', NULL, '1');
INSERT INTO `certificates` VALUES (109, 'Bandenmonteur', '0');
    INSERT INTO `sub_certificates` VALUES (383, 109, 'Herstelling band', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (384, 109, 'Montage/demontage wiel/band', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (385, 109, 'Uitlijning voertuig', NULL, '1');
INSERT INTO `certificates` VALUES (110, 'Kassier', '0');
    INSERT INTO `sub_certificates` VALUES (386, 110, 'Kassawerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (387, 110, 'Klantencontact', NULL, '1');
INSERT INTO `certificates` VALUES (111, 'Meubelstoffeerder', '0');
    INSERT INTO `sub_certificates` VALUES (388, 111, 'Eenvoudig stoffeerwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (389, 111, 'Grijswerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (390, 111, 'Stoffeerwerk', NULL, '1');
INSERT INTO `certificates` VALUES (112, 'Productieoperator metaal', '0');
    INSERT INTO `sub_certificates` VALUES (391, 112, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (392, 112, 'Bediening machinestraat of installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (393, 112, 'Initiatie metaalbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (394, 112, 'Opvolging productieproces', NULL, '1');
INSERT INTO `certificates` VALUES (113, 'Administratief medewerker', '0');
    INSERT INTO `sub_certificates` VALUES (395, 113, 'Administratief werk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (396, 113, 'Ondersteunende taken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (397, 113, 'Onthaal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (398, 113, 'Postverwerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (399, 113, 'Telefonie', NULL, '1');
INSERT INTO `certificates` VALUES (114, 'Kunststofbewerker', '0');
    INSERT INTO `sub_certificates` VALUES (400, 114, 'Bewerking kunststofhalffabricaten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (401, 114, 'Vervaardiging en afwerking kunststofproduct', NULL, '1');
INSERT INTO `certificates` VALUES (115, 'Decor- & standenbouwer', '0');
    INSERT INTO `sub_certificates` VALUES (402, 115, 'Decor- & standenbouw', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (403, 115, 'Inrichting sets en standen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (404, 115, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (405, 115, 'Manuele houtbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (116, 'Glaswerker', '0');
    INSERT INTO `sub_certificates` VALUES (406, 116, 'Bewerking glas en glasvervangende kunststoffen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (407, 116, 'Plaatsing glas en glasvervangende kunststoffen', NULL, '1');
INSERT INTO `certificates` VALUES (117, 'Lasser MIG/MAG', '0');
    INSERT INTO `sub_certificates` VALUES (408, 117, 'MIG/MAG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (409, 117, 'MIG/MAG pijplas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (410, 117, 'MIG/MAG plaatlas', NULL, '1');
INSERT INTO `certificates` VALUES (118, 'Assistent podiumtechnicus', '0');
    INSERT INTO `sub_certificates` VALUES (411, 118, 'Belichting podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (412, 118, 'Decormontage podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (413, 118, 'Geluidsversterking podiumkunsten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (414, 118, 'Voorbereiding productie podiumkunsten', NULL, '1');
INSERT INTO `certificates` VALUES (119, 'Motorfietsmecanicien', '0');
    INSERT INTO `sub_certificates` VALUES (415, 119, 'Montage en afstelling motorfiets', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (416, 119, 'Periodiek onderhoud motorfiets', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (417, 119, 'Vervanging en herstelling onderdelen motorfiets', NULL, '1');
INSERT INTO `certificates` VALUES (120, 'Binnenschrijnwerker', '0');
    INSERT INTO `sub_certificates` VALUES (418, 120, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (419, 120, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (420, 120, 'Plaatsing binnenschrijnwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (421, 120, 'Plaatsing parket', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (422, 120, 'Werkplaatsbinnenschrijnwerk', NULL, '1');
INSERT INTO `certificates` VALUES (121, 'Medewerker kamerdienst', '0');
    INSERT INTO `sub_certificates` VALUES (423, 121, 'Kamerdienst', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (424, 121, 'Linnendienst', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (425, 121, 'Ontbijtdienst', NULL, '1');
INSERT INTO `certificates` VALUES (122, 'Machinaal houtbewerker', '0');
    INSERT INTO `sub_certificates` VALUES (426, 122, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (427, 122, 'Manuele houtbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (123, 'Rigger-monteerder', '0');
    INSERT INTO `sub_certificates` VALUES (428, 123, 'Montage- en verbindingstechnieken rigger-monteerder', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (429, 123, 'Rigging', NULL, '1');
INSERT INTO `certificates` VALUES (124, 'Stellingbouwer', '0');
    INSERT INTO `sub_certificates` VALUES (430, 124, 'Inplanting stelling', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (431, 124, 'Op- en afbouw stelling', NULL, '1');
INSERT INTO `certificates` VALUES (125, 'Koelmonteur', '0');
    INSERT INTO `sub_certificates` VALUES (432, 125, 'Elektriciteitswerken koelinstallatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (433, 125, 'Inbedrijfstelling koelinstallatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (434, 125, 'Koeltechnische installatiewerken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (435, 125, 'Montagewerken elektriciteit industrieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (436, 125, 'Montagewerken koelinstallatie', NULL, '1');
INSERT INTO `certificates` VALUES (126, 'Assistent productieoperator metaal', '0');
    INSERT INTO `sub_certificates` VALUES (437, 126, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (438, 126, 'Bediening machinestraat of installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (439, 126, 'Initiatie metaalbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (127, 'Slager/spekslager', '0');
    INSERT INTO `sub_certificates` VALUES (440, 127, 'Inkoop vlees', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (441, 127, 'Ontvangst en opslag vers vlees', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (442, 127, 'Toonbankverkoop', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (443, 127, 'Uitbenen en versnijden', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (444, 127, 'Vleesbereidingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (445, 127, 'Vleesproducten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (446, 127, 'Winkelklaar maken vers vlees', NULL, '1');
INSERT INTO `certificates` VALUES (128, 'Verhuizer-drager', '0');
    INSERT INTO `sub_certificates` VALUES (447, 128, 'Laden en lossen goederen verhuis', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (448, 128, 'Voorbereiding verhuis', NULL, '1');
INSERT INTO `certificates` VALUES (129, 'Stratenmaker', '0');
    INSERT INTO `sub_certificates` VALUES (449, 129, 'Plaatsing boordstenen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (450, 129, 'Plaatsing wegverhardingen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (451, 129, 'Voorbereiding bestrating', NULL, '1');
INSERT INTO `certificates` VALUES (130, 'Bestuurder mobiele kraan', '0');
    INSERT INTO `sub_certificates` VALUES (452, 130, 'Hijswerkzaamheden mobiele kraan', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (453, 130, 'Opstelling mobiele kraan', NULL, '1');
INSERT INTO `certificates` VALUES (131, 'Productiemedewerker interieurbouw', '0');
    INSERT INTO `sub_certificates` VALUES (454, 131, 'Interieurelementen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (455, 131, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (456, 131, 'Manuele houtbewerking', NULL, '1');
INSERT INTO `certificates` VALUES (132, 'Technicus immotica', '0');
    INSERT INTO `sub_certificates` VALUES (457, 132, 'Borden elektriciteit tertiair', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (458, 132, 'Implementatie immoticasysteem', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (459, 132, 'Inbedrijfstelling tertiaire installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (460, 132, 'Installatiewerken elektriciteit tertiair', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (461, 132, 'Montagewerken elektriciteit tertiair', NULL, '1');
INSERT INTO `certificates` VALUES (133, 'Dakdekker metalen daken', '0');
    INSERT INTO `sub_certificates` VALUES (462, 133, 'Dakgoten en hemelwaterafvoeren', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (463, 133, 'Metalen gevel- en dakelementen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (464, 133, 'Plaatsing metalen gevel- en dakelementen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (465, 133, 'Voorbereiding dakwerken', NULL, '1');
INSERT INTO `certificates` VALUES (134, 'Carrosseriehersteller', '0');
    INSERT INTO `sub_certificates` VALUES (466, 134, 'Bewerking ondergrond carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (467, 134, 'Demontage/montage carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (468, 134, 'Grondlagen carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (469, 134, 'Hechtings- en laklagen carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (470, 134, 'Plaatwerk carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (471, 134, 'Spuitklaar maken carrosserie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (472, 134, 'Voorbereiding carrosseriewerken', NULL, '1');
INSERT INTO `certificates` VALUES (135, 'Meubelmaker', '0');
    INSERT INTO `sub_certificates` VALUES (473, 135, 'Afwerking meubel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (474, 135, 'Machinale houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (475, 135, 'Manuele houtbewerking', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (476, 135, 'Meubel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (477, 135, 'Meubelassemblage', NULL, '1');
INSERT INTO `certificates` VALUES (136, 'Bekister', '0');
    INSERT INTO `sub_certificates` VALUES (478, 136, 'Basistechnieken metselwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (479, 136, 'Bekisting', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (480, 136, 'Funderingen op staal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (481, 136, 'Metselwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (482, 136, 'Opritten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (483, 136, 'Riolerings- en afwateringsstelsels', NULL, '1');
INSERT INTO `certificates` VALUES (137, 'Dakdekker', '0');
    INSERT INTO `sub_certificates` VALUES (484, 137, 'Dakgoten en hemelwaterafvoeren', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (485, 137, 'Metalen gevel- en dakelementen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (486, 137, 'Plaatsing leien', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (487, 137, 'Plaatsing metalen gevel- en dakelementen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (488, 137, 'Plaatsing pannen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (489, 137, 'Voorbereiding dakwerken', NULL, '1');
INSERT INTO `certificates` VALUES (138, 'Schilder-decorateur', '0');
    INSERT INTO `sub_certificates` VALUES (490, 138, 'Basistechnieken schilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (491, 138, 'Basistechnieken vloerbekleding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (492, 138, 'Basistechnieken wandbekleding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (493, 138, 'Pistoolschilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (494, 138, 'Plaatsing soepele vloerbekleding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (495, 138, 'Plaatsing wandbekleding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (496, 138, 'Schilderwerk', NULL, '1');
INSERT INTO `certificates` VALUES (139, 'Matroos motordrijver binnenvaart', '0');
    INSERT INTO `sub_certificates` VALUES (497, 139, 'Basisonderhoud scheepsinstallaties', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (498, 139, 'Basisonderhoud schip', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (499, 139, 'Bediening scheepsmotoren', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (500, 139, 'Controle en onderhoud scheepsinstallaties', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (501, 139, 'Herstelling scheepsinstallaties', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (502, 139, 'Ondersteuning laden en lossen schip', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (503, 139, 'Ondersteuning manoeuvres schip', NULL, '1');
INSERT INTO `certificates` VALUES (140, 'Industrieel vleesproductenbereider', '0');
    INSERT INTO `sub_certificates` VALUES (504, 140, 'Industriële vleesproductenbereiding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (505, 140, 'Voedingsindustrie', NULL, '1');
INSERT INTO `certificates` VALUES (141, 'Brood- en banketbakker', '0');
    INSERT INTO `sub_certificates` VALUES (506, 141, 'Beslagen banketproducten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (507, 141, 'Broden', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (508, 141, 'Broodspecialiteiten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (509, 141, 'Crèmes, mousses en romen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (510, 141, 'Degen banketproducten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (511, 141, 'Harde- en zachteluxe', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (512, 141, 'Harde- en zachteluxespecialiteiten', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (513, 141, 'Initiatie ijs', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (514, 141, 'Marsepein, suikerwerk en chocolade', NULL, '1');
INSERT INTO `certificates` VALUES (142, 'Machineregelaar extrusie', '0');
    INSERT INTO `sub_certificates` VALUES (515, 142, 'Afwerking en verpakking kunststofproduct', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (516, 142, 'Bediening machine of (deel)installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (517, 142, 'Bediening extrusiemachine', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (518, 142, 'Conditionering grondstoffen voor kunststoffen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (519, 142, 'Opvolging productieproces kunststofverwerking', NULL, '1');
INSERT INTO `certificates` VALUES (143, 'Sanitair installateur', '0');
    INSERT INTO `sub_certificates` VALUES (520, 143, 'Plaatsing en aansluiting toestellen individuele gasverwarming', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (521, 143, 'Plaatsing en aansluiting toestellen sanitair', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (522, 143, 'Plaatsing leidingen individuele gasverwarming', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (523, 143, 'Plaatsing leidingen sanitair', NULL, '1');
INSERT INTO `certificates` VALUES (144, 'Industrieel schilder', '0');
    INSERT INTO `sub_certificates` VALUES (524, 144, 'Basistechnieken industrieel schilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (525, 144, 'Industrieel schilderwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (526, 144, 'Voorbehandeling industrieel schilderwerk', NULL, '1');
INSERT INTO `certificates` VALUES (145, 'Installateur fotovoltaïsche systemen', '0');
    INSERT INTO `sub_certificates` VALUES (527, 145, 'Dakwerkzaamheden plaatsing fotovoltaïsche systemen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (528, 145, 'Inbedrijfstelling fotovoltaïsche systemen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (529, 145, 'Installatiewerken elektriciteit fotovoltaïsche systemen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (530, 145, 'Montagewerken elektriciteit residentieel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (531, 145, 'Plaatsing fotovoltaïsche modules', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (532, 145, 'Ruwbouwwerken elektriciteit residentieel', NULL, '1');
INSERT INTO `certificates` VALUES (146, 'Polyvalent mecanicien zware bedrijfsvoertuigen', '0');
    INSERT INTO `sub_certificates` VALUES (533, 146, 'Herstelling elektrische defecten zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (534, 146, 'Herstelling mechanische en hydraulische defecten zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (535, 146, 'In- of opbouw installaties zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (536, 146, 'Periodiek onderhoud zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (537, 146, 'Vervanging en herstelling onderdelen zware bedrijfsvoertuigen', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (538, 146, 'Voorbereiding technische keuring zware bedrijfsvoertuigen', NULL, '1');
INSERT INTO `certificates` VALUES (147, 'Tertiair elektrotechnisch installateur', '0');
    INSERT INTO `sub_certificates` VALUES (539, 147, 'Borden elektriciteit tertiair', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (540, 147, 'Inbedrijfstelling tertiaire installatie', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (541, 147, 'Installatiewerken elektriciteit tertiair', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (542, 147, 'Montagewerken elektriciteit tertiair', NULL, '1');
INSERT INTO `certificates` VALUES (148, 'Keukenmedewerker', '0');
    INSERT INTO `sub_certificates` VALUES (543, 148, 'Initiatie keuken', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (544, 148, 'Keukentechnieken', NULL, '1');
INSERT INTO `certificates` VALUES (149, 'Hoeknaadlasser', '0');
    INSERT INTO `sub_certificates` VALUES (545, 149, 'Beklede elektrode hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (546, 149, 'MIG/MAG hoeknaadlas', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (547, 149, 'TIG hoeknaadlas', NULL, '1');
INSERT INTO `certificates` VALUES (150, 'Industrieel maaltijdbereider', '0');
    INSERT INTO `sub_certificates` VALUES (548, 150, 'Industriële maaltijdbereiding', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (549, 150, 'Organisatie industriële maaltijdbereiding', NULL, '1');
INSERT INTO `certificates` VALUES (151, 'Metselaar', '0');
    INSERT INTO `sub_certificates` VALUES (550, 151, 'Basistechnieken metselwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (551, 151, 'Bekisting', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (552, 151, 'Funderingen op staal', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (553, 151, 'Metselwerk', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (554, 151, 'Riolerings- en afwateringsstelsels', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (555, 151, 'Specifiek uitvoeringswerk', NULL, '1');
INSERT INTO `certificates` VALUES (152, 'Bestuurder heftruck', '0');
    INSERT INTO `sub_certificates` VALUES (556, 152, 'Besturing heftruck', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (557, 152, 'Verplaatsing lading extern transportmiddel', NULL, '1');
    INSERT INTO `sub_certificates` VALUES (558, 152, 'Verplaatsing lading heftruck', NULL, '1');


CREATE TABLE `sub_certificate_categories` (
  `id`                 INT          NOT NULL AUTO_INCREMENT,
  `sub_certificate_id` INT          NOT NULL,
  `name`               VARCHAR(255) NOT NULL,
  `custom_name`        VARCHAR(255),
  `enabled`            TINYINT(1)   NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`sub_certificate_id`) REFERENCES `sub_certificates` (`id`)
    ON DELETE CASCADE
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

CREATE TABLE `student_info` (
  `id`                             INT          NOT NULL AUTO_INCREMENT,
  `user_id`                        INT          NOT NULL,
  `parent_id`                      INT,
  `grade_id`                       INT          NOT NULL,
  `certificate_id`                 INT          NOT NULL,
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
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`parent_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`grade_id`) REFERENCES `grades` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`certificate_id`) REFERENCES `certificates` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `student_info` VALUES
  (1, 11, 32, 1, 1, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000',
   'Hasselt', '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (2, 12, 31, 1, 1, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000', 'Hasselt',
   '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (3, 13, 31, 2, 1, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000', 'Hasselt',
   '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (4, 14, 32, 1, 2, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000',
   'Hasselt', '012857496', '085479621', 'BE67-500-555-9685');
INSERT INTO `student_info` VALUES
  (5, 15, 31, 1, 2, '2012-01-01', 'Hasselt', 'Belgium', '12345678900', 'Straat', '10', '3000', 'Hasselt',
   '012857496', '085479621', 'BE67-500-555-9685');

CREATE TABLE `student_class` (
  `student_info_id` INT NOT NULL,
  `class_id`        INT NOT NULL,
  PRIMARY KEY (`student_info_id`, `class_id`),
  FOREIGN KEY (`student_info_id`) REFERENCES `student_info` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `student_class` VALUES (1, 1);
INSERT INTO `student_class` VALUES (2, 1);
INSERT INTO `student_class` VALUES (3, 1);
INSERT INTO `student_class` VALUES (4, 3);
INSERT INTO `student_class` VALUES (5, 3);

CREATE TABLE `grade_class` (
  `grade_id` INT NOT NULL,
  `class_id` INT NOT NULL,
  PRIMARY KEY (`grade_id`, `class_id`),
  FOREIGN KEY (`grade_id`) REFERENCES `grades` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `grade_class` VALUES (1, 5);
INSERT INTO `grade_class` VALUES (2, 6);

CREATE TABLE `class_certificate` (
  `class_id`       INT NOT NULL,
  `certificate_id` INT NOT NULL,
  PRIMARY KEY (`class_id`, `certificate_id`),
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`certificate_id`) REFERENCES `certificates` (`id`)
    ON DELETE CASCADE
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
  FOREIGN KEY (`sub_certificate_category_id`) REFERENCES `sub_certificate_categories` (`id`)
    ON DELETE CASCADE
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
  FOREIGN KEY (`grade_id`) REFERENCES `grades` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`course_topic_id`) REFERENCES `course_topics` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `objectives` VALUES (1, 1, 1, 'Kent Vakthema 1', NULL);
INSERT INTO `objectives` VALUES (2, 2, 1, 'Kent Vakthema 2', NULL);

CREATE TABLE `student_bgv_score` (
  `id`            INT     NOT NULL AUTO_INCREMENT,
  `student_id`    INT     NOT NULL,
  `competence_id` INT     NOT NULL,
  `score`         CHAR(1) NOT NULL,
  `graded_date`   DATE    NOT NULL,
  `remarks`       VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`competence_id`) REFERENCES `competences` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `student_bgv_score` VALUES (1, 1, 1, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (2, 1, 2, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (3, 1, 3, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (4, 1, 4, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (5, 1, 5, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (6, 1, 6, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (7, 1, 7, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (8, 1, 8, 'V', '2016-04-08', 'Remarks test');
INSERT INTO `student_bgv_score` VALUES (9, 1, 9, 'V', '2016-04-08', 'Remarks test');

CREATE TABLE `student_pav_score` (
  `id`           INT     NOT NULL AUTO_INCREMENT,
  `student_id`   INT     NOT NULL,
  `objective_id` INT     NOT NULL,
  `score`        CHAR(1) NOT NULL,
  `graded_date`  DATE    NOT NULL,
  `remarks`      VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`student_id`) REFERENCES `student_info` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`objective_id`) REFERENCES `objectives` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `student_pav_score` VALUES (1, 1, 1, 'V', '2016-04-08', 'Remarks test');

CREATE TABLE `class_topics` (
  `course_topic_id` INT NOT NULL,
  `class_id`        INT NOT NULL,
  PRIMARY KEY (`course_topic_id`, `class_id`),
  FOREIGN KEY (`course_topic_id`) REFERENCES `course_topics` (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`)
    ON DELETE CASCADE
);

INSERT INTO `class_topics` VALUES (1, 1);
