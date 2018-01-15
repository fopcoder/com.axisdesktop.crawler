package com.axisdesktop.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "provider_data_type" )

public class ProviderDataType extends SimpleEntity<Integer> {
	@Override
	public String toString() {
		return "ProviderDataType [" + super.toString() + " ]";
	}
}
