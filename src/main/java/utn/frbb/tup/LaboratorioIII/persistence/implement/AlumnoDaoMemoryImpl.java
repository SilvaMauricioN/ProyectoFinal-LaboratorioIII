package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class AlumnoDaoMemoryImpl implements AlumnoDao {
    private final Map<Long, Alumno> repositorioAlumnos = new HashMap<>();
    public AlumnoDaoMemoryImpl(){
        inicializarAlumnos();
    }
    private void inicializarAlumnos() {
        Alumno a1 = new Alumno("Mauricio","silva", 3569875);
        Alumno a2 = new Alumno("Rolando", "fernandez", 8745693);
        saveAlumno(a1);
        saveAlumno(a2);
    }
    @Override
    public void saveAlumno(Alumno alumno) {
        repositorioAlumnos.put(alumno.getDni(), alumno);
    }
    @Override
    public Alumno findAlumno(Long dni) {
        return null;
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
    public Alumno upDateAlumno(Long dni) {
        return null;
    }
    public Alumno loadAlumno(Long dni) {
        return null;
    }

}
