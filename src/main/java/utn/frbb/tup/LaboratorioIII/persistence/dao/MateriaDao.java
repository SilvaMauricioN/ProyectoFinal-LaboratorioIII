package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;

import java.util.List;

public interface MateriaDao {
    void saveMateria(Materia materia) throws MateriaNotFoundException;
    Materia findMateria(Integer materiaId) throws MateriaNotFoundException;
    List<Materia> getAllMaterias();

    void upDateMateria(Materia materia);

    void deleteMateria(Integer idMateria) throws MateriaNotFoundException;
}
