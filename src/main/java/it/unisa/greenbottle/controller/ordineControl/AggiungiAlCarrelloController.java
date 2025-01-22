package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.ordineControl.form.ProdottoForm;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AggiungiAlCarrelloController {

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private SessionCarrello sessionCarrello;

  @PostMapping("/aggiungi-al-carrello")
  public ResponseEntity<?> post(@ModelAttribute @Valid ProdottoForm prodottoForm) {

    Prodotto prod = prodottoDao.findProdottoById(prodottoForm.getIdProdotto()).orElse(null);
    if (prod == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prodotto non trovato nel sistema");
    }

    if (prodottoForm.getQuantita() > prod.getQuantita()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Quantità richiesta non disponibile");
    }

    sessionCarrello.addToCarrello(prod.getId(), prodottoForm.getQuantita());
    return ResponseEntity.ok("Prodotto aggiunto al carrello");

  }
}

