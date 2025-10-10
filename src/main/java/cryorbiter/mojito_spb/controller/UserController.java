package cryorbiter.mojito_spb.controller;

import java.util.List;

import cryorbiter.mojito_spb.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import cryorbiter.mojito_spb.dto.UserDto;
import cryorbiter.mojito_spb.form.UserForm;
import cryorbiter.mojito_spb.service.RoleService;
import cryorbiter.mojito_spb.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/userslist")
    public String usersList(Model model) {

        List<UserDto> users = userService.getAllUsers();

        model.addAttribute("users", users);
        return "User/listUser";
    }

    // Méthode mutualisée pour afficher le formulaire (création et édition)
    @GetMapping({ "/usercreate", "/useredit" })
    public String showUserForm(@RequestParam(value = "userId", required = false) Long id, Model model) {
        if (id != null) {
            // Mode édition : charger l'utilisateur existant
            UserDto user = userService.getUserById(id);
            UserForm userForm = new UserForm();
            userForm.setId(id);
            userForm.setUsername(user.getUsername());
            userForm.setEmail(user.getEmail());
            userForm.setPassword(user.getPassword());
            userForm.setRoles(user.getRoles().stream().map(RoleDto::getId).toList().toArray(Long[]::new));
            model.addAttribute("user", userForm);
            model.addAttribute("isEdit", true);
        } else {
            // Mode création : nouveau formulaire vide
            model.addAttribute("user", new UserForm());
            model.addAttribute("isEdit", false);
        }
        model.addAttribute("roles", roleService.getAllRoles());
        return "User/createUser";
    }

    // Méthode mutualisée pour traiter le formulaire (création et édition)
    @PostMapping({ "/usercreate", "/useredit" })
    public String processUserForm(@RequestParam(value = "id", required = false) Long urlId,
            @Valid UserForm userForm,
            BindingResult bindingResult,
            Model model) {

        // Récupérer l'ID soit de l'URL soit du formulaire
        Long id = (urlId != null) ? urlId : userForm.getId();

        if (!bindingResult.hasErrors()) {
            if (id != null) {
                // Mode édition
                userService.updateUser(userForm);
            } else {
                // Mode création
                userService.createUser(userForm);
            }
            return "redirect:/userslist";
        } else {
            model.addAttribute("error", "Le formulaire contient des erreurs.");
            // En cas d'erreur, on garde l'id dans le formulaire pour le mode édition
            if (id != null) {
                userForm.setId(id);
                model.addAttribute("isEdit", true);
            } else {
                model.addAttribute("isEdit", false);
            }
            model.addAttribute("roles", roleService.getAllRoles());
            model.addAttribute("user", userForm);
            return "User/createUser";
        }
    }

    @GetMapping("/userdelete/{id}")
    public String deleteUserGet(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userService.getUserById(id));
        redirectAttributes.addFlashAttribute("success", "L'utilisateur a été supprimé avec succès.");
        return "redirect:/userslist";
    }

    @GetMapping("/userview")
    public String viewUser(@RequestParam("userId") Long id, Model model) {
        UserDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "User/viewUser";
    }
}
