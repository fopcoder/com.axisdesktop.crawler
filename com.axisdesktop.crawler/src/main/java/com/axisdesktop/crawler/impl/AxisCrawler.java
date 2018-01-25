package com.axisdesktop.crawler.impl;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.base.Crawler;
import com.axisdesktop.crawler.base.Queue;
import com.axisdesktop.crawler.base.Worker;
import com.axisdesktop.crawler.entity.Provider;

public class AxisCrawler extends Crawler {
	private Queue queue;
	private AxisProducer prod;

	private final String propertiesFileName = "crawler.properties";

	public AxisCrawler() throws IOException {
		this.loadProperties( this.propertiesFileName );
		this.buildSessionFactory( this.getProperties() );

		this.queue = new AxisQueue();
		this.prod = new AxisProducer( queue );
		this.prod.start();

		Session ses = this.getSessionFactory().openSession();
		Transaction tx = ses.beginTransaction();

		Provider prov = ses.load( Provider.class, 2 );

		System.out.println( prov );

		String sql = "SELECT * FROM {h-schema}provider";
		// SQLQuery query = ses.createSQLQuery( sql );
		Query query = ses.createNativeQuery( sql );
		List results = query.getResultList();

		System.out.println( results );

		// query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP );
		// List results = query.list();

		tx.commit();
		ses.close();
	}

	@Override
	protected Worker createWorker( URI uri ) {
		return new AxisWorker( this, uri );
	}

	@Override
	protected URI shiftURI() {
		URI u = this.queue.get();
		if( u != null ) System.out.println( "consumed: " + u.toString() );

		return u;
	}
}
