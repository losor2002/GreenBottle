package it.unisa.greenbottle.controller.accessoControl;

import it.unisa.greenbottle.controller.accessoControl.util.SessionAdmin;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe gestisce il logout dell'utente e dell'amministratore.
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {

  @Autowired
  private SessionCliente sessionCliente;

  @Autowired
  private SessionAdmin sessionAdmin;

  /**
   * Questo metodo permette di effettuare il logout del cliente e dell'amministratore.
   *
   * @param session sessione del cliente
   * @return redirect alla home
   */
  @GetMapping
  public String get(HttpSession session) {
    if (sessionCliente != null) {
      sessionCliente.emptyCliente();
    }
    if (sessionAdmin != null) {
      sessionAdmin.emptyAdmin();
    }

    session.setAttribute("sessionCliente", sessionCliente);
    session.setAttribute("sessionAdmin", sessionAdmin);
    return "redirect:/";
  }
}
