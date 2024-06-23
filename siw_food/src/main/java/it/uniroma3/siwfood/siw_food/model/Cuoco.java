package it.uniroma3.siwfood.siw_food.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cuoco {
    
    /*ATTRIBUTI CUOCO*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascita;
    
    @Column(nullable = false)
    private String urlImage;
    
    @OneToMany(mappedBy = "cuoco", cascade = CascadeType.ALL)
    private List<Ricetta> ricette;
    /*FINE ATTRIBUTI*/

    
    /*COSTRUTTORI*/
    public Cuoco(){

    }

    public Cuoco(String nome, String cognome, String url, LocalDate dataNascita){
        this.nome = nome;
        this.cognome = cognome;
        this.urlImage = url;
        this.dataNascita = dataNascita;
    }
    /*FINE COSTRUTTORI*/


    /*EQUALS & HASHCODE*/
    @Override
    public int hashCode() {
        return Objects.hash(nome,cognome,dataNascita);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cuoco other = (Cuoco) obj;
        return Objects.equals(nome, other.nome) &&
               Objects.equals(cognome, other.cognome) &&
               Objects.equals(dataNascita, other.dataNascita);
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

    //ricette 
    public void setRicette(List<Ricetta> ricette){
        this.ricette = ricette;
    }

    public List<Ricetta> getRicette(){
        return this.ricette;
    }

    //url
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String uRL) {
        urlImage = uRL;
    }
    /*FINE GETTER & SETTER*/
    
}