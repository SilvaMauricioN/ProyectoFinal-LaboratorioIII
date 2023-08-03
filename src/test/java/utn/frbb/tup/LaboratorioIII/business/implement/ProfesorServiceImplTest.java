package utn.frbb.tup.LaboratorioIII.business.implement;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ProfesorServiceImplTest {
    @Mock
    private ProfesorDao profesorDao;
    @InjectMocks
    private ProfesorServiceImpl profesorServiceImpl;
    @Test
    void getMateriasDictadas() throws ProfesorException {
        Profesor profesor = mock(Profesor.class);
        Materia materia_1 = new Materia("Laboratorio I", 1, 1);
        Materia materia_2 = new Materia("Laboratorio II", 1, 2);
        Materia materia_3 = new Materia("Laboratorio III", 2, 1);
        List<Materia> materiasCorelativas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};
        materia_3.setListaCorrelatividades(materiasCorelativas);
        List<Materia> dictadas = new ArrayList<>(){{
            add(materia_3);
        }};


        when(profesorDao.findProfesor(anyInt())).thenReturn(profesor);
        when(profesor.getMateriasDictadas()).thenReturn(dictadas);

        List<MateriaDtoSalida> materiaDtoSalida = profesorServiceImpl.getMateriasDictadas(3);
        MateriaDtoSalida materiaDtoS_1 = new MateriaDtoSalida("LABORATORIO III",2,1);
        MateriaDtoSalida materiaDtoS_2 = new MateriaDtoSalida("LABORATORIO II",1,2);
        MateriaDtoSalida materiaDtoS_3 = new MateriaDtoSalida("LABORATORIO I",1,1);
        List<MateriaDtoSalida> materiasDtoSalida = new ArrayList<>(){{
            add(materiaDtoS_3);
            add(materiaDtoS_2);
        }};
        materiaDtoS_1.setCorrelativas(materiasDtoSalida);
        List<MateriaDtoSalida> materiasSalidaEsperada = new ArrayList<>(){{
            add(materiaDtoS_1);
        }};

        assertEquals(materiasSalidaEsperada,materiaDtoSalida, "Las Listas no son Iguales");
    }
}