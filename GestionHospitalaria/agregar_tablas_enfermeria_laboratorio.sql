-- Script para agregar las tablas de Enfermería y Laboratorio
-- al sistema hospitalario existente

USE hospital;

-- Tabla de Registros de Enfermería
CREATE TABLE IF NOT EXISTS enfermeria (
    id_enfermeria INT AUTO_INCREMENT PRIMARY KEY,
    id_expediente INT NOT NULL,
    id_enfermero INT NOT NULL,
    expediente_enfermeria TEXT,
    procedimientos TEXT,
    medicamentos TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_expediente) REFERENCES expediente(id),
    FOREIGN KEY (id_enfermero) REFERENCES usuario(id)
);

-- Tabla de Resultados de Laboratorio
CREATE TABLE IF NOT EXISTS laboratorio (
    id_laboratorio INT AUTO_INCREMENT PRIMARY KEY,
    id_expediente INT NOT NULL,
    id_tecnico INT NOT NULL,
    expediente_laboratorio TEXT,
    procedimientos TEXT,
    resultados TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_expediente) REFERENCES expediente(id),
    FOREIGN KEY (id_tecnico) REFERENCES usuario(id)
);

-- Verificar que los roles existan (deberían estar ya creados)
-- Si no existen, los insertamos
INSERT IGNORE INTO rol (nombre) VALUES ('Enfermero'), ('Laboratorio');

SELECT 'Tablas de Enfermería y Laboratorio creadas correctamente' AS mensaje;
