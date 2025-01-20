package it.unisa.greenbottle.controller.accessoControl;

import it.unisa.greenbottle.controller.accessoControl.form.LoginForm;
import it.unisa.greenbottle.controller.accessoControl.util.JasyptUtil;
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

@Controller
@RequestMapping("/loginAdmin")
public class LoginAdminController {

  private static final String loginView = "/AccessoView/LoginAdmin";
  private static final String homeView = "/";


  @Autowired
  private AdminDao adminDao;

  @Autowired
  private SessionAdmin sessionAdmin;

  @GetMapping
  public String get(@ModelAttribute LoginForm loginForm, Model model) {
    model.addAttribute("nameLogin", "/loginAdmin");
    // DEBUG: System.out.println(JasyptUtil.encrypt("asdfAsdf123@")); ===>  1FU7WYQZftZbHQuBb3M5Tw==
    return loginView;
  }


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

    String encryptedPassword = JasyptUtil.encrypt(password);
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
