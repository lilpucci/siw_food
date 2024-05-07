package it.uniroma3.siw.model;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class ricetta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Cuoco cuoco;
    private List<String> URLs;
    private String desc;
    private Map<String,Integer> ingredienti_nome2quant;

    
    
}