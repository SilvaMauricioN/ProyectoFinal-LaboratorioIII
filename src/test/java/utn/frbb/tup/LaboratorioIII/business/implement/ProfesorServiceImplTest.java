package utn.frbb.tup.LaboratorioIII.business.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceImplTest {
    @Mock
    private ProfesorDao profesorDao;
    @Mock
    private MateriaDao materiaDao;
    private CastingDtos castingDtos;
    @InjectMocks
    private ProfesorServiceImpl profesorServiceImpl;
    private static ProfesorDtoSalida profesorDtoSalida_1, profesorDtoSalida_2;
    private static ProfesorDto profesorDto_1;
    private static Profesor profesor,profesor_2;
    private static Materia materia_1, materia_2;
    private static MateriaDtoSalida materiaEsperada_1, materiaEsperada_2;

    @BeforeEach
    public void setUp(){
        castingDtos = new CastingDtos(materiaDao);

        profesor = new Profesor("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
        profesor_2 = new Profesor("Ricardo", "Coppo", "Ing", 43145665);
        profesorDtoSalida_1 = new ProfesorDtoSalida("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
        profesorDtoSalida_2 = new ProfesorDtoSalida("Ricardo", "Coppo", "Ing", 43145665);
        profesorDto_1 = new ProfesorDto("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
        materia_1 = new Materia("Laboratorio I", 1, 1);
        materia_2 = new Materia("Laboratorio II", 1, 2);
        materiaEsperada_1 = new MateriaDtoSalida("Laboratorio I",1,1);
        materiaEsperada_2 = new MateriaDtoSalida("Laboratorio II",1,2);
    }
    @Test
    void crearProfesor() throws ProfesorException {
        ProfesorDto p1 = new ProfesorDto("Ricardo", "Coppo", "Ing", 43145665);
        ProfesorDtoSalida esperado = new ProfesorDtoSalida("Ricardo", "Coppo", "Ing", 43145665);

        ProfesorDtoSalida profesorObtenido = profesorServiceImpl.crearProfesor(p1);

        assertEquals(esperado, profesorObtenido);
    }
    @Test
    void actualizarProfesor() throws ProfesorException {
        when(profesorDao.findProfesor(eq(1))).thenReturn(profesor);
        ProfesorDtoSalida profesorObtenido = profesorServiceImpl.actualizarProfesor(1,profesorDto_1);
        assertEquals(profesorDtoSalida_1,profesorObtenido);
    }
    @Test
    void actualizarProfesorConMaterias() throws MateriaNotFoundException, ProfesorException {
        List<Integer> idMaterias = new ArrayList<>(){{
            add(1);
            add(2);
        }};
        profesorDto_1.setMateriasDictadasID(idMaterias);

        List<MateriaDtoSalida> esperada = new ArrayList<>(){{
            add(materiaEsperada_1);
            add(materiaEsperada_2);
        }};
        Profesor profesorEncontrado = new Profesor("Marcos","Salotto", "Lic",123432);
        when(profesorDao.findProfesor(1)).thenReturn(profesorEncontrado);
        when(materiaDao.findMateria(eq(1))).thenReturn(materia_1);
        when(materiaDao.findMateria(eq(2))).thenReturn(materia_2);

        ProfesorDtoSalida profesorObtenido = profesorServiceImpl.actualizarProfesor(1,profesorDto_1);

        profesorDtoSalida_1.setMaterias(esperada);

        verify(profesorDao,times(1)).findProfesor(1);
        verify(profesorDao,times(1)).upDateProfesor(any(Profesor.class));
        assertEquals(profesorDtoSalida_1,profesorObtenido);
    }
    @Test
    public void getAllProfesor() throws ProfesorException {
        profesor.setMateria(materia_1);
        List<Profesor> guardados = new ArrayList<>(){{
            add(profesor);
            add(profesor_2);
        }};

        when(profesorDao.getAllProfesores()).thenReturn(guardados);

        List<MateriaDtoSalida> guardadas = new ArrayList<>(){{
            add(materiaEsperada_1);
        }};
        profesorDtoSalida_1.setMaterias(guardadas);
        List<ProfesorDtoSalida> listaEsperada = new ArrayList<>(){{
            add(profesorDtoSalida_1);
            add(profesorDtoSalida_2);
        }};
        List<ProfesorDtoSalida> listaObtenida = profesorServiceImpl.getAllProfesor();

        verify(profesorDao,times(1)).getAllProfesores();
        assertEquals(listaEsperada,listaObtenida);
    }
    @Test
    public void findProfesor() throws ProfesorException {
        when(profesorDao.findProfesor(eq(5))).thenThrow(new ProfesorException("PROFESOR NO ENCONTRADO"));

        Exception exception = assertThrows(ProfesorException.class, () -> {
            profesorServiceImpl.findProfesor(5);
        });

        String MensajeEsperado = "PROFESOR NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();

        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    public void findProfesorEncontrado() throws ProfesorException {
        when(profesorDao.findProfesor(eq(5))).thenReturn(profesor);
        ProfesorDtoSalida profesorObtenido = profesorServiceImpl.findProfesor(5);

        verify(profesorDao,times(1)).findProfesor(5);
        assertEquals(profesorDtoSalida_1,profesorObtenido);
    }
    @Test
    public void getMateriasDictadas() throws ProfesorException {
        List<Materia> dictadas = new ArrayList<>(){{
           add(materia_1);
           add(materia_2);
        }};
        profesor_2.setListaMateriasDictadas(dictadas);

        when(profesorDao.findProfesor(eq(2))).thenReturn(profesor_2);

        List<MateriaDtoSalida> Esperadas = new ArrayList<>(){{
            add(materiaEsperada_1);
            add(materiaEsperada_2);
        }};
        List<MateriaDtoSalida> Obtenidas = profesorServiceImpl.getMateriasDictadas(2);

        assertEquals(Esperadas, Obtenidas);
    }
}