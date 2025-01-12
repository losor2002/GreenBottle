package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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


  public Composizione(Prodotto prodotto, int quantita) {
    this.prodotto = prodotto;
    this.quantita = quantita;
  }

  @Override
  public String toString() {
    return "Composizione{" +
        "id=" + id +
        ", ordineId=" + (ordine != null ? ordine.getId() : "null") +
        ", prodotto=" + (prodotto != null ? prodotto.getNome() : "null") +
        ", quantita=" + quantita +
        '}';
  }
}

