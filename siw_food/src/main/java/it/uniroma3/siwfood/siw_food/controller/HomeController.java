package it.uniroma3.siwfood.siw_food.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Immagine;
import it.uniroma3.siwfood.siw_food.model.auth.Credentials;
import it.uniroma3.siwfood.siw_food.model.auth.Utente;
import it.uniroma3.siwfood.siw_food.service.CredentialsService;
import it.uniroma3.siwfood.siw_food.service.CuocoService;
import it.uniroma3.siwfood.siw_food.service.ImmagineService;
import it.uniroma3.siwfood.siw_food.service.UtenteService;
import jakarta.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private ImmagineService immagineService;
    
    //RESTITUISCE IL TEMPLATE DELL'HOME PAGE
    @GetMapping("/")
    public String getHomePage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 

        return "home.html";
    }

    //LOGIN
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        return "auth/login.html";
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
        
        model.addAttribute("cuoco", new Cuoco());
        model.addAttribute("utente", new Utente());
        model.addAttribute("credentials", new Credentials());

        return "auth/register.html";
    }
    
    @PostMapping("/register")
    public String postNewUtente(@Valid @ModelAttribute("utente") Utente utente, 
                                BindingResult utenteBindingResult, 
                                @Valid @ModelAttribute("credentials") Credentials credentials, 
                                BindingResult credentialsBindingResult, 
                                @ModelAttribute("cuoco") Cuoco cuoco, 
                                @RequestParam("immagine") MultipartFile immagine,
                                Model model) throws IOException {

        if(!immagine.isEmpty()){
            Immagine img = new Immagine();
            img.setFileName(immagine.getOriginalFilename());
            img.setImageData(immagine.getBytes());
            if (cuoco.getImmagini() == null) {
                cuoco.setImmagini(new ArrayList<>());
            }
            cuoco.getImmagini().add(img);
            this.immagineService.save(img);
        }
        
        if(!utenteBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()){
            
            
            //cuoco
            cuoco.setNome(utente.getNome());
            cuoco.setCognome(utente.getCognome());
            cuoco.setDataNascita(utente.getDataNascita());
            cuocoService.saveCuoco(cuoco);

            utente.setCuoco(cuoco);
            utenteService.saveUtente(utente);


            credentials.setUtente(utente);
            credentials.setRole(Credentials.UTENTE_REGISTRATO);
            
            credentialsService.saveCredentials(credentials);
            
            model.addAttribute("utente", utente);

            return "redirect:/";
        }
        
        return "auth/register.html";
    }
    
    //TODO capire come togliere i warning
    
    
}
