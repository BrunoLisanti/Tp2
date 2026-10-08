package negocio;

import java.util.*;

public class AGM {

    public static Grafo obtenerAGMConPrimm(Grafo grafo) {

        Grafo agm = new Grafo(grafo.obtenerTamano());
        Set<Integer> marcados = new HashSet<>();
        marcados.add(0);

        //Un bucle por arista del grafo final;
        for (int i = 0; i < grafo.obtenerTamano() - 1; i++) {
            Integer verticeOrigenOptimo = null;
            Integer verticeDestinoOptimo = null;
            Integer pesoMinimo = null;
            for (Integer marcado : marcados) {
                Set<Integer> vecinos = grafo.obtenerVecinos(marcado);

                for(Integer vecino : vecinos) {
                    if (marcados.contains(vecino))
                        continue;

                    Integer pesoDeVecino = grafo.obtenerPesoArista(marcado, vecino);

                    if (pesoDeVecino == null) continue;

                    if (pesoMinimo == null || pesoDeVecino < pesoMinimo) {
                        verticeOrigenOptimo = marcado;
                        verticeDestinoOptimo = vecino;
                        pesoMinimo = pesoDeVecino;
                    }
                }
            }

            // Si faltan vértices por agregar pero no encontramos mas vecinos, el grafo no es conexo.
            if (verticeDestinoOptimo == null) {
                throw new RuntimeException("El grafo no es conexo, no es posible hallar un AGM");
            }

            agm.agregarArista(verticeOrigenOptimo, verticeDestinoOptimo, pesoMinimo);
            marcados.add(verticeDestinoOptimo);
        }
        return agm;
    }

    // TODO: Faltan chequeos de que el grafo sea conexo
    public static Grafo obtenerAGMConKruskalYBFS(Grafo grafo) {
        Grafo agm = new Grafo(grafo.obtenerTamano());

        PriorityQueue<Arista> aristasOrdenadas = grafo.obtenerAristasOrdenadas();

        for (int i = 0; i < grafo.obtenerTamano() - 1;) {
            //Obtener arista de menor peso
            Arista aristaDeMenorPeso = aristasOrdenadas.poll();
            //Chequear que no forme circuito con bfs;
            boolean formaCiclo = BFS.alcanzables(agm, aristaDeMenorPeso.getOrigen())
                                    .contains(aristaDeMenorPeso.getDestino());
            if (!formaCiclo) {
                agm.agregarArista(aristaDeMenorPeso);
                i++;
            }
        }
        return agm;
    }

    // TODO: Faltan chequeos de que el grafo sea conexo
    static public Grafo obtenerAGMConKruskalYUnionFind(Grafo grafo) {
        Grafo agm = new Grafo(grafo.obtenerTamano());

        PriorityQueue<Arista> aristasOrdenadas = grafo.obtenerAristasOrdenadas();
        int[] unionFind = inicializarUnionFind(grafo.obtenerTamano());

        for (int i = 0; i < grafo.obtenerTamano() - 1;) {
            if (aristasOrdenadas.isEmpty()) { break; }

            //Obtener arista de menor peso
            Arista aristaDeMenorPeso = aristasOrdenadas.poll();
            int raizOrigen = unionFindRaizVertice(unionFind, aristaDeMenorPeso.getOrigen());
            int raizDestino = unionFindRaizVertice(unionFind, aristaDeMenorPeso.getDestino());
            //Chequear que no forme circuito con union find;
            boolean formaCiclo = raizOrigen == raizDestino;

            if (!formaCiclo) {
                agm.agregarArista(aristaDeMenorPeso);
                unionFind[raizDestino] = raizOrigen;
                i++;
            }
        }
        return agm;
    }

    private static int[] inicializarUnionFind(int cantidadVertices) {
        int[] unionFind = new int[cantidadVertices];
        for (int i = 0; i < cantidadVertices; i++) {
            unionFind[i] = i;
        }
        return unionFind;
    }

    private static int unionFindRaizVertice(int[] unionFind, int vertice) {
        while(unionFind[vertice] != vertice)
            vertice = unionFind[vertice];

        return vertice;
    }


}
