package com.jso.deco.api.common;

public enum Gender {
	M("Male"), F("Female");
	
	private final String dictionnary;
	private Gender(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
	public String getDictionnary() {
		return dictionnary;
	}
}
