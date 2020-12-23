package ricardotenorio.reserva.de.livros.livro;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ricardotenorio.reserva.de.livros.entity.LivroEstado;
import ricardotenorio.reserva.de.livros.entity.Livro;
import ricardotenorio.reserva.de.livros.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class LivroController {
    private final LivroRepository repository;
    private final LivroModelAssembler assembler;

    public LivroController(LivroRepository repository, LivroModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/livros")
    CollectionModel<EntityModel<Livro>> all() {
        List<EntityModel<Livro>> livros = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(livros,
                linkTo(methodOn(LivroController.class).all()).withSelfRel());
    }

    @PostMapping("/livros")
    ResponseEntity<?> create(@RequestBody Livro livro) {
        livro.setEstado(LivroEstado.DISPONIVEL);
        EntityModel<Livro> entityModel = assembler.toModel(repository.save(livro));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/livros/{id}")
    EntityModel<Livro> one(@PathVariable Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("livro", id));
        return assembler.toModel(livro);
    }

    @PutMapping("/livros/{id}")
    ResponseEntity<?> replace(@RequestBody Livro livro, @PathVariable Long id) {
        Livro updatedLivro = repository.findById(id)
                .map(oldLivro -> {
                    oldLivro.setTitulo(livro.getTitulo());
                    oldLivro.setAutor(livro.getAutor());
                    oldLivro.setCategoria(livro.getCategoria());
                    oldLivro.setIsbn(livro.getIsbn());
                    oldLivro.setNumeroPaginas(livro.getNumeroPaginas());
                    oldLivro.setEstado(livro.getEstado());
                    return repository.save(livro);
                })
                .orElseGet(() -> {
                    livro.setId(id);
                    return repository.save(livro);
                });
        EntityModel<Livro> entityModel = assembler.toModel(updatedLivro);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/livros/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
