package ricardotenorio.reserva.de.livros.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Token extends Entidade{
    @Id
    @GeneratedValue
    private Long id;

    private String hash;
    private Date criadoEm;
    private Date terminaEm;

    @OneToOne(mappedBy = "token")
    @JsonIgnoreProperties("token")
    private Cliente cliente;

    public Token() {}

    public Token(String hash) {
        this.hash = hash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Date getTerminaEm() {
        return terminaEm;
    }

    public void setTerminaEm(Date terminaEm) {
        this.terminaEm = terminaEm;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return id.equals(token.id) && hash.equals(token.hash) && criadoEm.equals(token.criadoEm) && terminaEm.equals(token.terminaEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash, criadoEm, terminaEm);
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", value='" + hash + '\'' +
                ", criadoEm=" + criadoEm +
                ", terminaEm=" + terminaEm +
                '}';
    }
}
