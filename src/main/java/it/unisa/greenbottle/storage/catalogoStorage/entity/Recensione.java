package it.unisa.greenbottle.storage.catalogoStorage.entity;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta una recensione di un prodotto nel sistema GreenBottle.
 * Ogni recensione contiene una descrizione del prodotto, un voto espresso dall'utente,
 * il prodotto e cliente associati.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Recensione {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // Identificativo univoco della recensione

  @Column(nullable = false, length = 1024)
  private String descrizione; // Descrizione della recensione

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private VotoRecensione voto; // Voto dato alla recensione (da 1 a 5)

  @ManyToOne
  private Prodotto prodotto; // Prodotto a cui è associata la recensione

  @ManyToOne
  private Cliente cliente; // Cliente che ha scritto la recensione

  /**
   * Costruttore per creare una recensione con descrizione, voto, prodotto e cliente specifici.
   *
   * @param descrizione Descrizione della recensione
   * @param voto        Voto dato alla recensione
   * @param prodotto    Prodotto a cui è associata la recensione
   * @param cliente     Cliente che ha scritto la recensione
   */
  public Recensione(String descrizione, VotoRecensione voto, Prodotto prodotto, Cliente cliente) {
    this.descrizione = descrizione;
    this.voto = voto;
    this.prodotto = prodotto;
    this.cliente = cliente;
  }

  /**
   * Enum che rappresenta i possibili voti per una recensione.
   * Ogni valore è associato a un numero da 1 a 5.
   */
  public enum VotoRecensione {
    UNO(1),
    DUE(2),
    TRE(3),
    QUATTRO(4),
    CINQUE(5);

    private final int value;

    VotoRecensione(int value) {
      this.value = value;
    }

    /**
     * Restituisce il valore dell'enum corrispondente al numero dato.
     *
     * @param value Il numero associato al voto
     * @return Il voto corrispondente, oppure null se il valore non è valido
     */
    static VotoRecensione fromValue(int value) {
      for (VotoRecensione voto : VotoRecensione.values()) {
        if (voto.value == value) {
          return voto;
        }
      }
      return null;
    }
  }
}
