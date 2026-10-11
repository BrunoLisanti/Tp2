package tests;

import negocio.Grafo;
import negocio.Regiones;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class RegionesTest {
    private Grafo grafoOriginal;

    @Before
    public void setUp() {
        grafoOriginal = new Grafo(6);

        // Creamos la Región 1
        grafoOriginal.agregarArista(0, 1, 10);

        // Creamos la Región 2
        grafoOriginal.agregarArista(2, 3, 15);

        // Creamos la Región 3
        grafoOriginal.agregarArista(4, 5, 20);

        // Aristas mas pesadas que se borrarán.
        grafoOriginal.agregarArista(1, 2, 100);
        grafoOriginal.agregarArista(3, 4, 200);

        // Arista que no pertenece al agm
        grafoOriginal.agregarArista(0, 5, 500);
    }

    @Test
    public void obtenerMapaCon3RegionesTest() {
        Grafo grafoEsperado = new Grafo(6);
        grafoEsperado.agregarArista(0, 1, 10);
        grafoEsperado.agregarArista(2, 3, 15);
        grafoEsperado.agregarArista(4, 5, 20);

        Grafo resultado = Regiones.obtenerMapaConRegiones(grafoOriginal, 3);

        assertTrue(resultado.comparar(grafoEsperado));
    }

    @Test
    public void unaSolaRegionEsTodoElGrafoTest() {
        List<Set<Integer>> regiones = Regiones.dividir(grafoOriginal, 1);

        assertEquals(1, regiones.size());
        assertEquals(Set.of(0, 1, 2, 3, 4, 5), regiones.get(0));
    }

    @Test
    public void tantasRegionesComoVerticesTest() {
        assertEquals(6, Regiones.dividir(grafoOriginal, 6).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ceroRegionesTest() {
        Regiones.dividir(grafoOriginal, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void masRegionesQueVerticesTest() {
        Regiones.dividir(grafoOriginal, 7);
    }

    // Caso de borde, si las dos aristas mas pesadas tienen el mismo peso y k = 2
    // hay que sacar solo una
    @Test
    public void empateDePesosSacaSoloUnaTest() {
        Grafo grafo = new Grafo(3);
        grafo.agregarArista(0, 1, 10);
        grafo.agregarArista(1, 2, 10);

        assertEquals(2, Regiones.dividir(grafo, 2).size());
    }
}
