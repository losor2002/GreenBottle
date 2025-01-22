package it.unisa.greenbottle.controller.abbonamentoControl.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AbbonamentoForm {

  private Long id;

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
          message = "CVV non valido."
  )
  private String CVV;
  @NotNull
  @Pattern(
          regexp = "^[A-Za-zÀ-ÿ\\s']{2,20}$",
          message = "Nome del titolare della carta non valido."
  )
  private String nomeTitolare;

  public Long getId() {
    return id;
  }

  public String getNumeroCarta() {return numeroCarta;}

  public String getDataScadenza() {return dataScadenza;}

  public String getCVV() {return CVV;}

  public String getNomeTitolare() {return nomeTitolare;}
}