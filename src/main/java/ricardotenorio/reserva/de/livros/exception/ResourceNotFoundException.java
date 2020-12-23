package ricardotenorio.reserva.de.livros.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String nome, Long id) {
        super("Não foi possível achar o " + nome + " com id: " + id);
    }
}
