package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.controller.catalogoControl.form.FiltroForm;
import it.unisa.greenbottle.controller.ordineControl.form.ProdottoForm;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.RecensioneDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
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

  private final String catalogoView = "/CatalogoView/Catalogo";

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private CategoriaDao categoriaDao;

  @Autowired
  private RecensioneDao recensioneDao;

  @GetMapping
  public String get(@ModelAttribute @Valid FiltroForm filtroForm,
                    Model model,
                    HttpServletResponse httpServletResponse)
      throws IOException {

    Specification<Prodotto> spec = Specification.where(null);

    //CONTROLLO CATEGORIA
    if (filtroForm.getIdCategoria() != null) {
      if (filtroForm.getIdCategoria().compareTo(0L) < 0) {
        httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id non valido");
        return null;
      }

      if (categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).isEmpty()) {
        httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoria non presente");
        return null;
      }

      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.equal(root.get("categoria"),
              categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).get()));
    }

    //CONTROLLO PREZZOMIN
    if (filtroForm.getPrezzoMin() != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThanOrEqualTo(root.get("prezzo"), filtroForm.getPrezzoMin())
      );
    }

    //CONTROLLO PREZZOMAX
    if (filtroForm.getPrezzoMax() != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.lessThanOrEqualTo(root.get("prezzo"), filtroForm.getPrezzoMax())
      );
    }

    //CONTROLLO MEDIA
    if (filtroForm.getMedia() != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThanOrEqualTo(root.get("mediaVoti"), filtroForm.getMedia())
      );
    }

    List<Prodotto> prodotti = prodottoDao.findAll(spec);
    ProdottoForm prodottoForm = new ProdottoForm();
    model.addAttribute("prodotti", prodotti);
    model.addAttribute("filterForm", filtroForm);
    model.addAttribute("prodottoForm", prodottoForm);
    return catalogoView;
  }

}
