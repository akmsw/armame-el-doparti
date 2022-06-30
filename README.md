# Armame el doparti v3.0 ⚽

![cover](./src/main/res/img/cover.png)

```
Release date: TBD
```

## 🔎 ¿Qué es?
Este programa ofrece una intuitiva y rápida manera de generar equipos para partidos de fútbol 7 u 8.

## 📦 Requisitos
### ☕ Java
- #### 🟡 Versión mínima
    - Java 11.
- #### 🟢 Versión recomendada
    - Java 17 o más reciente.

## 🛠️ Instalación
[TODO]

## 📝 ¿Cómo se usa?

El usuario podrá generar sus equipos ingresando los nombres de los jugadores a sortear en cada posición.

- Si cada equipo consta de 7 jugadores, la distribución para los mismos será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 2
  - **Delanteros**: 1
  - **Arqueros**: 1
- Si cada equipo consta de 8 jugadores, la distribución para los mismos será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 3
  - **Delanteros**: 1
  - **Arqueros**: 1

Una vez ingresados los nombres de todos los jugadores a repartir en cada posición para ambos equipos, se podrá seleccionar si distribuirlos de manera aleatoria, o en base a una puntuación. Si se elige la segunda opción, le será solicitado al usuario una puntuación de 1 ***(mal jugador)*** a 5 ***(excelente jugador)*** para cada jugador ingresado. Finalmente, se armarán los equipos de la manera más equitativa posible.

Si los jugadores se reparten en base a sus puntuaciones, la distribución óptima será única. Si se decide distribuirlos de manera aleatoria, se podrán redistribuir tantas veces como se quiera hasta estar conforme.

## ⭐ Nuevas funcionalidades
### 🔗 Anclaje de jugadores
El objetivo de esta funcionalidad es la de indicarle al programa que al menos 2 jugadores seleccionados por el usuario deberán pertenecer al mismo equipo sin importar la distribución que se elija para el resto. El número máximo posible de jugadores a anclar a un mismo equipo será 6 (si se opta por *fútbol 7*) ó 7 (si se opta por *fútbol 8*). El programa garantiza que siempre queden al menos dos jugadores sin anclar para poder realizar alguna distribución.\
Para esto, se proporciona un checkbox rotulado con el texto "*Anclar jugadores*" en la ventana de ingreso de nombres. Si el usuario tilda este checkbox, luego de seleccionar el método de distribución de jugadores se le presentará una ventana en la que habrá una lista con todos los nombres ingresados y su respectivo checkbox. Los jugadores cuyo checkbox esté tildado serán anclados al mismo equipo.\
No se podrán anclar a un mismo equipo todos los jugadores de un mismo tipo (por ejemplo, si se anclan todos los mediocampistas para un mismo equipo, el otro equipo no tendrá mediocampistas y esto no es posible). Lo mismo sucede con anclar a un mismo equipo más de la mitad de jugadores registrados para una posición particular.

## ✅ Mejoras

- GUI mucho más cómoda, intuitiva y agradable que en versiones anteriores.
- Arreglo de importantes bugs de la GUI.
- Se arreglaron partes del código hardcodeadas que condicionaban la GUI y los algoritmos de distribución.
- Se prescindió de clases y métodos que no eran vitales, mejorando significativamente la modularización del código, su mantenibilidad y la velocidad de ejecución del programa.
- Implementación de expresiones regulares para alivianar tareas.
- Importantes cambios de refactorización.

## 🔜 Próximamente
- Se agregarán las opciones de 5 y 9 jugadores por equipo.
- El usuario podrá optar por ingresar una distribución propia de jugadores, o utilizar las provistas por defecto.

## 📸 Capturas de pantalla
![ventana_principal](./src/main/res/img/ss1.png)\
*Ventana principal*

![ventana_ingreso](./src/main/res/img/ss2.png)\
*Ventana de ingreso de nombres*

![ventana_anclajes](./src/main/res/img/ss3.png)\
*Ventana de anclajes*

![ventana_puntuaciones](./src/main/res/img/ss4.png)\
*Ventana de puntuaciones*

![ventana_resultados1](./src/main/res/img/ss5.png)\
*Ventana de muestra de resultados*