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
    //Inyeccion de dependencia por construcctor
    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorDao profesorDao){
        this.materiaDao = materiaDao;
        this.profesorDao = profesorDao;
    }
    @Override
    public MateriaResponse crearMateria(MateriaDto materiaDto) throws MateriaNotFoundException, ProfesorException {
        Profesor profesor = profesorDao.findProfesor(materiaDto.getProfesorId());

        Materia materia = new Materia(
                materiaDto.getNombre(),
                materiaDto.getYear(),
                materiaDto.getCuatrimestre(),
                profesor);

        List<Integer> correlatividadesDto = materiaDto.getListaCorrelatividades();
        List <Map<String,String>> posiblesErrores = new ArrayList<>();
        List<Materia> listaCorrelativas = getListaMateriaPorId(correlatividadesDto,posiblesErrores);

        materia.setListaCorrelatividades(listaCorrelativas);
        materiaDao.saveMateria(materia);
        return new MateriaResponse(materia,posiblesErrores);
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

        if(materia != null){
            materia.setNombre(materiaDto.getNombre());
            materia.setAnio(materiaDto.getYear());
            materia.setCuatrimestre(materiaDto.getCuatrimestre());

            Profesor profesor = profesorDao.findProfesor(materiaDto.getProfesorId());
            materia.setProfesor(profesor);

            List<Integer> correlatividadesDto = materiaDto.getListaCorrelatividades();
            List <Map<String,String>> posiblesErrores = new ArrayList<>();
            List<Materia> listaCorrelativas = getListaMateriaPorId(correlatividadesDto,posiblesErrores);

            materia.setListaCorrelatividades(listaCorrelativas);

            materiaDao.upDateMateria(materia);
        }else{
            throw new MateriaNotFoundException("NO SE ENCONTROLA MATERIA A ACTUALIZAR");

        }
        return materia;
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

    private void actualizarMateriaEnProfesor(Integer id, Materia materia){


    }
}
