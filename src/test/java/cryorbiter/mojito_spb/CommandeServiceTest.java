package cryorbiter.mojito_spb;

import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.mapper.CommandeMapper;
import cryorbiter.mojito_spb.model.Commande;
import cryorbiter.mojito_spb.repository.CommandeRepository;
import cryorbiter.mojito_spb.service.CommandeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class CommandeServiceTest {
    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private CommandeMapper commandeMapper;

    @InjectMocks
    private CommandeService commandeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCommandeDtoById() {
        Commande entity = new Commande();
        entity.setId(1L);
        entity.setPrixKilo(500.0);

        CommandeDto dto = new CommandeDto();
        dto.setId(1L);
        dto.setPrixKilo(500.0);

        when(commandeRepository.findById(1L)).thenReturn(java.util.Optional.of(entity));
        when(commandeMapper.toDto(entity)).thenReturn(dto);

        CommandeDto result = commandeService.getCommandeById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getPrixKilo()).isEqualTo(500.0);
    }
}

