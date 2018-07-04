package com.zxventures.challenge.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.zxventures.challenge.dto.create.CreatePdvDto;
import com.zxventures.challenge.exception.DocumentAlreadyUsedException;
import com.zxventures.challenge.exception.ValidationException;
import com.zxventures.challenge.dto.PdvConverter;
import com.zxventures.challenge.dto.read.GetPdvDto;
import com.zxventures.challenge.model.Pdv;
import com.zxventures.challenge.repository.PdvRepository;
import com.zxventures.challenge.service.PdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private PdvService pdvService;

    @Autowired
    private PdvConverter pdvConverter;

    @Autowired
    private Validator validator;

    public GetPdvDto save(CreatePdvDto dto) {
        Set<ConstraintViolation<CreatePdvDto>> constraintViolations = validator.validate(dto);
        if(!constraintViolations.isEmpty()){
            String errorMessage = constraintViolations.stream()
                    .map(violation -> String.format("%s %s", violation.getPropertyPath(), violation.getMessage()))
                    .reduce((a, b) -> String.join(" | ", a, b)).orElse("Validation error!");

            throw new ValidationException(errorMessage);
        }

        Pdv pdv = pdvService.save(pdvConverter.fromDtoToModel(dto));
        return pdvConverter.fromModelToDto(pdv);
    }
}