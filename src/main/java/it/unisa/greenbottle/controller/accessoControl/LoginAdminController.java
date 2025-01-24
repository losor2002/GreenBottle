package it.unisa.greenbottle.controller.accessoControl;

import it.unisa.greenbottle.controller.accessoControl.form.LoginForm;
import it.unisa.greenbottle.controller.accessoControl.util.EncryptorUtil;
import it.unisa.greenbottle.controller.accessoControl.util.SessionAdmin;
import it.unisa.greenbottle.storage.accessoStorage.dao.AdminDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Admin;
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
 * Classe LoginAdminController, utilizzata per la gestione del login dell'admin.
 */
@Controller
@RequestMapping("/loginAdmin")
public class LoginAdminController {

  private static final String loginView = "/AccessoView/LoginAdmin";

  @Autowired
  private AdminDao adminDao;

  @Autowired
  private SessionAdmin sessionAdmin;

  /**
   * Metodo per ottenere la pagina di login dell'admin.
   *
   * @param loginForm form di login
   * @param model     model
   * @return pagina di login dell'admin
   */
  @GetMapping
  public String get(@ModelAttribute LoginForm loginForm, Model model) {
    model.addAttribute("nameLogin", "/loginAdmin");
    // DEBUG: System.out.println(JasyptUtil.encrypt("asdfAsdf123@")); ===>  1FU7WYQZftZbHQuBb3M5Tw==
    return loginView;
  }

  /**
   * Metodo per effettuare il login dell'admin.
   * Se l'email non esiste o la password è errata, ritorna la pagina di login.
   *
   * @param loginForm     form di login
   * @param bindingResult binding result
   * @param model         model
   * @param session       sessioneHttp
   * @return redirect alla dashboard dell'admin
   */
  @PostMapping
  public String post(@ModelAttribute @Valid LoginForm loginForm,
                     BindingResult bindingResult, Model model, HttpSession session) {
    model.addAttribute("nameLogin", "/loginAdmin");
    if (bindingResult.hasErrors()) {
      return loginView;
    }

    String email = loginForm.getEmail();
    String password = loginForm.getPassword();

    Optional<Admin> a = adminDao.findAdminByEmail(email);
    boolean existsEmail = a.isPresent();
    model.addAttribute("existsEmail", existsEmail);
    if (!existsEmail) {
      return loginView;
    }

    String encryptedPassword = EncryptorUtil.encrypt(password);
    if (!a.get().getPassword().equals(encryptedPassword)) {
      model.addAttribute("correctPassword", false);
      return loginView;
    }
    model.addAttribute("correctPassword", true);

    sessionAdmin.setAdmin(a.get());
    session.setAttribute("admin", sessionAdmin);
    return "redirect:" + "/admin";
  }
}
