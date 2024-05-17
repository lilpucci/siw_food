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
        return ricettaRepository.findById(id).get();
    }

    public Iterable<Ricetta> findAll(){
        return ricettaRepository.findAll();
    }
}
