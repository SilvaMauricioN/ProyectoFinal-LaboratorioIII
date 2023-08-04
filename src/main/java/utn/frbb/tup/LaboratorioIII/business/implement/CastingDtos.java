package utn.frbb.tup.LaboratorioIII.business.implement;

import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;
import utn.frbb.tup.LaboratorioIII.model.dto.MateriaDtoSalida;
import utn.frbb.tup.LaboratorioIII.model.dto.ProfesorDtoSalida;

import java.util.ArrayList;
import java.util.List;

public class CastingDtos {
    public MateriaDtoSalida convertirAmateriaDtoSalida(Materia materia){
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
                MateriaDtoSalida materiaDtoSalida = convertirAmateriaDtoSalida(m);
                materiasCorrelativasDto.add(materiaDtoSalida);
            }
        }
        materiaSalida.setCorrelativas(materiasCorrelativasDto);
        return materiaSalida;

    }
    public ProfesorDtoSalida convertirAprofesorDtoSalida(Profesor profesor){
        ProfesorDtoSalida profesorDtoSalida = new ProfesorDtoSalida(profesor.getNombre(),
                profesor.getApellido(), profesor.getTitulo(), profesor.getDni());

        List<MateriaDtoSalida> materiaDtoSalidas = new ArrayList<>();
        if(profesor.getMateriasDictadas() != null){
            List<Materia> materiasDictadas = profesor.getMateriasDictadas();

            for(Materia m : materiasDictadas){
                MateriaDtoSalida materiaDtoSalida = convertirAmateriaDtoSalida(m);
                materiaDtoSalidas.add(materiaDtoSalida);
            }
        }
        profesorDtoSalida.setMaterias(materiaDtoSalidas);
        return profesorDtoSalida;
    }


}
