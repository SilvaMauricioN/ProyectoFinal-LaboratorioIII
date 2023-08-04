package utn.frbb.tup.LaboratorioIII.business.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.Alumno;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;
import utn.frbb.tup.LaboratorioIII.model.EstadoAsignatura;
import utn.frbb.tup.LaboratorioIII.model.Materia;
import utn.frbb.tup.LaboratorioIII.model.dto.*;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.AsignaturaInexistenteException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadesNoAprobadasException;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoDao alumnoDao;
    private final AsignaturaService asignaturaService;
//    private final MateriaService materiaService;
    private final CastingDtos castingDtos;
    @Autowired
    public AlumnoServiceImpl(AlumnoDao alumnoDao, AsignaturaService asignaturaService, CastingDtos castingDtos){
        this.alumnoDao = alumnoDao;
        this.asignaturaService = asignaturaService;
//        this.materiaService = materiaService;
        this.castingDtos = castingDtos;
    }
    @Override
    public void aprobarAsignatura(int materiaId, int nota, long dni) throws CorrelatividadesNoAprobadasException, AlumnoNotFoundException {
        Asignatura a = asignaturaService.getAsignatura(materiaId, dni);
        for (Materia m:
             a.getMateria().getListaCorrelatividades()) {
            Asignatura correlativa = asignaturaService.getAsignatura(m.getMateriaId(), dni);
            if (!EstadoAsignatura.APROBADA.equals(correlativa.getEstado())) {
                throw new CorrelatividadesNoAprobadasException("La materia " + m.getNombre() + " debe estar aprobada para aprobar " );
            }
        }
        a.aprobarAsignatura(nota);
        asignaturaService.actualizarAsignatura(a);
        Alumno alumno = alumnoDao.loadAlumno(dni);
        alumno.actualizarAsignatura(a);
        alumnoDao.saveAlumno(alumno);
    }
    @Override
    public AlumnoDtoSalida crearAlumno(AlumnoDto dtoAlumno) throws AsignaturaInexistenteException, AlumnoNotFoundException {
        Alumno alumno = new Alumno();
        List<Map<String, String>> errores = castingAlumnoDto(alumno, dtoAlumno);
        alumnoDao.saveAlumno(alumno);

        AlumnoDtoSalida alumnoDtoSalida = castingAlumnoDtoSalida(alumno);
        alumnoDtoSalida.setStatus(errores);

        return alumnoDtoSalida;
    }
    @Override
    public List<Alumno> buscarAlumno(String apellido) throws AlumnoNotFoundException {
        return alumnoDao.findAlumno(apellido);
    }
    private List<Map<String, String>> castingAlumnoDto(Alumno alumno, AlumnoDto dtoAlumno) throws AsignaturaInexistenteException {
        alumno.setNombre(dtoAlumno.getNombre());
        alumno.setApellido(dtoAlumno.getApellido());
        alumno.setDni(dtoAlumno.getDni());

        List<Asignatura> registrado = new ArrayList<>();
        List<Integer> idAsignaturas = dtoAlumno.getAsignaturasId();
        List<Map<String,String>> posiblesErrores = new ArrayList<>();

        if(dtoAlumno.getAsignaturasId() != null){
            List<Materia> materiasInscripto = castingDtos.getListaMateriaPorId(idAsignaturas,posiblesErrores);

            for(Materia m : materiasInscripto){
                Asignatura asignatura = new Asignatura(m);
                registrado.add(asignatura);
            }
            alumno.setListaAsignaturas(registrado);
            return posiblesErrores;
        }else{
            throw new AsignaturaInexistenteException("EL ALUMNO DEBE ESTAR INSCRIPTO EN ALGUNA MATERIA");
        }
    }
    private AlumnoDtoSalida castingAlumnoDtoSalida(Alumno alumno){
        AlumnoDtoSalida alumnoDtoSalida = new AlumnoDtoSalida(alumno.getNombre(),
                alumno.getApellido(),alumno.getDni());
        List<Asignatura> asignaturas = alumno.getListaAsignaturas();

        List<AsignaturaDtoSalida> asignaturaRegistrado = new ArrayList<>();

        for(Asignatura a : asignaturas){
            AsignaturaDtoSalida asignaturaDtoSalida = new AsignaturaDtoSalida();

            asignaturaDtoSalida.setNombre(a.getMateria().getNombre());
            asignaturaDtoSalida.setAnio(a.getMateria().getAnio());
            asignaturaDtoSalida.setCuatrimestre(a.getMateria().getCuatrimestre());
            if(a.getMateria().getProfesor() != null){
                asignaturaDtoSalida.setProfesor(a.getMateria().getProfesor().getApellido());
            }
            asignaturaDtoSalida.setEstado(a.getEstado());

            List<Materia> correlativas = a.getMateria().getListaCorrelatividades();
            if(correlativas != null){
                for(Materia m : correlativas){
                    asignaturaDtoSalida.setCorrelativas(m.getNombre());
                }
            }

            asignaturaDtoSalida.setNota(a.getNota());
            asignaturaRegistrado.add(asignaturaDtoSalida);
        }

        alumnoDtoSalida.setAsignaturas(asignaturaRegistrado);
        return alumnoDtoSalida;
    }


}
