package tests;

import negocio.BFS;
import negocio.Grafo;
import org.junit.Test;

import java.util.List;
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

    @Test()
    public void componentesConexasTest() {
        Grafo grafo = new Grafo(5);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(2, 3);
        List<Set<Integer>> componentes = BFS.componentesConexas(grafo);

        assertEquals(3, componentes.size());
        assertTrue(componentes.contains(Set.of(0, 1)));
        assertTrue(componentes.contains(Set.of(2, 3)));
        assertTrue(componentes.contains(Set.of(4)));
    }

}
