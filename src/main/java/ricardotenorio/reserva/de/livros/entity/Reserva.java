package ricardotenorio.reserva.de.livros.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Reserva extends Entidade {

    private @Id @GeneratedValue Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    private Date feitaEm;

    @Temporal(TemporalType.DATE)
    private Date terminaEm;

    private ReservaEstado reservaEstado;

    public Reserva() {}

    public Reserva(Livro livro, Cliente cliente) {
        this.livro = livro;
        this.cliente = cliente;
    }

    public Reserva(Livro livro, Cliente cliente, Date feitaEm, Date terminaEm, ReservaEstado reservaEstado) {
        this.livro = livro;
        this.cliente = cliente;
        this.feitaEm = feitaEm;
        this.terminaEm = terminaEm;
        this.reservaEstado = reservaEstado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFeitaEm() {
        return feitaEm;
    }

    public void setFeitaEm(Date feitaEm) {
        this.feitaEm = feitaEm;
    }

    public Date getTerminaEm() {
        return terminaEm;
    }

    public void setTerminaEm(Date terminaEm) {
        this.terminaEm = terminaEm;
    }

    public ReservaEstado getReservaEstado() {
        return reservaEstado;
    }

    public void setReservaEstado(ReservaEstado reservaEstado) {
        this.reservaEstado = reservaEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return id.equals(reserva.id) && livro.equals(reserva.livro) && cliente.equals(reserva.cliente) && feitaEm.equals(reserva.feitaEm) && terminaEm.equals(reserva.terminaEm) && reservaEstado == reserva.reservaEstado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, livro, cliente, feitaEm, terminaEm, reservaEstado);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", livro=" + livro +
                ", cliente=" + cliente +
                ", feitaEm=" + feitaEm +
                ", terminaEm=" + terminaEm +
                ", estado=" + reservaEstado +
                '}';
    }
}
