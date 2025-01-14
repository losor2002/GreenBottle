package it.unisa.greenbottle.storage.ordineStorage.entity;

import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import java.util.HashSet;
import java.util.Set;

public class OrdineDirector {

  public static Ordine createEmptyOrdine(Indirizzo indirizzo, Cliente cliente) {
    return new OrdineBuilder()
        .prezzo(0.0f)
        .stato(Ordine.StatoSpedizione.ELABORAZIONE)
        .isRitiro(false)
        .carta("0000-0000-0000-0000")
        .isSupporto(false)
        .descrizione("Default description")
        .indirizzo(indirizzo)
        .cliente(cliente)
        .composizioni(new HashSet<>())
        .build();
  }

  public static Ordine createOrdineConSupporto(float prezzoTotale, boolean isRitiro, String carta,
                                               String descrizione, Indirizzo indirizzo,
                                               Cliente cliente,
                                               Set<Composizione> composizioni) {
    Ordine ordine = new OrdineBuilder()
        .prezzo(prezzoTotale)
        .isRitiro(isRitiro)
        .carta(carta)
        .isSupporto(true)
        .descrizione(descrizione)
        .indirizzo(indirizzo)
        .cliente(cliente)
        .build();
    for (Composizione composizione : composizioni) {
      composizione.setOrdine(ordine);
    }
    ordine.setComposizioni(composizioni);
    return ordine;
  }

  public static Ordine createOrdine(float prezzo, boolean isRitiro, String carta,
                                    Indirizzo indirizzo, Cliente cliente,
                                    Set<Composizione> composizioni) {
    Ordine ordine = new OrdineBuilder()
        .prezzo(prezzo)
        .stato(Ordine.StatoSpedizione.ACCETTATO)
        .isRitiro(isRitiro)
        .carta(carta)
        .isSupporto(false)
        .descrizione("Default description")
        .indirizzo(indirizzo)
        .cliente(cliente)
        .build();
    for (Composizione composizione : composizioni) {
      composizione.setOrdine(ordine);
    }
    ordine.setComposizioni(composizioni);
    return ordine;
  }
}
