package cryorbiter.mojito_spb.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            model.addAttribute("errorCode", statusCode);         

            switch (statusCode) {
                case 404 -> model.addAttribute("errorMessage", "Page non trouvée");
                case 500 -> model.addAttribute("errorMessage", "Erreur interne du serveur");
                case 403 -> model.addAttribute("errorMessage", "Accès interdit");
                default -> model.addAttribute("errorMessage", "Une erreur s'est produite");
            }
        } else {
            System.out.println("Pas de code d'erreur trouvé");
            model.addAttribute("errorCode", "Unknown");
            model.addAttribute("errorMessage", "Une erreur s'est produite");
        }

        return "error/error";
    }
}
