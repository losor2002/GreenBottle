package it.unisa.greenbottle.controller.catalogoControl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
  private String homeView = "/CatalogoView/Home";

  @GetMapping(value = {"/", "/home"})
  public String home() {
    return homeView;
  }
}
