# 📊 Guía Paso a Paso - Crear frmReporte Manualmente en NetBeans

## 🎯 Objetivo
Crear un formulario de reportes que muestre estadísticas de citas médicas:
- ✅ Total de personas atendidas (estado: "Realizada")
- ✅ Total de citas canceladas (estado: "Cancelada")
- ✅ Total de personas ausentes (estado: "Ausente")

---

## 📋 Pasos para Crear el Formulario en NetBeans

### Paso 1: Abrir el Formulario en el Editor Visual

1. En NetBeans, abre el proyecto `GestionHospitalaria`
2. Navega a: `Source Packages → Vista → frmReporte.java`
3. Haz **doble clic** en `frmReporte.java`
4. Se abrirá en el **editor visual** (Design View)

---

### Paso 2: Configurar Propiedades del JFrame

1. Haz clic en el **fondo gris** (el JFrame mismo)
2. En la ventana de **Properties** (panel derecho):
   - **title**: `Reporte de Citas`
   - **defaultCloseOperation**: `DISPOSE_ON_CLOSE` (importante: no EXIT, para no cerrar toda la app)
   - **resizable**: `false` (opcional, para tamaño fijo)

---

### Paso 3: Agregar Componentes - Panel Principal

#### 3.1 Agregar Panel Superior (jPanel1)
1. En **Palette** (panel izquierdo), busca **JPanel** en la sección "Swing Containers"
2. **Arrastra** un JPanel al formulario
3. Propiedades del panel:
   - **Variable Name**: `jPanel1`
   - **border**: Click en [...] → TitledBorder → Title: "Estadísticas de Citas"
   - **background**: Color preferido (ej: blanco)

---

### Paso 4: Agregar Etiquetas (Labels)

Dentro de `jPanel1`, agrega **6 JLabels** (3 para títulos, 3 para valores):

#### Labels de Títulos (Columna Izquierda):
1. **Arrastra** JLabel al panel
   - **Variable Name**: `jLabel1`
   - **text**: `Personas Atendidas:`
   - **font**: Segoe UI, Bold, 14

2. **Arrastra** JLabel
   - **Variable Name**: `jLabel2`
   - **text**: `Citas Canceladas:`
   - **font**: Segoe UI, Bold, 14

3. **Arrastra** JLabel
   - **Variable Name**: `jLabel3`
   - **text**: `Personas Ausentes:`
   - **font**: Segoe UI, Bold, 14

#### Labels de Valores (Columna Derecha):
4. **Arrastra** JLabel
   - **Variable Name**: `lblAtendidas`
   - **text**: `0`
   - **font**: Segoe UI, Plain, 18
   - **foreground**: Verde (0, 153, 0)
   - **horizontalAlignment**: CENTER

5. **Arrastra** JLabel
   - **Variable Name**: `lblCanceladas`
   - **text**: `0`
   - **font**: Segoe UI, Plain, 18
   - **foreground**: Naranja (255, 102, 0)
   - **horizontalAlignment**: CENTER

6. **Arrastra** JLabel
   - **Variable Name**: `lblAusentes`
   - **text**: `0`
   - **font**: Segoe UI, Plain, 18
   - **foreground**: Rojo (204, 0, 0)
   - **horizontalAlignment**: CENTER

---

### Paso 5: Agregar Filtros de Fecha (Opcional pero Recomendado)

#### 5.1 Agregar Panel de Filtros (jPanel2)
1. Arrastra otro **JPanel** arriba de jPanel1
2. **Variable Name**: `jPanel2`
3. **border**: TitledBorder → Title: "Filtrar por Fecha"

#### 5.2 Componentes del Panel de Filtros:

1. **JLabel**: 
   - text: `Fecha Inicio:`
   
2. **JTextField**:
   - **Variable Name**: `txtFechaInicio`
   - **toolTipText**: `Formato: DD-MM-YYYY`
   - **columns**: 10

3. **JLabel**:
   - text: `Fecha Fin:`

4. **JTextField**:
   - **Variable Name**: `txtFechaFin`
   - **toolTipText**: `Formato: DD-MM-YYYY`
   - **columns**: 10

5. **JButton**:
   - **Variable Name**: `btnFiltrar`
   - **text**: `Filtrar`
   - **background**: Color azul (0, 102, 204)
   - **foreground**: Blanco

---

### Paso 6: Agregar Botones de Acción

Debajo del panel principal, agrega:

1. **JButton**:
   - **Variable Name**: `btnActualizar`
   - **text**: `Actualizar`
   - **icon**: (opcional) buscar ícono de refresh
   - **background**: Color verde (0, 153, 51)
   - **foreground**: Blanco

