# Fiesta de fulbito v3.0
```
Fecha de release: TBD.
```

Este programa ofrece una intuitiva y rápida manera de generar equipos para partidos de fútbol 7 y fútbol 8.

## Modo de uso

El usuario podrá generar sus equipos ingresando los nombres de los jugadores a sortear en cada posición.
Para comenzar, se necesitarán 14 posibles jugadores. La distribución utilizada para cada equipo dependerá de la cantidad de jugadores por equipo.

- Si cada equipo consta de 7 jugadores, la distribución será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 2
  - **Delanteros**: 1
  - **Comodines**: 1
- Si cada equipo consta de 8 jugadores, la distribución será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 3
  - **Delanteros**: 1
  - **Comodines**: 1

Un requisito de desarrollo fue no incluir la posición *"Arquero"* y reemplazarla por *"Comodín"*. El jugador de tipo *Comodín* podrá ocupar la posición de *Arquero*, o intercambiar con cualquier otro jugador que prefiera atajar.

Una vez ingresados los nombres de todos los jugadores a repartir en cada posición para ambos equipos, se podrá seleccionar si distribuirlos de manera aleatoria, o en base a una puntuación. Si se elige la segunda opción, le será solicitado al usuario una puntuación de 1 ***(malo)*** a 4 ***(excelente)*** para cada jugador ingresado. Finalmente, se repartirán los jugadores de la manera más equitativa posible.

Claramente, si los jugadores se reparten en base a sus puntuaciones, la distribución será única. Si se decide distribuirlos de manera aleatoria, se podrán redistribuir tantas veces como se quiera hasta estar conforme.

## Nuevas funcionalidades

El usuario ahora podrá anclar jugadores a un mismo equipo. El objetivo de esta funcionalidad es la de indicarle al programa que al menos 2 (y no más de 6) jugadores seleccionados por el usuario deberán pertenecer al mismo equipo.

Para esto, se proporciona un checkbox rotulado con el texto "Anclar jugadores" en la ventana de ingreso de nombres. Si el usuario tilda este checkbox, y luego de seleccionar el método de distribución de jugadores, se le presentará una ventana en la que habrá una lista con todos los nombres de los jugadores ingresados y su respectivo checkbox. Los jugadores cuyo checkbox esté tildado serán anclados al mismo equipo.

Por obvias razones no se podrán anclar a un mismo equipo todos los jugadores de un mismo tipo.

## Mejoras

- Implementación de expresiones regulares para alivianar procesos de búsqueda.
- Se arreglaron partes del código hardcodeadas que condicionaban la GUI y el algoritmo de distribución.
- Se logró un nivel de abstracción mayor que permitió prescindir de clases y métodos que no eran vitales.
- Se lograron mejoras significativas en la modularización del código, mejorando la velocidad de ejecución del programa.
- Se refactorizaron métodos y clases completas.
- Arreglo de importantes bugs de la GUI.