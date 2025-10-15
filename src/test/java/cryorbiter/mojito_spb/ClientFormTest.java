package cryorbiter.mojito_spb;

import cryorbiter.mojito_spb.form.ClientForm;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles("test")
public class ClientFormTest {
    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testInvalidEmail() {
        ClientForm dto = new ClientForm();
        dto.setEmail("invalid");

        var violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }
}
