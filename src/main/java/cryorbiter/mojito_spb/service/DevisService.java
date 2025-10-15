package cryorbiter.mojito_spb.service;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.mapper.ClientMapper;
import cryorbiter.mojito_spb.mapper.CommandeMapper;
import cryorbiter.mojito_spb.mapper.DevisMapper;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.model.Devis;
import cryorbiter.mojito_spb.repository.DevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DevisService {

    private final DevisRepository devisRepository;
    private final DevisMapper devisMapper;
    private final ClientMapper clientMapper;

    @Autowired
    public DevisService(DevisRepository devisRepository, DevisMapper devisMapper, ClientMapper clientMapper) {
        this.devisRepository = devisRepository;
        this.devisMapper = devisMapper;
        this.clientMapper = clientMapper;
    }

    public DevisDto getDevisById(long id) {
        Optional<Devis> devisOpt = devisRepository.findById(id);
        return devisOpt.map(devisMapper::toDto).orElse(null);
    }

    public DevisDto creerDevis(DevisDto devis) {
        if (devis == null) {
            throw new IllegalArgumentException("devis doit être renseigné");
        }

        // Format de date pour le nom
        String dateStr = new java.text.SimpleDateFormat("yyyyMMdd").format(devis.getDate());

        // Compter combien de devis existent pour cette date
        long countToday = devisRepository.countByDate(devis.getDate());

        // Numéro séquentiel du jour, 001, 002, ...
        String numero = String.format("%03d", countToday + 1);

        // Définir le nom du document
        devis.setNomDocument("DEV_" + dateStr + "_" + numero);

        // Sauvegarde
        var savedEntity = devisRepository.save(devisMapper.toEntity(devis));
        return devisMapper.toDto(savedEntity);
    }

    public void modifierDevis(DevisDto devis) {
        if (devis == null) {
            throw new IllegalArgumentException("devis doit être renseigné");
        }

        if (devis.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant du devis doit être renseigné pour la modification");
        }

        // Vérifier que le devis existe et utiliser la méthode update du mapper
        Optional<Devis> existingEntityOpt = devisRepository.findById(devis.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new IllegalArgumentException("Aucun devis trouvé avec l'identifiant " + devis.getId());
        }

        Devis existingEntity = existingEntityOpt.get();
        devisMapper.updateEntity(existingEntity, devis);
        devisRepository.save(existingEntity);
    }

    public void supprimerDevis(DevisDto devis) {
        if (devis == null) {
            throw new IllegalArgumentException("devis doit être renseigné");
        }

        if (devis.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant du devis doit être renseigné pour la suppression");
        }

        devisRepository.deleteById(devis.getId());
    }

    public List<DevisDto> getAllDevis() {
        System.out.println("debug" + devisRepository.findAll());
        return devisMapper.toDtoList(devisRepository.findAll());
    }

    public List<DevisDto> getAllByClient(ClientDto client) {
        Client entityClient = clientMapper.toEntity(client);
        List<Devis> devises = devisRepository.findAllByClient(entityClient);
        return devises.stream()
                .map(devisMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<DevisDto> getAllDevis(Pageable pageable) {
        return devisRepository.findAll(pageable)
                .map(devisMapper::toDto);
    }

    public List<DevisDto> findByLibelle(String libelle) {
        return devisRepository.findByLibelleContainingIgnoreCase(libelle)
                .stream()
                .map(devisMapper::toDto)
                .toList();
    }

    public Long count() {
        return devisRepository.count();
    }
}