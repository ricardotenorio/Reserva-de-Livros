package ricardotenorio.reserva.de.livros.livro;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LivroModelAssembler implements
        RepresentationModelAssembler<Livro, EntityModel<Livro>> {
    @Override
    public EntityModel<Livro> toModel(Livro livro) {
        return EntityModel.of(livro,
                linkTo(methodOn(LivroController.class).one(livro.getId()))
                .withSelfRel(),
                linkTo(methodOn(LivroController.class).all()).withRel("livros"));
    }
}
