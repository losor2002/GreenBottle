package it.unisa.greenbottle.controller.ordineControl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisualizzaCarrelloController {

  private static final String CARRELLO_VIEW = "/OrdineView/Carrello";

  @GetMapping("/carrello")
  public String get(Model model) {
    return CARRELLO_VIEW;
  }
}
