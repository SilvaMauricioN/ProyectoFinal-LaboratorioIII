package utn.frbb.tup.LaboratorioIII.persistence.implement;


import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class MateriaDaoMemoryImpl implements MateriaDao {
    private final Map<Integer, Materia> repositorioMateria = new HashMap<>();
    private static int nextId = 1;
    private MateriaDaoMemoryImpl(){
        inicializarMateria();
    }
    private void inicializarMateria(){
        Materia m = new Materia("laboratorio I", 1, 1, new Profesor("Lucho", "Salotto", "Lic",3216598));
        Materia m1 = new Materia("laboratorio II", 1, 1, new Profesor("Juan", "Perez", "Lic",587461));
        m.setMateriaId(nextId++);
        m1.setMateriaId(nextId++);
        repositorioMateria.put(m.getMateriaId(),m);
        repositorioMateria.put(m1.getMateriaId(), m1);
    }
    @Override
    public void saveMateria(Materia materia) throws MateriaNotFoundException {
        if(!repositorioMateria.containsValue(materia)){
            materia.setMateriaId(nextId++);
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
}
