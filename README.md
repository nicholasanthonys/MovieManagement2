# Movie Management
#Tugas Movie Management untuk matkul Pemrograman berbasis Platform
Menggunakan JavaFX  database PostgreSQL dengan JPA, dan konsep DAO

Database : MovieManagementDB

Tables : 
CREATE TABLE PERSON (
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE MOVIE(
    id SERIAL NOT NULL,
    name VARCHAR NOT NULL,
    poster bytea,
    releasedate VARCHAR DEFAULT '-',
    PRIMARY KEY (id)
);

CREATE TABLE DIRECTOR(
    id  SERIAL NOT NULL,
    name VARCHAR NOT NULL,
    picture bytea,
    shortbio VARCHAR DEFAULT '-',
    birthdate VARCHAR DEFAULT '-',
    occupation VARCHAR DEFAULT '-',
    PRIMARY KEY (id)
);

CREATE TABLE daftarmoviefavorite(
    username VARCHAR NOT NULL,
    idmovie INT NOT NULL,
    PRIMARY KEY(username,idmovie),
    FOREIGN KEY (username) REFERENCES person(username),
    FOREIGN KEY (idmovie) REFERENCES movie(id)
);

CREATE TABLE daftarmoviedirected(
    idmovie INT NOT NULL,
    iddirector INT NOT NULL,
    PRIMARY KEY(idmovie,iddirector),
    FOREIGN KEY (idmovie) REFERENCES movie(id),
    FOREIGN KEY (iddirector) REFERENCES director(id)
);
