package utn.frbb.tup.LaboratorioIII.persistence.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(AsignaturaDaoMemoryImpl.class);
    @Override
    public synchronized void saveAsignatura(Alumno alumno) {
        repositorioAsignatura.put(alumno.getId(),alumno.getListaAsignaturas());
        log.info("Regristro Asignatura Alumno: {} , id: {}",alumno.getApellido(), alumno.getId());
    }
    @Override
    public synchronized Asignatura findAsignatura(Integer AlumnoId, Integer AsignaturaId) throws AsignaturaInexistenteException {

        if(repositorioAsignatura.containsKey(AlumnoId)){
            List<Asignatura> asignaturas = repositorioAsignatura.get(AlumnoId);

            for(Asignatura a: asignaturas){
                if(AsignaturaId.equals(a.getMateria().getMateriaId())){
                    return a;
                }else{
                    return null;
                }
            }
        }
        throw new AsignaturaInexistenteException("NO EXISTE ALUMNO");
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
