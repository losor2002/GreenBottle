package it.unisa.greenbottle.storage.ordineStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
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

  public Ordine(float prezzo, StatoSpedizione stato, boolean isRitiro, String carta,
                boolean isSupporto, String descrizione) {
    this.prezzo = prezzo;
    this.stato = stato;
    this.isRitiro = isRitiro;
    this.carta = carta;
    this.isSupporto = isSupporto;
    this.descrizione = descrizione;
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
