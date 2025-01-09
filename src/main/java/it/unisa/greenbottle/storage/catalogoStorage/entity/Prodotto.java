package it.unisa.greenbottle.storage.catalogoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public final class Prodotto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 256)
  private String nome;
  @Column(nullable = false, length = 1024)
  private String descrizione;
  private byte[] img;
  @Column(nullable = false)
  private float prezzo;
  @Column(nullable = false)
  private int quantita = 0;
  @ManyToOne
  private Categoria categoria;

  public Prodotto(String nome, String descrizione, byte[] img, float prezzo, int quantita,
                  Categoria categoria) {
    this.nome = nome;
    this.descrizione = descrizione;
    this.img = img;
    this.prezzo = prezzo;
    this.quantita = quantita;
    this.categoria = categoria;
  }

}
