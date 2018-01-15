package com.axisdesktop.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.axisdesktop.base.db.entity.BaseEntity;

@Entity
@Table( name = "proxy" )

public class CrawlerProxy extends BaseEntity<Integer> {
	private String host;
	private int port;

	@Column( name = "`user`" )
	private String user;

	private String password;
	private String log;
	private int tries;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "status_id", insertable = false, updatable = false )
	private CrawlerProxyStatus crawlerProxyStatus;

	@Column( name = "status_id" )
	private int statusId;

	public CrawlerProxy() {
	}

	public CrawlerProxy( String host, int port ) {
		this.host = host;
		this.port = port;
		this.statusId = 1;
	}

	public String getHost() {
		return host;
	}

	public void setHost( String host ) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort( int port ) {
		this.port = port;
	}

	public CrawlerProxyStatus getCrawlerProxyStatus() {
		return crawlerProxyStatus;
	}

	public void setCrawlerProxyStatus( CrawlerProxyStatus crawlerProxyStatus ) {
		this.crawlerProxyStatus = crawlerProxyStatus;
	}

	public String getUser() {
		return user;
	}

	public void setUser( String user ) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId( int status_id ) {
		this.statusId = status_id;
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

	@Override
	public String toString() {
		return "CrawlerProxy [" + super.toString() + ", host=" + host + ", port=" + port + ", user=" + user
				+ ", password=" + password + ", log=" + log + ", status_id=" + statusId + ", tries=" + tries + "]";
	}
}
