package com.jso.deco.api.common;

public enum HouseType {
	HT("House tenant"), AT("Apartment tenant"), HO("House owner"), AO("Apartment owner");
	
	private final String dictionnary;
	private HouseType(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
	public String getDictionnary() {
		return dictionnary;
	}
}
