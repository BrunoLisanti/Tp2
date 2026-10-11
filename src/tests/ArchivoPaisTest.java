package tests;

import negocio.ArchivoPais;
import negocio.Pais;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class ArchivoPaisTest {
    @Rule
    public TemporaryFolder carpeta = new TemporaryFolder();

    @Test
    public void leerArchivoTest() throws IOException {
        Path archivo = escribir("# comentario",
                                "PROVINCIAS",
                                "A",
                                "B",
                                "",
                                "C",
                                "SIMILARIDADES",
                                "A,B,10",
                                " B , C , 20 ");

        Pais pais = ArchivoPais.leer(archivo);

        assertEquals(List.of("A", "B", "C"), pais.obtenerProvincias());
        assertEquals(2, pais.obtenerSimilaridades().size());
        assertEquals(20, pais.obtenerSimilaridades().get(1).getPeso());
    }

    @Test
    public void guardarYVolverALeerTest() throws IOException {
        Pais pais = new Pais();
        pais.agregarProvincia("A");
        pais.agregarProvincia("B");
        pais.agregarSimilaridad("A", "B", 15);
        Path archivo = carpeta.newFile().toPath();

        ArchivoPais.guardar(pais, archivo);
        Pais leido = ArchivoPais.leer(archivo);

        assertEquals(pais.obtenerProvincias(), leido.obtenerProvincias());
        assertEquals(1, leido.obtenerSimilaridades().size());
        assertTrue(leido.obtenerSimilaridades().get(0).une("A", "B"));
        assertEquals(15, leido.obtenerSimilaridades().get(0).getPeso());
    }

    @Test
    public void elErrorDiceLaLineaTest() throws IOException {
        Path archivo = escribir("PROVINCIAS", "A", "B", "SIMILARIDADES", "A,B,diez");
        try {
            ArchivoPais.leer(archivo);
            fail("Tendría que haber tirado una excepción");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().startsWith("Línea 5"));
        }
    }

    private Path escribir(String... lineas) throws IOException {
        Path archivo = carpeta.newFile().toPath();
        Files.write(archivo, List.of(lineas), StandardCharsets.UTF_8);
        return archivo;
    }

    // Caso de borde, un archivo vacío es un país sin provincias
    @Test
    public void archivoVacioTest() throws IOException {
        Pais pais = ArchivoPais.leer(escribir());

        assertEquals(0, pais.cantidadDeProvincias());
    }
}
