package utn.frbb.tup.LaboratorioIII.business.implement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CastingDtosTest {
    @Mock
    private MateriaDao materiaDao;
    @InjectMocks
    CastingDtos castingDtos;


    @Test
    void aMateriaDtoSalida() {
        Materia materia_1 = new Materia("Laboratorio I", 1, 1);
        Materia materia_2 = new Materia("Laboratorio II", 1, 2);
        Materia materia_3 = new Materia("Laboratorio III", 2, 1);
        List<Materia> materiasCorelativas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
        }};
        materia_3.setListaCorrelatividades(materiasCorelativas);
        MateriaDtoSalida materiaEsperada_1 = new MateriaDtoSalida("Laboratorio III",2,1);
        MateriaDtoSalida materiaEsperada_2 = new MateriaDtoSalida("Laboratorio II",1,2);
        MateriaDtoSalida materiaEsperada_3 = new MateriaDtoSalida("Laboratorio I",1,1);

        List<MateriaDtoSalida> listaEsperada = new ArrayList<>(){{
            add(materiaEsperada_3);
            add(materiaEsperada_2);
        }};
        materiaEsperada_1.setCorrelativas(listaEsperada);
        MateriaDtoSalida materiaDtoSalidaObtenida = castingDtos.aMateriaDtoSalida(materia_3);
        assertEquals(materiaEsperada_1,materiaDtoSalidaObtenida, "Las Materias a Mostar no son Iguales");
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

    }

    @Test
    void toProfesorDtoSalida() {
    }

    @Test
    void aAlumnoDtoSalida() {
    }

    @Test
    void aAsignaturaDtoSalida() {
    }

    @Test
    void aMateriaDto() {
    }

    @Test
    void aProfesorDto() {
    }

    @Test
    void aAlumnoDto() {
    }

    @Test
    void getListaMateriaPorId() {
    }
}