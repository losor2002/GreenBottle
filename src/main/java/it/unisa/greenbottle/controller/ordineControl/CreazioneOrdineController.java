package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.form.OrdineForm;
import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.dao.ProdottoDao;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import it.unisa.greenbottle.storage.ordineStorage.entity.OrdineDirector;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ordina")
public class CreazioneOrdineController {

  private static final String ordineView = "OrdineView/Checkout";

  private static final String fallbackView = "redirect:/";

  private static final String successView = "redirect:/areaPersonale/visualizzaStoricoOrdini";


  @Autowired
  private OrdineDao ordineDao;

  @Autowired
  private ProdottoDao prodottoDao;

  @Autowired
  private IndirizzoDao indirizzoDao;

  @Autowired
  private SessionCliente sessionCliente;

  @Autowired
  private SessionCarrello sessionCarrello;

  @GetMapping
  public String get(Model model, HttpServletResponse httpServletResponse) throws IOException {
    Optional<Cliente> clienteOptional = sessionCliente.getCliente();
    Map<Prodotto, Integer> carrello = sessionCarrello.getRealCarrello();
    if (clienteOptional.isEmpty()) {
      return "redirect:/login";
    }
    if (carrello == null || carrello.isEmpty()) {
      model.addAttribute("errore", "Il carrello è vuoto.");
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Il carrello è vuoto");
      return fallbackView;
    }
    // return a model attribute of List<Indirizzo> grabbing all Indirizzo by Cliente in the session:
    Cliente cliente = clienteOptional.get();
    List<Indirizzo> indirizzi = indirizzoDao.findAllByCliente(cliente).orElse(new ArrayList<>());
    model.addAttribute("indirizzi", indirizzi);
    return ordineView;
  }

  @PostMapping
  @Transactional
  public String post(@ModelAttribute @Valid OrdineForm ordineForm, Model model,
                     BindingResult bindingResult, HttpServletResponse httpServletResponse) throws
      IOException {
    Optional<Cliente> clienteOptional = sessionCliente.getCliente();

    if (clienteOptional.isEmpty()) {
      return "redirect:/login";
    }

    final Cliente cliente = clienteOptional.get();

    if (bindingResult.hasErrors()) {
      // Se c'è un errore specifico per un campo, gestisci il messaggio
      FieldError fieldError = bindingResult.getFieldErrors().getFirst();
      model.addAttribute("message", fieldError.getDefaultMessage());
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, fieldError.getField());
      return "error";// Visualizza la vista con il messaggio di errore
    }

    Map<Long, Integer> prodottiUnparsed = sessionCarrello.getCarrello();

    if (prodottiUnparsed == null || prodottiUnparsed.isEmpty()) {
      model.addAttribute("errore", "Il carrello è vuoto");
      return fallbackView;
    }

    Map<Prodotto, Integer> prodotti = new HashMap<>();
    for (Map.Entry<Long, Integer> entry : prodottiUnparsed.entrySet()) {
      Optional<Prodotto> prodottoOpt = prodottoDao.findProdottoById(entry.getKey());
      if (prodottoOpt.isPresent()) {
        Prodotto p = prodottoOpt.get();
        int quantita = entry.getValue();

        if (quantita > p.getQuantita()) {
          model.addAttribute("errore",
              "Prodotto: " + p.getNome() + " non disponibile in quantità richiesta.");
          httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
              "Prodotto" + p.getNome() + " non disponibile in quantità richiesta");
          return fallbackView;
        }
        prodotti.put(p, quantita);
      }
    }

    final float prezzoTotale = (float) prodotti.entrySet().stream()
        .mapToDouble(entry -> entry.getKey().getPrezzo() * entry.getValue())
        .sum();
    String numeroCarta = ordineForm.getNumeroCarta();
    String dataScadenza = ordineForm.getDataScadenza();
    String nomeTitolare = ordineForm.getNomeTitolare();
    final String riassuntoCarta = nomeTitolare + "/" + dataScadenza + "/" + numeroCarta.substring(
        numeroCarta.length() - 4);
    Boolean isSupporto = ordineForm.getIsSupporto();
    String descrizioneSupporto = ordineForm.getDescrizioneSupporto();
    final Boolean isRitiro = ordineForm.getIsRitiro();

    if (isSupporto && descrizioneSupporto.isBlank()) {
      model.addAttribute("errore", "Descrizione supporto non inserita.");
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Descrizione supporto non inserita");
      return null;
    } else if (!(isSupporto || descrizioneSupporto.isBlank())) { // De Morgan
      model.addAttribute("warning",
              "Errata selezione dell’opzione di richiesta supporto aggiuntivo");
    }

    Long idIndirizzo = ordineForm.getIndirizzo();
    Optional<Indirizzo> indirizzoOpt = indirizzoDao.findIndirizzoById(idIndirizzo);

    if (indirizzoOpt.isEmpty()) {
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      model.addAttribute("message", "Indirizzo non trovato.");
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Indirizzo non trovato");
      return "error";
    }
    Indirizzo indirizzo = indirizzoOpt.get();

    Set<Composizione> composizioni = new HashSet<>();
    for (Map.Entry<Prodotto, Integer> entry : prodotti.entrySet()) {
      Prodotto prod = entry.getKey();
      int quantita = entry.getValue();
      composizioni.add(new Composizione(prod, quantita));
    }

    Ordine ordine;
    if (isSupporto) {
      ordine = OrdineDirector.createOrdineConSupporto(prezzoTotale, isRitiro, riassuntoCarta,
          descrizioneSupporto, indirizzo, cliente, composizioni);
    } else {
      ordine = OrdineDirector.createOrdine(prezzoTotale, isRitiro, riassuntoCarta, indirizzo,
          cliente, composizioni);
    }

    ordineDao.save(ordine);
    model.addAttribute("successo", "Ordine inserito con successo.");
    sessionCarrello.clearCarrello();
    return successView;
  }
}
