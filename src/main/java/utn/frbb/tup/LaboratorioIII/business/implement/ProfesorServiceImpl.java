package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.*;

@Service
public class ProfesorServiceImpl implements ProfesorService {
    private final ProfesorDao profesorDao;
    private final MateriaService materiaService;
    @Autowired
    public ProfesorServiceImpl(ProfesorDao profesorDao,MateriaService materiaService){
        this.profesorDao = profesorDao;
        this.materiaService = materiaService;
    }
    @Override
    public ProfesorDtoSalida crearProfesor(ProfesorDto profesorDto) throws ProfesorException{
        Profesor profesor = new Profesor();
        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        posiblesErrores = castingProfesorDto(profesor,profesorDto);
        profesorDao.saveProfesor(profesor);

        ProfesorDtoSalida profesorDtoSalida = castingProfesorDtoSalida(profesor);
        profesorDtoSalida.setStatus(posiblesErrores);
        return profesorDtoSalida;
    }
    @Override
    public ProfesorDtoSalida actualizarProfesor(Integer id, ProfesorDto profesorDto) throws ProfesorException{
        Profesor profesor = profesorDao.findProfesor(id);
        eliminarMateriasDictadas(profesor);
        List<Map<String,String>> posiblesErrores = new ArrayList<>();
        posiblesErrores = castingProfesorDto(profesor,profesorDto);

        profesorDao.upDateProfesor(profesor);
        ProfesorDtoSalida profesorDtoSalida = castingProfesorDtoSalida(profesor);
        profesorDtoSalida.setStatus(posiblesErrores);
        return profesorDtoSalida;
    }
    @Override
    public List<ProfesorDtoSalida> getAllProfesor() throws ProfesorException {
        List<Profesor> guardados = profesorDao.getAllProfesores();
        List<ProfesorDtoSalida> profesoresSalida = new ArrayList<>();
        if(!guardados.isEmpty()){
            for(Profesor p: guardados){
                ProfesorDtoSalida profesorDtoSalida = castingProfesorDtoSalida(p);
                profesoresSalida.add(profesorDtoSalida);
            }
        }
        return profesoresSalida;
    }
    @Override
    public ProfesorDtoSalida findProfesor(int profesorDni) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(profesorDni);
        return castingProfesorDtoSalida(profesor);
    }
    @Override
    public List<MateriaDtoSalida> getMateriasDictadas(Integer idProfesor) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(idProfesor);
        List<Materia> MateriasDictadas = profesor.getMateriasDictadas();
        List<MateriaDtoSalida> materiasDictadasDTO = new ArrayList<>();
        if(MateriasDictadas != null){
            for(Materia m: MateriasDictadas){
                materiasDictadasDTO.add(materiaService.castingMateriaDtoSalida(m));
            }
            Collections.sort(materiasDictadasDTO);
        }
        return materiasDictadasDTO;
    }
    @Override
    public void deleteProfesor(Integer idProfesor) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(idProfesor);
        List<Materia> materiasDictas = profesor.getMateriasDictadas();

        for(Materia m : materiasDictas){
            m.setProfesor(null);
        }
        profesorDao.deleteProfesor(idProfesor);
    }
    private ProfesorDtoSalida castingProfesorDtoSalida(Profesor profesor) throws ProfesorException {
        ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida(profesor.getNombre(),
                profesor.getApellido(), profesor.getTitulo(), profesor.getDni());

        List<MateriaDtoSalida> materiaDtoSalidas = new ArrayList<>();
        if(profesor.getMateriasDictadas() != null){
            materiaDtoSalidas = getMateriasDictadas(profesor.getProfesorId());
        }
        profesorDtoSalida.setMateriasDictadas(materiaDtoSalidas);
        return profesorDtoSalida;
    }
//    private MateriaDtoSalida castingMateriaDtoSalida(Materia m){
//        MateriaDtoSalida materiaSalida = new MateriaDtoSalida();
//        List<MateriaDtoSalida> materiasCorrelativasDto = new ArrayList<>();
//
//        materiaSalida.setNombre(m.getNombre());
//        materiaSalida.setYear(m.getAnio());
//        materiaSalida.setCuatrimestre(m.getCuatrimestre());
//
//        if(m.getListaCorrelatividades() == null){
//            materiaSalida.setCorrelativas(materiasCorrelativasDto);
//        }else{
//            List<Materia> materiasCorrelativas = m.getListaCorrelatividades();
//            for(Materia materia: materiasCorrelativas){
//                MateriaDtoSalida materiaDtoSalida = castingMateriaDtoSalida(materia);
//                materiasCorrelativasDto.add(materiaDtoSalida);
//            }
//        }
//        materiaSalida.setCorrelativas(materiasCorrelativasDto);
//        return materiaSalida;
//    }
    private List<Map<String,String>> castingProfesorDto(Profesor profesor, ProfesorDto profesorDto) throws ProfesorException {
        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellido(profesorDto.getApellido());
        profesor.setTitulo(profesorDto.getTitulo());
        profesor.setDni(profesorDto.getDni());

        List<Integer> Idmaterias = profesorDto.getMateriasDictadasID();
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
