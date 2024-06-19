package it.uniroma3.siwfood.siw_food.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import java.time.LocalDate;


@Repository
//questa inferfaccia offre a CuocoService i metodi di CRUDrepository
public interface CuocoRepository extends CrudRepository<Cuoco, Long>{

    public Iterable<Cuoco> findByNomeOrderByCognomeAsc(String nome);
    public Iterable<Cuoco> findByDataNascitaAfter(LocalDate dataNascita);
}
