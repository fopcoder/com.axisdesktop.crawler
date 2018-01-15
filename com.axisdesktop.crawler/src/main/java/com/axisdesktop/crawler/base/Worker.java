package com.axisdesktop.crawler.base;

import java.net.URI;

public interface Worker extends Runnable {
	Crawler getCrawler();

	URI getUri();

	String getUriContent();
}
