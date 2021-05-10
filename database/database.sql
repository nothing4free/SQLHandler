CREATE DATABASE [IF NOT EXISTS] GaleriaDeArte;

CREATE TABLE GaleriaDeArte.Cliente (
 Nombre varchar(100),
 Direccion varchar(100) NULL,
 Gastos FLOAT NULL,
 CHECK (Gastos >= 0)
);

CREATE TABLE GaleriaDeArte.GrupoDeObras (
 Nombre varchar(100) NOT NULL
);

CREATE TABLE GaleriaDeArte.ObraDeArte (
 ID_obra INT NOT NULL,
 Titulo varchar(100) NULL,
 Precio INT NULL,
 Tipo varchar(100) NULL,
 Fecha INT NOT NULL,
 CHECK (Fecha >= 0 AND Fecha <= 2021),
 Artista varchar(100) NULL
);

CREATE TABLE GaleriaDeArte.Sigue (
 Nombre_Artista varchar(100) NOT NULL,
 Nombre_Cliente varchar(100) NOT NULL
);

CREATE TABLE GaleriaDeArte.Interesa (
 Nombre_Cliente varchar(100) NOT NULL,
 Nombre_Grupo varchar(100) NOT NULL
);

CREATE TABLE GaleriaDeArte.Artista (
 Nombre varchar(100) NOT NULL,
 Lugar_Nacimiento varchar(100) NULL,
 Year_Nacimiento INT NOT NULL,
 CHECK (Year_Nacimiento >= 0 AND Year_Nacimiento <= 2021),
 Estilo varchar(100) NULL
);

CREATE TABLE GaleriaDeArte.Pertenece (
 ID_obra INT NOT NULL,
 Nombre_Grupo varchar(100) NOT NULL
);

-- PRIMARY KEYS
ALTER TABLE GaleriaDeArte.Artista ADD CONSTRAINT Artista_PK PRIMARY KEY (Nombre);
ALTER TABLE GaleriaDeArte.ObraDeArte ADD CONSTRAINT ObraDeArte_PK PRIMARY KEY (ID_obra);
ALTER TABLE GaleriaDeArte.GrupoDeObras ADD CONSTRAINT GrupoDeObras_PK PRIMARY KEY (Nombre);
ALTER TABLE GaleriaDeArte.Cliente ADD CONSTRAINT Cliente_PK PRIMARY KEY (Nombre);

-- FOREIGN KEYS
ALTER TABLE GaleriaDeArte.Sigue ADD CONSTRAINT Artista_FK FOREIGN KEY (Nombre_Artista) REFERENCES GaleriaDeArte.Artista(Nombre);
ALTER TABLE GaleriaDeArte.Sigue ADD CONSTRAINT Cliente_FK FOREIGN KEY (Nombre_Cliente) REFERENCES GaleriaDeArte.Cliente(Nombre);

ALTER TABLE GaleriaDeArte.Interesa ADD CONSTRAINT Cliente_Grupo_FK FOREIGN KEY (Nombre_Cliente) REFERENCES GaleriaDeArte.Cliente(Nombre);
ALTER TABLE GaleriaDeArte.Interesa ADD CONSTRAINT Grupo_FK FOREIGN KEY (Nombre_Grupo) REFERENCES GaleriaDeArte.GrupoDeObras(Nombre);

ALTER TABLE GaleriaDeArte.ObraDeArte ADD CONSTRAINT ObraDeArte_FK FOREIGN KEY (Artista) REFERENCES GaleriaDeArte.Artista(Nombre);

ALTER TABLE GaleriaDeArte.Pertenece ADD CONSTRAINT Pertenece_ID_FK FOREIGN KEY (ID_obra) REFERENCES GaleriaDeArte.ObraDeArte(ID_obra);
ALTER TABLE GaleriaDeArte.Pertenece ADD CONSTRAINT Pertenece_Nombre_FK FOREIGN KEY (Nombre_Grupo) REFERENCES GaleriaDeArte.GrupoDeObras(Nombre);

