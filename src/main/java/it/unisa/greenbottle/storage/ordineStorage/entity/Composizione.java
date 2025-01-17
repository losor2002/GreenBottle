package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Composizione {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ordine_id", nullable = false)
  private Ordine ordine;

  @ManyToOne
  @JoinColumn(name = "prodotto_id", nullable = false)
  private Prodotto prodotto;

  @Column(nullable = false)
  private int quantita;

  @PrePersist
  public void updateQuantitaProdotto() {
    if (prodotto.getQuantita() < quantita) {
      throw new IllegalArgumentException(
          "Quantità richiesta per " + prodotto.getNome() + " non disponibile."
      );
    }
    prodotto.setQuantita(prodotto.getQuantita() - quantita);

  }

  public Composizione(Prodotto prodotto, int quantita) { //TODO: Aggiungere Ordine.
    this.prodotto = prodotto;
    this.quantita = quantita;
  }

  @Override
  public String toString() {
    return "Composizione{"
        +
        "id=" + id
        + ", ordineId=" + (ordine != null ? ordine.getId() : "null")
        + ", prodotto=" + (prodotto != null ? prodotto.getNome() : "null")
        + ", quantita=" + quantita
        + '}';
  }
}

