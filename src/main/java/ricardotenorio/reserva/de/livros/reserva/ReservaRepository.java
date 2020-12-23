package ricardotenorio.reserva.de.livros.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import ricardotenorio.reserva.de.livros.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
