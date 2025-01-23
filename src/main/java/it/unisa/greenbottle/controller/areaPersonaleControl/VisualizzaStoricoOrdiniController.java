package it.unisa.greenbottle.controller.areaPersonaleControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.areaPersonaleControl.form.DataForm;
import it.unisa.greenbottle.controller.ordineControl.util.OrdineWrapper;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("areaPersonale/visualizzaStoricoOrdini")
public class VisualizzaStoricoOrdiniController {

  private final String visualizzaStoricoOrdiniView = "/AreaPersonaleView/StoricoOrdini";

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private SessionCliente sessionCliente;

  @GetMapping
  public String get(@ModelAttribute DataForm dataForm, BindingResult bindingResult, Model model,
                    HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {
    if (bindingResult.hasErrors()) {
      // Se c'è un errore specifico per un campo, gestisci il messaggio
      FieldError fieldError = bindingResult.getFieldErrors().getFirst();
      model.addAttribute("message", fieldError.getDefaultMessage());
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, fieldError.getField());
      return "error";// Visualizza la vista con il messaggio di errore
    }

    Cliente cliente = null;
    if(sessionCliente.getCliente().isPresent()) cliente = sessionCliente.getCliente().get();
    else return "redirect:/login";

    // se non è stata inserita una data di inizio, la data di inizio è 2023-01-01
    String startDateStr =
        !dataForm.getStartDate().isEmpty() ? dataForm.getStartDate() : "2023-01-01";
    // se non è stata inserita una data di fine, la data di fine è la data attuale
    String endDateStr = !dataForm.getEndDate().isEmpty() ? dataForm.getEndDate() :
        LocalDate.ofEpochDay(System.currentTimeMillis() / 86_400_000).toString();
    LocalDate startDate;
    LocalDate endDate;

    try {
      startDate = LocalDate.parse(startDateStr);
      endDate = LocalDate.parse(endDateStr);
    } catch (DateTimeException e) {

      httpServletRequest.setAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      httpServletRequest.setAttribute("message", "Data non valida.");

      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data non valida.");
      return visualizzaStoricoOrdiniView;
    }

    if(startDate.isAfter(endDate)) {

      httpServletRequest.setAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      httpServletRequest.setAttribute("message", "Data iniziale successiva alla data finale.");

      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data iniziale successiva alla data finale.");
      return visualizzaStoricoOrdiniView;
    }

    List<OrdineWrapper> ordiniFinale = new LinkedList<>();
    List<Ordine> ordiniCliente = ordineDao.findOrdineByCliente(cliente);

    for (Ordine ordine : ordiniCliente) {
      LocalDate dataOrdine = ordine.getData().toLocalDateTime().toLocalDate();
      if ((dataOrdine.isEqual(startDate) || dataOrdine.isAfter(startDate))
          && (dataOrdine.isEqual(endDate) || dataOrdine.isBefore(endDate))) {
        ordiniFinale.add(new OrdineWrapper(ordine, ordine.getComposizioni().stream().toList()));
      }
    }

    model.addAttribute("ordini", ordiniFinale);
    model.addAttribute("StatoSpedizione", Ordine.StatoSpedizione.class);
    return visualizzaStoricoOrdiniView;
  }

}
