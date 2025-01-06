package it.unisa.greenbottle.storage.accessoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true, length = 319)
  private String email;
  @Column(nullable = false)
  private String password;

  @Builder
  public Admin(String email, String password) {
    this.email = email;
    this.password = password;
  }

  /*
  public List<Ordine> getOrdiniApprovati() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

   */
  /*
    public void addOrdineApprovato(Ordine o, Corriere c) {
      throw new UnsupportedOperationException("Not supported yet.");
    }

   */

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Admin admin = (Admin) o;
    return Objects.equals(id, admin.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Admin{"
        + "id=" + id
        + ", email='" + email + '\''
        + ", password='" + password + '\''
        + '}';
  }
}
