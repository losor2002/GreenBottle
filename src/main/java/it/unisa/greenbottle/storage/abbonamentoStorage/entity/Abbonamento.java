package it.unisa.greenbottle.storage.abbonamentoStorage.entity;

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
public class Abbonamento {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column()
  @Enumerated(EnumType.STRING)

  private TipoAbbonamento tipo;

  @Column()
  @Enumerated(EnumType.STRING)

  private RinnovoAbbonamento rinnovo;

  @Enumerated(EnumType.STRING)
  @Column()

  private FrequenzaAbbonamento frequenza;

  public Abbonamento(TipoAbbonamento tipo, RinnovoAbbonamento rinnovo,
                     FrequenzaAbbonamento frequenza) {
    this.tipo = tipo;
    this.rinnovo = rinnovo;
    this.frequenza = frequenza;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TipoAbbonamento getTipo() {
    return tipo;
  }

  public void setTipo(
      TipoAbbonamento tipo) {
    this.tipo = tipo;
  }

  public RinnovoAbbonamento getRinnovo() {
    return rinnovo;
  }

  public void setRinnovo(
      RinnovoAbbonamento rinnovo) {
    this.rinnovo = rinnovo;
  }

  public FrequenzaAbbonamento getFrequenza() {
    return frequenza;
  }

  public void setFrequenza(
      FrequenzaAbbonamento frequenza) {
    this.frequenza = frequenza;
  }

  @Override
  public String toString() {
    return "Abbonamento{"
        + "id=" + id
        + ", tipo=" + tipo
        + ", rinnovo=" + rinnovo
        + ", frequenza=" + frequenza
        + '}';
  }

  public enum TipoAbbonamento {
    BRONZE,
    SILVER,
    GOLD
  }

  public enum RinnovoAbbonamento {
    MENSILE,
    BIMESTRALE,
    SEMESTRALE,
    ANNUALE
  }

  public enum FrequenzaAbbonamento {
    GIORNALIERO,
    SETTIMANALE,
    MENSILE
  }

}

