package com.axisdesktop.crawler.service;

import com.axisdesktop.crawler.entity.ProviderUrl;

public abstract class ProviderUrlService implements CrawlerService<ProviderUrl, Long> {
	abstract public ProviderUrl createIfNotExists( ProviderUrl purl );
}
