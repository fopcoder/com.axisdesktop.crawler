package com.axisdesktop.crawler.service;

import com.axisdesktop.crawler.entity.Provider;

public abstract class ProviderService implements CrawlerService<Provider, Integer> {
	abstract public void createUrl();
	abstract public void isUrlExists();
}
