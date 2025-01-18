package it.unisa.greenbottle.controller.areaPersonaleControl.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataForm {

  @NotBlank(message = "Data vuota")
  @Pattern(
      regexp = "^2\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
      message = "Data di inizio non valida."
  )
  private String startDate;

  @NotBlank(message = "Data vuota")
  @Pattern(
      regexp = "^2\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
      message = "Data di fine non valida."
  )
  private String endDate;

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }
}
