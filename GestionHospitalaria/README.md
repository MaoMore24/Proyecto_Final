# 🏥 Sistema de Gestión Hospitalaria
## Proyecto Final - Programación

---

## 📌 Descripción del Proyecto
Sistema integral para la gestión de un hospital que permite la administración de:
- ✅ Usuarios y roles
- ✅ Pacientes y médicos
- ✅ Citas médicas
- ✅ Expedientes médicos
- ✅ Registros de enfermería ⭐ **NUEVO**
- ✅ Resultados de laboratorio ⭐ **NUEVO**

---

## 🚀 Inicio Rápido

### Requisitos Previos
- ☕ Java JDK 8+
- 🗄️ MySQL 5.7+
- 🔧 NetBeans IDE

### Instalación en 3 Pasos

1. **Base de Datos**
   ```bash
   mysql -u root -p < hospital.sql
   mysql -u root -p hospital < agregar_tablas_enfermeria_laboratorio.sql
   ```

2. **Compilar**
   - Abrir proyecto en NetBeans
   - Click derecho → Clean and Build

3. **Ejecutar**
   - Click derecho → Run
   - Login con usuario admin (ver documentación)

---

## 📚 Documentación Completa

### 🎯 Para Empezar
- **[INICIO_RAPIDO.md](INICIO_RAPIDO.md)** ← ⭐ **EMPIEZA AQUÍ**
  - Guía de 5 minutos para probar el sistema

### 📖 Guías de Usuario
- **[GUIA_Enfermeria_Laboratorio.md](GUIA_Enfermeria_Laboratorio.md)**
  - Cómo usar los módulos de Enfermería y Laboratorio
  - Configuración y solución de problemas

- **[GUIA_Permisos_Botones.md](GUIA_Permisos_Botones.md)**
  - Sistema de roles y permisos
  - Qué puede hacer cada usuario

- **[GUIA_frmAgregarUsuarios.md](GUIA_frmAgregarUsuarios.md)**
  - Cómo registrar nuevos usuarios en el sistema

### 🏗️ Documentación Técnica
- **[RESUMEN_Integracion.md](RESUMEN_Integracion.md)**
  - Resumen de la integración de módulos nuevos
  - Archivos creados y modificados

- **[ESTRUCTURA_Proyecto.md](ESTRUCTURA_Proyecto.md)**
  - Estructura completa del proyecto
  - Relaciones entre componentes
  - Arquitectura MVC

---

## 👥 Roles del Sistema

| Rol | Funcionalidades |
|-----|-----------------|
| **🔐 Administrador** | Gestión de usuarios, médicos, expedientes |
| **👨‍⚕️ Médico** | Agenda personal, expedientes de pacientes |
| **🧑‍⚕️ Enfermero** | Registros de enfermería en expedientes |
| **🔬 Laboratorio** | Resultados de laboratorio en expedientes |
| **🧑‍🤝‍🧑 Paciente** | Agendar citas médicas |

---

## 🗄️ Base de Datos

### Tablas Principales
- `usuario` - Todos los usuarios del sistema
- `rol` - Roles disponibles (Admin, Médico, Paciente, etc.)
- `paciente` - Información de pacientes
- `medico` - Información de médicos
- `cita` - Citas médicas
- `expediente` - Expedientes médicos
- `diagnostico` - Diagnósticos médicos
- `receta` - Recetas médicas
- `enfermeria` ⭐ **NUEVO** - Registros de enfermería
- `laboratorio` ⭐ **NUEVO** - Resultados de laboratorio

### Scripts SQL
- `hospital.sql` - Estructura base de datos
- `agregar_tablas_enfermeria_laboratorio.sql` - Tablas nuevas
- `limpiar_usuarios.sql` - Limpieza de datos de prueba
- `verificar_usuarios.sql` - Verificación de usuarios

---

## 🏛️ Arquitectura

