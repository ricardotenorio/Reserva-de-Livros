package ricardotenorio.reserva.de.livros.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ricardotenorio.reserva.de.livros.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Cliente findByCpfAndSenha(String cpf, String senha);
}
