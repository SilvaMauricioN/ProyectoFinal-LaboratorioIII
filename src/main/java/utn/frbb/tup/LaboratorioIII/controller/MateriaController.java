package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping
    public List<MateriaDtoSalida> getMaterias() {
        return (materiaService.getAllMaterias());
    }
    @PostMapping("/" )
    //crear materia
    public MateriaDtoSalida crearMateria(@RequestBody MateriaDto materiaDto) throws MateriaNotFoundException, IllegalAccessException, ProfesorException, CorrelatividadException {
        Validator.ValidarCampos(materiaDto);
        return materiaService.crearMateria(materiaDto);
    }
    @PutMapping("/{materiaId}")
    public MateriaDtoSalida actualizarMateria(@PathVariable("materiaId") Integer id, @RequestBody MateriaDto materiaDto) throws IllegalAccessException, ProfesorException, MateriaNotFoundException, CorrelatividadException {
        Validator.ValidarCampos(materiaDto);
        return materiaService.actualizarMateria(id,materiaDto);
    }
    //Buscar materia por identificador
    @GetMapping("/{materiaId}")
    public MateriaDtoSalida buscarMateria(@PathVariable("materiaId") int materiaId) throws MateriaNotFoundException {
        return materiaService.findMateria(materiaId);
    }
}
