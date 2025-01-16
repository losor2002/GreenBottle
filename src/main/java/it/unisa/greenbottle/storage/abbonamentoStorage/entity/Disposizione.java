package it.unisa.greenbottle.storage.abbonamentoStorage.entity;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Disposizione {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false)
  private int quantita;

  @ManyToOne
  private Prodotto prodotto;
  @ManyToOne
  private Abbonamento abbonamento;

  public Disposizione(int quantita) {
    this.quantita = quantita;
  }

  public Disposizione(int quantita, Prodotto prodotto, Abbonamento abbonamento) {
    this.quantita = quantita;
    this.prodotto = prodotto;
    this.abbonamento = abbonamento;
  }
}
