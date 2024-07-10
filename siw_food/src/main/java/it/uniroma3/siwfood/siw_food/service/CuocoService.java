package it.uniroma3.siwfood.siw_food.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.auth.Utente;
import it.uniroma3.siwfood.siw_food.repository.CuocoRepository;

@Service
public class CuocoService {

    @Autowired
    private CuocoRepository cuocoRepository;
    

    /*RICERCHE*/
    //cerca un cuoco in base all'id
    public Cuoco findById(Long id){
        return this.cuocoRepository.findById(id).orElse(null);  //oppure invece di .orElse(null) -> .get()
    }
    //restituisce tutti i cuochi salvati nel sistema
    public Iterable<Cuoco> findAll(){
        return this.cuocoRepository.findAll();
    }
    //restituisce tutti i cuochi con un determinato nome
    public Iterable<Cuoco> findByNome(String nome){
        return this.cuocoRepository.findByNomeOrderByCognomeAsc(nome);
    }
    //restituisce tutti i cuochi nati dopo una determinata data
    public Iterable<Cuoco> findByDataNascita(int year){
        LocalDate dataNascita = LocalDate.of(year,1,1); //da int -> a localdate
        return this.cuocoRepository.findByDataNascitaAfter(dataNascita);
    }
    /*FINE RICERCHE*/


    /*SALVATAGGIO*/
    public void saveCuoco (Cuoco cuoco){
        this.cuocoRepository.save(cuoco);
    }

    public void saveCuocoFromUtente (Utente utente, Cuoco cuoco){
        cuoco.setNome(utente.getNome());
        cuoco.setCognome(utente.getCognome());
        cuoco.setDataNascita(utente.getDataNascita());
        this.saveCuoco(cuoco);
    }
    /*FINE SALVATAGGIO*/


    /*CANCELLAZIONE*/
    public void deleteCuoco (Long id){
        this.cuocoRepository.deleteById(id);
    }
    /*FINE CANCELLAZIONE*/
}
