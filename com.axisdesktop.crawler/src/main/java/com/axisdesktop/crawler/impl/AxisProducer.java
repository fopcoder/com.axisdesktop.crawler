package com.axisdesktop.crawler.impl;

import java.net.URI;
import java.net.URISyntaxException;

import com.axisdesktop.crawler.base.Queue;

public class AxisProducer extends Thread {
	private Queue queue;

	public AxisProducer( Queue queue ) {
		this.queue = queue;
	}

	@Override
	public void run() {
		for( int i = 0; i < 10; i++ ) {
			try {
				String uri = "/shit/fuck" + i;
				this.queue.put( new URI( uri ) );
				System.out.println( "produced: " + uri );
				sleep( (long)( Math.random() * 1000 ) );
			}
			catch( URISyntaxException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch( InterruptedException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
