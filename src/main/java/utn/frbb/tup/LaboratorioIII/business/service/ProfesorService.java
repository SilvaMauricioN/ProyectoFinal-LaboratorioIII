package utn.frbb.tup.LaboratorioIII.business.service;





import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

import java.util.List;

public interface ProfesorService {

    ProfesorDtoSalida crearProfesor(ProfesorDto profesorDto) throws ProfesorException, MateriaNotFoundException;
    List<ProfesorDtoSalida> getAllProfesor() throws ProfesorException;
    ProfesorDtoSalida findProfesor(int profesorDni) throws ProfesorException;

    ProfesorDtoSalida actualizarProfesor(Integer id, ProfesorDto profesorDto) throws ProfesorException, MateriaNotFoundException;

    List<MateriaDtoSalida> getMateriasDictadas(Integer idProfesor) throws ProfesorException;

    void deleteProfesor(Integer idProfesor) throws ProfesorException;
}
