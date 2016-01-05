package hu.unideb.inf.aprosef.model;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hu.unideb.inf.jaxb.JAXBUtil;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Recipe {

	@XmlAttribute(required = true)
	private String image;

	@XmlElement(required = true)
	private String title;

	@XmlElement(required = true)
	private Author author = new Author();

	@XmlElement(required = true)
	private Integer yield;

	@XmlElement(name = "ingredient", required = true)
	@XmlElementWrapper(name = "ingredients")
	private List<Ingredient> ingredients;

	@XmlElement(required = true)
	private Integer totalTime;

	@XmlElement(required = true)
	private String description;

	@XmlElement(name = "step", required = true)
	@XmlElementWrapper(name = "instructions")
	private List<String> instructions;

	public Recipe() {

	}

	public Recipe(String image, String title, Author author, Integer yield, List<Ingredient> ingredients,
			Integer totalTime, String description, List<String> instructions) {
		super();
		this.image = image;
		this.title = title;
		this.author = author;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
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

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Author {

		@XmlElement(required = true)
		private String name;

		@XmlAttribute(required = true)
		private String profile;

		public Author() {
		}

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

		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Ingredient {

		@XmlElement(required = false)
		private Double amount;

		@XmlElement(required = false)
		private String unit;

		@XmlElement(required = true)
		private String name;

		public Ingredient() {
		}

		public Ingredient(Double amount, String unit, String name) {
			super();
			this.amount = amount;
			this.unit = unit;
			this.name = name;
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return ReflectionToStringBuilder.toString(this);
		}

	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public static void main(String[] args) throws Exception {

		Recipe recipe = new Recipe();

		recipe.setImage(
				"http://aprosef.hu/sites/default/files/styles/mainphoto_870x468/public/mainphotos/2014/habkarika13.jpg?itok=0-5dB9Wq");
		recipe.setTitle("Kar�csonyi habkarika recept");
		recipe.setTotalTime(160);
		recipe.setYield(13);

		Author auth = new Author();

		auth.setName("aprosef");
		auth.setProfile("aprosef.hu");

		recipe.setAuthor(auth);

		Ingredient in = new Ingredient();

		in.setAmount(10.0);
		in.setUnit("dkg");
		in.setName("cukor");

		List<Ingredient> inList = new LinkedList<Recipe.Ingredient>();

		inList.add(in);

		recipe.setIngredients(inList);
		
		List<String> insList = new LinkedList<String>();

		insList.add("Valami szöveg");

		recipe.setInstructions(insList);

		JAXBUtil.toXML(recipe, System.out);

	}

}
