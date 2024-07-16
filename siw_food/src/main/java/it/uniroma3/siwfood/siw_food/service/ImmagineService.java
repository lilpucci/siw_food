package it.uniroma3.siwfood.siw_food.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Immagine;
import it.uniroma3.siwfood.siw_food.model.Ricetta;
import it.uniroma3.siwfood.siw_food.repository.ImmagineRepository;
import jakarta.transaction.Transactional;

@Service
public class ImmagineService {
    
    @Autowired
    private ImmagineRepository immagineRepository;
    

    public Immagine findById(Long id){
        return this.immagineRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Immagine immagine){
        this.immagineRepository.delete(immagine);
    }

    @Transactional
    public Immagine save(Immagine immagine){
        return this.immagineRepository.save(immagine);
    }

    //se non vuoto il multipart file lo aggiunge alla collezione di foto del cuoco
    public void addFotoToCuoco(Cuoco cuoco, MultipartFile immagine) throws IOException{
        if(!immagine.isEmpty()){
            Immagine img = new Immagine();
            img.setFileName(immagine.getOriginalFilename());
            img.setImageData(immagine.getBytes());
            //lo aggiungo al cuoco
            cuoco.getImmagini().add(img);
            this.save(img);
        }
    }

    //se non vuoto il multipart file lo aggiunge alla collezione di foto della ricetta
    public void addFotoToRicetta(Ricetta ricetta, MultipartFile immagine) throws IOException{
        if(!immagine.isEmpty()){
            Immagine image = new Immagine();
            image.setFileName(immagine.getOriginalFilename());
            image.setImageData(immagine.getBytes());
            // lo aggiungo alla ricetta
            ricetta.getImmagini().add(image);
            this.save(image);
        }
    }
    
}
