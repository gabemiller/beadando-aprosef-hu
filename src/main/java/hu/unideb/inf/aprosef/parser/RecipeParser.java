package hu.unideb.inf.aprosef.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import hu.unideb.inf.aprosef.model.Recipe;
import hu.unideb.inf.aprosef.model.Recipe.Author;
import hu.unideb.inf.aprosef.model.Recipe.Ingredient;
import hu.unideb.inf.aprosef.model.Recipe.Instruction;
import hu.unideb.inf.jaxb.JAXBUtil;

public class RecipeParser {

	public RecipeParser() {
	}

	public Recipe parse(String url) throws IOException {
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		Recipe recipe = parse(doc);
		return recipe;
	}

	public Recipe parse(File file) throws IOException {
		Document doc = Jsoup.parse(file, null);
		Recipe recipe = parse(doc);
		return recipe;
	}

	public Recipe parse(Document doc) throws IOException {

		Recipe recipe = new Recipe();

		String image = null;

		try {
			image = doc.select("div#field_mainphoto > a").attr("href");
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setImage(image);

		String title = null;

		try {
			title = doc.select("h1.recipetitle").first().text().trim();
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setTitle(title);

		Author author = new Author();
		try {
			author.setName(doc.select("div.username > a > span").first().text().trim());
			author.setProfile(doc.select("div.username > a").attr("href"));
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setAuthor(author);
		
		Integer yield = null;
		try {
			yield = Integer.parseInt(doc.select("div[itemprop=recipeYield] > span#number").first().text().trim());
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setYield(yield);

		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			for (Element e : doc.select("div[itemprop=recipeInstructions] > div.step")) {
				Ingredient ing = new Ingredient();
				
				
				
				ingredients.add(ing);
			}
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setIngredients(ingredients);
		
		Integer totalTime = null;
		
		try {
			totalTime = Integer.parseInt(doc.select("time[itemprop=totalTime] > span.number").first().text().trim());
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setTotalTime(totalTime);

		String description = null;
		try {
			description = doc.select("div#summary > span[itemprop=description]").first().text().trim();
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setDescription(description);

		List<Instruction> instructions = new ArrayList<Instruction>();
		try {
			for (Element e : doc.select("div[itemprop=recipeInstructions] > div.step")) {
				Instruction ins = new Instruction();
				ins.setStep(Integer.parseInt(e.select("div.stepnumber").first().text().trim()));
				ins.setText(e.select("div.text").first().text().trim());
				
				instructions.add(ins);
			}
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setInstructions(instructions);
		
		return recipe;
	}

	public static void main(String[] args) throws Exception {
		try {
			Recipe recipe = new RecipeParser().parse("http://aprosef.hu/kakukkfuves_gesztenyekremleves_recept");
			System.out.println(recipe);
			JAXBUtil.toXML(recipe, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
