package com.axisdesktop.crawler.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.entity.CrawlerProxy;
import com.axisdesktop.crawler.entity.CrawlerProxyStatus;

public class CrawlerProxyServiceDb extends CrawlerProxyService {
	private SessionFactory factory;

	public CrawlerProxyServiceDb( SessionFactory factory ) {
		this.factory = factory;
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory( SessionFactory factory ) {
		this.factory = factory;
	}

	@Override
	public CrawlerProxy create( CrawlerProxy proxy ) {
		Session ses = getFactory().openSession();
		Transaction tx = ses.beginTransaction();

		ses.persist( proxy );

		tx.commit();
		ses.close();

		return proxy;
	}

	@Override
	public CrawlerProxy load( Integer id ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update( CrawlerProxy obj ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete( CrawlerProxy obj ) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CrawlerProxy> findByProperty( String prop, String val ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CrawlerProxy getByProperty( String prop, String val ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExist( Integer id ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExist( CrawlerProxy obj ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CrawlerProxy createIfNotExists( String host, int port ) {
		if( host == null ) {
			throw new IllegalArgumentException( "host is null" );
		}

		if( port == 0 ) {
			throw new IllegalArgumentException( "port is empty" );
		}

		CrawlerProxy p = null;

		try {
			Session ses = this.getFactory().openSession();

			Query<CrawlerProxy> query = ses.getNamedQuery( "CrawlerProxy.getByHostAndPort" );
			query.setParameter( "host", host );
			query.setParameter( "port", port );
			p = query.getSingleResult();
			// if( pu != null ) pu.getProvider().getName();

			ses.close();
		}
		catch( Exception e ) {} ;

		if( p == null ) {
			p = create( new CrawlerProxy( host, port ) );
		}

		return p;
	}

	@Override
	public CrawlerProxy createIfNotExists( CrawlerProxy proxy ) {
		return createIfNotExists( proxy.getHost(), proxy.getPort() );
	}

	@Override
	public CrawlerProxy getActive() {
		CrawlerProxy crawlerProxy = null;

		try {
			Session ses = this.getFactory().openSession();

			Query<CrawlerProxy> query = ses.getNamedQuery( "CrawlerProxy.getActive" );
			query.setMaxResults( 1 );

			while( ( crawlerProxy = query.getSingleResult() ) != null ) {
				if( !checkConnection( crawlerProxy ) ) {
					Transaction tx = ses.beginTransaction();
					crawlerProxy.setTries( crawlerProxy.getTries() + 1 );
					ses.persist( crawlerProxy );
					tx.commit();

					crawlerProxy = null;
					continue;
				}

				break;
			}

			ses.close();
		}
		catch( Exception e ) {
			crawlerProxy = null;
		} ;

		return crawlerProxy;
	}

	public boolean checkConnection( CrawlerProxy crawlerProxy ) {
		boolean res = false;
		HttpURLConnection conn = null;

		try {
			Proxy proxy = new Proxy( Proxy.Type.HTTP,
					new InetSocketAddress( crawlerProxy.getHost(), crawlerProxy.getPort() ) );

			conn = CrawlerProxyService.getConnection( proxy, new URL( "https://google.com" ), null, null, null );
			conn.getInputStream().close();

			res = true;
		}
		catch( IOException e ) {
			crawlerProxy.setLog( e.getMessage() );
			crawlerProxy.setStatus( CrawlerProxyStatus.ERROR );

			System.err.println( "check proxy failed" );
			System.err.println( crawlerProxy );
		}
		finally {
			if( conn != null ) {
				conn.disconnect();
			}
		}

		return res;
	}

}
