package com.axisdesktop.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "provider_status" )
public class ProviderStatusOld extends SimpleEntity<Integer> {
	@Override
	public String toString() {
		return "ProviderStatusOld [" + super.toString() + " ]";
	}
}
