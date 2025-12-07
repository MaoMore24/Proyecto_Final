# 📁 Estructura del Proyecto - Sistema de Gestión Hospitalaria

## 🌳 Árbol de Directorios

```
GestionHospitalaria/
│
├── 📄 GUIA_Permisos_Botones.md
├── 📄 GUIA_frmAgregarUsuarios.md
├── 📄 GUIA_Enfermeria_Laboratorio.md       ← ⭐ NUEVO
├── 📄 RESUMEN_Integracion.md               ← ⭐ NUEVO
│
├── 🗄️ hospital.sql
├── 🗄️ agregar_tablas_enfermeria_laboratorio.sql  ← ⭐ NUEVO
├── 🗄️ limpiar_usuarios.sql
├── 🗄️ verificar_usuarios.sql
├── ⚙️ compilar.bat                          ← ⭐ NUEVO
│
└── src/
    │
    ├── Controlador/
    │   ├── CtrlAgenda.java
    │   ├── CtrlAgendarCita.java
    │   ├── CtrlEnfermeria.java          ← ⭐ NUEVO
    │   ├── CtrlExpediente.java
    │   ├── CtrlHorario.java
    │   ├── CtrlLaboratorio.java         ← ⭐ NUEVO
    │   ├── CtrlMedico.java
    │   ├── CtrlRegistrarUsuario.java
    │   ├── CtrlRegistroPaciente.java
    │   ├── ctrlLogin.java
    │   └── ctrlSistema.java             ← ⭐ MODIFICADO
    │
    ├── Modelo/
    │   ├── Cita.java
    │   ├── Conexion.java
    │   ├── ConsultasCita.java
    │   ├── ConsultasEnfermeria.java     ← ⭐ NUEVO
    │   ├── ConsultasExpediente.java
    │   ├── ConsultasHorario.java
    │   ├── ConsultasLaboratorio.java    ← ⭐ NUEVO
    │   ├── ConsultasMedico.java
    │   ├── ConsultasPaciente.java
    │   ├── ConsultasSistema.java
    │   ├── ConsultasUsuario.java
    │   ├── Diagnostico.java
    │   ├── Enfermeria.java              ← ⭐ NUEVO
    │   ├── Examen.java
    │   ├── HorarioMedico.java
    │   ├── Medico.java
    │   ├── Paciente.java
    │   ├── Receta.java
    │   ├── ResultadoLaboratorio.java    ← ⭐ NUEVO
    │   └── Usuario.java
    │
    └── Vista/
        ├── frmAgenda.java / .form
        ├── frmAgendarCitas.java / .form
        ├── frmAgregarUsuarios.java / .form
        ├── frmEnfermeria.java / .form   ← ✅ INTEGRADO
        ├── frmExpediente.java / .form
        ├── frmGestionHorarios.java / .form
        ├── frmLaboratorio.java / .form  ← ✅ INTEGRADO
        ├── frmLogin.java / .form
        ├── frmMedicos.java / .form
        ├── frmRegistroPacientes.java / .form
        ├── frmReporte.java / .form
        └── frmSistema.java / .form
```

---

## 📊 Resumen de Archivos por Tipo

### ⭐ Archivos NUEVOS (10)

#### Controladores (2)
- `CtrlEnfermeria.java`
- `CtrlLaboratorio.java`

#### Modelos (4)
- `Enfermeria.java`
- `ResultadoLaboratorio.java`
- `ConsultasEnfermeria.java`
- `ConsultasLaboratorio.java`

#### Documentación (3)
- `GUIA_Enfermeria_Laboratorio.md`
- `RESUMEN_Integracion.md`
- `ESTRUCTURA_Proyecto.md` (este archivo)

#### Scripts (1)
- `agregar_tablas_enfermeria_laboratorio.sql`
- `compilar.bat`

### ⭐ Archivos MODIFICADOS (1)
- `ctrlSistema.java` (integración de nuevos módulos)

### ✅ Archivos INTEGRADOS (2)
- `frmEnfermeria.java / .form` (ya existían, ahora funcionales)
- `frmLaboratorio.java / .form` (ya existían, ahora funcionales)

---

## 🔗 Relaciones entre Componentes

### Módulo de Enfermería
```
frmEnfermeria (Vista)
      ↓
CtrlEnfermeria (Controlador)
      ↓
ConsultasEnfermeria (Modelo - DAO)
      ↓
Enfermeria (Modelo - POJO)
      ↓
Base de Datos (tabla: enfermeria)
```

