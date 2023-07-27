package utn.frbb.tup.LaboratorioIII.business.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorDao profesorDao;
    private final MateriaService materiaService;
    private final MateriaDao materiaDao;
    @Autowired
    public ProfesorServiceImpl(ProfesorDao profesorDao, MateriaDao materiaDao,MateriaService materiaService){
        this.profesorDao = profesorDao;
        this.materiaDao = materiaDao;
        this.materiaService = materiaService;
    }
    @Override
    public Profesor crearProfesor(ProfesorDto profesorDto) throws ProfesorException {
        Profesor profesor = new Profesor(profesorDto.getNombre(),
                profesorDto.getApellido(),
                profesorDto.getTitulo(),
                profesorDto.getDni());

        List<Integer> idMaterias = profesorDto.getMateriasDictadasID();
        List <Map<String,String>> listaErrores = new ArrayList<>();
        List<Materia> materiaDictadas = materiaService.getListaMateriaPorId(idMaterias,listaErrores);

        profesor.setMateriasDictadas(materiaDictadas);
        profesorDao.saveProfesor(profesor);
        return profesor;
    }

    @Override
    public List<Profesor> getAllProfesor() {
        return profesorDao.getAllProfesores();
    }

    public Profesor findProfesor(int profesorDni) throws ProfesorException {
        return profesorDao.findProfesor(profesorDni);
    }

    @Override
    public Profesor actualizarProfesor(Integer id, ProfesorDto profesorDto) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(id);

        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellido(profesorDto.getApellido());
        profesor.setTitulo(profesorDto.getTitulo());
        profesor.setDni(profesorDto.getDni());

        List <Map<String,String>> listaErrores = new ArrayList<>();
        List<Materia> materiasDictadas = materiaService.getListaMateriaPorId(profesorDto.getMateriasDictadasID(),listaErrores);

        profesor.setMateriasDictadas(materiasDictadas);

        profesorDao.upDateProfesor(profesor);
        return profesor;
    }

}
