package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Nota;
import utn.frbb.tup.LaboratorioIII.model.dto.*;
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
    public AlumnoDtoSalida actualizarAlumno(@PathVariable("idAlumno") Integer id, @RequestBody AlumnoDto alumnoDto) throws IllegalAccessException, AsignaturaInexistenteException, AlumnoNotFoundException {
        Validator.ValidarCampos(alumnoDto);
        return alumnoService.actualizarAlumno(id, alumnoDto);
    }
    @DeleteMapping("/{idAlumno}")
    public ResponseEntity<String> deleteAlumno(@PathVariable("idAlumno") Integer idAlumno) throws AlumnoNotFoundException {
        alumnoService.deleteAlumno(idAlumno);
        return ResponseEntity.status(HttpStatus.OK)
                .body("ALUMNO ELIMINADO DE BASE DE DATOS");
    }

    @PutMapping("/{idAlumno}/asignatura/{idAsignatura}")
    public AsignaturaDtoSalida actualizarEstadoAsignatura(@PathVariable("idAlumno") Integer idAlumno,
                                                          @PathVariable("idAsignatura") Integer idAsignatura,
                                                          @RequestBody Nota nota) throws AsignaturaInexistenteException, CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AlumnoNotFoundException {
        if(nota.nota() < 0 || nota.nota() > 10){
            throw new IllegalArgumentException("LA NOTA DEBE ESTAR ENTRE 0 Y 10");
        }
        return alumnoService.modificaEstadoAsignatura(idAlumno, idAsignatura, nota);
    }


}
