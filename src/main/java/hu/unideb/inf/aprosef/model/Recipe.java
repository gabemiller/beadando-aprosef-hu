package hu.unideb.inf.aprosef.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import hu.unideb.inf.jaxb.JAXBUtil;

@javax.xml.bind.annotation.XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Recipe {
	
	@XmlAttribute(required=true)
	private String image;
	
	@XmlElement(required=true)
	private String name;
	
	@XmlElement(name="author",required=true)
	@XmlElementWrapper(name="authors")
	private List<Author> authors = new ArrayList<Author>();
	
	@XmlElement(required=true)
	private Integer yield;
	
	@XmlElement(name="ingredient",required=true)
	@XmlElementWrapper(name="ingredients")
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	@XmlElement(required=true)
	private Integer totalTime;
	
	@XmlElement(required=true)
	private String description;
	
	@XmlElement(required=true)
	private String instructions;
	
	public Recipe(){
	
	}
	
	public Recipe(String image, String name, List<Author> authors, Integer yield, List<Ingredient> ingredients,
			Integer totalTime, String description, String instructions) {
		super();
		this.image = image;
		this.name = name;
		this.authors = authors;
		this.yield = yield;
		this.ingredients = ingredients;
		this.totalTime = totalTime;
		this.description = description;
		this.instructions = instructions;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public Integer getYield() {
		return yield;
	}

	public void setYield(Integer yield) {
		this.yield = yield;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Author {

		@XmlElement(required=true)
		private String name;
		
		@XmlAttribute(required=true)
		private String profile;

		public Author() {}
		
		public Author(String name, String profile) {
			super();
			this.name = name;
			this.profile = profile;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getProfile() {
			return profile;
		}

		public void setProfile(String profile) {
			this.profile = profile;
		}
		
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Ingredient {

		@XmlElement(required=true)
		private Double amount;
		
		@XmlElement(required=true)
		private String unit;
		
		@XmlElement(required=true)
		private String ingredient;
		
		public Ingredient() {}
		
		public Ingredient(Double amount, String unit, String ingredient) {
			super();
			this.amount = amount;
			this.unit = unit;
			this.ingredient = ingredient;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		public String getIngredient() {
			return ingredient;
		}

		public void setIngredient(String ingredient) {
			this.ingredient = ingredient;
		}	
		
	}
	
	public static void main(String[] args) throws Exception {
		
		Recipe recipe = new Recipe();
		
		recipe.setImage("http://aprosef.hu/sites/default/files/styles/mainphoto_870x468/public/mainphotos/2014/habkarika13.jpg?itok=0-5dB9Wq");
		recipe.setName("Karácsonyi habkarika recept");
		recipe.setTotalTime(160);
		recipe.setYield(13);
		
		Author auth = new Author();
		
		auth.setName("aprosef");
		auth.setProfile("aprosef.hu");
		
		List<Author> authList = new ArrayList<Recipe.Author>();
		
		authList.add(auth);
		
		recipe.setAuthors(authList);
		
		Ingredient in = new Ingredient();
		
		in.setAmount(10.0);
		in.setUnit("dkg");
		in.setIngredient("cukor");
		
		List<Ingredient> inList = new ArrayList<Recipe.Ingredient>();
		
		inList.add(in);
		
		recipe.setIngredients(inList);
		
		JAXBUtil.toXML(recipe, System.out);
		
		
	}
	
	
}
