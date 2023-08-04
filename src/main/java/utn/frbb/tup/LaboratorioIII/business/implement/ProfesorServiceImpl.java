package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.*;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    private final ProfesorDao profesorDao;
    private final CastingDtos castingDtos;
    @Autowired
    public ProfesorServiceImpl(ProfesorDao profesorDao,MateriaDao materiaDao){
        this.profesorDao = profesorDao;
        this.castingDtos = new CastingDtos(materiaDao);
    }
    @Override
    public ProfesorDtoSalida crearProfesor(ProfesorDto dtoProfesor) throws ProfesorException{
        Profesor profesor = new Profesor();
        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        posiblesErrores = castingDtos.aProfesorDto(profesor, dtoProfesor);
        profesorDao.saveProfesor(profesor);

        ProfesorDtoSalida profesorDtoSalida = castingDtos.aProfesorDtoSalida(profesor);
        profesorDtoSalida.setStatus(posiblesErrores);
        return profesorDtoSalida;
    }
    @Override
    public ProfesorDtoSalida actualizarProfesor(Integer id, ProfesorDto dtoProfesor) throws ProfesorException{
        Profesor profesor = profesorDao.findProfesor(id);
        eliminarMateriasDictadas(profesor);
        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        posiblesErrores = castingDtos.aProfesorDto(profesor, dtoProfesor);

        profesorDao.upDateProfesor(profesor);
        ProfesorDtoSalida profesorDtoSalida = castingDtos.aProfesorDtoSalida(profesor);
        profesorDtoSalida.setStatus(posiblesErrores);
        return profesorDtoSalida;
    }
    @Override
    public List<ProfesorDtoSalida> getAllProfesor() throws ProfesorException {
        List<Profesor> guardados = profesorDao.getAllProfesores();
        List<ProfesorDtoSalida> profesoresSalida = new ArrayList<>();
        if(!guardados.isEmpty()){
            for(Profesor p: guardados){
                ProfesorDtoSalida profesorDtoSalida = castingDtos.aProfesorDtoSalida(p);
                profesoresSalida.add(profesorDtoSalida);
            }
        }
        return profesoresSalida;
    }
    @Override
    public ProfesorDtoSalida findProfesor(int profesorDni) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(profesorDni);
        return castingDtos.aProfesorDtoSalida(profesor);
    }
    @Override
    public List<MateriaDtoSalida> getMateriasDictadas(Integer idProfesor) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(idProfesor);
        List<Materia> MateriasDictadas = profesor.getMateriasDictadas();
        List<MateriaDtoSalida> materiasDictadasDTO = new ArrayList<>();
        if(MateriasDictadas != null){
            for(Materia m: MateriasDictadas){
                materiasDictadasDTO.add(castingDtos.aMateriaDtoSalida(m));
            }
            Collections.sort(materiasDictadasDTO);
        }
        return materiasDictadasDTO;
    }
    @Override
    public void deleteProfesor(Integer idProfesor) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(idProfesor);
        List<Materia> materiasDictas = profesor.getMateriasDictadas();
        if(materiasDictas != null){
            for(Materia m : materiasDictas){
                m.setProfesor(null);
            }
        }
        profesorDao.deleteProfesor(idProfesor);
    }
    //Al eliminar un profesor, lo desasigno de la materia dictada
    private void eliminarMateriasDictadas(Profesor profesor){
        List<Materia> listaMateriasAntiguas = profesor.getMateriasDictadas();
        if(listaMateriasAntiguas != null){
            for(Materia m:listaMateriasAntiguas){
                m.setProfesor(null);
            }
            listaMateriasAntiguas.clear();
        }
    }
}
