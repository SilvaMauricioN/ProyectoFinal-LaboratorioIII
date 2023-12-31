package utn.frbb.tup.LaboratorioIII.business.service;

import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Nota;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDto;
import utn.frbb.tup.LaboratorioIII.model.dto.AlumnoDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.AsignaturaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.*;

import java.util.List;

public interface AlumnoService {
    AlumnoDtoSalida crearAlumno(AlumnoDto dtoAlumno) throws AsignaturaInexistenteException, AlumnoNotFoundException;
    List<AlumnoDtoSalida> buscarAlumnoPorApellido(String apellido) throws AlumnoNotFoundException;
    AlumnoDtoSalida buscarAlumnoPorId(Integer alumnoId) throws AlumnoNotFoundException;
    AlumnoDtoSalida actualizarAlumno(Integer id, AlumnoDto alumnoDto) throws AsignaturaInexistenteException, AlumnoNotFoundException;

    void deleteAlumno(Integer idAlumno) throws AlumnoNotFoundException, AsignaturaInexistenteException;

    AsignaturaDtoSalida modificaEstadoAsignatura(Integer idAlumno, Integer idAsignatura, Nota nota) throws AsignaturaInexistenteException, CorrelatividadesNoAprobadasException, EstadoIncorrectoException, AlumnoNotFoundException;
}
