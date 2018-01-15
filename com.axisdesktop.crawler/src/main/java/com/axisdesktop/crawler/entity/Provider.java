package com.axisdesktop.crawler.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "provider" )
public class Provider extends SimpleEntity<Integer> {

	@Column( name = "status_id" )
	private int statusId;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "status_id", insertable = false, updatable = false )
	private ProviderStatus status;

	@OneToMany( fetch = FetchType.LAZY, mappedBy = "providerId" )
	private Set<ProviderUrl> providerUrl;

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId( int status_id ) {
		this.statusId = status_id;
	}

	public ProviderStatus getStatus() {
		return status;
	}

	public void setStatus( ProviderStatus status ) {
		this.status = status;
	}

	public Set<ProviderUrl> getProviderFeedUri() {
		return providerUrl;
	}

	public void setProviderFeedUri( Set<ProviderUrl> providerUrl ) {
		this.providerUrl = providerUrl;
	}

	public Set<ProviderUrl> getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl( Set<ProviderUrl> providerUrl ) {
		this.providerUrl = providerUrl;
	}

	@Override
	public String toString() {
		return "Provider [" + super.toString() + ", status_id=" + statusId + "]";
	}

}
