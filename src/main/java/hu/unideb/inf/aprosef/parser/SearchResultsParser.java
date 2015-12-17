package hu.unideb.inf.aprosef.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.aprosef.model.SearchResultItem;
import hu.unideb.inf.aprosef.model.SearchResults;
import hu.unideb.inf.jaxb.JAXBUtil;

public class SearchResultsParser {

	private static Logger logger = LoggerFactory.getLogger(SearchResultsParser.class);

	public static final int MAX_ITEMS = 67;

	private int maxItems = MAX_ITEMS;

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

	/**
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	private Document getNextPage(Document doc) throws IOException {
		String nextPage = null;
		String currentPage = doc.baseUri();
		int index = 0;
		for (int i = 0; i < 6; i++)
			index = currentPage.indexOf("/", index + 1);

		int nextPageNumber = Integer.parseInt(currentPage.substring(index + 1)) + 1;

		try {
			nextPage = currentPage.substring(0, index).concat("/" + String.valueOf(nextPageNumber));
			String totalItems = getTotalItems(Jsoup.connect(nextPage).userAgent("Mozilla").get());

			if (totalItems != null) {
				if (totalItems.contains("Sajnos nincs találat")) {
					nextPage = null;
					logger.warn("Next page: there is no more page!");
					return null;
				}
			}
			logger.info("Next page: {}", nextPage);
		} catch (Exception e) {
			nextPage = null;
			logger.warn("Next page: there is no more page!");
		}
		return nextPage != null ? Jsoup.connect(nextPage).userAgent("Mozilla").get() : null;
	}

	/**
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	private int getTotalItemsNumber(Document doc) throws IOException {
		List<SearchResultItem> items = new LinkedList<SearchResultItem>();

		while (doc != null) {
			for (SearchResultItem item : extractItems(doc)) {

				items.add(item);
			}
			doc = getNextPage(doc);
		}
		return items.size();
	}

	/**
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	private List<SearchResultItem> extractItems(Document doc) throws IOException {

		List<SearchResultItem> sriList = new ArrayList<SearchResultItem>();

		String totalItems = getTotalItems(doc);

		if (totalItems != null) {
			if (totalItems.contains("Sajnos nincs találat"))
				return null;
		}

		for (Element element : doc.select("div.views-row").not(".clearfix")) {
			SearchResultItem sri = new SearchResultItem();

			String image = null;
			try {
				image = element.select("div.views-field.views-field-field-mainphotos img").attr("src").trim();
				logger.info("Image: {}", image);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setImage(image);

			String title = null;
			try {
				title = element.select("div.views-field.views-field-title a").text().trim();
				logger.info("Title: {}", title);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setTitle(title);

			String uri = null;
			try {
				uri = element.select("div.views-field.views-field-field-mainphotos a").attr("href").trim();
				logger.info("URI: {}", uri);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setUri(uri);

			String authorProfile = null;
			try {
				authorProfile = element.select("div.views-field.views-field-picture a").attr("href").trim();
				logger.info("Author Profile: {}", authorProfile);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setAuthorProfile(authorProfile);

			Integer views = null;
			try {
				if (!element.select("div.views-field.views-field-count span").text().isEmpty())
					views = Integer.parseInt(element.select("div.views-field.views-field-count span").text().trim());

				logger.info("Views: {}", views);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setViews(views);

			Integer comments = null;
			try {
				if (!element.select("div.views-field.views-field-field-counted-commentnumber div.comments").text()
						.isEmpty())
					comments = Integer.parseInt(
							element.select("div.views-field.views-field-field-counted-commentnumber div.comments")
									.text().trim());

				logger.info("Comments: {}", comments);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setComments(comments);

			Integer pics = null;
			try {

				if (!element.select("div.views-field.views-field-field-counted-commentnumber div.pictures").text()
						.isEmpty())
					pics = Integer.parseInt(
							element.select("div.views-field.views-field-field-counted-commentnumber div.pictures")
									.text().trim());

				logger.info("Pics: {}", pics);
			} catch (Exception e) {
				throw new IOException("Malformed document");
			}
			sri.setPics(pics);

			sriList.add(sri);

		}
		return sriList;

	}

	/**
	 * 
	 * @param doc
	 * @return
	 * @throws IOException
	 */
	public SearchResults parse(Document doc) throws IOException {
		List<SearchResultItem> items = new LinkedList<SearchResultItem>();

		int totalItems = getTotalItemsNumber(doc);
		logger.info("Total number of items: {}", totalItems);
		loop: while (totalItems != 0 && doc != null) {
			for (SearchResultItem item : extractItems(doc)) {
				if (0 <= maxItems && maxItems <= items.size()) {
					break loop;
				}
				items.add(item);
			}
			if (0 <= maxItems && maxItems <= items.size())
				break;
			doc = getNextPage(doc);
		}
		
		return new SearchResults(totalItems,items);
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Document doc = Jsoup.connect("http://aprosef.hu/kereses/minden/borsó/0").userAgent("Mozilla").get();

		JAXBUtil.toXML(new SearchResultsParser().parse(doc), System.out);

	}

}
