package cryorbiter.mojito_spb.service;

import java.util.List;
import java.util.Optional;

import cryorbiter.mojito_spb.model.Garantie;
import org.springframework.beans.factory.annotation.Autowired;
import cryorbiter.mojito_spb.dto.GarantieDto;
import cryorbiter.mojito_spb.mapper.GarantieMapper;
import cryorbiter.mojito_spb.repository.GarantieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GarantieService {

    private final GarantieRepository garantieRepository;
    private final GarantieMapper garantieMapper;

    @Autowired
    public GarantieService(GarantieRepository garantieRepository,GarantieMapper garantieMapper) {
        this.garantieRepository = garantieRepository;
        this.garantieMapper = garantieMapper;
    }

    public GarantieDto creerGarantie(GarantieDto garantie) {
        if (garantie == null) {
            throw new IllegalArgumentException("garantie doit être renseignée");
        }
        
        Garantie entity = garantieMapper.toEntity(garantie);
        Garantie savedEntity = garantieRepository.save(entity);
        return garantieMapper.toDto(savedEntity);
    }

    public GarantieDto getGarantieById(long id) {
        Optional<Garantie> garantieOpt = garantieRepository.findById(id);
        return garantieOpt.map(garantieMapper::toDto).orElse(null);
    }

    public List<GarantieDto> getAllGaranties() {
        return garantieMapper.toDtoList(garantieRepository.findAll());
    }

    public void modifierGarantie(GarantieDto garantie) {
        if (garantie == null) {
            throw new IllegalArgumentException("garantie doit être renseignée");
        }
        
        if (garantie.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant de la garantie doit être renseigné pour la modification");
        }
        
        // Vérifier que la garantie existe et utiliser la méthode update du mapper
        Optional<Garantie> existingEntityOpt = garantieRepository.findById(garantie.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new IllegalArgumentException("Aucune garantie trouvée avec l'identifiant " + garantie.getId());
        }
        
        Garantie existingEntity = existingEntityOpt.get();
        garantieMapper.updateEntity(existingEntity, garantie);
        garantieRepository.save(existingEntity);
    }

    public void supprimerGarantie(GarantieDto garantie) {
        if (garantie == null) {
            throw new IllegalArgumentException("garantie doit être renseignée");
        }
        
        if (garantie.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant de la garantie doit être renseigné pour la suppression");
        }
        
        garantieRepository.deleteById(garantie.getId());
    }
}
