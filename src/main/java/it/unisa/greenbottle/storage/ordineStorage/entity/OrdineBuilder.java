package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine.StatoSpedizione;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdineBuilder {
  private float prezzo;
  private StatoSpedizione stato;
  private boolean isRitiro;
  private String carta;
  private boolean isSupporto;
  private String descrizione;
  private Set<Composizione> composizioni = new HashSet<>();
  private Indirizzo indirizzo;
  private Cliente cliente;

  public OrdineBuilder() {
  }

  public OrdineBuilder prezzo(float prezzo) {
    this.prezzo = prezzo;
    return this;
  }

  public OrdineBuilder stato(StatoSpedizione stato) {
    this.stato = stato;
    return this;
  }

  public OrdineBuilder isRitiro(boolean isRitiro) {
    this.isRitiro = isRitiro;
    return this;
  }

  public OrdineBuilder carta(String carta) {
    this.carta = carta;
    return this;
  }

  public OrdineBuilder isSupporto(boolean isSupporto) {
    this.isSupporto = isSupporto;
    return this;
  }

  public OrdineBuilder descrizione(String descrizione) {
    this.descrizione = descrizione;
    return this;
  }

  public OrdineBuilder composizione(Composizione composizione) {
    this.composizioni.add(composizione);
    return this;
  }

  public OrdineBuilder composizioni(Set<Composizione> composizioni) {
    this.composizioni = composizioni;
    return this;
  }

  public OrdineBuilder indirizzo(Indirizzo indirizzo) {
    this.indirizzo = indirizzo;
    return this;
  }

  public OrdineBuilder cliente(Cliente cliente) {
    this.cliente = cliente;
    return this;
  }

  public Ordine build() {
    Ordine ordine = new Ordine();
    ordine.setPrezzo(prezzo);
    ordine.setStato(stato);
    ordine.setRitiro(isRitiro);
    ordine.setCarta(carta);
    ordine.setSupporto(isSupporto);
    ordine.setDescrizione(descrizione);
    ordine.setComposizioni(composizioni);
    ordine.setIndirizzo(indirizzo);
    ordine.setCliente(cliente);
    return ordine;
  }
}

