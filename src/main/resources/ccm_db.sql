-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 25. Jan 2017 um 20:13
-- Server-Version: 10.1.19-MariaDB
-- PHP-Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ccm_db`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `adresse`
--

CREATE TABLE `adresse` (
  `adresse_id` int(128) NOT NULL,
  `adresse_plz_id` varchar(128) NOT NULL,
  `unternehmen_id` int(128) DEFAULT NULL,
  `adresse_strasse` varchar(128) NOT NULL,
  `adresse_hausnummer` varchar(128) NOT NULL,
  `adresse_ort` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `adresse`
--

INSERT INTO `adresse` (`adresse_id`, `adresse_plz_id`, `unternehmen_id`, `adresse_strasse`, `adresse_hausnummer`, `adresse_ort`) VALUES
(1, '74131', 1, 'August-Haeusser-Strasse', '10', 'Heilbronn'),
(2, '74131', 2, 'Andere Strasse', '12b', 'Anderer Ort'),
(3, '1234', 3, 'Beispielstrasse', '1', 'Beispielhausen'),
(4, '1234', 4, '4Strasse', '4', 'Test4'),
(5, '1234', 5, 'Strasse5', '5', 'test5'),
(6, '1234', 7, 'strasse7', '7', 'ort7'),
(7, '1234', 8, 'strasse8', '8', 'ort8'),
(8, '1234', 1, '123435241', '1231', '1342322');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ansprechpartner`
--

