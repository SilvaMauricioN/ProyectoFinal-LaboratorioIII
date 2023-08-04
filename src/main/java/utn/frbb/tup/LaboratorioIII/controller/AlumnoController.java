package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;

import java.util.List;

@RestController
//Endpoint
@RequestMapping("alumno")
public class AlumnoController {
    private final AlumnoService alumnoService;
    @Autowired
    public AlumnoController(AlumnoService alumnoService){
        this.alumnoService = alumnoService;
    }
    //Accesible desde un post
    @PostMapping()
    public AlumnoDtoSalida crearAlumno(@RequestBody AlumnoDto dtoAlumno) throws AsignaturaInexistenteException, AlumnoNotFoundException {
        return alumnoService.crearAlumno(dtoAlumno);
    }
    @GetMapping
    public List<Alumno> buscarAlumno(@RequestParam String apellido) throws AlumnoNotFoundException {
        return alumnoService.buscarAlumno(apellido);
    }


}
