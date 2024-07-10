package it.uniroma3.siwfood.siw_food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.auth.Utente;
import it.uniroma3.siwfood.siw_food.repository.UtenteRepository;

@Service
public class UtenteService {
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private CuocoService cuocoService;


    public Utente getUtente(Long id){
        return this.utenteRepository.findById(id).get();
    }

    public Utente saveUtente(Utente u){
        return this.utenteRepository.save(u);
    }

    public boolean utenteIsCuoco (Utente u, Long idC){
        return u.getCuoco().equals(this.cuocoService.findById(idC));
    }
}