CREATE TABLE `ansprechpartner` (
  `ansprechpartner_id` int(126) NOT NULL,
  `ansprechpartner_vorname` varchar(255) NOT NULL,
  `ansprechpartner_nachname` varchar(255) NOT NULL,
  `adresse_id` int(126) NOT NULL,
  `ansprechpartner_emailadresse` varchar(256) DEFAULT NULL,
  `ansprechpartner_telefonnummer` varchar(20) DEFAULT NULL,
  `ansprechpartner_unternehmen_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `ansprechpartner`
--

INSERT INTO `ansprechpartner` (`ansprechpartner_id`, `ansprechpartner_vorname`, `ansprechpartner_nachname`, `adresse_id`, `ansprechpartner_emailadresse`, `ansprechpartner_telefonnummer`, `ansprechpartner_unternehmen_id`) VALUES
(1, 'Willibald', 'Ansprech', 1, NULL, NULL, 1),
(3, 'Mister', 'Ansprechpartner', 2, 'mister@ansprechpartner.de', '+49 1234 5678', 2),
(4, 'Werner', 'Anprecher', 1, NULL, NULL, 1),
(5, 'kjljk', 'asdfasdf', 1, NULL, NULL, 1),
(6, 'asdfafg', 'rqetrgfdsz', 1, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE `benutzer` (
  `vorname` varchar(16) NOT NULL,
  `nachname` varchar(16) NOT NULL,
  `benutzer_id` varchar(12) NOT NULL,
  `rolle_id` int(2) NOT NULL,
  `beruf_id` int(2) NOT NULL,
  `benutzer_email` varchar(30) DEFAULT NULL,
  `benutzer_telefon` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`vorname`, `nachname`, `benutzer_id`, `rolle_id`, `beruf_id`, `benutzer_email`, `benutzer_telefon`) VALUES
('Friedrich', 'Gustavson', 'fgustavson', 1, 1, NULL, NULL),
('Max', 'Mustermann', 'mmustermann', 1, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer_besuch`
--

CREATE TABLE `benutzer_besuch` (
  `benutzer_besuch_id` int(11) NOT NULL,
  `benutzer_id` varchar(12) NOT NULL,
  `besuch_id` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `benutzer_besuch`
--

INSERT INTO `benutzer_besuch` (`benutzer_besuch_id`, `benutzer_id`, `besuch_id`) VALUES
(13, 'mmustermann', 5),
(16, 'fgustavson', 6),
(17, 'mmustermann', 6);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `berechtigung`
--

CREATE TABLE `berechtigung` (
  `berechtigung_id` int(11) NOT NULL,
  `berechtigung_bezeichnung` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `berechtigung`
--

INSERT INTO `berechtigung` (`berechtigung_id`, `berechtigung_bezeichnung`) VALUES
(1, 'BesuchBearbeitung9'),
(2, 'test'),
(3, 'BesuchBearbeitung1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `beruf`
--

CREATE TABLE `beruf` (
  `beruf_id` int(2) NOT NULL,
  `beruf_bezeichnung` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `beruf`
--

INSERT INTO `beruf` (`beruf_id`, `beruf_bezeichnung`) VALUES
(1, 'Studiengangsleiter');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `besuch`
--

CREATE TABLE `besuch` (
  `besuch_id` int(255) NOT NULL,
  `adresse_id` int(11) NOT NULL,
  `besuch_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `besuch_beginn` datetime NOT NULL,
  `besuch_ende` datetime NOT NULL,
  `besuch_name` varchar(255) NOT NULL,
  `besuch_autor` varchar(12) NOT NULL,
  `status_id` int(2) NOT NULL,
  `ansprechpartner_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `besuch`
--

INSERT INTO `besuch` (`besuch_id`, `adresse_id`, `besuch_timestamp`, `besuch_beginn`, `besuch_ende`, `besuch_name`, `besuch_autor`, `status_id`, `ansprechpartner_id`) VALUES
(1, 1, '2017-01-17 00:01:18', '2017-01-17 11:00:00', '2017-01-17 14:00:00', 'Erstes Treffen', 'fgustavson', 1, 1),
(2, 1, '2017-01-18 11:21:15', '2017-01-18 00:00:00', '2017-01-18 00:00:00', 'Titel zum testen von Probs', 'mmustermann', 1, 1),
(3, 1, '2017-01-18 00:34:55', '2017-01-19 00:00:00', '2017-01-19 00:00:00', 'Testtitel', 'mmustermann', 1, 1),
(4, 1, '2017-01-18 11:26:01', '2017-01-19 00:00:00', '2017-01-19 00:00:00', 'Tets', 'mmustermann', 1, 1),
(5, 1, '2017-01-18 11:28:47', '2017-01-19 00:00:00', '2017-01-19 00:00:00', 'Test3', 'mmustermann', 1, 1),
(6, 1, '2017-01-19 14:41:53', '2017-01-20 13:00:00', '2017-01-20 14:00:00', 'teschd', 'mmustermann', 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `gespraechsnotizen`
--

CREATE TABLE `gespraechsnotizen` (
  `gespraechsnotiz_id` int(255) NOT NULL,
  `gespraechsnotiz_notiz` longblob,
  `gespraechsnotiz_bild` longblob,
  `unternehmen_id` int(11) NOT NULL,
  `besuch_id` int(255) DEFAULT NULL,
  `gespraechsnotiz_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `gespraechsnotizen`
--

INSERT INTO `gespraechsnotizen` (`gespraechsnotiz_id`, `gespraechsnotiz_notiz`, `gespraechsnotiz_bild`, `unternehmen_id`, `besuch_id`, `gespraechsnotiz_timestamp`) VALUES
(8, 0x546573740d0a31, 0x546573740d0a31, 1, 1, '2017-01-23 00:24:08');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `password`
--

CREATE TABLE `password` (
  `password_hash` varchar(244) NOT NULL,
  `benutzer_id` varchar(12) NOT NULL,
  `password_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `password`
--

INSERT INTO `password` (`password_hash`, `benutzer_id`, `password_id`) VALUES
('5f4dcc3b5aa765d61d8327deb882cf99', 'mmustermann', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rolle`
--

CREATE TABLE `rolle` (
  `rolle_id` int(11) NOT NULL,
  `rolle_bezeichnung` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `rolle`
--

INSERT INTO `rolle` (`rolle_id`, `rolle_bezeichnung`) VALUES
(1, 'ccm_all'),
(2, 'Kein Bearbeiten von Besuchen');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rolle_berechtigung`
--

CREATE TABLE `rolle_berechtigung` (
  `rolle_berechtigung_id` int(4) NOT NULL,
  `rolle_id` int(11) NOT NULL,
  `berechtigung_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `rolle_berechtigung`
--

INSERT INTO `rolle_berechtigung` (`rolle_berechtigung_id`, `rolle_id`, `berechtigung_id`) VALUES
(2, 1, 3),
(3, 2, 3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `status`
--

CREATE TABLE `status` (
  `status_id` int(2) NOT NULL,
  `status_bezeichnung` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `status`
--

INSERT INTO `status` (`status_id`, `status_bezeichnung`) VALUES
(1, 'alles gut!');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `studiengang`
--

CREATE TABLE `studiengang` (
  `studiengang_id` int(3) NOT NULL,
  `studiengang_bezeichnung` varchar(255) CHARACTER SET latin1 COLLATE latin1_german2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `studiengang`
--

INSERT INTO `studiengang` (`studiengang_id`, `studiengang_bezeichnung`) VALUES
(1, 'Wirtschaftsinformatik');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `studiengang_ansprechpartner`
--

CREATE TABLE `studiengang_ansprechpartner` (
  `studiengang_ansprechpartner_id` int(126) NOT NULL,
  `studiengang_id` int(126) NOT NULL,
  `ansprechpartner_id` int(126) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `studiengang_ansprechpartner`
--

INSERT INTO `studiengang_ansprechpartner` (`studiengang_ansprechpartner_id`, `studiengang_id`, `ansprechpartner_id`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `studiengang_benutzer`
--

CREATE TABLE `studiengang_benutzer` (
  `studiengang_benutzer_id` int(126) NOT NULL,
  `studiengang_id` int(11) NOT NULL,
  `benutzer_id` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `studiengang_benutzer`
--

INSERT INTO `studiengang_benutzer` (`studiengang_benutzer_id`, `studiengang_id`, `benutzer_id`) VALUES
(3, 1, 'fgustavson');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `unternehmen`
--

CREATE TABLE `unternehmen` (
  `unternehmen_id` int(128) NOT NULL,
  `unternehmen_name` varchar(255) NOT NULL,
  `unternehmen_abc_kennzeichen` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `unternehmen`
--

INSERT INTO `unternehmen` (`unternehmen_id`, `unternehmen_name`, `unternehmen_abc_kennzeichen`) VALUES
(1, 'Amphenol-Tuchel Electronics GmbH', 'A'),
(2, 'Andere Firma', 'A'),
(3, 'Beispielfirma', 'B'),
(4, 'Test4', NULL),
(5, 'Test5', NULL),
(6, 'Test6', NULL),
(7, 'test7', NULL),
(8, 'test8', NULL);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`adresse_id`),
  ADD UNIQUE KEY `adresse_id` (`adresse_id`) USING BTREE,
  ADD KEY `adresse_plz_id` (`adresse_plz_id`) USING BTREE,
  ADD KEY `unternehmen_id` (`unternehmen_id`) USING BTREE;

--
-- Indizes für die Tabelle `ansprechpartner`
--
ALTER TABLE `ansprechpartner`
  ADD PRIMARY KEY (`ansprechpartner_id`),
  ADD KEY `adressen_id` (`adresse_id`) USING BTREE,
  ADD KEY `ansprechpartner_unternehmen_id` (`ansprechpartner_unternehmen_id`);

--
-- Indizes für die Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD PRIMARY KEY (`benutzer_id`),
  ADD UNIQUE KEY `id` (`benutzer_id`),
  ADD KEY `rolle_id` (`rolle_id`) USING BTREE,
  ADD KEY `beruf_id` (`beruf_id`) USING BTREE;

--
-- Indizes für die Tabelle `benutzer_besuch`
--
ALTER TABLE `benutzer_besuch`
  ADD PRIMARY KEY (`benutzer_besuch_id`),
  ADD KEY `benutzer_id` (`benutzer_id`) USING BTREE,
  ADD KEY `besuch_id` (`besuch_id`) USING BTREE;

--
-- Indizes für die Tabelle `berechtigung`
--
ALTER TABLE `berechtigung`
  ADD PRIMARY KEY (`berechtigung_id`),
  ADD UNIQUE KEY `berechtigung_id` (`berechtigung_id`);

--
-- Indizes für die Tabelle `beruf`
--
ALTER TABLE `beruf`
  ADD PRIMARY KEY (`beruf_id`),
  ADD UNIQUE KEY `beruf_id` (`beruf_id`),
  ADD UNIQUE KEY `beruf_bezeichnung` (`beruf_bezeichnung`);

--
-- Indizes für die Tabelle `besuch`
--
ALTER TABLE `besuch`
  ADD PRIMARY KEY (`besuch_id`),
  ADD KEY `besuch_autor` (`besuch_autor`) USING BTREE,
  ADD KEY `status_id` (`status_id`) USING BTREE,
  ADD KEY `adresse_id` (`adresse_id`) USING BTREE,
  ADD KEY `ansprechpartner_id` (`ansprechpartner_id`);

--
-- Indizes für die Tabelle `gespraechsnotizen`
--
ALTER TABLE `gespraechsnotizen`
  ADD PRIMARY KEY (`gespraechsnotiz_id`),
  ADD UNIQUE KEY `gespraechsnotizen_id` (`gespraechsnotiz_id`),
  ADD KEY `unternehmen_id` (`unternehmen_id`) USING BTREE;

--
-- Indizes für die Tabelle `password`
--
ALTER TABLE `password`
  ADD PRIMARY KEY (`password_id`),
  ADD UNIQUE KEY `benutzer_id` (`benutzer_id`) USING BTREE;

--
-- Indizes für die Tabelle `rolle`
--
ALTER TABLE `rolle`
  ADD PRIMARY KEY (`rolle_id`),
  ADD UNIQUE KEY `rolle_id` (`rolle_id`),
  ADD UNIQUE KEY `rolle_bezeichnung` (`rolle_bezeichnung`);

--
-- Indizes für die Tabelle `rolle_berechtigung`
--
ALTER TABLE `rolle_berechtigung`
  ADD PRIMARY KEY (`rolle_berechtigung_id`),
  ADD KEY `berechtigung_id` (`berechtigung_id`),
  ADD KEY `rolle_id` (`rolle_id`);

--
-- Indizes für die Tabelle `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`status_id`),
  ADD UNIQUE KEY `status_id` (`status_id`);

--
-- Indizes für die Tabelle `studiengang`
--
ALTER TABLE `studiengang`
  ADD PRIMARY KEY (`studiengang_id`),
  ADD UNIQUE KEY `studiengang_id` (`studiengang_id`),
  ADD UNIQUE KEY `studiengang_bezeichnung` (`studiengang_bezeichnung`);

--
-- Indizes für die Tabelle `studiengang_ansprechpartner`
--
ALTER TABLE `studiengang_ansprechpartner`
  ADD PRIMARY KEY (`studiengang_ansprechpartner_id`),
  ADD KEY `ansprechpartner_id` (`ansprechpartner_id`) USING BTREE,
  ADD KEY `studiengang_id` (`studiengang_id`) USING BTREE;

--
-- Indizes für die Tabelle `studiengang_benutzer`
--
ALTER TABLE `studiengang_benutzer`
  ADD PRIMARY KEY (`studiengang_benutzer_id`),
  ADD KEY `benutzer_id` (`benutzer_id`) USING BTREE,
  ADD KEY `studiengang_id` (`studiengang_id`) USING BTREE;

--
-- Indizes für die Tabelle `unternehmen`
--
ALTER TABLE `unternehmen`
  ADD PRIMARY KEY (`unternehmen_id`),
  ADD KEY `unternehmen_id` (`unternehmen_id`) USING BTREE;

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `adresse`
--
ALTER TABLE `adresse`
  MODIFY `adresse_id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT für Tabelle `ansprechpartner`
--
ALTER TABLE `ansprechpartner`
  MODIFY `ansprechpartner_id` int(126) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT für Tabelle `benutzer_besuch`
--
ALTER TABLE `benutzer_besuch`
  MODIFY `benutzer_besuch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT für Tabelle `berechtigung`
--
ALTER TABLE `berechtigung`
  MODIFY `berechtigung_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `besuch`
--
ALTER TABLE `besuch`
  MODIFY `besuch_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT für Tabelle `gespraechsnotizen`
--
ALTER TABLE `gespraechsnotizen`
  MODIFY `gespraechsnotiz_id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT für Tabelle `password`
--
ALTER TABLE `password`
  MODIFY `password_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `rolle`
--
ALTER TABLE `rolle`
  MODIFY `rolle_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `rolle_berechtigung`
--
ALTER TABLE `rolle_berechtigung`
  MODIFY `rolle_berechtigung_id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `status`
--
ALTER TABLE `status`
  MODIFY `status_id` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `studiengang`
--
ALTER TABLE `studiengang`
  MODIFY `studiengang_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `studiengang_ansprechpartner`
--
ALTER TABLE `studiengang_ansprechpartner`
  MODIFY `studiengang_ansprechpartner_id` int(126) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT für Tabelle `studiengang_benutzer`
--
ALTER TABLE `studiengang_benutzer`
  MODIFY `studiengang_benutzer_id` int(126) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `unternehmen`
--
ALTER TABLE `unternehmen`
  MODIFY `unternehmen_id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `adresse`
--
ALTER TABLE `adresse`
  ADD CONSTRAINT `adresse_ibfk_2` FOREIGN KEY (`unternehmen_id`) REFERENCES `unternehmen` (`unternehmen_id`);

--
-- Constraints der Tabelle `ansprechpartner`
--
ALTER TABLE `ansprechpartner`
  ADD CONSTRAINT `ansprechpartner_ibfk_1` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`adresse_id`),
  ADD CONSTRAINT `ansprechpartner_ibfk_2` FOREIGN KEY (`ansprechpartner_unternehmen_id`) REFERENCES `unternehmen` (`unternehmen_id`);

--
-- Constraints der Tabelle `benutzer`
--
ALTER TABLE `benutzer`
  ADD CONSTRAINT `benutzer_ibfk_3` FOREIGN KEY (`beruf_id`) REFERENCES `beruf` (`beruf_id`),
  ADD CONSTRAINT `benutzer_ibfk_4` FOREIGN KEY (`rolle_id`) REFERENCES `rolle` (`rolle_id`);

--
-- Constraints der Tabelle `benutzer_besuch`
--
ALTER TABLE `benutzer_besuch`
  ADD CONSTRAINT `benutzer_besuch_ibfk_1` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`benutzer_id`),
  ADD CONSTRAINT `benutzer_besuch_ibfk_2` FOREIGN KEY (`besuch_id`) REFERENCES `besuch` (`besuch_id`);

--
-- Constraints der Tabelle `besuch`
--
ALTER TABLE `besuch`
  ADD CONSTRAINT `besuch_ibfk_1` FOREIGN KEY (`besuch_autor`) REFERENCES `benutzer` (`benutzer_id`),
  ADD CONSTRAINT `besuch_ibfk_2` FOREIGN KEY (`adresse_id`) REFERENCES `adresse` (`adresse_id`),
  ADD CONSTRAINT `besuch_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`),
  ADD CONSTRAINT `besuch_ibfk_4` FOREIGN KEY (`ansprechpartner_id`) REFERENCES `ansprechpartner` (`ansprechpartner_id`);

--
-- Constraints der Tabelle `gespraechsnotizen`
--
ALTER TABLE `gespraechsnotizen`
  ADD CONSTRAINT `gespraechsnotizen_ibfk_1` FOREIGN KEY (`unternehmen_id`) REFERENCES `unternehmen` (`unternehmen_id`);

--
-- Constraints der Tabelle `password`
--
ALTER TABLE `password`
  ADD CONSTRAINT `password_ibfk_1` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`benutzer_id`);

--
-- Constraints der Tabelle `rolle_berechtigung`
--
ALTER TABLE `rolle_berechtigung`
  ADD CONSTRAINT `rolle_berechtigung_ibfk_2` FOREIGN KEY (`berechtigung_id`) REFERENCES `berechtigung` (`berechtigung_id`),
  ADD CONSTRAINT `rolle_berechtigung_ibfk_3` FOREIGN KEY (`rolle_id`) REFERENCES `rolle` (`rolle_id`);

--
-- Constraints der Tabelle `studiengang_ansprechpartner`
--
ALTER TABLE `studiengang_ansprechpartner`
  ADD CONSTRAINT `studiengang_ansprechpartner_ibfk_1` FOREIGN KEY (`ansprechpartner_id`) REFERENCES `ansprechpartner` (`ansprechpartner_id`),
  ADD CONSTRAINT `studiengang_ansprechpartner_ibfk_2` FOREIGN KEY (`studiengang_id`) REFERENCES `studiengang` (`studiengang_id`);

--
-- Constraints der Tabelle `studiengang_benutzer`
--
ALTER TABLE `studiengang_benutzer`
  ADD CONSTRAINT `studiengang_benutzer_ibfk_1` FOREIGN KEY (`studiengang_id`) REFERENCES `studiengang` (`studiengang_id`),
  ADD CONSTRAINT `studiengang_benutzer_ibfk_2` FOREIGN KEY (`benutzer_id`) REFERENCES `benutzer` (`benutzer_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
