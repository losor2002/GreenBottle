package it.unisa.greenbottle.controller.accessoControl;

import it.unisa.greenbottle.controller.accessoControl.form.LoginForm;
import it.unisa.greenbottle.controller.accessoControl.util.EncryptorUtil;
import it.unisa.greenbottle.controller.accessoControl.util.SessionCliente;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe LoginController, utilizzata per la gestione del login del cliente.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

  private static final String loginView = "/AccessoView/Login";

  @Autowired
  private ClienteDao clienteDao;

  @Autowired
  private SessionCliente sessionCliente;

  /**
   * Metodo per ottenere la pagina di login del cliente.
   *
   * @param loginForm form di login
   * @param model     model
   * @return pagina di login del cliente
   */
  @GetMapping
  public String get(@ModelAttribute LoginForm loginForm, Model model) {
    model.addAttribute("nameLogin", "/login");
    // DEBUG: System.out.println(JasyptUtil.encrypt("asdfAsdf123@")); ===>  1FU7WYQZftZbHQuBb3M5Tw==
    return loginView;
  }

  /**
   * Metodo per effettuare il login del cliente.
   * Se l'email non esiste o la password è errata, ritorna la pagina di login.
   *
   * @param loginForm     form di login
   * @param bindingResult binding result
   * @param model         model
   * @param session       sessioneHttp
   * @return redirect all'area personale del cliente
   */
  @PostMapping
  public String post(@ModelAttribute @Valid LoginForm loginForm,
                     BindingResult bindingResult, Model model, HttpSession session) {
    model.addAttribute("nameLogin", "/login");
    if (bindingResult.hasErrors()) {
      return loginView;
    }

    String email = loginForm.getEmail();
    String password = loginForm.getPassword();

    Optional<Cliente> c = clienteDao.findClienteByEmail(email);
    boolean existsEmail = c.isPresent();
    model.addAttribute("existsEmail", existsEmail);
    if (!existsEmail) {
      return loginView;
    }

    String encryptedPassword = EncryptorUtil.encrypt(password);
    if (!c.get().getPassword().equals(encryptedPassword)) {
      model.addAttribute("correctPassword", false);
      return loginView;
    }
    model.addAttribute("correctPassword", true);

    sessionCliente.setCliente(c.get());
    session.setAttribute("sessionCliente", sessionCliente);
    return "redirect:" + "/areaPersonale";
  }
}
