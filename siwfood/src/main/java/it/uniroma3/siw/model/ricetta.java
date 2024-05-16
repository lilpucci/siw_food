package it.uniroma3.siw.model;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Ricetta {

    /*ATTRIBUTI RICETTA*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Cuoco cuoco;
    private List<String> URLs;
    private String desc;
    private Map<String,Integer> ingredienti_nome2quant;
    /*FINE ATTRIBUTI*/


    

    /*EQUALS & HASHCODE*/
    @Override
    public int hashCode() {
        return Objects.hash(id,desc,cuoco);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ricetta other = (Ricetta) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (cuoco == null) {
            if (other.cuoco != null)
                return false;
        } else if (!cuoco.equals(other.cuoco))
            return false;
        if (desc == null) {
            if (other.desc != null)
                return false;
        } else if (!desc.equals(other.desc))
            return false;
        return true;
    }
    /*FINE EQUALS & HASHCODE*/

    /*GETTER & SETTERS*/
    //id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //cuoco
    public Cuoco getCuoco() {
        return cuoco;
    }

    public void setCuoco(Cuoco cuoco) {
        this.cuoco = cuoco;
    }

    //immagini
    public List<String> getURLs() {
        return URLs;
    }

    public void setURLs(List<String> uRLs) {
        URLs = uRLs;
    }

    //descrizione
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    //ingredienti
    public Map<String, Integer> getIngredienti_nome2quant() {
        return ingredienti_nome2quant;
    }

    public void setIngredienti_nome2quant(Map<String, Integer> ingredienti_nome2quant) {
        this.ingredienti_nome2quant = ingredienti_nome2quant;
    }
    /*FINE GETTER & SETTER*/

    
    
}