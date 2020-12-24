package ricardotenorio.reserva.de.livros.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token inválido");
    }
}
