package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaResponse;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.*;

@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaDao materiaDao;
    private final ProfesorDao profesorDao;
    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorDao profesorDao){
        this.materiaDao = materiaDao;
        this.profesorDao = profesorDao;
    }
    @Override
    public MateriaResponse crearMateria(MateriaDto materiaDto) throws MateriaNotFoundException, ProfesorException {
        Materia materia = new Materia();
        List<Map<String, String>> errores = castingDtoMateria(materia, materiaDto);

        Profesor profesor = profesorDao.findProfesor(materiaDto.getProfesorId());
        materia.setProfesor(profesor);

        materiaDao.saveMateria(materia);
        return new MateriaResponse(materia,errores);
    }
    @Override
    public List<Materia> getAllMaterias() {
        return (materiaDao.getAllMaterias());
    }

    @Override
    public Materia findMateria(int materiaId) throws MateriaNotFoundException {
        return materiaDao.findMateria(materiaId);
    }

    @Override
    public Materia actualizarMateria(Integer id,MateriaDto materiaDto) throws MateriaNotFoundException, ProfesorException {
        Materia materia = materiaDao.findMateria(id);
        Profesor profesor = profesorDao.findProfesor(materiaDto.getProfesorId());

        if(!profesor.equals(materia.getProfesor())){
            if(materia.getProfesor() != null){
                Profesor p = materia.getProfesor();
                List <Materia> listaMateria =  p.getMateriasDictadas();
                if(listaMateria != null){
                    listaMateria.remove(materia);
                    profesorDao.upDateProfesor(p);
                }
            }
        }

        List<Map<String, String>> errores = castingDtoMateria(materia, materiaDto);
        materia.setProfesor(profesor);
        materiaDao.upDateMateria(materia);
        return materia;
    }
    private List<Map<String, String>> castingDtoMateria(Materia materia, MateriaDto materiaDto){

        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getYear());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());

        List<Integer> correlatividadesDtoId = materiaDto.getListaCorrelatividades();
        List <Map<String,String>> posiblesErrores = new ArrayList<>();
        List<Materia> listaCorrelativas = getListaMateriaPorId(correlatividadesDtoId,posiblesErrores);
        materia.setListaCorrelatividades(listaCorrelativas);

        return posiblesErrores;
    }
    public List<Materia> getListaMateriaPorId(List<Integer> listaId,List<Map<String,String>> Errores){
        List<Materia> listaMaterias = new ArrayList<>();
        if (listaId != null){
            for(Integer i:listaId){
                try {
                    Materia materiaDictada = materiaDao.findMateria(i);
                    listaMaterias.add(materiaDictada);
                } catch (MateriaNotFoundException e) {
                    Map<String,String> error = new HashMap<>(){{
                        put("Materia Id",String.valueOf(i));
                        put("Mensaje", e.getMessage());
                    }};
                    Errores.add(error);
                }
            }
        }
        return listaMaterias;
    }
}
