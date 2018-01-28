package com.axisdesktop.crawler.entity;

public enum ProviderUrlStatus {
	PENDING(0, "pending"), //
	PROCESS(1, "process"), //
	UPDATE(2, "update"), //
	DONE(3, "done"), //
	ERROR(4, "error"), //
	INACTIVE(5, "inactive"), //
	DELETED(6, "deleted");

	private int id;
	private String name;

	private ProviderUrlStatus( int id, String name ) {
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
