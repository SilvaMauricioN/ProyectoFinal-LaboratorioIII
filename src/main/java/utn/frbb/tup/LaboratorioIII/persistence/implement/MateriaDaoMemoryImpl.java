package utn.frbb.tup.LaboratorioIII.persistence.implement;


import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
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
    private void inicializarMateria(){
//        Profesor p1 = null;
//        Profesor p2 = null;
//
//        try {
//            p1 = profesorDao.findProfesor(1);
//            p2 = profesorDao.findProfesor(2);
//        } catch (ProfesorException e) {
//            throw new RuntimeException(e);
//        }

        Materia m = new Materia("laboratorio I", 1, 1,null);
        Materia m1 = new Materia("laboratorio II", 1, 1, null);
        m.setMateriaId(generadorId.getIdNuevo());
        m1.setMateriaId(generadorId.getIdNuevo());
        repositorioMateria.put(m.getMateriaId(),m);
        repositorioMateria.put(m1.getMateriaId(), m1);
    }
    @Override
    public void saveMateria(Materia materia) throws MateriaNotFoundException {
        if(!repositorioMateria.containsValue(materia)){
            materia.setMateriaId(generadorId.getIdNuevo());
            repositorioMateria.put(materia.getMateriaId(), materia);
        }else{
            throw new MateriaNotFoundException("LA MATERIA YA EXISTE");
        }
    }
    @Override
    public Materia findMateria(Integer materiaId) throws MateriaNotFoundException {

        for(Materia materia:repositorioMateria.values()){
            if (materiaId.equals(materia.getMateriaId())){
                return materia;
            }
        }
        throw new MateriaNotFoundException("MATERIA NO ENCOTRADA");
    }
    @Override
    public List<Materia> getAllMaterias() {
        List<Materia> listaMaterias;
        listaMaterias = new ArrayList<>(repositorioMateria.values());

        return listaMaterias;
    }

    @Override
    public void upDateMateria(Materia materia) {
        repositorioMateria.put(materia.getMateriaId(),materia);
    }
}
