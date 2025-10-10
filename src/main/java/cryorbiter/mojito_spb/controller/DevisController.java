package cryorbiter.mojito_spb.controller;

import java.util.List;

import cryorbiter.mojito_spb.service.DevisService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import cryorbiter.mojito_spb.service.ClientService;
import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.enumerations.StatutDevis;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import cryorbiter.mojito_spb.form.DevisForm;

@Controller
@Transactional
public class DevisController {

	public static final String SESSION_DEVIS = "devises";
	private ClientService clientService;
	private DevisService devisService;

	public DevisController(ClientService clientService, DevisService devisService) {
		this.clientService = clientService;
		this.devisService = devisService;
	}

	@GetMapping("/listeDevis")
    public String showForm(Model model, HttpSession httpSession) {
		List<DevisDto> listDevis = devisService.getAllDevis();
		model.addAttribute("listDevis", listDevis);
		model.addAttribute("statuts", StatutDevis.values());
		return "Devis/listeDevis";
	}

	@GetMapping("/createDevis")
	public String showForm(DevisForm devisForm, Model model) {
		model.addAttribute("devisForm", new DevisForm());
		//model.addAttribute("isEdit", false);
		// Récupérer la liste des clients depuis le repository
		List<ClientDto> clients = clientService.getAllClients();
		model.addAttribute("clients", clients);
		// Enum directement
		model.addAttribute("types", TypeDocument.values());
		model.addAttribute("statuts", StatutDevis.values());
		return "Devis/createDevis";
	}

	@PostMapping("/createDevis")
	public String checkDevisInfo(@Validated @ModelAttribute("devisForm") DevisForm devisForm,
			BindingResult bindingResult, Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			// Affichage du formulaire avec les erreurs
			bindingResult.getAllErrors().forEach(e -> System.out.println(e)); // <--- affichera toutes les erreurs

			//model.addAttribute("isEdit", false);
			model.addAttribute("types", TypeDocument.values());
			model.addAttribute("statuts", StatutDevis.values());
			System.out.println("*** Erreur : redirection vers /createDevis ***");
			return "Devis/createDevis";
		}
		DevisDto devis = new DevisDto();


		devis.setLibelle(devisForm.getLibelle());
		devis.setDate(devisForm.getDate());
		devis.setNomDocument(devisForm.getNomDocument());
		devis.setPoids(devisForm.getPoids());
		devis.setTva(devisForm.getTva());
		devis.setType(devisForm.getType());
		devis.setLibelle(devisForm.getLibelle());
		devis.setPrixKilo(devisForm.getPrixKilo());
		devis.setStatut(devisForm.getStatut());
		Long clientId = devisForm.getClientId();
		if (clientId != null) {
			ClientDto client = clientService.getClientById(clientId);// DTO retourné par le service
			devis.setClient(client);
		} else {
			bindingResult.rejectValue("clientId", "error.devisForm", "Le client est obligatoire.");
			return "Devis/createDevis";
		}

		DevisDto saved = devisService.creerDevis(devis);

		//Ajout du bean DevisEntity et de l'objet métier form à la requête
		model.addAttribute("devis", devis);


		//Ajout du bean DevisEntity et de l'objet métier form à la requête
		// model.addAttribute("devis", devis);

			// Pas d'erreur : r&eacute;cup&eacute;ration de la Map des clients dans la session
		//Map<Long, DevisDto> devises = (HashMap<Long, DevisDto>) session.getAttribute(SESSION_DEVIS);


//		if (devises == null) {
//				// Initialisation d'une Map si rien en session
//				devises = new HashMap<>();
//		}
		// Ajout du client courant dans la Map
		// devises.put(devis.getId(), saved);
		// Repositionnement de la Map en session
		// session.setAttribute(SESSION_DEVIS, devises);

		System.out.println("*** Pas d'erreur : redirection vers /listeDevis ***");
		return "redirect:/listeDevis";

	}

	@GetMapping("/updateDevis/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
		DevisDto devis = devisService.getDevisById(id);
		if (devis == null) {
			return "redirect:/listeDevis"; // sécurité si ID inconnu
		}

		DevisForm devisForm = new DevisForm();
		devisForm.setLibelle(devis.getLibelle());
		devisForm.setDate(devis.getDate());
		devisForm.setPoids(devis.getPoids());
		devisForm.setTva(devis.getTva());
		devisForm.setType(devis.getType());
		devisForm.setPrixKilo(devis.getPrixKilo());
		devisForm.setStatut(devis.getStatut());
		devisForm.setCommentaire(devis.getCommentaire());
		devisForm.setNomDocument(devis.getNomDocument());
		devisForm.setClientId(devis.getClient().getId()); // <--- on passe le client dès le début

		model.addAttribute("devisForm", devisForm);
		model.addAttribute("devisId", devis.getId());
		//model.addAttribute("isEdit", true);
		// Enum directement
		model.addAttribute("types", TypeDocument.values());
		model.addAttribute("statuts", StatutDevis.values());

		System.out.println(model.asMap());
		return "Devis/updateDevis";
	}

	// Mise à jour
	@PostMapping("/updateDevis/{id}")
	public String updateDevis(@PathVariable("id") Long id, @Validated @ModelAttribute("devisForm") DevisForm devisForm,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			//model.addAttribute("isEdit", true);
			bindingResult.getAllErrors().forEach(e -> System.out.println(e)); // <--- affichera toutes les erreurs
			model.addAttribute("devisId", id);
			model.addAttribute("types", TypeDocument.values());
			model.addAttribute("statuts", StatutDevis.values());
			return "Devis/updateDevis";
		}

		DevisDto devis = devisService.getDevisById(id);
		if (devis == null) {
			return "redirect:/listeDevis";
		}

		devis.setLibelle(devisForm.getLibelle());
		devis.setDate(devisForm.getDate());
		devis.setPoids(devisForm.getPoids());
		devis.setTva(devisForm.getTva());
		devis.setType(devisForm.getType());
		devis.setPrixKilo(devisForm.getPrixKilo());
		devis.setStatut(devisForm.getStatut());
		devis.setCommentaire(devisForm.getCommentaire());
		devis.setNomDocument(devisForm.getNomDocument());
		Long clientId = devisForm.getClientId();
		if (clientId != null) {
			ClientDto client = clientService.getClientById(clientId);// DTO retourné par le service
			devis.setClient(client);
		} else {
			bindingResult.rejectValue("clientId", "error.devisForm", "Le client est obligatoire.");
			System.out.println("*** Erreur : pas de client attaché -> redirection vers /createDevis ***");
			return "Devis/updateDevis";
		}

		devisService.modifierDevis(devis);

		return "redirect:/listeDevis";
	}

	@GetMapping("deleteDevis")
	public String deleteDevis(@RequestParam("id") Long id, Model model, HttpSession session) {
		try {
			DevisDto devis = devisService.getDevisById(id);
			if (devis != null) {
				devisService.supprimerDevis(devis);
				session.removeAttribute(SESSION_DEVIS);
			}
		} catch (Exception e) {
			System.out.println("*** Erreur : redirection vers /deleteDevis ***");
		}
		return "redirect:/listeDevis";
	}

	@GetMapping("/viewDevis/{id}")
	public String viewDevis(@PathVariable("id") Long id, Model model) {
		DevisDto devis = devisService.getDevisById(id);
		if (devis == null) {
			return "redirect:/listeDevis"; // sécurité si l'id n'existe pas
		}
		model.addAttribute("devis", devis);
		return "Devis/viewDevis"; // JSP pour visualiser le devis
	}

}
