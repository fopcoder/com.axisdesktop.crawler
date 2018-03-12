package com.axisdesktop.crawler.entity;

public enum CrawlerProxyStatus {
	ACTIVE(0, "active"), //
	ERROR(1, "error"), //
	INACTIVE(2, "inactive"), //
	DELETED(3, "deleted");

	private int id;
	private String name;

	private CrawlerProxyStatus( int id, String name ) {
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
