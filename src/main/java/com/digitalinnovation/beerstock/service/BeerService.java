package com.digitalinnovation.beerstock.service;

import com.digitalinnovation.beerstock.dto.BeerDTO;
import com.digitalinnovation.beerstock.entity.Beer;
import com.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import com.digitalinnovation.beerstock.exception.BeerNotFoundException;
import com.digitalinnovation.beerstock.exception.BeerStockExceededException;
import com.digitalinnovation.beerstock.mapper.BeerMapper;
import com.digitalinnovation.beerstock.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;

	public BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {
		verifyIfIsAlreadyRegistered(beerDTO.getName());
		Beer beer = beerMapper.toModel(beerDTO);
		Beer savedBeer = beerRepository.save(beer);
		return beerMapper.toDTO(savedBeer);
	}

	public BeerDTO increment(Long id, int quantityToIncrement)
			throws BeerNotFoundException, BeerStockExceededException {
		// Reutilizando o método auxiliar
		Beer beerToIncrementStock = verifyIfExists(id);

		if (beerToIncrementStock.getQuantity() + quantityToIncrement > beerToIncrementStock.getMax()) {
			throw new BeerStockExceededException(id, quantityToIncrement);
		}

		beerToIncrementStock.setQuantity(beerToIncrementStock.getQuantity() + quantityToIncrement);
		Beer incrementedBeerStock = beerRepository.save(beerToIncrementStock);

		return beerMapper.toDTO(incrementedBeerStock);
	}

	// --- Métodos Auxiliares (Privados) ---

	private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {
		Optional<Beer> optSavedBeer = beerRepository.findByName(name);
		if (optSavedBeer.isPresent()) {
			throw new BeerAlreadyRegisteredException(name);
		}
	}

	// Esse método será MUITO útil quando criarmos o 'decrement' e 'delete'
	private Beer verifyIfExists(Long id) throws BeerNotFoundException {
		return beerRepository.findById(id).orElseThrow(() -> new BeerNotFoundException(id));
	}
}