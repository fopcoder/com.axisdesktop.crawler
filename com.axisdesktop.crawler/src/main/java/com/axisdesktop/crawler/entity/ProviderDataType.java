package com.axisdesktop.crawler.entity;

public enum ProviderDataType {
	PENDING(0, "pending"), //
	FEED(1, "feed"), //
	CATEGORY(2, "category"), //
	ITEM(3, "item"), //
	IMAGE(4, "image"), //
	FILE(5, "file"), //
	URL(6, "url"), //
	COMMENT(7, "comment"), //
	TAG(8, "tag");//

	private int id;
	private String name;

	private ProviderDataType( int id, String name ) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
