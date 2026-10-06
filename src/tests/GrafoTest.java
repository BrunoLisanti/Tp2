package tests;

import negocio.Arista;
import negocio.BFS;
import negocio.Grafo;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GrafoTest {
    @Test()
    public void agregarAristaTest() {
        Grafo grafo = new Grafo(2);
        grafo.agregarAristaSinPeso(0, 1);
        assertTrue(grafo.existeArista(0, 1));
    }

    @Test()
    public void eliminarAristaTest() {
        Grafo grafo = new Grafo(2);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.eliminarArista(0, 1);
        assertFalse(grafo.existeArista(0, 1));
    }

    @Test()
    public void obtenerVecinosTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(0, 2);
        Set<Integer> vecinos = grafo.obtenerVecinos(0);
        Set<Integer> esperado = Set.of(1, 2);
        assertEquals(vecinos, esperado);
    }

    @Test()
    public void pesoDeUnaAristaTest() {
        Grafo grafo = new Grafo(2);
        grafo.agregarArista(0, 1, 10);
        assertEquals(grafo.obtenerPeso(), 10);
    }

    @Test()
    public void pesoSumadoDeAristasTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(1, 2, 10);
        grafo.agregarArista(2, 3, 10);
        grafo.agregarArista(0, 3, 10);
        assertEquals(grafo.obtenerPeso(), 40);
    }
}