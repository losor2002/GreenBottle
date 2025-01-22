package it.unisa.greenbottle.controller.ordineControl;

import it.unisa.greenbottle.controller.ordineControl.util.SessionCarrello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class VisualizzaCarrelloController {

  @Autowired
  private SessionCarrello sessionCarrello;

  private static final String CARRELLO_VIEW = "/OrdineView/Carrello";

  @GetMapping("/carrello")
  public String get(Model model) {

    model.addAttribute("carrello", sessionCarrello.getRealCarrello());
    return CARRELLO_VIEW;
  }
}
