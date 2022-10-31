package fptu.prm.cookcook.dao;

import androidx.lifecycle.LiveData;

import java.util.Map;

import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Recipe;

public interface RecipeDao {


    // get all food
    void getAllRecipe(RecipeCallback callback);
    // get food by id
    void getRecipesById(String food, RecipeCallback callback);
    // get food by user id
    LiveData<Recipe> getRecipeByUserId(String userId);
    // add food
    void addRecipe(Recipe recipe, RecipeCallback callback);
    // update food
    void updateRecipe(Map<String, Object> food, RecipeCallback callback);
    // delete food
    void deleteRecipe(String foodId, RecipeCallback callback);

}
