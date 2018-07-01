package com.zxventures.challenge.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Polygon must have at least 3 vertices")
public class InvalidPolygonException extends RuntimeException {
}
