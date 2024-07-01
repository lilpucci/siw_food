package it.uniroma3.siwfood.siw_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siwfood.siw_food.model.Credentials;
import it.uniroma3.siwfood.siw_food.model.Utente;
import it.uniroma3.siwfood.siw_food.service.CredentialsService;
import it.uniroma3.siwfood.siw_food.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UtenteService utenteService;
    
    //RESTITUISCE IL TEMPLATE DELL'HOME PAGE
    @GetMapping("/")
    public String getHomePage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 

        return "home.html";
    }

    //LOGIN
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "auth/formLogin.html";
    }

    @GetMapping("/success")
    public String getHomeAfterLogin(Model model) {
        
        //capire come vengono passati  o comunque cosa ci fanno
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());

        return "home.html";
    }


    //REGISTRAZIONE
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        
        model.addAttribute("utente", new Utente());
        model.addAttribute("credentials", new Credentials());

        return "auth/formRegistrazione.html";
    }
    
    @PostMapping("/register")
    public String postNewUtente(@Valid @ModelAttribute("utente") Utente utente, BindingResult utenteBindingResult, @Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, Model model) {
        
        if(!utenteBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()){
            utenteService.saveUtente(utente);
            credentials.setUtente(utente);

            credentials.setRole(Credentials.UTENTE_REGISTRATO);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("utente", utente);

            return "redirect:/";
        }
        
        return "auth/formRegistrazione.html";
    }
    
    //TODO capire come togliere i warning
    
    
}
