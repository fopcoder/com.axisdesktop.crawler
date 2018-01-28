package com.axisdesktop.crawler.impl;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.base.Crawler;
import com.axisdesktop.crawler.base.Queue;
import com.axisdesktop.crawler.base.Worker;
import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderStatus;

public class AxisCrawler extends Crawler {
	private Queue queue;
	private AxisProducer prod;
	private Session session;

	private final String propertiesFileName = "crawler.properties";

	public AxisCrawler() throws IOException {
		this.loadProperties( this.propertiesFileName );
		this.buildSessionFactory( this.getProperties() );

		// this.queue = new AxisQueue();
		// this.prod = new AxisProducer( queue );
		// this.prod.start();

		this.session = this.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();

		Provider p = new Provider( "kokoko", ProviderStatus.ACTIVE );
		p.setName( "kokoko" );
		// session.save( p );
		this.session.persist( p );

		System.out.println( p.getId() );

		Provider p2 = this.getProviderByName( "kokoko" );

		System.out.println( p2.getId() );

		// p = new Provider();
		// p.setName( "kokoko" );
		// session.persist( p );

		// String sql = "SELECT * FROM {h-schema}provider";
		// SQLQuery query = ses.createSQLQuery( sql );
		// Query query = ses.createNativeQuery( sql );
		// List results = query.getResultList();

		// System.out.println( results );

		// query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP );
		// List results = query.list();

		tx.commit();
		this.session.close();
		this.getSessionFactory().close();

		try {
			Server server = Server.createWebServer( "-web" ).start();
		}
		catch( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public Provider getProviderByName( String name ) {
		// Criteria criteria = this.session.createCriteria( Provider.class );
		// Provider p = (Provider)criteria.add( Restrictions.eq( "name", name ) ).uniqueResult();
		// this.getSessionFactory().getCurrentSession().

		Query<Provider> query = this.session.getNamedQuery( "Provider.getByName" );
		query.setParameter( "name", name );
		Provider p = query.getSingleResult();

		return p;
	}
}
