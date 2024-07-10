package it.uniroma3.siwfood.siw_food.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Ingrediente;
import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.service.CuocoService;
import it.uniroma3.siwfood.siw_food.service.ImmagineService;
import it.uniroma3.siwfood.siw_food.service.IngredienteService;
import it.uniroma3.siwfood.siw_food.service.RicettaService;
import it.uniroma3.siwfood.siw_food.service.UtenteService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class RicettaController extends GlobalController {
    
    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private IngredienteService ingredienteService;

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ImmagineService immagineService;


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




    /*INSERIMENTO NUOVE RICETTE*/  //ORA DOVREBBE FARE DOPPIO CONTROLLO
    //porta alla form per l'inserimento di una nuova ricetta
    @GetMapping("/admin/addRicetta/{cuoco_id}")
    public String getFormNewRicetta(@PathVariable("cuoco_id") Long idC, Model model) {
        //controllo dei permessi 
        if(!getCredentials().isAdmin() && !this.utenteService.utenteIsCuoco(getCredentials().getUtente(), idC)){
            return "error/errorPage.html";
        }
        model.addAttribute("cuoco", this.cuocoService.findById(idC));
        model.addAttribute("ricetta", new Ricetta());
        return "admin/formNewRicetta.html";
    }

    //finalizza la creazione della ricetta
    @PostMapping("/admin/addRicetta/{cuoco_id}")
    public String postAdminNewRicetta(@PathVariable("cuoco_id") Long idC, @ModelAttribute Ricetta ricetta, @RequestParam("immagine") MultipartFile immagine) throws IOException {
        //salvo l'immagine e la aggiungo alla collezione della ricetta
        this.immagineService.addFotoToRicetta(ricetta, immagine);
        //aggiungo la ricetta al cuoco e la salvo
        this.ricettaService.addRicettaToCuoco(ricetta, idC);
        return "redirect:/ricette/" + ricetta.getId();
    }
    /*FINE INSERIMENTO NUOVE RICETTE*/




    /*AGGIORNAMENTO DEI DATI DI UNA RICETTA*/  //COME ADMIN
    //porta alla form per l'edit di una ricetta
    @GetMapping("/admin/editRicetta/{ricetta_id}")
    public String getFormEditRicetta(@PathVariable("ricetta_id") Long idR, Model model) {

        Cuoco cuoco = this.ricettaService.findById(idR).getCuoco();

        //controllo dei permessi 
        if(!getCredentials().isAdmin() && !this.utenteService.utenteIsCuoco(getCredentials().getUtente(), cuoco.getId())){
            return "error/errorPage.html";
        }

        model.addAttribute("ricetta", this.ricettaService.findById(idR));
        model.addAttribute("ingredienti", this.ricettaService.findById(idR).getIngredienti());
        model.addAttribute("ingrediente", new Ingrediente());

        return "admin/formEditRicettaNuova.html";
    }

    //aggiorna i dati 
    @PostMapping("/admin/editRicetta/{ricetta_id}")
    public String updateRicetta(@PathVariable("ricetta_id") Long idR, @ModelAttribute Ricetta ricetta,@ModelAttribute List<Ingrediente> ingredienti, @RequestParam("immagine") MultipartFile immagine) throws IOException {
        ricetta.setId(idR);
        //se img isEmpty non fa niente
        this.immagineService.addFotoToRicetta(ricetta, immagine);
        //salvo la ricetta
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/ricette/" + ricetta.getId();
    }

    @PostMapping("/admin/addIngrediente/{ricetta_id}")
    public String addIngrediente(@PathVariable("ricetta_id") Long idR, @ModelAttribute Ingrediente ingrediente, Model model) {
        
        this.ingredienteService.addIngredienteToRicetta(ingrediente, this.ricettaService.findById(idR)); 

        return "redirect:/admin/editRicetta/" + idR;
    }
    /*FINE AGGIORNAMENTO DEI DATI DI UNA RICETTA*/

    


    /*CANCELLAZIONE*/  //COME ADMIN
    @GetMapping("/admin/deleteRicetta/{ricetta_id}")
    public String adminDeleteRicetta(@PathVariable("ricetta_id") Long idR) {
        Cuoco cuoco = this.ricettaService.findById(idR).getCuoco();
        //controllo dei permessi
        if(!getCredentials().isAdmin() && !this.utenteService.utenteIsCuoco(getCredentials().getUtente(), cuoco.getId()) ){
            return "error/errorPage.html";
        }
        this.ricettaService.deleteRicettaById(idR);
        return "redirect:/ricette";
    }
    /*FINE CANCELLAZIONE*/


}
