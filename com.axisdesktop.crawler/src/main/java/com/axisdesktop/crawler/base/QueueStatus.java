package com.axisdesktop.crawler.base;

public enum QueueStatus {
	PENDING(0, "pending"), //
	PROCESS(1, "process"), //
	ERROR(2, "error"), //
	DONE(3, "done"), //
	INACTIVE(4, "inactive"), //
	DELETED(5, "deleted");

	private int id;
	private String name;

	private QueueStatus(int id, String name) {
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
