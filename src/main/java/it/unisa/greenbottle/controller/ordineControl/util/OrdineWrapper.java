package it.unisa.greenbottle.controller.ordineControl.util;

import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class OrdineWrapper implements Serializable {
  private static final long serialVersionUID = 1L;

  private Ordine o;
  private List<Composizione> c;

  public OrdineWrapper(Ordine o, List<Composizione> c) {
    this.o = o;
    this.c = c;
  }

  public Long getId() {
    return o.getId();
  }

  public String getDescrizione() {
    return o.getDescrizione();
  }

  public float getPrezzo() {
    return o.getPrezzo();
  }

  public Ordine.StatoSpedizione getStato() {
    return o.getStato();
  }

  public boolean isRitiro() {
    return o.isRitiro();
  }

  public boolean isSupporto() {
    return o.isSupporto();
  }

  public String getNomeCliente() {
    return o.getCliente().getNome();
  }

  public String getCognomeCliente() {
    return o.getCliente().getCognome();
  }

  public String getEmailCliente() {
    return o.getCliente().getEmail();
  }

  public Timestamp getData() {
    return o.getData();
  }

  public String getIndirizzo() {
    Indirizzo i = o.getIndirizzo();
    return i.getVia() + " " + i.getCivico() + " " + i.getCap() + " " + i.getCitta() + " "
        + i.getProvincia();
  }

  public HashMap<Prodotto, Integer> getProdottiOrdine() {
    HashMap<Prodotto, Integer> mappa = new HashMap<>();
    for (Composizione composizione : c) {
      Prodotto chiave = composizione.getProdotto();
      Integer valore = composizione.getQuantita();
      mappa.put(chiave, valore);
    }
    return mappa;
  }

  @Override
  public String toString() {
    return "OrdineWrapper{"
        + "id=" + getId()
        + ", descrizione='" + getDescrizione() + '\''
        + ", prezzo=" + getPrezzo()
        + ", stato=" + getStato()
        + ", ritiro=" + isRitiro()
        + ", supporto=" + isSupporto()
        + ", nomeCliente='" + getNomeCliente() + '\''
        + ", cognomeCliente='" + getCognomeCliente() + '\''
        + ", emailCliente='" + getEmailCliente() + '\''
        + ", indirizzo='" + getIndirizzo() + '\''
        + ", prodottiOrdine=" + getProdottiOrdine()
        + '}';
  }

}
