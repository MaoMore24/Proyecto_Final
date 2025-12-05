-- Script para verificar los datos de usuarios registrados
-- Ejecutar en MySQL Workbench o línea de comandos

USE hospital;

-- Ver todos los usuarios registrados
SELECT 
    u.id,
    u.username,
    u.password AS password_encriptada,
    u.nombre,
    u.apellido,
    u.email,
    r.nombre AS rol
FROM usuario u
INNER JOIN rol r ON u.id_rol = r.id
ORDER BY u.id DESC;

-- Ver pacientes registrados
SELECT 
    u.id,
    u.username,
    u.nombre,
    u.apellido,
    p.fecha_nacimiento,
    p.telefono,
    p.direccion
FROM paciente p
INNER JOIN usuario u ON p.id = u.id
ORDER BY u.id DESC;

-- Para verificar una contraseña específica (reemplaza 'tu_password' con tu contraseña real)
-- La contraseña encriptada debe coincidir con la que está en la columna password
-- Ejemplo: SELECT SHA2('tu_password', 256);
