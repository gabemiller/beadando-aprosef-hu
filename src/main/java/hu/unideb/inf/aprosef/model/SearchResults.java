package hu.unideb.inf.aprosef.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResults {
	
	@XmlAttribute(required = true)
	private int	itemsTotal;

	@XmlElement(name = "item", required = false)
	private List<SearchResultItem>	items;

	public SearchResults() {
	}

	public SearchResults(int itemsTotal, List<SearchResultItem> items) {
		this.itemsTotal = itemsTotal;
		this.items = items;
	}

	public int getItemsTotal() {
		return itemsTotal;
	}

	public void setItemsTotal(int itemsTotal) {
		this.itemsTotal = itemsTotal;
	}

	public List<SearchResultItem> getItems() {
		return items;
	}

	public void setItems(List<SearchResultItem> items) {
		this.items = items;
	}
	
	
	
}
