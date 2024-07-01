package it.uniroma3.siwfood.siw_food.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siwfood.siw_food.model.Ingrediente;

@Repository
public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

    //verifica la presenza di un ingrediente con quel nome
    public boolean existsByNome(String nome);
    
}
