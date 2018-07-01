package com.zxventures.challenge.model.repository;

import com.zxventures.challenge.model.Pdv;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


//TODO: use real database implementation
@Component
public class PdvRepository {

    private Map<Long, Pdv> pdvs = new HashMap<>();

    public Pdv save(Pdv pdv){
        try {
            Long id = Long.valueOf(pdvs.entrySet().size()) + 1;

            Field declaredField = pdv.getClass().getDeclaredField("id");
            declaredField.setAccessible(true);
            declaredField.set(pdv, id);
            declaredField.setAccessible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        pdvs.put(pdv.getId(), pdv);
        return pdv;
    }

    public Optional<Pdv> findById(Long id){
        return Optional.ofNullable(pdvs.get(id));
    }

    public List<Pdv> findAll(){
        return pdvs.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public void deleteAll() {
        this.pdvs = new HashMap<>();
    }
}
