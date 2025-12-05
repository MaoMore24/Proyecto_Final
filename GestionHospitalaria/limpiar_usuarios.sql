-- Script para limpiar usuarios existentes con contraseñas encriptadas
-- Ejecutar SOLO si ya tenías usuarios registrados con contraseñas encriptadas
-- y quieres empezar de nuevo

USE hospital;

-- OPCIÓN 1: Eliminar TODOS los usuarios (excepto administrador si existe)
-- ADVERTENCIA: Esto borrará todos los pacientes y médicos registrados
DELETE FROM diagnostico;
DELETE FROM expediente;
DELETE FROM cita;
DELETE FROM horario_medico;
DELETE FROM paciente;
DELETE FROM medico;
DELETE FROM usuario WHERE id_rol != 1; -- Mantener solo administradores si existen

-- OPCIÓN 2: Si quieres mantener los usuarios pero resetear sus contraseñas
-- Descomenta las siguientes líneas y comenta la OPCIÓN 1
-- UPDATE usuario SET password = 'admin123' WHERE id_rol = 1; -- Cambiar contraseña de administradores
-- UPDATE usuario SET password = 'medico123' WHERE id_rol = 2; -- Cambiar contraseña de médicos
-- UPDATE usuario SET password = 'paciente123' WHERE id_rol = 3; -- Cambiar contraseña de pacientes

-- Verificar que no hay usuarios
SELECT * FROM usuario;
