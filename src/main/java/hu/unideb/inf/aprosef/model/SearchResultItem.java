package hu.unideb.inf.aprosef.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResultItem {

	@XmlElement(required=true)
	private String image;
	
	@XmlElement(required=true)
	private String title;
	
	@XmlAttribute(required=true)
	private String uri;
	
	@XmlElement(required=true)
	private String authorProfile;
	
	@XmlElement(required=true)
	private Integer views;
	
	@XmlElement(required=true)
	private Integer comments;
	
	@XmlElement(required=true)
	private Integer pics;

	public SearchResultItem() {}
	
	public SearchResultItem(String image, String title, String uri, String authorProfile, Integer views,
			Integer comments, Integer pics) {
		super();
		this.image = image;
		this.title = title;
		this.uri = uri;
		this.authorProfile = authorProfile;
		this.views = views;
		this.comments = comments;
		this.pics = pics;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAuthorProfile() {
		return authorProfile;
	}

	public void setAuthorProfile(String authorProfile) {
		this.authorProfile = authorProfile;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getPics() {
		return pics;
	}

	public void setPics(Integer pics) {
		this.pics = pics;
	}

	@Override
	public String toString() {
		return "SearchResultItem [image=" + image + ", title=" + title + ", uri=" + uri + ", authorProfile="
				+ authorProfile + ", views=" + views + ", comments=" + comments + ", pics=" + pics + "]";
	}
	
	
	
	
	
}
