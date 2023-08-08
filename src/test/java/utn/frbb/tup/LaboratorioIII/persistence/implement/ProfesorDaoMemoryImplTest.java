package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorDaoMemoryImplTest {
    ProfesorDaoMemoryImpl profesorDaoMemory;
    Profesor profesor_1, profesor_2, profesor_3, profesor_4;
    private final GeneradorId generadorId = GeneradorId.getInstance();

    @BeforeEach
    void setUp() throws ProfesorException {
        profesorDaoMemory = new ProfesorDaoMemoryImpl();
        profesor_1 = new Profesor("Ricardo", "Coppo", "Lic.", 35432567);
        profesor_1.setProfesorId(1);
        profesor_2 = new Profesor("Juan", "Troilo", "Lic.", 35432667);
        profesor_2.setProfesorId(2);
        profesor_3 = new Profesor("Maria jose", "Padilla", "Lic.", 35432767);
        profesor_3.setProfesorId(3);
        profesor_4 = new Profesor("Luciano","Balmaceda","Lic.",35432867);
        profesor_4.setProfesorId(4);
        generadorId.reset();
    }

    @Test
    void saveProfesorException() {
        Exception exception = assertThrows(ProfesorException.class, () -> {
            profesorDaoMemory.saveProfesor(profesor_1);
        });

        String MensajeEsperado = "PROFESOR YA GUARDADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    void saveProfesorExceptionDos() {
        Profesor p = new Profesor();
        Exception exception = assertThrows(ProfesorException.class, () -> {
            profesorDaoMemory.saveProfesor(p);
        });

        String MensajeEsperado = "PROFESOR NO PUEDE ESTAR SIN DATOS";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

    @Test
    void findProfesor() throws ProfesorException {
        Profesor profesor = profesorDaoMemory.findProfesor(1);
        assertEquals(profesor_1,profesor);

        Exception exception = assertThrows(ProfesorException.class, () -> {
            profesorDaoMemory.findProfesor(5);
        });

        String MensajeEsperado = "PROFESOR NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

    @Test
    void getAllProfesores() {
        List<Profesor> guardados = new ArrayList<>(){{
            add(profesor_1);
            add(profesor_2);
            add(profesor_3);
            add(profesor_4);
        }};
        List<Profesor> Obtenidos = profesorDaoMemory.getAllProfesores();
        assertEquals(4, Obtenidos.size());
        assertEquals(guardados,Obtenidos);
    }
    @Test
    void deleteProfesor() throws ProfesorException {
        profesorDaoMemory.deleteProfesor(1);
        List<Profesor> guardados = profesorDaoMemory.getAllProfesores();
        assertEquals(3,guardados.size());



        Exception exception = assertThrows(ProfesorException.class, () -> {
            profesorDaoMemory.deleteProfesor(5);
        });

        String MensajeEsperado = "PROFESOR NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));

    }
}