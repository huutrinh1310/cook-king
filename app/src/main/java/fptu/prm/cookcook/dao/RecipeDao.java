package fptu.prm.cookcook.dao;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Recipe;

public interface RecipeDao {


    // get all food
    LiveData<List<Recipe>> getAllRecipe();
    // get food by id
    LiveData<List<Recipe>> getRecipesById(String foodId);
    // get food by user id
    LiveData<List<Recipe>> getRecipeByUserId(String userId);
    // add food
    void addRecipe(Recipe recipe, RecipeCallback callback);
    // update food
    void updateRecipe(Map<String, Object> food, RecipeCallback callback);
    // delete food
    void deleteRecipe(int foodId, RecipeCallback callback);
}
