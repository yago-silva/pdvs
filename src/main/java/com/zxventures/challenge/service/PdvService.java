package com.zxventures.challenge.service;

import com.zxventures.challenge.exception.DocumentAlreadyUsedException;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.repository.PdvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PdvService {

    private PdvRepository pdvRepository;

    @Autowired
    PdvService(PdvRepository pdvRepository){
        this.pdvRepository = pdvRepository;
    }

    public Pdv save(Pdv pdv){
        pdvRepository.findByDocument(pdv.getDocument()).ifPresent( foundPdv -> {
            throw new DocumentAlreadyUsedException(String.format("Document %s already used", foundPdv.getDocument()));
        });
        return pdvRepository.save(pdv);
    }

    public Optional<Pdv> findById(String id){
        return Optional.ofNullable(pdvRepository.findOne(id));
    }

    public Optional<Pdv> findCloserFrom(Point point){
        return pdvRepository.findAll().stream()
                .sorted(Comparator.comparing(pdv -> pdv.distanceFrom(point)))
                .filter(pdv -> pdv.serveRegion(point))
                .findFirst();
    }
}