package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;
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
    private MateriaDaoMemoryImpl materiaDaoMemoryImpl;
    private Materia materia_1, materia_2, materia_3, materia_4;
    private final GeneradorId generadorId = GeneradorId.getInstance();

    @BeforeEach
    public void setUp() throws MateriaNotFoundException {
        materia_1 = new Materia("Programacion I", 1, 1);
        materia_1.setMateriaId(1);
        materia_2 = new Materia("Laboratorio I", 1, 1);
        materia_2.setMateriaId(2);
        materia_3 = new Materia("Sistema de Datos", 1, 1);
        materia_3.setMateriaId(3);
        materia_4 = new Materia("Ingles I", 1, 1);
        materia_4.setMateriaId(4);
    }
    @AfterEach
    public void resetIdGenerator() {
        generadorId.reset();
    }
    @Test
    void saveMateria() throws MateriaNotFoundException {
        Materia nuevaEsperada = new Materia("Estadistica",1,2);
        nuevaEsperada.setMateriaId(5);
        List<Materia> esperadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
            add(materia_3);
            add(materia_4);
            add(nuevaEsperada);
        }};
        Materia nueva = new Materia("Estadistica",1,2);
        materiaDaoMemoryImpl.saveMateria(nueva);
        assertNotNull(materiaDaoMemoryImpl.findMateria(5));
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
        assertEquals(materia_1,Obtenida);
    }
    @Test
    void findMateriaException(){
        Exception exception = assertThrows(MateriaNotFoundException.class, () -> {
            materiaDaoMemoryImpl.findMateria(10);
        });
        String MensajeEsperado = "MATERIA NO ENCONTRADA";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    void getAllMaterias(){
        List<Materia> guardadas = new ArrayList<>(){{
            add(materia_1);
            add(materia_2);
            add(materia_3);
            add(materia_4);
        }};
        List<Materia> materiasObtenidas = materiaDaoMemoryImpl.getAllMaterias();
        assertEquals(guardadas,materiasObtenidas);
        assertEquals(4 ,materiasObtenidas.size());
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
        List<Materia> guardadas = new ArrayList<>(){{
            add(materia_2);
            add(materia_3);
            add(materia_4);
        }};
        assertEquals(guardadas,obtenidas);
        assertEquals(3,obtenidas.size());
    }
    @Test
    void deleteMateriaException(){
        Exception exception = assertThrows(MateriaNotFoundException.class, () -> {
            materiaDaoMemoryImpl.deleteMateria(10);
        });
        String MensajeEsperado = "MATERIA ID: 10 NO ENCONTRADA";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }

}