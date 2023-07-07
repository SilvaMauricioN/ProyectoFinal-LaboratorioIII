package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;

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
    public List<Materia> getMaterias() {

        return (materiaService.getAllMaterias());
    }

    @PostMapping( )
    //request body, toma lo que viene del postman
    public Materia crearMateria(@RequestBody MateriaDto materiaDto){
        return materiaService.crearMateria(materiaDto);
    }

    //Buscar materia por identificador
    @GetMapping("/{materiaId}")
    public Materia buscarMateria(@PathVariable("materiaId") int materiaId) throws MateriaNotFoundException {
        return materiaService.findMateria(materiaId);
    }


}
