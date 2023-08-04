package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.*;

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
    @PutMapping("/{idAlumno}")
    public AlumnoDtoSalida actualizarMateria(@PathVariable("idAlumno") Integer id, @RequestBody AlumnoDto alumnoDto) throws IllegalAccessException, AsignaturaInexistenteException, AlumnoNotFoundException {
        Validator.ValidarCampos(alumnoDto);
        return alumnoService.actualizarMateria(id, alumnoDto);
    }
    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<String> deleteAlumno(@PathVariable("idAlumno") Integer idAlumno) throws ProfesorException, AlumnoNotFoundException {
        alumnoService.deleteAlumno(idAlumno);
        return ResponseEntity.status(HttpStatus.OK)
                .body("ALUMNO ELIMINADO DE BASE DE DATOS");
    }


}
