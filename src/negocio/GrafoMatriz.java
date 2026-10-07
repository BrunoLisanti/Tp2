package negocio;

import javax.swing.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GrafoMatriz {
    private Integer[][] _matrizAdyacencia;
    public GrafoMatriz(int cantidadVertices) {
        _matrizAdyacencia = new Integer[cantidadVertices][cantidadVertices];
    }

    public void agregarArista(int vertice1, int vertice2, int peso) {
        _matrizAdyacencia[vertice1][vertice2] = peso;
    }

    public void agregarAristaSinPeso(int vertice1, int vertice2) {
        _matrizAdyacencia[vertice1][vertice2] = 0;
    }

    public void eliminarArista(int vertice1, int vertice2) {
        _matrizAdyacencia[vertice1][vertice2] = null;
    }

    public boolean existeArista(int vertice1, int vertice2) {
        return _matrizAdyacencia[vertice1][vertice2] != null;
    }

    public Set<Integer> obtenerVecinos(int vertice) {
        Set<Integer> vecinos = new HashSet<>();
        for (int i = 0; i < _matrizAdyacencia.length; i++) {
            Integer value = _matrizAdyacencia[vertice][i];
            if(value != null) {
                vecinos.add(i);
            }
        }
        return vecinos;
    }

    public int obtenerPesoTotal() {
        int peso = 0;
        int vertices = _matrizAdyacencia.length;
        for (int i = 0; i < vertices; i++) {
            for (int j = i; j < vertices; j++) {
                if (_matrizAdyacencia[i][j] != null) {
                    peso += _matrizAdyacencia[i][j];
                }
            }
        }
        return peso;
    }

    public Integer obtenerPesoArista(int vertice1, int vertice2) {
        return _matrizAdyacencia[vertice1][vertice2];
    }

    public int obtenerTamano() {
        return _matrizAdyacencia.length;
    }

    public boolean comparar(GrafoMatriz grafo) {
        if (obtenerTamano() != grafo.obtenerTamano())
                return false;

        int vertices = _matrizAdyacencia.length;

        for (int i = 0; i < vertices; i++) {
            for (int j = i; j < vertices; j++) {
                if (!Objects.equals(_matrizAdyacencia[i][j], grafo.obtenerPesoArista(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
