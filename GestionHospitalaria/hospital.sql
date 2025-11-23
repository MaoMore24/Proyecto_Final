-- Base de datos para el Sistema Hospitalario
CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

-- Tabla de Roles
CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE -- 'Administrador', 'Medico', 'Paciente', 'Enfermero', 'Laboratorio'
);

-- Insertar roles por defecto
INSERT INTO rol (nombre) VALUES ('Administrador'), ('Medico'), ('Paciente'), ('Enfermero'), ('Laboratorio');

-- Tabla de Usuarios (Base para todos los actores)
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Debe estar encriptada
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    id_rol INT NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES rol(id)
);

-- Tabla de Especialidades Médicas
CREATE TABLE especialidad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- Tabla de Médicos (Extensión de Usuario)
CREATE TABLE medico (
    id INT PRIMARY KEY, -- Mismo ID que usuario
    id_especialidad INT NOT NULL,
    cedula_profesional VARCHAR(50),
    FOREIGN KEY (id) REFERENCES usuario(id),
    FOREIGN KEY (id_especialidad) REFERENCES especialidad(id)
);

-- Tabla de Pacientes (Extensión de Usuario)
CREATE TABLE paciente (
    id INT PRIMARY KEY, -- Mismo ID que usuario
    fecha_nacimiento DATE,
    telefono VARCHAR(20),
    direccion TEXT,
    FOREIGN KEY (id) REFERENCES usuario(id)
);

-- Tabla de Horarios de Médicos
CREATE TABLE horario_medico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_medico INT NOT NULL,
    dia_semana VARCHAR(15) NOT NULL, -- Lunes, Martes, etc.
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (id_medico) REFERENCES medico(id)
);

-- Tabla de Citas
CREATE TABLE cita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo TEXT,
    estado VARCHAR(20) DEFAULT 'Pendiente', -- Pendiente, Confirmada, Cancelada, Realizada
    FOREIGN KEY (id_paciente) REFERENCES paciente(id),
    FOREIGN KEY (id_medico) REFERENCES medico(id)
);

-- Tabla de Expediente Médico
CREATE TABLE expediente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL UNIQUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_paciente) REFERENCES paciente(id)
);

-- Tabla de Diagnósticos (Parte del expediente)
CREATE TABLE diagnostico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_expediente INT NOT NULL,
    id_medico INT NOT NULL,
    descripcion TEXT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_expediente) REFERENCES expediente(id),
    FOREIGN KEY (id_medico) REFERENCES medico(id)
);

-- Tabla de Recetas
CREATE TABLE receta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_diagnostico INT NOT NULL,
    medicamentos TEXT NOT NULL,
    indicaciones TEXT NOT NULL,
    FOREIGN KEY (id_diagnostico) REFERENCES diagnostico(id)
);

-- Tabla de Exámenes de Laboratorio
CREATE TABLE examen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    tipo_examen VARCHAR(100) NOT NULL,
    resultados TEXT,
    fecha_solicitud DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_resultado DATETIME,
    realizado_por INT, -- ID del usuario de laboratorio
    FOREIGN KEY (id_paciente) REFERENCES paciente(id),
    FOREIGN KEY (realizado_por) REFERENCES usuario(id)
);
