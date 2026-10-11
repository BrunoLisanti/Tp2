package tests;

import negocio.Pais;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PaisTest {
    private Pais pais;

    @Before
    public void setUp() {
        pais = new Pais();
        pais.agregarProvincia("Jujuy");
        pais.agregarProvincia("Salta");
        pais.agregarProvincia("Tucumán");
        pais.agregarProvincia("Misiones");
        pais.agregarProvincia("Corrientes");
    }

    @Test
    public void agregarProvinciaTest() {
        assertEquals(5, pais.cantidadDeProvincias());
        assertTrue(pais.obtenerProvincias().contains("Salta"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void provinciaRepetidaTest() {
        pais.agregarProvincia("Salta");
    }

    @Test(expected = IllegalArgumentException.class)
    public void provinciaVaciaTest() {
        pais.agregarProvincia("   ");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void noSePuedeModificarLaListaDeAfueraTest() {
        pais.obtenerProvincias().add("Chaco");
    }

    @Test
    public void agregarSimilaridadRepetidaLaReemplazaTest() {
        pais.agregarSimilaridad("Jujuy", "Salta", 10);
        pais.agregarSimilaridad("Salta", "Jujuy", 30);

        assertEquals(1, pais.obtenerSimilaridades().size());
        assertEquals(30, pais.obtenerSimilaridades().get(0).getPeso());
    }

    @Test
    public void calcularRegionesTest() {
        cargarSimilaridades();

        List<List<String>> regiones = pais.calcularRegiones(2);

        assertEquals(2, regiones.size());
        assertTrue(regiones.contains(List.of("Jujuy", "Salta", "Tucumán")));
        assertTrue(regiones.contains(List.of("Corrientes", "Misiones")));
    }

    @Test(expected = IllegalStateException.class)
    public void calcularRegionesSinSerConexoTest() {
        pais.agregarSimilaridad("Jujuy", "Salta", 10);
        pais.calcularRegiones(2);
    }

    // Caso de borde, con una sola provincia la única región es ella misma
    @Test
    public void unaSolaProvinciaTest() {
        Pais paisChico = new Pais();
        paisChico.agregarProvincia("Jujuy");

        assertEquals(List.of(List.of("Jujuy")), paisChico.calcularRegiones(1));
    }

    private void cargarSimilaridades() {
        pais.agregarSimilaridad("Jujuy", "Salta", 10);
        pais.agregarSimilaridad("Salta", "Tucumán", 15);
        pais.agregarSimilaridad("Misiones", "Corrientes", 20);
        pais.agregarSimilaridad("Salta", "Corrientes", 90);
    }
}
