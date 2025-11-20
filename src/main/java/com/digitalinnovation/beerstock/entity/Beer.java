package com.digitalinnovation.beerstock.entity;

import com.digitalinnovation.beerstock.enums.BeerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "beer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private int max;

	@Column(nullable = false)
	private int quantity;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BeerType type;
}