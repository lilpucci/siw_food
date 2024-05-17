package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Cuoco;

@Repository
//questa inferfaccia offre a CuocoService i metodi di CRUDrepository
public interface CuocoRepository extends CrudRepository<Cuoco, Long>{

}
