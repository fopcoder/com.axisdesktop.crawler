package com.axisdesktop.crawler.impl;

import java.io.IOException;

import com.axisdesktop.crawler.base.Crawler;

public class AxisCrawlerApp {
	public static void main( String[] args ) throws IOException {
		Crawler crawler = new AxisCrawler();
		crawler.run();
	}
}
