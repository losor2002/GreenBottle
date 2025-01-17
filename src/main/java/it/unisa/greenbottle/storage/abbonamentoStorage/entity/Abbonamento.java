package it.unisa.greenbottle.storage.abbonamentoStorage.entity;

import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
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

