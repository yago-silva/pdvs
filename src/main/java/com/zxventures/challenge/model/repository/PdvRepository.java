package com.zxventures.challenge.model.repository;

import com.zxventures.challenge.model.Pdv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdvRepository  extends MongoRepository<Pdv, String> {

}
