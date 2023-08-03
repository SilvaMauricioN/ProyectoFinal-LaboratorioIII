package utn.frbb.tup.LaboratorioIII.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utn.frbb.tup.LaboratorioIII.model.exception.AlumnoNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.CorrelatividadException;
import utn.frbb.tup.LaboratorioIII.model.exception.MateriaNotFoundException;
import utn.frbb.tup.LaboratorioIII.model.exception.ProfesorException;

//anotacion particular, indica que la clase majena las exepciones y mapeas el codigo de error adecuado
@ControllerAdvice
public class UtnResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class,ProfesorException.class, CorrelatividadException.class,Exception.class})
    //recibe una exepcion del tipo runtimeException(illegalArgumet/illegalState)
    protected ResponseEntity<Object> handleConflict(Exception exception, WebRequest request) {
        HttpStatus status;
        String mensajeError = exception.getMessage();

        if(exception instanceof MateriaNotFoundException || exception instanceof AlumnoNotFoundException){
            status = HttpStatus.NOT_FOUND;
        }else if((exception instanceof IllegalArgumentException || exception instanceof ProfesorException || exception instanceof CorrelatividadException)){
            status = HttpStatus.BAD_REQUEST;
        }else{
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        CustomApiError error = new CustomApiError();
        error.setErrorCode(status.value());
        error.setErrorMessage(mensajeError);
        //Metodo de spring boot
        return handleExceptionInternal(exception, error, new HttpHeaders(), status, request);
    }

   // @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(ex.getMessage());
            body = error;
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
