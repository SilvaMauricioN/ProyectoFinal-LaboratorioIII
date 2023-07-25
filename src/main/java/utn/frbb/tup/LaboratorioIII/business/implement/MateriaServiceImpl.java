package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.business.service.ProfesorService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaResponse;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;

import java.util.*;

@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaDao materiaDao;
    private final ProfesorService profesorService;
    //Inyeccion de dependencia por construcctor
    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorService profesorServicio){
        this.materiaDao = materiaDao;
        this.profesorService = profesorServicio;
    }
    @Override
    public MateriaResponse crearMateria(MateriaDto materiaDto) throws MateriaNotFoundException {
        Materia materia = new Materia();
//
//        Random random = new Random();
       // materia.setMateriaId(2);

        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getAnio());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());

        List<Materia> listaCorrelativas = new ArrayList<>();
        List<Integer> correlatividadesDto = materiaDto.getListaCorrelatividades();

        List <Map<String,String>> listaErrores = new ArrayList<>();


        if(correlatividadesDto != null){
            for(Integer i: correlatividadesDto){
                try {
                    Materia materiaCorrelativa = materiaDao.findMateria(i);
                    listaCorrelativas.add(materiaCorrelativa);
                } catch (MateriaNotFoundException e) {
                    Map<String,String> error = new HashMap<>(){{
                        put("Materia Id",String.valueOf(i));
                        put("Mensaje",e.getMessage());
                    }};
                    listaErrores.add(error);
                }
            }
        }
        materia.setListaCorrelatividades(listaCorrelativas);

        Profesor profesor = profesorService.findProfesor(materiaDto.getProfesorDni());
        materia.setProfesor(profesor);

        materiaDao.saveMateria(materia);
        return new MateriaResponse(materia,listaErrores);
    }

    @Override
    public List<Materia> getAllMaterias() {
        return (materiaDao.getAllMaterias());
    }

    @Override
    public Materia findMateria(int materiaId) throws MateriaNotFoundException {
        return materiaDao.findMateria(materiaId);
    }
}
