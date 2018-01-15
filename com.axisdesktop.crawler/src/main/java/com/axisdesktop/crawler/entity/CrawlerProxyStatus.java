package com.axisdesktop.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.SimpleEntity;

@Entity
@Table( name = "proxy_status", schema = "crawler" )

public class CrawlerProxyStatus extends SimpleEntity<Integer> {
	@Override
	public String toString() {
		return "CrawlerProxyStatus [ " + super.toString() + " ]";
	}
}
