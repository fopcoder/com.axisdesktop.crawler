package com.axisdesktop.crawler.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.hibernate.SessionFactory;

import com.axisdesktop.crawler.base.Crawler;
import com.axisdesktop.crawler.base.Queue;
import com.axisdesktop.crawler.base.Worker;
import com.axisdesktop.crawler.entity.CrawlerProxy;
import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderDataType;
import com.axisdesktop.crawler.entity.ProviderStatus;
import com.axisdesktop.crawler.entity.ProviderUrl;
import com.axisdesktop.crawler.entity.ProviderUrlStatus;
import com.axisdesktop.crawler.service.CrawlerProxyService;
import com.axisdesktop.crawler.service.CrawlerProxyServiceDb;
import com.axisdesktop.crawler.service.ProviderService;
import com.axisdesktop.crawler.service.ProviderServiceDb;
import com.axisdesktop.crawler.service.ProviderUrlService;
import com.axisdesktop.crawler.service.ProviderUrlServiceDb;

public class AxisCrawler extends Crawler {
	private Queue queue;
	// private AxisProducer prod;
	// private Session session;

	private final String propertiesFileName = "crawler.properties";

	public AxisCrawler() throws IOException {
		this.loadProperties( this.propertiesFileName );
		SessionFactory factory = this.buildSessionFactory( this.getProperties() );

		ProviderService provService = new ProviderServiceDb( factory );
		ProviderUrlService urlService = new ProviderUrlServiceDb( factory );
		CrawlerProxyService crawlerService = new CrawlerProxyServiceDb( factory );

		Provider prov = provService.createIfNotExists( "axisdesktop.com", ProviderStatus.ACTIVE );

		for( String u : importFeedUrls( "feed.urls" ) ) {
			ProviderUrl purl = new ProviderUrl( prov, u, ProviderDataType.FEED, ProviderUrlStatus.PENDING );
			urlService.createIfNotExists( purl );
		}

		for( CrawlerProxy p : importProxy( "proxy.list" ) ) {
			crawlerService.createIfNotExists( p );
		}

		this.queue = new DbQueue( prov, factory );
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
		// this.getSessionFactory().close();

	}

	@Override
	protected Worker createWorker( URI uri ) {
		return new AxisWorker( this, uri );
	}

	@Override
	protected URI getNextURI() {
		URI u = this.queue.get();
		if( u != null ) System.out.println( "consumed: " + u.toString() );

		return u;
	}

	private List<String> importFeedUrls( String fname ) {
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

	private List<CrawlerProxy> importProxy( String fname ) {
		List<CrawlerProxy> l = new ArrayList<>();

		try( Stream<String> stream = Files.lines( Paths.get( ClassLoader.getSystemResource( fname ).toURI() ) ) ) {
			stream.forEach( line -> {
				if( line != null && !line.isEmpty() ) {
					String[] dd = line.split( ":" );
					CrawlerProxy p = new CrawlerProxy( dd[0], Integer.valueOf( dd[1] ) );
					l.add( p );
				}
			} );
		}
		catch( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch( URISyntaxException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l;
	}

	public Queue getQueue() {
		return this.queue;
	}

}
