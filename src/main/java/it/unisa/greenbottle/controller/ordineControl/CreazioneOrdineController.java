package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.controller.ordineControl.form.OrdineForm;
import it.unisa.greenbottle.controller.ordineControl.form.ProdottoForm;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.areaPersonaleStorage.dao.IndirizzoDao;
import it.unisa.greenbottle.storage.areaPersonaleStorage.entity.Indirizzo;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import it.unisa.greenbottle.storage.ordineStorage.dao.OrdineDao;
import it.unisa.greenbottle.storage.ordineStorage.entity.Composizione;
import it.unisa.greenbottle.storage.ordineStorage.entity.Ordine;
import it.unisa.greenbottle.storage.ordineStorage.entity.OrdineBuilder;
import it.unisa.greenbottle.storage.ordineStorage.entity.OrdineDirector;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.*;

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
    private IndirizzoDao indirizzoDao;

    @Autowired
    private SessionCliente sessionCliente;

    @GetMapping
    public String get(@ModelAttribute ProdottoForm prodottiForm, Model model) {

        Optional<Cliente> clienteOptional = sessionCliente.getCliente();

        if(clienteOptional.isEmpty()){
            //invalida la sessione.
            return "redirect:/login";
        }

        return ordineView;
    }

    @PostMapping
    public String post(@ModelAttribute @Valid OrdineForm ordineForm, Model model) {

        Optional<Cliente> clienteOptional = sessionCliente.getCliente();

        if(clienteOptional.isEmpty()){
            return "redirect:/login";
        }

        Optional<Map<Prodotto, Integer>> prodottiOptional = sessionCliente.getCarrello();

        if(prodottiOptional.isEmpty()) {
           return "redirect:/error";
        }

        Map<Prodotto, Integer> prodotti = prodottiOptional.get();

        float prezzoTotale = (float) prodotti.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrezzo() * entry.getValue())
                .sum();

        String numeroCarta = ordineForm.getNumeroCarta();
        String dataScadenza = ordineForm.getDataScadenza();
        String nomeTitolare = ordineForm.getNomeTitolare();
        String riassuntoCarta = nomeTitolare + "/" + dataScadenza +"/" +  numeroCarta.substring(numeroCarta.length() - 4);
        
        boolean isSupporto = ordineForm.isSupporto();
        String descrizioneSupporto = ordineForm.getDescrizioneSupporto();
        boolean isRitiro = ordineForm.isRitiro();


        if(isSupporto && descrizioneSupporto.isBlank()){
            model.addAttribute("errore", "Descrizione supporto non inserita.");
            return "redirect:/carrello";
        } else if(!(isSupporto || descrizioneSupporto.isBlank())){ //perle di De Morgan
            model.addAttribute("warning", "Errata selezione dell’opzione di richiesta supporto aggiuntivo");

        }

        Long idIndirizzo = ordineForm.getIndirizzo();
        Optional<Indirizzo> indirizzoOpt = indirizzoDao.findById(idIndirizzo);

        if(indirizzoOpt.isEmpty()){
            model.addAttribute("errore", "Indirizzo non trovato.");
            return "redirect:/carrello";
        }
        Indirizzo indirizzo = indirizzoOpt.get();

        Set<Composizione> composizioni = new HashSet<>();
        for (Map.Entry<Prodotto, Integer> entry : prodotti.entrySet()) {
            Prodotto prodotto = entry.getKey();
            int quantita = entry.getValue();
            composizioni.add(new Composizione(prodotto, quantita));
        }

        Ordine ordine;
        if(isSupporto) {
            ordine = OrdineDirector.createOrdineConSupporto(prezzoTotale, isRitiro, riassuntoCarta, descrizioneSupporto, indirizzo, composizioni);
        } else {
            ordine = OrdineDirector.createOrdine(prezzoTotale, isRitiro, riassuntoCarta, indirizzo, composizioni);
        }

        ordineDao.save(ordine);
        model.addAttribute("successo", "Ordine creato con successo.");
        sessionCliente.emptyCarrello();

        return ordineView;
    }


}
