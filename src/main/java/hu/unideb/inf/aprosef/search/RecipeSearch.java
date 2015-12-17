package hu.unideb.inf.aprosef.search;

import java.io.IOException;
import java.net.URLEncoder;

import javax.xml.bind.JAXBException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import hu.unideb.inf.aprosef.model.Recipe;
import hu.unideb.inf.aprosef.model.SearchResults;
import hu.unideb.inf.aprosef.parser.RecipeParser;
import hu.unideb.inf.aprosef.parser.SearchResultsParser;
import hu.unideb.inf.jaxb.JAXBUtil;


public class RecipeSearch  extends SearchResultsParser{
	private static final String SEARCH_URI = "http://aprosef.hu/kereses/minden";

	public RecipeSearch() {
		super(1);
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public Recipe doSearch(String name) throws IOException {
		
		String searchUri = SEARCH_URI + "/" + URLEncoder.encode(name,"UTF-8") + "/0";
		
		Document doc = Jsoup.connect(searchUri).userAgent("Mozilla").get();
		SearchResults results = parse(doc);
		if (results.getItems().size() == 0)
			return null;
		String uri = results.getItems().get(0).getUri();
		return new RecipeParser().parse("http://aprosef.hu"+uri);
	}
	
	public static void main(String[] args) {
		try {
			Recipe recipe = (new RecipeSearch()).doSearch("Zöldborsófőzelék");
			if(recipe == null)
				System.out.println("Recipe is not found!");
			else
			JAXBUtil.toXML(recipe, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
