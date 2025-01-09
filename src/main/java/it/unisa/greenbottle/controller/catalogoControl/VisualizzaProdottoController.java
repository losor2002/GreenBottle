package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/catalogo/prodotto")
public class VisualizzaProdottoController {

  @Autowired
  private ProdottoDao prodottoDao;


  @GetMapping
  @ResponseBody
  public String get(@RequestParam Long id) {
    Optional<Prodotto> prodotto = prodottoDao.findById(id);

    if (prodotto.isEmpty()) {
      return "/errore";
    }

    return prodotto.get().toString();
  }
}
