package utn.frbb.tup.LaboratorioIII.business.implement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.*;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.AsignaturaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AsignaturaDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceImplTest {
    @InjectMocks
    private AlumnoServiceImpl alumnoServiceImpl;
    @Mock
    AlumnoDao alumnoDao;
    @Mock
    AsignaturaService asignaturaService;
    @Mock
    AsignaturaDao asignaturaDao;
    @Mock
    private CastingDtos castingDtos;
    @Test
    void crearAlumno() throws AsignaturaInexistenteException, AlumnoNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto("Nicolas", "Silva", 1234567L);
        AlumnoDtoSalida alumnoDtoSalida = new AlumnoDtoSalida("Nicolas", "Silva", 1234567L);

        when(castingDtos.aAlumnoDto(any(), any())).thenReturn(new ArrayList<>());
        when(castingDtos.aAlumnoDtoSalida(any())).thenReturn(alumnoDtoSalida);

        AlumnoDtoSalida Obtenido = alumnoServiceImpl.crearAlumno(alumnoDto);

        verify(castingDtos,times(1)).aAlumnoDto(any(),any());
        verify(castingDtos,times(1)).aAlumnoDtoSalida(any());
        assertNotNull(Obtenido);
    }

    @Test
    void buscarAlumnoPorApellido() throws AlumnoNotFoundException {
        Alumno alumno_1 = new Alumno("Silva", "Mauricio",1232133);
        Alumno alumno_2 = new Alumno("Silva", "Nicolas",32456123);
        List<Alumno> guardados = new ArrayList<>(){{
            add(alumno_1);
            add(alumno_2);
        }};
        AlumnoDtoSalida alumnoSalida_1 = new AlumnoDtoSalida("Silva", "Mauricio",1232133L);
        AlumnoDtoSalida alumnoSalida_2 = new AlumnoDtoSalida("Silva", "Nicolas",32456123L);

        List<AlumnoDtoSalida> guardadosSalida = new ArrayList<>(){{
            add(alumnoSalida_1);
            add(alumnoSalida_2);
        }};

        when(alumnoDao.findAlumnoApellido(eq("Silva"))).thenReturn(guardados);
        when(castingDtos.aAlumnoDtoSalida(eq(alumno_1))).thenReturn(alumnoSalida_1);
        when(castingDtos.aAlumnoDtoSalida(eq(alumno_2))).thenReturn(alumnoSalida_2);

        List<AlumnoDtoSalida> Obtenida = alumnoServiceImpl.buscarAlumnoPorApellido("Silva");

        verify(castingDtos,times(1)).aAlumnoDtoSalida(eq(alumno_1));
        verify(castingDtos,times(1)).aAlumnoDtoSalida(eq(alumno_2));
        verify(alumnoDao,times(1)).findAlumnoApellido("Silva");
        assertEquals(guardadosSalida,Obtenida);
    }

    @Test
    void buscarAlumnoPorId() throws AlumnoNotFoundException {
        Alumno alumno_1 = new Alumno("Silva", "Mauricio",1232133);
        AlumnoDtoSalida alumnoSalida_1 = new AlumnoDtoSalida("Silva", "Mauricio",1232133L);

        when(alumnoDao.findAlumnoId(eq(1))).thenReturn(alumno_1);
        when(castingDtos.aAlumnoDtoSalida(alumno_1)).thenReturn(alumnoSalida_1);

        AlumnoDtoSalida Obtenido = alumnoServiceImpl.buscarAlumnoPorId(1);

        assertEquals(alumnoSalida_1,Obtenido);
        verify(castingDtos,times(1)).aAlumnoDtoSalida(eq(alumno_1));
        verify(alumnoDao,times(1)).findAlumnoId(1);
    }

    @Test
    void buscarAlumnoPorIdNoEncontrado() throws AlumnoNotFoundException {
        when(alumnoDao.findAlumnoId(eq(1))).thenThrow(new AlumnoNotFoundException("ALUMNO NO ENCONTRADO"));

        Exception exception = assertThrows(AlumnoNotFoundException.class, () -> {
            alumnoServiceImpl.buscarAlumnoPorId(1);
        });

        String MensajeEsperado = "ALUMNO NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();

        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    void modificaEstadoAsignaturaNoCursada() {
        Nota nota = new Nota(7, EstadoAsignatura.NO_CURSADA);

        Exception exception = assertThrows(EstadoIncorrectoException.class, () -> {
            alumnoServiceImpl.modificaEstadoAsignatura(1,1,nota);
        });

        String MensajeEsperado = "LA MATERIA DEBE ESTAR CURSADA";
        String MensajeObtenido = exception.getMessage();

        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    void modificaEstadoAsignatura() throws AsignaturaInexistenteException, CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AlumnoNotFoundException {
        Alumno alumno = new Alumno("Nicolas","Silva",1232145);
        Materia materia = new Materia("Ingles",1,2);
        Asignatura asignatura = new Asignatura(materia);
        List<Asignatura> cursadas = new ArrayList<>(){{
            add(asignatura);
        }};
        alumno.setListaAsignaturas(cursadas);

        asignatura.setNota(7);
        asignatura.setEstado(EstadoAsignatura.APROBADA);

        Nota nota = new Nota(7, EstadoAsignatura.CURSADA);

        AsignaturaDtoSalida asignaturaDtoSalida = new AsignaturaDtoSalida();
        asignaturaDtoSalida.setNombre("Ingles");
        asignaturaDtoSalida.setAnio(1);
        asignaturaDtoSalida.setCuatrimestre(2);
        asignaturaDtoSalida.setNota(Optional.of(7));
        asignaturaDtoSalida.setEstado(EstadoAsignatura.APROBADA);

        when(asignaturaService.aprobarAsignatura(1,1,nota)).thenReturn(asignatura);
        when(castingDtos.aAsignaturaDtoSalida(asignatura)).thenReturn(asignaturaDtoSalida);
        when(alumnoDao.findAlumnoId(1)).thenReturn(alumno);
        AsignaturaDtoSalida Obtenida = alumnoServiceImpl.modificaEstadoAsignatura(1,1,nota);

        verify(asignaturaService,times(1)).aprobarAsignatura(1,1,nota);
        verify(castingDtos,times(1)).aAsignaturaDtoSalida(asignatura);
        verify(alumnoDao,times(1)).findAlumnoId(1);

        assertEquals(asignaturaDtoSalida,Obtenida);
    }


}