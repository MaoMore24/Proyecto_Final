# Guía de Integración - Módulos de Enfermería y Laboratorio

## 📋 Resumen
Se han integrado exitosamente los formularios `frmEnfermeria` y `frmLaboratorio` al sistema hospitalario siguiendo el patrón MVC.

## 🗂️ Archivos Creados

### Modelo (src/Modelo/)
1. **Enfermeria.java** - Clase que representa un registro de enfermería
2. **ResultadoLaboratorio.java** - Clase que representa un resultado de laboratorio
3. **ConsultasEnfermeria.java** - Maneja las consultas SQL para enfermería
4. **ConsultasLaboratorio.java** - Maneja las consultas SQL para laboratorio

### Controlador (src/Controlador/)
1. **CtrlEnfermeria.java** - Controlador del formulario de enfermería
2. **CtrlLaboratorio.java** - Controlador del formulario de laboratorio

### Vista (src/Vista/)
- `frmEnfermeria.java/.form` - Ya existente (generado por ti)
- `frmLaboratorio.java/.form` - Ya existente (generado por ti)

### Base de Datos
- **agregar_tablas_enfermeria_laboratorio.sql** - Script para crear las tablas necesarias

## 🔧 Integración en el Sistema

### Modificaciones en ctrlSistema.java
El controlador del sistema fue actualizado para:

1. **Importar las nuevas clases** (líneas 7-12)
2. **Aplicar permisos por rol** (método `aplicarPermisos()`)
   - Enfermeros y técnicos de laboratorio ahora ven el botón "Expedientes"
3. **Abrir formularios específicos** (método `actionPerformed()`)
   - Al presionar "Expedientes", se abre el formulario correspondiente según el rol

## 🗄️ Base de Datos

### Paso 1: Ejecutar el Script SQL
Ejecuta el archivo `agregar_tablas_enfermeria_laboratorio.sql` en tu base de datos MySQL:

```bash
mysql -u root -p hospital < agregar_tablas_enfermeria_laboratorio.sql
```

O desde MySQL Workbench:
1. Abre el archivo SQL
2. Ejecuta el script (Ctrl + Shift + Enter)

### Estructura de las Tablas

#### Tabla `enfermeria`
```sql
- id_enfermeria (INT, PK, AUTO_INCREMENT)
- id_expediente (INT, FK → expediente)
- id_enfermero (INT, FK → usuario)
- expediente_enfermeria (TEXT)
- procedimientos (TEXT)
- medicamentos (TEXT)
- fecha_registro (TIMESTAMP)
```

#### Tabla `laboratorio`
```sql
- id_laboratorio (INT, PK, AUTO_INCREMENT)
- id_expediente (INT, FK → expediente)
- id_tecnico (INT, FK → usuario)
- expediente_laboratorio (TEXT)
- procedimientos (TEXT)
- resultados (TEXT)
- fecha_registro (TIMESTAMP)
```

## 👥 Flujo de Uso por Rol

### Para Enfermeros (rol: "Enfermero")
1. Login al sistema
2. Se muestra el botón "Expedientes" en el panel principal
3. Al hacer clic, se abre `frmEnfermeria`
4. Pueden:
   - Buscar pacientes por nombre
   - Seleccionar un paciente
   - Registrar información de enfermería (expediente, procedimientos, medicamentos)
   - Guardar el registro

### Para Técnicos de Laboratorio (rol: "Laboratorio")
1. Login al sistema
2. Se muestra el botón "Expedientes" en el panel principal
3. Al hacer clic, se abre `frmLaboratorio`
4. Pueden:
   - Buscar pacientes por nombre
   - Seleccionar un paciente
   - Registrar resultados de laboratorio (expediente, procedimientos, resultados)
   - Guardar el registro

## 🔑 Mapeo de Campos

### frmEnfermeria
- `txtPadecimientos` → `expediente_enfermeria`
- `txtExamenFisico` → `procedimientos`
- `txtMedicamentos` → `medicamentos`

### frmLaboratorio
- `txtPadecimientos` → `expediente_laboratorio`
- `txtExamenFisico` → `procedimientos`
- `txtMedicamentos` → `resultados`

> **Nota**: Los nombres de los campos en los formularios podrían actualizarse en el futuro para reflejar mejor su función específica.

## ✅ Pruebas Recomendadas

1. **Crear usuarios de prueba**:
   ```sql
   -- Crear un usuario enfermero
   INSERT INTO usuario (username, password, nombre, apellido, email, id_rol)
   VALUES ('enfermero1', SHA2('password123', 256), 'María', 'González', 'maria@hospital.com', 4);
   
   -- Crear un usuario de laboratorio
   INSERT INTO usuario (username, password, nombre, apellido, email, id_rol)
   VALUES ('lab1', SHA2('password123', 256), 'Carlos', 'Ramírez', 'carlos@hospital.com', 5);
   ```

2. **Hacer login con estos usuarios**
3. **Verificar que aparezca el botón correcto**
4. **Probar la búsqueda de pacientes**
5. **Registrar información y verificar en la base de datos**

## 🔍 Validaciones Implementadas

- No se puede guardar sin seleccionar un paciente
- Se valida que el paciente tenga un expediente creado
- Se requiere al menos un campo con datos para guardar
- Los campos se limpian automáticamente después de guardar

## 📝 Próximos Pasos (Opcional)

Si quieres mejorar aún más estos módulos, podrías:

1. **Actualizar las etiquetas** en los formularios (en el editor de NetBeans) para que sean más descriptivas
2. **Agregar validaciones adicionales** (por ejemplo, formatos específicos)
3. **Implementar historial** (mostrar registros anteriores del paciente)
4. **Agregar reportes** para enfermería y laboratorio

## 🐛 Solución de Problemas

### Error: "No se encontró la tabla enfermeria/laboratorio"
- Asegúrate de haber ejecutado el script SQL

### Error: "El paciente no tiene un expediente creado"
- El paciente debe tener un registro en la tabla `expediente`
- Los médicos crean expedientes al atender pacientes

### El botón "Expedientes" no aparece
- Verifica que el rol del usuario sea exactamente "Enfermero" o "Laboratorio" (con mayúsculas)
- Revisa la tabla `rol` y `usuario` en la base de datos

## 📧 Contacto y Soporte
Si tienes alguna duda o problema, puedes revisar el código en:
- `src/Controlador/CtrlEnfermeria.java`
- `src/Controlador/CtrlLaboratorio.java`
- `src/Modelo/ConsultasEnfermeria.java`
- `src/Modelo/ConsultasLaboratorio.java`

---
**Fecha de integración**: Diciembre 2025
**Autor**: Sistema de Gestión Hospitalaria
