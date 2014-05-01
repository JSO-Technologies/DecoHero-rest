package com.jso.deco.api.common;

public enum Relationship {
	S("Single"), R("in a Relationship"), M("Married"), D("Divorced"), W("Widowed");
	
	private final String dictionnary;
	private Relationship(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
	public String getDictionnary() {
		return dictionnary;
	}
}
