package it.unisa.greenbottle.storage.accessoStorage.entity;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
@Data
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
  @Lob
  private byte[] img;
  private Timestamp sottoscrizione;
  @ManyToOne
  private Abbonamento abbonamento;

  public Cliente() {
  }

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
