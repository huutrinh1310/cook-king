package fptu.prm.cookcook.entities;

import java.io.Serializable;
import java.util.Map;

import fptu.prm.cookcook.dao.Impl.AccountDaoImpl;
import fptu.prm.cookcook.dao.callback.AccountCallBack;

public class Recipe implements Serializable {
    private int accountId;
    private int id;
    private String image;
    private String description;
    private Map<String, Ingredients> ingredients;
    private int readyInMinutes;
    private String servings;
    private Map<String, Step> steps;
    private String title;

    public Recipe() {
    }

    public Recipe(int accountId, int id, String image, String description, Map<String, Ingredients> ingredients, int readyInMinutes, String servings, Map<String, Step> steps, String title) {
        this.accountId = accountId;
        this.id = id;
        this.image = image;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
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

    public Account getAccount() {
        final Account[] account = {new Account()};
        AccountDaoImpl.getInstance().getAccountById(accountId, new AccountCallBack() {
            @Override
            public void onSuccess(Object object) {
                account[0] = (Account) object;
            }

            @Override
            public void onFail(String message) {

            }
        });
        return account[0];
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
