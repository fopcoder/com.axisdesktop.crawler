package com.axisdesktop.crawler.impl;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PropertiesApp {
	private static SessionFactory factory;

	public static void main( String[] args ) throws FileNotFoundException, IOException {

		try {
			factory = new Configuration().setProperty( "hibernate.connection.driver_class", "org.postgresql.Driver" )
					.buildSessionFactory();

			Session ses = factory.openSession();

		}
		catch( Throwable ex ) {
			System.err.println( "Failed to create sessionFactory object." + ex );
			throw new ExceptionInInitializerError( ex );
		}
		factory.close();
		// Properties p = new Properties();
		// // p.load( new FileInputStream( "crawler.properties" ) );
		//
		// URL url = ClassLoader.getSystemResource( "crawler.properties" );
		// p.load( url.openStream() );
		//
		// p.list( System.out );
		// System.out.println( p.getProperty( "db.name" ) );
		// // return props;

	}

}
