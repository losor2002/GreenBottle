package it.unisa.greenbottle.storage.accessoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
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
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true, length = 319)
  private String email;
  @Column(nullable = false)
  private String password;
  @Column(length = 30)
  private String nome;
  @Column(length = 30)
  private String cognome;
  private int bottiglie;
  @Column(nullable = false, precision = 2)
  private float risparmio;
  private byte[] img; // spring-content-jpa se vogliamo usare BLOB (sicuramente no)
  private Timestamp sottoscrizione;

  public Cliente(String email, String password, String nome, String cognome, int bottiglie,
                 float risparmio, byte[] img, Timestamp sottoscrizione) {
    this.email = email;
    this.password = password;
    this.nome = nome;
    this.cognome = cognome;
    this.bottiglie = bottiglie;
    this.risparmio = risparmio;
    this.img = img;
    this.sottoscrizione = sottoscrizione;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cliente cliente = (Cliente) o;
    return Objects.equals(id, cliente.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Cliente{"
        + "id=" + id
        + ", email='" + email + '\''
        + ", password='" + password + '\''
        + ", nome='" + nome + '\''
        + ", cognome='" + cognome + '\''
        + ", bottiglie=" + bottiglie
        + ", risparmio=" + risparmio
        + ", sottoscrizione=" + sottoscrizione
        + '}';
  }
}
