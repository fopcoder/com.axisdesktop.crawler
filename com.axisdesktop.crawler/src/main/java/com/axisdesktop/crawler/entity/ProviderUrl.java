package com.axisdesktop.crawler.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.axisdesktop.base.db.entity.BaseEntity;
import com.axisdesktop.base.db.usertype.HstoreUserType;

@Entity
@Table( name = "provider_url" )
@TypeDef( name = "hstore", typeClass = HstoreUserType.class )
@NamedQueries( { @NamedQuery( name = "ProviderUrl.findActiveFeedUrl", //
		query = "SELECT u FROM ProviderUrl u " //
				+ "WHERE providerId = :providerId  AND typeId = 1 AND ( " //
				+ "( statusId = 1 AND modified < :nextTime ) OR " //
				+ "statusId = 4 OR" //
				+ "( statusId = 3 AND tries < :maxTries AND modified < :waitFor ) ) ) " //
		) } )

public class ProviderUrl extends BaseEntity<Long> {

	@Column( name = "provider_id" )
	private int providerId;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "status_id", insertable = false, updatable = false )
	private ProviderUrlStatus status;

	@Column( name = "status_id" )
	private int statusId;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "type_id", insertable = false, updatable = false )
	private ProviderDataType type;

	@Column( name = "type_id" )
	private int typeId;

	private String url;
	private String log;
	private int tries;

	@Column( name = "parent_id" )
	private Long parentId;

	@Type( type = "hstore" )
	private Map<String, Object> params = new HashMap<>();

	public ProviderUrl() {
	}

	public ProviderUrl( int providerId, String url, int typeId, int statusId ) {
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

	public ProviderUrlStatus getStatus() {
		return status;
	}

	public void setStatus( ProviderUrlStatus status ) {
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId( int statusId ) {
		this.statusId = statusId;
	}

	public ProviderDataType getType() {
		return type;
	}

	public void setType( ProviderDataType type ) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId( int typeId ) {
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

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams( Map<String, Object> params ) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "ProviderUrl [" + super.toString() + ", providerId=" + providerId + ", statusId=" + statusId
				+ ", typeId=" + typeId + ", url=" + url + ", log=" + log + ", tries=" + tries + ", parentId=" + parentId
				+ ", params=" + params + "]";
	}

}
