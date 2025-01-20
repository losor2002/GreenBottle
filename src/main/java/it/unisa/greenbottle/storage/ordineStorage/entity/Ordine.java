package it.unisa.greenbottle.storage.ordineStorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unisa.greenbottle.storage.accessoStorage.entity.Admin;
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
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta un ordine effettuato da un cliente nel sistema GreenBottle.
 * Ogni ordine può includere diversi prodotti e informazioni di spedizione.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "composizioni")
public class Ordine {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // Identificativo univoco dell'ordine

  @Column(nullable = false, length = 7, precision = 2)
  private float prezzo; // Prezzo totale dell'ordine

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatoSpedizione stato; // Stato attuale della spedizione

  @Column(nullable = false)
  private boolean isRitiro; // Indica se l'ordine è per il ritiro o la spedizione

  @Column(nullable = false, length = 19)
  private String carta; // Numero della carta di pagamento

  @Column(nullable = false)
  private boolean isSupporto; // Indica se l'ordine è supportato da un servizio

  @Column(nullable = false, length = 300)
  private String descrizione; // Descrizione dell'ordine

  @Column(nullable = false)
  private Timestamp data; // Data e ora di creazione dell'ordine

  @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  // Lista di composizioni (prodotti) nell'ordine
  private Set<Composizione> composizioni = new HashSet<>();

  @ManyToOne(optional = false)
  private Cliente cliente; // Cliente che ha effettuato l'ordine

  @ManyToOne
  private Indirizzo indirizzo; // Indirizzo di spedizione (se applicabile)

  @ManyToOne
  private Admin admin; // Amministratore che gestisce l'ordine

  /**
   * Costruttore per creare un ordine con i dettagli specifici.
   *
   * @param prezzo      Prezzo totale dell'ordine
   * @param stato       Stato attuale della spedizione
   * @param isRitiro    Indica se l'ordine è per il ritiro
   * @param carta       Numero della carta di pagamento
   * @param isSupporto  Indica se l'ordine è supportato
   * @param descrizione Descrizione dell'ordine
   * @param data        Data e ora di creazione dell'ordine
   * @param cliente     Cliente che ha effettuato l'ordine
   * @param admin       Amministratore che gestisce l'ordine
   */
  public Ordine(float prezzo, StatoSpedizione stato, boolean isRitiro, String carta,
                boolean isSupporto, String descrizione, Timestamp data,
                Cliente cliente, Admin admin) {
    this.prezzo = prezzo;
    this.stato = stato;
    this.isRitiro = isRitiro;
    this.carta = carta;
    this.isSupporto = isSupporto;
    this.descrizione = descrizione;
    this.data = data;
    this.cliente = cliente;
    this.admin = admin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ordine ordine = (Ordine) o;
    return id != null && id.equals(ordine.id); // Confronta per ID
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0; // Usa l'ID come base per hashCode
  }

  /**
   * Enum che rappresenta i possibili stati di spedizione di un ordine.
   * I valori possibili sono:
   * - ELABORAZIONE
   * - ACCETTATO
   * - RIFIUTATO
   * - SPEDITO
   * - CONSEGNATO
   */
  public enum StatoSpedizione {
    ELABORAZIONE, // Ordine in fase di elaborazione
    ACCETTATO,    // Ordine accettato
    RIFIUTATO,    // Ordine rifiutato
    SPEDITO,      // Ordine spedito
    CONSEGNATO    // Ordine consegnato
  }
}
