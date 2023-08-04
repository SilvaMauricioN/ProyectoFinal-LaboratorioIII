package utn.frbb.tup.LaboratorioIII.business.service;

import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.Nota;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;

public interface AsignaturaService {
    Asignatura getAsignatura(Integer idAlumno, Integer idAsignaturas) throws AsignaturaInexistenteException;
    void actualizarAsignatura(Integer idAlumno, Asignatura asignatura);
    Asignatura aprobarAsignatura(Integer idAlumno, Integer idAsignatura, Nota nota) throws CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AsignaturaInexistenteException;
}
