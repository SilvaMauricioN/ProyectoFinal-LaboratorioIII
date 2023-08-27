package utn.frbb.tup.LaboratorioIII.persistence.implement;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.GeneradorId;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

@Component
public class MateriaDaoMemoryImpl implements MateriaDao {
    private final Map<Integer, Materia> repositorioMateria = new HashMap<>();
    private final ProfesorDao profesorDao;
    private final GeneradorId generadorId = GeneradorId.getInstance();
    private static final Logger log = LoggerFactory.getLogger(MateriaDaoMemoryImpl.class);
    @Autowired
    public MateriaDaoMemoryImpl(ProfesorDao profesorDao) throws MateriaNotFoundException {
        this.profesorDao = profesorDao;
        inicializarMateria();
    }
    private synchronized void inicializarMateria() throws MateriaNotFoundException {
        String[] nombresMateriasPrimer = {"Programacion I", "Laboratorio I", "Sistema de Datos", "Ingles I"};
        List<Materia> materias = new ArrayList<>();

        for (int i = 0; i < nombresMateriasPrimer.length; i++) {
            materias.add(new Materia(nombresMateriasPrimer[i], 1, 1));
        }

        for(int i =0; i<materias.size(); i++){
            Materia a = materias.get(i);
            try {
                a.setProfesor(profesorDao.findProfesor(i + 1));
            } catch (ProfesorException e){
                throw new RuntimeException(e);
            }
        }

        for(int i = 0; i< materias.size() ; i++){
            saveMateria(materias.get(i));
        }
    }
    @Override
    public synchronized void saveMateria(Materia materia) throws MateriaNotFoundException {
        if(!repositorioMateria.containsValue(materia)){
            int id = generadorId.getIdNuevo();
            materia.setMateriaId(id);

            repositorioMateria.put(materia.getMateriaId(), materia);
            log.info("Regristro Materia: {} , id: {}",materia.getNombre(), id);
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
        throw new MateriaNotFoundException("MATERIA NO ENCONTRADA");
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
    public synchronized void deleteMateria(Integer idMateria) throws MateriaNotFoundException {
        if (!repositorioMateria.containsKey(idMateria)) {
            throw new MateriaNotFoundException("MATERIA ID: " + idMateria + " NO ENCONTRADA");
        }
        repositorioMateria.remove(idMateria);
    }
}
