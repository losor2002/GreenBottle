package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Composizione {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private int quantita;
  @ManyToOne
  private Ordine ordine;
  @ManyToOne
  private Prodotto prodotto;

  public Composizione(int quantita, Ordine ordine, Prodotto prodotto) {
    this.quantita = quantita;
    this.ordine = ordine;
    this.prodotto = prodotto;
  }

}
