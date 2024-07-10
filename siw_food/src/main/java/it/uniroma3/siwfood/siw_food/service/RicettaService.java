package it.uniroma3.siwfood.siw_food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Ingrediente;
import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.repository.RicettaRepository;

@Service
public class RicettaService {
    
    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired 
    private CuocoService cuocoService;

    @Autowired
    private IngredienteService ingredienteService;


    /*SOLO RIGUARDANTI LA RICETTA*/
    //ricerche
    public Ricetta findById(Long id){
        return this.ricettaRepository.findById(id).orElse(null);
    }

    public Iterable<Ricetta> findAll(){
        return this.ricettaRepository.findAll();
    }

    public Iterable<Ricetta> findByNome(String nome){
        return this.ricettaRepository.findByNome(nome);
    }

    public Iterable<Ricetta> findByIngrediente(String nomeIngrediente){
        return this.ricettaRepository.findByIngredienteNome(nomeIngrediente);
    }

    //salvataggio
    public Ricetta saveRicetta(Ricetta ricetta){
        return this.ricettaRepository.save(ricetta);
    }

    //cancellazione
    public void deleteRicettaById(Long id){
        this.ricettaRepository.deleteById(id);
    }
    
    public void deleteRicetta(Ricetta ricetta){
        this.ricettaRepository.delete(ricetta);
    }

    /*UTILIZZO DI ALTRI SERVICE*/
    public void saveIngredienteToRicetta (Long idRicetta, Long idIngr, String nome, String quantita){

        Ricetta ricetta = this.findById(idRicetta);
        //questa riga credo non mi serva
        //Ingrediente ingrediente = this.ingredienteService.findById(idIngr);

        Ingrediente ingrediente = new Ingrediente(nome, quantita, ricetta);

        if(ricetta.getIngredienti().contains(ingrediente)){
            throw new RuntimeException("ingrediente già presente nella ricetta");
        }

        ricetta.getIngredienti().add(ingrediente);
        this.ingredienteService.saveIngrediente(ingrediente);  //questa riga è diversa capire cosa cambia
        this.saveRicetta(ricetta);

    }

    //TODO DA TESTARE SE FUNZIONA
    public void addRicettaToCuoco (Ricetta ricetta, Long idC){
        ricetta.setCuoco(this.cuocoService.findById(idC));
        this.saveRicetta(ricetta);
    }

    public void saveCuocoToRicetta (Long idRicetta, Long idCuoco){
        
        Ricetta ricetta = this.findById(idRicetta);
        Cuoco cuoco = this.cuocoService.findById(idCuoco);

        ricetta.setCuoco(cuoco);
        cuoco.getRicette().add(ricetta);
        this.cuocoService.saveCuoco(cuoco);
        this.saveRicetta(ricetta);

    }
    
}
