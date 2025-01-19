package it.unisa.greenbottle.controller.ordineControl.util;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Setter
public class SessionCarrello {

  private Map<Long, Integer> carrello = new HashMap<>();

  @Autowired
  private ProdottoDao prodottoDao;

  public Optional<Map<Long, Integer>> getCarrello() {
    return carrello != null ? Optional.of(carrello) : Optional.empty();
  }


  public boolean addToCarrello(Long prodotto, Integer quantita) {
    if (prodotto == null || quantita == null || quantita <= 0) {
      return false;
    }
    carrello.put(prodotto, carrello.getOrDefault(prodotto, 0) + quantita);
    return true;
  }

  public boolean removeFromCarrello(Long prodotto) {
    if (prodotto == null || !carrello.containsKey(prodotto)) {
      return false;
    }
    carrello.remove(prodotto);
    return true;
  }

  public boolean clearCarrello() {
    if (!carrello.isEmpty()) {
      carrello.clear();
      return true;
    }
    return false;
  }

}