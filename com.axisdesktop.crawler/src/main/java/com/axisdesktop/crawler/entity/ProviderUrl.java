package com.axisdesktop.crawler.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.BaseEntity;

@Entity
@Table( name = "provider_url" )
// @TypeDef( name = "hstore", typeClass = HstoreUserType.class )
@NamedQueries( { @NamedQuery( name = "ProviderUrl.findActiveFeedUrl", //
		query = "SELECT u FROM ProviderUrl u " //
				+ "WHERE providerId = :providerId  AND typeId = 1 AND ( " //
				+ "( statusId = 1 AND modified < :nextTime ) OR " //
				+ "statusId = 4 OR" //
				+ "( statusId = 3 AND tries < :maxTries AND modified < :waitFor ) ) ) " //
		) } )

public class ProviderUrl extends BaseEntity<Long> {
	@Column( name = "provider_id", nullable = false )
	private int providerId;

	@Column( name = "status_id", nullable = false )
	private ProviderUrlStatus statusId;

	@Column( name = "type_id", nullable = false )
	private ProviderDataType typeId;

	@Column( nullable = false )
	private String url;

	private String log;
	private int tries;

	@Column( name = "parent_id", nullable = false )
	private Long parentId;

	@Lob
	private String params;

	public ProviderUrl() {
	}

	public ProviderUrl( int providerId, String url, ProviderDataType typeId ) {
		this( providerId, url, typeId, ProviderUrlStatus.PENDING );
	}

	public ProviderUrl( int providerId, String url, ProviderDataType typeId, ProviderUrlStatus statusId ) {
		this.providerId = providerId;
		this.statusId = statusId;
		this.typeId = typeId;
		this.url = url;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId( int providerId ) {
		this.providerId = providerId;
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
		return "ProviderUrl [" + super.toString() + ", providerId=" + providerId + ", statusId=" + statusId
				+ ", typeId=" + typeId + ", url=" + url + ", log=" + log + ", tries=" + tries + ", parentId=" + parentId
				+ ", params=" + params + "]";
	}

}
