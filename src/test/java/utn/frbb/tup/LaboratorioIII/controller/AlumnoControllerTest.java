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
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Nota;
import utn.frbb.tup.LaboratorioIII.model.dto.*;
import utn.frbb.tup.LaboratorioIII.model.exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class AlumnoControllerTest {
    @InjectMocks
    AlumnoController alumnoController;
    @Mock
    AlumnoService alumnoService;
    private MockMvc mvc;
    private static ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(alumnoController).build();
    }
    @Test
    void crearAlumno() throws Exception {
        when(alumnoService.crearAlumno(any(AlumnoDto.class))).thenReturn(new AlumnoDtoSalida());

        AlumnoDto alumnoDto = new AlumnoDto("Nicola","Silva",32123534L);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/alumno/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDto))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(new AlumnoDtoSalida(), mapper.readValue(result.getResponse().getContentAsString(),AlumnoDtoSalida.class));
    }
    @Test
    void crearAlumnoException() throws AsignaturaInexistenteException, AlumnoNotFoundException {
        when(alumnoService.crearAlumno(any(AlumnoDto.class))).thenThrow(new AlumnoNotFoundException("EL ALUMNO YA EXISTE"));
        AlumnoDto alumnoDto = new AlumnoDto("Nicola","Silva",32123534L);

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.post("/alumno")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(alumnoDto))
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });

        String MensajeEsperado = "EL ALUMNO YA EXISTE";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof AlumnoNotFoundException);
    }

    @Test
    void buscarAlumnoId() throws Exception {
        AlumnoDtoSalida alumnoDtoSalida = new AlumnoDtoSalida("Mauricio","Silva",1223212L);

        when(alumnoService.buscarAlumnoPorId(1)).thenReturn(alumnoDtoSalida);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/alumno/{idAlumno}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        AlumnoDtoSalida Esperado = new AlumnoDtoSalida("Mauricio","Silva",1223212L);
        Assertions.assertEquals(Esperado, mapper.readValue(result.getResponse().getContentAsString(), AlumnoDtoSalida.class));
    }
    @Test
    void actualizarAlumnoException() throws Exception {
        AlumnoDto alumnoDto = new AlumnoDto("Mauricio","Silva",121231L);

        when(alumnoService.actualizarAlumno(anyInt(),any(AlumnoDto.class) )).thenThrow(new AlumnoNotFoundException("ALUMNO NO ENCONTRADO"));

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}",1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(alumnoDto))
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });

        String MensajeEsperado = "ALUMNO NO ENCONTRADO";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof AlumnoNotFoundException);
    }

    @Test
    void actualizarAlumno() throws Exception {
        AlumnoDto alumnoDto = new AlumnoDto("Mauricio","Silva",121231L);
        AlumnoDtoSalida alumnoDtoSalida = new AlumnoDtoSalida("Mauricio","Silva",121231L);

        when(alumnoService.actualizarAlumno(anyInt(),any(AlumnoDto.class) )).thenReturn(alumnoDtoSalida);

        when(alumnoService.actualizarAlumno(1,alumnoDto)).thenReturn(alumnoDtoSalida);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDto))
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        AlumnoDtoSalida alumnoDtoSalidaObtenido = mapper.readValue(responseContent, AlumnoDtoSalida.class);

        Assertions.assertEquals(alumnoDtoSalida,alumnoDtoSalidaObtenido);
    }
    @Test
    void deleteAlumno() throws Exception {
        Mockito.doNothing().when(alumnoService).deleteAlumno(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/alumno/{idAlumno}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andReturn();

        String MensajeEsperado = "ALUMNO ELIMINADO DE BASE DE DATOS";
        String MensajeObtenido = result.getResponse().getContentAsString();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
    }
    @Test
    void actualizarEstadoAsignaturaNotaNovalida() {
        Nota nota = new Nota(-1, EstadoAsignatura.CURSADA);

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}/asignatura/{idAsignatura}",1,1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(nota))
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });

        String MensajeEsperado = "LA NOTA DEBE ESTAR ENTRE 0 Y 10";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
    }
    @Test
    void actualizarEstadoAsignaturaEstadoIncorrecto() {
        Nota nota = new Nota(7, EstadoAsignatura.NO_CURSADA);

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.put("/alumno/{idAlumno}/asignatura/{idAsignatura}",1,1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(nota))
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });

        String MensajeEsperado = "LA MATERIA DEBE ESTAR CURSADA";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof EstadoIncorrectoException);
    }
}