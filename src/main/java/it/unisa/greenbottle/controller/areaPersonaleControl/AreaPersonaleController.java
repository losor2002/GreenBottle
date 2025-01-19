package it.unisa.greenbottle.controller.areaPersonaleControl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/areaPersonale")
public class AreaPersonaleController {

  private final String areaPersonaleView = "/AreaPersonaleView/AreaPersonale";


  @GetMapping
  public String get(Model model) {
    return areaPersonaleView;
  }
}
