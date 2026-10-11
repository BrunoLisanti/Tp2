package tests;

import negocio.Similaridad;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimilaridadTest {

    @Test
    public void uneEnCualquierOrdenTest() {
        Similaridad similaridad = new Similaridad("Jujuy", "Salta", 10);

        assertTrue(similaridad.une("Jujuy", "Salta"));
        assertTrue(similaridad.une("Salta", "Jujuy"));
    }

}
