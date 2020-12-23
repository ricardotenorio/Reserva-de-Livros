package ricardotenorio.reserva.de.livros.livro;

import org.springframework.data.jpa.repository.JpaRepository;
import ricardotenorio.reserva.de.livros.livro.Livro;

interface LivroRepository extends JpaRepository<Livro, Long> {
}
