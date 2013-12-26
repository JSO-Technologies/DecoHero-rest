package com.jso.deco.api.common;

public enum Style {
	A("Ancient"), R("Art deco"), T("Traditional"), M("Modern"), D("Design"), O("Out of context");
	
	private String dictionnary;
	private Style(String dictionnary) {
		this.dictionnary = dictionnary;
	}
}
