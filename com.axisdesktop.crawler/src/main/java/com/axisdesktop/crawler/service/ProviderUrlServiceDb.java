package com.axisdesktop.crawler.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.entity.ProviderUrl;

public class ProviderUrlServiceDb extends ProviderUrlService {
	private SessionFactory factory;

	public ProviderUrlServiceDb( SessionFactory factory ) {
		this.factory = factory;
	}

	@Override
	public ProviderUrl create( ProviderUrl u ) {
		Session ses = getFactory().openSession();
		Transaction tx = ses.beginTransaction();

		ses.persist( u );

		tx.commit();
		ses.close();

		return u;
	}

	@Override
	public ProviderUrl load( Long id ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update( ProviderUrl obj ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete( ProviderUrl obj ) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProviderUrl> findByProperty( String prop, String val ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProviderUrl getByProperty( String prop, String val ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExist( Long id ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExist( ProviderUrl pu ) {

		return false;
	}

	@Override
	public ProviderUrl createIfNotExists( ProviderUrl purl ) {
		if( purl == null ) {
			throw new IllegalArgumentException( "name is null" );
		}

		if( purl.getProvider() == null ) {
			throw new IllegalArgumentException( "provider is empty" );
		}

		if( purl.getUrl() == null ) {
			throw new IllegalArgumentException( "url is empty" );
		}

		ProviderUrl pu = null;

		try {
			Session ses = this.getFactory().openSession();

			Query<ProviderUrl> query = ses.getNamedQuery( "ProviderUrl.getByProviderAndUrl" );
			query.setParameter( "providerId", purl.getProvider().getId() );
			query.setParameter( "url", purl.getUrl() );
			pu = query.getSingleResult();
			// if( pu != null ) pu.getProvider().getName();

			ses.close();
		}
		catch( Exception e ) {} ;

		if( pu == null ) {
			create( purl );
		}
		else {
			purl = pu;
		}

		return purl;
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory( SessionFactory factory ) {
		this.factory = factory;
	}

}
