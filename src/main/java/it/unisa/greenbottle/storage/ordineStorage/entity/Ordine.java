package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ordine {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 7, precision = 2)
  private float prezzo;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatoSpedizione stato;

  @Column(nullable = false)
  private boolean isRitiro;

  @Column(nullable = false, length = 19)
  private String carta;

  @Column(nullable = false)
  private boolean isSupporto;

  @Column(nullable = false, length = 300)
  private String descrizione;

  @Column(nullable = false)
  private Timestamp data;

  @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Composizione> composizioni = new HashSet<>();

  @ManyToOne(optional = false)
  private Cliente cliente;

  @ManyToOne
  private Indirizzo indirizzo;

  protected Ordine(OrdineBuilder builder) {
    this.prezzo = builder.getPrezzo();
    this.stato = builder.getStato();
    this.isRitiro = builder.isRitiro();
    this.carta = builder.getCarta();
    this.isSupporto = builder.isSupporto();
    this.descrizione = builder.getDescrizione();
    this.composizioni =
        builder.getComposizioni() != null ? builder.getComposizioni() : new HashSet<>();
    this.indirizzo = builder.getIndirizzo();
    this.cliente = builder.getCliente(); // Updated
  }

  public Ordine(float prezzo, StatoSpedizione stato, boolean isRitiro, String carta,
                boolean isSupporto, String descrizione, Timestamp data,
                Cliente cliente) { // Updated
    this.prezzo = prezzo;
    this.stato = stato;
    this.isRitiro = isRitiro;
    this.carta = carta;
    this.isSupporto = isSupporto;
    this.descrizione = descrizione;
    this.data = data;
    this.cliente = cliente; // Updated
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ordine ordine = (Ordine) o;
    return Objects.equals(id, ordine.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Ordine{"
        + "id=" + id
        + ", prezzo=" + prezzo
        + ", stato=" + stato
        + ", isRitiro=" + isRitiro
        + ", carta='" + carta + '\''
        + ", isSupporto=" + isSupporto
        + ", descrizione='" + descrizione + '\''
        + ", data=" + data
        + ", cliente=" + cliente
        + '}';
  }

  public enum StatoSpedizione {
    ELABORAZIONE,
    ACCETTATO,
    RIFIUTATO,
    SPEDITO,
    CONSEGNATO
  }
}