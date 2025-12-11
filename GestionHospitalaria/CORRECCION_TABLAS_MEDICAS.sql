USE hospital;

-- Asegurar tabla EXPEDIENTE
CREATE TABLE IF NOT EXISTS expediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Corregir tabla DIAGNOSTICO
-- Asegurar que existan las columnas requeridas por el sistema
ALTER TABLE diagnostico 
ADD COLUMN IF NOT EXISTS id_expediente INT,
ADD COLUMN IF NOT EXISTS id_medico INT,
ADD COLUMN IF NOT EXISTS padecimientos TEXT,
ADD COLUMN IF NOT EXISTS diagnostico TEXT, -- Columna redundante que causaba problemas, pero la agregamos para cumplir con el código
ADD COLUMN IF NOT EXISTS notas TEXT,
ADD COLUMN IF NOT EXISTS fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Corregir tabla RECETA
ALTER TABLE receta 
ADD COLUMN IF NOT EXISTS id_expediente INT,
ADD COLUMN IF NOT EXISTS id_medico INT,
ADD COLUMN IF NOT EXISTS medicamentos TEXT,
ADD COLUMN IF NOT EXISTS instrucciones TEXT,
ADD COLUMN IF NOT EXISTS fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Corregir tabla EXAMEN
ALTER TABLE examen 
ADD COLUMN IF NOT EXISTS id_expediente INT,
ADD COLUMN IF NOT EXISTS id_medico INT,
ADD COLUMN IF NOT EXISTS tipo_examen VARCHAR(255),
ADD COLUMN IF NOT EXISTS resultados TEXT,
ADD COLUMN IF NOT EXISTS fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- IMPORTANTE: Si las columnas 'id_expediente' no son llaves foráneas, podemos intentar crearlas
-- Pero primero debemos asegurarnos de que la columna exista.
-- (El ADD COLUMN arriba lo asegura).

SELECT 'Tablas Médicas corregidas correctamente' AS mensaje;
