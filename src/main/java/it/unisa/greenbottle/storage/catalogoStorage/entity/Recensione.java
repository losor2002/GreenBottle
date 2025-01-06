package it.unisa.greenbottle.storage.catalogoStorage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Recensione {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(nullable = false, length = 1024)
  private String desc;
  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private VotoRecensione voto;

  public Recensione(String desc, VotoRecensione voto) {
    this.desc = desc;
    this.voto = voto;
  }

  @Override
  public String toString() {
    return "Recensione{"
        + "id=" + id
        + ", desc='" + desc + '\''
        + ", voto=" + voto
        + '}';
  }

  public enum VotoRecensione {
    UNO(1),
    DUE(2),
    TRE(3),
    QUATTRO(4),
    CINQUE(5);

    private final int value;

    VotoRecensione(int value) {
      this.value = value;
    }

    static VotoRecensione fromValue(int value) {
      for (VotoRecensione voto : VotoRecensione.values()) {
        if (voto.value == value) {
          return voto;
        }
      }
      return null;
    }
  }

}

