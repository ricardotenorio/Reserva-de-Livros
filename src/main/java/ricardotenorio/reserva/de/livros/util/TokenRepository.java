package ricardotenorio.reserva.de.livros.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ricardotenorio.reserva.de.livros.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    public Token findByHash(String hash);
}
