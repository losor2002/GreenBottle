package it.unisa.greenbottle.controller.catalogoControl.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroForm {
  @Min(value = 1, message = "IdCategoria deve essere maggiore o uguale a 1")
  private Long idCategoria;

  @Min(value = 1, message = "Prezzo minimo deve essere maggiore o uguale a 1")
  private Float prezzoMin;

  @Max(value = 999, message = "Prezzo massimo deve essere minore o uguale a 999")
  @Min(value = 1, message = "Prezzo massimo deve essere maggiore o uguale a 1")
  private Float prezzoMax;

  @Min(value = 1, message = "Media deve essere almeno 1.0")
  @Max(value = 5, message = "Media deve essere al massimo 5.0")
  private Float media;

  @AssertTrue(message = "Prezzo minimo non può essere maggiore del prezzo massimo")
  public boolean isPrezzoMinMaxValid() {
    if (prezzoMin != null && prezzoMax != null) {
      return prezzoMin <= prezzoMax;
    }
    return true;
  }
}
