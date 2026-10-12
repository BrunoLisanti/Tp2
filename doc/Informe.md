<div style="height: 90vh; display: flex; flex-direction: column; justify-content: space-between; text-align: center;"> 
	<div> 
		<h1>Universidad Nacional General Sarmiento</h1> 
		<h3>Licenciatura en Sistemas</h3> 
	</div> 
	<div> 
		<h1 style="font-size: 2.2rem;">Diseñando regiones</h1> 
		<p style="color: #666;">Trabajo Práctico 2</p> 
	</div>
	<div style="text-align: right;"> 
		<p> <strong>Integrantes:</strong> Bruno Lisanti, Mateo Lauzan, Martín Godoy, Santino Tettamanti </p>
		<p><strong>Materia:</strong> Programación III </p> 
		<p><strong>Docentes:</strong> Daniel Bertaccini, Patricia Bagnes, Gonzalo Faccini</p>
		<p><strong>Fecha:</strong> 11 de Octubre de 2026</p> 
	</div> 
</div> <div style="page-break-after: always;"></div>

### El problema

Tenemos un grafo donde cada vértice es una provincia y cada arista une dos provincias limítrofes. El peso de la arista es la similaridad entre las dos provincias, y es un dato que carga el usuario. Para dividir el país en k regiones conexas seguimos los pasos de la consigna.

1. Construir un árbol generador mínimo del grafo.
2. Eliminar las k - 1 aristas de mayor peso de ese árbol.
3. Las k componentes conexas que quedan son las regiones.

Ningún dato del problema está escrito en el código. Las provincias, las similaridades y la cantidad de regiones las ingresa el usuario desde la ventana o desde un archivo. El mapa de Argentina del enunciado lo dejamos como un archivo de ejemplo en la carpeta ```datos```, que se abre con el botón "Abrir archivo...".

### Arquitectura elegida

Igual que en el TP1 usamos **forms and controls**. La ventana tiene pocos eventos (agregar una provincia, agregar o borrar una similaridad, calcular, abrir y guardar) y cada uno se resuelve con una o dos llamadas a la clase ```Pais```, así que no nos pareció necesario agregar un controlador o un presentador en el medio. Por ejemplo, este es el código que se ejecuta al apretar "Calcular regiones".

```java
private void calcularRegiones(boolean pedidoPorElUsuario) {
    try {
        List<List<String>> regiones = pais.calcularRegiones((Integer) spnRegiones.getValue());
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < regiones.size(); i++) {
            ...
        }
        txtRegiones.setText(texto.toString());
    } catch (IllegalStateException | IllegalArgumentException ex) {
        ...
    }
}
```

La ventana no sabe nada del árbol generador mínimo ni de los índices de los vértices. Le pasa el k a ```Pais``` y recibe una lista de regiones con los nombres de las provincias, que lo único que hace es mostrar.

Las capas están separadas en dos paquetes, ```negocio``` e ```interfaz```, y los tests en un tercero, ```tests```. Ninguna clase de ```negocio``` importa ```javax.swing```, ```java.awt``` ni nada de ```interfaz```, se puede comprobar mirando los imports. Las dependencias van siempre de la interfaz hacia el negocio. Las validaciones (provincias repetidas, una provincia vecina de si misma, un k fuera de rango, un mapa que no es conexo) también están en el negocio, y la ventana solo muestra el mensaje de la excepción.

### Clases utilizadas

