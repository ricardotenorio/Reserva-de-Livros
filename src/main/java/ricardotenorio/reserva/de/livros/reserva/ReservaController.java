package ricardotenorio.reserva.de.livros.reserva;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ricardotenorio.reserva.de.livros.entity.Livro;
import ricardotenorio.reserva.de.livros.entity.LivroEstado;
import ricardotenorio.reserva.de.livros.entity.Reserva;
import ricardotenorio.reserva.de.livros.entity.ReservaEstado;
import ricardotenorio.reserva.de.livros.exception.ResourceNotFoundException;
import ricardotenorio.reserva.de.livros.livro.LivroRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ReservaController {
    private final ReservaRepository repository;
    private final LivroRepository livroRepository;
    private final ReservaModelAssembler assembler;

    public ReservaController(ReservaRepository repository, LivroRepository livroRepository, ReservaModelAssembler assembler) {
        this.repository = repository;
        this.livroRepository = livroRepository;
        this.assembler = assembler;
    }

    @GetMapping("/reservas")
    CollectionModel<EntityModel<Reserva>> all() {
        List<EntityModel<Reserva>> reservas = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaController.class).all()).withSelfRel());
    }

    @PostMapping("/reservas")
    ResponseEntity<?> create(@RequestBody Livro livroId) {
        Livro livro = livroRepository.findById(livroId.getId())
                .orElseThrow(() -> new ResourceNotFoundException("livro", livroId.getId()));
        if (livro.getEstado() == LivroEstado.EMPRESTADO ||
            livro.getEstado() == LivroEstado.RESERVADO) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("O livro não está disponível"));
        }
        livro.setEstado(LivroEstado.RESERVADO);
        Reserva reserva = new Reserva(livro, null);
        reserva.setReservaEstado(ReservaEstado.EM_ANDAMENTO);
        reserva.setFeitaEm(new Date());
        reserva.setTerminaEm(new Date(reserva.getFeitaEm().getTime() + (1000l * 60 * 60 * 24 * 3)));
        EntityModel<Reserva> entityModel = assembler.toModel(repository.save(reserva));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/reservas/{id}")
    EntityModel<Reserva> one(@PathVariable Long id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva", id));
        return assembler.toModel(reserva);
    }

    @PutMapping("/reservas/{id}")
    ResponseEntity<?> replace(@RequestBody Reserva reserva, @PathVariable Long id) {
        Reserva updatedReserva = repository.findById(id)
                .map(oldReserva -> {
                    oldReserva.setLivro(reserva.getLivro());
                    oldReserva.setCliente(reserva.getCliente());
                    oldReserva.setFeitaEm(reserva.getFeitaEm());
                    oldReserva.setTerminaEm(reserva.getTerminaEm());
                    oldReserva.setReservaEstado(reserva.getReservaEstado());

                    return repository.save(reserva);
                })
                .orElseGet(() -> {
                    reserva.setId(id);
                    return repository.save(reserva);
                });
        EntityModel<Reserva> entityModel = assembler.toModel(updatedReserva);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/reservas/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        Reserva reserva = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("reserva", id));

        reserva.getLivro().setEstado(LivroEstado.DISPONIVEL);
        reserva.setReservaEstado(ReservaEstado.CANCELADA);
        repository.save(reserva);

        return ResponseEntity.noContent().build();
    }
}
