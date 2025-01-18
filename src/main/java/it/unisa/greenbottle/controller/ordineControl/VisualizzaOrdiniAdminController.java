package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionAdmin;
import it.unisa.greenbottle.controller.ordineControl.util.OrdineWrapper;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/visualizzaOrdini")
public class VisualizzaOrdiniAdminController {

  private final String visualizzaOrdiniView = "/OrdineView/VisualizzaOrdiniAdmin";

  @Autowired
  private ComposizioneDao composizioneDao;

  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private SessionAdmin sessionAdmin;


  @GetMapping
  public String get(Model model) {
    List<OrdineWrapper> ordiniFinale = new LinkedList<>();
    List<Ordine> ordiniDaElaborare =
        ordineDao.findOrdineByStato(Ordine.StatoSpedizione.ELABORAZIONE);
    for (Ordine ordine : ordiniDaElaborare) {
      List<Composizione> composizione = composizioneDao.findComposizioneByOrdine(ordine);
      ordiniFinale.add(new OrdineWrapper(ordine, composizione));
    }

    model.addAttribute("ordini", ordiniFinale);
    model.addAttribute("StatoSpedizione", Ordine.StatoSpedizione.class);

    return visualizzaOrdiniView;
  }


}
