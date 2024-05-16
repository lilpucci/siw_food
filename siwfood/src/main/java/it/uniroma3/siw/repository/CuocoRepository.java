package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;


//questa inferfaccia offre a CuocoService i metodi di CRUDrepository
public interface CuocoRepository extends CrudRepository<Cuoco, Long>{

}
