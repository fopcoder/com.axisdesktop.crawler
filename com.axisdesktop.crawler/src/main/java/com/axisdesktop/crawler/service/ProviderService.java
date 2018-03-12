package com.axisdesktop.crawler.service;

import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderStatus;

public abstract class ProviderService implements CrawlerService<Provider, Integer> {

	abstract public Provider createIfNotExists( String name );

	abstract public Provider createIfNotExists( Provider prov );

	abstract public Provider createIfNotExists( String name, ProviderStatus status );
}
