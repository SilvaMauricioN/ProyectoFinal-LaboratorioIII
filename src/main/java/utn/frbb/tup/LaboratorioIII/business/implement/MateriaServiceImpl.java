package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDto;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
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
    public MateriaDtoSalida crearMateria(MateriaDto materiaDto) throws MateriaNotFoundException, ProfesorException {
        Materia materia = new Materia();
        List<Map<String, String>> errores = castingMateriaDto(materia, materiaDto);
        Profesor profesor = profesorDao.findProfesor(materiaDto.getProfesorId());
        materia.setProfesor(profesor);
        materiaDao.saveMateria(materia);

        MateriaDtoSalida materiaDtoSalida = castingMateriaDtoSalida(materia);
        ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida(profesor.getNombre(),
                profesor.getApellido(), profesor.getTitulo(), profesor.getDni());

        materiaDtoSalida.setProfesorDtoSalida(profesorDtoSalida);
        materiaDtoSalida.setStatus(errores);

        return materiaDtoSalida;
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
        List<Map<String, String>> errores = castingMateriaDto(materia, materiaDto);
        materia.setProfesor(profesor);
        materiaDao.upDateMateria(materia);
        return materia;
    }
    private List<Map<String, String>> castingMateriaDto(Materia materia, MateriaDto materiaDto){

        materia.setNombre(materiaDto.getNombre());
        materia.setAnio(materiaDto.getYear());
        materia.setCuatrimestre(materiaDto.getCuatrimestre());

        List<Integer> correlatividadesDtoId = materiaDto.getListaCorrelatividades();
        List <Map<String,String>> posiblesErrores = new ArrayList<>();
        List<Materia> listaCorrelativas = getListaMateriaPorId(correlatividadesDtoId,posiblesErrores);
        materia.setListaCorrelatividades(listaCorrelativas);

        return posiblesErrores;
    }
    @Override
    public MateriaDtoSalida castingMateriaDtoSalida(Materia m){
        MateriaDtoSalida materiaSalida = new MateriaDtoSalida();
        List<MateriaDtoSalida> materiasCorrelativasDto = new ArrayList<>();

        materiaSalida.setNombre(m.getNombre());
        materiaSalida.setYear(m.getAnio());
        materiaSalida.setCuatrimestre(m.getCuatrimestre());

        if(m.getListaCorrelatividades() == null){
            materiaSalida.setCorrelativas(materiasCorrelativasDto);
        }else{
            List<Materia> materiasCorrelativas = m.getListaCorrelatividades();
            for(Materia materia: materiasCorrelativas){
                MateriaDtoSalida materiaDtoSalida = castingMateriaDtoSalida(materia);
                materiasCorrelativasDto.add(materiaDtoSalida);
            }
        }
        materiaSalida.setCorrelativas(materiasCorrelativasDto);
        return materiaSalida;
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
