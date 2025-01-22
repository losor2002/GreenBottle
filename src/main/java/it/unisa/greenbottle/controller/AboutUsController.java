package it.unisa.greenbottle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

  private String footerView = "/AboutUs";

  @GetMapping(value = {"/AboutUs"})
  public String home() {
    return footerView;
  }
}
