package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;

import java.util.HashMap;
import java.util.Map;
@Component
public class ProfesorDaoMemoryImpl implements ProfesorDao {
    private final Map<Integer, Profesor> repositorioProfesor= new HashMap<>();
    private static int nextId = 1;
    public ProfesorDaoMemoryImpl(){
        inicializarProfesor();
    }
    private void inicializarProfesor() {
        Profesor p1 = new Profesor("Lucho", "Salotto", "Lic",3216598);
        Profesor p2 = new Profesor("Juan", "Perez", "Lic",5874613);
        p1.setProfesorId(nextId++);
        p2.setProfesorId(nextId++);
        repositorioProfesor.put(p1.getProfesorId(), p1);
        repositorioProfesor.put(p2.getProfesorId(),p2);
    }
    @Override
    public void saveProfesor(Profesor profesor) {
        repositorioProfesor.put(profesor.getDni(),profesor);
    }
    @Override
    public Profesor findProfesor(Integer profesorDni){
        for(Profesor profesor: repositorioProfesor.values()){
            if(profesorDni.equals(profesor.getDni())){
                return profesor;
            }
        }
        return null;
    }
}
