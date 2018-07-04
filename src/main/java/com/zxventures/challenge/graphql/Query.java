package com.zxventures.challenge.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.zxventures.challenge.model.Point;
import com.zxventures.challenge.dto.PdvConverter;
import com.zxventures.challenge.dto.read.GetPdvDto;
import com.zxventures.challenge.service.PdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private PdvService pdvService;

    @Autowired
    private PdvConverter pdvConverter;

    public GetPdvDto getPdvById(String id){
        return pdvService.findById(id).map(pdvConverter::fromModelToDto).orElse(null);
    }

    public GetPdvDto getCloserFrom(BigDecimal lng, BigDecimal lat){
        return pdvService.findCloserFrom(new Point(lng, lat)).map(pdvConverter::fromModelToDto).orElse(null);
    }
}