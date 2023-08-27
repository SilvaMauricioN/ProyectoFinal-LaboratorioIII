package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoDaoMemoryImplTest {

     private AlumnoDaoMemoryImpl alumnoDaoMemory;
    private final GeneradorId generadorId = GeneradorId.getInstance();

    @BeforeEach
    public void setUp(){
        alumnoDaoMemory = new AlumnoDaoMemoryImpl();
    }
    @AfterEach
    public void resetIdGenerator() {
        generadorId.reset();
    }
    @Test
    void saveAlumno() throws AlumnoNotFoundException {
        Alumno alumno = new Alumno("Nicolas","Silva",231245);
        alumnoDaoMemory.saveAlumno(alumno);

        Alumno encontrado = alumnoDaoMemory.findAlumnoId(1);
        assertEquals(alumno,encontrado);

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoDaoMemory.saveAlumno(alumno);
        });

        String MensajeEsperado = "EL ALUMNO YA EXISTE";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

    @Test
    void findAlumnoId() {
        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoDaoMemory.findAlumnoId(1);
        });

        String MensajeEsperado = "ALUMNO NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

    @Test
    void findAlumnoApellido() throws AlumnoNotFoundException {
        Alumno alumno = new Alumno("Nicolas","Silva",231245);
        Alumno alumno_2 = new Alumno("Mauricio","Silva",231245);

        alumnoDaoMemory.saveAlumno(alumno);
        alumnoDaoMemory.saveAlumno(alumno_2);

        List<Alumno> obtenidos = alumnoDaoMemory.findAlumnoApellido("Silva");
        List<Alumno> guardados = new ArrayList<>(){{
            add(alumno);
            add(alumno_2);
        }};
        assertEquals(guardados,obtenidos);

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoDaoMemory.findAlumnoApellido("Coppo");
        });

        String MensajeEsperado = "ALUMNO NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));

        alumnoDaoMemory.deleteAlumno(1);
        List<Alumno> obtenidosModificada = alumnoDaoMemory.findAlumnoApellido("Silva");

        assertEquals(1, obtenidosModificada.size());
    }
    @Test
    void deleteAlumno() throws AlumnoNotFoundException {
        Alumno alumno = new Alumno("Nicolas","Silva",231245);
        Alumno alumno_2 = new Alumno("Mauricio","Silva",231245);
        alumnoDaoMemory.saveAlumno(alumno);
        alumnoDaoMemory.saveAlumno(alumno_2);
        List<Alumno> obtenidas = alumnoDaoMemory.findAlumnoApellido("Silva");
        List<Alumno> guardadas = new ArrayList<>(){{
            add(alumno);
            add(alumno_2);
        }};
        assertEquals(2,obtenidas.size());
        assertEquals(guardadas,obtenidas);
        alumnoDaoMemory.deleteAlumno(2);
        List<Alumno> obtenidoActualizado = alumnoDaoMemory.findAlumnoApellido("Silva");
        assertEquals(alumno,obtenidoActualizado.get(0));
        assertEquals(1,obtenidoActualizado.size());
    }
    @Test
    void deleteAlumnoException(){
        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoDaoMemory.deleteAlumno(10);
        });
        String MensajeEsperado = "ALUMNO ID: 10 NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
}