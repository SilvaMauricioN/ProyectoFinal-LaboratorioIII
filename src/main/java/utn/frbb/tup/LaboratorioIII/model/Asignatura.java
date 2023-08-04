package utn.frbb.tup.LaboratorioIII.model;

import utn.frbb.tup.LaboratorioIII.model.exception.EstadoIncorrectoException;

import java.util.Optional;

public class Asignatura {
    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;
    public Asignatura() {
    }
    public Asignatura(Materia materia) {
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
    }

    public Optional<Integer> getNota() {
        return Optional.ofNullable(nota);
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public EstadoAsignatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }
    public void cursarAsignatura(){
        this.estado = EstadoAsignatura.CURSADA;
    }

    public void aprobarAsignatura(int nota) throws EstadoIncorrectoException {
        if (nota >= 4 && nota <= 10) {
            this.estado = EstadoAsignatura.APROBADA;
            this.nota = nota;
        }else{
            this.estado = EstadoAsignatura.RECURSA;
            this.nota = nota;
        }
    }

}
