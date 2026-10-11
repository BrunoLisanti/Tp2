package negocio;

import java.util.*;

public class BFS {

    public static boolean esConexo(Grafo grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo es null");
        }
        return grafo.obtenerTamano() == 0 || alcanzables(grafo, 0).size() == grafo.obtenerTamano();
    }

    public static Set<Integer> alcanzables(Grafo grafo, int origen) {
        Set<Integer> visitados = new HashSet<>();

        Queue<Integer> cola = new ArrayDeque<Integer>();
        boolean[] marcados = new boolean[grafo.obtenerTamano()];
        cola.add(origen);

        while (!cola.isEmpty()) {
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

    // Cada componente conexa es el conjunto de vértices alcanzables desde un vértice
    // que todavía no quedó en ninguna componente.
    public static List<Set<Integer>> componentesConexas(Grafo grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo es null");
        }
        List<Set<Integer>> componentes = new ArrayList<>();
        Set<Integer> yaAsignados = new HashSet<>();

        for (int vertice = 0; vertice < grafo.obtenerTamano(); vertice++) {
            if (yaAsignados.contains(vertice))
                continue;

            Set<Integer> componente = alcanzables(grafo, vertice);
            componentes.add(componente);
            yaAsignados.addAll(componente);
        }
        return componentes;
    }
}
