package ricardotenorio.reserva.de.livros.livro;

import org.springframework.data.jpa.repository.JpaRepository;
import ricardotenorio.reserva.de.livros.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
