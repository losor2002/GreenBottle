package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.controller.catalogoControl.form.FiltroForm;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.RecensioneDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalogo")
public class VisualizzaCatalogoController {

  private String catalogoView = "/catalogoView/Catalogo";

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private CategoriaDao categoriaDao;

  @Autowired
  private RecensioneDao recensioneDao;

  @GetMapping
  public String get(@ModelAttribute FiltroForm filterForm, Model model) {
    Specification<Prodotto> spec = Specification.where(null);

    if (filterForm.getNome() != null && !filterForm.getNome().isEmpty()) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.like(root.get("nome"), "%" + filterForm.getNome() + "%"));
    }
    if (filterForm.getCategoria() != null && !filterForm.getCategoria().isEmpty()) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.equal(root.get("categoria").get("nome"), filterForm.getCategoria()));
    }
    if (filterForm.getPrezzoMin() != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThanOrEqualTo(root.get("prezzo"), filterForm.getPrezzoMin()));
    }
    if (filterForm.getPrezzoMax() != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.lessThanOrEqualTo(root.get("prezzo"), filterForm.getPrezzoMax()));
    }
    if (filterForm.getVoto() != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThanOrEqualTo(root.get("votoMedio"), filterForm.getVoto()));
    }

    List<Prodotto> prodotti = prodottoDao.findAll(spec);


    model.addAttribute("prodotti", prodotti);
    model.addAttribute("filterForm", filterForm);
    return prodotti.toString();

  }

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


}