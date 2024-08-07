package it.uniroma3.siwfood.siw_food.model.auth;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
public class Utente {
    
    /*ATTRIBUTI*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String nome;
    @NotBlank
    private String cognome;
    
    //private String luogoDiNascita;

    @Past
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascita;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cuoco cuoco;
    
    //@NotBlank
    //private String email;
    /*FINE ATTRIBUTI*/


    /*EQUALS & HASHCODE*/
    @Override
    public int hashCode() {
        return Objects.hash(nome,cognome/*,email*/);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Utente other = (Utente) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (cognome == null) {
            if (other.cognome != null)
                return false;
        } else if (!cognome.equals(other.cognome))
            return false;
        /*if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;*/
        return true;
    }
    //TODO toString
    /*FINE EQUALS & HASHCODE*/
    

    /*COSTRUTTORI*/
    public Utente(){

    }

    public Utente(String nome, String cognome, /*String email, String luogoDiNascita,*/ LocalDate dataNascita){
        this.nome = nome;
        this.cognome = cognome;
        //this.email = email;
        //this.luogoDiNascita = luogoDiNascita;
        this.dataNascita = dataNascita;
    }
    /*FINE COSTRUTTORI*/


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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /*public String getLuogoDiNascita() {
        return luogoDiNascita;
    }

    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }*/

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    /*public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

    public Cuoco getCuoco() {
        return cuoco;
    }

    public void setCuoco(Cuoco cuoco) {
        this.cuoco = cuoco;
    }
    /*FINE GETTER & SETTER*/

    
}
