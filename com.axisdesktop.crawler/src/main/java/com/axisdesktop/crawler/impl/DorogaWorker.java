package com.axisdesktop.crawler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.axisdesktop.crawler.base.Crawler;
import com.axisdesktop.crawler.base.Worker;
import com.axisdesktop.crawler.entity.CrawlerProxy;
import com.axisdesktop.crawler.service.CrawlerProxyService;
import com.axisdesktop.crawler.service.CrawlerProxyServiceDb;

public class DorogaWorker implements Worker {
	private Crawler crawler;
	private URI uri;

	public DorogaWorker( Crawler crawler, URI uri ) {
		if( crawler == null ) throw new IllegalArgumentException( "Carwler is null" );
		if( uri == null ) throw new IllegalArgumentException( "URI is null" );

		this.crawler = crawler;
		this.uri = uri;
	}

	@Override
	public void run() {
		System.out.println( "thread " + this.uri.toString() );

		Session ses = this.crawler.getSessionFactory().openSession();
		Transaction tx = ses.beginTransaction();

		CrawlerProxyService crawlerService = new CrawlerProxyServiceDb( this.getCrawler().getSessionFactory() );
		CrawlerProxy crawlerProxy = crawlerService.getActive();
		// System.out.println( 55555555 );
		// Query<CrawlerProxy> query = ses.getNamedQuery( "CrawlerProxy.getActive" );
		// query.setMaxResults( 1 );
		// CrawlerProxy crawlerProxy = query.getSingleResult();
		// System.out.println( 111111111 );
		// System.out.println( crawlerProxy );

		if( crawlerProxy != null ) {
			System.out
					.println( String.format( "proxy found: [%d] %s", crawlerProxy.getId(), crawlerProxy.hostport() ) );

			try {
				Proxy proxy = new Proxy( Proxy.Type.HTTP,
						new InetSocketAddress( crawlerProxy.getHost(), crawlerProxy.getPort() ) );

				URL url = this.uri.toURL();
				HttpURLConnection conn = CrawlerProxyService.getConnection( proxy, url, null, null, null );

				InputStream is = conn.getInputStream();

				String text = null;

				try( BufferedReader br = new BufferedReader( new InputStreamReader( is ) ) ) {
					StringBuilder sb = new StringBuilder();

					String str;
					while( ( str = br.readLine() ) != null ) {
						sb.append( str );
					}

					text = sb.toString();

				}
				catch( IOException e ) { /* ignore */ }

				System.out.println( text );

				// URLConnection connection = null;
				// connection = url.openConnection( proxy );
				// else connection = url.openConnection();

				// connection.setRequestProperty( "User-Agent", "user kokoko" );
				// InputStream is = connection.getInputStream();
				// HttpURLConnection conn = (HttpURLConnection)url.openConnection( proxy );

			}
			catch( IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		tx.commit();
		ses.close();

		//
		// String sql = "SELECT * FROM {h-schema}provider";
		// // SQLQuery query = ses.createSQLQuery( sql );
		// Query query = ses.createNativeQuery( sql );
		// List results = query.getResultList();
		//
		// System.out.println( results );
		//
		// // query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP );
		// // List results = query.list();
		//
		// tx.commit();
		// ses.close();
		//
		// // try {
		// // Thread.sleep( 1000 );
		// // }
		// // catch( InterruptedException e ) {
		// // // TODO Auto-generated catch block
		// // e.printStackTrace();
		// // }
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
