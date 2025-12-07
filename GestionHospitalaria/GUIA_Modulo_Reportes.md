# 📊 Módulo de Reportes - Guía Completa

## ✅ Archivos Creados

### 1. Modelo
- ✅ `src/Modelo/Reporte.java` - POJO con estadísticas

### 2. Consultas
- ✅ `src/Modelo/ConsultasReporte.java` - DAO para obtener estadísticas

### 3. Controlador
- ✅ `src/Controlador/CtrlReporte.java` - Lógica del formulario de reportes

### 4. Vista
- ✅ `src/Vista/frmReporte.java` - Formulario completo con:
  - Filtros de fecha
  - Estadísticas de citas (atendidas, canceladas, ausentes)
  - Botones de acción

---

## 🚀 Cómo Usar el Módulo de Reportes

### Opción 1: Probar Directamente
```java
// En cualquier parte del código (o en main)
Reporte modelo = new Reporte();
ConsultasReporte consultas = new ConsultasReporte();
frmReporte vista = new frmReporte();
CtrlReporte ctrl = new CtrlReporte(modelo, consultas, vista);
ctrl.iniciar();
vista.setVisible(true);
```

### Opción 2: Integrar en frmSistema

#### 2.1 Agregar Botón en frmSistema (Visualmente en NetBeans)

1. Abre `frmSistema.java` en el editor visual
2. Agrega un **JButton** en el toolbar
3. Propiedades:
   - **Variable Name**: `btnReportes`
   - **text**: `Reportes`
   - **icon**: (opcional) ícono de gráfica
   - **background**: Color según tu diseño

#### 2.2 Hacer el Botón Público

En la sección de variables de `frmSistema.java`:
```java
public javax.swing.JButton btnReportes;
```

#### 2.3 Actualizar ctrlSistema.java

**En el constructor**, agregar listener:
```java
public ctrlSistema(frmSistema frm, Usuario usuario) {
    this.frm = frm;
    this.usuario = usuario;
    
    // Listeners existentes...
    this.frm.btnSalir.addActionListener(this);
    this.frm.btnInicio.addActionListener(this);
    // ... otros listeners
    
    // AGREGAR ESTO:
    this.frm.btnReportes.addActionListener(this);
}
```

**En aplicarPermisos()**, mostrar botón según rol:
```java
private void aplicarPermisos() {
    String rol = usuario.getNombre_rol();
    
    // Ocultar todos
    frm.btnPacientes.setVisible(false);
    frm.btnMedicos.setVisible(false);
    frm.btnCitas.setVisible(false);
    frm.btnExpedientes.setVisible(false);
    frm.btnAgenda.setVisible(false);
    frm.btnReportes.setVisible(false); // AGREGAR
    
    if ("Administrador".equalsIgnoreCase(rol)) {
        frm.btnPacientes.setVisible(true);
        frm.btnMedicos.setVisible(true);
        frm.btnExpedientes.setVisible(true);
        frm.btnReportes.setVisible(true); // AGREGAR - Admin ve reportes
    } 
    // ... resto del código
}
```

**En actionPerformed()**, manejar el evento:
```java
@Override
public void actionPerformed(ActionEvent e) {
    // ... otros if existentes
    
    // AGREGAR ESTO:
    if (e.getSource() == frm.btnReportes) {
        // Patrón universitario MVC
        Reporte modelo = new Reporte();
        ConsultasReporte consultas = new ConsultasReporte();
        frmReporte vista = new frmReporte();
        CtrlReporte ctrl = new CtrlReporte(modelo, consultas, vista);
        ctrl.iniciar();
        vista.setVisible(true);
    }
}
```

**Agregar importaciones** al inicio de `ctrlSistema.java`:
```java
import Modelo.Reporte;
import Modelo.ConsultasReporte;
import Vista.frmReporte;
```

---

## 📊 Funcionalidades del Reporte

### 1. Ver Estadísticas Generales
- Al abrir el formulario, se cargan automáticamente las estadísticas de **todas** las citas
- Muestra:
  - **Personas Atendidas**: Citas con estado "Realizada"
  - **Citas Canceladas**: Citas con estado "Cancelada"
  - **Personas Ausentes**: Citas con estado "Ausente"

### 2. Filtrar por Rango de Fechas
1. Ingresa **Fecha Inicio**: formato `DD-MM-YYYY` (ej: `01-01-2025`)
2. Ingresa **Fecha Fin**: formato `DD-MM-YYYY` (ej: `31-12-2025`)
3. Click en **Filtrar**
4. Se actualizan las estadísticas solo para ese rango

### 3. Actualizar Datos
- Click en **Actualizar** para recargar las estadísticas generales (sin filtros)

