package utn.frbb.tup.LaboratorioIII.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class ProfesorControllerTest {
    @InjectMocks
    ProfesorController profesorController;
    @Mock
    ProfesorService profesorService;
    private MockMvc mvc;
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(profesorController).build();
    }

    @Test
    void crearProfesor() throws Exception {
        Mockito.when(profesorService.crearProfesor(any(ProfesorDto.class))).thenReturn(new ProfesorDtoSalida());

        ProfesorDto profesorDto = new ProfesorDto("Nicolas","Silva","Tec",1232212 );

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/profesor/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(profesorDto))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(new ProfesorDtoSalida(), mapper.readValue(result.getResponse().getContentAsString(),ProfesorDtoSalida.class));
    }
    @Test
    void actualizarProfesorException() throws ProfesorException, MateriaNotFoundException {
        ProfesorDto profesorDto = new ProfesorDto("Mauricio","Silva","Tec",121231);
        Mockito.when(profesorService.actualizarProfesor(1, profesorDto)).thenThrow(new ProfesorException("PROFESOR NO ENCONTRADO"));

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.put("/profesor/{idProfesor}",1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(profesorDto))
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });

        String MensajeEsperado = "PROFESOR NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof ProfesorException);
    }
    @Test
    void getMateriasProfesor() throws Exception {
        List<MateriaDtoSalida> esperado =  new ArrayList<>(){{
            add(new MateriaDtoSalida("Programacion",1,1));
            add(new MateriaDtoSalida("Laboratorio",1 ,2));
        }};

        Mockito.when(profesorService.getMateriasDictadas(1)).thenReturn(esperado);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/profesor/materias")
                        .param("idProfesor", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

        List<MateriaDtoSalida> materiasResultantes = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<MateriaDtoSalida>>() {});

        Assertions.assertEquals(esperado,materiasResultantes);
        assertEquals(2, materiasResultantes.size());
    }

    @Test
    void getProfesores() throws ProfesorException {
        Mockito.when(profesorService.getAllProfesor()).thenThrow(new ProfesorException("SIN PROFESORES GUARDADOS"));

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.get("/profesor/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });
        String MensajeEsperado = "SIN PROFESORES GUARDADOS";
        String MensajeObtenido = exception.getMessage();

        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof ProfesorException);
    }

    @Test
    void deleteProfesorNoEncontrado() throws ProfesorException {
        Mockito.doThrow(new ProfesorException("PROFESOR NO ENCONTRADO")).when(profesorService).deleteProfesor(1);


        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.delete("/profesor/{idProfesor}",1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });
        String MensajeEsperado = "PROFESOR NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();

        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof ProfesorException);
    }

    @Test
    void deleteProfesor() throws Exception {
        Mockito.doNothing().when(profesorService).deleteProfesor(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/profesor/{idProfesor}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andReturn();

        String MensajeEsperado = "PROFESOR ELIMINADO DE BASE DE DATOS";
        String MensajeObtenido = result.getResponse().getContentAsString();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
}