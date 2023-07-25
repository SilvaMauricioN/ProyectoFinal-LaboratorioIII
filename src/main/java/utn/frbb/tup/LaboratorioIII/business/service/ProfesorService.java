package utn.frbb.tup.LaboratorioIII.business.service;




import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;

import java.util.List;

public interface ProfesorService {

    Profesor crearProfesor(ProfesorDto profesorDto);
    List<Profesor> getAllProfesor();
    Profesor findProfesor(int profesorDni);
}
