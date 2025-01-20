package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/modifica-carrello")
public class ModificaCarrelloController {

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private SessionCarrello sessionCarrello;

  @Autowired
  private SessionCliente sessionCliente;

  @PostMapping("/{action}/{productId}")
  public ResponseEntity<?> update(@PathVariable String action, @PathVariable Long productId) {
    if (!List.of("add", "remove", "removeAll").contains(action)) {
      return ResponseEntity.badRequest().body(Map.of("message", "Azione non valida."));
    }

    Optional<Prodotto> prodottoOpt = prodottoDao.findProdottoById(productId);
    if (prodottoOpt.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("message", "Prodotto non trovato."));
    }

    Prodotto prodotto = prodottoOpt.get();


    Map<Long, Integer> carrello = sessionCarrello.getCarrello().orElse(new HashMap<>());

    switch (action) {
      case "add":
        int currentQuantity = carrello.getOrDefault(productId, 0);
        if (currentQuantity >= prodotto.getQuantita()) {
          return ResponseEntity.badRequest()
              .body(Map.of("message", "Quantità massima disponibile raggiunta."));
        }
        carrello.put(productId, currentQuantity + 1);
        break;

      case "remove":
        if (carrello.containsKey(productId)) {
          int newQuantity = carrello.get(productId) - 1;
          if (newQuantity <= 0) {
            carrello.remove(productId);
          } else {
            carrello.put(productId, newQuantity);
          }
        } else {
          return ResponseEntity.badRequest()
              .body(Map.of("message", "Prodotto non presente nel carrello."));
        }
        break;

      case "removeAll":
        if (carrello.containsKey(productId)) {
          carrello.remove(productId);
        } else {
          return ResponseEntity.badRequest()
              .body(Map.of("message", "Prodotto non presente nel carrello."));
        }
        break;

      default:
        return ResponseEntity.badRequest().body(Map.of("message", "Azione non valida."));
    }


    sessionCarrello.setCarrello(carrello);

    return ResponseEntity.ok(
        Map.of("message", "Carrello aggiornato con successo.", "carrello", carrello));
  }
}


