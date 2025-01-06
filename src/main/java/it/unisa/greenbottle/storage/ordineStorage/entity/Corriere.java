package it.unisa.greenbottle.storage.ordineStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Corriere {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 30)
  private String nome;
  @Column(nullable = false, length = 30)
  private String cognome;
  @Column(nullable = false, length = 10)
  private String telefono;

  public Corriere(String nome, String cognome, String telefono) {
    this.nome = nome;
    this.cognome = cognome;
    this.telefono = telefono;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Corriere corriere = (Corriere) o;
    return Objects.equals(id, corriere.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Corriere{"
        + "id=" + id
        + ", nome='" + nome + '\''
        + ", cognome='" + cognome + '\''
        + ", telefono='" + telefono + '\''
        + '}';
  }
}
