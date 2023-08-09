package utn.frbb.tup.LaboratorioIII.controller;

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
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class MateriaControllerTest {

    @InjectMocks
    MateriaController materiaController;
    @Mock
    MateriaService materiaService;
    private MockMvc mvc;
    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(materiaController).build();
    }
    @Test
    void getMateria() throws Exception {
        MateriaDtoSalida materiaDtoSalida = new MateriaDtoSalida("Programacion",1,1);

        Mockito.when(materiaService.findMateria(1)).thenReturn(materiaDtoSalida);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/materia/{idMateria}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(materiaDtoSalida, mapper.readValue(result.getResponse().getContentAsString(),MateriaDtoSalida.class));
    }
    @Test
    void getMateriaException() throws MateriaNotFoundException{
        Mockito.when(materiaService.findMateria(1)).thenThrow(new MateriaNotFoundException("MATERIA NO ENCONTRADA"));

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.get("/materia/{idMateria}",1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                    .andReturn();
        });

        String MensajeEsperado = "MATERIA NO ENCONTRADA";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof MateriaNotFoundException);
    }
    @Test
    void crearMateria() throws Exception {
        Mockito.when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new MateriaDtoSalida());

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setYear(1);
        materiaDto.setCuatrimestre(2);
        materiaDto.setNombre("Laboratorio II");
        materiaDto.setProfesorId(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/materia/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(materiaDto))
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(new MateriaDtoSalida(), mapper.readValue(result.getResponse().getContentAsString(),MateriaDtoSalida.class));
    }
    @Test
    void crearMateriaValorInvalido() throws Exception {
        Mockito.when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new MateriaDtoSalida());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/materia/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"nombre\" : \"Laboratorio II\",\n" +
                        "    \"year\" : \"segundo\", \n" +
                        "    \"cuatrimestre\" : 1,\n" +
                        "    \"profesorId\" : 2 \n"+
                        "}")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    void crearMateriaFaltanDatosException() throws Exception {
        Mockito.when(materiaService.crearMateria(any(MateriaDto.class))).thenReturn(new MateriaDtoSalida());

        Exception exception = assertThrows(ServletException.class,()->{
            mvc.perform(MockMvcRequestBuilders.post("/materia/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "    \"nombre\" : \"Laboratorio II\",\n" +
                                    "    \"year\" : \"\", \n" +
                                    "    \"cuatrimestre\" : 1,\n" +
                                    "    \"profesorId\" : 2 \n"+
                                    "}")
                            .accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                    .andReturn();
        });

        String MensajeEsperado = "Falta el campo: year";
        String MensajeObtenido = exception.getMessage();
        assertTrue(MensajeObtenido.contains(MensajeEsperado));
        assertTrue(exception.getCause() instanceof IllegalArgumentException);
    }
}