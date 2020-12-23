package ricardotenorio.reserva.de.livros.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class Livro extends Entidade {
    private @Id @GeneratedValue Long id;
    private String titulo;
    private String autor;
    private String categoria;
    private String isbn;
    private int numeroPaginas;
    private LivroEstado livroEstado;

    @OneToMany(mappedBy = "livro")
    private Set<Reserva> reservas;

    public Livro() {}

    public Livro(String titulo, String autor,
                 String categoria, String isbn, int numeroPaginas, LivroEstado livroEstado) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.isbn = isbn;
        this.numeroPaginas = numeroPaginas;
        this.livroEstado = livroEstado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public LivroEstado getEstado() {
        return livroEstado;
    }

    public void setEstado(LivroEstado livroEstado) {
        this.livroEstado = livroEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Livro)) return false;
        Livro livro = (Livro) o;
        return numeroPaginas == livro.numeroPaginas && id.equals(livro.id) && titulo.equals(livro.titulo) && autor.equals(livro.autor) && categoria.equals(livro.categoria) && isbn.equals(livro.isbn) && livroEstado == livro.livroEstado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor, categoria, isbn, numeroPaginas, livroEstado);
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", categoria='" + categoria + '\'' +
                ", isbn='" + isbn + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                ", estado=" + livroEstado +
                '}';
    }
}
