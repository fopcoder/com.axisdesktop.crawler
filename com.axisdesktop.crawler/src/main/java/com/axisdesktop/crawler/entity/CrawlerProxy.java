package com.axisdesktop.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.axisdesktop.base.db.entity.BaseEntity;

@Entity
@Table( name = "proxy", uniqueConstraints = {
		@UniqueConstraint( columnNames = { "host", "port" }, name = "uk_proxy_host_port" )//
} )

@NamedQueries( {
		@NamedQuery( name = "CrawlerProxy.getByHostAndPort", query = "SELECT p FROM CrawlerProxy p WHERE host = :host AND port = :port" )//
} )

public class CrawlerProxy extends BaseEntity<Integer> {
	@Column( nullable = false )
	private String host;

	@Column( nullable = false )
	private int port;

	@Column( name = "`user`" )
	private String user;

	private String password;
	private String log;
	private int tries;

	@Column( name = "status_id" )
	private CrawlerProxyStatus status;

	public CrawlerProxy() {
	}

	public CrawlerProxy( String host, int port ) {
		this.host = host;
		this.port = port;
		this.status = CrawlerProxyStatus.ACTIVE;
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

	public void setStatusId( CrawlerProxyStatus statusId ) {
		this.status = statusId;
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
				+ ", password=" + password + ", log=" + log + ", status_id=" + status.getName() + ", tries=" + tries
				+ "]";
	}
}
