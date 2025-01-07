package it.unisa.greenbottle.storage.catalogoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
  private String descrizione;
  @Column(nullable = false)
  private byte[] img;
  @Column(nullable = false, length = 7, precision = 2)
  private float prezzo;
  @Column(nullable = false)
  private int quantita;
  @ManyToOne
  private Categoria categoria;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescrizione() {
    return descrizione;
  }

  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  public byte[] getImg() {
    return img;
  }

  public void setImg(byte[] img) {
    this.img = img;
  }

  public float getPrezzo() {
    return prezzo;
  }

  public void setPrezzo(float prezzo) {
    this.prezzo = prezzo;
  }

  public int getQuantita() {
    return quantita;
  }

  public void setQuantita(int quantita) {
    this.quantita = quantita;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public Prodotto(String nome, String descrizione, byte[] img, float prezzo, int quantita) {
    this.nome = nome;
    this.descrizione = descrizione;
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
        + ", descrizione='" + descrizione + '\''
        + ", img=" + Arrays.toString(img)
        + ", prezzo=" + prezzo
        + ", quantita=" + quantita
        + '}';
  }
}
