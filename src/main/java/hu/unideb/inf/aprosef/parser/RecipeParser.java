package hu.unideb.inf.aprosef.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.aprosef.model.Recipe;
import hu.unideb.inf.aprosef.model.Recipe.Author;
import hu.unideb.inf.aprosef.model.Recipe.Ingredient;
import hu.unideb.inf.aprosef.model.Recipe.Instruction;
import hu.unideb.inf.jaxb.JAXBUtil;

public class RecipeParser {

	private static Logger logger = LoggerFactory.getLogger(RecipeParser.class);

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
			image = doc.select("div#field_mainphoto > a").first().attr("href");
			logger.info("Image: {}", image);
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setImage(image);

		String title = null;
		try {
			title = doc.select("h1.recipetitle").first().text().trim();
			logger.info("Title: {}", title);
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setTitle(title);

		Author author = new Author();
		try {
			author.setName(doc.select("div.username > a > span").first().text().trim());
			author.setProfile(doc.select("div.username > a").attr("href"));
			logger.info("Author: {} {}", author.getName(), author.getProfile());
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setAuthor(author);

		Integer yield = null;
		try {
			yield = Integer.parseInt(doc.select("div[itemprop=recipeYield] > span#number").first().text().trim());
			logger.info("Yield: {}", yield);
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setYield(yield);

		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			for (Element e : doc.select("div.field_counted_ingredients > ul > li")) {
				Ingredient ing = new Ingredient();

				if (!e.select("span").isEmpty()) {
					ing.setAmount(Double.parseDouble(e.select("span").first().text().trim()));
				} else {
					logger.warn("This ingredient does not have amount value!");
					ing.setAmount(null);
				}

				if (!e.select("a").isEmpty()) {
					ing.setName(e.select("a").first().text().trim());
					if (!e.ownText().isEmpty()){
						ing.setUnit(e.ownText());
					}else{
						logger.warn("This ingredient does not have unit value!");
						ing.setUnit(null);
					}
				} else {
					String[] ownText = e.ownText().trim().split(" ", 2);

					if (ownText.length == 2) {
						ing.setUnit(ownText[0]);
						ing.setName(ownText[1]);
					} else {
						ing.setName(ownText[0]);
					}
				}

				logger.info("Ingredient: {}", ing);

				ingredients.add(ing);
			}
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setIngredients(ingredients);

		Integer totalTime = null;

		try {
			totalTime = Integer.parseInt(doc.select("time[itemprop=totalTime] > span.number").first().text().trim());
			logger.info("Total time: {}", totalTime);
		} catch (Exception e) {
			throw new IOException("Malformed document");
		}
		recipe.setTotalTime(totalTime);

		String description = null;
		try {
			description = doc.select("div#summary > span[itemprop=description]").first().text().trim();
			logger.info("Description: {}", description);
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

				logger.info("Instruction: {}", ins);

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
			Recipe recipe = new RecipeParser().parse("http://aprosef.hu/paradicsomszoszos_gombas_penne_recept");
			System.out.println(recipe);
			JAXBUtil.toXML(recipe, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
