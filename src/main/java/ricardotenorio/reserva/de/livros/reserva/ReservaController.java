package ricardotenorio.reserva.de.livros.reserva;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ricardotenorio.reserva.de.livros.entity.Reserva;
import ricardotenorio.reserva.de.livros.entity.ReservaEstado;
import ricardotenorio.reserva.de.livros.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ReservaController {
    private final ReservaRepository repository;
    private final ReservaModelAssembler assembler;

    public ReservaController(ReservaRepository repository, ReservaModelAssembler assembler) {
        this.repository = repository;
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
    ResponseEntity<?> create(@RequestBody Reserva reserva) {
        reserva.setReservaEstado(ReservaEstado.EM_ANDAMENTO);
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
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
