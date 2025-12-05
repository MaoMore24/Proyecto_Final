# Guía para Diseñar frmAgregarUsuarios en NetBeans

## 📋 Componentes Necesarios

Debes agregar los siguientes componentes al formulario `frmAgregarUsuarios.java` en el editor visual de NetBeans:

### 1. **Labels (JLabel)**
Arrastra estos JLabel desde la paleta y cambia su propiedad `text`:
- `jLabel1` → Text: "Registrar Nuevo Usuario" (título, fuente grande)
- `lblUsuario` → Text: "Usuario:"
- `lblPassword` → Text: "Contraseña:"
- `lblNombre` → Text: "Nombre:"
- `lblApellido` → Text: "Apellido:"  
- `lblEmail` → Text: "Email:"
- `lblTipoUsuario` → Text: "Tipo de Usuario:"

### 2. **Text Fields (JTextField)**
- `txtUsuario` → Para el nombre de usuario
- `txtNombre` → Para el nombre
- `txtApellido` → Para el apellido
- `txtEmail` → Para el correo electrónico

### 3. **Password Field (JPasswordField)**
- `txtPassword` → Para la contraseña (aparecerá con asteriscos)

### 4. **ComboBox (JComboBox)**
- `cmbTipoUsuario` → Para seleccionar el rol (Administrador, Médico, Enfermero, Laboratorio)

### 5. **Buttons (JButton)**
- `btnGuardar` → Text: "Guardar"
- `btnCancelar` → Text: "Cancelar"

## 🎨 Layout Sugerido

```
┌─────────────────────────────────────────────┐
│  Registrar Nuevo Usuario (Título Grande)    │
├─────────────────────────────────────────────┤
│                                             │
│  Usuario:         [________________]        │
│                                             │
│  Contraseña:      [________________]        │
│                                             │
│  Nombre:          [________________]        │
│                                             │
│  Apellido:        [________________]        │
│                                             │
│  Email:           [________________]        │
│                                             │
│  Tipo de Usuario: [▼ Seleccionar  ]        │
│                                             │
│         [Guardar]    [Cancelar]             │
└─────────────────────────────────────────────┘
```

## 📝 Pasos en NetBeans

1. **Abre** el formulario `frmAgregarUsuarios.java` en modo diseño
2. **Arrastra** los componentes desde la paleta (Swing Controls)
3. **Cambia los nombres** (Name property) exactamente como se indica arriba
4. **Cambia el texto** (Text property) para los labels y botones
5. **Declara como public** los componentes que el controlador necesita:
   - Clic derecho en cada componente → "Change Variable Declaration"
   - Selecciona "public" en lugar de "private"

## ⚙️ Componentes que deben ser PUBLIC

Estos componentes DEBEN declararse como `public` para que el controlador pueda acceder:

```java
public javax.swing.JTextField txtUsuario;
public javax.swing.JPasswordField txtPassword;
public javax.swing.JTextField txtNombre;
public javax.swing.JTextField txtApellido;
public javax.swing.JTextField txtEmail;
public javax.swing.JComboBox<String> cmbTipoUsuario;
public javax.swing.JButton btnGuardar;
public javax.swing.JButton btnCancelar;
```

## 🔗 Conectar el Controlador

Una vez que hayas diseñado el formulario, conéctalo desde `ctrlSistema.java` agregando este código en el método `actionPerformed`:

```java
if (e.getSource() == frm.btnPacientes) {
    // Abrir formulario de registrar usuarios
    Usuario usr = new Usuario();
    ConsultasUsuario consUsr = new ConsultasUsuario();
    frmAgregarUsuarios frmUsuarios = new frmAgregarUsuarios();
    CtrlRegistrarUsuario ctrlUsuarios = new CtrlRegistrarUsuario(usr, consUsr, frmUsuarios);
    ctrlUsuarios.iniciar();
}
```

## ✅ Verificación

Después de diseñar el formulario, verifica que:
1. ✔️ Todos los componentes tienen los nombres correctos
2. ✔️ Los campos de texto, botones y combobox son públicos
3. ✔️ El formulario se ve ordenado y profesional
4. ✔️ El controlador está conectado desde ctrlSistema

---

**Nota**: Si necesitas ayuda con algún paso específico, ¡avísame!
