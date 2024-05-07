package it.uniroma3.siw.model;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Cuoco {
    
    /*ATTRIBUTI CUOCO*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascita;
    private String URL;
    /*FINE ATTRIBUTI*/

    /*EQUALS & HASHCODE*/
    @Override
    public int hashCode() {
        return Objects.hash(nome,cognome,dataNascita);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cuoco other = (Cuoco) obj;
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
        if (dataNascita == null) {
            if (other.dataNascita != null)
                return false;
        } else if (!dataNascita.equals(other.dataNascita))
            return false;
        return true;
    }    
    /*FINE EQUALS & HASHCODE*/

    /*GETTER & SETTER*/
    //id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //cognome
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    //dataNascita
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    //url
    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }
    /*FINE GETTER & SETTER*/
    
}