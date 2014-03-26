package com.jso.deco.controller.image;

public enum ImageSize {
	FULL(0, ""), INTER(650, "-inter"), THUMB(300, "-thumb");
	
	private int size;
	private String suffix;
	
	private ImageSize(final int size, final String suffix) {
		this.size = size;
		this.suffix = suffix;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getSuffix() {
		return suffix;
	}
}
