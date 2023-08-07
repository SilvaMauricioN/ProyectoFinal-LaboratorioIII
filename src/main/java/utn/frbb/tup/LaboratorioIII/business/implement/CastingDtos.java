package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.*;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.MateriaDao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CastingDtos {
    private final MateriaDao materiaDao;
    @Autowired
    public CastingDtos(MateriaDao materiaDao){
        this.materiaDao = materiaDao;
    }


    //Metodos Entidades de Salida
    public MateriaDtoSalida aMateriaDtoSalida(Materia materia){
        if(materia.getNombre() != null){
            MateriaDtoSalida materiaSalida = new MateriaDtoSalida();
            List<MateriaDtoSalida> materiasCorrelativasDto = new ArrayList<>();

            materiaSalida.setNombre(materia.getNombre());
            materiaSalida.setAnio(materia.getAnio());
            materiaSalida.setCuatrimestre(materia.getCuatrimestre());

            if(materia.getListaCorrelatividades() == null){
                materiaSalida.setCorrelativas(materiasCorrelativasDto);
            }else{
                List<Materia> materiasCorrelativas = materia.getListaCorrelatividades();
                for(Materia m: materiasCorrelativas){
                    MateriaDtoSalida materiaDtoSalida = aMateriaDtoSalida(m);
                    materiasCorrelativasDto.add(materiaDtoSalida);
                }
            }
            materiaSalida.setCorrelativas(materiasCorrelativasDto);
            return materiaSalida;
        }
        return new MateriaDtoSalida();
    }
    //ProfesorDtosSalida con materias dictadas
    public ProfesorDtoSalida aProfesorDtoSalida(Profesor profesor){
        if(profesor != null){
            ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida(profesor.getNombre(),
                    profesor.getApellido(), profesor.getTitulo(), profesor.getDni());

            List<MateriaDtoSalida> materiaDtoSalidas = new ArrayList<>();
            if(profesor.getMateriasDictadas() != null){
                List<Materia> materiasDictadas = profesor.getMateriasDictadas();

                for(Materia m : materiasDictadas){
                    MateriaDtoSalida materiaDtoSalida = aMateriaDtoSalida(m);
                    materiaDtoSalidas.add(materiaDtoSalida);
                }
            }
            profesorDtoSalida.setMaterias(materiaDtoSalidas);
            return profesorDtoSalida;
        }
        return new ProfesorDtoSalida();

    }
    public ProfesorDtoSalida toProfesorDtoSalida(Profesor profesor){
        ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida();
        profesorDtoSalida.setNombre(profesor.getNombre());
        profesorDtoSalida.setApellido(profesor.getApellido());
        profesorDtoSalida.setTitulo(profesor.getTitulo());
        profesorDtoSalida.setDni(profesor.getDni());
        return profesorDtoSalida;
    }
    public AlumnoDtoSalida aAlumnoDtoSalida(Alumno alumno){
        AlumnoDtoSalida alumnoDtoSalida = new AlumnoDtoSalida(alumno.getNombre(),
                alumno.getApellido(),alumno.getDni());

        List<Asignatura> asignaturas = alumno.getListaAsignaturas();
        List<AsignaturaDtoSalida> asignaturaRegistrado = new ArrayList<>();

        for(Asignatura a : asignaturas){
            AsignaturaDtoSalida asignaturaDtoSalida = aAsignaturaDtoSalida(a);
            asignaturaRegistrado.add(asignaturaDtoSalida);
        }

        alumnoDtoSalida.setAsignaturas(asignaturaRegistrado);
        return alumnoDtoSalida;
    }
    public AsignaturaDtoSalida aAsignaturaDtoSalida(Asignatura asignatura){
        AsignaturaDtoSalida asignaturaDtoSalida = new AsignaturaDtoSalida();
        asignaturaDtoSalida.setNombre(asignatura.getMateria().getNombre());
        asignaturaDtoSalida.setAnio(asignatura.getMateria().getAnio());
        asignaturaDtoSalida.setCuatrimestre(asignatura.getMateria().getCuatrimestre());
        asignaturaDtoSalida.setEstado(asignatura.getEstado());
        asignaturaDtoSalida.setNota(asignatura.getNota());

        if(asignatura.getMateria().getProfesor() != null){
            asignaturaDtoSalida.setProfesor(asignatura.getMateria().getProfesor().getApellido());
        }
        List<Materia> correlativas = asignatura.getMateria().getListaCorrelatividades();
        if(correlativas != null){
            for(Materia m : correlativas){
                asignaturaDtoSalida.setCorrelativas(m.getNombre());
            }
        }
        return asignaturaDtoSalida;
    }

    //Metodos Entidades de Entrada
    public List<Map<String, String>> aMateriaDto(Materia materia, MateriaDto dtoMateria) throws CorrelatividadException {
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
    public List<Map<String,String>> aProfesorDto(Profesor profesor, ProfesorDto dtoProfesor) throws ProfesorException {
        if(dtoProfesor != null){
            profesor.setNombre(dtoProfesor.getNombre());
            profesor.setApellido(dtoProfesor.getApellido());
            profesor.setTitulo(dtoProfesor.getTitulo());
            profesor.setDni(dtoProfesor.getDni());

            List<Integer> Idmaterias = dtoProfesor.getMateriasDictadasID();
            List<Map<String, String>> posiblesErrores = new ArrayList<>();
            List<Materia> listaMateriasDictadas = getListaMateriaPorId(Idmaterias,posiblesErrores);

            //Agrego las materias a dictar por el profesor,
            if(listaMateriasDictadas != null){
                for(Materia m: listaMateriasDictadas){
                    //profesor nuevo, lista materias vacias
                    if(m.getProfesor() == null){
                        profesor.setMateria(m);
                    }else{
                        throw new ProfesorException("LA MATERIA " + m.getNombre() + " YA TIENE PROFESOR ASIGNADO");
                    }
                }
            }
            return posiblesErrores;
        }
        throw new ProfesorException("SIN DATOS");

    }
    public  List<Map<String, String>> aAlumnoDto(Alumno alumno, AlumnoDto dtoAlumno) throws AsignaturaInexistenteException{
        alumno.setNombre(dtoAlumno.getNombre());
        alumno.setApellido(dtoAlumno.getApellido());
        alumno.setDni(dtoAlumno.getDni());

        List<Asignatura> registrado = new ArrayList<>();
        List<Integer> idAsignaturas = dtoAlumno.getAsignaturasId();
        List<Map<String,String>> posiblesErrores = new ArrayList<>();

        if(dtoAlumno.getAsignaturasId() != null){
            List<Materia> materiasInscripto = getListaMateriaPorId(idAsignaturas,posiblesErrores);

            for(Materia materia : materiasInscripto){
                Asignatura asignatura = new Asignatura(materia);
                registrado.add(asignatura);
            }
            alumno.setListaAsignaturas(registrado);
            return posiblesErrores;
        }else{
            throw new AsignaturaInexistenteException("EL ALUMNO DEBE ESTAR INSCRIPTO EN ALGUNA MATERIA");
        }

    }
    //Obtener lista de materias de la capa de persistencia de materia
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
