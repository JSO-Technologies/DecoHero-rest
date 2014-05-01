package com.jso.deco.api.common;

public enum DecoStatus {
	N("Newbie"), L("Lover"), A("Audacious"), S("Savior"), H("Hero"), SH("Super hero"); 

	private final String dictionnary;
	private DecoStatus(String dictionnary) {
		this.dictionnary = dictionnary;
	}
	
	public String getDictionnary() {
		return dictionnary;
	}
}
