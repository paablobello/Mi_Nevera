
# Mi Nevera

Aplicación para Android desarrollada en Android Studio que te permite generar recetas a partir de los productos que añadas a tu nevera. Las recetas se generan a través de la API de OpenAI. La aplicación también ofrece funcionalidades adicionales como la creación de un perfil una vez iniciada la sesión y una sección para tu lista de la compra.

## Requisitos Previos

- Android Studio instalado en tu máquina.
- Una clave de API válida de GPT.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/paablobello/Mi_Nevera
   ```

2. Abre el proyecto en Android Studio:
   - Selecciona "Open an existing project".
   - Navega al directorio del proyecto y selecciónalo.

3. Configura la clave de API:
   - Abre el archivo `Config.java` en la ruta `app/src/main/java/com/example/mi_nevera/io/`.
   - Añade tu clave de API de GPT en el lugar correspondiente:
     ```java
     public class Config {
         /**
          * API key de la API de OpenAI.
          */
         public static final String CHAT_GPT_API_KEY = "tu_api_key";
     }
     ```

4. Sincroniza el proyecto con Gradle y asegúrate de que todas las dependencias estén correctamente instaladas.

## Uso

1. Ejecuta la aplicación en un dispositivo o emulador Android desde Android Studio.
2. Inicia sesión o crea un nuevo perfil.
3. Añade los ingredientes disponibles en tu nevera.
4. Genera recetas basadas en los ingredientes añadidos.
5. Administra tu lista de la compra a través de la sección correspondiente de la aplicación.
