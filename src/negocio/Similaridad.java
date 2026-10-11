package negocio;

// Similaridad entre dos provincias vecinas
public class Similaridad {
    private final String _provincia1;
    private final String _provincia2;
    private final int _peso;

    public Similaridad(String provincia1, String provincia2, int peso) {
        _provincia1 = provincia1;
        _provincia2 = provincia2;
        _peso = peso;
    }

    public String getProvincia1() {
        return _provincia1;
    }

    public String getProvincia2() {
        return _provincia2;
    }

    public int getPeso() {
        return _peso;
    }

    // No importa el orden, A - B es lo mismo que B - A
    public boolean une(String provinciaA, String provinciaB) {
        return (_provincia1.equals(provinciaA) && _provincia2.equals(provinciaB)) ||
                (_provincia1.equals(provinciaB) && _provincia2.equals(provinciaA));
    }
}
