package it.uniroma3.siwfood.siw_food.controller;


import java.io.IOException;

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
        Utente utente = getCredentials().getUtente();
        /*if(!getCredentials().isAdmin() && !this.utenteService.utenteIsCuoco(getCredentials().getUtente(), idC)){
            return "error/errorPage.html";
        }*/
        if(getCredentials().isAdmin() || this.cuocoService.findById(utente.getCuoco().getId()).getId() == idC){
            model.addAttribute("cuoco", this.cuocoService.findById(idC));
            model.addAttribute("ricetta", new Ricetta());
            return "logged/formNewRicetta.html";
        }
        else{
            return "error/errorPage.html";
        }
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
        //model.addAttribute("cuoco", cuoco);
        model.addAttribute("ingrediente", new Ingrediente());

        return "logged/formEditRicetta.html";
    }

    //aggiorna i dati e rimanda alla pagina della ricetta
    @PostMapping("/admin/editRicetta/{ricetta_id}")
    public String updateRicetta(@PathVariable("ricetta_id") Long idR, @ModelAttribute Ricetta ricetta, @RequestParam("immagine") MultipartFile immagine) throws IOException {
        //imposto lo stesso id 
        /*ricetta.setId(idR);
        //salvo la foto alla ricetta
        this.immagineService.addFotoToRicetta(ricetta, immagine);
        //salvo la ricetta aggiungendola al cuoco
        this.ricettaService.addRicettaToCuoco(ricetta, cuoco.getId());*/
        Ricetta vecchiaRicetta = this.ricettaService.findById(idR);
        
        if(ricetta.getNome() != null || ricetta.getNome() != ""){
            vecchiaRicetta.setNome(ricetta.getNome());
        }
        if(ricetta.getDescrizione() != null || ricetta.getDescrizione() != ""){
            vecchiaRicetta.setDescrizione(ricetta.getDescrizione());
        }

        this.immagineService.addFotoToRicetta(vecchiaRicetta, immagine);

        return "redirect:/ricette/" + vecchiaRicetta.getId();
    }

    //aggiunge un ingrediente e rimanda alla form di modifica della ricetta
    @PostMapping("/admin/addIngrediente/{ricetta_id}")
    public String addIngrediente(@PathVariable("ricetta_id") Long idR, @ModelAttribute Ingrediente ingrediente) {
        //aggiungo l'ingrediente
        this.ingredienteService.addIngredienteToRicetta(ingrediente, this.ricettaService.findById(idR)); 
        return "redirect:/admin/editRicetta/" + idR;
    }
    /*FINE AGGIORNAMENTO DEI DATI DI UNA RICETTA*/

    
    /*CANCELLAZIONE*/
    @GetMapping("/admin/deleteRicetta/{ricetta_id}")
    public String adminDeleteRicetta(@PathVariable("ricetta_id") Long idR) {
        Cuoco cuoco = this.ricettaService.findById(idR).getCuoco();
        //controllo dei permessi
        if(!getCredentials().isAdmin() && !this.utenteService.utenteIsCuoco(getCredentials().getUtente(), cuoco.getId()) ){
            return "error/errorPage.html";
        }
        //cancello la ricetta
        this.ricettaService.deleteRicettaById(idR);
        return "redirect:/ricette";
    }
    /*FINE CANCELLAZIONE*/

    /*CANCELLAZIONE INGREDIENTE*/
    @PostMapping("/admin/deleteIngrediente/{ricetta_id}/{ingrediente_id}")
    public String adminDeleteIngrediente(@PathVariable("ricetta_id") Long idR, @PathVariable("ingrediente_id") Long idI){
        Ricetta r = this.ricettaService.findById(idR);
        r.getIngredienti().remove(this.ingredienteService.findById(idI));
        this.ingredienteService.deleteIngrediente(idI);
        //torno alla pagina di modifica della ricetta
        return "redirect:/admin/editRicetta/" + idR;
    }
    /*FINE CANCELLAZIONE INGREDIENTE*/


}
