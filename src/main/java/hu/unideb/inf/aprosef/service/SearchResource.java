package hu.unideb.inf.aprosef.service;

import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import hu.unideb.inf.aprosef.model.SearchResults;
import hu.unideb.inf.aprosef.search.SimpleSearch;

public class SearchResource extends ServerResource {

	@Get("xml")
	public SearchResults represent() {
		String searchTerm = getQueryValue("searchTerm");

		int maxItem = 0;
		if (getQueryValue("maxItem") != null)
			maxItem = Integer.parseInt(getQueryValue("maxItem"));

		if (searchTerm == null) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Required parameter 'searchTerm' is missing");
		}

		try {
			if (maxItem > 0) {
				return new SimpleSearch(maxItem).doSearch(searchTerm);
			} else {
				return new SimpleSearch().doSearch(searchTerm);
			}
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}
}
