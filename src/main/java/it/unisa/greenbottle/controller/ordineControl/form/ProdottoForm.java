package it.unisa.greenbottle.controller.ordineControl.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe che rappresenta il form per un prodotto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoForm {

  @NotNull
  @Positive(message = "id_Prodotto non valido")
  private Long idProdotto;
  @NotNull
  @Positive(message = "Quantità non valida.")
  private Integer quantita;

}
