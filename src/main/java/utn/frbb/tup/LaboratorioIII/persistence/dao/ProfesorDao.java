package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

import java.util.List;

public interface ProfesorDao {
    void saveProfesor(Profesor profesor) throws ProfesorException;
    Profesor findProfesor(Integer profesorId) throws ProfesorException;
    List<Profesor> getAllProfesores();

    void upDateProfesor(Profesor profesor);
}
