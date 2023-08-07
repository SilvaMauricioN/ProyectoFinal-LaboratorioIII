package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class MateriaDaoMemoryImplTest {
    @Mock
    ProfesorDao profesorDao;
    @InjectMocks
    MateriaDaoMemoryImpl materiaDaoMemoryImpl;
    private static Materia materia_1, materia_2, materia_3, materia_4;
    @BeforeEach
    public void setUp() throws MateriaNotFoundException, ProfesorException {
        materia_1 = new Materia("Programacion I", 1, 1);
        materia_2 = new Materia("Laboratorio I", 1, 1);
        materia_3 = new Materia("Sistema de Datos", 1, 1);
        materia_4 = new Materia("Ingles I", 1, 1);
    }
    @Test
    void saveMateria() throws MateriaNotFoundException {
        Materia nuevaEsperada = new Materia("Estadistica",1,2);
        nuevaEsperada.setMateriaId(5);
        materia_1.setMateriaId(1);
        materia_2.setMateriaId(2);
        materia_3.setMateriaId(3);
        materia_4.setMateriaId(4);
        List<Materia> esperadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
            add(materia_3);
            add(materia_4);
            add(nuevaEsperada);
        }};
        Materia nueva = new Materia("Estadistica",1,2);
        materiaDaoMemoryImpl.saveMateria(nueva);
        List<Materia> obtenidas = materiaDaoMemoryImpl.getAllMaterias();

        assertEquals(esperadas,obtenidas);
        assertEquals(5,obtenidas.size());
    }
    @Test
    void saveMateriaExcepcion() {
        Exception exception = assertThrows(MateriaNotFoundException.class, () -> {
            materiaDaoMemoryImpl.saveMateria(materia_1);
        });

        String MensajeEsperado = "LA MATERIA YA EXISTE";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }


    @Test
    void findMateria() throws MateriaNotFoundException {
        Materia Obtenida = materiaDaoMemoryImpl.findMateria(1);
        materia_1.setMateriaId(1);
        assertEquals(materia_1,Obtenida);
    }

    @Test
    void findMateriaException(){
        Exception exception = assertThrows(MateriaNotFoundException.class, () -> {
            materiaDaoMemoryImpl.findMateria(5);
        });

        String MensajeEsperado = "MATERIA NO ENCONTRADA";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    //primero se inicializa materiaDaoImpl, que a su vez inicializa sus materias, dentro de este metod
    //se llama a ProfesdorDao, para buscar a los profesores y  asignarselos a las materias. Como
    // esto se realiza primer, no puedo simular la respuesta del mock de profesorDao,
    @Test
    void getAllMaterias(){
        materia_1.setMateriaId(1);
        materia_2.setMateriaId(2);
        materia_3.setMateriaId(3);
        materia_4.setMateriaId(4);
        List<Materia> guardadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
            add(materia_3);
            add(materia_4);
        }};

        List<Materia> materiasObtenidas = materiaDaoMemoryImpl.getAllMaterias();

        assertEquals(guardadas,materiasObtenidas);
    }
    @Test
    void upDateMateria() {
        Profesor profesor_1 = new Profesor("Marcos","Utarroz", "Lic.",124321);
        Materia actualizada = new Materia("Programacion II",1,1);
        actualizada.setMateriaId(1);
        actualizada.setProfesor(profesor_1);

        materiaDaoMemoryImpl.upDateMateria(actualizada);
        List<Materia> materiasObtenidas = materiaDaoMemoryImpl.getAllMaterias();

        List<Materia> guardadas = new ArrayList<>(){{
            add(actualizada);
            add(materia_2);
            add(materia_3);
            add(materia_4);
        }};

        assertEquals(guardadas,materiasObtenidas);
        assertEquals(4,materiasObtenidas.size());
    }
    @Test
    void deleteMateria() throws MateriaNotFoundException {
        materiaDaoMemoryImpl.deleteMateria(1);
        List<Materia> obtenidas = materiaDaoMemoryImpl.getAllMaterias();

        materia_2.setMateriaId(2);
        materia_3.setMateriaId(3);
        materia_4.setMateriaId(4);
        List<Materia> guardadas = new ArrayList<>(){{
            add(materia_2);
            add(materia_3);
            add(materia_4);
        }};

        assertEquals(guardadas,obtenidas);
        assertEquals(3,obtenidas.size());
    }
}