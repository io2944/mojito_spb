package cryorbiter.mojito_spb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Page publique de login
    @GetMapping("/login")
    public String displayLoginForm() {
        return "mylogin";
    }

    // Page d’accueil après login
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // src/main/resources/templates/home.html
    }

}
