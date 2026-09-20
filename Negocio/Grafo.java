package Negocio;

import java.util.*;

public class Grafo {
    private ArrayList<HashSet<Integer>> vecinos;

    public Grafo(int cantidadVertices) {
        vecinos = new ArrayList<HashSet<Integer>>();
        for (int i = 0; i < cantidadVertices; i++)
            vecinos.add(new HashSet<Integer>());
    }

    public void agregarArista(int vertice1, int vertice2) {
        vecinos.get(vertice1).add(vertice2);
        vecinos.get(vertice2).add(vertice1);
    }

    public void eliminarArista(int vertice1, int vertice2) {
        vecinos.get(vertice1).remove(vertice2);
        vecinos.get(vertice2).remove(vertice1);
    }

    public boolean existeArista(int vertice1, int vertice2) {
        return vecinos.get(vertice1).contains(vertice2);
    }

    public Set<Integer> obtenerVecinos(int vertice) {
        return vecinos.get(vertice);
    }

    public int obtenerTamano() {
        return vecinos.size();
    }

    public boolean esConexo() {
        Queue<Integer> L = new ArrayDeque<Integer>();
        boolean[] marked = new boolean[vecinos.size()];
        L.add(0);
        while (!L.isEmpty()) {
            Integer i = L.remove();
            marked[i] = true;
            for (Integer vecino : vecinos.get(i))
            {
                if (!marked[vecino])
                    L.add(vecino);
            }
        }
        return marked[vecinos.size() - 1];
    }

    public 

}
