package com.axisdesktop.crawler.service;

import com.axisdesktop.crawler.entity.CrawlerProxy;
import com.axisdesktop.crawler.entity.Provider;

public abstract class CrawlerProxyService implements CrawlerService<CrawlerProxy, Integer> {
	abstract public CrawlerProxy createIfNotExists( String host, int port );
}
