package hu.unideb.inf.aprosef.search;

import java.io.IOException;
import java.net.URLEncoder;

import javax.xml.bind.JAXBException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import hu.unideb.inf.aprosef.model.SearchResults;
import hu.unideb.inf.aprosef.parser.SearchResultsParser;
import hu.unideb.inf.jaxb.JAXBUtil;

public class SimpleSearch extends SearchResultsParser {

	private static final String SEARCH_URI = "http://aprosef.hu/kereses/minden";

	public SimpleSearch() {
		// TODO Auto-generated constructor stub
	}

	public SimpleSearch(int maxItem) {
		super(maxItem);
	}

	/**
	 * 
	 * @param searchTerm
	 * @return
	 * @throws IOException
	 */
	public SearchResults doSearch(String searchTerm) throws IOException {
		String uri = SEARCH_URI + "/" + URLEncoder.encode(searchTerm,"UTF-8") + "/0";
		Document doc = Jsoup.connect(uri).userAgent("Mozilla").get();
		return parse(doc);
	}
	
	public static void main(String[] args) {
		try {
			SearchResults results = (new SimpleSearch(1)).doSearch("borsó");
			JAXBUtil.toXML(results, System.out);
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
