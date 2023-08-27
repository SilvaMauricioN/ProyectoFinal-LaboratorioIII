package utn.frbb.tup.LaboratorioIII.business.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.business.service.AlumnoService;
import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.*;
import utn.frbb.tup.LaboratorioIII.model.dto.*;
import utn.frbb.tup.LaboratorioIII.model.exception.*;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AlumnoDao;
import utn.frbb.tup.LaboratorioIII.persistence.dao.AsignaturaDao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoDao alumnoDao;
    private final AsignaturaService asignaturaService;
    private final AsignaturaDao asignaturaDao;
    private final CastingDtos castingDtos;
    @Autowired
    public AlumnoServiceImpl(AlumnoDao alumnoDao, AsignaturaService asignaturaService,AsignaturaDao asignaturaDao, CastingDtos castingDtos){
        this.alumnoDao = alumnoDao;
        this.asignaturaService = asignaturaService;
        this.asignaturaDao = asignaturaDao;
        this.castingDtos = castingDtos;
    }
    @Override
    public AlumnoDtoSalida crearAlumno(AlumnoDto dtoAlumno) throws AsignaturaInexistenteException, AlumnoNotFoundException {
        Alumno alumno = new Alumno();
        List<Map<String, String>> errores = castingDtos.aAlumnoDto(alumno, dtoAlumno);
        alumnoDao.saveAlumno(alumno);
        asignaturaDao.saveAsignatura(alumno);

        AlumnoDtoSalida alumnoDtoSalida = castingDtos.aAlumnoDtoSalida(alumno);
        alumnoDtoSalida.setStatus(errores);

        return alumnoDtoSalida;
    }
    @Override
    public List<AlumnoDtoSalida> buscarAlumnoPorApellido(String apellido) throws AlumnoNotFoundException {
        List<Alumno> guardados = alumnoDao.findAlumnoApellido(apellido);
        List<AlumnoDtoSalida> guardadosSalida = new ArrayList<>();

        for(Alumno a : guardados){
            AlumnoDtoSalida alumnoDtoSalida = castingDtos.aAlumnoDtoSalida(a);
            guardadosSalida.add(alumnoDtoSalida);
        }
        return guardadosSalida;
    }

    @Override
    public AlumnoDtoSalida buscarAlumnoPorId(Integer alumnoId) throws AlumnoNotFoundException {
        Alumno alumno = alumnoDao.findAlumnoId(alumnoId);

        return castingDtos.aAlumnoDtoSalida(alumno);
    }

    @Override
    public AlumnoDtoSalida actualizarAlumno(Integer id, AlumnoDto alumnoDto) throws AsignaturaInexistenteException, AlumnoNotFoundException {
        Alumno alumno = alumnoDao.findAlumnoId(id);

        List<Map<String,String>> posiblesErrores = castingDtos.aAlumnoDto(alumno,alumnoDto);

        alumnoDao.upDateAlumno(alumno);
        asignaturaDao.saveAsignatura(alumno);

        AlumnoDtoSalida alumnoDtoSalida = castingDtos.aAlumnoDtoSalida(alumno);
        alumnoDtoSalida.setStatus(posiblesErrores);

        return alumnoDtoSalida;
    }
    @Override
    public void deleteAlumno(Integer idAlumno) throws AlumnoNotFoundException, AsignaturaInexistenteException{
        alumnoDao.findAlumnoId(idAlumno);
        alumnoDao.deleteAlumno(idAlumno);
        asignaturaDao.deleteAsignaturas(idAlumno);
    }
    //aprobar,cursar o recursar
    @Override
    public AsignaturaDtoSalida modificaEstadoAsignatura(Integer idAlumno, Integer idAsignatura, Nota nota) throws EstadoIncorrectoException, CorrelatividadesNoAprobadasException, AsignaturaInexistenteException, AlumnoNotFoundException {
        if(!nota.estado().equals(EstadoAsignatura.CURSADA)){
            throw new EstadoIncorrectoException("LA MATERIA DEBE ESTAR CURSADA");
        }
        Asignatura asignatura = asignaturaService.aprobarAsignatura(idAlumno, idAsignatura, nota);

        actualizarMateriaEnAlumno(idAlumno, asignatura);

        return castingDtos.aAsignaturaDtoSalida(asignatura);
    }

    private void actualizarMateriaEnAlumno(Integer idAlumno, Asignatura asignaturaActualizada) throws AlumnoNotFoundException {
        Alumno alumno = alumnoDao.findAlumnoId(idAlumno);
        List<Asignatura> cursadas = alumno.getListaAsignaturas();

        for(int i = 0 ; i < cursadas.size(); i++ ){
            Asignatura a = cursadas.get(i);
            if(a.getMateria().getMateriaId() == asignaturaActualizada.getMateria().getMateriaId()){
                cursadas.set(i,asignaturaActualizada);
                break;
            }
        }

        alumnoDao.upDateAlumno(alumno);
    }


}
