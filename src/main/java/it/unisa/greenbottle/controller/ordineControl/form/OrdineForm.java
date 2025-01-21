package it.unisa.greenbottle.controller.ordineControl.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Getter
@Setter
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
      message = "Data di scadenza della carta non valida"
  )
  private String dataScadenza;

  @NotNull
  @Pattern(
      regexp = "^\\d{3,4}$",
      message = "CVV non valido"
  )
  private String CVV;

  @NotNull
  @Pattern(
      regexp = "^[A-Za-zÀ-ÿ\\s']{2,20}$",
      message = "Nome del titolare della carta non valido"
  )
  private String nomeTitolare;

  @NotNull
  @Positive(message = "Indirizzo non valido.")
  private Long indirizzo;

  @NotNull(message = "isSupporto non valido.")
  @BooleanFlag
  private Boolean isSupporto = false;

  @NotNull(message = "isRitiro non valido.")
  @BooleanFlag
  private Boolean isRitiro;

  @Size(max = 300, message = "descrizioneSupporto troppo lunga.")
  private String descrizioneSupporto = "";

  public String getNumeroCarta() {
    return numeroCarta;
  }

  public void setNumeroCarta(String numeroCarta) {
    this.numeroCarta = numeroCarta;
  }

  public String getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(String dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  public String getCVV() {
    return CVV;
  }

  public void setCVV(String CVV) {
    this.CVV = CVV;
  }

  public String getNomeTitolare() {
    return nomeTitolare;
  }

  public void setNomeTitolare(String nomeTitolare) {
    this.nomeTitolare = nomeTitolare;
  }

  public Long getIndirizzo() {
    return indirizzo;
  }

  public void setIndirizzo(Long indirizzo) {
    this.indirizzo = indirizzo;
  }

  public String getDescrizioneSupporto() {
    return descrizioneSupporto == null ? "" : descrizioneSupporto;
  }

}
