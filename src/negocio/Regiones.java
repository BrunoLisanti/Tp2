package negocio;

import java.util.*;

public class Regiones {

    // Devuelve el AGM del grafo sin sus k - 1 aristas mas pesadas
    public static Grafo obtenerMapaConRegiones(Grafo grafo, int k) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo es null");
        }
        if (k < 1 || k > grafo.obtenerTamano()) {
            throw new IllegalArgumentException(
                    "La cantidad de regiones tiene que estar entre 1 y " + grafo.obtenerTamano());
        }

        // Construimos un árbol generador mínimo
        Grafo resultado = AGM.obtenerAGMConKruskalYUnionFind(grafo);

        // Eliminamos las k - 1 aristas de mayor peso
        for (int i = 0; i < k - 1; i++) {
            resultado.eliminarArista(resultado.obtenerAristaMasPesada());
        }
        return resultado;
    }

    // Las k componentes conexas del grafo resultante son las regiones buscadas
    public static List<Set<Integer>> dividir(Grafo grafo, int k) {
        return BFS.componentesConexas(obtenerMapaConRegiones(grafo, k));
    }
}
