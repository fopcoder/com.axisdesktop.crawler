package com.axisdesktop.crawler.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.h2.tools.Server;

import com.axisdesktop.crawler.base.Crawler;

public class AxisCrawlerApp {
	public static void main( String[] args ) throws IOException {
		Crawler crawler = new AxisCrawler();
		crawler.run();

		System.out.println( 111111 );

		try {
			Server server = Server.createWebServer( "-web" ).start();
		}
		catch( SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
