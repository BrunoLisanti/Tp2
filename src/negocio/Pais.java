package negocio;

import java.util.*;

// Guarda las provincias por nombre y las similaridades entre vecinas.
// El grafo con índices se arma recién al calcular las regiones, así la interfaz no tiene que saber
// qué número le tocó a cada provincia
public class Pais {
    private List<String> _provincias;
    private List<Similaridad> _similaridades;

    public Pais() {
        _provincias = new ArrayList<>();
        _similaridades = new ArrayList<>();
    }

    public void agregarProvincia(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío");
        }
        String nombreLimpio = nombre.trim();
        if (_provincias.contains(nombreLimpio)) {
            throw new IllegalArgumentException("La provincia " + nombreLimpio + " ya fue cargada");
        }
        _provincias.add(nombreLimpio);
    }

    // Si ya había una similaridad entre esas dos provincias se reemplaza por la
    // nueva
    public void agregarSimilaridad(String provincia1, String provincia2, int peso) {
        verificarProvincia(provincia1);
        verificarProvincia(provincia2);
        if (provincia1.equals(provincia2)) {
            throw new IllegalArgumentException("Una provincia no puede ser vecina de si misma");
        }
        if (peso < 0) {
            throw new IllegalArgumentException("La similaridad no puede ser negativa");
        }
        eliminarSimilaridad(provincia1, provincia2);
        _similaridades.add(new Similaridad(provincia1, provincia2, peso));
    }

    public void eliminarSimilaridad(String provincia1, String provincia2) {
        for (int i = _similaridades.size() - 1; i >= 0; i--) {
            if (_similaridades.get(i).une(provincia1, provincia2)) {
                _similaridades.remove(i);
            }
        }
    }

    public List<String> obtenerProvincias() {
        return Collections.unmodifiableList(_provincias);
    }

    public List<Similaridad> obtenerSimilaridades() {
        return Collections.unmodifiableList(_similaridades);
    }

    public int cantidadDeProvincias() {
        return _provincias.size();
    }

    public boolean esConexo() {
        return BFS.esConexo(armarGrafo());
    }

    // Cada región es la lista de nombres de las provincias que la forman
    public List<List<String>> calcularRegiones(int k) {
        if (_provincias.isEmpty()) {
            throw new IllegalStateException("No hay provincias cargadas");
        }
        Grafo grafo = armarGrafo();
        if (!BFS.esConexo(grafo)) {
            throw new IllegalStateException("El mapa no es conexo, faltan similaridades entre provincias");
        }

        List<List<String>> regiones = new ArrayList<>();
        for (Set<Integer> componente : Regiones.dividir(grafo, k)) {
            List<String> region = new ArrayList<>();
            for (Integer vertice : componente) {
                region.add(_provincias.get(vertice));
            }
            Collections.sort(region);
            regiones.add(region);
        }
        return regiones;
    }

    private Grafo armarGrafo() {
        Grafo grafo = new Grafo(_provincias.size());
        for (Similaridad similaridad : _similaridades) {
            grafo.agregarArista(_provincias.indexOf(similaridad.getProvincia1()),
                    _provincias.indexOf(similaridad.getProvincia2()),
                    similaridad.getPeso());
        }
        return grafo;
    }

    private void verificarProvincia(String nombre) {
        if (!_provincias.contains(nombre)) {
            throw new IllegalArgumentException("La provincia " + nombre + " no existe");
        }
    }
}
