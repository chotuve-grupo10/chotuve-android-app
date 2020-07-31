# Chotuve Android App

## Descripción
Este es un cliente de Android construido en lenguaje Kotlin que consume los servicios del Application Server y permite a los usuarios acceder al contenido de la plataforma.
Chotuve permite al usuario gestionar un perfil, registrándose como un usuario local o utilizando la autorización de Facebook o Google, y a partir de ahí buscar a sus amigos, subir videos propios, buscar videos de otros, reaccionar a ellos publicando comentarios o votando "me gusta" o "me disgusta"

## Setup:

- Se debe descargar AndroidStudio
- Clonar el repositorio en la carpeta /path/del/usuario/AndroidStudioProjects
- Una vez ahí dentro, se ejecuta AndroidStudio y se puede abrir el proyecto chotuve-android-app. 
- Para correrlo, podemos ir por dos caminos:
    - Instalación de un dispositivo virtual (AVD). Se trata de una instancia de qemu que corre alguna versión de Android. En nuestro caso, será la versión 4.1). Para mejor experiencia se recomienda utilizar el equipo Pixel 2 API R.
    - Se conecta un equipo Android via USB, se activa la opción de Programador y se activa el Modo de depuración por USB.
- Una vez que se cuenta con alguno de los dos canales de ejecución, se puede apretar en el botón verde que ejecuta el Build y luego instala la aplicación en el equipo en cuestión.

## Código generado desde swagger:

Utilizando los archivos de swagger para documentar siguiendo el estándar OpenApi 2.0, pudimos además utilizar ésta documentación para generar código utilizando gradle. Este código incluyea interfaz DefaultApi desde la cual se definieron todos los endpoints a utilizar (todos estos endpoints propios del AppServer), además de un set de objetos que se utilizan en nuestro código para realizar consultas, y además para recibir las respuestas del AppServer.
El comando para generar código con gradle es el siguiente y debe ejecutarse desde la raíz del proyecto
```
gradle app:generateSwagger
```
