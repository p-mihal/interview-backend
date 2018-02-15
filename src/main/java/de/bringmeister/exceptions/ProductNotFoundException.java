package de.bringmeister.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Product")  // 404
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super("No product found for id:" + id);
    }
}
