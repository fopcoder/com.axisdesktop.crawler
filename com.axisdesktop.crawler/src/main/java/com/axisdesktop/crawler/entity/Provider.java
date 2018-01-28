package com.axisdesktop.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "provider", //
		uniqueConstraints = { @UniqueConstraint( columnNames = "name", name = "uk_provider_name" ) } //
)
@NamedQueries( { @NamedQuery( name = "Provider.getByName", query = "SELECT p FROM Provider p WHERE name = :name" ) } )
public class Provider extends SimpleEntity<Integer> {

	@Column( name = "status_id", nullable = false )
	private ProviderStatus statusId;

	// @OneToMany( fetch = FetchType.LAZY, mappedBy = "providerId" )
	// private Set<ProviderUrl> providerUrl;

	public Provider() {
	}

	public Provider( String name, ProviderStatus statusId ) {
		this.setName( name );
		this.statusId = statusId;
	}

	public ProviderStatus getStatusId() {
		return statusId;
	}

	public void setStatusId( ProviderStatus statusId ) {
		this.statusId = statusId;
	}

	// public Set<ProviderUrl> getProviderUrl() {
	// return providerUrl;
	// }
	//
	// public void setProviderUrl( Set<ProviderUrl> providerUrl ) {
	// this.providerUrl = providerUrl;
	// }

	@Override
	public String toString() {
		return "Provider [" + super.toString() + ", status_id=" + statusId + "]";
	}

}
