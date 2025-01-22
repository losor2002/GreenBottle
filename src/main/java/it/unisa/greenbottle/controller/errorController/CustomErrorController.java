package it.unisa.greenbottle.controller.errorController;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("message", httpServletRequest.getAttribute("message"));
        model.addAttribute("status", httpServletRequest.getAttribute("status"));

        return "error";
    }
}
