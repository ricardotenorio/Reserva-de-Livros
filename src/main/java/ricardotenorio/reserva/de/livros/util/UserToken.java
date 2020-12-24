package ricardotenorio.reserva.de.livros.util;

import org.springframework.stereotype.Component;
import ricardotenorio.reserva.de.livros.cliente.ClienteRepository;
import ricardotenorio.reserva.de.livros.entity.Cliente;
import ricardotenorio.reserva.de.livros.entity.Token;
import ricardotenorio.reserva.de.livros.exception.InvalidTokenException;

import java.util.Date;

@Component
public class UserToken {
    private final TokenRepository repository;
    private final ClienteRepository clienteRepository;

    public UserToken(TokenRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public String generateToken(Cliente cliente) {
        String hash = HashString.getHash(cliente.getCpf());
        Token token = new Token(hash);
        token.setCriadoEm(new Date());
        token.setTerminaEm(new Date(token.getCriadoEm().getTime() + (1000l * 60 * 60 * 24 * 7)));
        token.setCliente(cliente);
        repository.save(token);

        cliente.setToken(token);
        clienteRepository.save(cliente);

        return hash;
    }

    public boolean validToken(String tokenString, Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new InvalidTokenException());
        String hash = HashString.getHash(cliente.getCpf());

        Token token = repository.findByHash(hash);

        if (token.getTerminaEm().after(new Date())) {
            throw new InvalidTokenException();
        }

        return true;
    }
}
