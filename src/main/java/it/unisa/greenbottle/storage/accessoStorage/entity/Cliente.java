package it.unisa.greenbottle.storage.accessoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;
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
  private byte[] img; // spring-content-jpa se vogliamo usare BLOB (sicuramente no)
  private Timestamp sottoscrizione;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public int getBottiglie() {
    return bottiglie;
  }

  public void setBottiglie(int bottiglie) {
    this.bottiglie = bottiglie;
  }

  public float getRisparmio() {
    return risparmio;
  }

  public void setRisparmio(float risparmio) {
    this.risparmio = risparmio;
  }

  public byte[] getImg() {
    return img;
  }

  public void setImg(byte[] img) {
    this.img = img;
  }

  public Timestamp getSottoscrizione() {
    return sottoscrizione;
  }

  public void setSottoscrizione(Timestamp sottoscrizione) {
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
