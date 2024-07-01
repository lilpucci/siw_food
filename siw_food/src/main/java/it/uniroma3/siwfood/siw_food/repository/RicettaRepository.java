package it.uniroma3.siwfood.siw_food.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Ricetta;


@Repository
public interface RicettaRepository extends CrudRepository<Ricetta,Long>{

    // Metodo per trovare le ricette per nome della ricetta
    public Iterable<Ricetta> findByNome(String nome);

    // Metodo per trovare le ricette che contengono un determinato ingrediente
    @Query("SELECT r FROM Ricetta r JOIN r.ingredienti i WHERE i.nome = :nomeIngrediente")
    public Iterable<Ricetta> findByIngredienteNome(@Param("nomeIngrediente") String nomeIngrediente);
    
    //verifica la presenza di una ricetta in base ai parametri specificati
    public boolean existsByNomeAndCuoco(String nome, Cuoco cuoco);
}