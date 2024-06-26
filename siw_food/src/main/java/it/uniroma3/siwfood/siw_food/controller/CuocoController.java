package it.uniroma3.siwfood.siw_food.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Immagine;
import it.uniroma3.siwfood.siw_food.service.CuocoService;
import it.uniroma3.siwfood.siw_food.service.ImmagineService;


@Controller
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private ImmagineService immagineService;

    /*RICERCHE*/
    //restituisce una pagina con tutti i cuochi
    @GetMapping("/cuochi")
    public String getCuochi(Model model) {
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "cuochi.html";
    }

    //restituisce la pagina di un singolo cuoco in base all'id
    @GetMapping("/cuochi/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "cuoco.html";
    }

    //porta al form per la ricerca dei cuochi
    @GetMapping("/cuochi/search")
    public String getFormSearchCuoco() {
        return "forms/formSearchCuoco.html";
    }

    //restituisce una pagina con tutti i cuochi con un determinato nome
    @PostMapping("/cuochi/byNome")  //invia al server i dati con cui effettuare la ricerca
    public String postCuochiByNome(@RequestParam String nome, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByNome(nome));
        return "cuochi.html";
    }
    
    //restituisce una pagina con tutti i cuochi nati dopo un certo anno
    @PostMapping("/cuochi/byYear")
    public String postCuochiByYear(@RequestParam int year, Model model) {
        model.addAttribute("cuochi", this.cuocoService.findByDataNascita(year));
        return "cuochi.html";
    }
    /*FINE RICERCHE*/

    /*SALVATAGGIO CUOCHI*/
    //porta alla pagina in cui si inseriscono i dati per il nuovo cuoco
    @GetMapping("/admin/addCuoco")
    public String getFormNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        return "forms/formNewCuoco.html";
    }

    //finalizza la creazione e reindirizza alla pagina del cuoco appena creato
    @PostMapping("/admin/addCuoco")
    public String postNewCuoco(@ModelAttribute Cuoco cuoco, @RequestParam("immagine") MultipartFile immagine) throws IOException {
        
        if (!immagine.isEmpty()) {
            Immagine img = new Immagine();
            img.setFileName(immagine.getOriginalFilename());
            img.setImageData(immagine.getBytes());
            if (cuoco.getImmagini() == null) {
                cuoco.setImmagini(new ArrayList<>());
            }
            cuoco.getImmagini().add(img);
            immagineService.save(img);
        }
        cuocoService.saveCuoco(cuoco);
        return "redirect:/cuochi/" + cuoco.getId();
    }
    /*FINE SALVATAGGIO CUOCHI*/

    /*AGGIORNAMENTO DATI*/
    //restituisce la form per editare i dati di un cuoco
    @GetMapping("/admin/editCuoco/{id}")
    public String getFormEditCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "forms/formEditCuoco.html";
    }

    //fa partire l'aggiornamento dei dati verso il db
    @PostMapping("/admin/editCuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id, @ModelAttribute Cuoco cuoco) {
        cuoco.setId(id);
        this.cuocoService.saveCuoco(cuoco);  
        return "redirect:/cuochi/" + cuoco.getId();
    }
    /*FINE AGGIORNAMENTO DATI*/
    

    /*CANCELLAZIONE CUOCHI*/
    //cancella un cuoco in base all'id
    @GetMapping("/admin/deleteCuoco/{id}")
    public String deleteCuocoById(@PathVariable("id") Long id) {
        cuocoService.deleteCuoco(id);
        return "redirect:/cuochi";
    }
    /*FINE CANCELLAZIONE CUOCHI*/
        
}
