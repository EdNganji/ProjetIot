CREATE TABLE IF NOT EXISTS lectures
(
    id serial PRIMARY KEY,
	serNum  varchar(10) NOT NULL,
    date_envoi date NOT NULL DEFAULT CURRENT_DATE,
    heure_envoi time without time zone NOT NULL DEFAULT CURRENT_TIME,
    lecture1 numeric(5, 1) NOT NULL,
    lecture2 integer NOT NULL,
    CONSTRAINT serNum FOREIGN KEY (serNum)
    REFERENCES Appareils (serNum)
);

CREATE TABLE IF NOT EXISTS Appareils
( 
    id serial ,
	serNum varchar(10) NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
	type varchar NOT NULL,
    etatFonct varchar  NOT NULL

);

CREATE TABLE IF NOT EXISTS Utilisateurs
( 
    name character varying NOT NULL,
	password character varying NOT NULL,
	PRIMARY KEY (name, password)

);

DELETE FROM links
WHERE id = 8;

INSERT INTO Appareils (serNum, name, type, etatFonct)
VALUES
    ('SN12345678', 'Appareil 1', 'Type 1', 'Fonctionnel'),
    ('SN98765432', 'Appareil 2', 'Type 2', 'En panne'),
    ('SN45678912', 'Appareil 3', 'Type 1', 'Fonctionnel'),
    ('SN32165498', 'Appareil 4', 'Type 3', 'Fonctionnel'),
    ('SN78945612', 'Appareil 5', 'Type 2', 'En panne'),
    ('SN65432178', 'Appareil 6', 'Type 3', 'Fonctionnel'),
    ('SN23456789', 'Appareil 7', 'Type 1', 'Fonctionnel'),
    ('SN87654321', 'Appareil 8', 'Type 2', 'En panne'),
    ('SN54321987', 'Appareil 9', 'Type 3', 'En panne'),
    ('SN98712345', 'Appareil 10', 'Type 1', 'Fonctionnel');


UPDATE Appareils
SET column1 = ?,
    column2 = ?,
    ...
WHERE id = ?;

CREATE TABLE IF NOT EXISTS Appareils
( 
    id serial PRIMARY KEY ,
	adresseIp character(15) NOT NULL ,
    name character varying NOT NULL,
	type character varying NOT NULL,
    etatFonct varchar  NOT NULL,
    active boolean DEFAULT TRUE

);

CREATE TABLE IF NOT EXISTS Capteur
( 
    id serial PRIMARY KEY ,
    name character varying NOT NULL,
    idApparreil integer NOT NULL,
	typeMesure character varying NOT NULL,
    nbreChannel integer  DEFAULT 1,
    active boolean DEFAULT TRUE,
    CONSTRAINT idApparreil FOREIGN KEY (idApparreil)
    REFERENCES Appareils (id)
);

CREATE TABLE IF NOT EXISTS Actionneur
( 
    id serial PRIMARY KEY ,
    name character varying NOT NULL,
    idApparreil integer NOT NULL,
	typeAction character varying NOT NULL,
    Puissance integer  NOT NULL,
    active boolean DEFAULT TRUE,
    CONSTRAINT idApparreil FOREIGN KEY (idApparreil)
    REFERENCES Appareils (id)
);

CREATE TABLE IF NOT EXISTS Channel
( 
    id serial PRIMARY KEY ,
    name character varying NOT NULL,
	unit character varying NOT NULL,
    active boolean DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS Attributions
( 
    id serial PRIMARY KEY ,
    idCapteur integer NOT NULL ,
    idChannel integer NOT NULL  ,
    active boolean DEFAULT TRUE,
    CONSTRAINT idCapteur FOREIGN KEY (idCapteur)
    REFERENCES Capteur (id),
    CONSTRAINT idChannel FOREIGN KEY (idChannel)
    REFERENCES Channel (id)

);

CREATE TABLE IF NOT EXISTS Lectures
( 
    id serial PRIMARY KEY ,
    idCapteur integer NOT NULL ,
    idChannel integer NOT NULL  ,
    readValue numeric NOT NULL  ,
    date_envoi date NOT NULL DEFAULT CURRENT_DATE,
    heure_envoi time without time zone NOT NULL DEFAULT CURRENT_TIME,
    active boolean DEFAULT TRUE,
    CONSTRAINT idCapteur FOREIGN KEY (idCapteur)
    REFERENCES Capteur (id),
    CONSTRAINT idChannel FOREIGN KEY (idChannel)
    REFERENCES Channel (id)

);