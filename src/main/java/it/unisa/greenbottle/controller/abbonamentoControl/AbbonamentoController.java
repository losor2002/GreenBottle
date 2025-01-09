package it.unisa.greenbottle.controller.abbonamentoControl;

import it.unisa.greenbottle.controller.abbonamentoControl.form.AbbonamentoForm;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.abbonamentoStorage.dao.AbbonamentoDao;
import it.unisa.greenbottle.storage.abbonamentoStorage.entity.Abbonamento;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import jakarta.validation.Valid;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/abbonamenti")
public class AbbonamentoController {
  private static final String abbonamentoView = "/abbonamenti";

  @Autowired
  private AbbonamentoDao abbonamentoDao;

  @Autowired
  private SessionCliente sessionCliente;

  @GetMapping
  public String get(@ModelAttribute AbbonamentoForm abbonamentoForm, Model model) {

    Optional<List<Abbonamento>> abbonamenti = Optional.of(abbonamentoDao.findAll());
    List<Abbonamento> abbonamentiList = abbonamenti.get();
    model.addAttribute("abbonamentiList", abbonamentiList);
    model.addAttribute("message", "Test messaggio");
    return "redirect:" + abbonamentoView;
  }


  @PostMapping
  @ResponseBody
  public String post(@ModelAttribute @Valid AbbonamentoForm abbonamentoForm,
                     BindingResult bindingResult, Model model) {

    Long id = abbonamentoForm.getId();
    Optional<Abbonamento> abbonamentoOptional = abbonamentoDao.findAbbonamentoById(id);
    if (abbonamentoOptional.isPresent()) {
      Abbonamento abbonamento = abbonamentoOptional.get();
      Optional<Cliente> clienteOptional = sessionCliente.getCliente();
      if (clienteOptional.isPresent()) {
        Cliente cliente = clienteOptional.get();
        cliente.setAbbonamento(abbonamento);
        cliente.setSottoscrizione(new Timestamp(System.currentTimeMillis()));
      }
    }

    return abbonamentoView;
  }
}
