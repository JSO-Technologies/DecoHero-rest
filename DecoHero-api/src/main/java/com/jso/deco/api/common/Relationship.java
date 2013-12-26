package com.jso.deco.api.common;

public enum Relationship {
	S("Single"), R("in a Relationship"), M("Married"), D("Divorced"), W("Widowed");
	
	private String dictionnary;
	private Relationship(String dictionnary) {
		this.dictionnary = dictionnary;
	}
}
