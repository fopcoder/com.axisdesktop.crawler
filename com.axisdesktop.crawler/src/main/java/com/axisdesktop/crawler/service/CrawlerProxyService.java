package com.axisdesktop.crawler.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.axisdesktop.crawler.entity.CrawlerProxy;

public abstract class CrawlerProxyService implements CrawlerService<CrawlerProxy, Integer> {
	abstract public CrawlerProxy createIfNotExists( String host, int port );

	abstract public CrawlerProxy createIfNotExists( CrawlerProxy proxy );

	abstract public CrawlerProxy getActive();

	public static HttpURLConnection getConnection( Proxy proxy, URL url, Map<String, String> params,
			Map<String, String> headers, Map<String, String> payload ) throws IOException {
		if( url == null ) throw new IllegalArgumentException( "url is null" );
		if( params == null ) params = new HashMap<>();

		HttpURLConnection conn = null;

		if( proxy == null ) {
			conn = (HttpURLConnection)url.openConnection();
		}
		else {
			conn = (HttpURLConnection)url.openConnection( proxy );
		}

		conn.setConnectTimeout( Integer.valueOf( params.getOrDefault( "timeout", "15000" ) ) );

		if( headers != null ) {
			for( Map.Entry<String, String> param : headers.entrySet() ) {
				conn.setRequestProperty( param.getKey(), param.getValue() );
			}
		}

		if( params.getOrDefault( "method", "get" ).equalsIgnoreCase( "post" ) ) {
			StringBuilder postData = new StringBuilder();

			for( Map.Entry<String, String> param : payload.entrySet() ) {
				if( postData.length() != 0 ) postData.append( "&" );

				postData.append( URLEncoder.encode( param.getKey(), "UTF-8" ) );
				postData.append( '=' );
				postData.append( URLEncoder.encode( param.getValue(), "UTF-8" ) );
			}

			byte[] postDataBytes = postData.toString().getBytes( "UTF-8" );

			conn.setRequestMethod( "POST" );
			conn.setDoOutput( true );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			conn.setRequestProperty( "Content-Length", String.valueOf( postDataBytes.length ) );
			conn.getOutputStream().write( postDataBytes );
		}
		else {

		}

		return conn;
	}
}
