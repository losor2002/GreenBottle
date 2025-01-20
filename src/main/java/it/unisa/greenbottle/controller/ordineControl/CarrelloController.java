/*package it.unisa.greenbottle.controller.ordineControl;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/getProdotti")
public class CarrelloController {

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private SessionCarrello sessionCarrello;

  @Autowired
  private SessionCliente sessionCliente;


  @GetMapping
  public ResponseEntity<?> getCarrello(Model model) {
    Optional<Map<Long, Integer>> optcarrello = sessionCarrello.getCarrello();
    Map<Long, Integer> carrello;
    if (optcarrello.isEmpty()) {
      return ResponseEntity.ok(Map.of("message", "Il carrello è vuoto."));
    } else {
      carrello = optcarrello.get();
    }

    Map<String, Object> carrelloDetails = new HashMap<>();
    carrello.forEach((productId, quantity) -> {
      Optional<Prodotto> prodottoOpt = prodottoDao.findProdottoById(productId);
      prodottoOpt.ifPresent(prodotto -> {
        Map<String, Object> prodottoDetails = new HashMap<>();
        prodottoDetails.put("id", prodotto.getId());
        prodottoDetails.put("nome", prodotto.getNome());
        prodottoDetails.put("descrizione", prodotto.getDescrizione());
        prodottoDetails.put("quantita", quantity);
        prodottoDetails.put("prezzo", prodotto.getPrezzo());
        prodottoDetails.put("immagine", prodotto.getImg());
        carrelloDetails.put(productId.toString(), prodottoDetails);
      });
    });

    return ResponseEntity.ok(carrelloDetails);
  }

  @PostMapping("/{action}/{productId}")
  public ResponseEntity<?> updateCart(@PathVariable String action, @PathVariable Long productId) {
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

    return ResponseEntity.ok(Map.of(
        "message", "Carrello aggiornato con successo.",
        "carrello", carrello
    ));
  }
}*/