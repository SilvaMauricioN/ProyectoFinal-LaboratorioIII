package utn.frbb.tup.LaboratorioIII.business.service;

import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;


import java.util.List;

public interface MateriaService {
    MateriaDtoSalida crearMateria(MateriaDto inputData) throws MateriaNotFoundException, ProfesorException, CorrelatividadException;
    List<MateriaDtoSalida> getAllMaterias();
    MateriaDtoSalida findMateria(int materiaId) throws MateriaNotFoundException;
//    List<Materia> getListaMateriaPorId(List<Integer> listaId, List<Map<String,String>> Errores);

    MateriaDtoSalida actualizarMateria(Integer id, MateriaDto dtoMateria) throws MateriaNotFoundException, ProfesorException, CorrelatividadException;

    void deleteMateria(Integer idMateria) throws MateriaNotFoundException;
}
