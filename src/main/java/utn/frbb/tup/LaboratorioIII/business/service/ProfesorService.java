package utn.frbb.tup.LaboratorioIII.business.service;




import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

import java.util.List;

public interface ProfesorService {

    Profesor crearProfesor(ProfesorDto profesorDto) throws ProfesorException, MateriaNotFoundException;
    List<Profesor> getAllProfesor();
    Profesor findProfesor(int profesorDni) throws ProfesorException;

    Profesor actualizarProfesor(Integer id, ProfesorDto profesorDto) throws ProfesorException, MateriaNotFoundException;

    List<Materia> getMateriasDictadas(Integer idProfesor) throws ProfesorException;
}
