package ricardotenorio.reserva.de.livros.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClienteInvalidoAdvice {
    @ResponseBody
    @ExceptionHandler(ClienteInvalidoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String clienteInvalidoHandler(ClienteInvalidoException e) {
        return e.getMessage();
    }
}
