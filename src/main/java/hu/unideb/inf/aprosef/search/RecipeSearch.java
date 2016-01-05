package hu.unideb.inf.aprosef.search;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import hu.unideb.inf.aprosef.model.Recipe;
import hu.unideb.inf.aprosef.parser.RecipeParser;
import hu.unideb.inf.jaxb.JAXBUtil;

public class RecipeSearch extends RecipeParser {
	private static final String SEARCH_URI = "http://aprosef.hu";

	public RecipeSearch() {
	}


	/**
	 * 
	 * @param URI
	 * @return
	 * @throws IOException
	 */
	public Recipe doSearchByUri(String URI) throws IOException {

		
		String searchUri = SEARCH_URI + "/" + URI;

		return parse(searchUri);

	}

	public static void main(String[] args) {
		try {
			Recipe recipe = (new RecipeSearch()).doSearchByUri("bolognai_spagetti");
			if (recipe == null)
				System.out.println("Recipe is not found!");
			else
				JAXBUtil.toXML(recipe, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
