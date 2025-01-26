package it.unisa.greenbottle.controller.ordineControl.util;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Classe che rappresenta il carrello dell'utente in sessione.
 */
@Component
@SessionScope
@Setter
@Getter
public class SessionCarrello {

  private Map<Long, Integer> carrello = new HashMap<>();

  @Autowired
  private ProdottoDao prodottoDao;


  /**
   * Restituisce il carrello filtrato.
   *
   * @return il carrello filtrato
   */
  public Map<Prodotto, Integer> getRealCarrello() {
    if (carrello == null || carrello.isEmpty()) {
      return new HashMap<>();
    }

    Map<Prodotto, Integer> filteredCarrello = new HashMap<>();

    for (Map.Entry<Long, Integer> entry : carrello.entrySet()) {
      Long productId = entry.getKey();

      Optional<Prodotto> existingProduct = prodottoDao.findById(productId);

      existingProduct.ifPresent(prodotto -> filteredCarrello.put(prodotto, entry.getValue()));
    }

    return filteredCarrello;
  }

  /**
   * Aggiunge un prodotto al carrello.
   *
   * @param prodotto id del prodotto
   * @param quantita quantità del prodotto
   * @return true se l'operazione è andata a buon fine, false altrimenti
   */
  public boolean addToCarrello(Long prodotto, Integer quantita) {
    if (prodotto == null || quantita == null || quantita <= 0) {
      return false;
    }
    carrello.put(prodotto, carrello.getOrDefault(prodotto, 0) + quantita);
    return true;
  }

  /**
   * Rimuove un prodotto dal carrello.
   *
   * @param prodotto id del prodotto
   * @return true se l'operazione è andata a buon fine, false altrimenti
   */
  public boolean removeFromCarrello(Long prodotto) {
    if (prodotto == null || !carrello.containsKey(prodotto)) {
      return false;
    }
    carrello.remove(prodotto);
    return true;
  }

  /**
   * Svuota il carrello.
   *
   * @return true se l'operazione è andata a buon fine, false altrimenti
   */
  public boolean clearCarrello() {
    if (!carrello.isEmpty()) {
      carrello.clear();
      return true;
    }
    return false;
  }
}