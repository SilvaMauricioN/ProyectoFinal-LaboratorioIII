package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDto;
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
    public Profesor crearProfesor(ProfesorDto profesorDto) throws ProfesorException{
        Profesor profesor = new Profesor();
        castingDtoProfesor(profesor,profesorDto);
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
    public Profesor actualizarProfesor(Integer id, ProfesorDto profesorDto) throws ProfesorException{
        Profesor profesor = profesorDao.findProfesor(id);
        eliminarMaterias(profesor);
        castingDtoProfesor(profesor,profesorDto);

        profesorDao.upDateProfesor(profesor);
        return profesor;
    }

    @Override
    public List<Materia> getMateriasDictadas(Integer idProfesor) throws ProfesorException {
        Profesor profesor = profesorDao.findProfesor(idProfesor);

        List<Materia> MateriasDictadas = profesor.getMateriasDictadas();
        Collections.sort(MateriasDictadas);

        return MateriasDictadas;
    }
    @Override
    public void deleteProfesor(Integer idProfesor) throws ProfesorException {
        profesorDao.deleteProfesor(idProfesor);


    }
    private void castingDtoProfesor(Profesor profesor, ProfesorDto profesorDto) throws ProfesorException {
        profesor.setNombre(profesorDto.getNombre());
        profesor.setApellido(profesorDto.getApellido());
        profesor.setTitulo(profesorDto.getTitulo());
        profesor.setDni(profesorDto.getDni());

        List<Integer> Idmaterias = profesorDto.getMateriasDictadasID();
        List<Map<String, String>> status = new ArrayList<>();
        List<Materia> listaMateriasDictadas = materiaService.getListaMateriaPorId(Idmaterias,status);

        //Agrego las materias a dictar por el profesor, y a las materias se le asigna el profesor
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
    }
    private void eliminarMaterias(Profesor profesor){
        List<Materia> listaMateriasAntiguas = profesor.getMateriasDictadas();
        if(listaMateriasAntiguas != null){
            for(Materia m:listaMateriasAntiguas){
                m.setProfesor(null);
            }
            listaMateriasAntiguas.clear();
        }
    }

}
