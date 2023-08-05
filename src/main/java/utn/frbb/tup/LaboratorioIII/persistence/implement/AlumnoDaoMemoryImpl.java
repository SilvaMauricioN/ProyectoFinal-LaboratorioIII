package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class AlumnoDaoMemoryImpl implements AlumnoDao {
    private final Map<Integer, Alumno> repositorioAlumnos = new HashMap<>();
    private final GeneradorId generadorId = GeneradorId.getInstance();
    private static final Logger log = LoggerFactory.getLogger(AlumnoDaoMemoryImpl.class);
    public AlumnoDaoMemoryImpl() throws AlumnoNotFoundException {
        inicializarAlumnos();
    }
    private void inicializarAlumnos() throws AlumnoNotFoundException {
//        Alumno a1 = new Alumno("Mauricio","silva", 3569875);
//        Alumno a2 = new Alumno("Rolando", "fernandez", 8745693);
//        saveAlumno(a1);
//        saveAlumno(a2);
    }
    @Override
    public void saveAlumno(Alumno alumno) throws AlumnoNotFoundException {
        if(!repositorioAlumnos.containsValue(alumno)){
            int id = generadorId.getIdNuevo();
            alumno.setId(id);
            repositorioAlumnos.put(alumno.getId(), alumno);
            log.info("Regristro Alumno: {} , id: {}",alumno.getApellido(), id);

        }else{
            throw new AlumnoNotFoundException("EL ALUMNO YA EXISTE");
        }
    }

    @Override
    public Alumno findAlumno(Integer id) throws AlumnoNotFoundException {
        for(Alumno alumno : repositorioAlumnos.values()){
            if(alumno.getId() == id){
                return alumno;
            }
        }
        throw new AlumnoNotFoundException("ALUMNO NO ENCONTRADO");
    }
    @Override
    public List<Alumno> findAlumno(String apellido) throws AlumnoNotFoundException {
        List<Alumno> alumnosFind = new ArrayList<>();

        for (Alumno alumno: repositorioAlumnos.values()){
            if (apellido.equals(alumno.getApellido())){
                alumnosFind.add(alumno);
            }
        }
        if(!alumnosFind.isEmpty()){
            return alumnosFind;
        }
        throw new AlumnoNotFoundException("Alumno No Encontrado");
    }
    @Override
    public void upDateAlumno(Alumno alumno) {
        repositorioAlumnos.put(alumno.getId(),alumno);
    }

    @Override
    public void deleteAlumno(Integer idAlumno) {
        repositorioAlumnos.remove(idAlumno);

    }

}