### Patrón MVC
```
┌─────────┐
│  Vista  │ ← Formularios JFrame (frmXXX.java)
└────┬────┘
     │
┌────▼────────┐
│ Controlador │ ← Lógica de negocio (CtrlXXX.java)
└────┬────────┘
     │
┌────▼────┐
│ Modelo  │ ← Datos y BD (XXX.java, ConsultasXXX.java)
└─────────┘
```

### Estructura de Paquetes
```
src/
├── Controlador/    (10 clases)
├── Modelo/         (20 clases)
└── Vista/          (12 formularios)
```

---

## ⚡ Últimas Actualizaciones

### ✨ Versión 1.1 - Diciembre 2025
- ✅ Módulo de Enfermería integrado
- ✅ Módulo de Laboratorio integrado
- ✅ Documentación completa actualizada
- ✅ Scripts SQL para nuevas tablas
- ✅ Control de permisos por rol mejorado

### 🔧 Archivos Nuevos (11)
1. `CtrlEnfermeria.java` - Controlador de enfermería
2. `CtrlLaboratorio.java` - Controlador de laboratorio
3. `Enfermeria.java` - Modelo de enfermería
4. `ResultadoLaboratorio.java` - Modelo de laboratorio
5. `ConsultasEnfermeria.java` - DAO enfermería
6. `ConsultasLaboratorio.java` - DAO laboratorio
7. `agregar_tablas_enfermeria_laboratorio.sql` - Script SQL
8. `GUIA_Enfermeria_Laboratorio.md` - Documentación
9. `RESUMEN_Integracion.md` - Resumen técnico
10. `ESTRUCTURA_Proyecto.md` - Arquitectura
11. `INICIO_RAPIDO.md` - Guía de inicio

### ♻️ Archivos Modificados (1)
- `ctrlSistema.java` - Integración de nuevos módulos

---

## 🧪 Credenciales de Prueba

### Usuarios por Defecto
```
Usuario: admin
Password: admin123
Rol: Administrador

Usuario: enfermero1
Password: password123
Rol: Enfermero

Usuario: lab1
Password: password123
Rol: Laboratorio
```

> ⚠️ **Nota**: Las contraseñas están encriptadas con SHA-256.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 8+
- **GUI**: Java Swing
- **Base de Datos**: MySQL
- **Patrón**: MVC (Modelo-Vista-Controlador)
- **IDE**: NetBeans
- **Gestión de Dependencias**: Apache Ant

---

## 📞 Soporte y Ayuda

### 🐛 Problemas Comunes
Ver sección de "Solución de Problemas" en:
- [INICIO_RAPIDO.md](INICIO_RAPIDO.md)
- [GUIA_Enfermeria_Laboratorio.md](GUIA_Enfermeria_Laboratorio.md)

### 📧 Contacto
Para dudas o reportar problemas, revisar la documentación técnica o contactar al equipo de desarrollo.

---

## 🎓 Proyecto Académico

**Institución**: [Tu Universidad]  
**Materia**: Programación  
**Semestre**: [Tu Semestre]  
**Año**: 2025

---

## 📄 Licencia

Este proyecto es de uso académico.

---

## 🙏 Agradecimientos

Gracias a todos los que contribuyeron al desarrollo y documentación de este sistema.

---

## 📊 Estado del Proyecto

![Estado](https://img.shields.io/badge/Estado-Completado-success)
![Versión](https://img.shields.io/badge/Versión-1.1-blue)
![Java](https://img.shields.io/badge/Java-8+-orange)
![MySQL](https://img.shields.io/badge/MySQL-5.7+-blue)

**Última actualización**: 6 de Diciembre, 2025

---

<div align="center">

### 🎯 ¡Sistema 100% Funcional!

**[📖 Ver Documentación Completa](INICIO_RAPIDO.md)** | **[🚀 Inicio Rápido](INICIO_RAPIDO.md)**

</div>
