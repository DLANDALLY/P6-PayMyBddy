package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.ProfileForm;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class ProfileController {
    private IUser userService;

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Principal principal, Model model) {
        log.info("GET Profile");

        User user;
        if (session.getAttribute("user") != null) {
            User userSession = (User) session.getAttribute("user");
            user = userService.getUserByEmail(userSession.getEmail());}
        else if (principal.getName() != null){
            user = userService.getUserByEmail(principal.getName());
            session.setAttribute("user", user);}
        else return "redirect:/login";

        String message = (String) session.getAttribute("errorUpdate");
        if (message != null)
            model.addAttribute("errorMessage", message);

        prepareUserProfileModel(model, user);
        return "profile";
    }

    @GetMapping("/profileGH")
    public String showProfileProvideGithub(OAuth2AuthenticationToken authenticationToken,
                                           HttpSession session, Model model){
        log.info("Authentification Github ");
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        String username = (String) attributes.get("login");
        String email = (String) attributes.get("email");

        User user;
        if (email != null){
            user = userService.getUserByEmail(email);
            session.setAttribute("user", user);
            prepareUserProfileModel(model, user);
            return "profile";
        }

        session.setAttribute("username", username);
        return "redirect:/register";
    }

    private void prepareUserProfileModel(Model model, User user){
        model.addAttribute("user", user);
        model.addAttribute("profileForm", new ProfileForm(user.getUsername(),user.getEmail()));
    }

    @PostMapping("/updateprofile")
    public String setProfile(@Validated @ModelAttribute ProfileForm profileForm,
                             BindingResult result, Model model, HttpSession session){
        log.info("Update Profile");
        User userSession = (User) session.getAttribute("user");

        if (result.hasErrors()) {
            model.addAttribute("user", userSession);
            return "profile";
        }

        try {
            User user = userService.updateProfile(profileForm, userSession.getId());
            session.setAttribute("user", user);

            model.addAttribute("user", user);
            model.addAttribute("successMessage", "Profile updated successfully");
            return "profile";

        }catch (IllegalArgumentException iae){
            session.setAttribute("errorUpdate", iae.getMessage());
            return "redirect:/profile";
        }
    }
}
