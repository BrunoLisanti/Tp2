package negocio;

import java.util.*;

public class Grafo {
    private ArrayList<HashSet<Integer>> vecinos;
    private TreeSet<Arista> aristas;

    public Grafo(int cantidadVertices) {
        vecinos = new ArrayList<HashSet<Integer>>();
        aristas = new TreeSet<>();
        for (int i = 0; i < cantidadVertices; i++)
            vecinos.add(new HashSet<Integer>());
    }

    public void agregarArista(int vertice1, int vertice2, int peso) {
        vecinos.get(vertice1).add(vertice2);
        vecinos.get(vertice2).add(vertice1);

        aristas.add(new Arista(vertice1, vertice2, peso));
    }

    public void agregarAristaSinPeso(int vertice1, int vertice2) {
        vecinos.get(vertice1).add(vertice2);
        vecinos.get(vertice2).add(vertice1);

        aristas.add(new Arista(vertice1, vertice2, 0));
    }

    public void eliminarArista(int vertice1, int vertice2) {
        vecinos.get(vertice1).remove(vertice2);
        vecinos.get(vertice2).remove(vertice1);

        aristas.removeIf( arista -> {
            return (arista.obtenerOrigen() == vertice1 && arista.obtenerDestino() == vertice2) ||
            (arista.obtenerOrigen() == vertice2 && arista.obtenerDestino() == vertice1);
        });
    }

    public boolean existeArista(int vertice1, int vertice2) {
        return vecinos.get(vertice1).contains(vertice2);
    }

    public Set<Integer> obtenerVecinos(int vertice) {
        return vecinos.get(vertice);
    }

    public int obtenerPeso() {
        int peso = 0;
        for (Arista arista : aristas) {
            peso += arista.getPeso();
        }
        return peso;
    }

    public int obtenerTamano() {
        return vecinos.size();
    }

}
