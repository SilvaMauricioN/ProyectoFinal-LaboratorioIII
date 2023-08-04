package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;

import java.util.List;

public interface AlumnoDao {
    void saveAlumno(Alumno alumno) throws AlumnoNotFoundException;

    Alumno findAlumno(Integer id) throws AlumnoNotFoundException;

    List<Alumno> findAlumno(String apellido) throws AlumnoNotFoundException;
    void upDateAlumno(Alumno alumno);


    void deleteAlumno(Integer idAlumno);
}
