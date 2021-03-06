package com.axisdesktop.crawler.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.base.Queue;
import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderUrl;
import com.axisdesktop.crawler.entity.ProviderUrlStatus;
import com.axisdesktop.crawler.service.ProviderUrlService;
import com.axisdesktop.crawler.service.ProviderUrlServiceDb;

public class DbQueue implements Queue {
	private boolean available = true;
	private Map<URI, Integer> urls;
	private SessionFactory factory;
	private Provider provider;

	public DbQueue( Provider provider, SessionFactory factory ) {
		this.factory = factory;
		this.provider = provider;
	}

	@Override
	public synchronized URI get() {
		while( available == false ) {
			try {
				wait( 60_000 );
				if( available == false ) {
					System.out.println( "return!" );
					return null;
				}
			}
			catch( InterruptedException e ) {}
		}

		ProviderUrlService pService = new ProviderUrlServiceDb( factory );

		Session ses = factory.openSession();
		Transaction tx = ses.beginTransaction();

		// ses.createNamedQuery( "ProviderUrl.ProviderUrl.findActiveUrl" );
		Calendar cal = Calendar.getInstance();
		cal.set( 2019, 1, 1 );

		Query<ProviderUrl> query = ses.getNamedQuery( "ProviderUrl.findActiveUrl" );
		query.setParameter( "providerId", provider.getId() );
		query.setParameter( "maxTries", 15 );
		query.setParameter( "nextTimeFeed", cal );
		query.setParameter( "nextTimeItem", cal );
		ProviderUrl pu = query.getSingleResult();

		pu.setStatusId( ProviderUrlStatus.PROCESS );
		pu.setModified( Calendar.getInstance() );

		ses.save( pu );

		tx.commit();
		ses.close();

		available = false;
		notifyAll();

		URI uri = null;

		try {
			uri = new URI( pu.getUrl() );
		}
		catch( URISyntaxException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
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
