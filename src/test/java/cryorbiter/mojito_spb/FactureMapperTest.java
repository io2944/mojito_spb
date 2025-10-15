package cryorbiter.mojito_spb;

import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.mapper.FactureMapper;
import cryorbiter.mojito_spb.model.Facture;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
public class FactureMapperTest {
    private final FactureMapper mapper = new FactureMapper(); // si tu utilises MapStruct

    @Test
    void testToDto() {
        Facture f = new Facture();
        f.setId(10L);
        f.setPrixKilo(200.5);

        FactureDto dto = mapper.toDto(f);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getPrixKilo()).isEqualTo(200.5);
    }

    @Test
    void testToEntity() {
        FactureDto dto = new FactureDto();
        dto.setId(10L);
        dto.setPrixKilo(200.5);

        Facture entity = mapper.toEntity(dto);

        assertThat(entity.getPrixKilo()).isEqualTo(200.5);
    }
}

