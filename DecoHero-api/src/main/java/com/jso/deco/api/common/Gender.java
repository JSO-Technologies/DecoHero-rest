package com.jso.deco.api.common;

public enum Gender {
	M("Male"), F("Female");
	
	private String dictionnary;
	private Gender(String dictionnary) {
		this.dictionnary = dictionnary;
	}
}
