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
    public MateriaDaoMemoryImpl(){
        inicializarMateria();
    }
    private void inicializarMateria() {
        Materia m = new Materia("laboratorio I", 2, 1, new Profesor("Lucho", "Salotto", "Lic",3216598));
        Materia m1 = new Materia("laboratorio II", 2, 1, new Profesor("Juan", "Perez", "Lic",587461));
        m.setMateriaId(5555);
        m1.setMateriaId(5556);
        saveMateria(m);
        saveMateria(m1);
    }
    @Override
    public void saveMateria(Materia materia) {
        repositorioMateria.put(materia.getMateriaId(), materia);
    }
    @Override
    public Materia findMateria(Integer materiaId) throws MateriaNotFoundException {

        for(Materia materia:repositorioMateria.values()){
            if (materiaId.equals(materia.getMateriaId())){
                return materia;
            }
        }
        throw new MateriaNotFoundException("Materia No Encontrada");
    }
    @Override
    public List<Materia> getAllMaterias() {
        List<Materia> listaMaterias;
        listaMaterias = new ArrayList<>(repositorioMateria.values());

        return listaMaterias;
    }
}
