# 📋 Configuración de Botones por Perfil en frmSistema

## 📍 Ubicación del Código
**Archivo:** `src/Controlador/ctrlSistema.java`
**Método:** `aplicarPermisos()` (líneas 47-93)

## 🎯 Botones Disponibles en el Menú Lateral

| Botón | Descripción |
|-------|-------------|
| `btnInicio` | Página de inicio (siempre visible) |
| `btnPacientes` | Gestión de usuarios (registrar médicos, enfermeros, etc.) |
| `btnMedicos` | Gestión de médicos |
| `btnCitas` | Agendar citas médicas |
| `btnAgenda` | Ver agenda de citas (para médicos) |
| `btnExpedientes` | Ver expedientes médicos |
| `btnSalir` | Cerrar sesión (siempre visible) |

## 👥 Permisos por Rol

### 1️⃣ **ADMINISTRADOR**
```
✅ btnInicio
✅ btnPacientes    → Registrar usuarios (médicos, enfermeros, laboratorio)
✅ btnMedicos      → Gestionar médicos
✅ btnExpedientes  → Ver todos los expedientes
❌ btnCitas        → No agenda citas
❌ btnAgenda       → No tiene agenda propia
✅ btnSalir
```

### 2️⃣ **PACIENTE**
```
✅ btnInicio
❌ btnPacientes
❌ btnMedicos
✅ btnCitas        → Agendar citas médicas
❌ btnAgenda
❌ btnExpedientes
✅ btnSalir
```

### 3️⃣ **MÉDICO**
```
✅ btnInicio
❌ btnPacientes
❌ btnMedicos
❌ btnCitas
✅ btnAgenda       → Ver su agenda de citas
✅ btnExpedientes  → Ver expedientes de pacientes
✅ btnSalir
```

### 4️⃣ **ENFERMERO**
```
✅ btnInicio
❌ btnPacientes
❌ btnMedicos
❌ btnCitas
❌ btnAgenda
✅ btnExpedientes  → Ver expedientes
✅ btnSalir
```

### 5️⃣ **LABORATORIO**
```
✅ btnInicio
❌ btnPacientes
❌ btnMedicos
❌ btnCitas
❌ btnAgenda
✅ btnExpedientes  → Ver expedientes (para agregar resultados)
✅ btnSalir
```

## 🔧 Cómo Modificar los Permisos

Para cambiar qué botones ve cada perfil, edita el método `aplicarPermisos()` en `ctrlSistema.java`:

### Ejemplo: Permitir que Enfermeros agenden citas

```java
else if ("Enfermero".equalsIgnoreCase(rol)) {
    frm.btnExpedientes.setVisible(true);
    frm.btnCitas.setVisible(true);  // ← AGREGAR ESTA LÍNEA
}
```

### Ejemplo: Ocultar Expedientes al Administrador

```java
if ("Administrador".equalsIgnoreCase(rol)) {
    frm.btnPacientes.setVisible(true);
    frm.btnMedicos.setVisible(true);
    // frm.btnExpedientes.setVisible(true); ← COMENTAR O ELIMINAR
}
```

## 📝 Notas Importantes

1. **Por defecto:** TODOS los botones se ocultan al inicio (líneas 50-54)
2. **Luego:** Se muestran solo los botones permitidos según el rol
3. **btnInicio y btnSalir:** Siempre visibles (manejados por el formulario)
4. **Sensible a mayúsculas:** Los roles en la BD deben coincidir exactamente:
   - "Administrador" (con mayúscula inicial)
   - "Paciente"
   - "Medico" (sin tilde)
   - "Enfermero"
   - "Laboratorio"

## 🎨 Renombrar Botones en la Interfaz

Si quieres cambiar el texto que aparece en los botones (ej: "Usuarios" en lugar de "Pacientes"):

1. Abre `frmSistema.java` en modo **Design**
2. Selecciona el botón
3. En Properties, cambia la propiedad `text`

**Importante:** Esto solo cambia el TEXTO visible, el nombre del componente (`btnPacientes`) sigue igual en el código.
