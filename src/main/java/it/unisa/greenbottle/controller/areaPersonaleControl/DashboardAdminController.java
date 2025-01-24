package it.unisa.greenbottle.controller.areaPersonaleControl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Questa classe gestisce le richieste relative alla dashboard dell'admin.
 */
@Controller
@RequestMapping("/admin")
public class DashboardAdminController {
  @GetMapping
  public String dashboard() {
    return "AreaPersonaleView/DashboardAdmin";
  }
}
