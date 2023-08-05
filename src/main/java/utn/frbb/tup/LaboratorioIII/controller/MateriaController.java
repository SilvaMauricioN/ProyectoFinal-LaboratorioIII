package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import java.util.List;

@RestController
@RequestMapping("Materia")
public class MateriaController {
    private final MateriaService materiaService;
    @Autowired
    public MateriaController(MateriaService materiaService){
        this.materiaService = materiaService;
    }
    @GetMapping("/{idMateria}")
    public MateriaDtoSalida buscarMateria(@PathVariable("idMateria") int materiaId) throws MateriaNotFoundException {
        return materiaService.findMateria(materiaId);
    }
    @GetMapping
    public List<MateriaDtoSalida> getMaterias() {
        return (materiaService.getAllMaterias());
    }
    @PostMapping("/" )
    //crear materia
    public MateriaDtoSalida crearMateria(@RequestBody MateriaDto dtoMateria) throws MateriaNotFoundException, IllegalAccessException, ProfesorException, CorrelatividadException {
        Validator.ValidarCampos(dtoMateria);
        return materiaService.crearMateria(dtoMateria);
    }
    @PutMapping("/{idMateria}")
    public MateriaDtoSalida actualizarMateria(@PathVariable("idMateria") Integer id, @RequestBody MateriaDto dtoMateria) throws IllegalAccessException, ProfesorException, MateriaNotFoundException, CorrelatividadException {
        Validator.ValidarCampos(dtoMateria);
        return materiaService.actualizarMateria(id, dtoMateria);
    }
    @DeleteMapping("/{idMateria}")
    public ResponseEntity<String> deleteMateria(@PathVariable("idMateria") Integer idMateria) throws MateriaNotFoundException {
        materiaService.deleteMateria(idMateria);
        return ResponseEntity.status(HttpStatus.OK)
                .body("MATERIA ELIMINADO DE BASE DE DATOS");
    }

}
