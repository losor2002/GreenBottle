package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Data
@ToString
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
  @Column(nullable = true, length = 300)
  private String descrizione;
  @ManyToOne
  private Indirizzo indirizzo;
  @ManyToOne
  private Cliente cliente;


  public enum StatoSpedizione {
    ELABORAZIONE,
    ACCETTATO,
    RIFIUTATO,
    SPEDITO,
    CONSEGNATO
  }
}
