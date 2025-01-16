package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.ordineControl.util.OrdineWrapper;
import it.unisa.greenbottle.storage.ordineStorage.dao.ComposizioneDao;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/visualizzaOrdini")
public class VisualizzaOrdiniAdminController {

  private final String visualizzaOrdiniView = "/OrdineView/VisualizzaOrdiniAdmin";

  @Autowired
  private ComposizioneDao composizioneDao;

  @Autowired
  private OrdineDao ordineDao;


  @GetMapping
  public String get(Model model) {
    List<OrdineWrapper> ordiniFinale = new LinkedList<>();
    List<Ordine> ordiniDaElaborare = ordineDao.findByStato(Ordine.StatoSpedizione.ELABORAZIONE);
    for (Ordine ordine : ordiniDaElaborare) {
      List<Composizione> composizione = composizioneDao.findByOrdine(ordine);
      ordiniFinale.add(new OrdineWrapper(ordine, composizione));
    }

    model.addAttribute("ordini", ordiniFinale);
    model.addAttribute("StatoSpedizione", Ordine.StatoSpedizione.class);

    return visualizzaOrdiniView;
  }


  @PostMapping
  public ResponseEntity<?> post(@RequestParam("ordineId") String id,
                                @RequestParam("newState") String statoSpedizione) {
    Long veroId = Long.valueOf(id);
    Optional<Ordine> optOrdine = ordineDao.findById(veroId);
    if (optOrdine.isEmpty()) {
      // Risponde con un errore se l'ordine non esiste
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non trovato.");
    }

    Ordine ordine = optOrdine.get();
    ordine.setStato(Ordine.StatoSpedizione.valueOf(statoSpedizione));
    ordineDao.save(ordine);

    // Risponde con un messaggio di successo
    return ResponseEntity.status(HttpStatus.OK)
        .body("Stato dell'ordine aggiornato a: " + statoSpedizione);
  }
}
