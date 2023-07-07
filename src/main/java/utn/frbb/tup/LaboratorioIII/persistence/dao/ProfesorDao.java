package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Profesor;

public interface ProfesorDao {
    void saveProfesor(Profesor profesor);
    Profesor findProfesor(Integer profesorDni);
}
