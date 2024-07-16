package it.uniroma3.siwfood.siw_food.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.auth.Credentials;
import it.uniroma3.siwfood.siw_food.model.auth.Utente;
import it.uniroma3.siwfood.siw_food.repository.CredentialsRepository;

@Service
public class CredentialsService {
    
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*RICERCHE*/
    //in base all'id
    public Credentials getCredentials(Long id){
        return this.credentialsRepository.findById(id).get();
    }
    //in base all'username
    public Credentials getCredentialsByUsername(String username){
        return this.credentialsRepository.findByUsername(username).get();
    }
    /*FINE RICERCHE*/

    /*SALVATAGGIO*/
    public Credentials saveCredentials(Credentials credentials){

        //String vecchiapwd = credentials.getPassword();  non serve
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    
    }
    /*FINE SALVATAGGIO*/

    /*CANCELLAZIONE*/
    public void deleteCredentials(Credentials credentials){
        this.credentialsRepository.delete(credentials);
    }

    public void deleteCredentialsByUtente(Utente utente){
        this.deleteCredentials(this.credentialsRepository.findByUtente(utente));
    }
    /*FINE CANCELLAZIONE*/

}
