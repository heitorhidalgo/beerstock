package com.digitalinnovation.beerstock.service;

import com.digitalinnovation.beerstock.dto.BeerDTO;
import com.digitalinnovation.beerstock.entity.Beer;
import com.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
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
		// Regra 1: Verificar se já existe uma cerveja com esse nome
		Optional<Beer> optSavedBeer = beerRepository.findByName(beerDTO.getName());
		if (optSavedBeer.isPresent()) {
			throw new BeerAlreadyRegisteredException(beerDTO.getName());
		}

		// Regra 2: Converter DTO para Entidade, Salvar e Converter de volta
		Beer beer = beerMapper.toModel(beerDTO);
		Beer savedBeer = beerRepository.save(beer);
		return beerMapper.toDTO(savedBeer);
	}
}