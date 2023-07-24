package utn.frbb.tup.LaboratorioIII.business.service;

import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;


import java.util.List;

public interface MateriaService {
    Materia crearMateria(MateriaDto inputData) throws MateriaNotFoundException;

    List<Materia> getAllMaterias();

    Materia findMateria(int materiaId) throws MateriaNotFoundException;
}
