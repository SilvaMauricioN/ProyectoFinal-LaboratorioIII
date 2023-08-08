package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsignaturaDaoMemoryImplTest {
    AsignaturaDaoMemoryImpl asignaturaDaoMemory;
    @BeforeEach
    void setUp(){
        asignaturaDaoMemory = new AsignaturaDaoMemoryImpl();
        Alumno alumno = new Alumno("Nicolas","Silva",231245);
        alumno.setId(1);
        Materia materia = new Materia("Ingles",1,1);
        materia.setMateriaId(1);
        Asignatura asignatura = new Asignatura(materia);
        Materia materia_2 = new Materia("Laboratorio II",1,1);
        materia_2.setMateriaId(2);
        Asignatura asignatura_2 = new Asignatura(materia_2);
        List<Asignatura> cursando = new ArrayList<>(){{
            add(asignatura);
            add(asignatura_2);
        }};
        alumno.setListaAsignaturas(cursando);
        asignaturaDaoMemory.saveAsignatura(alumno);
    }
    @Test
    void findAsignatura() throws AsignaturaInexistenteException {
        Asignatura asignaturaObtenida = asignaturaDaoMemory.findAsignatura(1,1);

        Materia m = new Materia("Ingles",1,1);
        m.setMateriaId(1);
        Asignatura Esperada = new Asignatura(m);
        assertEquals(Esperada, asignaturaObtenida);


        Exception exception = assertThrows(AsignaturaInexistenteException.class, () -> {
            asignaturaDaoMemory.findAsignatura(3,1);
        });

        String MensajeEsperado = "NO EXISTE ALUMNO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

    @Test
    void upDateAsignatura() throws AsignaturaInexistenteException {
        Asignatura asignaturaSinActualiza = asignaturaDaoMemory.findAsignatura(1,1);

        Materia m = new Materia("Ingles",1,1);
        m.setMateriaId(1);
        Asignatura Esperada = new Asignatura(m);

        assertEquals(Esperada, asignaturaSinActualiza);

        Esperada.setNota(7);
        Esperada.setEstado(EstadoAsignatura.APROBADA);

        asignaturaDaoMemory.upDateAsignatura(1,Esperada);
        Asignatura asignaturaActualizada = asignaturaDaoMemory.findAsignatura(1,1);
        assertEquals(Esperada, asignaturaActualizada);
    }

    @Test
    void deleteAsignaturas() {
        Exception exception = assertThrows(AsignaturaInexistenteException.class, () -> {
            asignaturaDaoMemory.deleteAsignaturas(3);
        });

        String MensajeEsperado = "ASIGNATURAS NO ENCONTRADAS";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
}