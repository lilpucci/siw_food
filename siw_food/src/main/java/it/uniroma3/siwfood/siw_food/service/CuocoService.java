package it.uniroma3.siwfood.siw_food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.repository.CuocoRepository;

@Service
public class CuocoService {

    @Autowired
    private CuocoRepository cuocoRepository;
    
    public Cuoco findById(Long id){
        return cuocoRepository.findById(id).get();
    }

    public Iterable<Cuoco> findAll(){
        return cuocoRepository.findAll();
    }
    
}
