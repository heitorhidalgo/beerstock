package com.digitalinnovation.beerstock.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.digitalinnovation.beerstock.dto.BeerDTO;
import com.digitalinnovation.beerstock.entity.Beer;
import com.digitalinnovation.beerstock.enums.BeerType;
import com.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import com.digitalinnovation.beerstock.exception.BeerNotFoundException;
import com.digitalinnovation.beerstock.exception.BeerStockExceededException;
import com.digitalinnovation.beerstock.exception.BeerStockInsufficientException;
import com.digitalinnovation.beerstock.mapper.BeerMapper;
import com.digitalinnovation.beerstock.repository.BeerRepository;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

	private static final long INVALID_BEER_ID = 1L;

	@Mock
	private BeerRepository beerRepository;

	@Mock
	private BeerMapper beerMapper;

	@InjectMocks
	private BeerService beerService;

	@Test
	void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
		
		BeerDTO beerDTO = BeerDTO.builder().name("Brahma").brand("Ambev").max(10).quantity(5).type(BeerType.LAGER)
				.build();
		Beer expectedSavedBeer = Beer.builder().id(1L).name("Brahma").brand("Ambev").max(10).quantity(5)
				.type(BeerType.LAGER).build();

		
		when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.empty());
		when(beerMapper.toModel(beerDTO)).thenReturn(expectedSavedBeer);
		when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);
		when(beerMapper.toDTO(expectedSavedBeer)).thenReturn(beerDTO);

		
		BeerDTO createdBeerDTO = beerService.createBeer(beerDTO);

		
		assertThat(createdBeerDTO.getId(), is(equalTo(beerDTO.getId())));
		assertThat(createdBeerDTO.getName(), is(equalTo(beerDTO.getName())));
		assertThat(createdBeerDTO.getQuantity(), is(equalTo(beerDTO.getQuantity())));
	}

	@Test
	void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
		
		BeerDTO beerDTO = BeerDTO.builder().name("Brahma").brand("Ambev").max(10).quantity(5).type(BeerType.LAGER)
				.build();
		Beer duplicatedBeer = Beer.builder().id(1L).name("Brahma").brand("Ambev").max(10).quantity(5)
				.type(BeerType.LAGER).build();

		
		when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

		
		assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(beerDTO));
	}

	

	@Test
	void whenIncrementIsCalledThenUpdateStock() throws BeerNotFoundException, BeerStockExceededException {
		
		BeerDTO expectedBeerDTO = BeerDTO.builder().id(1L).name("Brahma").brand("Ambev").max(50).quantity(10)
				.type(BeerType.LAGER).build();

		
		Beer expectedBeer = Beer.builder().id(1L).name("Brahma").brand("Ambev").max(50).quantity(10)
				.type(BeerType.LAGER).build();

		
		when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
		when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

		int quantityToIncrement = 10;
		int expectedQuantityAfterIncrement = 20;

		
		BeerDTO expectedBeerDTOAfterIncrement = BeerDTO.builder().id(1L).name("Brahma").brand("Ambev").max(50)
				.quantity(expectedQuantityAfterIncrement).type(BeerType.LAGER).build();

		
		when(beerMapper.toDTO(expectedBeer)).thenReturn(expectedBeerDTOAfterIncrement);

		
		BeerDTO incrementedBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

		
		assertThat(incrementedBeerDTO.getQuantity(), is(equalTo(expectedQuantityAfterIncrement)));
		assertThat(incrementedBeerDTO.getMax(), is(equalTo(expectedBeerDTO.getMax())));
	}

	@Test
	void whenIncrementIsCalledWithInvalidIdThenThrowException() {
		int quantityToIncrement = 10;

		when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

		assertThrows(BeerNotFoundException.class, () -> beerService.increment(INVALID_BEER_ID, quantityToIncrement));
	}

	@Test
	void whenIncrementIsGreatherThanMaxThenThrowException() {
		BeerDTO expectedBeerDTO = BeerDTO.builder().name("Brahma").brand("Ambev").max(50).quantity(10)
				.type(BeerType.LAGER).build();

		Beer expectedBeer = Beer.builder().id(1L).name("Brahma").brand("Ambev").max(50).quantity(10)
				.type(BeerType.LAGER).build();

		when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

		int quantityToIncrement = 45; 

		assertThrows(BeerStockExceededException.class,
				() -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
	}

	@Test
	void whenDecrementIsCalledWithInvalidQuantityThenThrowException() {
		int quantityToDecrement = 80; 

		
		BeerDTO expectedBeerDTO = BeerDTO.builder().name("Brahma").brand("Ambev").max(50).quantity(10)
				.type(BeerType.LAGER).build();

		Beer expectedBeer = Beer.builder().id(1L).name("Brahma").brand("Ambev").max(50).quantity(10)
				.type(BeerType.LAGER).build();

		
		when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

		
		assertThrows(BeerStockInsufficientException.class,
				() -> beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement));
	}
}