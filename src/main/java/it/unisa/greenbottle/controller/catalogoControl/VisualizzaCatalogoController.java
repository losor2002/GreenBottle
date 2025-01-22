package it.unisa.greenbottle.controller.catalogoControl;

import it.unisa.greenbottle.controller.catalogoControl.form.FiltroForm;
import it.unisa.greenbottle.storage.catalogoStorage.dao.CategoriaDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.dao.RecensioneDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
  public String get(@ModelAttribute @Valid FiltroForm filtroForm, BindingResult bindingResult,
                    Model model,
                    HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest
                    )
      throws IOException {

  if (bindingResult.hasErrors()) {
    // Se c'è un errore specifico per un campo, gestisci il messaggio
    bindingResult.getFieldErrors().forEach(fieldError -> {
      if ("prezzoMin".equals(fieldError.getField())) {
        model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("message", "Il prezzo minimo deve essere maggiore o uguale a 1.");

          try {
              httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Il prezzo minimo deve essere maggiore o uguale a 1.");
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
      if ("prezzoMax".equals(fieldError.getField())) {
        model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("message", "Il prezzo massimo deve essere minore o uguale a 999.");

          try {
              httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Il prezzo massimo deve essere minore o uguale a 999.");
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
      if ("media".equals(fieldError.getField())) {
        model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("message", "La media deve essere compresa tra 1.0 e 5.0.");

          try {
              httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "La media deve essere compresa tra 1.0 e 5.0");
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
    });
    return "error";  // Visualizza la vista con il messaggio di errore
  }

    Specification<Prodotto> spec = Specification.where(null);

    //CONTROLLO CATEGORIA
    if (filtroForm.getIdCategoria() != null) {
      if (filtroForm.getIdCategoria().compareTo(0L) < 0) {

        httpServletRequest.setAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
        httpServletRequest.setAttribute("message", "Id non valido.");

        httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id non valido.");
        return "error";
      }

      if (categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).isEmpty()) {

        httpServletRequest.setAttribute("status", HttpServletResponse.SC_NOT_FOUND);
        httpServletRequest.setAttribute("message", "Categoria non presente.");

        httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoria non presente.");
        return "error";
      }

      if(categoriaDao.findCategoriaById(filtroForm.getIdCategoria()).isPresent()) {
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
    model.addAttribute("prodotti", prodotti);
    model.addAttribute("filterForm", filtroForm);
    return catalogoView;
  }

}
