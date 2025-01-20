package it.unisa.greenbottle.storage.accessoStorage.entity;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta un cliente del sistema GreenBottle.
 * Include informazioni personali, credenziali di accesso e dettagli sul risparmio e
 * sull'uso del servizio.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"img"})
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Identificativo univoco del cliente

  @Column(nullable = false, unique = true, length = 319)
  private String email; // Email del cliente

  @Column(nullable = false)
  private String password; // Password del cliente

  @Column(length = 30)
  private String nome; // Nome del cliente

  @Column(length = 30)
  private String cognome; // Cognome del cliente

  private int bottiglie; // Numero di bottiglie salvate dal cliente

  @Column(nullable = false, precision = 2)
  private float risparmio; // Risparmio totale ottenuto dal cliente

  @Lob
  private byte[] img; // Immagine di profilo del cliente

  private Timestamp sottoscrizione; // Data e ora di sottoscrizione all'abbonamento

  @ManyToOne
  private Abbonamento abbonamento; // Abbonamento associato al cliente


  /**
   * Costruttore di un cliente.
   *
   * @param email          Email del cliente
   * @param password       Password del cliente
   * @param nome           Nome del cliente
   * @param cognome        Cognome del cliente
   * @param bottiglie      Numero di bottiglie salvate dal cliente
   * @param risparmio      Risparmio totale ottenuto dal cliente
   * @param img            Immagine di profilo del cliente
   * @param sottoscrizione Data e ora di sottoscrizione all'abbonamento
   */
  public Cliente(String email, String password, String nome, String cognome, int bottiglie,
                 float risparmio, byte[] img, Timestamp sottoscrizione) {
    this.email = email;
    this.password = password;
    this.nome = nome;
    this.cognome = cognome;
    this.bottiglie = bottiglie;
    this.risparmio = risparmio;
    this.img = img;
    this.sottoscrizione = sottoscrizione;
  }
}
