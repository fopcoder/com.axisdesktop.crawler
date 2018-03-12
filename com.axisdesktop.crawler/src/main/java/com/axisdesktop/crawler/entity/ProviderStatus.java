package com.axisdesktop.crawler.entity;

public enum ProviderStatus {
	PENDING(0, "pending"), //
	ACTIVE(1, "active"), //
	INACTIVE(2, "inactive"), //
	DELETED(3, "deleted");

	private int id;
	private String name;

	private ProviderStatus(int statusId, String name) {
		this.id = statusId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
