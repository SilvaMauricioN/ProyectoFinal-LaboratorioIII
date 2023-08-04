package utn.frbb.tup.LaboratorioIII.persistence.dao;

import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;

public interface AsignaturaDao {
    void saveAsignatura(Alumno alumno);
    Asignatura findAsignatura(Integer AlumnoId, Integer AsignaturaId) throws AsignaturaInexistenteException;
    void upDateAsignatura(Integer Alumnoid, Asignatura asignatura);

    void deleteAsignaturas(Integer idAlumno) throws AsignaturaInexistenteException;
}
