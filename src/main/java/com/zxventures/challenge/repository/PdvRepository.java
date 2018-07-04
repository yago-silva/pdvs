package com.zxventures.challenge.repository;

import com.zxventures.challenge.model.Pdv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PdvRepository  extends MongoRepository<Pdv, String> {
    Optional<Pdv> findByDocument(String document);
}
