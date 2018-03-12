package com.axisdesktop.crawler.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.axisdesktop.crawler.entity.Provider;
import com.axisdesktop.crawler.entity.ProviderStatus;

public class ProviderServiceDb extends ProviderService {
	private SessionFactory factory;

	public ProviderServiceDb( SessionFactory factory ) {
		this.factory = factory;
	}

	@Override
	public Provider create( Provider p ) {
		Session ses = getFactory().openSession();
		Transaction tx = ses.beginTransaction();

		ses.persist( p );

		tx.commit();
		ses.close();

		return p;
	}

	@Override
	public Provider load( Integer id ) {
		Provider p = null;

		Session ses = getFactory().openSession();
		p = ses.load( Provider.class, id );
		ses.close();

		return p;
	}

	@Override
	public void update( Provider obj ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete( Provider obj ) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Provider> findByProperty( String prop, String val ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider getByProperty( String prop, String val ) {
		Provider p = null;

		if( prop.equals( "name" ) ) {
			Session ses = this.getFactory().openSession();

			try {
				Query<Provider> query = ses.getNamedQuery( "Provider.getByName" );
				query.setParameter( "name", val );
				p = query.getSingleResult();
			}
			catch( Exception e ) {} ;

			ses.close();
		}

		return p;
	}

	@Override
	public boolean isExist( Integer id ) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExist( Provider obj ) {
		// TODO Auto-generated method stub
		return false;
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory( SessionFactory factory ) {
		this.factory = factory;
	}

	@Override
	public Provider createIfNotExists( String name ) {
		return createIfNotExists( name, ProviderStatus.PENDING );
	}

	@Override
	public Provider createIfNotExists( Provider prov ) {
		if( prov == null ) {
			throw new IllegalArgumentException( "prov is empty" );
		}

		return createIfNotExists( prov.getName(), prov.getStatusId() );
	}

	@Override
	public Provider createIfNotExists( String name, ProviderStatus status ) {
		if( name == null || name.isEmpty() ) {
			throw new IllegalArgumentException( "name is empty" );
		}

		if( status == null ) {
			throw new IllegalArgumentException( "status is empty" );
		}

		Provider p = getByProperty( "name", name );

		if( p == null ) {
			p = create( new Provider( name, status ) );
		}

		return p;
	}

}
