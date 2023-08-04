package utn.frbb.tup.LaboratorioIII.business.service;

import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;

import java.util.List;

public interface AlumnoService {
    void aprobarAsignatura(int materiaId, int nota, long dni) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException, AlumnoNotFoundException;
    AlumnoDtoSalida crearAlumno(AlumnoDto dtoAlumno) throws AsignaturaInexistenteException, AlumnoNotFoundException;
    List<Alumno> buscarAlumno(String apellido) throws AlumnoNotFoundException;

    AlumnoDtoSalida actualizarMateria(Integer id, AlumnoDto alumnoDto) throws AsignaturaInexistenteException, AlumnoNotFoundException;

    void deleteAlumno(Integer idAlumno) throws AlumnoNotFoundException;
}
