DROP TABLE cliente IF EXISTS;
DROP TABLE reserva IF EXISTS;
DROP TABLE HABITACION IF EXISTS;
DROP TABLE hotel IF EXISTS;

CREATE TABLE cliente (
    idCliente         INTEGER IDENTITY PRIMARY KEY,
    nombre	 		VARCHAR(50),
    rut	 		    VARCHAR(9),
    fechaNacimiento	DATE,
    telefono			VARCHAR(11),
    correoElectronico	VARCHAR(50),
    contrasena		VARCHAR(20)
);


CREATE TABLE hotel (
	idHotel             INTEGER IDENTITY PRIMARY KEY,
	nombre              VARCHAR(45),
	nroHabitaciones     INTEGER,
	direccion           VARCHAR (45),
	contactoTelefonico  VARCHAR (11),
	contactoCorreo      VARCHAR (45),
	contrasena          VARCHAR (45)
);

CREATE TABLE HABITACION (
	idHabitacion        INTEGER IDENTITY PRIMARY KEY,
	nroHabitacion       VARCHAR(4),
	precioHabitacion    INTEGER,
	pisoHabitacion	    INTEGER,
	enUso               INTEGER,
	idHotel             INTEGER NOT NULL
);

ALTER TABLE HABITACION ADD CONSTRAINT fk_Habitacion_Hotel FOREIGN KEY (idHotel) REFERENCES hotel (idHotel);


CREATE TABLE reserva (
	idReserva		INTEGER IDENTITY PRIMARY KEY,
	fechaInicio 	DATE,
	montoFinal      INTEGER,
	fechaTermino 	DATE,
	idCliente	    INTEGER NOT NULL,
	idHabitacion    INTEGER NOT NULL
);
ALTER TABLE reserva ADD CONSTRAINT fk_Reserva_Cliente FOREIGN KEY (idCliente) REFERENCES cliente (idCliente);
ALTER TABLE reserva ADD CONSTRAINT fk_Reserva_Habitacion FOREIGN KEY (idHabitacion) REFERENCES HABITACION (idHabitacion);



