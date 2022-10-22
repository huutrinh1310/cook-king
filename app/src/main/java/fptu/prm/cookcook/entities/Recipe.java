package fptu.prm.cookcook.entities;

import java.util.Map;

public class Recipe {
    private int accountId;
    private int id;
    private String image;
    private Map<String,Ingredients> ingredients;
    private int readyInMinutes;
    private int servings;
    private Map<String,Step> steps;
    private String title;

    public Recipe() {
    }

    public Recipe(int accountId, int id, String image, Map<String, Ingredients> ingredients, int readyInMinutes, int servings, Map<String, Step> steps, String title) {
        this.accountId = accountId;
        this.id = id;
        this.image = image;
        this.ingredients = ingredients;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.steps = steps;
        this.title = title;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Map<String, Step> getSteps() {
        return steps;
    }

    public void setSteps(Map<String, Step> steps) {
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "accountId=" + accountId +
                ", id=" + id +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                ", readyInMinutes=" + readyInMinutes +
                ", servings=" + servings +
                ", steps=" + steps +
                ", title='" + title + '\'' +
                '}';
    }
}
