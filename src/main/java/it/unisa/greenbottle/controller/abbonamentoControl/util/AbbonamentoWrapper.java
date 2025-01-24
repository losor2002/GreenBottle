package it.unisa.greenbottle.controller.abbonamentoControl.util;

import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Wrapper per l'abbonamento, contiene l'abbonamento e la lista delle disposizioni.
 */
// do not lombok
public class AbbonamentoWrapper implements Serializable {
  private static final long serialVersionUID = 1L;
  private Abbonamento abbonamento;
  private List<Disposizione> disposizioni;

  public AbbonamentoWrapper(Abbonamento abbonamento, List<Disposizione> disposizioni) {
    this.abbonamento = abbonamento;
    this.disposizioni = disposizioni;
  }

  public Abbonamento getAbbonamento() {
    return abbonamento;
  }

  public List<Disposizione> getDisposizione() {
    return disposizioni;
  }

  public Long getId() {
    return abbonamento.getId();
  }

  public Abbonamento.TipoAbbonamento getTipo() {
    return abbonamento.getTipo();
  }

  public Abbonamento.FrequenzaAbbonamento getFrequenza() {
    return abbonamento.getFrequenza();
  }

  public Abbonamento.RinnovoAbbonamento getRinnovo() {
    return abbonamento.getRinnovo();
  }

  public float getPrezzo() {
    return abbonamento.getPrezzo();
  }

  /**
   * Restituisce una mappa con i prodotti dell'abbonamento e le relative quantità.
   */
  public HashMap<Prodotto, Integer> getProdottiAbbonamento() {
    HashMap<Prodotto, Integer> mappa = new HashMap<>();
    for (Disposizione disposizione : disposizioni) {
      Prodotto chiave = disposizione.getProdotto();
      Integer valore = disposizione.getQuantita();
      mappa.put(chiave, valore);
    }
    return mappa;
  }

  @Override
  public String toString() {
    return "AbbonamentoWrapper{"
        + "id=" + abbonamento.getId()
        + ", tipo=" + abbonamento.getTipo()
        + ", frequenza=" + abbonamento.getFrequenza()
        + ", rinnovo=" + abbonamento.getRinnovo()
        + ", prodottiAbbonamento=" + getProdottiAbbonamento()
        + '}';

  }
}
