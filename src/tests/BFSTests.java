package tests;

import negocio.BFS;
import negocio.Grafo;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class BFSTests {
    @Test()
    public void esConexoTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(1, 2);
        assertTrue(BFS.esConexo(grafo));
    }

    @Test()
    public void unSoloVerticeEsConexoTest() {
        Grafo grafo = new Grafo(1);
        assertTrue(BFS.esConexo(grafo));
    }

    @Test()
    public void noEsConexoTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarAristaSinPeso(0, 3);
        grafo.agregarAristaSinPeso(1, 2);
        assertFalse(BFS.esConexo(grafo));
    }

    @Test()
    public void alcanzablesTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(1, 2);
        Set<Integer> result = BFS.alcanzables(grafo, 0);
        Set<Integer> esperado = Set.of(0, 1, 2);
        assertTrue(result.equals(esperado));
    }

    @Test()
    public void noAlcanzablesTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarAristaSinPeso(1, 2);
        assertFalse(BFS.alcanzables(grafo, 0).isEmpty());
    }
}
