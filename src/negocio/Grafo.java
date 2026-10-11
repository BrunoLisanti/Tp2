package negocio;

import java.util.*;

public class Grafo {
    private Integer[][] _matrizAdyacencia;
    public Grafo(int cantidadVertices) {
        _matrizAdyacencia = new Integer[cantidadVertices][cantidadVertices];
    }

    public void agregarArista(int vertice1, int vertice2, int peso) {
        _matrizAdyacencia[vertice1][vertice2] = peso;
        _matrizAdyacencia[vertice2][vertice1] = peso;
    }

    public void agregarArista(Arista arista) {
        _matrizAdyacencia[arista.getOrigen()][arista.getDestino()] = arista.getPeso();
        _matrizAdyacencia[arista.getDestino()][arista.getOrigen()] = arista.getPeso();
    }

    public void agregarAristaSinPeso(int vertice1, int vertice2) {
        _matrizAdyacencia[vertice1][vertice2] = 0;
        _matrizAdyacencia[vertice2][vertice1] = 0;
    }

    public void eliminarArista(int vertice1, int vertice2) {
        _matrizAdyacencia[vertice1][vertice2] = null;
        _matrizAdyacencia[vertice2][vertice1] = null;
    }

    public void eliminarArista(Arista arista) {
        _matrizAdyacencia[arista.getOrigen()][arista.getDestino()] = null;
        _matrizAdyacencia[arista.getDestino()][arista.getOrigen()] = null;
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

    public boolean comparar(Grafo grafo) {
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

    public PriorityQueue<Arista> obtenerAristasOrdenadas() {
        PriorityQueue<Arista> aristas = new PriorityQueue<>(Comparator.comparingInt(Arista::getPeso));
        int vertices = _matrizAdyacencia.length;
        for (int i = 0; i < vertices;){
            for (int j = i; j < vertices; j++){
                Integer peso = _matrizAdyacencia[i][j];
                if (peso != null) {
                    aristas.add(new Arista(i, j, peso));
                }
            }
            i++;
        }
        return aristas;
    }

    public Arista obtenerAristaMasPesada() {
        Arista masPesada = null;
        for (int i = 0; i < _matrizAdyacencia.length; i++) {
            for (int j = i; j < _matrizAdyacencia.length; j++) {
                Integer pesoActual = _matrizAdyacencia[i][j];
                if (pesoActual == null) { continue; }
                if (masPesada == null || pesoActual > masPesada.getPeso()) {
                    masPesada = new Arista(i,j,pesoActual);
                }

            }
        }
        return masPesada;
    }
}
