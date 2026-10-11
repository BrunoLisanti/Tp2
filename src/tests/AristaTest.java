package tests;

import negocio.Arista;
import org.junit.Test;

import static org.junit.Assert.*;

public class AristaTest {

    @Test
    public void igualesEnCualquierOrdenTest() {
        Arista arista1 = new Arista(0, 1, 10);
        Arista arista2 = new Arista(1, 0, 10);

        assertEquals(arista1, arista2);
        assertEquals(arista1.hashCode(), arista2.hashCode());
    }

    @Test
    public void compararPorPesoTest() {
        Arista liviana = new Arista(0, 1, 5);
        Arista pesada = new Arista(2, 3, 50);

        assertTrue(liviana.compareTo(pesada) < 0);
        assertTrue(pesada.compareTo(liviana) > 0);
        assertEquals(0, liviana.compareTo(new Arista(4, 5, 5)));
    }
}
