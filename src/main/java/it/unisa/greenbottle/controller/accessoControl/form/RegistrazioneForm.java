package it.unisa.greenbottle.controller.accessoControl.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrazioneForm {

  @NotBlank(message = "Nome vuoto.")
  @Size(max = 30, message = "Dimensione Nome errata.")
  @Pattern(
      regexp = "^[a-zA-Z]{0,30}$",
      message =
          "Nome non e' rispettato il formato."
  )
  private String nome;


  @NotBlank(message = "Cognome vuoto.")
  @Size(max = 30, message = "Dimensione Cognome errata.")
  @Pattern(
      regexp = "^[a-zA-Z]{0,30}$",
      message =
          "Cognome non e' rispettato il formato."
  )
  private String cognome;

  @NotBlank(message = "Email vuota.")
  @Email(message = "Formato Email errato.")
  @Size(min = 6, max = 319, message = "Dimensione Email errata.")
  @Pattern(
      regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message =
          " "
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {

    this.email = email;

  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

}
