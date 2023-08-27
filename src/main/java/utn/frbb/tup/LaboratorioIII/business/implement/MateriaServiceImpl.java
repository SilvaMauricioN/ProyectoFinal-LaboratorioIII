package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.*;

@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaDao materiaDao;
    private final ProfesorDao profesorDao;
    private final CastingDtos castingDtos;
    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorDao profesorDao, CastingDtos castingDtos){
        this.materiaDao = materiaDao;
        this.profesorDao = profesorDao;
        this.castingDtos = castingDtos;
    }
    @Override
    public MateriaDtoSalida crearMateria(MateriaDto dtoMateria) throws MateriaNotFoundException, ProfesorException, CorrelatividadException {
        Materia materia = new Materia();
        List<Map<String, String>> errores = castingDtos.aMateriaDto(materia, dtoMateria);
        Profesor profesor = profesorDao.findProfesor(dtoMateria.getProfesorId());
        materia.setProfesor(profesor);
        materiaDao.saveMateria(materia);

        MateriaDtoSalida materiaDtoSalida =  castingDtos.aMateriaDtoSalida(materia);
        ProfesorDtoSalida profesorDtoSalida = castingDtos.toProfesorDtoSalida(profesor);

        materiaDtoSalida.setProfesor(profesorDtoSalida);
        materiaDtoSalida.setStatus(errores);

        return materiaDtoSalida;
    }
    @Override
    public List<MateriaDtoSalida> getAllMaterias() {
        List<Materia> guardadas = materiaDao.getAllMaterias();
        List<MateriaDtoSalida> materiasDtoGuardadas = new ArrayList<>();

        if(!guardadas.isEmpty()){
            for(Materia m : guardadas){
                MateriaDtoSalida materiaDtoSalida = castingDtos.aMateriaDtoSalida(m);
                if(m.getProfesor() != null){
                    ProfesorDtoSalida profesorDtoSalida = castingDtos.toProfesorDtoSalida(m.getProfesor());
                    materiaDtoSalida.setProfesor(profesorDtoSalida);
                }
                materiasDtoGuardadas.add(materiaDtoSalida);
            }
        }
        return materiasDtoGuardadas;
    }

    @Override
    public MateriaDtoSalida findMateria(int materiaId) throws MateriaNotFoundException {

        Materia materia = materiaDao.findMateria(materiaId);
        MateriaDtoSalida materiaDtoSalida = castingDtos.aMateriaDtoSalida(materia);
        if(materia.getProfesor() != null){
            ProfesorDtoSalida profesorDtoSalida = castingDtos.toProfesorDtoSalida(materia.getProfesor());
            materiaDtoSalida.setProfesor(profesorDtoSalida);
        }
        return materiaDtoSalida;
    }

    //al actualizar materia tambien se actualiza la materia en el profesor que la dicta
    @Override
    public MateriaDtoSalida actualizarMateria(Integer id, MateriaDto dtoMateria) throws MateriaNotFoundException, ProfesorException, CorrelatividadException {

        Materia materia = materiaDao.findMateria(id);
        Profesor profesor = profesorDao.findProfesor(dtoMateria.getProfesorId());

        if(!profesor.equals(materia.getProfesor())){
            if(materia.getProfesor() != null){
                Profesor p = materia.getProfesor();
                List <Materia> listaMateria =  p.getMateriasDictadas();
                if(listaMateria != null){
                    listaMateria.remove(materia);
                    profesorDao.upDateProfesor(p);
                }
            }
        }
        List<Map<String, String>> errores = castingDtos.aMateriaDto(materia, dtoMateria);
        materia.setProfesor(profesor);
        materiaDao.upDateMateria(materia);

        MateriaDtoSalida materiaDtoSalida = castingDtos.aMateriaDtoSalida(materia);
        ProfesorDtoSalida profesorDtoSalida = castingDtos.toProfesorDtoSalida(profesor);

        materiaDtoSalida.setStatus(errores);
        materiaDtoSalida.setProfesor(profesorDtoSalida);

        return materiaDtoSalida;
    }

    //si se elimina una materia, tambien se elimina la materia del registro de materia dictadas del profesor
    @Override
    public void deleteMateria(Integer idMateria) throws MateriaNotFoundException {
        Materia materiaPorEliminar = materiaDao.findMateria(idMateria);
        Profesor profesor = materiaPorEliminar.getProfesor();

        if(profesor != null){
            List<Materia> dictadas  = profesor.getMateriasDictadas();
            Iterator <Materia> iterador = dictadas.iterator();

            while(iterador.hasNext()){
                Materia materia = iterador.next();
                if(materia.equals(materiaPorEliminar)){
                    iterador.remove();
                }
            }
        }
        materiaDao.deleteMateria(idMateria);
    }

}
