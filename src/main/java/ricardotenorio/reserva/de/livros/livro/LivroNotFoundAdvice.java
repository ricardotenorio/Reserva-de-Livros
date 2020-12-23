package ricardotenorio.reserva.de.livros.livro;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LivroNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(LivroNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String livroNotFoundHandler(LivroNotFoundException e) {
        return e.getMessage();
    }
}
