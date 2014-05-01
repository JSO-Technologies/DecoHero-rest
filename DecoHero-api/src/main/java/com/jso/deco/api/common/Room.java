package com.jso.deco.api.common;

public enum Room {
	ATC("Attic"), BAR("Bathroom"), BER("Bedroom"), CEL("Cellar"), ENT("Entrance"), GAR("Garage"), HAW("Hallway"), KTC("Kitchen"), LAR("Laundry room"), LIR("Living room"), WC("WC");
	
	private final String dictionnary;
	private Room(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
	public String getDictionnary() {
		return dictionnary;
	}
}
