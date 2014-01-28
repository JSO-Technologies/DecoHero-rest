package com.jso.deco.api.common;

public enum Category {
	OB("Decoration object"), GD("Garden"), MW("Major work"), RM("Room decoration");
	
	private String dictionnary;
	private Category(String dictionnary) {
		this.dictionnary = dictionnary;
	}
}
