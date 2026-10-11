package negocio;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

// Lee y guarda un país en un archivo de texto con este formato
//
// PROVINCIAS
// nombre de una provincia por línea
// SIMILARIDADES
// provincia1,provincia2,peso
//
// Las líneas vacías y las que empiezan con # se ignoran por los comentarios del archivo que agregamos
public class ArchivoPais {
    private static final String SECCION_PROVINCIAS = "PROVINCIAS";
    private static final String SECCION_SIMILARIDADES = "SIMILARIDADES";

    public static Pais leer(Path ruta) throws IOException {
        Pais pais = new Pais();
        String seccion = null;
        int numeroDeLinea = 0;

        for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
            numeroDeLinea++;
            linea = linea.trim();
            if (linea.isEmpty() || linea.startsWith("#"))
                continue;

            if (linea.equals(SECCION_PROVINCIAS) || linea.equals(SECCION_SIMILARIDADES)) {
                seccion = linea;
                continue;
            }

            try {
                if (SECCION_PROVINCIAS.equals(seccion)) {
                    pais.agregarProvincia(linea);
                } else if (SECCION_SIMILARIDADES.equals(seccion)) {
                    agregarSimilaridad(pais, linea);
                } else {
                    throw new IllegalArgumentException("Falta el título PROVINCIAS o SIMILARIDADES");
                }
            } catch (IllegalArgumentException e) {
                // Agregamos el número de línea para que sea fácil encontrar el error en el archivo
                throw new IllegalArgumentException("Línea " + numeroDeLinea + ". " + e.getMessage());
            }
        }
        return pais;
    }

    public static void guardar(Pais pais, Path ruta) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add(SECCION_PROVINCIAS);
        lineas.addAll(pais.obtenerProvincias());
        lineas.add("");
        lineas.add(SECCION_SIMILARIDADES);
        for (Similaridad similaridad : pais.obtenerSimilaridades()) {
            lineas.add(similaridad.getProvincia1() + "," + similaridad.getProvincia2() + "," + similaridad.getPeso());
        }
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    private static void agregarSimilaridad(Pais pais, String linea) {
        String[] partes = linea.split(",");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Se esperaba provincia1,provincia2,peso");
        }
        int peso;
        try {
            peso = Integer.parseInt(partes[2].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El peso tiene que ser un número entero");
        }
        pais.agregarSimilaridad(partes[0].trim(), partes[1].trim(), peso);
    }
}
