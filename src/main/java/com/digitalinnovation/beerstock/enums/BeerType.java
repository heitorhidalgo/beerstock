package com.digitalinnovation.beerstock.enums;

import lombok.Getter;

@Getter
public enum BeerType {

	LAGER("Lager"), MALZBIER("Malzbier"), WITBIER("Witbier"), WEISS("Weiss"), ALE("Ale"), IPA("IPA"), STOUT("Stout");

	private final String description;

	BeerType(String description) {
		this.description = description;
	}
}