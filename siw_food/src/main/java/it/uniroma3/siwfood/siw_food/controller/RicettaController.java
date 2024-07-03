package it.uniroma3.siwfood.siw_food.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Ingrediente;
import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.model.auth.Utente;
import it.uniroma3.siwfood.siw_food.service.CuocoService;
import it.uniroma3.siwfood.siw_food.service.IngredienteService;
import it.uniroma3.siwfood.siw_food.service.RicettaService;
import it.uniroma3.siwfood.siw_food.service.UtenteService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RicettaController {
    
    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private IngredienteService ingredienteService;


    /*RICERCHE*/   //LE POSSONO FARE TUTTI
    //risponde all richiesta get che mi indirizza al template che mostra tutte le ricette
    @GetMapping("/ricette")   
    public String getRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "ricette.html";
    }
    
    //risponde con il template che mostra i dettagli della singola ricetta
    @GetMapping("/ricette/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "ricetta.html";
    }

    //metodo che mi porta alla form per la ricerca delle ricette
    @GetMapping("/ricette/search")
    public String getFormSearchRicetta() {
        return "forms/formSearchRicetta.html";
    }
    //metodo che invia il nome in base al quale effettuare la ricerca -> restituisce ricette.html con tutti le ricette con quel nome
    @PostMapping("/ricette/byNome")
    public String postRicetteByNome(@RequestParam String nome, Model model) {
        model.addAttribute("ricette", this.ricettaService.findByNome(nome));
        return "ricette.html";
    }
    //metodo che invia il nome dell'ingrediente in base al quale effettuare la ricerca -> restituisce ricette.html con tutti le ricette con quell'ingrediente
    @PostMapping("/ricette/byIngrediente")
    public String postRicetteByIngrediente(@RequestParam String ingr, Model model) {
        model.addAttribute("ricette", this.ricettaService.findByIngrediente(ingr));
        return "ricette.html";
    }
    /*FINE RICERCHE*/


    /*INSERIMENTO NUOVE RICETTE*/  //COME ADMIN
    //porta alla form per l'inserimento di una nuova ricetta
    @GetMapping("/admin/addRicetta/{cuoco_id}")
    public String getAdminFormNewRicetta(@PathVariable("cuoco_id") Long id,Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        model.addAttribute("ricetta", new Ricetta());
        return "admin/formNewRicetta.html";
    }

    //finalizza la creazione della ricetta
    @PostMapping("/admin/addRicetta/{cuoco_id}")
    public String postAdminNewRicetta(@PathVariable("cuoco_id") Long id, @ModelAttribute Ricetta ricetta) {
        ricetta.setCuoco(this.cuocoService.findById(id));
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/cuochi/" + id;
    }
    /*FINE INSERIMENTO NUOVE RICETTE*/


    /*INSERIMENTO NUOVE RICETTE*/  //COME REGISTRATO (cuoco)
    //porta alla form per l'inserimento di una nuova ricetta
    @GetMapping("/cuoco/addRicetta/{cuoco_id}/{utente_id}")
    public String getCuocoFormNewRicetta(@PathVariable("cuoco_id") Long idC, @PathVariable("utente_id") Long idU,Model model) {
        //se l'utente non corrisponde al cuoco per il quale sta provando ad aggiungere la ricetta
        //torna a cuochi.html
        if(!(this.cuocoService.findById(idC).equals(this.utenteService.getUtente(idU).getCuoco()))){
            return "redirect:/cuochi";
        }
        //altrimenti inserisce la ricetta
        model.addAttribute("cuoco", this.cuocoService.findById(idC));
        model.addAttribute("ricetta", new Ricetta());
        return "cuoco/formNewRicettaCuoco.html";
    }

    //finalizza la creazione della ricetta
    @PostMapping("/cuoco/addRicetta/{cuoco_id}")
    public String postCuocoNewRicetta(@PathVariable("cuoco_id") Long id, @ModelAttribute Ricetta ricetta) {
        ricetta.setCuoco(this.cuocoService.findById(id));
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/cuochi/" + id;
    }
    /*FINE INSERIMENTO NUOVE RICETTE*/


    /*AGGIORNAMENTO DEI DATI DI UNA RICETTA*/  //COME ADMIN
    //porta alla form per l'edit di una ricetta
    @GetMapping("/admin/editRicetta/{id}")
    public String getFormEditRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        model.addAttribute("ingrediente", new Ingrediente());
        return "forms/formEditRicetta.html";
    }
    //aggiorna i dati 
    @PostMapping("/admin/editRicetta/{id}")
    public String updateRicetta(@PathVariable("id") Long id, @ModelAttribute Ricetta ricetta, @ModelAttribute Ingrediente ingrediente) {
        ricetta.setId(id);
        ingrediente.setRicetta(ricetta);
        this.ingredienteService.saveIngrediente(ingrediente);
        ricetta.getIngredienti().add(ingrediente);
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/ricette/" + ricetta.getId();
    }
    /*FINE AGGIORNAMENTO DEI DATI DI UNA RICETTA*/

      
    /*AGGIORNAMENTO DEI DATI DI UNA RICETTA*/  //COME REGISTRATO
    //porta alla form per l'edit di una ricetta
    @GetMapping("/cuoco/editRicetta/{ricetta_id}/{utente_id}")
    public String getCuocoFormEditRicetta(@PathVariable("ricetta_id") Long idR,@PathVariable("utente_id") Long idU, Model model) {
        Cuoco cuoco = this.utenteService.getUtente(idU).getCuoco();
        Ricetta ricetta = this.ricettaService.findById(idR);
        if(!(ricetta.getCuoco().equals(cuoco))){
            return "redirect:/ricette/" + idR;
        }
        model.addAttribute("ricetta", ricetta);
        model.addAttribute("ingrediente", new Ingrediente());
        return "forms/formEditRicetta.html";
    }
    //aggiorna i dati 
    @PostMapping("/cuoco/editRicetta/{ricetta_id}")
    public String updateCuocoRicetta(@PathVariable("ricetta_id") Long idR, @ModelAttribute Ricetta ricetta, @ModelAttribute Ingrediente ingrediente) {
        ricetta.setId(idR);
        ingrediente.setRicetta(ricetta);
        this.ingredienteService.saveIngrediente(ingrediente);
        ricetta.getIngredienti().add(ingrediente);
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/ricette/" + ricetta.getId();
    }
    /*FINE AGGIORNAMENTO DEI DATI DI UNA RICETTA*/

    /*CANCELLAZIONE*/  //COME ADMIN
    @GetMapping("/admin/deleteRicetta/{id}")
    public String adminDeleteRicetta(@PathVariable("id") Long id) {
        this.ricettaService.deleteRicettaById(id);
        return "redirect:/ricette";
    }
    /*FINE CANCELLAZIONE*/

    /*CANCELLAZIONE*/  //COME REGISTRATO (cuoco)
    @GetMapping("/cuoco/deleteRicetta/{ricetta_id}/{utente_id}")
    public String cuocoDeleteRicetta(@PathVariable("ricetta_id") Long idR, @PathVariable("utente_id") Long idU) {
        //prendo ricetta e utente
        Ricetta ricetta = this.ricettaService.findById(idR);
        Utente utente = this.utenteService.getUtente(idU);
        //se il cuoco della ricetta è il cuoco associato all'utente -> proceed
        if(ricetta.getCuoco().equals(utente.getCuoco())){
            this.ricettaService.deleteRicetta(ricetta);
            return "redirect:/ricette";
        }//altrimenti rimandalo a ricette
        else{
            return "redirect:/ricette";
        }
    }
    /*FINE CANCELLAZIONE*/

}
