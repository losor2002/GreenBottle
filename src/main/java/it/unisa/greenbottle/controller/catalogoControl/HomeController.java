package it.unisa.greenbottle.controller.catalogoControl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//MOMENTANEO: Mi serviva solo per visualizzare la homepage dal web per verificare l'adeguatezza di css

@Controller
public class HomeController {

  @GetMapping("/home")
  public String home() {

    return "/CatalogoView/Home";
  }
}
