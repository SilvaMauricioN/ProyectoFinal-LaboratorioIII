package utn.frbb.tup.LaboratorioIII.model.dto;

import java.util.List;

public record ProfesorDtoSalida (String nombre, String apellido,
                                 String titulo,Integer dni,
                                 List<MateriaDtoSalida> materiasDictadas){

}
