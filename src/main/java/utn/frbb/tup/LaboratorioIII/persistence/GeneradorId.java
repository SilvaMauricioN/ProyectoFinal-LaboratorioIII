package utn.frbb.tup.LaboratorioIII.persistence;

public class GeneradorId {
    private int IdActual =  0;
    private static GeneradorId INSTANCE;

    public static synchronized GeneradorId getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GeneradorId();
        }
        return INSTANCE;
    }
    public synchronized int getIdNuevo(){
        if(IdActual >= 100){
            throw new IllegalStateException("PROBLEMA AL GENERAR ID");
        }
        return IdActual = IdActual + 1;
    }
    public void reset(){
        IdActual = 0;
    }
}
