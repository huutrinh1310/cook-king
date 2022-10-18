package fptu.prm.cookcook.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Food {
    @SerializedName("foodId")
    private String foodId;
    @SerializedName("foodName")
    private String foodName;
    @SerializedName("ingredients")
    private List<Ingredients> ingredients;
    @SerializedName("userId")
    private String userId;
    @SerializedName("description")
    private String description;

    public Food() {
    }

    public Food(String foodId, String foodName, List<Ingredients> ingredients, String userId, String description) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.userId = userId;
        this.description = description;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", ingredients=" + ingredients +
                ", userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
