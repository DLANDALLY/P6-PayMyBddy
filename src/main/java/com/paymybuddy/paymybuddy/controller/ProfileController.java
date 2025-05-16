package com.paymybuddy.paymybuddy.controller;

import com.paymybuddy.paymybuddy.entities.User;
import com.paymybuddy.paymybuddy.form.ProfileForm;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class ProfileController {
    private IUser userService;
    private OAuth2AuthorizedClientService clientService;

    //TODO : a faire
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Principal principal, Model model) {
        User user = userService.getUserByEmail(principal.getName());
        session.setAttribute("user", user);

        System.out.println("GET ShowProfile id "+ user.getId());
        String message = (String) session.getAttribute("errorUpdate");
        if (message != null) model.addAttribute("errorMessage", message);

        model.addAttribute("user", user);
        model.addAttribute("profileForm", new ProfileForm());
        return "profile";
    }

    @GetMapping("/profileGH")
    public String showProfileProvideGithub(OAuth2AuthenticationToken authenticationToken,
                                           HttpSession session, Model model){
        log.info("Authentification Github ");
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        System.out.println("Attributes -> "+ authenticationToken );


        String username = (String) attributes.get("login");
        System.out.println("pseudo -> " + username);

        String email = (String) attributes.get("email");
        System.out.println("email -> "+ email );

        User user = new User();
        if (email != null){
            user = userService.getUserByEmail(email);
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            model.addAttribute("profileForm", new ProfileForm());
            return "profile";
        }

        session.setAttribute("username", username);
        return "redirect:/register";
    }

    @PostMapping("/updateprofile")
    public String setProfile(@Valid @ModelAttribute ProfileForm profileForm,
                             BindingResult result, Model model, HttpSession session){
        log.info("Update Profile");
        if (result.hasErrors()) return "updateprofile";
        try {
            User user = userService.updateProfile(profileForm);
            model.addAttribute("user", user);
            return "/profile";

        }catch (IllegalArgumentException iae){
            session.setAttribute("errorUpdate", iae.getMessage());
            return "redirect:/profile";
        }
    }


    //TODO : à faire
    @GetMapping("/addbuddy")
    public String addBuddy(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "relation";
    }
}
