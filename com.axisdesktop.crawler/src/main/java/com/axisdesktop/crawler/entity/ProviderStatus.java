package com.axisdesktop.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "provider_status" )
public class ProviderStatus extends SimpleEntity<Integer> {
	@Override
	public String toString() {
		return "ProviderStatus [" + super.toString() + " ]";
	}
}
