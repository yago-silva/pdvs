package com.zxventures.challenge.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Document already used")
public class DocumentAlreadyUsedException extends RuntimeException implements GraphQLError {

    public DocumentAlreadyUsedException(String message){
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
    }
}
