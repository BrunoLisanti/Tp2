¿Qué hay que hacer?
Hay que hacer un programa donde quede representado un grafo G, sus vértices representarían las provincias de un país, cada arista contaría con un peso, hasta ahora todo está dado por el usuario.

Se necesita que el programa divida el país en k regiones conexas.
Esto se logra:
1. Construir un árbol generador mínimo T de G.
2. Eliminar las k - 1 aristas de payor peso de T.
De esta manera quedarán K componentes conexas del grafo, que son las regiones buscadas.

Cosas para hacer
- Implementar en la clase grafo el algoritmo para hallar el arbol generador mínimo
- Implementar en la clase grafo el concepto de peso.