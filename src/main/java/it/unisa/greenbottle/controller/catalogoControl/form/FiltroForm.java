package it.unisa.greenbottle.controller.catalogoControl.form;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltroForm {
  @Nullable
  @Size(min = 1)
  private String nome;
  @Nullable
  @Size(min = 1)
  private String categoria;
  @Nullable
  @Size(min = 1)
  private Float prezzoMin;
  @Nullable
  @Size(min = 1, max = 999)
  private Float prezzoMax;
  @Nullable
  @Size(min = 1, max = 5)
  private Float voto;
}
