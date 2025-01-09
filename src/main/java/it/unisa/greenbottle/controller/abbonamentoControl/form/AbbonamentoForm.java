package it.unisa.greenbottle.controller.abbonamentoControl.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AbbonamentoForm {

  private Long id;

  public Long getId() {
    return id;
  }

  private void setId(Long id) {
    this.id = id;
  }
}
