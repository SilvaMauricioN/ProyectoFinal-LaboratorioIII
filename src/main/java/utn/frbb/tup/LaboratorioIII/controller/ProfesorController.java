package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;

import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaResponse;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;

@RestController
@RequestMapping("Profesor")
public class ProfesorController {
    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService){
        this.profesorService = profesorService;
    }
    @PostMapping("/" )
    //crear materia
    public Profesor crearProfesor(@RequestBody ProfesorDto profesorDto){
        return profesorService.crearProfesor(profesorDto);
    }
}
