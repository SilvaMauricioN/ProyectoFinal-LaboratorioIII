package utn.frbb.tup.LaboratorioIII.model.dto;

import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.Profesor;

import java.util.List;
import java.util.Map;

public record ProfesorResponse (Profesor profesor, List<Map<String,String>> errores){
}