-- INSERTING DATA
INSERT INTO GaleriaDeArte.Artista (Nombre,Lugar_Nacimiento,Year_Nacimiento,Estilo) VALUES
 ('Caspar','Amsterdam',1584,'Surrealismo'),
 ('Dali','Figueras',1904,'Luminismo'),
 ('Frida','México',1907,'Cubismo'),
 ('Grant','Concepcion',1887,'Surrealismo'),
 ('Joan','Alcoy ',1912,'Renacimiento'),
 ('Miguel Angel','Caprese',1475,'Renacimiento'),
 ('Picaso','Malaga',1881,'Academicismo'),
 ('Rafael','Jimenez',1849,'Prerrafaelismo'), ('Rene','Puerto Rico',1978,'Renacimiento'),
 ('Velazquez','Sevilla',1599,'Expresionismo');
 
INSERT INTO GaleriaDeArte.Cliente (Nombre,Direccion,Gastos) VALUES
 ('Alon','5755 Mallard Way',102290.0),
 ('Anissa','157 Schlimgen Court',216463.0),
 ('Fairlie','087 Crownhardt Avenue',812669.0),
 ('Ingaborg','5 Vahlen Court',921688.0),
 ('Jeannette','77 Division Place',616269.0),
 ('Lowe','143 Randy Avenue',763977.0),
 ('Nessa','58442 Iowa Crossing',92554.0),
 ('Page','96 Chinook Center',120689.0),
 ('Rosabelle','83849 Daystar Terrace',114727.0),
 ('Timmie','47 Sage Way',825369.0);
 
INSERT INTO GaleriaDeArte.GrupoDeObras (Nombre) VALUES
 ('Abstractos'),
 ('Bodegones'),
 ('Cubismos'),
 ('NFT'),
 ('Obra del Siglo XIX'),
 ('Obra del siglo XX'),
 ('Obras de Picasso'),
 ('Paisajes'),
 ('Realistas'),
 ('Retratos');
 
INSERT INTO GaleriaDeArte.Interesa (Nombre_Cliente,Nombre_Grupo) VALUES
 ('Alon','Abstractos'),
 ('Anissa','Bodegones'),
 ('Fairlie','Cubismos'),
 ('Ingaborg','NFT'),
 ('Jeannette','Obra del Siglo XIX'),
 ('Lowe','Obra del siglo XX'),
 ('Nessa','Obras de Picasso'),
 ('Page','Paisajes'),
 ('Rosabelle','Realistas'),
 ('Timmie','Retratos');
 
INSERT INTO GaleriaDeArte.ObraDeArte (ID_obra,Titulo,Precio,Tipo,Fecha,Artista) VALUES
 (1,'La Gioconda',247771,'Vittariaceae',1990,'Rene'),
 (2,'Guernica',48632,'Lamiaceae',1986,'Dali'),
 (3,'Las meninas',235527,'Apiaceae',1992,'Frida'),
 (4,'la noche estrellada',158798,'Malvaceae',1978,'Grant'),
 (5,'la ultima cena ',223194,'Asteraceae',1969,'Joan'),
 (6,'el nacimiento de venus',222431,'Catillariaceae',1957,'Miguel Angel'),
 (7,'el beso',1651956,'Brachytheciaceae',1937,'Picaso'),
 (8,'la joven de la perla',617524,'Euphorbiaceae',1980,'Rafael'),
 (9,'la ronda de noche',132288,'Asteraceae',2017,'Caspar'),
 (10,'la ultima cena',383800,'Convolvulaceae',1975,'Velazquez');
 
INSERT INTO GaleriaDeArte.Pertenece (ID_obra,Nombre_Grupo) VALUES
 (1,'Abstractos'),
 (2,'Bodegones'),
 (3,'Cubismos'),
 (4,'NFT'),
 (5,'Obra del Siglo XIX'),
 (6,'Obra del siglo XX'),
 (7,'Obras de Picasso'),
 (8,'Paisajes'),
 (9,'Realistas'),
 (10,'Retratos');
 
INSERT INTO GaleriaDeArte.Sigue (Nombre_Artista,Nombre_Cliente) VALUES
 ('Caspar','Alon'),
 ('Dali','Anissa'),
 ('Frida','Fairlie'),
 ('Grant','Ingaborg'),
 ('Joan','Jeannette'),
 ('Miguel Angel','Lowe'),
 ('Picaso','Nessa'),
 ('Rafael','Page'),
 ('Rene','Rosabelle'),
 ('Velazquez','Timmie');
