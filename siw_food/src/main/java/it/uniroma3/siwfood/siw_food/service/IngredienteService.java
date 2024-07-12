package it.uniroma3.siwfood.siw_food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.Ingrediente;
import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.repository.IngredienteRepository;

@Service
public class IngredienteService {
    
    @Autowired
    private IngredienteRepository ingredienteRepository;

    //RICERCHE
    public Ingrediente findById(Long id){
        return this.ingredienteRepository.findById(id).orElse(null);
    }

    public Iterable<Ingrediente> findAll(){
        return this.ingredienteRepository.findAll();
    }

    //SALVATAGGIO E CANCELLAZIONE
    public Ingrediente saveIngrediente(Ingrediente Ingrediente){
        return this.ingredienteRepository.save(Ingrediente);
    }

    public void deleteIngrediente(Long id){
        this.ingredienteRepository.deleteById(id);
    }


    //UTILITIES
    public void addIngredienteToRicetta(Ingrediente ing, Ricetta ricetta){
        ing.setRicetta(ricetta);  //set della ricetta a cui appartiene
        ricetta.getIngredienti().add(ing);   //aggiungo l'ingrediente alla collezione della ricetta
        this.saveIngrediente(ing);   //salvo il nuovo ingrediente
    }

}
