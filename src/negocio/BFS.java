package negocio;

import java.util.*;

public class BFS {

    private static Queue<Integer> cola;
    private static boolean[] marcados;

    public static boolean esConexo(Grafo grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo es null");
        }
        return grafo.obtenerTamano() == 0 || alcanzables(grafo, 0).size() == grafo.obtenerTamano();
    }

    public static Set<Integer> alcanzables(Grafo grafo, int origen) {
        Set<Integer> visitados = new HashSet<Integer>();

        cola  = new ArrayDeque<Integer>() ;
        marcados = new boolean[grafo.obtenerTamano()];
        cola.add(origen);

        while (!cola.isEmpty())
        {
            Integer verticeActual = cola.poll();
            marcados[verticeActual] = true;
            visitados.add(verticeActual);
            for (Integer vertice : grafo.obtenerVecinos(verticeActual)) {
                if (!marcados[vertice]) {
                    marcados[vertice] = true;
                    cola.add(vertice);
                }
            }
        }
        return visitados;
    }
}
