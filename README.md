# Armame el doparti v3.0 ⚽ [WIP]

![cover_img](https://user-images.githubusercontent.com/66426042/123534140-5830fd00-d6f1-11eb-8efc-ab0822087b31.png)

```
🔜 Release date: TBD.
```

Este programa ofrece una intuitiva y rápida manera de generar equipos para partidos de fútbol con 7 u 8 jugadores por equipo.

## Modo de uso 📝

El usuario podrá generar sus equipos ingresando los nombres de los jugadores a sortear en cada posición.

Un requisito de desarrollo fue no incluir la posición *"Arquero"* y reemplazarla por *"Comodín"*. El jugador de tipo *Comodín* podrá ocupar la posición de *Arquero* o intercambiar con cualquier otro jugador que prefiera atajar.

- 📋 Si cada equipo consta de 7 jugadores, la distribución será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 2
  - **Delanteros**: 1
  - **Comodines**: 1
- 📋 Si cada equipo consta de 8 jugadores, la distribución será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 3
  - **Delanteros**: 1
  - **Comodines**: 1

Una vez ingresados los nombres de todos los jugadores a repartir en cada posición para ambos equipos, se podrá seleccionar si distribuirlos de manera aleatoria, o en base a una puntuación. Si se elige la segunda opción, le será solicitado al usuario una puntuación de 1 ***(mal jugador)*** a 5 ***(excelente jugador)*** para cada jugador ingresado. Finalmente, se armarán los equipos de la manera más equitativa posible.

Claramente, si los jugadores se reparten en base a sus puntuaciones, la distribución óptima será única. Si se decide distribuirlos de manera aleatoria, se podrán redistribuir tantas veces como se quiera hasta estar conforme.

## Nuevas funcionalidades ⭐

El usuario ahora podrá anclar jugadores 🔗. El objetivo de esta funcionalidad es la de indicarle al programa que al menos 2 jugadores seleccionados por el usuario deberán pertenecer al mismo equipo. El número máximo posible de jugadores a anclar será 6 (si se opta por *fútbol 7*) ó 7 (si se opta por *fútbol 8*).  

Para esto, se proporciona un checkbox rotulado con el texto "*Anclar jugadores*" en la ventana de ingreso de nombres. Si el usuario tilda este checkbox, y luego de seleccionar el método de distribución de jugadores, se le presentará una ventana en la que habrá una lista con todos los nombres ingresados y su respectivo checkbox. Los jugadores cuyo checkbox esté tildado serán anclados al mismo equipo.

Por obvias razones no se podrán anclar a un mismo equipo todos los jugadores de un mismo tipo (si se anclan todos los mediocampistas para un mismo equipo, el otro equipo no tendrá mediocampistas y esto no es posible).

## Mejoras 🔧

- ✔️ GUI mucho más cómoda, intuitiva y agradable que en versiones anteriores.
- ✔️ Implementación de expresiones regulares para alivianar procesos de búsqueda.
- ✔️ Se arreglaron partes del código hardcodeadas que condicionaban la GUI y el algoritmo de distribución.
- ✔️ Se logró un nivel de abstracción mayor que permitió prescindir de clases y métodos que no eran vitales.
- ✔️ Se lograron mejoras significativas en la modularización del código, mejorando la velocidad de ejecución del programa.
- ✔️ Se refactorizaron métodos y clases completas, favoreciendo enormemente la mantenibilidad del código.
- ✔️ Arreglo de importantes bugs de la GUI.
