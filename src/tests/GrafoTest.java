package tests;

import negocio.AGM;
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
    public void pesoTotalTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(1, 2, 10);
        grafo.agregarArista(2, 3, 10);
        grafo.agregarArista(0, 3, 10);

        assertEquals(grafo.obtenerPesoTotal(), 40);
    }

    @Test()
    public void pesoDeAristaTest() {
        Grafo grafo = new Grafo(4);
        grafo.agregarArista(0, 1, 40);

        assertEquals(grafo.obtenerPesoArista(0, 1), Integer.valueOf(40));
    }

    @Test()
    public void compararIgualesTest() {
        Grafo grafo1 = new Grafo(4);
        grafo1.agregarArista(0, 1, 10);
        grafo1.agregarArista(1, 2, 10);
        grafo1.agregarArista(2, 3, 10);
        grafo1.agregarArista(0, 3, 10);

        Grafo grafo2 = new Grafo(4);
        grafo2.agregarArista(0, 1, 10);
        grafo2.agregarArista(1, 2, 10);
        grafo2.agregarArista(2, 3, 10);
        grafo2.agregarArista(0, 3, 10);

        assertTrue(grafo1.comparar(grafo2));
    }
}
