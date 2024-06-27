package it.uniroma3.siwfood.siw_food.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.siw_food.model.Credentials;
import it.uniroma3.siwfood.siw_food.repository.CredentialsRepository;

@Service
public class CredentialsService {
    
    @Autowired
    private CredentialsRepository credentialsRepository;


    public Credentials getCredentials(Long id){
        return this.credentialsRepository.findById(id).get();
    }

    public Optional<Credentials> getCredentialsByUsername(String username){
        return this.credentialsRepository.findByUsername(username);
    }

    public Credentials saveCredentials(Credentials credentials){
        return this.credentialsRepository.save(credentials);
    }

}
