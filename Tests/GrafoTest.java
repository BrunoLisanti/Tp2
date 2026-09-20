package Tests;

import Negocio.Grafo;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GrafoTest {
    @Test()
    public void agregarAristaTest() {
        Grafo grafo = new Grafo(2);
        grafo.agregarArista(0, 1);
        assertTrue(grafo.existeArista(0, 1));
    }

    @Test()
    public void eliminarAristaTest() {
        Grafo grafo = new Grafo(2);
        grafo.agregarArista(0, 1);
        grafo.eliminarArista(0, 1);
        assertFalse(grafo.existeArista(0, 1));
    }

    @Test()
    public void obtenerVecinosTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 2);
        Set<Integer> vecinos = grafo.obtenerVecinos(0);
        Set<Integer> esperado = Set.of(1, 2);
        assertEquals(vecinos, esperado);
    }

    @Test()
    public void esConexoTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        assertTrue(grafo.esConexo());
    }

    @Test()
    public void unSoloVerticeEsConexoTest() {
        Grafo grafo = new Grafo(1);
        assertTrue(grafo.esConexo());
    }

    @Test()
    public void noEsConexoTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(2, 3);
        assertFalse(grafo.esConexo());
    }
}