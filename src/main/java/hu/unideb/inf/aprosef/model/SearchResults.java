package hu.unideb.inf.aprosef.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResults {
	
	@XmlElement(required=false)
	private List<SearchResultItem> items = new ArrayList<SearchResultItem>();

	public SearchResults() {}
	
	public SearchResults(List<SearchResultItem> items) {
		super();
		this.items = items;
	}

	public List<SearchResultItem> getItems() {
		return items;
	}

	public void setItems(List<SearchResultItem> items) {
		this.items = items;
	}
	
	
	
}
