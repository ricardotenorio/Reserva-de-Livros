package ricardotenorio.reserva.de.livros.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Cliente extends Entidade {
    private @Id @GeneratedValue Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String senha;

    @OneToOne
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    @JsonIgnoreProperties("cliente")
    private Token token;

    @OneToMany(mappedBy = "cliente")
    private Set<Reserva> reservas;

    public Cliente() {}

    public Cliente(String nome, String sobrenome, String cpf, String senha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id) && nome.equals(cliente.nome) && sobrenome.equals(cliente.sobrenome) && cpf.equals(cliente.cpf) && senha.equals(cliente.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobrenome, cpf, senha);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
