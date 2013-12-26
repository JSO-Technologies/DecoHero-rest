package com.jso.deco.api.common;

public enum Job {
	FARM("Farm worker"),
	MNGR("Craftman, storekeeper, manager"),
	INGR("Intellectual or liberal profession, engineer"),
	ITOC("Intermediate Occupations (Ex : teacher, nurse, technician ...)"),
	EMPL("Employee"),
	WORK("Worker"),
	RETI("Retired"),
	STUD("Student, trainee"),
	HISC("(Junior) high school student"),
	UNEM("Unemployed"),
	HOUS("House wife, house husband"),
	OTHR("Other");
	
//	{"id" :1, "label" : "Agriculteur, exploitant ou ouvrier agricole"}
//	{"id" :2, "label" : "Artisan, commerçant, chef d'entreprise"}
//	{"id" :3, "label" : "Profession intellectuelle, libérale ou cadre"}
//	{"id" :4, "label" : "Profession intermédiaire (Ex : Instituteur, infirmier, technicien ...)"}
//	{"id" :5, "label" : "Employé"}
//	{"id" :6, "label" : "Ouvrier"}
//	{"id" :7, "label" : "Retraité"}
//	{"id" :8, "label" : "Etudiant, stagiaire"}
//	{"id" :9, "label" : "Collégien, lycéen"}
//	{"id" :10, "label" : "Demandeur d'emploi"}
//	{"id" :11, "label" : "Homme ou femme au foyer"}
//	{"id" :12, "label" : "Autre"}
	
	private String dictionnary;
	private Job(String dictionnary) {
		this.dictionnary = dictionnary;
	}
}
