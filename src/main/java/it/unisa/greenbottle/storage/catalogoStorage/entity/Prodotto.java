package it.unisa.greenbottle.storage.catalogoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Prodotto {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 256)
  private String nome;
  @Column(nullable = false, length = 1024)
  private String desc;
  @Column(nullable = false)
  private byte[] img;
  @Column(nullable = false, length = 7, precision = 2)
  private float prezzo;
  @Column(nullable = false)
  private int quantita;

  public Prodotto(String nome, String desc, byte[] img, float prezzo, int quantita) {
    this.nome = nome;
    this.desc = desc;
    this.img = img;
    this.prezzo = prezzo;
    this.quantita = quantita;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Prodotto prodotto = (Prodotto) o;
    return Objects.equals(id, prodotto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Prodotto{"
        + "id=" + id
        + ", nome='" + nome + '\''
        + ", desc='" + desc + '\''
        + ", img=" + Arrays.toString(img)
        + ", prezzo=" + prezzo
        + ", quantita=" + quantita
        + '}';
  }
}
