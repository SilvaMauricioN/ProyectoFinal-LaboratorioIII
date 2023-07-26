package utn.frbb.tup.LaboratorioIII.business.service;


import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaResponse;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;


import java.util.List;
import java.util.Map;

public interface MateriaService {
    MateriaResponse crearMateria(MateriaDto inputData) throws MateriaNotFoundException;

    List<Materia> getAllMaterias();

    Materia findMateria(int materiaId) throws MateriaNotFoundException;
    List<Materia> getListaMateriaPorId(List<Integer> listaId,List<Map<String,String>> Errores);
}
