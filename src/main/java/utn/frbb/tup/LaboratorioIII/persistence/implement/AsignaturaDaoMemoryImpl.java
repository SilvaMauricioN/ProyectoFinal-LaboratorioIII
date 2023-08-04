package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AsignaturaDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class AsignaturaDaoMemoryImpl implements AsignaturaDao {
    private final Map<Integer, List<Asignatura>> repositorioAsignatura= new HashMap<>();
    @Override
    public synchronized void saveAsignatura(Alumno alumno) {
        repositorioAsignatura.put(alumno.getId(),alumno.getListaAsignaturas());
    }
    @Override
    public synchronized Asignatura findAsignatura(Integer AlumnoId, Integer AsignaturaId) throws AsignaturaInexistenteException {
        if(repositorioAsignatura.containsKey(AlumnoId)){
            List<Asignatura> asignaturas = repositorioAsignatura.get(AlumnoId);

            for(Asignatura a: asignaturas){
                if(a.getMateria().getMateriaId() == AsignaturaId){
                    return a;
                }
            }
        }
        throw new AsignaturaInexistenteException("NO EXISTE ALUMNO NI ASIGNATURA");
    }
    @Override
    public synchronized void upDateAsignatura(Integer Alumnoid, Asignatura asignaturaActualizada) {
        if(repositorioAsignatura.containsKey(Alumnoid)){
            List<Asignatura> cursadas = repositorioAsignatura.get(Alumnoid);

            for(int i = 0 ; i < cursadas.size(); i++ ){
                Asignatura asignatura = cursadas.get(i);
                if(asignatura.getMateria().getMateriaId() == asignaturaActualizada.getMateria().getMateriaId()){
                    cursadas.set(i,asignaturaActualizada);
                    break;
                }
            }
        }
    }
    @Override
    public synchronized void deleteAsignaturas(Integer idAlumno) throws AsignaturaInexistenteException {
        if(repositorioAsignatura.containsKey(idAlumno)){
            repositorioAsignatura.remove(idAlumno);
        }else{
            throw new AsignaturaInexistenteException("ASIGNATURAS NO ENCONTRADAS");
        }

    }
}