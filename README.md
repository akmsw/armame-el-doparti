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
  - **Comodines***: 1
- Si cada equipo consta de 8 jugadores, la distribución será:
  - **Defensores centrales**: 1
  - **Defensores laterales**: 2
  - **Mediocampistas**: 3
  - **Delanteros**: 1
  - **Comodines***: 1

Un requisito de desarrollo fue no incluir la posición *"Arquero"* y reemplazarla por *"Comodín"*. El jugador de tipo "Comodín" podrá ocupar la posición de Arquero, o intercambiar con cualquier otro jugador que prefiera atajar.

Una vez ingresados los nombres de todos los jugadores a repartir en cada posición para ambos equipos, se podrá seleccionar si distribuirlos de manera aleatoria, o en base a una puntuación. Si se elige la segunda opción, le será solicitado al usuario una puntuación de 1 (malo) a 4 (excelente) para cada jugador ingresado. Finalmente, se repartirán los jugadores de la manera más equitativa posible.

Claramente, si los jugadores se reparten en base a sus puntuaciones, la distribución será única. Si se decide distribuirlos de manera aleatoria, se podrán redistribuir tantas veces como se quiera hasta estar conforme.

### Mejoras [WIP]

- Se deja el trabajo pesado de distribución de jugadores a un archivo en lenguaje de programación de bajo nivel (C).
- Se logró prescindir de ciertas clases que eran innecesarias.
- Se refactorizaron métodos y clases completas.
- Arreglo de bugs de la GUI.