package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frbb.tup.LaboratorioIII.business.service.MateriaService;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.DtoMateria;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.ProfesorDao;
import java.util.*;

@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaDao materiaDao;
    private final ProfesorDao profesorDao;
    private final CastingDtos castingDtos;
    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao, ProfesorDao profesorDao){
        this.materiaDao = materiaDao;
        this.profesorDao = profesorDao;
        this.castingDtos = new CastingDtos();
    }
    @Override
    public MateriaDtoSalida crearMateria(DtoMateria dtoMateria) throws MateriaNotFoundException, ProfesorException, CorrelatividadException {
        Materia materia = new Materia();
        List<Map<String, String>> errores = castingMateriaDto(materia, dtoMateria);
        Profesor profesor = profesorDao.findProfesor(dtoMateria.getProfesorId());
        materia.setProfesor(profesor);
        materiaDao.saveMateria(materia);

        MateriaDtoSalida materiaDtoSalida =  castingDtos.convertirAmateriaDtoSalida(materia);
        ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida(profesor.getNombre(),
                profesor.getApellido(), profesor.getTitulo(), profesor.getDni());

        materiaDtoSalida.setProfesor(profesorDtoSalida);
        materiaDtoSalida.setStatus(errores);

        return materiaDtoSalida;
    }
    @Override
    public List<MateriaDtoSalida> getAllMaterias() {
        List<Materia> guardadas = materiaDao.getAllMaterias();
        List<MateriaDtoSalida> materiasDtoSalida = new ArrayList<>();

        if(!guardadas.isEmpty()){
            for(Materia m : guardadas){
                MateriaDtoSalida materiaDtoSalida = castingDtos.convertirAmateriaDtoSalida(m);
                materiasDtoSalida.add(materiaDtoSalida);
            }
        }
        return materiasDtoSalida;
    }

    @Override
    public MateriaDtoSalida findMateria(int materiaId) throws MateriaNotFoundException {

        Materia materia = materiaDao.findMateria(materiaId);
        return  castingDtos.convertirAmateriaDtoSalida(materia);
    }

    @Override
    public MateriaDtoSalida actualizarMateria(Integer id, DtoMateria dtoMateria) throws MateriaNotFoundException, ProfesorException, CorrelatividadException {

        Materia materia = materiaDao.findMateria(id);
        Profesor profesor = profesorDao.findProfesor(dtoMateria.getProfesorId());

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
        List<Map<String, String>> errores = castingMateriaDto(materia, dtoMateria);
        materia.setProfesor(profesor);
        materiaDao.upDateMateria(materia);

        MateriaDtoSalida materiaDtoSalida = castingDtos.convertirAmateriaDtoSalida(materia);
        ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida(profesor.getNombre(),
                profesor.getApellido(), profesor.getTitulo(), profesor.getDni());

        materiaDtoSalida.setStatus(errores);
        materiaDtoSalida.setProfesor(profesorDtoSalida);

        return materiaDtoSalida;
    }
    private List<Map<String, String>> castingMateriaDto(Materia materia, DtoMateria dtoMateria) throws CorrelatividadException {

        materia.setNombre(dtoMateria.getNombre());
        materia.setAnio(dtoMateria.getYear());
        materia.setCuatrimestre(dtoMateria.getCuatrimestre());

        List<Integer> correlatividadesDtoId = dtoMateria.getListaCorrelatividades();

        if(correlatividadesDtoId.contains(materia.getMateriaId())){
            throw new CorrelatividadException("LA MATERIA " + materia.getNombre() + " NO PUEDE SER SU PROPIA CORRELATIVA ");
        }else{
            List <Map<String,String>> posiblesErrores = new ArrayList<>();
            List<Materia> listaCorrelativas = getListaMateriaPorId(correlatividadesDtoId,posiblesErrores);
            materia.setListaCorrelatividades(listaCorrelativas);

            return posiblesErrores;
        }
    }
    public List<Materia> getListaMateriaPorId(List<Integer> listaId, List<Map<String,String>> Errores){
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
