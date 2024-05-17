package it.uniroma3.siwfood.siw_food.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siwfood.siw_food.model.Ricetta;

@Repository
public interface RicettaRepository extends CrudRepository<Ricetta,Long>{

}