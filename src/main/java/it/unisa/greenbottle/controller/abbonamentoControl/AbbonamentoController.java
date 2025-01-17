package it.unisa.greenbottle.controller.abbonamentoControl;

import it.unisa.greenbottle.controller.abbonamentoControl.form.AbbonamentoForm;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.DisposizioneDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import it.unisa.greenbottle.storage.catalogoStorage.entity.Prodotto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;

@Controller
@RequestMapping("/abbonamento")
public class AbbonamentoController {
  private static final String abbonamentoView = "AbbonamentoView/Abbonamenti";

  @Autowired
  private AbbonamentoDao abbonamentoDao;

  @Autowired
  private SessionCliente sessionCliente;
    @Autowired
    private DisposizioneDao disposizioneDao;

  @GetMapping
  public String get(
          @RequestParam(name = "abbonamento", required = false) String tipo,
          @ModelAttribute AbbonamentoForm abbonamentoForm,
          Model model) {

    // Recupero l'abbonamento dal modello se non già presente
    if (!model.containsAttribute("abbonamento")) {
      Optional<Cliente> clienteOpt = sessionCliente.getCliente();
      clienteOpt.ifPresent(cliente -> {
        Abbonamento abbonamento = cliente.getAbbonamento();
        Optional.ofNullable(abbonamento)
                .ifPresent(a -> model.addAttribute("abbonamento", a.getId()));
      });
    }

    if (tipo != null && !tipo.isBlank()) {
      if (!tipo.equalsIgnoreCase("BRONZE") &&
              !tipo.equalsIgnoreCase("SILVER") &&
              !tipo.equalsIgnoreCase("GOLD")) {
        throw new IllegalArgumentException("Tipo di abbonamento non valido: " + tipo);
      }


      Abbonamento.TipoAbbonamento tipoAbbonamento = Abbonamento.TipoAbbonamento.valueOf(tipo);
      List<Abbonamento> abbonamenti = abbonamentoDao.findAbbonamentoByTipo(tipoAbbonamento);
      Map<Abbonamento, Map<Prodotto, Integer>> prodotti = new HashMap<>();
      for(Abbonamento abbonamento : abbonamenti) {
        prodotti.put(abbonamento, disposizioneDao.findByAllByAbbonamentoAndTipo(abbonamento, tipoAbbonamento));
      }
      model.addAttribute("abbonamenti", abbonamenti);
      model.addAttribute("prodotti", prodotti);
    } else {
      model.addAttribute("abbonamenti", List.of());
      model.addAttribute("prodotti", Map.of());
    }

    return abbonamentoView;
  }




  @PostMapping
  @ResponseBody
  public String post(@ModelAttribute @Valid AbbonamentoForm abbonamentoForm,
                     BindingResult bindingResult, Model model) {


    Optional<Cliente> clienteOpt = sessionCliente.getCliente();
    if (clienteOpt.isEmpty()) {
      return "redirect:/login";
    }


    Long idAbbonamento = abbonamentoForm.getId();
    Optional<Abbonamento> abbonamentoOptional = abbonamentoDao.findAbbonamentoById(idAbbonamento);
    if (abbonamentoOptional.isEmpty()) {
      return "redirect:/error";
    }


    Abbonamento abbonamento = abbonamentoOptional.get();
    Cliente cliente = clienteOpt.get();


    cliente.setAbbonamento(abbonamento);
    cliente.setSottoscrizione(new Timestamp(System.currentTimeMillis()));

    model.addAttribute("abbonamento", abbonamento.getId());


    if (model.containsAttribute("abbonamento")) {
      return abbonamentoView;
    } else {
      return "errore";
    }
  }
}