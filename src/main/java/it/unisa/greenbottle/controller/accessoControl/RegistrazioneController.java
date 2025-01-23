package it.unisa.greenbottle.controller.accessoControl;

import it.unisa.greenbottle.controller.accessoControl.form.RegistrazioneForm;
import it.unisa.greenbottle.controller.accessoControl.util.JasyptUtil;
import it.unisa.greenbottle.storage.accessoStorage.dao.ClienteDao;
import it.unisa.greenbottle.storage.accessoStorage.entity.Cliente;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registrazione")
public class RegistrazioneController {

  private static final String registrazioneView = "/AccessoView/Registrazione";
  private static final String loginController = "/login";

  @Autowired
  private ClienteDao clienteDao;

  @GetMapping
  public String get(@ModelAttribute RegistrazioneForm registrazioneForm) {
    return registrazioneView;
  }

  @PostMapping
  public String post(@ModelAttribute @Valid RegistrazioneForm registrazioneForm,
                     BindingResult bindingResult, Model model,
                     HttpServletResponse httpServletResponse)
      throws IOException {

    if (bindingResult.hasErrors()) {
      // Se c'è un errore specifico per un campo, gestisci il messaggio
      FieldError fieldError = bindingResult.getFieldErrors().getFirst();
      model.addAttribute("message", fieldError.getDefaultMessage());
      model.addAttribute("status", HttpServletResponse.SC_BAD_REQUEST);
      httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, fieldError.getField());
      return "error";// Visualizza la vista con il messaggio di errore
    }

    String encryptedPassword = JasyptUtil.encrypt(registrazioneForm.getPassword());
    String email = registrazioneForm.getEmail();
    String nome = registrazioneForm.getNome();
    String cognome = registrazioneForm.getCognome();

    if (clienteDao.findClienteByEmail(email).isPresent()) {
      model.addAttribute("errore", "Email gia' registrata.");
      return registrazioneView;
    } else {
      Cliente c = new Cliente(email, encryptedPassword, nome, cognome, 0, 0, null, null);
      clienteDao.save(c);
    }

    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    return "redirect:" + loginController;
  }
}
