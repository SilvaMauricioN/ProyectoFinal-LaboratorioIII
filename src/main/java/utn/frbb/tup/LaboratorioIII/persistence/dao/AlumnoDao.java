package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;

import java.util.List;

public interface AlumnoDao {
    void saveAlumno(Alumno alumno);

    Alumno findAlumno(Long dni);

    List<Alumno> findAlumno(String apellido) throws AlumnoNotFoundException;
    Alumno upDateAlumno(Long dni);
    Alumno loadAlumno(Long dni);

}
