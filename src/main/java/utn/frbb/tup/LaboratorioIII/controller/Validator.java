package utn.frbb.tup.LaboratorioIII.controller;

import java.lang.reflect.Field;

public class Validator {
    public static void ValidarCampos(Object dto) throws IllegalArgumentException, IllegalAccessException {
        Field[] campos = dto.getClass().getDeclaredFields();

        for(Field campo:campos){
            campo.setAccessible(true);
            Object valor = campo.get(dto);

            if(valor == null || (valor instanceof String && ((String) valor).trim().isEmpty())){
                throw  new IllegalArgumentException("Falta el campo: " + campo.getName());
            }
        }
    }
}
