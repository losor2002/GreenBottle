package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/catalogo")
public class VisualizzaCatalogoController {

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private CategoriaDao categoriaDao;

  @GetMapping
  @ResponseBody
  public String get(@RequestParam(required = false) Optional<String> nomeCategoria) {

    /* **** CREA PRODOTTI ****
    Categoria bevande = new Categoria("Bevande Gassate");
    categoriaDao.save(bevande);

    Prodotto cocaCola = new Prodotto("Coca Cola", "cocacola", null, 1.5f, 100, bevande);
    prodottoDao.save(cocaCola);
    Prodotto fanta = new Prodotto("Fanta", "fanta", null, 1.5f, 100, bevande);
    prodottoDao.save(fanta);
    Prodotto pepsi = new Prodotto("Pepsi", "pepsi", null, 1.5f, 100, bevande);
    prodottoDao.save(pepsi);
     */


    List<Prodotto> prodotti = prodottoDao.findAll();
    return prodotti.getFirst().getNome();
  }
}