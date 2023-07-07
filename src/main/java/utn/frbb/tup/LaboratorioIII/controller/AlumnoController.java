package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;

import java.util.List;

@RestController
//Endpoint
@RequestMapping("Alumno")
public class AlumnoController {

    //inyeccion de dependencia, clase que implementa alumno sevice. intancia de la clase implementada(no haces ningun New)
    private final AlumnoService alumnoService;
    @Autowired
    public AlumnoController(AlumnoService alumnoService){
        this.alumnoService = alumnoService;
    }
    //Accesible desde un post
    @PostMapping("/")
    public Alumno crearAlumno(@RequestBody AlumnoDto alumnoDto) {

        return alumnoService.crearAlumno(alumnoDto);
    }
    @GetMapping
    public List<Alumno> buscarAlumno(@RequestParam String apellido) throws AlumnoNotFoundException {
        return alumnoService.buscarAlumno(apellido);
    }


}
