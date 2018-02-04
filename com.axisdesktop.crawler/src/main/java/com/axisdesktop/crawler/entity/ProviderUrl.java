package com.axisdesktop.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.axisdesktop.base.db.entity.BaseEntity;

@Entity
@Table( name = "provider_url", uniqueConstraints = {
		@UniqueConstraint( columnNames = { "provider_id", "url" }, name = "uk_provider_url_provider_url" ) //
} )
@NamedQueries( { @NamedQuery( name = "ProviderUrl.findActiveUrl", //
		query = "SELECT u FROM ProviderUrl u " + //
				"WHERE providerId = :providerId AND " + //
				"	typeId = ProviderDataType.FEED AND (" + //
				"		statusId IN( ProviderUrlStatus.PENDING, ProviderUrlStatus.UPDATE, ProviderUrlStatus.DONE ) OR "
				+ //
				"		( statusId = ProviderUrlStatus.ERROR AND tries < :maxTries )" + //
				"	) AND modified < :nextTimeFeed" + //
				"UNION " + //
				"SELECT u FROM ProviderUrl u" + //
				"WHERE providerId = :providerId AND " + //
				"	typeId <> ProviderDataType.FEED AND (" + //
				"		statusId IN( ProviderUrlStatus.PENDING, ProviderUrlStatus.UPDATE, ProviderUrlStatus.DONE ) OR "
				+ //
				"		( statusId = ProviderUrlStatus.ERROR AND tries < :maxTries ) " + //
				"	) AND modified < :nextTimeItem )" //
		), //
		@NamedQuery( name = "ProviderUrl.getByProviderAndUrl", query = "SELECT u FROM ProviderUrl u WHERE provider_id = :providerId AND url = :url" ) //
} )

public class ProviderUrl extends BaseEntity<Long> {
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "provider_id", nullable = false, foreignKey = @ForeignKey( name = "fk_providerurl_provider" ) )
	private Provider provider;

	@Column( name = "status_id", nullable = false )
	private ProviderUrlStatus statusId;

	@Column( name = "type_id", nullable = false )
	private ProviderDataType typeId;

	@Column( nullable = false, length = 4000 )
	private String url;

	private String log;
	private int tries;

	@Column( name = "parent_id" )
	private Long parentId;

	@Lob
	private String params;

	public ProviderUrl() {
	}

	public ProviderUrl( Provider provider, String url, ProviderDataType typeId ) {
		this( provider, url, typeId, ProviderUrlStatus.PENDING );
	}

	public ProviderUrl( Provider provider, String url, ProviderDataType typeId, ProviderUrlStatus statusId ) {
		this.provider = provider;
		this.statusId = statusId;
		this.typeId = typeId;
		this.url = url;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider( Provider provider ) {
		this.provider = provider;
	}

	public ProviderUrlStatus getStatusId() {
		return statusId;
	}

	public void setStatusId( ProviderUrlStatus statusId ) {
		this.statusId = statusId;
	}

	public ProviderDataType getTypeId() {
		return typeId;
	}

	public void setTypeId( ProviderDataType typeId ) {
		this.typeId = typeId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl( String url ) {
		this.url = url;
	}

	public String getLog() {
		return log;
	}

	public void setLog( String log ) {
		this.log = log;
	}

	public int getTries() {
		return tries;
	}

	public void setTries( int tries ) {
		this.tries = tries;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId( Long parentId ) {
		this.parentId = parentId;
	}

	public String getParams() {
		return params;
	}

	public void setParams( String params ) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ProviderUrl [" + super.toString() + ", provider=" + provider + ", statusId=" + statusId + ", typeId="
				+ typeId + ", url=" + url + ", log=" + log + ", tries=" + tries + ", parentId=" + parentId + ", params="
				+ params + "]";
	}

}
