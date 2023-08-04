package utn.frbb.tup.LaboratorioIII.persistence.implement;


import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class MateriaDaoMemoryImpl implements MateriaDao {
    private final Map<Integer, Materia> repositorioMateria = new HashMap<>();
    private final ProfesorDao profesorDao;
    private final GeneradorId generadorId = GeneradorId.getInstance();

    private MateriaDaoMemoryImpl(ProfesorDao profesorDao){
        this.profesorDao = profesorDao;
        inicializarMateria();
    }
    private synchronized void inicializarMateria(){
//        Profesor p1;
//        Profesor p2;
//
//        try {
//            p1 = profesorDao.findProfesor(1);
//            p2 = profesorDao.findProfesor(2);
//        } catch (ProfesorException e) {
//            throw new RuntimeException(e);
//        }

        Materia m = new Materia("Laboratorio I", 1, 1);
        Materia m1 = new Materia("Laboratorio II", 1, 1);
//
//        m.setProfesor(p1);
//        m1.setProfesor(p2);

        m.setMateriaId(generadorId.getIdNuevo());
        m1.setMateriaId(generadorId.getIdNuevo());
        repositorioMateria.put(m.getMateriaId(),m);
        repositorioMateria.put(m1.getMateriaId(), m1);
    }
    @Override
    public synchronized void saveMateria(Materia materia) throws MateriaNotFoundException {
        if(!repositorioMateria.containsValue(materia)){
            materia.setMateriaId(generadorId.getIdNuevo());
            repositorioMateria.put(materia.getMateriaId(), materia);
        }else{
            throw new MateriaNotFoundException("LA MATERIA YA EXISTE");
        }
    }
    @Override
    public synchronized Materia findMateria(Integer materiaId) throws MateriaNotFoundException {

        for(Materia materia:repositorioMateria.values()){
            if(materia.getMateriaId() == materiaId){
                return materia;
            }
        }
        throw new MateriaNotFoundException("MATERIA NO ENCOTRADA");
    }
    @Override
    public synchronized List<Materia> getAllMaterias() {
        List<Materia> listaMaterias;
        listaMaterias = new ArrayList<>(repositorioMateria.values());

        return listaMaterias;
    }

    @Override
    public synchronized void upDateMateria(Materia materia) {
        repositorioMateria.put(materia.getMateriaId(),materia);
    }

    @Override
    public void deleteMateria(Integer idMateria) {
        repositorioMateria.remove(idMateria);
    }
}
