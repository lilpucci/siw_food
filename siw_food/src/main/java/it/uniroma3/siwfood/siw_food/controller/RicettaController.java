package it.uniroma3.siwfood.siw_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.service.IngredienteService;
import it.uniroma3.siwfood.siw_food.service.RicettaService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/ricette")
public class RicettaController {
    
    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private IngredienteService ingredienteService;


    //risponde all richiesta get che mi indirizza al template che mostra tutte le ricette nel db
    @GetMapping
    public String getRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "ricette.html";
    }
    
    //risponde con il template che mostra i dettagli della singola ricetta
    @GetMapping("/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "ricetta.html";
    }

    //porta alla form per l'inserimento di una nuova ricetta
    @GetMapping("/new")
    public String getFormNewRicetta(Model model) {
        model.addAttribute("ricetta", new Ricetta());
        return "ricetta.html";
    }

    //finalizza la creazione della ricetta
    @PostMapping("/save")
    public String postNewRicetta(@ModelAttribute Ricetta ricetta) {
        this.ricettaService.savRicetta(ricetta);
        return "redirect:/ricetta/" + ricetta.getId();
    }

    //porta alla form per l'edit di una ricetta
    @GetMapping("/edit/{id}")
    public String getMethodName(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "forms/formEditRicetta.html";
    }

    @PostMapping("/update/{id}")
    public String updateRicetta(@PathVariable("id") Long id, @ModelAttribute Ricetta ricetta) {
        ricetta.setId(id);
        this.ricettaService.savRicetta(ricetta);
        return "redirect:/ricette/" + ricetta.getId();
    }

    @GetMapping("/search")
    public String getFormSearchRicetta() {
        return "forms/formSearchRicetta.html";
    }

    @PostMapping("/byNome")
    public String postRicetteByNome(@RequestParam String nome, Model model) {
        model.addAttribute("ricette", this.ricettaService.findByNome(nome));
        return "ricette.html";
    }

    @PostMapping("/byIngrediente")
    public String postRicetteByIngrediente(@RequestParam String ingr, Model model) {
        model.addAttribute("ricette", this.ricettaService.findByIngrediente(ingr));
        return "ricette.html";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteRicetteById(@PathVariable("id") Long id) {
        this.ricettaService.deleteRicetta(id);
        return "redirect:/ricette";
    }
    
    
    
    
    

    
    

    

}
