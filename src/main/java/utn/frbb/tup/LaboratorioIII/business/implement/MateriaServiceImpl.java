package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;

import java.util.List;
import java.util.Random;

@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaDao materiaDao;
    private final ProfesorService profesorService;
    //Inyeccion de dependencia por construcctor
    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorService profesorServicio){
        this.materiaDao = materiaDao;
        this.profesorService = profesorServicio;
    }
    @Override
    public Materia crearMateria(MateriaDto materiaDto) {
        Materia materia = new Materia();

        Random random = new Random();
        materia.setMateriaId(random.nextInt(99) + 1);

        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getAnio());
        materia.setListaCorrelatividades(materiaDto.getListaCorrelatividades());
        System.out.println(materiaDto.getProfesorDni());

        Profesor profesor = profesorService.findProfesor(materiaDto.getProfesorDni());
        materia.setProfesor(profesor);

        materiaDao.saveMateria(materia);
        return materia;

    }

    @Override
    public List<Materia> getAllMaterias() {
        return (materiaDao.getAllMaterias());
    }

    @Override
    public Materia findMateria(int materiaId) throws MateriaNotFoundException {
        return materiaDao.findMateria(materiaId);
    }
}
