package cryorbiter.mojito_spb.exception;

import java.util.List;

public class ErreurDeValidation extends Exception {
    private List<String> errors;

    public ErreurDeValidation(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }

    // pour afficer une trace de l'erreur
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ErreurDeValidation [");
        if (errors != null) {
            for (String error : errors) {
                builder.append(error).append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
