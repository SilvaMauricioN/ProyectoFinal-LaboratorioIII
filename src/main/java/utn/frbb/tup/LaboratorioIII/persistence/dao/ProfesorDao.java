package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

public interface ProfesorDao {
    void saveProfesor(Profesor profesor) throws ProfesorException;
    Profesor findProfesor(Integer profesorDni);
}
