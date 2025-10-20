# 🔧 Fix: Corrección de persistencia de cambio de contraseña y configuración de web services

## 📋 Descripción

Este PR corrige un problema crítico donde el método `cambiarPasswordBibliotecario` no persistía los cambios en la base de datos, aunque el web service respondía correctamente.

## 🚨 Problema Identificado

- **Síntoma:** El web service respondía HTTP 200 OK pero los cambios de contraseña no se guardaban en la BD
- **Causa raíz:** El método buscaba bibliotecarios por ID en lugar de número de empleado
- **Impacto:** Los usuarios no podían cambiar contraseñas efectivamente

## ✅ Soluciones Implementadas

### 1. **Nuevo método de búsqueda**
- Agregado `obtenerBibliotecarioPorNumeroEmpleado()` en `ManejadorBibliotecario`
- Permite buscar bibliotecarios por número de empleado correctamente

### 2. **Corrección del método principal**
- Actualizado `cambiarPasswordBibliotecario()` para usar número de empleado
- Implementada actualización con SQL nativo para garantizar persistencia
- Agregados logs de debug para monitoreo

### 3. **Configuración de web services**
- Corregida lectura del archivo `.properties` en `WebServiceConfiguracion`
- Web services ahora funcionan correctamente en puerto 8081
- Agregado archivo de ejemplo `webservice-config.properties.example`

## 🧪 Pruebas Realizadas

### Prueba de funcionalidad:
```bash
# Llamada SOAP exitosa
curl -X POST -H "Content-Type: text/xml; charset=utf-8" \
  -d '<soap:Envelope...><pub:cambiarPasswordBibliotecario>
        <arg0>1018</arg0><arg1>123456</arg1><arg2>nuevaPassword789</arg2>
      </pub:cambiarPasswordBibliotecario></soap:Body></soap:Envelope>' \
  http://localhost:8081/autenticacion
```

### Verificación en BD:
```sql
-- Antes: password = "123456"
-- Después: password = "nuevaPassword789" ✅
SELECT password FROM usuarios WHERE id = 'B19';
```

## 📁 Archivos Modificados

- `src/main/java/configuraciones/WebServiceConfiguracion.java`
- `src/main/java/logica/AutenticacionControlador.java`
- `src/main/java/logica/ManejadorBibliotecario.java`
- `webservice-config.properties.example` (nuevo)

## 🔍 Cambios Técnicos Detallados

### ManejadorBibliotecario.java
```java
// Nuevo método agregado
public Bibliotecario obtenerBibliotecarioPorNumeroEmpleado(String numeroEmpleado) {
    // Implementación con consulta HQL
}

// Método actualizado con SQL nativo
public void actualizarBibliotecario(Bibliotecario bibliotecario) {
    // Usa UPDATE SQL nativo para garantizar persistencia
}
```

### AutenticacionControlador.java
```java
// Método corregido para usar número de empleado
public void cambiarPasswordBibliotecario(String numeroEmpleado, String passwordActual, String passwordNuevo) {
    // Busca por número de empleado, no por ID
    Bibliotecario bibliotecario = manejadorBibliotecario.obtenerBibliotecarioPorNumeroEmpleado(numeroEmpleado);
}
```

## 🎯 Resultado Esperado

Después del merge:
- ✅ Los usuarios pueden cambiar contraseñas exitosamente
- ✅ Los cambios se persisten correctamente en la BD
- ✅ La contraseña antigua deja de funcionar
- ✅ La nueva contraseña funciona para el login
- ✅ Web services funcionan en puerto 8081

## 📋 Checklist

- [x] Método probado con cliente SOAP
- [x] Persistencia verificada en base de datos
- [x] Validaciones funcionando correctamente
- [x] Logs de debug implementados
- [x] Configuración de web services corregida
- [x] Archivo de ejemplo agregado
- [x] Documentación actualizada

## 🚀 Instrucciones para el Frontend

**IMPORTANTE:** El frontend debe usar el **número de empleado** (ej: "1018") como primer parámetro, NO el ID (ej: "B19").

```xml
<!-- CORRECTO -->
<pub:cambiarPasswordBibliotecario>
    <arg0>1018</arg0>  <!-- Número de empleado -->
    <arg1>contraseñaActual</arg1>
    <arg2>nuevaContraseña</arg2>
</pub:cambiarPasswordBibliotecario>
```

## 🔗 URLs de Web Services

- **Base:** `http://localhost:8081`
- **Autenticación:** `http://localhost:8081/autenticacion`
- **Bibliotecarios:** `http://localhost:8081/bibliotecario`

---

**Este PR resuelve completamente el problema de persistencia de contraseñas y mejora la configuración de web services.**
