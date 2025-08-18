# Sistema de Gestión de Biblioteca

## Descripción
Sistema de gestión de biblioteca desarrollado en Java con arquitectura en capas, siguiendo los principios de Clean Architecture y patrones de diseño.

## Características
- **Arquitectura en Capas**: Separación clara de responsabilidades entre paquetes lógicos como `datatypes`, `excepciones`, `interfaces`, `logica`, `persistencia` y `presentacion`.
- **Persistencia con Hibernate**: ORM para mapeo objeto-relacional
- **Base de Datos H2**: Base de datos en memoria para desarrollo
- **Logging con Log4j**: Sistema de logging configurable
- **Interfaz Gráfica Swing**: Interfaz de usuario nativa de Java
- **Validaciones**: Sistema de validación robusto para entradas de usuario

## Estructura del Proyecto

```
src/main/java/
├── datatypes/                    # Clases para transferencia de datos
├── excepciones/                  # Clases de excepciones personalizadas
├── interfaces/                   # Interfaces del controlador y fábrica
├── logica/                       # Lógica de negocio y manejo de entidades
├── MainNuevo.java                # Punto de entrada principal de la aplicación
├── persistencia/                 # Clases para la conexión a la base de datos
├── presentacion/                 # Clases de la interfaz de usuario (Swing)
└── resources/                    # Archivos de configuración y recursos
    ├── database-postgresql.properties  # Configuración de PostgreSQL
    ├── database.properties       # Configuración general de la base de datos
    ├── hibernate-postgresql.cfg.xml    # Configuración de Hibernate para PostgreSQL
    ├── hibernate.cfg.xml         # Configuración general de Hibernate
    ├── log4j.properties          # Configuración de logging con Log4j
    └── META-INF/                 # Metadatos
        └── persistence.xml       # Configuración de JPA/Hibernate
```

## Modelo de Dominio

### Entidades Principales
- **Bibliotecario**: Entidad que representa a un bibliotecario del sistema.

## Casos de Uso Implementados

1. **Registrar Bibliotecario**: Crear un nuevo bibliotecario en el sistema.

## Tecnologías Utilizadas

- **Java 11**: Lenguaje de programación
- **Maven**: Gestión de dependencias y build
- **Hibernate 5.6**: ORM para persistencia
- **H2 Database**: Base de datos en memoria
- **Log4j**: Sistema de logging
- **JUnit 4**: Framework de testing
- **Swing**: Interfaz gráfica de usuario

## Requisitos del Sistema

- Java 11 o superior
- Maven 3.6 o superior
- Memoria RAM: 2GB mínimo
- Espacio en disco: 100MB mínimo

## Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd pap_proyect/tarea1
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar la aplicación
```bash
mvn exec:java
```

### 4. Ejecutar tests
```bash
mvn test
```

### 5. Crear JAR ejecutable
```bash
mvn package
```

## Configuración

### Base de Datos
El sistema utiliza H2 como base de datos en memoria por defecto. La configuración se encuentra en:
- `src/main/resources/hibernate.cfg.xml`
- `src/main/resources/database.properties`

### Logging
La configuración de logging se encuentra en:
- `src/main/resources/log4j.properties`

## Desarrollo

### Agregar Nueva Entidad
1. Crear la clase de entidad en el paquete `logica`.
2. Si es necesario, crear un Dt (Data Type) para la entidad en el paquete `datatypes`.
3. Implementar la lógica de negocio relacionada con la entidad en el `Controlador.java` o en una clase de negocio específica dentro de `logica`.
4. Si la entidad requiere persistencia, asegúrate de que esté mapeada correctamente con Hibernate y maneja las operaciones de persistencia en `Conexion.java` o una clase relacionada.
5. Crear o modificar la ventana (GUI) en el paquete `presentacion` para interactuar con la nueva entidad.

### Agregar Nuevo Caso de Uso
1. Definir el flujo en el `Controlador.java`.
2. Implementar la lógica de negocio en el `Controlador.java` o en clases auxiliares dentro de `logica`.
3. Crear la ventana (GUI) necesaria en el paquete `presentacion`.
4. Agregar validaciones y manejo de errores utilizando las clases en `excepciones`.

## Testing

### Estructura de Tests
```
src/test/java/com/pap/
├── domain/                     # Tests de dominio
├── infrastructure/             # Tests de infraestructura
├── application/                # Tests de aplicación
└── presentation/               # Tests de presentación
```

### Ejecutar Tests
```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=NombreClaseTest

# Tests con cobertura
mvn test jacoco:report
```

## Logging

El sistema utiliza Log4j para logging con los siguientes niveles:
- **ERROR**: Errores críticos del sistema
- **WARN**: Advertencias y situaciones no críticas
- **INFO**: Información general del sistema
- **DEBUG**: Información detallada para desarrollo

## Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto

- **Autor**: PAP Proyect
- **Email**: [tu-email@ejemplo.com]
- **Proyecto**: [https://github.com/tu-usuario/pap_proyect]

## Changelog

### v1.0.0 (2025-08-14)
- Implementación inicial del sistema
- Arquitectura en capas completa
- Modelo de dominio básico
- Interfaz gráfica básica
- Sistema de logging configurado
- Configuración de Hibernate y H2
