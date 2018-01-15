package com.axisdesktop.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "provider_url_status" )

public class ProviderUrlStatus extends SimpleEntity<Integer> {
	@Override
	public String toString() {
		return "ProviderUrlStatus [ " + super.toString() + " ]";
	}
}
