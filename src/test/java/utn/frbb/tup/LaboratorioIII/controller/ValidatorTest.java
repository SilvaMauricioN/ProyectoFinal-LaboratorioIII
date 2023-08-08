package utn.frbb.tup.LaboratorioIII.controller;

import org.junit.jupiter.api.Test;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Test
    void validarCampos() {
        AlumnoDto alumnoDto = new AlumnoDto("","",1232121L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.ValidarCampos(alumnoDto);

        });

        String MensajeEsperado = "Falta el campo: nombre";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    void validarCamposSegundoArgumento() {
        AlumnoDto alumnoDto = new AlumnoDto("Nicolas","",1232121L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Validator.ValidarCampos(alumnoDto);

        });

        String MensajeEsperado = "Falta el campo: apellido";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

}