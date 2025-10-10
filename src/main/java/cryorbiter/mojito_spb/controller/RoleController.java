package cryorbiter.mojito_spb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import cryorbiter.mojito_spb.dto.RoleDto;
import cryorbiter.mojito_spb.form.RoleForm;
import cryorbiter.mojito_spb.service.RoleService;

@Controller
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roleslist")
    public String rolesList(Model model) {

        List<RoleDto> roles = roleService.getAllRoles();

        model.addAttribute("roles", roles);
        return "Role/listRole";
    }

    // Méthode mutualisée pour afficher le formulaire (création et édition)
    @GetMapping({ "/rolecreate", "/roleedit" })
    public String showRoleForm(@RequestParam(value = "roleId", required = false) Long id, Model model)
            throws ServletException, IOException {
        if (id != null) {
            // Mode édition : charger le rôle existant
            RoleDto role = roleService.getRoleById(id.longValue());
            RoleForm roleForm = new RoleForm();
            roleForm.setId(id);
            roleForm.setName(role.getName()); 
            model.addAttribute("role", roleForm);
            model.addAttribute("isEdit", true);
        } else {
            // Mode création : nouveau formulaire vide
            model.addAttribute("role", new RoleForm());
            model.addAttribute("isEdit", false);
        }
        return "Role/createRole";
    }

    // Méthode mutualisée pour traiter le formulaire (création et édition)
    @PostMapping({ "/rolecreate", "/roleedit" })
    public String processRoleForm(@RequestParam(value = "id", required = false) Long urlId,
            @Valid RoleForm roleForm,
            BindingResult bindingResult,
            Model model) throws ServletException, IOException {
        
        // Récupérer l'ID soit de l'URL soit du formulaire
        Long id = (urlId != null) ? urlId : roleForm.getId();
        
        if (!bindingResult.hasErrors()) {
            if (id != null) {
                // Mode édition
                roleService.updateRole(id, roleForm);
            } else {
                // Mode création
                roleService.createRole(roleForm);
            }
            return "redirect:/roleslist";
        } else {
            model.addAttribute("error", "Le formulaire contient des erreurs.");
            // En cas d'erreur, on garde l'id dans le formulaire pour le mode édition
            if (id != null) {
                roleForm.setId(id);
                model.addAttribute("isEdit", true);
            } else {
                model.addAttribute("isEdit", false);
            }
            model.addAttribute("role", roleForm);
            return "Role/createRole";
        }
    }

    @GetMapping("/roledelete/{id}")
    public String deleteRoleGet(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        roleService.deleteRole(id);
        redirectAttributes.addFlashAttribute("success", "Le rôle a été supprimé avec succès.");
        return "redirect:/roleslist";
    }
}
