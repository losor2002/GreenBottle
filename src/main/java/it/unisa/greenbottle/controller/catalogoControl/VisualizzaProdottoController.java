package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalogo/prodotto")
public class VisualizzaProdottoController {

  private final String prodottoView = "/CatalogoView/Prodotto";

  @Autowired
  private ProdottoDao prodottoDao;


  @GetMapping
  public String get(@RequestParam Long id, Model model) {
    Optional<Prodotto> prodotto = prodottoDao.findById(id);
    if (prodotto.isEmpty()) {
      return "/errore";
    }
    model.addAttribute("prodotto", prodotto.get());
    return prodottoView;
  }
}
