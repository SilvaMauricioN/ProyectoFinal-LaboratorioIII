package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
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
    public Profesor crearProfesor(@RequestBody ProfesorDto profesorDto) throws ProfesorException, IllegalAccessException, NoSuchFieldException, MateriaNotFoundException {
        Validator.ValidarCampos(profesorDto);
        return profesorService.crearProfesor(profesorDto);
    }
    @PutMapping("/{idProfesor}")
    public Profesor actualizarProfesor(@PathVariable("idProfesor") Integer id, @RequestBody ProfesorDto profesorDto) throws IllegalAccessException, ProfesorException, MateriaNotFoundException {
        Validator.ValidarCampos(profesorDto);
        return profesorService.actualizarProfesor(id,profesorDto);
    }
    @GetMapping("/materias")
    public List<Materia> getMateriasProfesor(@RequestParam Integer idProfesor) throws ProfesorException {
        return profesorService.getMateriasDictadas(idProfesor);
    }
    @GetMapping
    public List<Profesor> getProfesores(){
        return profesorService.getAllProfesor();
    }

}
