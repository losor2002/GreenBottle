package it.unisa.greenbottle.storage.areaPersonaleStorage.entity;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
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

/**
 * Rappresenta un indirizzo di un cliente nel sistema GreenBottle.
 * Ogni indirizzo è associato a un cliente e include informazioni sulla via, civico,
 * città, provincia e CAP.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, exclude = {"cliente"})
public class Indirizzo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // Identificativo univoco dell'indirizzo

  @Column(nullable = false, length = 256)
  private String via; // Via dell'indirizzo

  @Column(nullable = false)
  private int civico; // Numero civico dell'indirizzo

  @Column(nullable = false, length = 256)
  private String citta; // Città dell'indirizzo

  @Column(nullable = false, length = 2)
  private String provincia; // Provincia dell'indirizzo

  @Column(nullable = false, length = 5)
  private String cap; // CAP dell'indirizzo

  @ManyToOne
  private Cliente cliente; // Cliente associato a questo indirizzo

  /**
   * Costruttore per creare un indirizzo con parametri specifici.
   *
   * @param via       Via dell'indirizzo
   * @param civico    Numero civico dell'indirizzo
   * @param citta     Città dell'indirizzo
   * @param provincia Provincia dell'indirizzo
   * @param cap       CAP dell'indirizzo
   * @param cliente   Cliente associato a questo indirizzo
   */
  public Indirizzo(String via, int civico, String citta, String provincia, String cap,
                   Cliente cliente) {
    this.via = via;
    this.civico = civico;
    this.citta = citta;
    this.provincia = provincia;
    this.cap = cap;
    this.cliente = cliente;
  }
}
