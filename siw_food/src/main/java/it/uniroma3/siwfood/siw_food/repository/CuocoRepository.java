package it.uniroma3.siwfood.siw_food.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import java.time.LocalDate;


@Repository
//questa inferfaccia offre a CuocoService i metodi di CRUDrepository
public interface CuocoRepository extends CrudRepository<Cuoco, Long>{

    //restituisce tutti i cuochi con il nome cercato, ordinati rispetto al cognome
    public Iterable<Cuoco> findByNomeOrderByCognomeAsc(String nome);
    //restituisce tutti i cuochi nati dopo una determinata data
    public Iterable<Cuoco> findByDataNascitaAfter(LocalDate dataNascita);

    //TODO cerco spaghetti -> restituisce tutti i cuochi che hanno una ricetta che nel nome ha la parola spaghetti

    //verifica la presenza di un cuoco nel db in base a nome e cognome
    public boolean existsByNomeAndCognome(String nome,String cognome);

}