### 4. Cerrar
- Click en **Cerrar** para cerrar el formulario

---

## 🗄️ Estructura de Base de Datos

El reporte usa la tabla **`cita`** que ya existe en tu base de datos:

```sql
SELECT 
    SUM(CASE WHEN estado = 'Realizada' THEN 1 ELSE 0 END) as atendidas,
    SUM(CASE WHEN estado = 'Cancelada' THEN 1 ELSE 0 END) as canceladas,
    SUM(CASE WHEN estado = 'Ausente' THEN 1 ELSE 0 END) as ausentes
FROM cita;
```

### Con Filtro de Fechas:
```sql
SELECT 
    SUM(CASE WHEN estado = 'Realizada' THEN 1 ELSE 0 END) as atendidas,
    SUM(CASE WHEN estado = 'Cancelada' THEN 1 ELSE 0 END) as canceladas,
    SUM(CASE WHEN estado = 'Ausente' THEN 1 ELSE 0 END) as ausentes
FROM cita
WHERE DATE(fecha_hora) BETWEEN '2025-01-01' AND '2025-12-31';
```

---

## 🧪 Datos de Prueba

Si no tienes citas en la base de datos, puedes insertar algunas de prueba:

```sql
-- Asumiendo que tienes id_paciente=1 e id_medico=1

-- Citas atendidas
INSERT INTO cita (id_paciente, id_medico, fecha_hora, motivo, estado)
VALUES 
(1, 1, '2025-11-01 10:00:00', 'Consulta general', 'Realizada'),
(1, 1, '2025-11-05 14:00:00', 'Seguimiento', 'Realizada'),
(1, 1, '2025-11-10 09:00:00', 'Revisión', 'Realizada');

-- Citas canceladas
INSERT INTO cita (id_paciente, id_medico, fecha_hora, motivo, estado)
VALUES 
(1, 1, '2025-11-15 11:00:00', 'Check-up', 'Cancelada'),
(1, 1, '2025-11-20 16:00:00', 'Consulta', 'Cancelada');

-- Citas con ausencias
INSERT INTO cita (id_paciente, id_medico, fecha_hora, motivo, estado)
VALUES 
(1, 1, '2025-11-25 10:30:00', 'Revisión', 'Ausente');
```

Después de insertar, al abrir el reporte deberías ver:
- **Personas Atendidas**: 3
- **Citas Canceladas**: 2
- **Personas Ausentes**: 1

---

## ✅ Verificación del Patrón Universitario

| Requisito | Estado |
|-----------|--------|
| Modelo POJO (Reporte.java) | ✅ |
| Consultas extend Conexion | ✅ |
| try-catch-finally (NO try-with-resources) | ✅ |
| Controlador recibe modelo, consultas, vista | ✅ |
| Método iniciar() | ✅ |
| implements ActionListener | ✅ |
| Cierre manual de conexiones | ✅ |

---

## 🎨 Captura de Pantalla del Resultado

```
┌────────────────────────────────────────────────┐
│           Reporte de Citas                     │
├────────────────────────────────────────────────┤
│  ┌─ Filtrar por Fecha ────────────────────┐   │
│  │ Fecha Inicio: [01-01-2025]             │   │
│  │ Fecha Fin:    [31-12-2025]  [Filtrar]  │   │
│  └────────────────────────────────────────┘   │
│  ┌─ Estadísticas de Citas ────────────────┐   │
│  │                                         │   │
│  │  Personas Atendidas:           150      │   │
│  │  Citas Canceladas:              25      │   │
│  │  Personas Ausentes:             10      │   │
│  │                                         │   │
│  └────────────────────────────────────────┘   │
│                                                 │
│          [Actualizar]      [Cerrar]             │
└────────────────────────────────────────────────┘
```

---

## 🔧 Posibles Mejoras Futuras

1. **Exportar a PDF/Excel**: Agregar botón para exportar estadísticas
2. **Gráficas**: Mostrar gráfico de pastel o barras
3. **Reportes por Médico**: Filtrar estadísticas por médico específico
4. **Reportes por Especialidad**: Agrupar por especialidad médica
5. **Histórico**: Comparar estadísticas de diferentes períodos

---

## 📝 Resumen

✅ **Modelo**: `Reporte.java` - Almacena las estadísticas  
✅ **Consultas**: `ConsultasReporte.java` - Obtiene datos de la BD  
✅ **Controlador**: `CtrlReporte.java` - Maneja la lógica  
✅ **Vista**: `frmReporte.java` - Interfaz gráfica  
✅ **Patrón MVC**: Cumple 100% con el estándar universitario  
✅ **Funcional**: Listo para usar  

---

**¡El módulo de reportes está completo y listo para integrar! 🎉**
