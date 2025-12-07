# ⚡ Inicio Rápido - Módulos de Enfermería y Laboratorio

## 🎯 Objetivo
Poner en funcionamiento los nuevos módulos en **5 minutos**.

---

## ✅ Checklist de 5 Pasos

### 📍 Paso 1: Ejecutar Script SQL (1 minuto)

**Opción A - MySQL Workbench:**
1. Abre MySQL Workbench
2. Abre el archivo: `agregar_tablas_enfermeria_laboratorio.sql`
3. Presiona `Ctrl + Shift + Enter` para ejecutar todo
4. Verifica el mensaje de éxito

**Opción B - Línea de Comandos:**
```bash
mysql -u root -p hospital < agregar_tablas_enfermeria_laboratorio.sql
```

**✅ Verificación:**
```sql
USE hospital;
SHOW TABLES;
-- Deberías ver: enfermeria y laboratorio
```

---

### 📍 Paso 2: Crear Usuarios de Prueba (1 minuto)

Ejecuta en MySQL:

```sql
USE hospital;

-- Usuario Enfermero (username: enfermero1, password: password123)
INSERT INTO usuario (username, password, nombre, apellido, email, id_rol)
VALUES ('enfermero1', SHA2('password123', 256), 'María', 'González', 'maria@hospital.com', 4);

-- Usuario Laboratorio (username: lab1, password: password123)
INSERT INTO usuario (username, password, nombre, apellido, email, id_rol)
VALUES ('lab1', SHA2('password123', 256), 'Carlos', 'Ramírez', 'carlos@hospital.com', 5);

-- Verificar
SELECT username, nombre, apellido, r.nombre as rol 
FROM usuario u 
INNER JOIN rol r ON u.id_rol = r.id 
WHERE u.id_rol IN (4, 5);
```

**✅ Verificación:** Deberías ver los 2 usuarios creados.

---

### 📍 Paso 3: Asegurar que Existe un Paciente con Expediente (1 minuto)

Los módulos necesitan pacientes con expedientes. Verifica:

```sql
-- Ver pacientes con expediente
SELECT p.id_paciente, u.nombre, u.apellido, e.id_expediente
FROM paciente p
INNER JOIN usuario u ON p.id_usuario = u.id_usuario
LEFT JOIN expediente e ON e.id_paciente = p.id_paciente;
```

**Si NO hay pacientes con expediente**, crea uno:

```sql
-- 1. Crear usuario paciente
INSERT INTO usuario (username, password, nombre, apellido, email, id_rol)
VALUES ('paciente1', SHA2('123', 256), 'Juan', 'Pérez', 'juan@email.com', 3);

-- 2. Crear registro de paciente
INSERT INTO paciente (id_usuario, fecha_nacimiento, telefono, direccion)
VALUES (LAST_INSERT_ID(), '1990-01-15', '555-1234', 'Calle Principal 123');

-- 3. Crear expediente para ese paciente
INSERT INTO expediente (id_paciente)
VALUES (LAST_INSERT_ID());

-- 4. Verificar
SELECT * FROM expediente ORDER BY id_expediente DESC LIMIT 1;
```

---

### 📍 Paso 4: Compilar el Proyecto (1 minuto)

**En NetBeans:**
1. Abre el proyecto en NetBeans
2. Click derecho en el proyecto `GestionHospitalaria`
3. Selecciona **"Clean and Build"**
4. Espera a que compile (debería ser exitoso)

**✅ Verificación:** 
- Consola de NetBeans debe mostrar: "BUILD SUCCESSFUL"
- No debe haber errores de compilación

---

### 📍 Paso 5: Probar los Módulos (1 minuto)

#### 🧑‍⚕️ Probar Módulo de Enfermería

1. **Run** el proyecto desde NetBeans
2. En login, ingresa:
   - **Username**: `enfermero1`
   - **Password**: `password123`
3. Click en **Login**
4. En el panel principal, verás el botón **"Expedientes"** (con ícono de folder)
5. Click en **"Expedientes"**
6. Se abrirá el formulario de **Enfermería**
7. En "Buscar nombre:", escribe parte del nombre del paciente (ej: "Juan")
8. Click en **"Buscar"**
9. Selecciona el paciente del combo
10. Llena al menos un campo:
    - Expediente enfermería
    - Procedimientos
    - Medicamentos
11. Click en **"Guardar"**
12. Debería mostrar mensaje de éxito

**✅ Verificar en BD:**
```sql
SELECT * FROM enfermeria ORDER BY id_enfermeria DESC LIMIT 1;
```

---

#### 🔬 Probar Módulo de Laboratorio

1. **Cerrar sesión** (o reinicia la aplicación)
2. En login, ingresa:
   - **Username**: `lab1`
   - **Password**: `password123`
3. Click en **Login**
4. En el panel principal, verás el botón **"Expedientes"**
5. Click en **"Expedientes"**
6. Se abrirá el formulario de **Laboratorio**
7. Repite pasos 7-11 anteriores
8. Click en **"Guardar"**

**✅ Verificar en BD:**
```sql
SELECT * FROM laboratorio ORDER BY id_laboratorio DESC LIMIT 1;
```

---

## 🎉 ¡Listo!

Si completaste todos los pasos, ambos módulos están funcionando correctamente.

---

## 🐛 Solución Rápida de Problemas

### ❌ Error: "El paciente no tiene un expediente creado"
**Solución:** Ejecuta el SQL del Paso 3 para crear un paciente con expediente.

### ❌ Error de compilación
**Solución:** 
1. En NetBeans: Menú → Tools → Palette → Swing/AWT Components
2. Asegúrate de que todas las librerías estén cargadas
3. Intenta "Clean and Build" de nuevo

### ❌ No aparece el botón "Expedientes"
**Solución:** 
1. Verifica el rol en la base de datos:
```sql
SELECT u.username, r.nombre as rol 
FROM usuario u 
INNER JOIN rol r ON u.id_rol = r.id 
WHERE u.username IN ('enfermero1', 'lab1');
```
2. El rol debe ser exactamente "Enfermero" o "Laboratorio"

### ❌ Error al guardar en la BD
**Solución:**
1. Verifica que las tablas existan:
```sql
SHOW TABLES LIKE '%enfermeria%';
SHOW TABLES LIKE '%laboratorio%';
```
2. Si no existen, ejecuta de nuevo el script del Paso 1

---

## 📚 Para Más Información

- **Guía Completa**: `GUIA_Enfermeria_Laboratorio.md`
- **Resumen de Integración**: `RESUMEN_Integracion.md`
- **Estructura del Proyecto**: `ESTRUCTURA_Proyecto.md`

---

## 🎯 Credenciales de Prueba - Resumen

| Usuario | Password | Rol |
|---------|----------|-----|
| `enfermero1` | `password123` | Enfermero |
| `lab1` | `password123` | Laboratorio |
| `paciente1` | `123` | Paciente |

---

**⏱️ Tiempo estimado total: 5 minutos**  
**✅ Estado**: Listo para usar

¡Disfruta del sistema! 🚀
