package ricardotenorio.reserva.de.livros.cliente;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ricardotenorio.reserva.de.livros.entity.Cliente;
import ricardotenorio.reserva.de.livros.entity.Token;
import ricardotenorio.reserva.de.livros.exception.ClienteInvalidoException;
import ricardotenorio.reserva.de.livros.exception.ResourceNotFoundException;
import ricardotenorio.reserva.de.livros.util.TokenRepository;
import ricardotenorio.reserva.de.livros.util.UserToken;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ClienteController {
    private final ClienteRepository repository;
    private final UserToken userToken;
    private final ClienteModelAssembler assembler;

    public ClienteController(ClienteRepository repository, UserToken userToken, ClienteModelAssembler assembler) {
        this.repository = repository;
        this.userToken = userToken;
        this.assembler = assembler;
    }

    @GetMapping("/clientes")
    CollectionModel<EntityModel<Cliente>> all() {
        List<EntityModel<Cliente>> clientes = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).all()).withSelfRel());
    }

    @PostMapping("/clientes")
    ResponseEntity<?> create(@RequestBody Cliente cliente) {
        EntityModel<Cliente> entityModel = assembler.toModel(repository.save(cliente));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/clientes/login")
    String login(@RequestBody Cliente cliente) throws ClienteInvalidoException {
        try {
            Cliente authCliente = repository.findByCpfAndSenha(cliente.getCpf(), cliente.getSenha());
            return userToken.generateToken(authCliente);
        } catch (Exception e) {
            throw new ClienteInvalidoException();
        }
    }

    @GetMapping("/clientes/{id}")
    EntityModel<Cliente> one(@PathVariable Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cliente", id));
        return assembler.toModel(cliente);
    }

    @PutMapping("/clientes/{id}")
    ResponseEntity<?> replace(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente updatedCliente = repository.findById(id)
                .map(oldCliente -> {
                    oldCliente.setNome(cliente.getNome());
                    oldCliente.setSobrenome(cliente.getSobrenome());
                    oldCliente.setCpf(cliente.getCpf());
                    oldCliente.setSenha(cliente.getSenha());
                    return repository.save(cliente);
                })
                .orElseGet(() -> {
                    cliente.setId(id);
                    return repository.save(cliente);
                });
        EntityModel<Cliente> entityModel = assembler.toModel(updatedCliente);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/clientes/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
