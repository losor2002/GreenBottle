package it.unisa.greenbottle.controller.abbonamentoControl;

import it.unisa.greenbottle.controller.abbonamentoControl.form.AbbonamentoForm;
import it.unisa.greenbottle.controller.abbonamentoControl.util.AbbonamentoWrapper;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.DisposizioneDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  private static final String checkoutAbbonamentoView = "AbbonamentoView/CheckoutAbbonamento";

  @Autowired
  private AbbonamentoDao abbonamentoDao;

  @Autowired
  private SessionCliente sessionCliente;
  @Autowired
  private DisposizioneDao disposizioneDao;
    @Autowired
    private ClienteDao clienteDao;

  @GetMapping
  public String get(
      @RequestParam(name = "abbonamento", required = false) String tipo, @RequestParam(name = "sottoscrivi", required = false) Long id, Model model) {
    // Roecupero l'abbonamento dal modello se non già presente
    /*if (!model.containsAttribute("abbonamento")) {
      Optional<Cliente> clienteOpt = sessionCliente.getCliente();
      clienteOpt.ifPresent(cliente -> {
        Abbonamento abbonamento = cliente.getAbbonamento();
        Optional.ofNullable(abbonamento)
            .ifPresent(a -> model.addAttribute("abbonamento", a.getId()));
      });
    }*/

    if(id != null) {
      model.addAttribute("abbonamento", abbonamentoDao.findAbbonamentoById(id).get());
      return checkoutAbbonamentoView;
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
        abbonamentiFinale.add(new AbbonamentoWrapper(abbonamento,
            disposizioneDao.findDisposizioneByAbbonamento(abbonamento)));
      }

      model.addAttribute("abbonamenti", abbonamentiFinale);
    } else {
      model.addAttribute("abbonamenti", List.of());
    }

    return abbonamentoView;

  }

  @PostMapping
  @ResponseBody
  public String post(@ModelAttribute @Valid AbbonamentoForm abbonamentoForm,
                     BindingResult bindingResult, Model model, HttpServletResponse httpServletResponse)
          throws IOException {

    if (bindingResult.hasErrors()) {
      // Se c'è un errore specifico per un campo, gestisci il messaggio
      bindingResult.getFieldErrors().forEach(fieldError -> {
        if ("numeroCarta".equals(fieldError.getField())) {
          model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
          model.addAttribute("message", "Numero di carta non valido.");

          try {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Numero di carta non valido.");
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        if ("dataScadenza".equals(fieldError.getField())) {
          model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
          model.addAttribute("message", "Data di scadenza della carta non valida.");

          try {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data di scadenza della carta non valida.");
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        if ("CVV".equals(fieldError.getField())) {
          model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
          model.addAttribute("message", "CVV non valido.");

          try {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "CVV non valido.");
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        if ("nomeTitolare".equals(fieldError.getField())) {
          model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
          model.addAttribute("message", "Nome del titolare della carta non valido.");

          try {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome del titolare della carta non valido.");
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      });
      return "error";  // Visualizza la vista con il messaggio di errore
    }

    Optional<Cliente> clienteOpt = sessionCliente.getCliente();
    if (clienteOpt.isEmpty()) {
      model.addAttribute("errore", "Non loggato.");
      return "redirect:/login";
    }


    Long idAbbonamento = abbonamentoForm.getId();
    Optional<Abbonamento> abbonamentoOptional = abbonamentoDao.findAbbonamentoById(idAbbonamento);
    if (abbonamentoOptional.isEmpty()) {
      httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Abbonamento non trovato.");
      return "redirect:/error";
    }



    Abbonamento abbonamento = abbonamentoOptional.get();
    Cliente cliente = clienteOpt.get();

    cliente.setAbbonamento(abbonamento);
    cliente.setSottoscrizione(new Timestamp(System.currentTimeMillis()));
    clienteDao.save(cliente);

    model.addAttribute("abbonamento", abbonamento.getId());


    if (model.containsAttribute("abbonamento")) {
      return abbonamentoView;
    } else {
      httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Errore nel sottoscrivere l'abbonamento.");
      return "redirect:/error";
    }
  }
}