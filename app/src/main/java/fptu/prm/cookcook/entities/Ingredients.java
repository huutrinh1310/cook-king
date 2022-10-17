package fptu.prm.cookcook.entities;

import com.google.gson.annotations.SerializedName;

public class Ingredients {

    @SerializedName("ingredientId")
    private String ingredientId;
    @SerializedName("ingredientName")
    private String ingredientName;

    public Ingredients() {
    }

    public Ingredients(String ingredientId, String ingredientName) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "ingredientId='" + ingredientId + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
