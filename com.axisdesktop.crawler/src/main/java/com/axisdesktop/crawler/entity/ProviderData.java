package com.axisdesktop.crawler.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.axisdesktop.base.db.entity.BaseEntity;
import com.axisdesktop.base.db.usertype.HstoreUserType;

@Entity
@Table( name = "provider_data" )
@TypeDef( name = "hstore", typeClass = HstoreUserType.class )
@NamedQueries( {
		@NamedQuery( name = "ProviderData.getIdByUrlIdAndTypeId", query = "SELECT d.id FROM ProviderData d WHERE urlId = :urlId AND typeId = :typeId" ),
		@NamedQuery( name = "ProviderData.clearCommentsByParentId", query = "DELETE FROM ProviderData WHERE parentId = :parentId" ),
		@NamedQuery( name = "ProviderData.findCommentsByParentId", query = "SELECT d FROM ProviderData d WHERE parentId = :parentId AND typeId = 7" ) } )

public class ProviderData extends BaseEntity<Long> {
	@Column( name = "url_id" )
	private long urlId;

	@Column( name = "provider_id" )
	private int providerId;

	@Column( name = "category_id" )
	private int categoryId;

	@Column( name = "language_id" )
	private String languageId;

	@Column( name = "parent_id" )
	private Long parentId;

	@Column( name = "type_id" )
	private int typeId;

	@Type( type = "hstore" )
	private Map<String, String> data = new HashMap<>();

	public ProviderData() {
	}

	public ProviderData( int providerId, long urlId, int typeId, String languageId ) {
		this.urlId = urlId;
		this.providerId = providerId;
		this.languageId = languageId;
		this.typeId = typeId;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData( Map<String, String> data ) {
		this.data = data;
	}

	public long getUrlId() {
		return urlId;
	}

	public void setUrlId( long urlId ) {
		this.urlId = urlId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId( int categoryId ) {
		this.categoryId = categoryId;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId( String languageId ) {
		this.languageId = languageId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId( Long parentId ) {
		this.parentId = parentId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId( int typeId ) {
		this.typeId = typeId;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId( int providerId ) {
		this.providerId = providerId;
	}

	@Override
	public String toString() {
		return "ProviderUrl [" + super.toString() + ", urlId=" + urlId + ", providerId=" + providerId + ", categoryId="
				+ categoryId + ", languageId=" + languageId + ", parentId=" + parentId + ", typeId=" + typeId
				+ ", data=" + data + "]";
	}

}
