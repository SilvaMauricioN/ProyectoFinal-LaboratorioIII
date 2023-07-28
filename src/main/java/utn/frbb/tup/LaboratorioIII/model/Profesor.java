package utn.frbb.tup.LaboratorioIII.model;

import com.fasterxml.jackson.annotation.*;

import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "profesorId")
public class Profesor {
    private int profesorId;
    private String nombre;
    private String apellido;
    private String titulo;
    private int dni;
    private List<Materia> materiasDictadas;
    public Profesor(){}
    public Profesor(String nombre, String apellido, String titulo, int dni) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.titulo = titulo;
        this.dni = dni;
    }
    public int getProfesorId() {
        return profesorId;
    }
    public void setProfesorId(int profesorId){
        this.profesorId = profesorId;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getDni() {
        return dni;
    }
    public void setDni(int dni) {
        this.dni = dni;
    }
    public List<Materia> getMateriasDictadas() {
        return materiasDictadas;
    }
    public void setMateriasDictadas(List<Materia> materiasDictadas) {
        this.materiasDictadas = materiasDictadas;
    }
}
