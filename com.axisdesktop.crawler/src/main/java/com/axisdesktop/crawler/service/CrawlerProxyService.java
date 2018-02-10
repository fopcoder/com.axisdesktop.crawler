package com.axisdesktop.crawler.service;

import com.axisdesktop.crawler.entity.CrawlerProxy;

public abstract class CrawlerProxyService implements CrawlerService<CrawlerProxy, Integer> {
	abstract public CrawlerProxy createIfNotExists( String host, int port );

	abstract public CrawlerProxy createIfNotExists( CrawlerProxy proxy );
}
