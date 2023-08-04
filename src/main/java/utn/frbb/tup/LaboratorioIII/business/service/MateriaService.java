package utn.frbb.tup.LaboratorioIII.business.service;

import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.DtoMateria;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.model.Materia;


import java.util.List;
import java.util.Map;

public interface MateriaService {
    MateriaDtoSalida crearMateria(DtoMateria inputData) throws MateriaNotFoundException, ProfesorException, CorrelatividadException;
    List<MateriaDtoSalida> getAllMaterias();
    MateriaDtoSalida findMateria(int materiaId) throws MateriaNotFoundException;
    List<Materia> getListaMateriaPorId(List<Integer> listaId, List<Map<String,String>> Errores);

    MateriaDtoSalida actualizarMateria(Integer id, DtoMateria dtoMateria) throws MateriaNotFoundException, ProfesorException, CorrelatividadException;
}
