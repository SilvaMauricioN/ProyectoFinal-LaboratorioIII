package utn.frbb.tup.LaboratorioIII.business.implement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CastingDtosTest {
    @Mock
    private MateriaDao materiaDao;
    private Materia materia_1, materia_2, materia_3;
    private MateriaDtoSalida materiaEsperada_1, materiaEsperada_2, materiaEsperada_3;
    private MateriaDto materiaDto1;
    private Profesor profesor_1;
    private ProfesorDtoSalida profesorEsperado;
    private ProfesorDto profesorDto;
    @InjectMocks
    CastingDtos castingDtos;
    @BeforeEach
    public void setUp() {
        materia_1 = new Materia("Laboratorio I", 1, 1);
        materia_2 = new Materia("Laboratorio II", 1, 2);
        materia_3 = new Materia("Laboratorio III", 2, 1);
        materiaEsperada_1 = new MateriaDtoSalida("Laboratorio I",1,1);
        materiaEsperada_2 = new MateriaDtoSalida("Laboratorio II",1,2);
        materiaEsperada_3 = new MateriaDtoSalida("Laboratorio III",2,1);
        materiaDto1 = new MateriaDto("Bases de Datos",2,1);
        profesor_1 = new Profesor("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
        profesorEsperado = new ProfesorDtoSalida("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
        profesorDto = new ProfesorDto("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
    }

    @Test
    void aMateriaDtoSalida() {
        List<Materia> materiasCorelativas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};
        materia_3.setListaCorrelatividades(materiasCorelativas);
        List<MateriaDtoSalida> listaEsperada = new ArrayList<>(){{
            add(materiaEsperada_1);
            add(materiaEsperada_2);
        }};
        materiaEsperada_3.setCorrelativas(listaEsperada);
        MateriaDtoSalida materiaDtoSalidaObtenida = castingDtos.aMateriaDtoSalida(materia_3);
        assertEquals(materiaEsperada_3,materiaDtoSalidaObtenida, "Las Materias a Mostar no son Iguales");
    }
    @Test
    void aMateriaDtoSalidaMateriaVacia() {
        Materia materia = new Materia();
        MateriaDtoSalida materiaEsperada = new MateriaDtoSalida();

        MateriaDtoSalida materiaDtoSalidaObtenida = castingDtos.aMateriaDtoSalida(materia);
        assertEquals(materiaEsperada,materiaDtoSalidaObtenida, "Las Materias a Mostar no son Iguales");
    }
    @Test
    void aProfesorDtoSalida() {
        Profesor profesor = new Profesor("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);
        ProfesorDtoSalida profesorDtoSalidaEsperado = new ProfesorDtoSalida("Luciano", "Salotto", "Lic. Ciencias Computación",1234321);

        ProfesorDtoSalida profesorDtoSalidaObtenido = castingDtos.aProfesorDtoSalida(profesor);
        assertEquals(profesorDtoSalidaEsperado,profesorDtoSalidaObtenido, "No son Iguales");
    }
    @Test
    void aProfesorDtoSalidaConMaterias(){
        profesor_1.setMateria(materia_1);
        List<MateriaDtoSalida> dictadas = new ArrayList<>(){{
           add(materiaEsperada_1);
        }};
        profesorEsperado.setMaterias(dictadas);

        ProfesorDtoSalida profesorDtoSalidaObtenido = castingDtos.aProfesorDtoSalida(profesor_1);
        assertEquals(profesorEsperado,profesorDtoSalidaObtenido, "No son Iguales");
    }
    @Test
    void aMateriaDto() throws CorrelatividadException {
        Materia materiaEsperada = new Materia("Bases de Datos",2,1);

        Materia materia = new Materia();
        List <Map<String,String>> posiblesErrores = castingDtos.aMateriaDto(materia,materiaDto1);
        assertTrue(posiblesErrores.isEmpty());
        assertEquals(materiaEsperada,materia);
    }
    @Test
    void aMateriaDtosCorrelativas() throws CorrelatividadException, MateriaNotFoundException {
        List<Materia> correlativas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};
        Materia materiaEsperada = new Materia("Bases de Datos",2,1);
        materiaEsperada.setListaCorrelatividades(correlativas);

        List<Integer> idCorrelativas = new ArrayList<>(){{
            add(1);
            add(2);
        }};
        materiaDto1.setListaCorrelatividades(idCorrelativas);
        Materia materia = new Materia();
        when(materiaDao.findMateria(eq(1))).thenReturn(materia_1);
        when(materiaDao.findMateria(eq(2))).thenReturn(materia_2);

        List <Map<String,String>> posiblesErrores = castingDtos.aMateriaDto(materia,materiaDto1);

        assertTrue(posiblesErrores.isEmpty());
        assertEquals(materiaEsperada,materia);
    }
    @Test
    void aMateriaDtoPosibleError() throws MateriaNotFoundException, CorrelatividadException {
        List<Materia> correlativas = new ArrayList<>(){{
            add(materia_1);
        }};
        Materia materiaEsperada = new Materia("Bases de Datos",2,1);
        materiaEsperada.setListaCorrelatividades(correlativas);
        List<Integer> idCorrelativas = new ArrayList<>(){{
            add(1);
            add(2);
        }};
        materiaDto1.setListaCorrelatividades(idCorrelativas);
        Materia materia = new Materia();
        when(materiaDao.findMateria(eq(1))).thenReturn(materia_1);
        when(materiaDao.findMateria(eq(2))).thenThrow(new MateriaNotFoundException("MATERIA NO ENCOTRADA"));

        List <Map<String,String>> posiblesErrores = castingDtos.aMateriaDto(materia,materiaDto1);

        Map<String, String> errorEsperado = new HashMap<>() {{
            put("Materia Id", "2");
            put("Mensaje", "MATERIA NO ENCOTRADA");
        }};
        List<Map<String,String>> listaMapEsperado = new ArrayList<>(){{
            add(errorEsperado);
        }};
        assertEquals(listaMapEsperado,posiblesErrores);
        assertEquals(materiaEsperada,materia);
    }
    @Test
    void aProfesorDto() throws ProfesorException, MateriaNotFoundException {
        List<Integer> idMaterias = new ArrayList<>(){{
            add(1);
            add(2);
        }};
        profesorDto.setMateriasDictadasID(idMaterias);
        List<Materia> dictadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};

        profesor_1.setListaMateriasDictadas(dictadas);
        Profesor profesor = new Profesor();

        when(materiaDao.findMateria(eq(1))).thenReturn(materia_1);
        when(materiaDao.findMateria(eq(2))).thenReturn(materia_2);

        List <Map<String,String>> posiblesErrores = castingDtos.aProfesorDto(profesor,profesorDto);

        assertTrue(posiblesErrores.isEmpty());
        assertEquals(profesor,profesor_1);
    }
    @Test
    void getListaMateriaPorId() throws MateriaNotFoundException {
        List<Integer> idMaterias = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }};
        List<Materia> materiasEsperadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
            add(materia_3);
        }};

        when(materiaDao.findMateria(eq(1))).thenReturn(materia_1);
        when(materiaDao.findMateria(eq(2))).thenReturn(materia_2);
        when(materiaDao.findMateria(eq(3))).thenReturn(materia_3);

        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        List<Materia> materiasObtenidas = castingDtos.getListaMateriaPorId(idMaterias, posiblesErrores);

        assertTrue(posiblesErrores.isEmpty());
        assertEquals(materiasEsperadas,materiasObtenidas);
    }

    @Test
    void getListaMateriaPorIdSinMateria() throws MateriaNotFoundException {
        List<Integer> idMaterias = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }};
        List<Materia> materiasEsperadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_3);
        }};

        when(materiaDao.findMateria(eq(1))).thenReturn(materia_1);
        when(materiaDao.findMateria(eq(2))).thenThrow(new MateriaNotFoundException("MATERIA NO ENCOTRADA"));
        when(materiaDao.findMateria(eq(3))).thenReturn(materia_3);

        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        List<Materia> materiasObtenidas = castingDtos.getListaMateriaPorId(idMaterias, posiblesErrores);

        Map<String, String> errorEsperado = new HashMap<>() {{
            put("Materia Id", "2");
            put("Mensaje", "MATERIA NO ENCOTRADA");
        }};
        List<Map<String,String>> listaMapEsperado = new ArrayList<>(){{
            add(errorEsperado);
        }};
        assertEquals(listaMapEsperado,posiblesErrores);
        assertEquals(materiasEsperadas,materiasObtenidas);
    }
}