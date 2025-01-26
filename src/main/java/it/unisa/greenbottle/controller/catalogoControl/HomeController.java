package it.unisa.greenbottle.controller.catalogoControl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Questa classe gestisce le richieste GET per la home page e la pagina aboutUs.
 */
@Controller
public class HomeController {
  private String homeView = "/CatalogoView/Home";
  private String aboutUsView = "/CatalogoView/AboutUs";

  @GetMapping(value = {"/", "/home"})
  public String home() {
    return homeView;
  }

  @GetMapping(value = {"/aboutUs"})
  public String aboutUs() {
    return aboutUsView;
  }
}
