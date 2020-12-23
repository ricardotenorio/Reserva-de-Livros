package ricardotenorio.reserva.de.livros.reserva;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ricardotenorio.reserva.de.livros.entity.Reserva;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ReservaModelAssembler implements
        RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaController.class).one(reserva.getId()))
                        .withSelfRel(),
                linkTo(methodOn(ReservaController.class).all()).withRel("reservas"));
    }
}
