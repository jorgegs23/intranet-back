INSERT INTO temporada (descripcion,fecha_inicio,fecha_fin,activa) VALUES
	 ('2020 - 2021','2020-09-01','2021-08-31',0),
	 ('2021 - 2022','2021-09-01','2022-08-31',0),
	 ('2022 - 2023','2022-09-01','2023-05-02',0),
	 ('2023 - 2024','2023-05-03',NULL,1);


INSERT INTO perfiles (perfil,descripcion,activo) VALUES
	 ('ADM','Administrador',1),
	 ('ARB','Arbitro',1),
	 ('FED','Federacion',1),
	 ('OFI','Oficial',1);
	
INSERT INTO usuarios (nombre,apellido1,apellido2,doc_identidad,tipo_doc_identidad,perfil,email,login,pass,telefono,direccion,municipio,activo,validado) VALUES
	 ('admin',NULL,NULL,NULL,NULL,'adm',NULL,'admin','admin',NULL,NULL,NULL,1,1),
	 ('Jorge','González','Sáenz',NULL,NULL,'OFI','jgselectronico@gmail.com','jorge','jorge',666312471,'C/ Manzanera nº 2',NULL,1,1),
	 ('aimar','moreno','amundarain',NULL,NULL,'ARB',NULL,'aimar','aimar',NULL,NULL,NULL,1,1),
	 ('lucia','trillo',NULL,NULL,NULL,'OFI',NULL,'lucia','lucia',NULL,NULL,NULL,1,1),
	 ('sandra',NULL,NULL,NULL,NULL,'ARB',NULL,'sandra','sandra',NULL,NULL,NULL,0,1),
	 ('leire',NULL,NULL,NULL,NULL,'OFI',NULL,'leire','leire',NULL,NULL,NULL,1,1),
	 ('cris',NULL,NULL,NULL,NULL,'ofi',NULL,'cris','cris',NULL,NULL,NULL,0,0),
	 ('victor','medrano',NULL,NULL,NULL,'OFI',NULL,'victor','victor',NULL,NULL,NULL,0,0),
	 ('belen',NULL,NULL,NULL,NULL,'ofi',NULL,'belen','belen',NULL,NULL,NULL,0,0),
	 ('daniel',NULL,NULL,NULL,NULL,'ofi',NULL,'daniel','daniel',NULL,NULL,NULL,0,0);
INSERT INTO usuarios (nombre,apellido1,apellido2,doc_identidad,tipo_doc_identidad,perfil,email,login,pass,telefono,direccion,municipio,activo,validado) VALUES
	 ('tamara',NULL,NULL,NULL,NULL,'ofi',NULL,'tamara','tamara',NULL,NULL,NULL,0,0),
	 ('sheraz',NULL,NULL,NULL,NULL,'ofi',NULL,'sheraz','sheraz',NULL,NULL,NULL,0,0),
	 ('fran',NULL,NULL,NULL,NULL,'ofi',NULL,'fran','fran',NULL,NULL,NULL,0,0),
	 ('ivan',NULL,NULL,NULL,NULL,'ofi',NULL,'ivan','ivan',NULL,NULL,NULL,0,0),
	 ('admin2',NULL,NULL,NULL,NULL,'adm',NULL,'admin2','admin2',NULL,NULL,NULL,0,0);

	
INSERT INTO categorias (categoria,descripcion,modalidad,cuantia,arbitros,oficiales) VALUES
	 ('jun_masc','Junior Masculino','masculino',11,2,2),
	 ('jun_fem','Junior Femenino','femenino',11,2,2),
	 ('sen_masc','Senior Masculino','masculino',15,2,2),
	 ('sen_fem','Senior Femenino','femenino',15,2,2),
	 ('2_div_masc','2ª Div. Masculina','masculino',20,2,3),
	 ('2_div_fem','2ª Div. femenino','femenino',20,2,3);

INSERT INTO equipos (nombre,categoria,municipio,pabellon,direccion,temporada) VALUES
	 ('Najerilla','jun_fem','Najera','Multiusos','Calle pradillo 2',4),
	 ('Clavijo','jun_fem','Logroño','Pol espartero','Calle breton',3),
	 ('Najera B','jun_fem','Najera','Multiusos','Calle pradillo 2',4),
	 ('Arnedo SM','sen_masc','Arnedo','Pol. Delgado Calvete ','calle del rio',4),
	 ('Alfaro Twin SM','sen_masc','Alfaro','Del colegio','calle colegio',4);


INSERT INTO competiciones (competicion,descripcion) VALUES
	 ('liga','Liga'),
	 ('copa','Copa'),
	 ('playoffs','Playoffs'),
	 ('finalfour','Fina Four');

	
INSERT INTO partidos (temporada,categoria,competicion,jornada,equipo1,equipo2,fecha,municipio,pabellon,direccion) VALUES
	 (4,'jun_fem','liga',1,1,3,'2023-05-30 12:11:06','Najera','Multiusos','Calle pradillo 2'),
	 (4,'sen_masc','copa',2,6,7,'2023-05-13 13:35:11','Arnedo','Pol. Delgado Calvete ','calle del rio');
	
INSERT INTO designaciones (fecha,id_partido,id_arbitro1,id_arbitro2,id_arbitro3,id_oficial1,id_oficial2,id_oficial3,id_oficial4) VALUES
	 ('2023-05-19',4,4,6,NULL,3,5,NULL,NULL),
	 ('2023-05-26',8,4,NULL,NULL,3,NULL,NULL,NULL);

	

select * from designaciones d  ;
select * from usuarios;
select  * from categorias ;
select  * from competiciones c ;

select * from temporada;

select * from equipos e ;

INSERT INTO intranet.temporada ()
