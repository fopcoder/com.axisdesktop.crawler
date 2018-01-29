package com.axisdesktop.crawler.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.axisdesktop.crawler.base.Crawler;
import com.axisdesktop.crawler.base.Queue;
import com.axisdesktop.crawler.base.Worker;
import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderDataType;
import com.axisdesktop.crawler.entity.ProviderStatus;
import com.axisdesktop.crawler.entity.ProviderUrl;
import com.axisdesktop.crawler.entity.ProviderUrlStatus;
import com.axisdesktop.crawler.service.ProviderService;
import com.axisdesktop.crawler.service.ProviderServiceDb;
import com.axisdesktop.crawler.service.ProviderUrlService;
import com.axisdesktop.crawler.service.ProviderUrlServiceDb;

public class AxisCrawler extends Crawler {
	private Queue queue;
	private AxisProducer prod;
	private Session session;

	private final String propertiesFileName = "crawler.properties";

	public AxisCrawler() throws IOException {
		this.loadProperties( this.propertiesFileName );
		SessionFactory factory = this.buildSessionFactory( this.getProperties() );

		ProviderService provServ = new ProviderServiceDb( factory );
		ProviderUrlService urlServ = new ProviderUrlServiceDb( factory );

		Provider prov = provServ.createIfNotExists( "axisdesktop.com", ProviderStatus.ACTIVE );

		for( String u : importFeedUrls( "feed.urls" ) ) {
			ProviderUrl purl = new ProviderUrl( prov, u, ProviderDataType.FEED, ProviderUrlStatus.PENDING );
			urlServ.createIfNotExists( purl );
		}

		// this.queue = new AxisQueue();
		// this.prod = new AxisProducer( queue );
		// this.prod.start();

		// this.session = this.getSessionFactory().openSession();
		// Transaction tx = session.beginTransaction();

		// String sql = "SELECT * FROM {h-schema}provider";
		// SQLQuery query = ses.createSQLQuery( sql );
		// Query query = ses.createNativeQuery( sql );
		// List results = query.getResultList();

		// System.out.println( results );

		// query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP );
		// List results = query.list();

		// tx.commit();
		// this.session.close();
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

	public List<String> importFeedUrls( String fname ) {
		List<String> l = new ArrayList<>();

		try( Stream<String> stream = Files.lines( Paths.get( ClassLoader.getSystemResource( fname ).toURI() ) ) ) {
			stream.forEach( line -> {
				if( line != null && !line.isEmpty() ) {
					l.add( line );
				}
			} );
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
		catch( URISyntaxException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return l;
	}

	// public Provider getProviderByName( String name ) {
	// // Criteria criteria = this.session.createCriteria( Provider.class );
	// // Provider p = (Provider)criteria.add( Restrictions.eq( "name", name ) ).uniqueResult();
	// // this.getSessionFactory().getCurrentSession().
	//
	// Query<Provider> query = this.session.getNamedQuery( "Provider.getByName" );
	// query.setParameter( "name", name );
	// Provider p = query.getSingleResult();
	//
	// return p;
	// }
}
