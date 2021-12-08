DROP TABLE IF EXISTS TBL_USUARIOS;

CREATE TABLE TBL_USUARIOS (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              cedula VARCHAR(25) NOT NULL,
                              name VARCHAR(255) NOT NULL,
                              lastName1 VARCHAR(255) NOT NULL,
                              lastName2 VARCHAR(255) NOT NULL,
                              phone VARCHAR(255) NOT NULL,
                              mail VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS TBL_INVENTARIO;

CREATE TABLE TBL_INVENTARIO (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                modelo VARCHAR(50) NOT NULL,
                                pasajeros INT NOT NULL,
                                precio INT NOT NULL,
                                cantidad int NOT NULL
);

DROP TABLE IF EXISTS TBL_SOLICITUDES;

CREATE TABLE TBL_SOLICITUDES (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 idUsuario INT NOT NULL,
                                 idInventario INT NOT NULL,
                                 monto FLOAT NOT NULL,
                                 status BOOLEAN NOT NULL,
                                 CONSTRAINT FK_idPersona_TBL_Solicitudes FOREIGN KEY (idUsuario) REFERENCES TBL_USUARIOS(id),
                                 CONSTRAINT FK_idInventario_TBL_Inventario FOREIGN KEY (idInventario) REFERENCES TBL_INVENTARIO(id)
);
