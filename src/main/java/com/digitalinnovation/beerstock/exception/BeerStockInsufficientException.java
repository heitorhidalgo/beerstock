package com.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockInsufficientException extends Exception {

	public BeerStockInsufficientException(Long id, int quantityToDecrement) {
		super(String.format("Beer with %s ID to decrement informed exceeds the current stock capacity: %s", id,
				quantityToDecrement));
	}
}