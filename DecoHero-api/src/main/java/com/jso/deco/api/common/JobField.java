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
//	{"id" :1, "label" : "Administratif"),
//	{"id" :2, "label" : "Aéronautique"),
//	{"id" :3, "label" : "Agriculture"),
//	{"id" :4, "label" : "Agroalimentaire"),
//	{"id" :5, "label" : "Architecture - BTP - Urbanisme"),
//	{"id" :6, "label" : "Art"),
//	{"id" :7, "label" : "Artisanat"),
//	{"id" :8, "label" : "Assurance"),
//	{"id" :9, "label" : "Audiovisuel - Cinéma"),
//	{"id" :10, "label" : "Automobile"),
//	{"id" :11, "label" : "Banque - Finance"),
//	{"id" :12, "label" : "Chimie - Biologie"),
//	{"id" :13, "label" : "Commerce - Vente"),
//	{"id" :14, "label" : "Communication"),
//	{"id" :15, "label" : "Comptabilité - Gestion"),
//	{"id" :16, "label" : "Création"),
//	{"id" :17, "label" : "Culture"),
//	{"id" :18, "label" : "Distribution"),
//	{"id" :19, "label" : "Droit - Justice"),
//	{"id" :20, "label" : "Economie"),
//	{"id" :21, "label" : "Electronique"),
//	{"id" :22, "label" : "Energie - Nucléaire"),
//	{"id" :23, "label" : "Enseignement"),
//	{"id" :24, "label" : "Environnement"),
//	{"id" :25, "label" : "Esthétique - Beauté"),
//	{"id" :26, "label" : "Fonction publique - Management public"),
//	{"id" :27, "label" : "Hôtellerie - Restauration"),
//	{"id" :28, "label" : "Humanitaire"),
//	{"id" :29, "label" : "Immobilier"),
//	{"id" :30, "label" : "Industrie"),
//	{"id" :31, "label" : "Informatique"),
//	{"id" :32, "label" : "Internet"),
//	{"id" :33, "label" : "Journalisme"),
//	{"id" :34, "label" : "Métiers animaliers"),
//	{"id" :35, "label" : "Mode - Luxe"),
//	{"id" :36, "label" : "Multimédia"),
//	{"id" :37, "label" : "Musique"),
//	{"id" :38, "label" : "Paramédical"),
//	{"id" :39, "label" : "Psychologie"),
//	{"id" :40, "label" : "Publicité - Marketing"),
//	{"id" :41, "label" : "Ressources humaines"),
//	{"id" :42, "label" : "Santé"),
//	{"id" :43, "label" : "Secrétaire-Assistant(e)"),
//	{"id" :44, "label" : "Sécurité - Défense"),
//	{"id" :45, "label" : "Social"),
//	{"id" :46, "label" : "Sport"),
//	{"id" :47, "label" : "Technicien"),
//	{"id" :48, "label" : "Tourisme"),
//	{"id" :49, "label" : "Transport - Logistique"),
	
	private String dictionnary;
	private JobField(String dictionnary) {
		this.dictionnary = dictionnary;
	}
}
