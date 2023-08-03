package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class ProfesorDaoMemoryImpl implements ProfesorDao {
    private final Map<Integer, Profesor> repositorioProfesor= new HashMap<>();
    private final GeneradorId generadorId = GeneradorId.getInstance();
    public ProfesorDaoMemoryImpl(){
        inicializarProfesor();
    }
    private synchronized void inicializarProfesor() {
        Profesor p1 = new Profesor("Luciano", "Salotto", "Lic",3216598);
        Profesor p2 = new Profesor("Juan", "Perez", "Lic",5874613);
        p1.setProfesorId(generadorId.getIdNuevo());
        p2.setProfesorId(generadorId.getIdNuevo());
        repositorioProfesor.put(p1.getProfesorId(), p1);
        repositorioProfesor.put(p2.getProfesorId(),p2);
    }
    @Override
    public synchronized void saveProfesor(Profesor profesor) throws ProfesorException {
        if(!repositorioProfesor.containsValue(profesor)){
            profesor.setProfesorId(generadorId.getIdNuevo());
            repositorioProfesor.put(profesor.getProfesorId(),profesor);
        }else{
            throw new ProfesorException("PROFESOR YA GUARDADO");
        }
    }
    @Override
    public synchronized Profesor findProfesor(Integer profesorId) throws ProfesorException {
        for(Profesor profesor: repositorioProfesor.values()){
            if(profesor.getProfesorId() == profesorId){
                return profesor;
            }
        }
        throw new ProfesorException("PROFESOR NO ENCONTRADO");
    }
    @Override
    public synchronized List<Profesor> getAllProfesores() {
        return new ArrayList<>(repositorioProfesor.values());
    }
    @Override
    public synchronized void upDateProfesor(Profesor profesor) {
        repositorioProfesor.put(profesor.getProfesorId(),profesor);
    }
    @Override
    public void deleteProfesor(Integer idProfesor) throws ProfesorException {
        if(repositorioProfesor.containsKey(idProfesor)){
            repositorioProfesor.remove(idProfesor);
        }else{
            throw new ProfesorException("PROFESOR NO ENCONTRADO");
        }
    }
}
