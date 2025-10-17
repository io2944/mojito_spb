package cryorbiter.mojito_spb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NonTrouve.class)
    public String handleNotFoundMVC(NonTrouve ex, Model model) {
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorTitle", "Ressource introuvable");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("key", ex.getKey());
        return "error/error"; // vers error.html
    }

    @ExceptionHandler(ErreurDeValidation.class)
    public String handleValidationErrorMVC(ErreurDeValidation ex, Model model) {
        model.addAttribute("errorTitle", "Erreur de validation");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errors", ex.getErrors());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericMVC(Exception ex, Model model) {
        model.addAttribute("errorCode", 500);
        model.addAttribute("errorTitle", "Erreur interne");
        model.addAttribute("errorMessage", "Une erreur interne s’est produite. Merci de réessayer plus tard.");
        model.addAttribute("timestamp", LocalDateTime.now());
        System.err.println(" Exception capturée : " + ex.getMessage());
        ex.printStackTrace();
        return "error/error";
    }
}

