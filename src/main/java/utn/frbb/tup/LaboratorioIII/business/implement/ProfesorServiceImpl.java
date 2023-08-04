package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.DtoProfesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.*;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    private final ProfesorDao profesorDao;
    private final MateriaService materiaService;
    private final CastingDtos castingDtos;
    @Autowired
    public ProfesorServiceImpl(ProfesorDao profesorDao,MateriaService materiaService){
        this.profesorDao = profesorDao;
        this.materiaService = materiaService;
        this.castingDtos = new CastingDtos();
    }
    @Override
    public ProfesorDtoSalida crearProfesor(DtoProfesor dtoProfesor) throws ProfesorException{
        Profesor profesor = new Profesor();
        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        posiblesErrores = castingProfesorDto(profesor, dtoProfesor);
        profesorDao.saveProfesor(profesor);

        ProfesorDtoSalida profesorDtoSalida = castingDtos.convertirAprofesorDtoSalida(profesor);
        profesorDtoSalida.setStatus(posiblesErrores);
        return profesorDtoSalida;
    }
    @Override
    public ProfesorDtoSalida actualizarProfesor(Integer id, DtoProfesor dtoProfesor) throws ProfesorException{
        Profesor profesor = profesorDao.findProfesor(id);
        eliminarMateriasDictadas(profesor);
        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        posiblesErrores = castingProfesorDto(profesor, dtoProfesor);

        profesorDao.upDateProfesor(profesor);
        ProfesorDtoSalida profesorDtoSalida = castingDtos.convertirAprofesorDtoSalida(profesor);
        profesorDtoSalida.setStatus(posiblesErrores);
        return profesorDtoSalida;
    }
    @Override
    public List<ProfesorDtoSalida> getAllProfesor() throws ProfesorException {
        List<Profesor> guardados = profesorDao.getAllProfesores();
        List<ProfesorDtoSalida> profesoresSalida = new ArrayList<>();
        if(!guardados.isEmpty()){
            for(Profesor p: guardados){
                ProfesorDtoSalida profesorDtoSalida = castingDtos.convertirAprofesorDtoSalida(p);
                profesoresSalida.add(profesorDtoSalida);
            }
        }
        return profesoresSalida;
    }
    @Override
    public ProfesorDtoSalida findProfesor(int profesorDni) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(profesorDni);
        return castingDtos.convertirAprofesorDtoSalida(profesor);
    }
    @Override
    public List<MateriaDtoSalida> getMateriasDictadas(Integer idProfesor) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(idProfesor);
        List<Materia> MateriasDictadas = profesor.getMateriasDictadas();
        List<MateriaDtoSalida> materiasDictadasDTO = new ArrayList<>();
        if(MateriasDictadas != null){
            for(Materia m: MateriasDictadas){
                materiasDictadasDTO.add(castingDtos.convertirAmateriaDtoSalida(m));
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
    private List<Map<String,String>> castingProfesorDto(Profesor profesor, DtoProfesor dtoProfesor) throws ProfesorException {
        profesor.setNombre(dtoProfesor.getNombre());
        profesor.setApellido(dtoProfesor.getApellido());
        profesor.setTitulo(dtoProfesor.getTitulo());
        profesor.setDni(dtoProfesor.getDni());

        List<Integer> Idmaterias = dtoProfesor.getMateriasDictadasID();
        List<Map<String, String>> status = new ArrayList<>();
        List<Materia> listaMateriasDictadas = materiaService.getListaMateriaPorId(Idmaterias,status);

        //Agrego las materias a dictar por el profesor,
        if(listaMateriasDictadas != null){
            for(Materia m: listaMateriasDictadas){
                //profesor nuevo, lista materias vacias
                if(m.getProfesor() == null){
                    profesor.setMateria(m);
                }else{
                    throw new ProfesorException("LA MATERIA " + m.getNombre() + " YA TIENE PROFESOR ASIGNADO");
                }
            }
        }
        return status;
    }
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
