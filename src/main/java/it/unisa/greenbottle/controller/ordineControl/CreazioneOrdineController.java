package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.form.OrdineForm;
import it.unisa.greenbottle.controller.ordineControl.form.ProdottoForm;
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
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ordina")
public class CreazioneOrdineController {

  private static final String ordineView = "OrdineView/Ordine";

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
  public String get(Model model) {
    Optional<Cliente> clienteOptional = sessionCliente.getCliente();
    Map<Prodotto, Integer> carrello = sessionCarrello.getRealCarrello();
    if (clienteOptional.isEmpty()) {
      return "redirect:/login";
    }
    if (carrello == null || carrello.isEmpty()){
      model.addAttribute("errore", "Il carrello è vuoto.");
      return "redirect:/carrello";
    }
    return ordineView;
  }

  @PostMapping
  @Transactional
  public String post(@ModelAttribute @Valid OrdineForm ordineForm, Model model, BindingResult bindingResult) {
    Optional<Cliente> clienteOptional = sessionCliente.getCliente();

    if (clienteOptional.isEmpty()) {
      return "redirect:/login";
    }

    final Cliente cliente = clienteOptional.get();

    if(bindingResult.hasErrors()) {
      model.addAttribute("errore", bindingResult.getAllErrors());
      return "redirect:/carrello";
    }

    Map<Long, Integer> prodottiUnparsed = sessionCarrello.getCarrello();
    Map<Prodotto, Integer> prodotti = new HashMap<>();

    for (Map.Entry<Long, Integer> entry : prodottiUnparsed.entrySet()) {
      Optional<Prodotto> prodottoOpt = prodottoDao.findProdottoById(entry.getKey());
      if (prodottoOpt.isPresent()) {
        Prodotto p = prodottoOpt.get();
        int quantita = entry.getValue();

        if (quantita > p.getQuantita()) {
          model.addAttribute("errore",
              "Prodotto: " + p.getNome() + " non disponibile in quantità richiesta.");
          return "redirect:/carrello";
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
    boolean isSupporto = ordineForm.isSupporto();
    String descrizioneSupporto = ordineForm.getDescrizioneSupporto();
    final boolean isRitiro = ordineForm.isRitiro();

    if (isSupporto && descrizioneSupporto.isBlank()) {
      model.addAttribute("errore", "Descrizione supporto non inserita.");
      return "redirect:/carrello";
    } else if (!(isSupporto || descrizioneSupporto.isBlank())) { // De Morgan
      model.addAttribute("warning",
          "Errata selezione dell’opzione di richiesta supporto aggiuntivo");
    }

    Long idIndirizzo = ordineForm.getIndirizzo();
    Optional<Indirizzo> indirizzoOpt = indirizzoDao.findIndirizzoById(idIndirizzo);

    if (indirizzoOpt.isEmpty()) {
      model.addAttribute("errore", "Indirizzo non trovato.");
      return "redirect:/carrello";
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

    return ordineView;
  }
}
