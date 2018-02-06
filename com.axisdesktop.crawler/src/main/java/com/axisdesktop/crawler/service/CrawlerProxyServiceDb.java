package com.axisdesktop.crawler.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.entity.CrawlerProxy;
import com.axisdesktop.crawler.entity.ProviderUrl;

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
	public CrawlerProxy create( CrawlerProxy obj ) {
		// TODO Auto-generated method stub
		return null;
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

}
