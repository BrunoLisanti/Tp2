package tests;

import negocio.AGM;
import negocio.GrafoMatriz;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AGMTests {

    @Test
    public void obtenerAGMConPrimmTest() {
        GrafoMatriz grafo = new GrafoMatriz(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(0, 2, 10);
        grafo.agregarArista(2, 3, 40);
        grafo.agregarArista(1, 3, 30);

        GrafoMatriz agmCorrecto = new GrafoMatriz(4);
        agmCorrecto.agregarArista(0, 1, 10);
        agmCorrecto.agregarArista(0, 2, 10);
        agmCorrecto.agregarArista(1, 3, 30);

        GrafoMatriz agmAEvaluar = AGM.obtenerAGMConPrimm(grafo);
        assertTrue(agmAEvaluar.comparar(agmCorrecto));
    }
}
