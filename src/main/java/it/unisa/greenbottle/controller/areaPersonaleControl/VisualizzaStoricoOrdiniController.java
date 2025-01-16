package it.unisa.greenbottle.controller.areaPersonaleControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.util.OrdineWrapper;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/areaPersonale/visualizzaStoricoOrdini")
public class VisualizzaStoricoOrdiniController {

  private final String homeView = "/home";
  private final String visualizzaStoricoOrdiniView = "/areaPersonale/visualizzaOrdini";

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private ComposizioneDao composizioneDao;

  @Autowired
  private SessionCliente sessionCliente;

  @RequestMapping
  public String visualizzaStoricoOrdini(Model model) {
    Optional<Cliente> optCliente = sessionCliente.getCliente();
    if (optCliente.isEmpty()) {
      return "redirect: " + homeView;
    }
    Cliente cliente = optCliente.get();

    List<OrdineWrapper> ordiniFinale = new LinkedList<>();
    List<Ordine> ordiniDelCliente = ordineDao.findByCliente(cliente);
    for (Ordine ordine : ordiniDelCliente) {
      List<Composizione> composizione = composizioneDao.findByOrdine(ordine);
      ordiniFinale.add(new OrdineWrapper(ordine, composizione));
    }

    model.addAttribute("ordini", ordiniFinale);
    model.addAttribute("StatoSpedizione", Ordine.StatoSpedizione.class);

    return visualizzaStoricoOrdiniView;
  }


}
