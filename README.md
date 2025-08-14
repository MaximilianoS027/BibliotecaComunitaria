# Sistema de Gestión de Biblioteca

## Descripción
Sistema de gestión de biblioteca desarrollado en Java con arquitectura en capas, siguiendo los principios de Clean Architecture y patrones de diseño.

## Características
- **Arquitectura en Capas**: Separación clara de responsabilidades entre dominio, aplicación, infraestructura y presentación
- **Persistencia con Hibernate**: ORM para mapeo objeto-relacional
- **Base de Datos H2**: Base de datos en memoria para desarrollo
- **Logging con Log4j**: Sistema de logging configurable
- **Interfaz Gráfica Swing**: Interfaz de usuario nativa de Java
- **Validaciones**: Sistema de validación robusto para entradas de usuario

## Estructura del Proyecto

```
src/main/java/com/pap/
├── domain/                    # Capa de dominio
│   ├── model/                # Modelos de dominio
│   │   ├── abstract/         # Clases abstractas (Usuario, Material)
│   │   ├── concrete/         # Clases concretas (Lector, Bibliotecario, Libro, etc.)
│   │   └── enums/            # Enumeraciones (EstadoLector, EstadoPrestamo, Zona)
│   └── repository/            # Interfaces de repositorio
├── infrastructure/             # Capa de infraestructura
│   ├── persistence/           # Implementaciones de persistencia
│   │   ├── dao/               # Data Access Objects
│   │   └── hibernate/         # Configuración de Hibernate
│   └── database/              # Configuración de base de datos
├── application/                # Capa de aplicación
│   ├── controller/            # Controladores de la aplicación
│   ├── service/               # Servicios de negocio
│   └── dto/                   # Objetos de transferencia de datos
├── presentation/               # Capa de presentación
│   ├── gui/                   # Interfaces gráficas de usuario
│   └── main/                  # Ventana principal
└── shared/                     # Utilidades compartidas
    ├── exception/              # Excepciones personalizadas
    ├── utils/                  # Utilidades generales
    └── constants/              # Constantes del sistema
```

## Modelo de Dominio

### Entidades Principales
- **Usuario**: Clase abstracta base para Lector y Bibliotecario
- **Material**: Clase abstracta base para Libro y Artículo
- **Prestamo**: Representa un préstamo de materiales
- **Lector**: Usuario que puede solicitar préstamos
- **Bibliotecario**: Empleado que gestiona préstamos
- **Libro**: Material específico con título y páginas
- **Artículo**: Material general con descripción y peso

### Enumeraciones
- **EstadoLector**: ACTIVO, SUSPENDIDO
- **EstadoPrestamo**: PENDIENTE, EN_CURSO, DEVUELTO
- **Zona**: BIBLIOTECA_CENTRAL, SUCURSAL_ESTE, SUCURSAL_OESTE, etc.

## Casos de Uso Implementados

1. **Registrar Lector**: Crear un nuevo lector en el sistema
2. **Registrar Libro**: Agregar un nuevo libro al catálogo
3. **Crear Préstamo**: Crear un nuevo préstamo de materiales
4. **Actualizar Préstamo**: Modificar el estado de un préstamo existente

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

## Arquitectura

### Patrón MVC
- **Model**: Clases de dominio en el paquete `domain`
- **View**: Interfaces gráficas en el paquete `presentation.gui`
- **Controller**: Controladores en el paquete `application.controller`

### Patrón DAO
- Interfaces de repositorio en `domain.repository`
- Implementaciones en `infrastructure.persistence.dao`

### Inyección de Dependencias
- Los servicios se inyectan en los controladores
- Los repositorios se inyectan en los servicios

## Desarrollo

### Agregar Nueva Entidad
1. Crear la clase en `domain.model.concrete`
2. Crear la interfaz de repositorio en `domain.repository`
3. Implementar el DAO en `infrastructure.persistence.dao`
4. Crear el servicio en `application.service`
5. Crear el controlador en `application.controller`
6. Crear la interfaz gráfica en `presentation.gui`

### Agregar Nuevo Caso de Uso
1. Definir el flujo en el controlador correspondiente
2. Implementar la lógica de negocio en el servicio
3. Crear la interfaz gráfica necesaria
4. Agregar validaciones y manejo de errores

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
