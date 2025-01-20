package it.unisa.greenbottle.storage.accessoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Rappresenta un amministratore del sistema GreenBottle.
 * Gli amministratori hanno accesso privilegiato per gestire il sistema.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Admin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Identificativo univoco dell'amministratore

  @Column(nullable = false, unique = true, length = 319)
  private String email; // Email dell'amministratore

  @Column(nullable = false)
  private String password; // Password dell'amministratore

  /**
   * Costruttore di un amministratore con parametri specifici.
   *
   * @param email    Email dell'amministratore
   * @param password Password dell'amministratore
   */
  @Builder
  public Admin(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
