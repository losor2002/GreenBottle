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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller che si occupa di visualizzare il catalogo dei prodotti.
 */
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

  /**
   * Metodo che si occupa di visualizzare il catalogo dei prodotti.
   *
   * @param filtroForm          filtro per la ricerca dei prodotti
   * @param bindingResult       binding result
   * @param model               model
   * @param httpServletResponse httpServletResponse
   * @return catalogoView
   * @throws IOException IOException
   */
  @GetMapping
  public String get(@ModelAttribute @Valid FiltroForm filtroForm, BindingResult bindingResult,
                    Model model,
                    HttpServletResponse httpServletResponse
  )
      throws IOException {

    if (bindingResult.hasErrors()) {
      // Se c'è un errore specifico per un campo, gestisci il messaggio
      FieldError fieldError = bindingResult.getFieldErrors().getFirst();
      model.addAttribute("message", fieldError.getDefaultMessage());
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
          fieldError.getDefaultMessage());
      return "error"; // Visualizza la vista con il messaggio di errore
    }

    if (!filtroForm.isPrezzoMinMaxValid()) {
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      model.addAttribute("message", "Prezzo minimo non può essere maggiore del prezzo massimo.");
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "Prezzo minimo non può essere maggiore del prezzo massimo.");
      return "error"; // Visualizza la vista con il messaggio di errore
    }

    Specification<Prodotto> spec = Specification.where(null);

    //CONTROLLO CATEGORIA
    if (filtroForm.getIdCategoria() != null) {

      if (categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).isEmpty()) {

        model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("message", "Categoria non presente.");

        httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoria non presente.");
        return "error";
      }

      if (categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).isPresent()) {
        spec = spec.and((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("categoria"),
                categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).get()));
      }
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
