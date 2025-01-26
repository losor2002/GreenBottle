package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.ordineControl.form.ProdottoForm;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Questa classe gestisce la richiesta di aggiungere un prodotto al carrello.
 */
@Controller
public class AggiungiAlCarrelloController {

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private SessionCarrello sessionCarrello;

  /**
   * Questo metodo gestisce la richiesta POST di aggiungere un prodotto al carrello.
   *
   * @param prodottoForm il form del prodotto da aggiungere al carrello
   * @return una stringa che rappresenta la pagina a cui reindirizzare
   */
  @PostMapping("/aggiungi-al-carrello")
  public String post(@ModelAttribute @Valid ProdottoForm prodottoForm) {

    Prodotto prod = prodottoDao.findProdottoById(prodottoForm.getIdProdotto()).orElse(null);
    if (prod == null) {
      return "error";
    }

    if (prodottoForm.getQuantita() > prod.getQuantita()) {
      return "error";
    }

    sessionCarrello.addToCarrello(prod.getId(), prodottoForm.getQuantita());
    return "redirect:/catalogo";
  }
}
