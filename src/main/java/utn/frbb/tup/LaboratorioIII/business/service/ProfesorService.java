package utn.frbb.tup.LaboratorioIII.business.service;




import utn.frbb.tup.LaboratorioIII.model.Profesor;

import java.util.List;

public interface ProfesorService {

    Profesor crearProfesor(Profesor profesor);
    List<Profesor> getAllProfesor();
    Profesor findProfesor(int profesorDni);
}
