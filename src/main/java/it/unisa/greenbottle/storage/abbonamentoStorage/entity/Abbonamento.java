package it.unisa.greenbottle.storage.abbonamentoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta un abbonamento nel sistema GreenBottle.
 * Gli abbonamenti includono un tipo, un rinnovo, una frequenza e un prezzo.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Abbonamento {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id; // Identificativo univoco dell'abbonamento

  @Column()
  @Enumerated(EnumType.STRING)
  private TipoAbbonamento tipo; // Tipo di abbonamento (es. BRONZE, SILVER, GOLD)

  @Column()
  @Enumerated(EnumType.STRING)
  private RinnovoAbbonamento rinnovo; // Modalità di rinnovo dell'abbonamento

  @Enumerated(EnumType.STRING)
  @Column()
  private FrequenzaAbbonamento frequenza; // Frequenza dell'abbonamento (es. giornaliero, mensile)

  @Column()
  private float prezzo; // Prezzo dell'abbonamento

  /**
   * Costruttore per creare un abbonamento con parametri specifici.
   *
   * @param tipo      Tipo di abbonamento
   * @param rinnovo   Modalità di rinnovo dell'abbonamento
   * @param frequenza Frequenza dell'abbonamento
   * @param prezzo    Prezzo dell'abbonamento
   */
  public Abbonamento(TipoAbbonamento tipo, RinnovoAbbonamento rinnovo,
                     FrequenzaAbbonamento frequenza, float prezzo) {
    this.tipo = tipo;
    this.rinnovo = rinnovo;
    this.frequenza = frequenza;
    this.prezzo = prezzo;
  }

  /**
   * Enum per rappresentare i tipi di abbonamento.
   */
  public enum TipoAbbonamento {
    BRONZE, // Abbonamento base
    SILVER, // Abbonamento intermedio
    GOLD    // Abbonamento premium
  }

  /**
   * Enum per rappresentare le modalità di rinnovo dell'abbonamento.
   */
  public enum RinnovoAbbonamento {
    MENSILE,      // Rinnovo ogni mese
    BIMESTRALE,   // Rinnovo ogni due mesi
    SEMESTRALE,   // Rinnovo ogni sei mesi
    ANNUALE       // Rinnovo annuale
  }

  /**
   * Enum per rappresentare la frequenza dell'abbonamento.
   */
  public enum FrequenzaAbbonamento {
    GIORNALIERO,  // Frequenza giornaliera
    SETTIMANALE,  // Frequenza settimanale
    MENSILE       // Frequenza mensile
  }
}
