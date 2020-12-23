package ricardotenorio.reserva.de.livros.entity;

public abstract class Entidade {
    private Long id;

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
