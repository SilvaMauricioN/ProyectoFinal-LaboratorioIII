package utn.frbb.tup.LaboratorioIII.business.implement;

import org.springframework.stereotype.Component;
import utn.frbb.tup.LaboratorioIII.business.service.AsignaturaService;
import utn.frbb.tup.LaboratorioIII.model.Asignatura;

@Component
public class AsignaturaServiceImpl implements AsignaturaService {
    @Override
    public Asignatura getAsignatura(int materiaId, long dni) {
        return null;
    }

    @Override
    public void actualizarAsignatura(Asignatura a) {

    }
}
