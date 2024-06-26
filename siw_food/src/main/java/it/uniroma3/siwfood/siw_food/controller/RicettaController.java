package it.uniroma3.siwfood.siw_food.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.service.CuocoService;
//import it.uniroma3.siwfood.siw_food.service.IngredienteService;
import it.uniroma3.siwfood.siw_food.service.RicettaService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class RicettaController {
    
    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private CuocoService cuocoService;

    //@Autowired
    //private IngredienteService ingredienteService;


    /*RICERCHE*/
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
        model.addAttribute("ricette", this.ricettaService.findByIngredienteNome(ingr));
        return "ricette.html";
    }
    /*FINE RICERCHE*/


    /*INSERIMENTO NUOVE RICETTE*/
    //porta alla form per l'inserimento di una nuova ricetta
    @GetMapping("/admin/addRicetta/{cuoco_id}")
    public String getFormNewRicetta(@PathVariable("cuoco_id") Long id,Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        model.addAttribute("ricetta", new Ricetta());
        return "forms/formNewRicetta.html";
    }

    //finalizza la creazione della ricetta
    @PostMapping("/admin/addRicetta/{cuoco_id}")
    public String postNewRicetta(@PathVariable("cuoco_id") Long id, @ModelAttribute Ricetta ricetta) {
        ricetta.setCuoco(this.cuocoService.findById(id));
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/cuochi/" + id;
    }
    /*FINE INSERIMENTO NUOVE RICETTE*/


    /*AGGIORNAMENTO DEI DATI DI UNA RICETTA*/
    //porta alla form per l'edit di una ricetta
    @GetMapping("/admin/editRicetta/{id}")
    public String getFormEditRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "forms/formEditRicetta.html";
    }
    //aggiorna i dati 
    @PostMapping("/admin/editRicetta/{id}")
    public String updateRicetta(@PathVariable("id") Long id, @ModelAttribute Ricetta ricetta) {
        ricetta.setId(id);
        this.ricettaService.saveRicetta(ricetta);
        return "redirect:/ricette/" + ricetta.getId();
    }
    /*FINE AGGIORNAMENTO DEI DATI DI UNA RICETTA*/

    
    /*CANCELLAZIONE*/
    @GetMapping("/admin/deleteRicetta/{id}")
    public String deleteRicetteById(@PathVariable("id") Long id) {
        this.ricettaService.deleteRicetta(id);
        return "redirect:/ricette";
    }
    /*FINE CANCELLAZIONE*/

}
