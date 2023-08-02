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
        Profesor p = mock(Profesor.class);
        Materia m = new Materia("Laboratorio I", 1, 1);
        Materia m1 = new Materia("Laboratorio II", 1, 2);
        Materia m3 = new Materia("Laboratorio III", 2, 1);
        List<Materia> materiasCorelativas = new ArrayList<>(){{
            add(m);
            add(m1);
        }};
        m3.setListaCorrelatividades(materiasCorelativas);
        List<Materia> dictadas = new ArrayList<>(){{
            add(m3);
        }};

        when(profesorDao.findProfesor(anyInt())).thenReturn(p);
        when(p.getMateriasDictadas()).thenReturn(dictadas);

        List<MateriaDtoSalida> materiaDtoSalida = profesorServiceImpl.getMateriasDictadas(3);
        MateriaDtoSalida mSalida = new MateriaDtoSalida("LABORATORIO III",2,1);
        MateriaDtoSalida mSalida2 = new MateriaDtoSalida("LABORATORIO II",1,2);
        MateriaDtoSalida mSalida3 = new MateriaDtoSalida("LABORATORIO I",1,1);
        List<MateriaDtoSalida> mdS = new ArrayList<>(){{
            add(mSalida3);
            add(mSalida2);
        }};
        mSalida.setCorrelativas(mdS);
        List<MateriaDtoSalida> materiasSalidaEsperada = new ArrayList<>(){{
            add(mSalida);
        }};
        assertEquals(materiasSalidaEsperada,materiaDtoSalida, "Las Listas no son Iguales");
    }
}