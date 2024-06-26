package it.uniroma3.siwfood.siw_food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.repository.RicettaRepository;

@Service
public class RicettaService {
    
    @Autowired
    private RicettaRepository ricettaRepository;

    public Ricetta findById(Long id){
        return this.ricettaRepository.findById(id).get();
    }

    public Iterable<Ricetta> findAll(){
        return this.ricettaRepository.findAll();
    }

    public Iterable<Ricetta> findByNome(String nome){
        return this.ricettaRepository.findByNome(null);
    } 

    public Iterable<Ricetta> findByIngrediente(String ingrediente){
        return this.ricettaRepository.findAllByIngredientiContaining(ingrediente);
    }

    public Ricetta savRicetta(Ricetta ricetta){
        return this.ricettaRepository.save(ricetta);
    }

    public void deleteRicetta(Long id){
        this.ricettaRepository.deleteById(id);
    }

}
