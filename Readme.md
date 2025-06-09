# Rick and Morty Android App

[![English](https://img.shields.io/badge/Language-English-blue)](README.md) [![Español](https://img.shields.io/badge/Idioma-Español-red)](README_ES.md)

Una aplicación Android moderna construida con Kotlin que consume la API GraphQL de Rick and Morty para mostrar personajes con información detallada, funcionalidad de favoritos y soporte offline.

<p align="center">
  <img src="https://github.com/andresarangoa/omnipro-rickandmorty/blob/main/assets/english_app.gif" width="45%" />
  <img src="https://github.com/andresarangoa/omnipro-rickandmorty/blob/main/assets/spanish_app.gif" width="45%" />
</p>


## 🚀 Características

- **Lista de Personajes**: Muestra lista paginada de personajes de Rick and Morty
- **Detalles del Personaje**: Información completa del personaje incluyendo origen, ubicación y episodios
- **Sistema de Favoritos**: Gestión local de favoritos con almacenamiento persistente
- **Soporte Offline**: Cachea datos localmente y los muestra cuando no hay conexión
- **Monitoreo de Estado de Red**: Estado de conectividad en tiempo real con feedback visual apropiado
- **Soporte Multi-idioma**: Localización en inglés y español
- **Tema Oscuro/Claro**: Sistema de temas completo con cambio dinámico de tema
- **Deslizar para Actualizar**: Actualiza datos de personajes con gesto de deslizar
- **Paginación**: Carga eficiente de grandes conjuntos de datos usando Paging3

## 🏗️ Arquitectura

La aplicación sigue los principios de **Arquitectura Limpia** con una estructura monolítica modular:

```
app/
├── data/
│   ├── local/          # Base de datos Room, DAOs, entidades
│   ├── remote/         # API GraphQL, fuentes de datos
│   ├── repository/     # Implementaciones de repositorio
│   └── mappers/        # Utilidades de mapeo de datos
├── domain/
│   ├── models/         # Entidades de dominio
│   ├── repository/     # Interfaces de repositorio
│   └── usecases/       # Casos de uso de lógica de negocio
└── ui/
    ├── components/     # Componentes UI reutilizables
    └── screens/        # Pantallas de características
```

### Patrones Arquitectónicos
- **MVVM (Model-View-ViewModel)**: Para separación de la capa de presentación
- **Patrón Repository**: Para abstracción de fuentes de datos
- **Casos de Uso**: Para encapsulación de lógica de negocio
- **Inyección de Dependencias**: Usando Koin para gestión modular de dependencias

## 🛠️ Stack Tecnológico

### Tecnologías Centrales
- **Kotlin** - Lenguaje de programación principal
- **Jetpack Compose** - Toolkit moderno de UI
- **Coroutines** - Programación asíncrona
- **Flow** - Programación reactiva

### Arquitectura & DI
- **Koin** - Framework de inyección de dependencias
- **Navigation Component 3** - Navegación en la aplicación
- **ViewModel** - Gestión de estado de UI

### Networking & Datos
- **Apollo GraphQL** - Cliente GraphQL para consumo de API
- **Room Database** - Persistencia de datos local
- **Paging 3** - Carga eficiente de datos y paginación

### UI & Diseño
- **Material Design 3** - Implementación del sistema de diseño
- **Coil** - Carga y caché de imágenes
- **Temas Dinámicos** - Temas adaptativos claro/oscuro

### Testing
- **JUnit 4** - Framework de pruebas unitarias
- **MockK** - Librería de mocking para Kotlin
- **Roborazzi** - Pruebas de captura de pantalla para Compose
- **Turbine** - Utilidades de testing para Flow
- **Coroutines Test** - Testing de corrutinas

### Herramientas de Desarrollo
- **KSP** - Procesamiento de Símbolos de Kotlin
- **Robolectric** - Pruebas unitarias de Android

## 📱 Pantallas

### Pantalla de Lista de Personajes
- Muestra lista paginada de personajes con nombre, estado, especie e imagen
- Funcionalidad de deslizar para actualizar
- Indicador de estado de red
- Botones de alternar favoritos

### Pantalla de Detalles del Personaje
- Muestra información completa del personaje
- Detalles de origen y ubicación actual
- Lista de apariciones en episodios
- Alternar estado de favorito
- Funcionalidad de compartir

### Pantalla de Favoritos
- Gestión de favoritos locales
- Enfoque offline-first
- Acceso rápido a personajes preferidos

## 🌐 Integración con API

La aplicación consume la API GraphQL de Rick and Morty:
- **URL Base**: `https://rickandmortyapi.com/graphql`
- **Optimización de Consultas**: Consultas GraphQL eficientes para transferencia mínima de datos
- **Manejo de Errores**: Gestión integral de errores con mensajes amigables
- **Estrategia de Caché**: Caché normalizado de Apollo con persistencia SQLite

### Consulta GraphQL de Ejemplo
```graphql
query GetCharacters($page: Int!) {
  characters(page: $page) {
    info {
      next
      pages
      count
    }
    results {
      id
      name
      status
      species
      type
      gender
      image
      origin {
        id
        name
        type
        dimension
      }
      location {
        id
        name
        type
        dimension
      }
      episode {
        id
        name
        air_date
        episode
      }
    }
  }
}
```

## 🧪 Estrategia de Testing

### Pruebas Unitarias
- **Pruebas de Repository**: Validación de la capa de datos
- **Pruebas de Casos de Uso**: Verificación de lógica de negocio
- **Pruebas de ViewModel**: Testing de gestión de estado

### Pruebas de UI
- **Pruebas de Captura**: Testing de regresión visual con Roborazzi
- **Pruebas de Compose**: Validación de comportamiento de componentes UI
- **Pruebas de Integración**: Testing de flujos de trabajo end-to-end

## 🚀 Comenzando

### Prerrequisitos
- Android Studio Hedgehog | 2023.1.1 o más reciente
- JDK 17 o superior
- Android SDK 34
- Gradle 8.9.3

### Instrucciones de Configuración

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/andresarangoa/omnipro-rickandmorty.git
   cd omnipro-rickandmorty
   ```

2. **Abrir en Android Studio**
   - Iniciar Android Studio
   - Seleccionar "Open an existing project"
   - Navegar a la carpeta del repositorio clonado

3. **Sincronizar Proyecto**
   - Android Studio sincronizará automáticamente las dependencias de Gradle
   - Esperar a que la sincronización se complete

4. **Compilar y Ejecutar**
   - Conectar un dispositivo Android o iniciar un emulador
   - Hacer clic en "Run" o usar `Shift + F10`

### Configuración

La aplicación no requiere configuración adicional. Todos los endpoints de API y configuraciones necesarias están incluidos.

## 📦 Variantes de Compilación

- **Debug**: Compilación de desarrollo con logging habilitado
- **Release**: Compilación de producción con ofuscación de código y optimización

## 🌍 Localización

La aplicación soporta múltiples idiomas:
- **Inglés** (por defecto)
- **Español** (es)

El cambio de idioma es automático basado en la configuración de idioma del dispositivo.

## 🎨 Temas

### Temas Dinámicos
- **Tema Claro**: Interfaz limpia y brillante
- **Tema Oscuro**: Interfaz oscura amigable con la batería
- **Tema del Sistema**: Sigue la preferencia de tema del dispositivo
- **Material You**: Colores adaptativos basados en el fondo de pantalla del dispositivo (Android 12+)

### Componentes de Tema
- Paletas de colores personalizadas
- Escalado de tipografía
- Estilos de componentes
- Soporte de accesibilidad

## 🔧 Optimizaciones de Rendimiento

- **Carga de Imágenes**: Coil con caché de memoria y disco
- **Consultas de Base de Datos**: Consultas Room optimizadas con índices
- **Gestión de Memoria**: Manejo apropiado del ciclo de vida y limpieza de recursos
- **Eficiencia de Red**: Optimización de consultas GraphQL y caché de respuestas
- **Rendimiento de UI**: LazyColumn para renderizado eficiente de listas

## 🔒 Privacidad de Datos

- **Almacenamiento Local**: Todos los datos del usuario almacenados localmente en el dispositivo
- **Sin Analytics**: Sin seguimiento de usuarios o recolección de analytics
- **Permisos Mínimos**: Solo se requiere permiso de red
- **Encriptación de Datos**: Datos sensibles encriptados usando Android Keystore

## 🐛 Manejo de Errores

### Errores de Red
- Manejo de timeout de conexión
- Parsing de errores GraphQL
- Mecanismos de reintento con backoff exponencial

### Estados de Error de UI
- Estados de carga con pantallas esqueleto
- Mensajes de error con opciones de reintento
- Estados vacíos con guía útil

### Logging
- Logging estructurado para debugging
- Reporte de crashes en compilaciones de desarrollo
- Monitoreo de rendimiento

## 🔄 Soporte Offline

### Estrategia de Caché
- **Base de Datos Primero**: La fuente de datos principal es la base de datos local
- **Sincronización de Red**: Sincronización en segundo plano cuando está online
- **Resolución de Conflictos**: Última escritura gana para conflictos de datos
- **Invalidación de Caché**: Actualización de caché basada en tiempo y manual

### Características Offline
- Ver personajes cargados previamente
- Acceder a personajes favoritos
- Leer detalles de personajes en caché
- Monitoreo de estado de red

## 🚀 Mejoras Futuras

- [ ] Funcionalidad de búsqueda de personajes
- [ ] Pantalla de detalles de episodios
- [ ] Pantalla de detalles de ubicaciones
- [ ] Característica de comparación de personajes
- [ ] Opciones de filtrado avanzadas
- [ ] Funcionalidad de exportación de datos
- [ ] Soporte de widgets
- [ ] Layouts optimizados para tablet

## 📄 Licencia

Este proyecto está desarrollado como una evaluación técnica y es solo para propósitos de demostración.

## 👨‍💻 Desarrollador

**Andrés Arango**
- GitHub: [@andresarangoa](https://github.com/andresarangoa)
- LinkedIn: [Andrés Arango](https://linkedin.com/in/andresarangoat)

## 🙏 Agradecimientos

- Equipo de la API de Rick and Morty por proporcionar la API GraphQL
- Equipo de Jetpack Compose por el toolkit moderno de UI
- Equipo de Apollo GraphQL por el excelente cliente de Android
- Comunidad de código abierto por las increíbles librerías utilizadas

---

*Construido con ❤️ usando prácticas modernas de desarrollo Android*
