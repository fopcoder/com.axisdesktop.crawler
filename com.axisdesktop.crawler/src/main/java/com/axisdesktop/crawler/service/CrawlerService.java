package com.axisdesktop.crawler.service;

import java.io.Serializable;
import java.util.List;

public interface CrawlerService<T, ID extends Serializable> {
	ID create(T obj);

	T read(ID id);

	void update(T obj);

	void delete(T obj);

	List<T> findByProperty(String prop, String val);

	T getByProperty(String prop, String val);

	boolean isExist(ID id);

	boolean isExist(T obj);
}