### Módulo de Laboratorio
```
frmLaboratorio (Vista)
      ↓
CtrlLaboratorio (Controlador)
      ↓
ConsultasLaboratorio (Modelo - DAO)
      ↓
ResultadoLaboratorio (Modelo - POJO)
      ↓
Base de Datos (tabla: laboratorio)
```

### Integración con el Sistema Principal
```
frmSistema (Vista Principal)
      ↓
ctrlSistema (Controlador Principal)
      ↓
aplicarPermisos() → Valida rol de usuario
      ↓
actionPerformed() → Abre módulo específico
      ↓
      ├─► Rol "Enfermero" → CtrlEnfermeria → frmEnfermeria
      └─► Rol "Laboratorio" → CtrlLaboratorio → frmLaboratorio
```

---

## 📈 Estadísticas del Proyecto

| Categoría | Cantidad |
|-----------|----------|
| **Controladores** | 11 (+2 nuevos) |
| **Modelos POJO** | 10 (+2 nuevos) |
| **Modelos DAO** | 10 (+2 nuevos) |
| **Vistas (JFrame)** | 12 |
| **Scripts SQL** | 4 (+1 nuevo) |
| **Documentación** | 5 (+2 nuevos) |
| **Total Archivos** | ~60 archivos |

---

## 🎯 Puntos de Entrada del Sistema

### 1. Inicio de la Aplicación
**Archivo**: `src/gestionhospitalaria/GestionHospitalaria.java` (Main)
```
Main → frmLogin → ctrlLogin → frmSistema → ctrlSistema
```

### 2. Login de Usuario
**Vista**: `frmLogin`
**Controlador**: `ctrlLogin`
```
Usuario ingresa credenciales → Validación → Redirect según rol
```

### 3. Panel Principal (Sistema)
**Vista**: `frmSistema`
**Controlador**: `ctrlSistema`
```
Muestra botones según rol → Usuario hace click → Abre módulo
```

### 4. Módulos Específicos
```
├─► Administrador
│   ├── Registro (frmAgregarUsuarios)
│   ├── Médicos (frmMedicos)
│   └── Expedientes (frmExpediente)
│
├─► Paciente
│   └── Citas (frmAgendarCitas)
│
├─► Médico
│   ├── Agenda (frmAgenda)
│   └── Expedientes (frmExpediente)
│
├─► Enfermero                    ← ⭐ NUEVO
│   └── Expedientes/Enfermería (frmEnfermeria)
│
└─► Laboratorio                  ← ⭐ NUEVO
    └── Expedientes/Laboratorio (frmLaboratorio)
```

---

## 🗄️ Esquema de Base de Datos

### Tablas Existentes
- `rol`
- `usuario`
- `especialidad`
- `medico`
- `paciente`
- `horario_medico`
- `cita`
- `expediente`
- `diagnostico`
- `receta`
- `examen`

### ⭐ Tablas NUEVAS
- `enfermeria` ← Para registros de enfermería
- `laboratorio` ← Para resultados de laboratorio

---

## 🔐 Sistema de Roles

| Rol | ID | Acceso a Módulos |
|-----|----|--------------------|
| Administrador | 1 | Registro, Médicos, Expedientes |
| Medico | 2 | Agenda, Expedientes |
| Paciente | 3 | Citas |
| Enfermero | 4 | **Enfermería** ⭐ |
| Laboratorio | 5 | **Laboratorio** ⭐ |

---

## 📝 Convenciones del Proyecto

### Nomenclatura de Archivos
- **Vistas**: `frm[Nombre].java`
- **Controladores**: `Ctrl[Nombre].java`
- **Modelos POJO**: `[Nombre].java`
- **Modelos DAO**: `Consultas[Nombre].java`

### Paquetes
- `Vista` → Formularios JFrame
- `Controlador` → Lógica de control (Listeners)
- `Modelo` → Clases de datos y acceso a BD

### Patrón MVC
```
Vista ←→ Controlador ←→ Modelo
```

---

## 🚀 Cómo Navegar el Proyecto

1. **Para entender el flujo**: Empieza en `ctrlLogin.java`
2. **Para ver los permisos**: Revisa `ctrlSistema.java` método `aplicarPermisos()`
3. **Para ver la estructura BD**: Abre `hospital.sql`
4. **Para probar Enfermería**: Ejecuta SQL, crea usuario, login, click en "Expedientes"
5. **Para probar Laboratorio**: Mismo proceso con rol "Laboratorio"

---

**Última actualización**: 6 de Diciembre, 2025  
**Versión del Proyecto**: 1.0  
**Estado**: ✅ Completamente Integrado
