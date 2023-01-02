# ⚽ Armame el doparti v3.0 **[WIP]**

![portada](./src/main/res/img/readme/cover.png)

[![releaseDateBadge](https://img.shields.io/badge/status-prealpha-yellow)]()
[![maintainedBadge](https://img.shields.io/badge/maintained-yes-brightgreen)]()

[![buildBadge](https://github.com/akmsw/armame-el-doparti/actions/workflows/maven.yml/badge.svg?branch=develop-v3.0)](https://github.com/akmsw/armame-el-doparti/actions/workflows/maven.yml)
[![issuesBadge](https://img.shields.io/github/issues/akmsw/armame-el-doparti.svg?logo=github)](https://github.com/akmsw/armame-el-doparti/issues)

[![checkStyleBadge](https://img.shields.io/badge/checkstyle10.6.0-passing-brightgreen)](https://checkstyle.sourceforge.io/)
[![sonarLintBadge](https://img.shields.io/badge/sonarlint-1-yellow?logo=sonarlint)](https://www.sonarlint.org/)
[![jUnit5Badge](https://img.shields.io/badge/junit5-passing-brightgreen?logo=junit5)](https://junit.org/junit5/)

[![openJDKTargetBadge](https://img.shields.io/badge/jdk-11%2B-red?logo=openjdk)](https://openjdk.org/projects/jdk/11/)
[![apacheMavenBadge](https://img.shields.io/badge/apache%20maven-3.8.7-orange?logo=apachemaven)](https://maven.apache.org/)
[![lombokBadge](https://img.shields.io/badge/lombok-1.18.24-red?logo=opensourceinitiative)](https://projectlombok.org/)
[![operatingSystemBadge](https://img.shields.io/badge/os-cross--platform-blueviolet?logo=windows-terminal)](https://en.wikipedia.org/wiki/Cross-platform_software)
[![licenseBadge](https://img.shields.io/badge/gpl-3.0-blue?logo=gnu)](https://www.gnu.org/licenses/gpl-3.0.en.html)

## 📜 Índice
- [¿Qué es?](https://github.com/akmsw/armame-el-doparti#-qu%C3%A9-es)
- [Requisitos generales](https://github.com/akmsw/armame-el-doparti#-requisitos-generales)
  - [Java](https://github.com/akmsw/armame-el-doparti#-java)
    - [Versión mínima](https://github.com/akmsw/armame-el-doparti#versi%C3%B3n-m%C3%ADnima)
    - [Versión recomendada](https://github.com/akmsw/armame-el-doparti#versi%C3%B3n-recomendada)
- [Requisitos para compilación manual](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-requisitos-para-compilaci%C3%B3n-manual)
  - [Apache Maven](https://github.com/akmsw/armame-el-doparti#-apache-maven)
    - [Versión recomendada](https://github.com/akmsw/armame-el-doparti#versi%C3%B3n-recomendada-1)
- [Descarga](https://github.com/akmsw/armame-el-doparti#-descarga)
- [Instalación y ejecución](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-instalaci%C3%B3n-y-ejecuci%C3%B3n)
- [¿Cómo se usa?](https://github.com/akmsw/armame-el-doparti#-c%C3%B3mo-se-usa)
- [Nuevas funcionalidades](https://github.com/akmsw/armame-el-doparti#-nuevas-funcionalidades)
  - [Anclaje de jugadores](https://github.com/akmsw/armame-el-doparti#-anclaje-de-jugadores)
- [Mejoras](https://github.com/akmsw/armame-el-doparti#-mejoras)
- [Solución a problemas frecuentes](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-soluci%C3%B3n-de-problemas-comunes)
  - [En Linux](https://github.com/akmsw/armame-el-doparti#-en-Linux)
- [Reportes y sugerencias](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-reportes-y-sugerencias)
- [Capturas de pantalla](https://github.com/akmsw/armame-el-doparti#-capturas-de-pantalla)

## 🔎 ¿Qué es?
Desarrollado completamente en Java y refactorizado en su totalidad, la nueva versión de este programa ofrece una rápida e intuitiva manera de generar equipos para partidos de fútbol 7, ya sea con distribución aleatoria de jugadores o basada en puntuaciones.\
Se ofrece la posibilidad de "anclar" dos o más jugadores entre sí, garantizando de esta forma que queden en el mismo equipo sin importar el método de distribución elegido.

## 📦 Requisitos generales
### ☕ Java
- #### Versión mínima
    🟡 [Java 11](https://www.oracle.com/ar/java/technologies/javase/jdk11-archive-downloads.html)
- #### Versión recomendada
    🟢 [Java 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html) *(o más reciente)*

## ⚙️ Requisitos para compilación manual
### 🪶 Apache Maven
- #### Versión recomendada
    🟢 [Apache Maven 3.8.6](https://maven.apache.org/download.cgi) *(o más reciente)*

## 📥 Descarga
La versión estable más reciente del programa se encuentra disponible para descargar en la sección [releases](https://github.com/akmsw/armame-el-doparti/releases) de este proyecto.

## ▶️ Instalación y ejecución
Más allá de los requisitos listados, no hace falta ninguna instalación para correr este programa.\
Una vez descargado el archivo con extensión ***.jar***, e independientemente del sistema operativo que uses, podés dirigirte a la carpeta donde está situado y abrirlo con un simple *doble click*. En caso de estar en Linux y que el programa no se abra, revisá la sección de [solución a problemas frecuentes en Linux](https://github.com/akmsw/armame-el-doparti#-en-Linux).\
Una alternativa es abrir una terminal dentro de la carpeta contenedora del archivo y ejecutar el comando:
```bash
java -jar nombre_del_archivo.jar
```

## 🛠️ Compilación manual
Para compilar manualmente el programa, asumiendo que instalaste correctamente tanto los [requisitos generales](https://github.com/akmsw/armame-el-doparti#-requisitos-generales) como los [requisitos para compilación manual](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-requisitos-para-compilaci%C3%B3n-manual), tenés que [descargar el proyecto en formato ZIP](https://github.com/akmsw/armame-el-doparti/archive/refs/heads/develop-v3.0.zip), extraer el archivo y, dentro de la carpeta extraída, ejecutar Apache Maven haciendo uso del archivo `pom.xml` mediante la terminal, con el siguiente comando:
```bash
mvn package --file pom.xml
```
o, simplemente:
```bash
mvn package
```

Esto va a crear una carpeta llamada `target` a la cual tenés que entrar. Ahí va a estar el archivo ejecutable en formato `.jar`. Para correr el programa se le puede hacer doble click o ejecutar, mediante la terminal, el comando indicado anteriormente:
```bash
java -jar armameeldoparti-3.0.jar
```

## 📝 ¿Cómo se usa?
Primero vas a tener que ingresar los nombres de los jugadores a sortear en cada posición.\
La distribución estándar de jugadores por equipo es:
- **Defensores centrales**: 1
- **Defensores laterales**: 2
- **Mediocampistas**: 2
- **Delanteros**: 1
- **Arqueros**: 1

Una vez ingresados los nombres de todos los jugadores a repartir en cada posición para ambos equipos, vas a poder seleccionar si distribuirlos de manera aleatoria o en base a una puntuación.\
Si elegís la segunda opción, vas a tener que ingresar una puntuación de 1 ***(mal jugador)*** a 5 ***(excelente jugador)*** para cada uno.\
Finalmente, los equipos se van a armar de la manera más equitativa posible.\
Si los jugadores se reparten en base a sus puntuaciones, la distribución óptima va a ser única. Si se los reparte de manera aleatoria, vas a poder redistribuirlos tantas veces como quieras.

## ⭐ Nuevas funcionalidades
### 🔗 Anclaje de jugadores
El objetivo de esta funcionalidad es la de indicarle al programa que al menos dos jugadores seleccionados por el usuario tienen que estar en el mismo equipo sin importar la distribución que se elija para el resto. El número máximo posible de jugadores a anclar a un mismo equipo es de 6, garantizando así que siempre queden al menos dos jugadores sin anclar para poder realizar alguna distribución.\
Para esto, hay una casilla rotulada con el texto "*Anclar jugadores*" en la ventana de ingreso de nombres. Si tildás esta casilla, luego de seleccionar el método de distribución de jugadores, vas a ver una ventana con una lista con todos los nombres ingresados, cada uno con una casilla similar asignada. Los jugadores cuya casilla esté tildada van a ser anclados al mismo equipo.\
No se pueden anclar a un mismo equipo todos los jugadores de un mismo tipo (por ejemplo, si se anclan todos los mediocampistas para un mismo equipo, el otro equipo no va a tener mediocampistas y esto no es posible). Lo mismo sucede con anclar a un mismo equipo más de la mitad de jugadores registrados para una posición particular.

## ✅ Mejoras
- GUI mucho más cómoda, intuitiva y agradable que en versiones anteriores, con arreglo de importantes bugs.
- Se implementaron algoritmos más eficientes para las distribuciones.
- Se implementó un patrón de diseño MVC para mejor organización del proyecto.
- Se prescindió de clases y métodos que no eran vitales, mejorando significativamente la abstracción, la modularización del código, su mantenibilidad y la velocidad de ejecución del programa.
- Se implementaron expresiones regulares para alivianar tareas.
- Se implementó un enfoque de programación funcional para agilizar la manipulación de datos.
- Importantes cambios generales de refactorización.

## 🛠️ Solución a problemas frecuentes
### 🐧 En Linux
- Si el archivo ***.jar*** no se ejecuta al hacerle doble click, hacé esto:
  - Click derecho sobre el archivo descargado
  - Propiedades
  - Abrir con...
  - En el campo de ingreso de comando personalizado, poné: `java -jar`
  - Seleccionalo como opción predeterminada para la ejecución de archivos .jar

## ⚠️ Reportes y sugerencias
Si el programa presenta algún error que debería ser reportado para arreglarlo, si se te ocurrió alguna nueva funcionalidad para agregar al programa, o si opinás que algo podría ser modificado, la sección de [issues](https://github.com/akmsw/armame-el-doparti/issues) está abierta para que hagas estos reportes y/o sugerencias. Es necesario tener una cuenta en GitHub para abrir un nuevo reporte en el repositorio. Para poder trabajar en eso lo más rápidamente posible, te proveo unas plantillas para cada caso donde te pido toda la información que necesito.

## 📸 Capturas de pantalla
![ventana_principal](./src/main/res/img/readme/ss1.png)\
*Menú principal*

![ventana_ayuda](./src/main/res/img/readme/ss2.png)\
*Ventana de ayuda*

![ventana_ingreso_nombres](./src/main/res/img/readme/ss3.png)\
*Ventana de ingreso de jugadores*

![ventana_anclajes](./src/main/res/img/readme/ss4.png)\
*Ventana de selección de anclajes*

![ventana_puntuaciones](./src/main/res/img/readme/ss5.png)\
*Ventana de ingreso de puntuaciones*

![ventana_resultados_1](./src/main/res/img/readme/ss6.png)\
*Ejemplo de resultado de distribución aleatoria sin anclajes*

![ventana_resultados_2](./src/main/res/img/readme/ss7.png)\
*Ejemplo de resultado de distribución aleatoria con tres anclajes distintos*

![ventana_resultados_3](./src/main/res/img/readme/ss8.png)\
*Ejemplo de resultado de distribución por puntuaciones sin anclajes*

![ventana_resultados_4](./src/main/res/img/readme/ss9.png)\
*Ejemplo de resultado de distribución por puntuaciones con cuatro anclajes distintos*