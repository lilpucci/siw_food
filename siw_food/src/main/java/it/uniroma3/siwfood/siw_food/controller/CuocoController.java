package it.uniroma3.siwfood.siw_food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.siw_food.service.CuocoService;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;

    @GetMapping("")
    public String index() {
        return "index.html";
    }
    
    //restituisce la pagina di un singolo cuoco in base all'id
    @GetMapping("/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "cuoco.html";
    }
    
    //restituisce una pagina con tutti i cuochi
    @GetMapping("/cuochi")
    public String getCuochi(Model model) {
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "cuochi.html";
    }
    
    //restituisce una pagina con tutti i cuochi con un determinato nome
    @GetMapping("/cuochi/byNome")
    public String getCuochiByNome(@RequestParam String nome, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByNome(nome));
        return "cuochi.html";
    }

    //restituisce una pagina con tutti i cuochi nati dopo un certo anno
    @GetMapping("/cuochi/byYear")
    public String getMethodName(@RequestParam int year, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByDataNascita(year));
        return "cuochi.html";
    }
    
    
    
}

