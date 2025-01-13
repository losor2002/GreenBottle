package it.unisa.greenbottle.controller.ordineControl;

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


  @GetMapping
  public String get(Model model) {
    //try {
    // Creazione dell'ordine
    /*
    Ordine ordine = new Ordine();
    ordine.setDescrizione("descrizione aiuto");
    ordine.setPrezzo(10.68f);
    ordine.setStato(Ordine.StatoSpedizione.ELABORAZIONE);
    ordine.setCarta("1234567890123456");
    ordine.setRitiro(true);
    ordine.setSupporto(true);

    // Associare cliente e indirizzo
    ordine.setCliente(clienteDao.findById(1L).orElseThrow(() ->
        new IllegalArgumentException("Cliente non trovato")));
    ordine.setIndirizzo(indirizzoDao.findById(1L).orElseThrow(() ->
        new IllegalArgumentException("Indirizzo non trovato")));

    // Salvare l'ordine
    ordineDao.save(ordine);

    // Creazione di una composizione
    Composizione composizione = new Composizione();
    composizione.setQuantita(2);
    composizione.setOrdine(ordine);
    composizione.setProdotto(prodottoDao.findById(1L).orElseThrow(() ->
        new IllegalArgumentException("Prodotto non trovato")));

    // Salvare la composizione
    composizioneDao.save(composizione);

    // Recupero delle composizioni associate all'ordine
    List<Composizione> composizioni = composizioneDao.findAllByOrdine(ordine);

    // Creazione della stringa di output
    StringBuilder output = new StringBuilder();
    output.append("Dettagli Ordine:\n");
    output.append("ID Ordine: ").append(ordine.getId()).append("\n");
    output.append("Descrizione: ").append(ordine.getDescrizione()).append("\n");
    output.append("Prezzo: ").append(ordine.getPrezzo()).append(" €\n");
    output.append("Stato: ").append(ordine.getStato()).append("\n");
    output.append("Cliente: ").append(ordine.getCliente().getNome()).append(" ")
        .append(ordine.getCliente().getCognome()).append("\n");
    output.append("Indirizzo: ").append(ordine.getIndirizzo().getVia()).append(", ")
        .append(ordine.getIndirizzo().getCitta()).append("\n");
    output.append("Ritiro: ").append(ordine.isRitiro() ? "Sì" : "No").append("\n");
    output.append("Supporto: ").append(ordine.isSupporto() ? "Sì" : "No").append("\n\n");

    output.append("Composizioni:\n");
    for (Composizione comp : composizioni) {
      output.append("- Prodotto: ").append(comp.getProdotto().getNome()).append("\n");
      output.append("  Quantità: ").append(comp.getQuantita()).append("\n");
      output.append("  Prezzo unitario: ").append(comp.getProdotto().getPrezzo()).append(" €\n");
    }

    return output.toString();
  } catch (Exception e) {
    // In caso di errore, restituisci il messaggio dell'eccezione
    return "Errore durante la creazione dell'ordine: " + e.getMessage();
  }
     */

    List<OrdineWrapper> ordiniFinale = new LinkedList<>();
    List<Ordine> ordiniDaElaborare = ordineDao.findByStato(Ordine.StatoSpedizione.ELABORAZIONE);
    for (Ordine ordine : ordiniDaElaborare) {
      List<Composizione> composizione = composizioneDao.findByOrdine(ordine);
      ordiniFinale.add(new OrdineWrapper(ordine, composizione));
    }

    // System.out.println(ordiniFinale);
    model.addAttribute("ordini", ordiniFinale);
    return visualizzaOrdiniView;

  }

}
