package utn.frbb.tup.LaboratorioIII.model.dto;

import utn.frbb.tup.LaboratorioIII.model.Materia;

import java.util.List;
import java.util.Map;

public record MateriaResponse(Materia materia, List<Map<String,String>> errores) {


}
