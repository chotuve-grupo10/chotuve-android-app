# chotuve-android-app
-----------------------------------------

### Setup:

- Descargan AndroidStudio
- Deben clonar el repositorio en la carpeta /path/del/usuario/AndroidStudioProjects
- Una vez ahí dentro, se ejecuta el AndroidStudio y pueden abrir el proyecto
- Para correrlo, fue necesario instalar un AVD (dispositivo virtual). Básicamente una instancia de qemu que corre algún Android (en nuestro caso el 4.1). Acá es importante que sepan que el cuello de botella, en mi compu, resultó ser la placa de video integrada. Habrá que ver si eso a mediano plazo es un limitante. El AVD a crear es el siguiente: Pixel 2 API R. Si siguen las instrucciones desde Tools/AVD Manager van a tener que descargar la API R.
- Una vez que lo tengan intenten correr la app. Para ésta instancia yo tuve un error de la librería Vulkan. Lo subsané (parcialmente, pues aún me tira unos warnings) con ésta instalación https://unix.stackexchange.com/questions/573336/android-studio-error-emulator-emulator-error-vkcommonoperations-cpp496-fa
 
-----------------------------------------

 ### Checklist:

- [x] armado de la estructura básica del proyecto de la aplicación
- [ ] botón que envíe un ping al Application Server