package ricardotenorio.reserva.de.livros.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import ricardotenorio.reserva.de.livros.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
