package com.axisdesktop.crawler.impl;

import java.net.URI;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.base.Crawler;
import com.axisdesktop.crawler.base.Worker;

public class AxisWorker implements Worker {
	private Crawler crawler;
	private URI uri;

	public AxisWorker( Crawler crawler, URI uri ) {
		if( crawler == null ) throw new IllegalArgumentException( "Carwler is null" );
		if( uri == null ) throw new IllegalArgumentException( "URI is null" );

		this.crawler = crawler;
		this.uri = uri;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println( "thread " + this.uri.toString() );

		Session ses = this.crawler.getSessionFactory().openSession();
		Transaction tx = ses.beginTransaction();

		String sql = "SELECT * FROM {h-schema}provider";
		// SQLQuery query = ses.createSQLQuery( sql );
		Query query = ses.createNativeQuery( sql );
		List results = query.getResultList();

		System.out.println( results );

		// query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP );
		// List results = query.list();

		tx.commit();
		ses.close();

		// try {
		// Thread.sleep( 1000 );
		// }
		// catch( InterruptedException e ) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public URI getUri() {
		return this.uri;
	}

	@Override
	public String getUriContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Crawler getCrawler() {
		return this.crawler;
	}

}
