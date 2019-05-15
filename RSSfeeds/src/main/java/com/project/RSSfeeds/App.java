package com.project.RSSfeeds;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class App {

	public static ArrayList<String> checkFeed(HashMap<String, SyndFeed> feed, int days) {
		ArrayList<String> inactiveCompanies = new ArrayList<String>();

		// for each entry, check dates

		for (Map.Entry<String, SyndFeed> pair : feed.entrySet()) {
			if (pair.getValue().getPublishedDate().compareTo(Date.valueOf(LocalDate.now().minusDays(days))) > -1) {
				inactiveCompanies.add(pair.getKey());
			}
		}
		return inactiveCompanies;
	}

	public static void main(String[] args) throws Exception {
		// CNN feed
		String urlCNN = "http://rss.cnn.com/rss/cnn_topstories.rss";

		URL feedUrlCNN = new URL(urlCNN);
		SyndFeedInput inputCNN = new SyndFeedInput();
		SyndFeed feedCNN = inputCNN.build(new XmlReader(feedUrlCNN));

		// BBC feed
		String urlBBC = "http://feeds.bbci.co.uk/news/rss.xml?edition=us";

		URL feedUrlBBC = new URL(urlBBC);
		SyndFeedInput inputBBC = new SyndFeedInput();
		SyndFeed feedBBC = inputBBC.build(new XmlReader(feedUrlBBC));

		// build dictionary of company: feed
		HashMap<String, SyndFeed> companyFeeds = new HashMap<String, SyndFeed>();

		// add each company
		companyFeeds.put("CNN", feedCNN);
		companyFeeds.put("BBC", feedBBC);

		// check feeds
		int days = 5;
		ArrayList<String> myList = checkFeed(companyFeeds, days);
		if (!myList.isEmpty()) {
			for (String c : myList) {
				System.out.println(c);
			}
		} else {
			System.out.println("All companies active last " + days);
		}
	}
}