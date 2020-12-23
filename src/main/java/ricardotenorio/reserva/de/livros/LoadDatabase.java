package ricardotenorio.reserva.de.livros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ricardotenorio.reserva.de.livros.cliente.ClienteRepository;
import ricardotenorio.reserva.de.livros.entity.Cliente;
import ricardotenorio.reserva.de.livros.entity.Livro;
import ricardotenorio.reserva.de.livros.entity.LivroEstado;
import ricardotenorio.reserva.de.livros.livro.LivroRepository;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(LivroRepository livroRepository, ClienteRepository clienteRepository) {

        return args -> {
            livroRepository.save(new Livro("Test", "author test",
                    "fiction", "12234", 122, LivroEstado.DISPONIVEL));
            livroRepository.save(new Livro("Another Test", "author test",
                    "fiction", "123334", 452, LivroEstado.DISPONIVEL));


            clienteRepository.save(new Cliente("jose", "silva", "12344567890", "senha"));
            clienteRepository.save(new Cliente("maria", "silva", "12344567891", "senha2"));
        };
    }
}