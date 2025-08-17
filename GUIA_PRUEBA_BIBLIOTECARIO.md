# 🧪 GUÍA PARA PROBAR LA FUNCIONALIDAD "REGISTRAR BIBLIOTECARIO"

## 🚀 Cómo ejecutar la aplicación

### Opción 1: Usar el script Maven (RECOMENDADO)
```bash
.\ejecutar_con_maven.bat
```

### Opción 2: Usar el script directo
```bash
.\ejecutar_directo.bat
```

## 📋 Pasos para probar la funcionalidad

### 1. **Iniciar la aplicación**
- Ejecutar uno de los scripts mencionados arriba
- Debe aparecer la ventana principal: "Sistema de Gestión de Biblioteca - Lectores.uy"

### 2. **Acceder al menú**
- En la barra de menú, hacer clic en **"Bibliotecarios"**
- Seleccionar **"Registrar Bibliotecario"**

### 3. **Llenar el formulario**
Se abrirá una ventana interna con los siguientes campos:
- **Número de empleado**: (obligatorio) Ej: "EMP001"
- **Nombre**: (mínimo 2 caracteres) Ej: "Juan Pérez"
- **Correo electrónico**: (debe contener @) Ej: "juan.perez@biblioteca.uy"

### 4. **Probar casos exitosos**
Datos válidos para probar:
```
Número de empleado: EMP001
Nombre: María González
Email: maria.gonzalez@biblioteca.uy
```

```
Número de empleado: EMP002  
Nombre: Carlos Rodríguez
Email: carlos.rodriguez@biblioteca.uy
```

### 5. **Probar validaciones de error**

#### A) Campos vacíos:
- Dejar cualquier campo vacío → Debe mostrar error "datos inválidos"

#### B) Número de empleado repetido:
- Intentar registrar el mismo número dos veces → Error "Bibliotecario Repetido"

#### C) Email inválido:
- Email sin @: "correo-invalido" → Error "datos inválidos"
- Email muy corto: "a@b" → Error "datos inválidos"

#### D) Nombre muy corto:
- Nombre de 1 carácter: "A" → Error "datos inválidos"

## ✅ Resultados esperados

### **Caso exitoso:**
- Mensaje: "Bibliotecario registrado exitosamente"
- El formulario se limpia automáticamente
- Se puede registrar otro bibliotecario

### **Casos de error:**
- **Datos inválidos**: Mensaje específico sobre qué campo es inválido
- **Bibliotecario repetido**: "Ya existe un bibliotecario con el número de empleado: XXX"
- Los datos permanecen en el formulario para corrección

## 🏗️ Arquitectura implementada

La funcionalidad sigue la arquitectura especificada:

```
Presentación (RegistrarBibliotecario.java)
    ↓
Interfaces (IControlador)
    ↓  
Lógica (Controlador → ManejadorBibliotecario)
    ↓
Persistencia (Conexion → EntityManager → H2 Database)
```

## 🔍 Verificación de datos

Para verificar que los datos se guardaron correctamente:
1. Registrar un bibliotecario
2. Ir al menú "Bibliotecarios" → "Consultar Bibliotecarios" 
3. Debería aparecer en la lista (funcionalidad básica implementada)

## 📊 Patrones implementados

- ✅ **Singleton**: Fabrica, ManejadorBibliotecario, Conexion
- ✅ **DAO**: ManejadorBibliotecario
- ✅ **DTO**: DtBibliotecario (inmutable)
- ✅ **Factory**: Fabrica para IControlador
- ✅ **Layered Architecture**: Separación estricta de capas
