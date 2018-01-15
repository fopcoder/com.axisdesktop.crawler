package com.axisdesktop.crawler.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.axisdesktop.crawler.base.Queue;

/*
 0 - new
 1 - in process
 2 - done
 3 - error
 */

public class AxisQueue implements Queue {
	private boolean available = false;
	private Map<URI, Integer> urls;

	public AxisQueue() {
		this.urls = new HashMap<>();
	}

	@Override
	public synchronized URI get() {
		while( available == false ) {
			try {
				wait( 10_000 );
				if( available == false ) {
					System.out.println( "return!" );
					return null;
				}
			}
			catch( InterruptedException e ) {}
		}

		available = false;
		notifyAll();

		for( Map.Entry<URI, Integer> qe : urls.entrySet() ) {
			if( qe.getValue() == 0 ) {
				qe.setValue( 2 );
				return qe.getKey();
			}
		}

		return null;
	}

	@Override
	public synchronized int put( URI u ) {
		while( available == true ) {
			try {
				wait();
			}
			catch( InterruptedException e ) {}
		}

		urls.putIfAbsent( u, 0 );
		available = true;
		notifyAll();

		return 1;
	}

	@Override
	public synchronized int put( List<URI> l ) {
		// TODO Auto-generated method stub
		return 0;
	}

}
