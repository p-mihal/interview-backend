package de.bringmeister.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No price for such product and unit")  // 404
public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(String productId, String unit) {
        super(String.format("No price found for product with id: %s and unit: %s" , productId, unit));
    }
}
