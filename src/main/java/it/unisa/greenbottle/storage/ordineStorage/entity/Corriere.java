package it.unisa.greenbottle.storage.ordineStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta un corriere nel sistema GreenBottle.
 * Il corriere è associato alla gestione della consegna degli ordini.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Corriere {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // Identificativo univoco del corriere

  @Column(nullable = false, length = 30)
  private String nome; // Nome del corriere

  @Column(nullable = false, length = 30)
  private String cognome; // Cognome del corriere

  @Column(nullable = false, length = 10)
  private String telefono; // Numero di telefono del corriere

  /**
   * Costruttore per creare un corriere con nome, cognome e numero di telefono.
   *
   * @param nome     Nome del corriere
   * @param cognome  Cognome del corriere
   * @param telefono Numero di telefono del corriere
   */
  public Corriere(String nome, String cognome, String telefono) {
    this.nome = nome;
    this.cognome = cognome;
    this.telefono = telefono;
  }
}
