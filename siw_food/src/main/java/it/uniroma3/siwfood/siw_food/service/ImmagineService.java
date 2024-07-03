package it.uniroma3.siwfood.siw_food.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.siw_food.model.Cuoco;
import it.uniroma3.siwfood.siw_food.model.Immagine;
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

    public void addFotoToCuoco(Cuoco cuoco, MultipartFile immagine) throws IOException{
        if(!immagine.isEmpty()){
            Immagine image = new Immagine();
            image.setFileName(immagine.getOriginalFilename());
            image.setImageData(immagine.getBytes());
            if (cuoco.getImmagini() == null) {
                cuoco.setImmagini(new ArrayList<>());
            }
            cuoco.getImmagini().add(image);
            this.save(image);
        }
    }
}
