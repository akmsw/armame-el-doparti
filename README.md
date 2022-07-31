# Armame el doparti v3.0 ⚽

![portada](./src/main/res/img/readme/cover.png)

[![releaseDateBadge](https://img.shields.io/badge/release%20date-tbd-yellow)]()
[![maintainedBadge](https://img.shields.io/badge/maintained-yes-brightgreen)]()

[![build](https://github.com/akmsw/armame-el-doparti/actions/workflows/maven.yml/badge.svg?branch=develop-v3.0)](https://github.com/akmsw/armame-el-doparti/actions/workflows/maven.yml)
[![issuesBadge](https://img.shields.io/github/issues/akmsw/armame-el-doparti.svg?logo=github)](https://github.com/akmsw/armame-el-doparti/issues)
[![checkStyleBadge](https://img.shields.io/badge/checkstyle10.3.1-passing-brightgreen)](https://checkstyle.sourceforge.io/)
[![sonarLintBadge](https://img.shields.io/badge/sonarlint-passing-brightgreen?logo=sonarlint)](https://www.sonarlint.org/)
[![jUnit5Badge](https://img.shields.io/badge/junit5-passing-brightgreen?logo=junit5)](https://junit.org/junit5/)

[![openJDKTarget](https://img.shields.io/badge/jdk-11%2B-red?logo=openjdk)](https://openjdk.org/projects/jdk/11/)
[![apacheMavenBadge](https://img.shields.io/badge/apache-maven-orange?logo=apachemaven)](https://maven.apache.org/)
[![operatingSystemBadge](https://img.shields.io/badge/os-cross--platform-blueviolet?logo=windows-terminal)](https://en.wikipedia.org/wiki/Cross-platform_software)
[![licenseBadge](https://img.shields.io/badge/gpl-3.0-blue?logo=gnu)](https://www.gnu.org/licenses/gpl-3.0.en.html)

# English
<details>
  <summary>Click to expand</summary>

  ## 👉 Notice
  At the moment, this program's language is only ***spanish***.\
  Maybe in a future version I'll add a language-change button. Sorry about that.\
  However, you can read the documentation and use the program anyway!

  ## 📜 Index
  - [What is it?](https://github.com/akmsw/armame-el-doparti#-what-is-it)
  - [Requirements](https://github.com/akmsw/armame-el-doparti#-requirements)
    - [Java](https://github.com/akmsw/armame-el-doparti#-java)
      - [Minimum version](https://github.com/akmsw/armame-el-doparti#minimum-version)
      - [Recommended version](https://github.com/akmsw/armame-el-doparti#recommended-version)
  - [Download](https://github.com/akmsw/armame-el-doparti#-download)
  - [Installing & running](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-installing--running)
  - [How to use it?](https://github.com/akmsw/armame-el-doparti#-how-to-use-it)
  - [New features](https://github.com/akmsw/armame-el-doparti#-new-features)
    - [Players anchoring](https://github.com/akmsw/armame-el-doparti#-players-anchoring)
  - [Improvements](https://github.com/akmsw/armame-el-doparti#-improvements)
  - [Troubleshooting](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-troubleshooting)
    - [On Linux](https://github.com/akmsw/armame-el-doparti#-on-linux)
  - [Issues & suggestions](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-issues--suggestions)
  - [Screenshots](https://github.com/akmsw/armame-el-doparti#-screenshots)

  ## 🔎 What is it?
  Completely developed in Java and fully refactored, the new version of this program offers a fast and intuitive way to generate teams for soccer matches with 7 players per team, distributed either randomly or based on their skill.\
  It offers the possibility of "anchoring" two or more players to each other, thus guaranteeing that they will remain on the same team regardless of the chosen distribution method.

  ## 📦 Requirements
  ### ☕ Java
  - #### Minimum version
      🟡 [Java 11](https://www.oracle.com/ar/java/technologies/javase/jdk11-archive-downloads.html)
  - #### Recommended version
      🟢 [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) *(or newer)*

  ## 📥 Download
  The latest stable version of the program is available for download in the [releases](https://github.com/akmsw/armame-el-doparti/releases) section.

  ## ▶️ Installing & running
  Beyond the listed requirements, no further installation is needed to run this program.\
  Once the file with the ***.jar*** extension has been downloaded, and regardless of the operating system you use, you can go to the folder where it is located and open it with a simple *double click*.\
  In case you are on Linux and the program does not open, see the [troubleshooting on Linux](https://github.com/akmsw/armame-el-doparti#-en-linux) section.\
  Another way to run this program is to open a terminal inside the file's containing folder and run the command:
  ```bash
  java -jar file_name.jar
  ```

  ## 📝 How to use it?
  The user will be able to generate their teams by entering the names of the players to be drawn in each position.\
  The standard team formation is:
  - **Central defenders**: 1
  - **Lateral defenders**: 2
  - **Midfielders**: 2
  - **Forwards**: 1
  - **Goalkeepers**: 1

  Once the names of all the players to distribute in each position for both teams have been entered, it will be possible to select whether to distribute them randomly, or based on their skill.\
  If the second option is chosen, the user will be prompted for a skill number from 1 ***(bad player)*** to 5 ***(excellent player)*** for each player.\
  Finally, the teams will be assembled in the most fair way possible.\
  If the players are distributed based on their skill, the optimal distribution will be unique. If you decide to distribute them randomly, you can redistribute them as many times as you want.

  ## ⭐ New features
  ### 🔗 Players anchoring
  The objective of this feature is to indicate that at least two players selected by the user must belong to the same team regardless of the distribution chosen. The maximum possible number of players to pin to the same team is 6, thus guaranteeing that there are always at least two players left unpinned in order to make a distribution.\
  For this, a checkbox labeled "*Anclar jugadores*" is provided in the names input window. If the user checks this checkbox, after selecting the player distribution method, a window will be presented in which there will be all the entered players names and their respective checkbox. Players whose checkbox is checked will be pinned to the same team.\
  It will not be possible to anchor all players of the same type to the same team (i.e., if all midfielders are anchored for the same team, the other team will not have midfielders and this is not possible). The same goes for anchoring more than half of the registered players for a particular position to the same team.

  ## ✅ Improvements
  - Much more comfortable, intuitive and nicer GUI than in previous versions.
  - Major GUI bugs fix.
  - More efficient algorithms for players distribution were implemented.
  - Non-vital classes and methods were dropped, significantly improving abstraction, code modularization, maintainability, and program execution speed.
  - Implementation of regular expressions to lighten tasks.
  - Important refactoring changes.

  ## 🛠️ Troubleshooting
  ### 🐧 On Linux
  - If the ***.jar*** file does not run when you double-click it, follow the steps below:
    - Right click on the downloaded file
    - Go to properties
    - Open with...
    - In the custom command input field, enter: `java -jar`
    - Set as default option for execution of .jar files

  ## ⚠️ Issues & suggestions
  If the program has an error that must be reported to fix it, if you have thought of any new functionality to add to the program, or if you think that something could be modified, the [issues](https://github.com/akmsw/armame-el-doparti/issues) section is open for you to make these reports and/or suggestions. A GitHub account is required to open a new issue in the repository. In order to work on it as quickly as possible, I provide you with some templates for each case where I ask you for all the information that will be useful to me.

  ## 📸 Screenshots
  ![main_menu_window](./src/main/res/img/readme/ss1.png)\
  *Main menu window*

  ![help_window](./src/main/res/img/readme/ss2.png)\
  *Help window*

  ![names_input_window](./src/main/res/img/readme/ss3.png)\
  *Names input window*

  ![anchorages_window](./src/main/res/img/readme/ss4.png)\
  *Anchorages window*

  ![skills_window](./src/main/res/img/readme/ss5.png)\
  *Skill points input window*

  ![results_window_1](./src/main/res/img/readme/ss6.png)\
  *Random distribution without anchorages*

  ![results_window_2](./src/main/res/img/readme/ss7.png)\
  *Random distribution with three anchorages*

  ![results_window_3](./src/main/res/img/readme/ss8.png)\
  *By-skill distribution without anchorages*

</details>

# Español
<details>
  <summary>Presioná para desplegar</summary>

  ## 📜 Índice
  - [¿Qué es?](https://github.com/akmsw/armame-el-doparti#-qu%C3%A9-es)
  - [Requisitos](https://github.com/akmsw/armame-el-doparti#-requisitos)
    - [Java](https://github.com/akmsw/armame-el-doparti#-java)
      - [Versión mínima](https://github.com/akmsw/armame-el-doparti#versi%C3%B3n-m%C3%ADnima)
      - [Versión recomendada](https://github.com/akmsw/armame-el-doparti#versi%C3%B3n-recomendada)
  - [Descarga](https://github.com/akmsw/armame-el-doparti#-descarga)
  - [Instalación y ejecución](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-instalaci%C3%B3n-y-ejecuci%C3%B3n)
  - [¿Cómo se usa?](https://github.com/akmsw/armame-el-doparti#-c%C3%B3mo-se-usa)
  - [Nuevas funcionalidades](https://github.com/akmsw/armame-el-doparti#-nuevas-funcionalidades)
    - [Anclaje de jugadores](https://github.com/akmsw/armame-el-doparti#-anclaje-de-jugadores)
  - [Mejoras](https://github.com/akmsw/armame-el-doparti#-mejoras)
  - [Solución a problemas frecuentes](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-soluci%C3%B3n-de-problemas-comunes)
    - [En Linux](https://github.com/akmsw/armame-el-doparti#-en-linux)
  - [Reportes y sugerencias](https://github.com/akmsw/armame-el-doparti#%EF%B8%8F-reportes-y-sugerencias)
  - [Capturas de pantalla](https://github.com/akmsw/armame-el-doparti#-capturas-de-pantalla)

  ## 🔎 ¿Qué es?
  Desarrollado completamente en Java y refactorizado en su totalidad, la nueva versión de este programa ofrece una rápida e intuitiva manera de generar equipos para partidos de fútbol 7, ya sea con distribución aleatoria de jugadores o basada en puntuaciones.\
  Se ofrece la posibilidad de "anclar" dos o más jugadores entre sí, garantizando de esta forma que queden en el mismo equipo sin importar el método de distribución elegido.

  ## 📦 Requisitos
  ### ☕ Java
  - #### Versión mínima
      🟡 [Java 11](https://www.oracle.com/ar/java/technologies/javase/jdk11-archive-downloads.html)
  - #### Versión recomendada
      🟢 [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) *(o más reciente)*

  ## 📥 Descarga
  La versión estable más reciente del programa se encuentra disponible para descargar en la sección [releases](https://github.com/akmsw/armame-el-doparti/releases) de este proyecto.

  ## ▶️ Instalación y ejecución
  Más allá de los requisitos listados, no hace falta ninguna instalación para correr este programa.\
  Una vez descargado el archivo con extensión ***.jar***, e independientemente del sistema operativo que uses, podés dirigirte a la carpeta donde está situado y abrirlo con un simple *doble click*. En caso de estar en Linux y que el programa no se abra, revisá la sección de [solución a problemas frecuentes en linux](https://github.com/akmsw/armame-el-doparti#-en-linux).\
  Una alternativa es abrir una terminal dentro de la carpeta contenedora del archivo y ejecutar el comando:
  ```bash
  java -jar nombre_del_archivo.jar
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
  - GUI mucho más cómoda, intuitiva y agradable que en versiones anteriores.
  - Arreglo de importantes bugs de la GUI.
  - Se implementaron algoritmos más eficientes para las distribuciones.
  - Se prescindió de clases y métodos que no eran vitales, mejorando significativamente la abstracción, la modularización del código, su mantenibilidad y la velocidad de ejecución del programa.
  - Se implementarion expresiones regulares para alivianar tareas.
  - Importantes cambios de refactorización.

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
  *Ventana de ingreso de nombres*

  ![ventana_anclajes](./src/main/res/img/readme/ss4.png)\
  *Ventana de anclajes*

  ![ventana_puntuaciones](./src/main/res/img/readme/ss5.png)\
  *Ventana de puntuaciones*

  ![ventana_resultados_1](./src/main/res/img/readme/ss6.png)\
  *Resultado de distribución aleatoria sin anclajes*

  ![ventana_resultados_2](./src/main/res/img/readme/ss7.png)\
  *Resultado de distribución aleatoria con tres anclajes distintos*

  ![ventana_resultados_3](./src/main/res/img/readme/ss8.png)\
  *Resultado de distribución por puntuaciones sin anclajes*
</details>