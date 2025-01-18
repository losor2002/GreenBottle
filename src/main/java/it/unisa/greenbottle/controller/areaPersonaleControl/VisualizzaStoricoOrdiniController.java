package it.unisa.greenbottle.controller.areaPersonaleControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.util.OrdineWrapper;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/areaPersonale/visualizzaStoricoOrdini")
public class VisualizzaStoricoOrdiniController {

  private final String homeView = "/home";
  private final String visualizzaStoricoOrdiniView = "/AreaPersonaleView/StoricoOrdini";

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private ComposizioneDao composizioneDao;

  @Autowired
  private SessionCliente sessionCliente;

  @Autowired
  private ClienteDao clienteDao;

  @GetMapping
  public String get(Model model) {
    /*
    TODO: AGGIUNGERE IL FILTRAGGIO
     */

    // TODO: rimuovi questo controllo quando saranno implementati i filtri
    Optional<Cliente> optCliente = sessionCliente.getCliente();
    if (optCliente.isEmpty()) {
      return "redirect:" + homeView;
    }
    Cliente cliente = optCliente.get();

    List<OrdineWrapper> ordiniFinale = new LinkedList<>();
    List<Ordine> ordiniCliente = ordineDao.findOrdineByCliente(cliente);
    for (Ordine ordine : ordiniCliente) {
      ordiniFinale.add(new OrdineWrapper(ordine, ordine.getComposizioni().stream().toList()));
    }

    model.addAttribute("ordini", ordiniFinale);
    model.addAttribute("StatoSpedizione", Ordine.StatoSpedizione.class);

    return visualizzaStoricoOrdiniView;
  }


}
