package com.jso.deco.api.common;

public enum JobField {
	ADMN("Administrative"),
	AENA("Aéronautics"),
	AGRI("Agriculture"),
	AGBU("Agribusiness"),
	ARCH("Architecture - Construction industry"),
	ART("Art"),
	CRAF("Crafts"),
	INSU("Insurance"),
	AUVI("Audiovisual - Cinema"),
	AUTO("Automobile"),
	BANK("Bank - Finance"),
	CHMT("Chemistry - Biology"),
	BUSI("Business - Selling"),
	COMM("Communication"),
	ACCT("Accounting"),
	CREA("Creation"),
	CULT("Culture"),
	SUPP("Retailing - Supply"),
	LAW("Law - Justice"),
	ECO("Economy"),
	ELCT("Electronics"),
	NRJ("Energy - Nuclear"),
	TEAC("Teaching"),
	ENV("Environment"),
	BEAU("Aesthetics - Beauty"),
	PUBL("Public service"),
	HOTL("Hotel - Restaurant"),
	HUMA("Humanitarian"),
	RESB("Real estate business"),
	INDU("Industry"),
	IT("Information Technology"),
	ITNT("Internet"),
	JRNM("Journalism"),
	ANML("Animal care"),
	FSHN("Fashion - Luxury"),
	MEDI("Multimedia"),
	MUSC("Music"),
	PARA("Paramedical"),
	PST("Psychology"),
	ADVT("Advertising - Marketing"),
	HURE("Human resources"),
	HLTH("Health"),
	SCRT("Secretaryship"),
	SECU("Security - Defense"),
	SOCL("Social"),
	SPRT("Sport"),
	TECH("Technician"),
	TOUR("Tourism"),
	TRSP("Transport - Logistics");

	private final String dictionnary;
	private JobField(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
	public String getDictionnary() {
		return dictionnary;
	}
}
