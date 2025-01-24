package it.unisa.greenbottle.controller.accessoControl.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe RegistrazioneForm,
 * utilizzata per la validazione dei dati inseriti dall'utente nel form di registrazione.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrazioneForm {

  @NotBlank(message = "Nome vuoto.")
  @Size(max = 30, message = "Dimensione Nome errata.")
  @Pattern(
      regexp = "^[a-zA-Z]{0,30}$",
      message =
          "Nome non rispetta il formato."
  )
  private String nome;

  @NotBlank(message = "Cognome vuoto.")
  @Size(max = 30, message = "Dimensione Cognome errata.")
  @Pattern(
      regexp = "^[a-zA-Z]{0,30}$",
      message =
          "Cognome non rispetta il formato."
  )
  private String cognome;

  @NotBlank(message = "Email vuota.")
  @Size(min = 6, max = 319, message = "Dimensione Email errata.")
  @Email(message = "Dimensione Email errata.")
  @Pattern(
      regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message =
          "Formato Email errato."
  )
  private String email;

  @NotBlank(message = "Password vuota.")
  @Size(max = 32, message = "Dimensione Password errata.")
  @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
      message =
          "La Password deve contenere almeno una lettera minuscola, una maiuscola, un numero, "
              + "un carattere speciale ( @, #, $, %, ^, &, +, =, !) "
              + "e avere una lunghezza minima di 8 caratteri."
  )
  private String password;
}
