package negocio;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Arista implements Comparable<Arista>{
    private int _origen;
    private int _destino;
    private int _peso;

    public Arista(int origen, int destino, int peso) {
        _origen = origen;
        _destino = destino;
        _peso = peso;
    }

    public Arista(int origen, int destino) {
        _origen = origen;
        _destino = destino;
        _peso = 0;
    }

    public Integer getPeso() {
        return _peso;
    }
    public Integer obtenerOrigen() {
        return _origen;
    }
    public Integer obtenerDestino() {
        return _destino;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Arista arista = (Arista) o;

        return
            (_origen == arista._origen && _destino == arista._destino) ||
            (_destino == arista._origen && _origen == arista._destino);
    }

    @Override
    public int hashCode() {
        int min = Math.min(_origen, _destino);
        int max = Math.max(_origen, _destino);
        return Objects.hash(min, max);
    }

    @Override
    public int compareTo(Arista a) {
        return _peso - a.getPeso();
    }
}
