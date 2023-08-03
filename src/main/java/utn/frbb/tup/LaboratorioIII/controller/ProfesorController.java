package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import java.util.List;

@RestController
@RequestMapping("profesor")
public class ProfesorController {
    private final ProfesorService profesorService;
    public ProfesorController(ProfesorService profesorService){
        this.profesorService = profesorService;
    }
    @PostMapping
    //crear profesor
    public ProfesorDtoSalida crearProfesor(@RequestBody ProfesorDto profesorDto) throws ProfesorException, IllegalAccessException, MateriaNotFoundException {
        Validator.ValidarCampos(profesorDto);
        return profesorService.crearProfesor(profesorDto);
    }
    @PutMapping("/{idProfesor}")
    public ProfesorDtoSalida actualizarProfesor(@PathVariable("idProfesor") Integer id, @RequestBody ProfesorDto profesorDto) throws IllegalAccessException, ProfesorException, MateriaNotFoundException {
        Validator.ValidarCampos(profesorDto);
        return profesorService.actualizarProfesor(id,profesorDto);
    }
    @GetMapping("/materias")
    public List<MateriaDtoSalida> getMateriasProfesor(@RequestParam Integer idProfesor) throws ProfesorException {
        return profesorService.getMateriasDictadas(idProfesor);
    }
    @GetMapping
    public List<ProfesorDtoSalida> getProfesores() throws ProfesorException {
        return profesorService.getAllProfesor();
    }
    @DeleteMapping("/{idProfesor}")
    public ResponseEntity<String> deleteProfesor(@PathVariable("idProfesor") Integer idProfesor) throws ProfesorException {
        profesorService.deleteProfesor(idProfesor);
        return ResponseEntity.status(HttpStatus.OK)
                .body("PROFESOR ELIMINADO");
    }

}
