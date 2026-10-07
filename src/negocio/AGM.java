package negocio;

import java.util.HashSet;
import java.util.Set;

public class AGM {
    public static GrafoMatriz obtenerAGMConPrimm(GrafoMatriz grafo) {

        GrafoMatriz agm = new GrafoMatriz(grafo.obtenerTamano());
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
}
