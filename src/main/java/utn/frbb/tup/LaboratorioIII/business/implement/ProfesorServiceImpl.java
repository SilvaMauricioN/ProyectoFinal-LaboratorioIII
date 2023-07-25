package utn.frbb.tup.LaboratorioIII.business.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorDao profesorDao;
    @Autowired
    public ProfesorServiceImpl(ProfesorDao profesorDao){
        this.profesorDao = profesorDao;
    }
    @Override
    public Profesor crearProfesor(ProfesorDto profesorDto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellido(profesorDto.getApellido());
        profesor.setDni(profesorDto.getDni());
        profesor.setTitulo(profesorDto.getTitulo());
        //profesor.setMateriasDictadas();

        return profesor;
    }

    @Override
    public List<Profesor> getAllProfesor() {
    return null;
    }

    public Profesor findProfesor(int profesorDni){
        return profesorDao.findProfesor(profesorDni);
    }

}
