package com.zxventures.challenge.service;

import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.model.repository.PdvRepository;
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
        return pdvRepository.save(pdv);
    }

    public Optional<Pdv> findById(Long id){
        return pdvRepository.findById(id);
    }

    public Optional<Pdv> findCloserFrom(Point point){
        return pdvRepository.findAll().stream()
                .sorted(Comparator.comparing(pdv -> pdv.distanceFrom(point)))
                .filter(pdv -> pdv.serveRegion(point))
                .findFirst();
    }
}