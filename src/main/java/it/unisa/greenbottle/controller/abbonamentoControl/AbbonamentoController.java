package it.unisa.greenbottle.controller.abbonamentoControl;

import it.unisa.greenbottle.controller.abbonamentoControl.form.AbbonamentoForm;
import it.unisa.greenbottle.controller.abbonamentoControl.util.AbbonamentoWrapper;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.DisposizioneDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Disposizione;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


      Abbonamento.TipoAbbonamento tipoAbbonamento =
          Abbonamento.TipoAbbonamento.valueOf(tipo.toUpperCase());
      List<Abbonamento> abbonamenti = abbonamentoDao.findAbbonamentoByTipo(tipoAbbonamento);
      List<AbbonamentoWrapper> abbonamentiFinale = new ArrayList<>();
      for (Abbonamento abbonamento : abbonamenti) {
        abbonamentiFinale.add(new AbbonamentoWrapper(abbonamento, disposizioneDao.findDisposizioneByAbbonamento(abbonamento)));
      }

      model.addAttribute("abbonamenti", abbonamenti);
    } else {
      model.addAttribute("abbonamenti", List.of());
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