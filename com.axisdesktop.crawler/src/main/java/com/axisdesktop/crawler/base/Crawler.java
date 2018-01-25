package com.axisdesktop.crawler.base;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderDataType;
import com.axisdesktop.crawler.entity.ProviderStatus;
import com.axisdesktop.crawler.entity.ProviderUrl;
import com.axisdesktop.crawler.entity.ProviderUrlStatus;

public abstract class Crawler {
	private Properties properties;
	private SessionFactory factory;

	protected final SessionFactory buildSessionFactory( Properties props ) {
		this.factory = new Configuration() //
//				.setProperty( "hibernate.connection.driver_class", "org.postgresql.Driver" ) //
				.setProperty( "hibernate.dialect", props.getProperty( "db.dialect", "" ) ) //
				.setProperty( "hibernate.connection.url", props.getProperty( "db.url" ) ) //
				.setProperty( "hibernate.connection.username", props.getProperty( "db.user" ) ) //
				.setProperty( "hibernate.connection.password", props.getProperty( "db.password" ) ) //
				.setProperty( "hibernate.default_schema", props.getProperty( "db.schema", "" ) ) //
				.addAnnotatedClass(Provider.class) //
				.addAnnotatedClass(ProviderStatus.class) //
				.addAnnotatedClass(ProviderUrl.class) //
				.addAnnotatedClass(ProviderUrlStatus.class) //
				.addAnnotatedClass(ProviderDataType.class) //
				.buildSessionFactory();
		return this.factory;
	}

	public final SessionFactory getSessionFactory() {
		return this.factory;
	}

	protected final Properties loadProperties( String fn ) throws IOException {
		URL url = ClassLoader.getSystemResource( fn );

		this.properties = new Properties();
		this.properties.load( url.openStream() );

		return this.properties;
	}

	public final Properties getProperties() {
		return this.properties;
	}

	protected abstract Worker createWorker( URI uri );

	protected abstract URI shiftURI();

	protected void shutdown() {
		this.factory.close();
	}

	public void run() {
		ExecutorService exec = Executors
				.newFixedThreadPool( Integer.parseInt( this.properties.getProperty( "crawler.threads", "5" ) ) );

		URI uri;
		while( ( uri = this.shiftURI() ) != null ) {
			Worker worker = this.createWorker( uri );
			exec.execute( worker );
		}

		exec.shutdown();

		this.shutdown();
	}

}
