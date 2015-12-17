package hu.unideb.inf.aprosef.service;

import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import hu.unideb.inf.aprosef.model.Recipe;
import hu.unideb.inf.aprosef.search.RecipeSearch;

public class RecipeResource extends ServerResource {

	@Get("xml")
	public Recipe represent(){
		String	name = getAttribute("name");
		try {
			return new RecipeSearch().doSearch(name);
		} catch (IOException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL);
		}
	}
}
