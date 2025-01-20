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


/* package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.form.ProdottoForm;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aggiungiAlCarrello")
public class AggiungiAlCarrelloController {

  @Autowired
  private SessionCliente sessionCliente;

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private SessionCarrello sessionCarrello;

  @PostMapping
  public String post(@ModelAttribute @Valid ProdottoForm prodottoForm, Model model) {

    Optional<Prodotto> prodottoOpt = prodottoDao.findProdottoById(prodottoForm.getIdProdotto());
    Integer quantita = prodottoForm.getQuantita();

    if (prodottoOpt.isPresent()) {
      Prodotto prodotto = prodottoOpt.get();

      if (quantita > prodotto.getQuantita()) {
        model.addAttribute("errore",
            "Quantità richiesta per " + prodotto.getNome() + " non disponibile");
        return "redirect:/catalogo";
      }
      sessionCarrello.addToCarrello(prodotto.getId(), quantita);
      model.addAttribute("successo", "Il prodotto è stato inserito con successo");
    } else {
      model.addAttribute("errore", "\"Il prodotto selezionato non esiste\".");
    }

    return "redirect:/catalogo";

  }
}
*/