package it.unisa.greenbottle.controller.ordineControl.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Classe che rappresenta il form per la creazione di un ordine.
 */
@AllArgsConstructor
@Data
public class OrdineForm {
  @NotNull
  @Pattern(
      regexp = "^(\\d{4}[-\\s]?){3}\\d{4}$",
      message = "Numero di carta non valido."
  )
  private String numeroCarta;

  @NotNull
  @Pattern(
      regexp = "^(0?[1-9]|1[0-2])[/\\-]\\d{2}(\\d{2})?$",
      message = "Data di scadenza della carta non valida."
  )
  private String dataScadenza;

  @NotNull
  @Pattern(
      regexp = "^\\d{3,4}$",
      message = "cvv non valido."
  )
  private String cvv;

  @NotNull
  @Pattern(
      regexp = "^[A-Za-zÀ-ÿ\\s']{2,20}$",
      message = "Nome del titolare della carta non valido."
  )
  private String nomeTitolare;

  @NotNull
  @Positive(message = "Indirizzo non valido.")
  private Long indirizzo;

  @NotNull(message = "isSupporto non valido.")
  @BooleanFlag
  private Boolean isSupporto;

  @NotNull(message = "isRitiro non valido.")
  @BooleanFlag
  private Boolean isRitiro;

  @Size(max = 300, message = "Descrizione supporto troppo lunga.")
  private String descrizioneSupporto;

  public Boolean getIsSupporto() {
    return isSupporto == null ? Boolean.FALSE : isSupporto;
  }

  public String getDescrizioneSupporto() {
    return descrizioneSupporto == null ? "" : descrizioneSupporto;
  }
}
