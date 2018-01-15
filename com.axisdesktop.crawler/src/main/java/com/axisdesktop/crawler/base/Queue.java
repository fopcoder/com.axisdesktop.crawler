package com.axisdesktop.crawler.base;

import java.net.URI;
import java.util.List;

public interface Queue {
	URI get();

	int put( URI u );

	int put( List<URI> l );
}
