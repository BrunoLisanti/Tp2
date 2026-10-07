package tests;

import negocio.GrafoMatriz;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GrafoMatrizTest {
    @Test()
    public void agregarAristaTest() {
        GrafoMatriz grafo = new GrafoMatriz(2);
        grafo.agregarAristaSinPeso(0, 1);

        assertTrue(grafo.existeArista(0, 1));
    }

    @Test()
    public void eliminarAristaTest() {
        GrafoMatriz grafo = new GrafoMatriz(2);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.eliminarArista(0, 1);

        assertFalse(grafo.existeArista(0, 1));
    }

    @Test()
    public void obtenerVecinosTest() {
        GrafoMatriz grafo = new GrafoMatriz(3);
        grafo.agregarAristaSinPeso(0, 1);
        grafo.agregarAristaSinPeso(0, 2);
        Set<Integer> vecinos = grafo.obtenerVecinos(0);
        Set<Integer> esperado = Set.of(1, 2);

        assertEquals(vecinos, esperado);
    }

    @Test()
    public void pesoTotalTest() {
        GrafoMatriz grafo = new GrafoMatriz(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(1, 2, 10);
        grafo.agregarArista(2, 3, 10);
        grafo.agregarArista(0, 3, 10);

        assertEquals(grafo.obtenerPesoTotal(), 40);
    }

    @Test()
    public void pesoDeAristaTest() {
        GrafoMatriz grafo = new GrafoMatriz(4);
        grafo.agregarArista(0, 1, 10);

        assertEquals(grafo.obtenerPesoArista(0, 1), Integer.valueOf(40));
    }

    @Test()
    public void compararIgualesTest() {
        GrafoMatriz grafo1 = new GrafoMatriz(4);
        grafo1.agregarArista(0, 1, 10);
        grafo1.agregarArista(1, 2, 10);
        grafo1.agregarArista(2, 3, 10);
        grafo1.agregarArista(0, 3, 10);

        GrafoMatriz grafo2 = new GrafoMatriz(4);
        grafo2.agregarArista(0, 1, 10);
        grafo2.agregarArista(1, 2, 10);
        grafo2.agregarArista(2, 3, 10);
        grafo2.agregarArista(0, 3, 10);

        assertTrue(grafo1.comparar(grafo2));
    }
}