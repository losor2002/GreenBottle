package it.unisa.greenbottle.controller.accessoControl.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe LoginForm, utilizzata per la validazione dei dati inseriti dal cliente nel form di login.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginForm {

  @NotBlank(message = "Email vuota")
  @Email(message = "Formato email errato")
  @Size(min = 6, max = 319, message = "dimensione email errata")
  private String email;

  @NotBlank(message = "password vuota")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
      message =
          "La password deve contenere almeno una lettera minuscola, una maiuscola, un numero, "
              + "un carattere speciale ( @, #, $, %, ^, &, +, = ) "
              + "e avere una lunghezza minima di 8 caratteri."
  )
  private String password;
}
