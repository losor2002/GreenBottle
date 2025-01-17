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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    // TODO: rimuovi questo controllo quando saranno implementati i filtri
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

  /*
  riceve una richiesta POST per cancellare l'ordine
   */
  @PostMapping
  public ResponseEntity<?> post(@RequestParam("ordineId") String id) {
    // TODO: rimuovi questo controllo quando saranno implementati i filtri
    Optional<Cliente> optCliente = sessionCliente.getCliente();
    if (optCliente.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato.");
    }
    Cliente cliente = optCliente.get();

    // CONTROLLO SE L'ORDINE ESISTE
    Long veroId = Long.valueOf(id);
    Optional<Ordine> optOrdine = ordineDao.findById(veroId);
    if (optOrdine.isEmpty()) {
      // Risponde con un errore se l'ordine non esiste
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordine non trovato.");
    }
    Ordine ordine = optOrdine.get();

    // CONTROLLO SE L'ORDINE APPARTIENE AL CLIENTE
    if (!ordine.getCliente().equals(cliente)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Non sei autorizzato.");
    }


    // se l'ordine non è in stato di elaborazione non permette di cambiare lo stato
    if (!ordine.getStato().equals(Ordine.StatoSpedizione.ELABORAZIONE)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Non è possibile cambiare lo stato dell'ordine.");
    }

    // cancella prima tutte le composizioni dell'ordine
    composizioneDao.deleteAll(composizioneDao.findByOrdine(ordine));
    // e poi cancella l'ordine
    ordineDao.delete(ordine);

    // Risponde con un messaggio di successo
    return ResponseEntity.status(HttpStatus.OK)
        .body("Ordine eliminato con successo.");
  }

}
