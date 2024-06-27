package it.uniroma3.siwfood.siw_food.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credentials {
    
    public static final String UTENTE_GENERICO = "GENERICO";
    public static final String UTENTE_REGISTRATO = "REGISTRATO";
    public static final String ADMIN = "ADMIN";
    

    /*ATTRIBUTI CREDENTIALS*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    @OneToOne
    private Utente utente;
    /*FINE ATTRIBUTI CREDENTIALS*/
}
