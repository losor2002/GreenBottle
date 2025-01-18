package it.unisa.greenbottle.controller.areaPersonaleControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/areaPersonale")
public class AreaPersonaleController {

  private final String areaPersonaleView = "/AreaPersonaleView/AreaPersonale";

  @Autowired
  private SessionCliente sessionCliente;

  @GetMapping
  public String get(Model model) {
    Cliente cliente = sessionCliente.getCliente().get();
    model.addAttribute("cliente", cliente);
    return areaPersonaleView;
  }
}
