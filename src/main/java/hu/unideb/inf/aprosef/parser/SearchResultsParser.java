package hu.unideb.inf.aprosef.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.aprosef.model.Recipe;
import hu.unideb.inf.aprosef.model.SearchResultItem;
import hu.unideb.inf.aprosef.model.SearchResults;
import hu.unideb.inf.aprosef.model.Recipe.Author;
import hu.unideb.inf.aprosef.model.Recipe.Ingredient;
import hu.unideb.inf.aprosef.model.Recipe.Instruction;
import hu.unideb.inf.jaxb.JAXBUtil;

public class SearchResultsParser {

	private static Logger logger = LoggerFactory.getLogger(SearchResultsParser.class);
	
	public static final int MAX_ITEMS = 10;

	private int maxItems = MAX_ITEMS;

	private int nextPageNum = 0;

	public SearchResultsParser() {
	}

	public SearchResultsParser(int maxItems) {
		this.maxItems = maxItems;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	private Document getNextPage(Document doc) throws IOException {
		String nextPage = null;
		try {
			nextPage = doc.select("#next-bottom > a").get(0).attr("href");
			logger.info("Next page: {}", nextPage);
		} catch (Exception e) {
			logger.warn("Next page: there is no more page!");
		}
		return nextPage != null ? Jsoup.connect(nextPage).userAgent("Mozilla").get() : null;
	}

	private String getTotalItems(Document doc) throws IOException {
		String totalItems = null;
		try {
			if (!doc.select("div.content > div.view-counter").isEmpty()) {
				totalItems = doc.select("div.content > div.view-counter").first().text().trim();
			}
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		return totalItems;
	}

	private List<SearchResultItem> extractItems(Document doc) throws IOException {

		List<SearchResultItem> sriList = new ArrayList<SearchResultItem>();

		String totalItems = getTotalItems(doc);
		
		if (totalItems != null) {
			if (totalItems.contains("Sajnos nincs találat"))
				return null;
		}
		
		for (Element e : doc.select("div.views-row")) {
			SearchResultItem sri = new SearchResultItem();

			String image = null;

			sri.setImage(image);

			String title = null;
			sri.setTitle(title);

			String uri = null;
			sri.setUri(uri);

			String authorProfile = null;
			sri.setAuthorProfile(authorProfile);

			Integer views = null;
			sri.setViews(views);

			Integer comments = null;
			sri.setComments(comments);

			Integer pics = null;
			sri.setPics(pics);

			sriList.add(sri);

		}
		return sriList;

	}

	public static void main(String[] args) throws Exception {

		Document doc = Jsoup.connect("http://aprosef.hu/kereses/minden/borso/2").userAgent("Mozilla").get();

		SearchResults sr = new SearchResults();

		sr.setItems(new SearchResultsParser().extractItems(doc));

		JAXBUtil.toXML(sr, System.out);

	}

}
