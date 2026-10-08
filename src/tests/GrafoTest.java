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

    @Test
    public void testObtenerAGMConGrafoConexo() {
        // Arrange: Grafo con 4 vértices (del 0 al 3)
        Grafo grafo = new Grafo(4); // Ajusta la creación según tu constructor

        // Aristas que DEBEN formar el AGM (peso total: 60)
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(1, 2, 20);
        grafo.agregarArista(2, 3, 30);

        // Aristas trampa: Forman ciclos y son más pesadas, deben ser ignoradas
        grafo.agregarArista(0, 2, 100);
        grafo.agregarArista(0, 3, 200);

        // Act
        Grafo agm = AGM.obtenerAGMConKruskalYUnionFind(grafo);


        // Verificamos que se seleccionaron las aristas correctas
        assertTrue(agm.existeArista(0, 1));
        assertTrue(agm.existeArista(1, 2));
        assertTrue(agm.existeArista(2, 3));

        // Verificamos que las aristas pesadas que forman ciclo no están
        assertFalse(agm.existeArista(0, 2));
        assertFalse(agm.existeArista(0, 3));
    }
}