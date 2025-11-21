package com.digitalinnovation.beerstock.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
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
import com.digitalinnovation.beerstock.mapper.BeerMapper;
import com.digitalinnovation.beerstock.repository.BeerRepository;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

	@Mock
	private BeerRepository beerRepository;

	@Mock
	private BeerMapper beerMapper;

	@InjectMocks
	private BeerService beerService;

	@Test
	void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
		// ARRANGE (Cenário)
		BeerDTO beerDTO = BeerDTO.builder().name("Brahma").brand("Ambev").max(10).quantity(5).type(BeerType.LAGER)
				.build();
		Beer expectedSavedBeer = Beer.builder().id(1L).name("Brahma").brand("Ambev").max(10).quantity(5)
				.type(BeerType.LAGER).build();

		// Ensinando os Mocks
		when(beerRepository.findByName(beerDTO.getName())).thenReturn(Optional.empty());
		when(beerMapper.toModel(beerDTO)).thenReturn(expectedSavedBeer);
		when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);
		when(beerMapper.toDTO(expectedSavedBeer)).thenReturn(beerDTO);

		// ACT (Execução)
		BeerDTO createdBeerDTO = beerService.createBeer(beerDTO);

		// ASSERT (Verificação)
		assertThat(createdBeerDTO.getId(), is(equalTo(beerDTO.getId())));
		assertThat(createdBeerDTO.getName(), is(equalTo(beerDTO.getName())));
		assertThat(createdBeerDTO.getQuantity(), is(equalTo(beerDTO.getQuantity())));
	}
}