package ricardotenorio.reserva.de.livros.livro;

public class LivroNotFoundException extends RuntimeException {
    public LivroNotFoundException(Long id) {
        super("Não foi possível achar o livro com id: " + id);
    }
}
