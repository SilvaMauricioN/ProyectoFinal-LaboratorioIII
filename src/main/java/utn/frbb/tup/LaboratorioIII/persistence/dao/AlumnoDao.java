package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;

import java.util.List;

public interface AlumnoDao {
    void saveAlumno(Alumno alumno) throws AlumnoNotFoundException;
    Alumno findAlumnoId(Integer id) throws AlumnoNotFoundException;
    List<Alumno> findAlumnoApellido(String apellido) throws AlumnoNotFoundException;
    void upDateAlumno(Alumno alumno);
    void deleteAlumno(Integer idAlumno);
}
