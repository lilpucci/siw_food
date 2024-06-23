package it.uniroma3.siwfood.siw_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.service.CuocoService;



@Controller
@RequestMapping("/cuochi")
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;

    //restituisce una pagina con tutti i cuochi
    @GetMapping
    public String getCuochi(Model model) {
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "cuochi.html";
    }

    //restituisce la pagina di un singolo cuoco in base all'id
    @GetMapping("/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "cuoco.html";
    }

    //porta alla pagina in cui si inseriscono i dati per il nuovo cuoco
    @GetMapping("/new")
    public String getFormNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        return "forms/formNewCuoco.html";
    }

    //finalizza la creazione e reindirizza alla pagina del cuoco appena creato
    @PostMapping
    public String postNewCuoco(@ModelAttribute Cuoco cuoco) {
        cuocoService.saveCuoco(cuoco);
        return "redirect:/cuochi/" + cuoco.getId();
    }
    
    //restituisce una pagina con tutti i cuochi con un determinato nome
    @GetMapping("/byNome")
    public String getCuochiByNome(@RequestParam String nome, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByNome(nome));
        return "cuochi.html";
    }

    //restituisce una pagina con tutti i cuochi nati dopo un certo anno
    @GetMapping("/byAnno")
    public String getCuochiByDataNascita(@RequestParam int year, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByDataNascita(year));
        return "cuochi.html";
    }
    
    //cancella un cuoco in base all'id
    @DeleteMapping("/{id}")
    public String deleteCuocoById(@PathVariable("id") Long id) {
        cuocoService.deleteCuoco(id);
        return "redirect:/cuochi";
    }
}

