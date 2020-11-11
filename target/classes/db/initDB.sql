DROP TABLE Cliente IF EXISTS;
DROP TABLE Reserva IF EXISTS;
DROP TABLE Habitacion IF EXISTS;
DROP TABLE Hotel IF EXISTS;

CREATE TABLE Cliente (
    idCliente         INTEGER IDENTITY PRIMARY KEY AUTO_INCREMENT,
    nombre	 		VARCHAR(50),
    rut	 		    VARCHAR(9),
    fechaNacimiento	DATE,
    telefono			VARCHAR(11),
    correoElectronico	VARCHAR(50),
    contrasena		VARCHAR(20),
);


CREATE TABLE Hotel (
	idHotel             INTEGER IDENTITY PRIMARY KEY AUTO_INCREMENT,
	nombre              VARCHAR(45),
	nroHabitaciones     INTEGER,
	direccion           VARCHAR (45),
	contactoTelefonico  VARCHAR (11),
	contactoCorreo      VARCHAR (45),
	contrasena          VARCHAR (45),
);

CREATE TABLE Habitacion (
	idHabitacion        INTEGER IDENTITY PRIMARY KEY AUTO_INCREMENT,
	nroHabitacion       VARCHAR(4),
	precioHabitacion    INTEGER,
	pisoHabitacion	    VARCHAR(2),
	enUso               INTEGER,
	idHotel             INTEGER NOT NULL
);

ALTER TABLE Habitacion ADD CONSTRAINT fk_Habitacion-Hotesl FOREIGN KEY (idHotel) REFERENCES Hotel (idHotel);


CREATE TABLE Reserva (
	idReserva		INTEGER IDENTITY PRIMARY KEY AUTO_INCREMENT,
	fechaInicio 	DATE,
	montoFinal      INTEGER,
	fechaTermino 	DATE,
	idCliente	    INTEGER NOT NULL,
	idHabitacion    INTEGER NOT NULL
);
ALTER TABLE Reserva ADD CONSTRAINT fk_Reserva_Cliente FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente);
ALTER TABLE Reserva ADD CONSTRAINT fk_Reserva_Habitacion FOREIGN KEY (idHabitacion) REFERENCES Habitacion (idHabitacion);



