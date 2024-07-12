package it.uniroma3.siwfood.siw_food.controller;

import java.io.IOException;

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
import it.uniroma3.siwfood.siw_food.model.auth.Utente;
import it.uniroma3.siwfood.siw_food.service.CuocoService;
import it.uniroma3.siwfood.siw_food.service.ImmagineService;
import it.uniroma3.siwfood.siw_food.service.UtenteService;


@Controller
public class CuocoController extends GlobalController{

    @Autowired
    private CuocoService cuocoService;

    @Autowired 
    private UtenteService utenteService;

    @Autowired
    private ImmagineService immagineService;

    /*RICERCHE*/   //LE POSSONO FARE TUTTI (NON REGISTRATO, REGISTRATO, ADMIN)
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
        //se l'utente loggato attualmente non è admin non può farlo
        if(!getCredentials().isAdmin()){
            return "error/errorPage.html";
        }
        //altrimenti va alla pagina di aggiunta di un cuoco
        model.addAttribute("cuoco", new Cuoco());
        return "logged/formNewCuoco.html";
    }

    //finalizza la creazione e reindirizza alla pagina del cuoco appena creato
    @PostMapping("/admin/addCuoco")
    public String postNewCuoco(@ModelAttribute Cuoco cuoco, @RequestParam("immagine") MultipartFile immagine) throws IOException {        
        this.immagineService.addFotoToCuoco(cuoco, immagine);
        cuocoService.saveCuoco(cuoco);
        return "redirect:/cuochi/" + cuoco.getId();
    }
    /*FINE SALVATAGGIO CUOCHI*/


    /*AGGIORNAMENTO DATI*/
    //restituisce la form per editare i dati di un cuoco
    @GetMapping("/admin/editCuoco/{id}")
    public String getFormEditCuoco(@PathVariable("id") Long id, Model model) {
        Utente utente = getCredentials().getUtente();
        //controllo dei permessi 
        if(!getCredentials().isAdmin() &&  this.utenteService.utenteIsCuoco(utente, id)){
            return "error/errorPage.html";
        }
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "logged/formEditCuoco.html";
    }

    //fa partire l'aggiornamento dei dati verso il db
    @PostMapping("/admin/editCuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id, @ModelAttribute Cuoco cuoco, @RequestParam("immagine") MultipartFile immagine) throws IOException{
        //così sono sicuro che l'id rimanga invariato
        cuoco.setId(id);
        //gestione delle foto
        this.immagineService.addFotoToCuoco(cuoco, immagine);
        //salvataggio del cuoco
        this.cuocoService.saveCuoco(cuoco); 
        //reindirizzato alla pagina del cuoco editato 
        return "redirect:/cuochi/" + cuoco.getId();
    }
    /*FINE AGGIORNAMENTO DATI*/
    

    /*CANCELLAZIONE CUOCHI*/  //ADMIN 
    //cancella un cuoco in base all'id
    @GetMapping("/admin/deleteCuoco/{id}")
    public String deleteCuocoById(@PathVariable("id") Long id) {
        //se l'utente loggato attualmente non è admin non può farlo
        if(!getCredentials().isAdmin()){
            return "error/errorPage.html";
        }
        
        this.cuocoService.deleteCuoco(id);
        return "redirect:/cuochi";
    }
    /*FINE CANCELLAZIONE CUOCHI*/
        
}
