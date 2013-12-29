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
	
	private String dictionnary;
	private Job(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
}
