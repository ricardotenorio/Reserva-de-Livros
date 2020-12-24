package ricardotenorio.reserva.de.livros.exception;

public class ClienteInvalidoException extends RuntimeException {
    public ClienteInvalidoException() {
        super("Cpf ou senha inválidos");
    }
}
