package utn.frbb.tup.LaboratorioIII.model.dto;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MateriaDtoSalida implements Comparable<MateriaDtoSalida>{
    String nombre;
    Integer year;
    Integer cuatrimestre;
    ProfesorDtoSalida profesorDtoSalida;
    List<MateriaDtoSalida> Correlativas = new ArrayList<>();
    List<Map<String,String>> status = new ArrayList<>();

    public MateriaDtoSalida() {
    }
    public MateriaDtoSalida(String nombre, Integer year, Integer cuatrimestre) {
        this.nombre = nombre;
        this.year = year;
        this.cuatrimestre = cuatrimestre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(Integer cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public ProfesorDtoSalida getProfesorDtoSalida() {
        return profesorDtoSalida;
    }
    public void setProfesorDtoSalida(ProfesorDtoSalida profesorDtoSalida) {
        this.profesorDtoSalida = profesorDtoSalida;
    }
    public List<MateriaDtoSalida> getCorrelativas() {
        return Correlativas;
    }

    public void setCorrelativas(List<MateriaDtoSalida> correlativas) {
        Correlativas = correlativas;
    }
    public List<Map<String, String>> getStatus() {
        return status;
    }
    public void setStatus(List<Map<String, String>> status) {
        this.status = status;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateriaDtoSalida materia = (MateriaDtoSalida) o;
        return this.year == materia.year && this.cuatrimestre == materia.cuatrimestre && Objects.equals(nombre, materia.nombre) && Objects.equals(Correlativas, materia.Correlativas);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nombre, year, cuatrimestre);
    }
    @Override
    public int compareTo(MateriaDtoSalida materiaSalida) {
        int valor = this.nombre.compareTo(materiaSalida.nombre);

        if(valor==0){
            valor = Integer.compare(this.year, materiaSalida.year);
        }
        return valor;
    }
    @Override
    public String toString() {
        return "MateriaDtoSalida{" +
                "nombre='" + nombre + '\'' +
                ", year=" + year +
                ", cuatrimestre=" + cuatrimestre +
                ", Correlativas=" + Correlativas +
                '}';
    }
}
