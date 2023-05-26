DROP DATABASE INTRANET;
CREATE DATABASE INTRANET;
USE INTRANET;

CREATE TABLE `USUARIOS` (
	`id_usuario` INT NOT NULL AUTO_INCREMENT,
	`nombre` varchar(40) NOT NULL,
	`apellido1` varchar(40),
	`apellido2` varchar(40),
	`doc_identidad` varchar(20),
	`tipo_doc_identidad` varchar(20),
	`perfil` varchar(10),
	`email` varchar(50),
	`login` varchar(50) NOT NULL UNIQUE,
	`pass` varchar(200) NOT NULL,
	`telefono` INT(10),
	`direccion` varchar(100),
	`municipio` varchar(100),
	`activo` BOOLEAN NOT NULL,
	`validado` BOOLEAN NOT NULL,
	PRIMARY KEY (`id_usuario`)
);

CREATE TABLE `PERFILES` (
	`perfil` varchar(10) NOT NULL,
	`descripcion` varchar(20) NOT NULL,
	`activo` BOOLEAN NOT NULL,
	PRIMARY KEY (`perfil`)
);

CREATE TABLE `EQUIPOS` (
	`id_equipo` INT NOT NULL AUTO_INCREMENT,
	`nombre` VARCHAR(255) NOT NULL,
	`categoria` varchar(10) NOT NULL,
	`municipio` varchar(100) NOT NULL,
	`pabellon` varchar(100) NOT NULL,
	`direccion` varchar(100) NOT NULL,
	`temporada` INT NOT NULL,
	PRIMARY KEY (`id_equipo`)
);

CREATE TABLE `CATEGORIAS` (
	`categoria` varchar(10) NOT NULL,
	`descripcion` varchar(50) NOT NULL,
	`modalidad` varchar(10) NOT NULL,
	`cuantia` FLOAT NOT NULL,
	`arbitros` INT NOT NULL,
	`oficiales` INT NOT NULL,
	PRIMARY KEY (`categoria`)
);

CREATE TABLE `PARTIDOS` (
	`id_partido` INT NOT NULL AUTO_INCREMENT,
	`temporada` INT NOT NULL,
	`categoria` varchar(10) NOT NULL,
	`competicion` varchar(20) NOT NULL,
	`jornada` INT NOT NULL,
	`equipo1` INT NOT NULL,
	`equipo2` INT NOT NULL,
	`fecha` DATETIME NOT NULL,
	`municipio` varchar(100) NOT NULL,
	`pabellon` varchar(100) NOT NULL,
	`direccion` varchar(100) NOT NULL,
	PRIMARY KEY (`id_partido`)
);

CREATE TABLE `DESIGNACIONES` (
	`id_designacion` INT NOT NULL AUTO_INCREMENT,
	`fecha` DATE NOT NULL,
	`id_partido` INT NOT NULL,
	`id_arbitro1` INT NOT NULL,
	`id_arbitro2` INT,
	`id_arbitro3` INT,
	`id_oficial1` INT NOT NULL,
	`id_oficial2` INT,
	`id_oficial3` INT,
	`id_oficial4` INT,
	PRIMARY KEY (`id_designacion`)
);

CREATE TABLE `TEMPORADA` (
	`id_temporada` INT NOT NULL AUTO_INCREMENT,
	`descripcion` varchar(100) NOT NULL,
	`fecha_inicio` DATE NOT NULL,
	`fecha_fin` DATE,
	`activa` BOOLEAN NOT NULL,
	PRIMARY KEY (`id_temporada`)
);

CREATE TABLE `COMPETICIONES` (
	`competicion` varchar(20) NOT NULL,
	`descripcion` varchar(50) NOT NULL,
	PRIMARY KEY (`competicion`)
);

ALTER TABLE `USUARIOS` ADD CONSTRAINT `USUARIOS_fk0` FOREIGN KEY (`perfil`) REFERENCES `PERFILES`(`perfil`);

ALTER TABLE `EQUIPOS` ADD CONSTRAINT `EQUIPOS_fk0` FOREIGN KEY (`categoria`) REFERENCES `CATEGORIAS`(`categoria`);

ALTER TABLE `EQUIPOS` ADD CONSTRAINT `EQUIPOS_fk1` FOREIGN KEY (`temporada`) REFERENCES `TEMPORADA`(`id_temporada`);

ALTER TABLE `PARTIDOS` ADD CONSTRAINT `PARTIDOS_fk0` FOREIGN KEY (`temporada`) REFERENCES `TEMPORADA`(`id_temporada`);

ALTER TABLE `PARTIDOS` ADD CONSTRAINT `PARTIDOS_fk1` FOREIGN KEY (`categoria`) REFERENCES `CATEGORIAS`(`categoria`);

ALTER TABLE `PARTIDOS` ADD CONSTRAINT `PARTIDOS_fk2` FOREIGN KEY (`competicion`) REFERENCES `COMPETICIONES`(`competicion`);

ALTER TABLE `PARTIDOS` ADD CONSTRAINT `PARTIDOS_fk3` FOREIGN KEY (`equipo1`) REFERENCES `EQUIPOS`(`id_equipo`);

ALTER TABLE `PARTIDOS` ADD CONSTRAINT `PARTIDOS_fk4` FOREIGN KEY (`equipo2`) REFERENCES `EQUIPOS`(`id_equipo`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk0` FOREIGN KEY (`id_partido`) REFERENCES `PARTIDOS`(`id_partido`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk1` FOREIGN KEY (`id_arbitro1`) REFERENCES `USUARIOS`(`id_usuario`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk2` FOREIGN KEY (`id_arbitro2`) REFERENCES `USUARIOS`(`id_usuario`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk3` FOREIGN KEY (`id_arbitro3`) REFERENCES `USUARIOS`(`id_usuario`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk4` FOREIGN KEY (`id_oficial1`) REFERENCES `USUARIOS`(`id_usuario`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk5` FOREIGN KEY (`id_oficial2`) REFERENCES `USUARIOS`(`id_usuario`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk6` FOREIGN KEY (`id_oficial3`) REFERENCES `USUARIOS`(`id_usuario`);

ALTER TABLE `DESIGNACIONES` ADD CONSTRAINT `DESIGNACIONES_fk7` FOREIGN KEY (`id_oficial4`) REFERENCES `USUARIOS`(`id_usuario`);