2. **JButton**:
   - **Variable Name**: `btnCerrar`
   - **text**: `Cerrar`
   - **background**: Color gris (153, 153, 153)
   - **foreground**: Blanco

---

### Paso 7: Organizar el Layout

Usa el **GroupLayout** (automático en NetBeans):

1. Selecciona todos los componentes
2. Click derecho → "Auto Set Component Names" (si no lo hiciste manualmente)
3. Ajusta el espaciado arrastrando los componentes para que se vean bien

**Distribución recomendada:**
```
+------------------------------------------+
|  [Panel Filtros]                         |
|  Fecha Inicio: [____] Fecha Fin: [____]  |
|  [Filtrar]                               |
+------------------------------------------+
|  [Panel Estadísticas]                    |
|  Personas Atendidas:          [50]       |
|  Citas Canceladas:            [10]       |
|  Personas Ausentes:           [5]        |
+------------------------------------------+
|      [Actualizar]    [Cerrar]            |
+------------------------------------------+
```

---

### Paso 8: Configurar Eventos de Botones

1. Haz **doble clic** en el botón `btnActualizar`
   - Se creará el método `btnActualizarActionPerformed()`
   
2. Haz **doble clic** en el botón `btnFiltrar`
   - Se creará el método `btnFiltrarActionPerformed()`

3. Haz **doble clic** en el botón `btnCerrar`
   - Se creará el método `btnCerrarActionPerformed()`
   - Dentro del método, agrega: `this.dispose();`

---

### Paso 9: Hacer Públicos los Labels de Estadísticas

**IMPORTANTE**: Los labels que muestran valores deben ser **public** para que el controlador los actualice.

1. Ve a la pestaña **Source** (arriba)
2. Busca la sección `// Variables declaration`
3. Cambia de `private` a `public`:

```java
// Variables declaration - do not modify//GEN-BEGIN:variables
public javax.swing.JButton btnActualizar;
public javax.swing.JButton btnCerrar;
public javax.swing.JButton btnFiltrar;
public javax.swing.JLabel lblAtendidas;
public javax.swing.JLabel lblAusentes;
public javax.swing.JLabel lblCanceladas;
public javax.swing.JTextField txtFechaFin;
public javax.swing.JTextField txtFechaInicio;
// ... resto private
```

---

### Paso 10: Guardar y Compilar

1. Guarda el archivo: `Ctrl + S`
2. Compila: Click derecho en el proyecto → "Build"
3. Verifica que no haya errores

---

## 🎨 Visualización Final Esperada

```
╔════════════════════════════════════════════╗
║       Reporte de Citas                     ║
╠════════════════════════════════════════════╣
║  ┌─ Filtrar por Fecha ──────────────────┐ ║
║  │ Fecha Inicio: [2025-01-01]           │ ║
║  │ Fecha Fin:    [2025-12-31]           │ ║
║  │        [ Filtrar ]                    │ ║
║  └──────────────────────────────────────-┘ ║
║  ┌─ Estadísticas de Citas ─────────────┐  ║
║  │                                      │  ║
║  │  Personas Atendidas:          150   │  ║
║  │  Citas Canceladas:             25   │  ║
║  │  Personas Ausentes:            10   │  ║
║  │                                      │  ║
║  └──────────────────────────────────────┘  ║
║                                             ║
║      [Actualizar]      [Cerrar]             ║
╚════════════════════════════════════════════╝
```

---

## 🔧 Tips y Trucos

### Para Alinear Componentes:
- Selecciona varios componentes
- Click derecho → Align → Left/Right/Top/Bottom

### Para Espaciado Uniforme:
- Usa las guías azules que aparecen al arrastrar

### Para Cambiar Colores:
- Selecciona el componente
- En Properties → foreground/background → Click en [...] → Elige color

### Para Agregar Íconos:
- Descarga íconos PNG de 16x16 o 24x24
- Guárdalos en `src/Recursos/`
- En Properties del botón → icon → Import to Project → Selecciona imagen

---

## 📝 Notas Importantes

1. **NO edites** el código dentro de `// <editor-fold>` manualmente
2. **SÍ edita** fuera de esas secciones (eventos, métodos personalizados)
3. Guarda frecuentemente para no perder cambios
4. Si algo se desalinea, usa `Ctrl + Z` para deshacer

---

## 🚀 Siguiente Paso

Una vez creado el formulario visualmente, necesitarás:
1. **Crear el Modelo**: `Reporte.java` (POJO)
2. **Crear las Consultas**: `ConsultasReporte.java` (DAO)
3. **Crear el Controlador**: `CtrlReporte.java`

Los archivos ya están listos en la siguiente guía. 😊

---

**¿Prefieres que te genere el código completo del formulario?**
Si encuentras complicado hacerlo visualmente, puedo darte el código Java completo para que lo copies directamente.
