package utn.frbb.tup.LaboratorioIII.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaResponse;
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
    public List<Materia> getMaterias() {
        return (materiaService.getAllMaterias());
    }
    @PostMapping("/" )
    //crear materia
    public MateriaResponse crearMateria(@RequestBody MateriaDto materiaDto) throws MateriaNotFoundException, IllegalAccessException, ProfesorException {
        Validator.ValidarCampos(materiaDto);
        return materiaService.crearMateria(materiaDto);
    }
    @PutMapping("/{materiaId}")
    public Materia actualizarMateria(@PathVariable("materiaId") Integer id, @RequestBody MateriaDto materiaDto) throws IllegalAccessException, ProfesorException, MateriaNotFoundException {
        Validator.ValidarCampos(materiaDto);
        return materiaService.actualizarMateria(id,materiaDto);
    }
    //Buscar materia por identificador
    @GetMapping("/{materiaId}")
    public Materia buscarMateria(@PathVariable("materiaId") int materiaId) throws MateriaNotFoundException {
        return materiaService.findMateria(materiaId);
    }
}
