package utn.frbb.tup.LaboratorioIII.business.implement;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {
    @Mock
    private MateriaDao materiaDao;
    @Mock
    private ProfesorDao profesorDao;
    @Mock
    private CastingDtos castingDtos = new CastingDtos(materiaDao);
    @InjectMocks
    private MateriaServiceImpl materiaServiceImpl;
    private static Materia materia_1, materia_2;
    private static MateriaDtoSalida materiaDtoSalida_1, materiaDtoSalida_2;
    private static Profesor profesor_2;
    private static ProfesorDtoSalida profesorDtoSalida_2;

    @BeforeAll
    public static void setUp() {
        materia_1 = new Materia("Laboratorio I", 1, 1);
        materia_2 = new Materia("Programacion II", 1, 2);
        materiaDtoSalida_1 = new MateriaDtoSalida("Laboratorio I", 1, 1);
        materiaDtoSalida_2 = new MateriaDtoSalida("Programacion II",1,2);
        profesor_2 = new Profesor("Ricardo", "Coppo", "Ing", 43145665);
        profesorDtoSalida_2 = new ProfesorDtoSalida("Ricardo", "Coppo", "Ing", 43145665);
    }
    @Test
    void getAllMaterias() {
        List<Materia> guardadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};

        List<MateriaDtoSalida> esperadas = new ArrayList<>(){{
            add(materiaDtoSalida_1);
            add(materiaDtoSalida_2);
        }};
        when(materiaDao.getAllMaterias()).thenReturn(guardadas);
        for(int i=0; i < guardadas.size(); i++){
            when(castingDtos.aMateriaDtoSalida(guardadas.get(i))).thenReturn(esperadas.get(i));
        }
        List<MateriaDtoSalida> Obtenidas = materiaServiceImpl.getAllMaterias();

        assertEquals(esperadas,Obtenidas);
    }
    @Test
    void getAllMateriasConProfesor(){
        materia_1.setProfesor(profesor_2);
        List<Materia> guardadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};

        List<MateriaDtoSalida> esperadas = new ArrayList<>(){{
            add(materiaDtoSalida_1);
            add(materiaDtoSalida_2);
        }};

        when(materiaDao.getAllMaterias()).thenReturn(guardadas);
        for(int i=0; i < guardadas.size(); i++){
            when(castingDtos.aMateriaDtoSalida(guardadas.get(i))).thenReturn(esperadas.get(i));
            if(guardadas.get(i).getProfesor() != null){
                when(castingDtos.toProfesorDtoSalida(materia_1.getProfesor())).thenReturn(profesorDtoSalida_2);
            }
        }
        materiaDtoSalida_1.setProfesor(profesorDtoSalida_2);
        List<MateriaDtoSalida> MateriasEsperadas = new ArrayList<>(){{
            add(materiaDtoSalida_1);
            add(materiaDtoSalida_2);
        }};

        List<MateriaDtoSalida> Obtenidas = materiaServiceImpl.getAllMaterias();

        verify(materiaDao,times(1)).getAllMaterias();
        verify(castingDtos,times(1)).aMateriaDtoSalida(eq(materia_1));
        verify(castingDtos,times(1)).aMateriaDtoSalida(eq(materia_2));
        assertEquals(MateriasEsperadas,Obtenidas);
    }
    @Test
    void findMateria() throws MateriaNotFoundException {
        when(materiaDao.findMateria(eq(2))).thenReturn(materia_2);
        when(castingDtos.aMateriaDtoSalida(materia_2)).thenReturn(materiaDtoSalida_2);
        MateriaDtoSalida materiaObtenida = materiaServiceImpl.findMateria(2);

        verify(materiaDao,times(1)).findMateria(2);
        verify(castingDtos,times(1)).aMateriaDtoSalida(materia_2);
        assertEquals(materiaDtoSalida_2,materiaObtenida);
    }

}