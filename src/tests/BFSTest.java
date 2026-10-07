package tests;

import negocio.Arista;
import negocio.BFS;
import negocio.GrafoMatriz;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class BFSTest {
    @Test()
    public void esConexoTest() {
        GrafoMatriz grafo = new GrafoMatriz(3);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(1, 2);
        assertTrue(BFS.esConexo(grafo));
    }

    @Test()
    public void unSoloVerticeEsConexoTest() {
        GrafoMatriz grafo = new GrafoMatriz(1);
        assertTrue(BFS.esConexo(grafo));
    }

    @Test()
    public void noEsConexoTest() {
        GrafoMatriz grafo = new GrafoMatriz(4);
        grafo.agregarAristaSinPeso(0, 3);
        grafo.agregarAristaSinPeso(1, 2);
        assertFalse(BFS.esConexo(grafo));
    }

    @Test()
    public void alcanzablesTest() {
        GrafoMatriz grafo = new GrafoMatriz(3);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(1, 2);
        Set<Integer> result = BFS.alcanzables(grafo, 0);
        Set<Integer> esperado = Set.of(0, 1, 2);
        assertTrue(result.equals(esperado));
    }

    @Test()
    public void noAlcanzablesTest() {
        GrafoMatriz grafo = new GrafoMatriz(3);
        grafo.agregarAristaSinPeso(1, 2);
        assertFalse(BFS.alcanzables(grafo, 0).isEmpty());
    }
}
