package it.unisa.greenbottle.storage.ordineStorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta una composizione di un ordine, che associa un prodotto a una quantità.
 * Ogni composizione fa parte di un ordine specifico.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "ordine")
public class Composizione {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // Identificativo univoco della composizione

  @ManyToOne
  @JoinColumn(name = "ordine_id", nullable = false)
  @JsonIgnore
  private Ordine ordine; // Ordine a cui è associata questa composizione

  @ManyToOne
  @JoinColumn(name = "prodotto_id", nullable = false)
  private Prodotto prodotto; // Prodotto associato alla composizione

  @Column(nullable = false)
  private int quantita; // Quantità del prodotto nell'ordine

  /**
   * Metodo chiamato prima del persist per aggiornare la quantità del prodotto.
   * Se la quantità richiesta è maggiore della quantità disponibile del prodotto,
   * solleva un'eccezione.
   */
  @PrePersist
  public void updateQuantitaProdotto() {
    if (prodotto.getQuantita() < quantita) {
      throw new IllegalArgumentException(
          "Quantità richiesta per " + prodotto.getNome() + " non disponibile."
      );
    }
    prodotto.setQuantita(
        prodotto.getQuantita() - quantita); // Decrementa la quantità disponibile del prodotto
  }

  /**
   * Costruttore per creare una composizione con il prodotto e la quantità.
   *
   * @param prodotto Prodotto associato alla composizione
   * @param quantita Quantità del prodotto
   */
  public Composizione(Prodotto prodotto, int quantita) {
    this.prodotto = prodotto;
    this.quantita = quantita;
  }

  /**
   * Costruttore per creare una composizione con il prodotto, la quantità e l'ordine associato.
   *
   * @param prodotto Prodotto associato alla composizione
   * @param quantita Quantità del prodotto
   * @param ordine   Ordine a cui appartiene questa composizione
   */
  public Composizione(Prodotto prodotto, int quantita, Ordine ordine) {
    this.prodotto = prodotto;
    this.quantita = quantita;
    this.ordine = ordine;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Composizione that = (Composizione) o;
    return id != null && id.equals(that.id); // Confronta per ID
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0; // Usa l'ID come base per hashCode
  }

}
