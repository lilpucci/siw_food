package it.uniroma3.siwfood.siw_food.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.auth.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {
    
    public Utente findByCuoco(Cuoco cuoco);
    
}
