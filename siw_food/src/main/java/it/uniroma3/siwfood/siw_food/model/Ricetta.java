package it.uniroma3.siwfood.siw_food.model;




import java.util.List;
import java.util.Objects;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;


@Entity
public class Ricetta {

    /*ATTRIBUTI*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @Lob
    private String descrizione;

    @ElementCollection
    private List<String> urlImage;

    @ElementCollection
    private List<String> ingredienti;

    @ManyToOne
    @JoinColumn(name = "cuoco_id")
    private Cuoco cuoco;
    /*FINE ATTRIBUTI*/
    
    
    /*EQUALS & HASHCODE*/
    @Override
    public int hashCode() {
        return Objects.hash(descrizione,ingredienti,cuoco);
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
        if (descrizione == null) {
            if (other.descrizione != null)
                return false;
        } else if (!descrizione.equals(other.descrizione))
            return false;
        if (ingredienti == null) {
            if (other.ingredienti != null)
                return false;
        } else if (!ingredienti.equals(other.ingredienti))
            return false;
        if (cuoco == null) {
            if (other.cuoco != null)
                return false;
        } else if (!cuoco.equals(other.cuoco))
            return false;
        return true;
    }
    /*FINE EQUALS & HASHCODE*/

    
    /*GETTER & SETTER*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String titolo) {
        this.nome = titolo;
    }

    public List<String> getUrlImage() {
        return this.urlImage;
    }

    public void setUrlImage(List<String> urlImage) {
        this.urlImage = urlImage;
    }

    public List<String> getIngredienti() {
        return this.ingredienti;
    }

    public void setIngredienti(List<String> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public Cuoco getCuoco() {
        return cuoco;
    }

    public void setCuoco(Cuoco autore) {
        this.cuoco = autore;
    }
    /*FINE GETTER & SETTER*/
}
