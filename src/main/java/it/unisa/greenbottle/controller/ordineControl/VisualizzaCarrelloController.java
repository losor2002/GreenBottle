package it.unisa.greenbottle.controller.ordineControl;


import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisualizzaCarrelloController {

  private static final String CARRELLO_VIEW = "/OrdineView/NewCarrello";

  @Autowired
  private SessionCarrello sessionCarrello;

  @Autowired
  private ProdottoDao prodottoDao;


  @GetMapping("/carrello")
  public String get(Model model) {
    Optional<Map<Long, Integer>> optcarrello = sessionCarrello.getCarrello();
    Map<Long, Integer> carrello;

    // Check if the cart is empty
    if (optcarrello.isEmpty()) {
      model.addAttribute("message", "Il carrello è vuoto.");
    } else {
      carrello = optcarrello.get();

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
      model.addAttribute("carrelloDetails", carrelloDetails);
    }

    return CARRELLO_VIEW;
  }
}

