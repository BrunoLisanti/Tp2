package tests;

import negocio.AGM;
import negocio.Grafo;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AGMTests {

    @Test
    public void obtenerAGMConPrimmTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(0, 2, 10);
        grafo.agregarArista(2, 3, 40);
        grafo.agregarArista(1, 3, 30);

        Grafo agmCorrecto = new Grafo(4);
        agmCorrecto.agregarArista(0, 1, 10);
        agmCorrecto.agregarArista(0, 2, 10);
        agmCorrecto.agregarArista(1, 3, 30);

        Grafo agmAEvaluar = AGM.obtenerAGMConPrimm(grafo);
        assertTrue(agmAEvaluar.comparar(agmCorrecto));
    }

    @Test
    public void obtenerAGMConKruskalYBFSTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(0, 2, 10);
        grafo.agregarArista(2, 3, 40);
        grafo.agregarArista(1, 3, 30);

        Grafo agmCorrecto = new Grafo(4);
        agmCorrecto.agregarArista(0, 1, 10);
        agmCorrecto.agregarArista(0, 2, 10);
        agmCorrecto.agregarArista(1, 3, 30);

        Grafo agmAEvaluar = AGM.obtenerAGMConKruskalYBFS(grafo);
        assertTrue(agmAEvaluar.comparar(agmCorrecto));
    }

    @Test
    public void obtenerAGMConKruskalYUnionFindTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(0, 2, 10);
        grafo.agregarArista(2, 3, 40);
        grafo.agregarArista(1, 3, 30);

        Grafo agmCorrecto = new Grafo(4);
        agmCorrecto.agregarArista(0, 1, 10);
        agmCorrecto.agregarArista(0, 2, 10);
        agmCorrecto.agregarArista(1, 3, 30);

        Grafo agmAEvaluar = AGM.obtenerAGMConKruskalYUnionFind(grafo);
        assertTrue(agmAEvaluar.comparar(agmCorrecto));
    }
}
