package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.util.OrdineWrapper;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/areaPersonale/visualizzaStatoOrdine")
public class VisualizzaStatoOrdineController {

  private final String homeView = "/home";
  private final String visualizzaStatoOrdineView = "/OrdineView/VisualizzaStatoOrdine";

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private ComposizioneDao composizioneDao;

  @Autowired
  private SessionCliente sessionCliente;


  @GetMapping
  public String get(@RequestParam Long id, Model model, HttpServletResponse httpServletResponse)
      throws IOException {
    Cliente cliente = sessionCliente.getCliente().get();

    if (id < 0) {
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      model.addAttribute("message", "Id non valido.");

      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id non valido.");
      return "error";
    }

    Optional<Ordine> optOrdine = ordineDao.findOrdineById(id);
    if (optOrdine.isEmpty()) {
      model.addAttribute("status", HttpServletResponse.SC_NOT_FOUND);
      model.addAttribute("message", "Ordine non presente.");

      httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Ordine non presente.");
      return "error";
    }

    Ordine ordine = optOrdine.get();
    if (!ordine.getCliente().equals(cliente)) {
      model.addAttribute("status", HttpServletResponse.SC_FORBIDDEN);
      model.addAttribute("message", "Ordine non accessibile.");

      httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Ordine non accessibile.");
      return "error";
    }

    List<Composizione> composizione = composizioneDao.findComposizioneByOrdine(ordine);
    OrdineWrapper ordineWrapper = new OrdineWrapper(ordine, composizione);

    model.addAttribute("ordine", ordineWrapper);
    model.addAttribute("StatoSpedizione", Ordine.StatoSpedizione.class);

    return visualizzaStatoOrdineView;
  }

  @PostMapping
  public String post(@RequestParam Long id, Model model) {
    // TODO: rimuovi questo controllo quando saranno implementati i filtri
    Optional<Cliente> optCliente = sessionCliente.getCliente();
    if (optCliente.isEmpty()) {
      return "redirect:" + homeView;
    }
    Cliente cliente = optCliente.get();


    Optional<Ordine> optOrdine = ordineDao.findOrdineById(id);
    if (optOrdine.isEmpty()) {
      return "/error";
    }
    Ordine ordine = optOrdine.get();
    if (!ordine.getCliente().equals(cliente)) {
      return "/error";
    }

    composizioneDao.deleteAll(composizioneDao.findComposizioneByOrdine(ordine));
    ordineDao.delete(ordine);

    return "redirect:" + "/areaPersonale/visualizzaStoricoOrdini";
  }

}