- ```Grafo``` representa un grafo con pesos en las aristas usando una matriz de adyacencia. Permite agregar y eliminar aristas, consultar vecinos y pesos, y obtener las aristas ordenadas por peso o la más pesada.
- ```Arista``` modela una arista con su origen, su destino y su peso. La usan Kruskal para recorrer las aristas de menor a mayor peso y ```Regiones``` para sacar la más pesada.
- ```AGM``` tiene los tres algoritmos de árbol generador mínimo que vimos en clase, Prim, Kruskal con BFS y Kruskal con Union-Find. Si el grafo no es conexo tiran una excepción, porque en ese caso no existe un árbol generador.
- ```BFS``` recorre el grafo a lo ancho. Lo usamos para saber si el grafo es conexo, para detectar ciclos en una de las versiones de Kruskal y para encontrar las componentes conexas, que son las regiones.
- ```Regiones``` aplica el método de la consigna. Arma el AGM, le saca las k - 1 aristas más pesadas y devuelve las componentes conexas. Trabaja con índices, no sabe nada de provincias.
- ```Similaridad``` guarda la similaridad entre dos provincias con sus nombres. No tiene setters, una vez creada no se puede cambiar.
- ```Pais``` es la clase con la que habla la interfaz. Guarda las provincias por nombre y las similaridades, valida lo que se carga, y al calcular arma el ```Grafo``` traduciendo cada nombre a un índice. Así el resto del negocio puede trabajar con números y la interfaz con nombres.
- ```ArchivoPais``` lee y guarda un ```Pais``` en un archivo de texto.
- ```MainForm``` es la ventana. Tiene tres partes, una para cargar provincias, otra para las similaridades y otra para elegir k y ver las regiones.

### Representación elegida

Para el grafo usamos una **matriz de adyacencia** de ```Integer```, donde cada celda tiene el peso de la arista o ```null``` si no hay arista. Usamos ```Integer``` y no ```int``` porque un peso 0 es válido y necesitábamos distinguirlo de "no hay arista".

| Operación | Costo con matriz |
|---|---|
| Agregar o eliminar una arista | O(1) |
| Saber si existe una arista o cuánto pesa | O(1) |
| Obtener los vecinos de un vértice | O(n) |
| Memoria | O(n²) |

La desventaja de la matriz es la memoria y que obtener los vecinos cuesta O(n) aunque el vértice tenga pocos. Con listas de vecinos eso sería O(grado), pero consultar el peso de una arista dejaría de ser O(1), y Prim lo consulta todo el tiempo. Como un país tiene pocas provincias (Argentina tiene 23), una matriz de 23 x 23 no es un problema de memoria, y preferimos que las consultas de peso sean directas.

Para las regiones calculamos el AGM con **Kruskal y Union-Find**. Ordenar las aristas con una cola de prioridad cuesta O(m log m), y cada consulta a Union-Find cuesta como mucho O(n) en nuestra versión, que no tiene compresión de caminos. Después se saca k - 1 veces la arista más pesada, recorriendo la matriz cada vez, O(k · n²), y BFS encuentra las componentes en O(n²) porque pide los vecinos con la matriz. Con los tamaños de este problema todo esto es instantáneo.

En ```Pais``` las provincias se guardan en una lista y el índice de cada una en el grafo es su posición en la lista. Buscar ese índice cuesta O(n), pero solo se hace una vez por similaridad al armar el grafo.

### Buenas prácticas

- **Encapsulamiento.** ```Pais``` devuelve sus listas con ```Collections.unmodifiableList```, así nadie puede agregar una provincia desde afuera sin pasar por las validaciones de ```agregarProvincia```.

```java
public List<String> obtenerProvincias() {
    return Collections.unmodifiableList(_provincias);
}
```

- **Objetos que no cambian.** ```Similaridad``` tiene sus atributos ```final``` y no tiene setters. Una similaridad que ya está guardada no se puede modificar por error, si el usuario cambia el peso se reemplaza por una nueva.
- **Una responsabilidad por clase.** Al principio la función que armaba las regiones estaba dentro de ```Grafo``` y siempre sacaba dos aristas (k = 3 fijo). La pasamos a la clase ```Regiones``` y ahora recibe k, porque ```Grafo``` tiene que servir para cualquier problema de grafos y no saber nada de regiones.
- **Validar en el negocio.** Todas las reglas (nombres vacíos o repetidos, pesos negativos, provincias que no existen, k entre 1 y la cantidad de provincias) están en ```Pais``` y ```Regiones```. La interfaz atrapa la excepción y muestra el mensaje en un cartel.
- **Errores con contexto.** Si un archivo tiene un error, el mensaje dice en qué línea está, por ejemplo "Línea 5. El peso tiene que ser un número entero".
- **Tests.** Todas las clases de negocio tienen su clase de test con JUnit 4, en total 38 tests. Seguimos lo que vimos en clase, para cada método probamos el caso normal, las excepciones que lanza y los casos de borde, sin repetir tests que prueban lo mismo. Algunos bordes que probamos son k = 1 (una sola región con todo el país), k igual a la cantidad de provincias (cada provincia es una región), dos aristas con el mismo peso cuando hay que sacar solo una, un país con una sola provincia, un archivo vacío y un grafo que no es conexo.

