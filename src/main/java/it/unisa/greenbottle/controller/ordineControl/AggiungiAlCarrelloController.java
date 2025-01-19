package it.unisa.greenbottle.controller.ordineControl;

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
