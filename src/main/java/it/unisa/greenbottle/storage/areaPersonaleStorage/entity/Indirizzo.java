package it.unisa.greenbottle.storage.areaPersonaleStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Indirizzo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 256)
  private String via;
  @Column(nullable = false)
  private int civico;
  @Column(nullable = false, length = 256)
  private String citta;
  @Column(nullable = false, length = 2)
  private String provincia;
  @Column(nullable = false, length = 5)
  private String cap;

  public Indirizzo(Long id, String via, int civico, String citta, String provincia, String cap) {
    this.id = id;
    this.via = via;
    this.civico = civico;
    this.citta = citta;
    this.provincia = provincia;
    this.cap = cap;
  }

  public Indirizzo(String via, int civico, String citta, String provincia, String cap) {
    this.via = via;
    this.civico = civico;
    this.citta = citta;
    this.provincia = provincia;
    this.cap = cap;
  }

  @Override
  public String toString() {
    return "Indirizzo{"
        + "id=" + id
        + ", via='" + via + '\''
        + ", civico=" + civico
        + ", citta='" + citta + '\''
        + ", provincia='" + provincia + '\''
        + ", cap='" + cap + '\''
        + '}';
  }
}