### Interfaz

- **La ventana está dividida en tres pasos numerados**, provincias, similaridades y regiones, en el orden en que se usan.
- **Las similaridades se cargan eligiendo las provincias de dos listas desplegables**, así no hay que escribir los nombres de nuevo y no se puede cargar una similaridad con una provincia que no existe.
- **Si se carga una similaridad que ya existía, se reemplaza el peso.** Por eso el botón dice "Agregar / modificar".
- **Las regiones se muestran en una lista**, con el nombre de cada región y abajo sus provincias.
- **Si se escribe un k que no es un número o que es mayor a la cantidad de provincias**, aparece un cartel que dice cuál es el rango válido. El rango lo controla el negocio, la ventana solo revisa que sea un número.
- **Con Enter en el campo de k también se calculan las regiones**, igual que con Enter en el campo de provincia se agrega.
- **Al lado del campo de similaridad hay un tooltip** que aclara que un número bajo significa provincias más parecidas.
- **Los errores se muestran en un cartel** con un mensaje que explica qué pasó, por ejemplo cuando el mapa no es conexo.
- **La ventana mide 1100 x 650 y no se puede agrandar**, para no pasar el límite de 1366 x 768 de la consigna.

### Decisiones tomadas durante el desarrollo

- **Qué algoritmo de AGM usar.** Tenemos implementados los tres que vimos en clase y los tres dan el mismo peso en los tests. Para las regiones usamos Kruskal con Union-Find, porque detecta los ciclos sin recorrer el grafo cada vez como hace la versión con BFS.
- **Qué pasa si el mapa no es conexo.** Si faltan similaridades y quedan provincias sueltas no existe un árbol generador, así que no calculamos nada y avisamos que faltan similaridades.
- **Qué significa el peso.** El método arma un árbol generador **mínimo** y después corta las aristas **más pesadas**, así que trata al peso como una distancia. Un número bajo quiere decir que las dos provincias se parecen mucho y un número alto que son muy distintas. Lo aclaramos en un tooltip al lado del campo de similaridad y los pesos del archivo de ejemplo siguen ese criterio.
- **Empates de peso.** Si dos aristas del árbol tienen el mismo peso y hay que sacar solo una, se saca cualquiera de las dos. La consigna no dice nada y las dos opciones dan regiones válidas.
- **Pesos negativos.** No los permitimos, porque la similaridad es una medida y no tiene sentido que sea negativa.
- **Por qué existe la clase ```Pais```.** El ```Grafo``` trabaja con índices de tamaño fijo, pero el usuario carga las provincias de a una y por nombre. ```Pais``` guarda los nombres y arma el grafo recién cuando hace falta calcular, así la interfaz nunca maneja índices.
- **No permitimos borrar provincias.** Borrar una provincia cambiaría los índices de todas las que están después. Si se carga una mal, se puede empezar de nuevo con "Borrar todo" o corregir el archivo.

### Objetivos opcionales

De los tres objetivos opcionales implementamos dos.

- **Cargar los datos desde un archivo.** Con "Abrir archivo..." se carga un país desde un archivo de texto y con "Guardar archivo..." se guarda lo que se cargó a mano. El formato es simple, una sección ```PROVINCIAS``` con un nombre por línea y una sección ```SIMILARIDADES``` con una línea ```provincia1,provincia2,peso``` por cada par de vecinas. Las líneas que empiezan con ```#``` son comentarios. En ```datos/argentina.txt``` dejamos el mapa del enunciado con similaridades inventadas.

```
PROVINCIAS
Jujuy
Salta
...

SIMILARIDADES
Jujuy,Salta,10
...
```

- **Recalcular las regiones cada vez que se modifica el grafo.** Si se marca "Recalcular al modificar el mapa", las regiones se vuelven a calcular cada vez que se agrega o borra algo. Si en ese momento el mapa no es conexo no se muestra un cartel, para no interrumpir la carga, y el aviso aparece en la lista de regiones.

No implementamos el mapa porque no es obligatorio y preferimos dedicarle el tiempo a los tests y a la interfaz.
